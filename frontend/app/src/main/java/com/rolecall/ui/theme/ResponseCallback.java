package com.rolecall.ui.theme;

import java.io.IOException;

public interface ResponseCallback {
    void onSuccess(String response);
    void onError(IOException e);
}