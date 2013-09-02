package com.pgtest.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.googlecode.gwtphonegap.client.PhoneGap;
import com.googlecode.gwtphonegap.client.PhoneGapAvailableEvent;
import com.googlecode.gwtphonegap.client.PhoneGapAvailableHandler;
import com.googlecode.gwtphonegap.client.PhoneGapTimeoutEvent;
import com.googlecode.gwtphonegap.client.PhoneGapTimeoutHandler;
import com.googlecode.gwtphonegap.client.camera.CameraMobileImpl;
import com.googlecode.gwtphonegap.client.camera.PictureCallback;
import com.googlecode.gwtphonegap.client.camera.PictureOptions;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class PGTest implements EntryPoint {

  private PGTestServiceAsync pgtestService =
          GWT.create(PGTestService.class);
  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {
    final PhoneGap phoneGap = GWT.create(PhoneGap.class);

    phoneGap.addHandler(new PhoneGapAvailableHandler() {

      @Override
      public void onPhoneGapAvailable(PhoneGapAvailableEvent event) {

//        PictureOptions options = new PictureOptions(25);
//        options.setDestinationType(PictureOptions.DESTINATION_TYPE_DATA_URL);
//        options.setSourceType(PictureOptions.PICTURE_SOURCE_TYPE_CAMERA);
//
//        CameraMobileImpl cameraMobile = new CameraMobileImpl();
//        cameraMobile.getPicture(options, new PictureCallback() {
//          @Override
//          public void onSuccess(String data) {
//          }
//
//          @Override
//          public void onFailure(String message) {
//            Window.alert("FAIL");
//          }
//        });


        SampleViewImpl view = new SampleViewImpl();
        SamplePresenter presenter = new SamplePresenter(view, phoneGap, pgtestService);
        view.setPresenter(presenter);

        RootPanel.get().add(view);

      }
    });

    phoneGap.addHandler(new PhoneGapTimeoutHandler() {

      @Override
      public void onPhoneGapTimeout(PhoneGapTimeoutEvent event) {
        //can not start phonegap - something is for with your setup
      }
    });

    phoneGap.initializePhoneGap();

  }
}
