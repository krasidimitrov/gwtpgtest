package com.pgtest.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.dom.client.event.touch.TouchCancelEvent;
import com.googlecode.mgwt.dom.client.event.touch.TouchEndEvent;
import com.googlecode.mgwt.dom.client.event.touch.TouchHandler;
import com.googlecode.mgwt.dom.client.event.touch.TouchMoveEvent;
import com.googlecode.mgwt.dom.client.event.touch.TouchStartEvent;
import com.googlecode.mgwt.ui.client.widget.Button;

/**
 * @author Krasimir Dimitrov (krasimir.dimitrov@clouway.com, kpackapgo@gmail.com)
 */
public class SampleViewImpl extends Composite {
  private SamplePresenter presenter;

  interface SampleViewImplUiBinder extends UiBinder<Widget, SampleViewImpl> {
  }

  private static SampleViewImplUiBinder binder = GWT.create(SampleViewImplUiBinder.class);

  @UiField
  Button openCameraButton;

  @UiField
  Button saveButton;

  public SampleViewImpl() {

    initWidget(binder.createAndBindUi(this));

    Window.alert("APPLICATION STARTED");

    openCameraButton.addTapHandler(new TapHandler() {
      @Override
      public void onTap(TapEvent event) {
        presenter.openCamera();
      }
    });

    saveButton.addTapHandler(new TapHandler() {
      @Override
      public void onTap(TapEvent event) {
        presenter.saveImage();
      }
    });
  }

  public void setPresenter(SamplePresenter presenter) {
    this.presenter = presenter;
  }
}