package com.pgtest.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface PGTestServiceAsync {
  void saveImage(String imageEncoded, AsyncCallback<String> async);
}
