package dev.georgebarker.androidsensorclient.view;

import android.content.Context;

import dev.georgebarker.androidsensorclient.model.SensorEvent;

public interface MainView {

    Context getContext();
    void onError(String message);
    void addSensorEvent(SensorEvent sensorEvent);
}
