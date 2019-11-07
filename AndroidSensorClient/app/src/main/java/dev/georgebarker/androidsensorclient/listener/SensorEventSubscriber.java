package dev.georgebarker.androidsensorclient.listener;

import dev.georgebarker.androidsensorclient.model.SensorEvent;

public interface SensorEventSubscriber {


    void processSensorEventMessage(SensorEvent sensorEvent);
}
