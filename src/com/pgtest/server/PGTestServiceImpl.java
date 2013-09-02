package com.pgtest.server;

import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.pgtest.client.PGTestService;

import java.io.IOException;

public class PGTestServiceImpl extends RemoteServiceServlet implements PGTestService {
  private GCSservice localExample;
  // Implementation of sample interface method

  public PGTestServiceImpl(){
    this.localExample = new GCSservice();
  }

  public String saveImage(String imageEncoded) {

    GcsFilename filename = new GcsFilename("image_bucket_test", "image");

    try {
      localExample.writeToFile(filename, "jpg", imageEncoded.getBytes());
    } catch (IOException e) {
      e.printStackTrace();
    }

    return null;
  }
}