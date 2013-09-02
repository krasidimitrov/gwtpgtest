package com.pgtest.server;

import com.google.appengine.tools.cloudstorage.GcsFileOptions;
import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.google.appengine.tools.cloudstorage.GcsInputChannel;
import com.google.appengine.tools.cloudstorage.GcsOutputChannel;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;
import com.google.appengine.tools.cloudstorage.RetryParams;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * A main method to show how to use the GCS client locally.
 *
 */
public class GCSservice {

  /**
   * Use this to make the library run locally as opposed to in a deployed servlet.
   */

  /**
   * This is the service from which all requests are initiated.
   * The retry and exponential backoff settings are configured here.
   */
  private final GcsService gcsService =
          GcsServiceFactory.createGcsService(RetryParams.getDefaultInstance());


  /**
   * Writes the byte array to the specified file. Note that the close at the end is not in a
   * finally.This is intentional. Because the file only exists for reading if close is called, if
   * there is an exception thrown while writing the file won't ever exist. (This way there is no
   * need to worry about cleaning up partly written files)
   */
  public void writeToFile(GcsFilename fileName, String fileType, byte[] content) throws IOException {
    GcsOutputChannel outputChannel =
            gcsService.createOrReplace(fileName, new GcsFileOptions.Builder().mimeType(fileType).acl("49077933619").build());
    outputChannel.write(ByteBuffer.wrap(content));
    outputChannel.close();
  }

  /**
   * Reads the contents of an entire file and returns it as a byte array. This works by first
   * requesting the length, and then fetching the whole file in a single call. (Because it calls
   * openReadChannel instead of openPrefetchingReadChannel there is no buffering, and thus there is
   * no need to wrap the read call in a loop)
   *
   * This is really only a good idea for small files. Large files should be streamed out using the
   * prefetchingReadChannel and processed incrementally.
   */
  private byte[] readFromFile(GcsFilename fileName) throws IOException {
    int fileSize = (int) gcsService.getMetadata(fileName).getLength();
    ByteBuffer result = ByteBuffer.allocate(fileSize);
    GcsInputChannel readChannel = gcsService.openReadChannel(fileName, 0);
    try {
      readChannel.read(result);
    } finally {
      readChannel.close();
    }
    return result.array();
  }
}