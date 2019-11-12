package dev.georgebarker.androidsensorclient.view;

import android.content.Context;

import java.util.List;

import dev.georgebarker.androidsensorclient.model.Room;
import dev.georgebarker.androidsensorclient.model.SensorEvent;

public interface MainView {

    Context getContext();
    void onError(String message);
    void addSensorEvent(SensorEvent sensorEvent);
    void populateRoomsSpinner(List<Room> rooms);
}
