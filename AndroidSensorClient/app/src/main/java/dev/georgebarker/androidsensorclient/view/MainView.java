package dev.georgebarker.androidsensorclient.view;

import android.content.Context;

public interface MainView {

    Context getContext();
    void onError(String message);
}
