package com.pgtest.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("PGTestService")
public interface PGTestService extends RemoteService {
  // Sample interface method of remote interface
  String saveImage(String imageEncoded);

  /**
   * Utility/Convenience class.
   * Use PGTestService.App.getInstance() to access static instance of PGTestServiceAsync
   */
  public static class App {
    private static PGTestServiceAsync ourInstance = GWT.create(PGTestService.class);

    public static synchronized PGTestServiceAsync getInstance() {
      return ourInstance;
    }
  }
}
