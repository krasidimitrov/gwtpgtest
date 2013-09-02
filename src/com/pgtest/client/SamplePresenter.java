package com.pgtest.client;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.googlecode.gwtphonegap.client.PhoneGap;
import com.googlecode.gwtphonegap.client.camera.CameraMobileImpl;
import com.googlecode.gwtphonegap.client.camera.PictureCallback;
import com.googlecode.gwtphonegap.client.camera.PictureOptions;
import com.googlecode.gwtphonegap.client.camera.PopOverOptions;
import com.googlecode.gwtphonegap.client.connection.Connection;
import com.googlecode.gwtphonegap.client.connection.ConnectionMobileImpl;
import com.googlecode.gwtphonegap.client.file.*;
import com.googlecode.gwtphonegap.client.file.js.FileJsImpl;

/**
 * @author Krasimir Dimitrov (krasimir.dimitrov@clouway.com, kpackapgo@gmail.com)
 */
public class SamplePresenter {

  private final SampleViewImpl sampleView;
  private final PhoneGap phoneGap;
  private final PGTestServiceAsync pgtestService;

  public SamplePresenter(SampleViewImpl sampleView, PhoneGap phoneGap, PGTestServiceAsync pgtestService){
    this.sampleView = sampleView;
    this.phoneGap = phoneGap;
    this.pgtestService = pgtestService;
  }

  public void openCamera(){
    PictureOptions options = new PictureOptions(25);
    options.setDestinationType(PictureOptions.DESTINATION_TYPE_DATA_URL);
    options.setSourceType(PictureOptions.PICTURE_SOURCE_TYPE_CAMERA);
    options.setAllowEdit(false);
//    options.setSaveToPhotoAlbum(true);

//    phoneGap.initializePhoneGap();
//
//    phoneGap.getCamera().getPicture(options, new PictureCallback() {
//
//      @Override
//      public void onSuccess(String data) {
////        clientFactory.getCameraDisplay().displayFoto("data:image/jpeg;base64," + data);
//      }
//
//      @Override
//      public void onFailure(String message) {
//        Window.alert("failure");
//      }
//    });

    CameraMobileImpl cameraMobile = new CameraMobileImpl();
    cameraMobile.getPicture(options, new PictureCallback() {
      @Override
      public void onSuccess(String data) {

        Window.alert("SUCCESS");
        ConnectionMobileImpl connectionMobile = new ConnectionMobileImpl();

        String connectionType = connectionMobile.getType();
        if(!connectionType.equals(Connection.WIFI)){
          Window.alert("NO INTERNET IMAGE IS SAVED AND WILL BE SEND TO THE SERVER WHEN INTERNET IS RESTORED");
          accessFileSystem(data);
        }
      }

      @Override
      public void onFailure(String message) {
        Window.alert("FAIL");
      }
    });
  }

  private void accessFileSystem(final String content){

    FileJsImpl fileJs = new FileJsImpl();

    fileJs.requestFileSystem(FileSystem.LocalFileSystem_PERSISTENT, 0, new FileCallback<FileSystem, FileError>() {

      @Override
      public void onSuccess(FileSystem entry) {
        DirectoryEntry root = entry.getRoot();
        Window.alert(root.getName());
        createFile(root, content);

      }

      @Override
      public void onFailure(FileError error) {
        Window.alert("FILE SYSTEM NOT OPENED1");
        Window.alert(error.toString());
        Window.alert(String.valueOf(error.getErrorCode()));

      }
    });
  }

  private void createFile(DirectoryEntry dirEntry, final String content){

    dirEntry.getFile("test.txt", new Flags(true, false), new FileCallback<FileEntry, FileError>() {
      @Override
      public void onSuccess(FileEntry entry) {
       Window.alert(entry.getName());
       writeToFile(entry, content);
      }

      @Override
      public void onFailure(FileError error) {
        Window.alert("FILE NOT CREATED");

      }
    });
  }

  private void writeToFile(FileEntry fileEntry, final String content){

    fileEntry.createWriter(new FileCallback<FileWriter, FileError>() {

      @Override
      public void onSuccess(FileWriter writer) {
        Window.alert("WRITING TO FILE");
        writer.write(content);
        writer.setOnWriteEndCallback(new WriterCallback<FileWriter>() {

          @Override
          public void onCallback(FileWriter result) {
            Window.alert("FILE WRITE CALLBACK SUCCESS");
          }
        });

        writer.setOnErrorCallback(new WriterCallback<FileWriter>() {

          @Override
          public void onCallback(FileWriter result) {
            Window.alert("FILE WRITE CALLBACK FAIL");

          }
        });
//        writer.write(display.getFileContent().getText());

      }

      @Override
      public void onFailure(FileError error) {
        Window.alert("Writing to file FAILED");

      }
    });
  }

  public void saveImage() {
    pgtestService.saveImage("abcd123", new AsyncCallback<String>() {
      @Override
      public void onFailure(Throwable caught) {
        Window.alert("fail to save to GCS");
      }

      @Override
      public void onSuccess(String result) {
        Window.alert("successful save to GCS");
      }
    });
  }
}
