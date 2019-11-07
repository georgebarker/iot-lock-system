package dev.georgebarker.androidsensorclient.presenter;

import org.eclipse.paho.client.mqttv3.MqttException;

import dev.georgebarker.androidsensorclient.listener.SensorEventListener;
import dev.georgebarker.androidsensorclient.listener.SensorEventListenerImpl;
import dev.georgebarker.androidsensorclient.listener.SensorEventSubscriber;
import dev.georgebarker.androidsensorclient.model.SensorEvent;
import dev.georgebarker.androidsensorclient.model.UnlockEvent;
import dev.georgebarker.androidsensorclient.publisher.UnlockEventPublisher;
import dev.georgebarker.androidsensorclient.publisher.UnlockEventPublisherImpl;
import dev.georgebarker.androidsensorclient.view.MainView;

public class MainPresenterImpl implements MainPresenter, SensorEventSubscriber {

    private MainView mainView;
    private UnlockEventPublisher unlockEventPublisher;
    private SensorEventListener sensorEventListener;

    public MainPresenterImpl(MainView mainView) {
        this.mainView = mainView;
    }

    @Override
    public void onViewPrepared() {
        try {
            sensorEventListener = new SensorEventListenerImpl(mainView.getContext(), this);
            unlockEventPublisher = new UnlockEventPublisherImpl(mainView.getContext());
        } catch (MqttException e) {
            mainView.onError("Failed to connect to server. Please restart and try again.");
            e.printStackTrace();
        }
    }

    @Override
    public void onUnlockButtonClicked(String deviceId, String sensorId) {
        if(!isDeviceAndSensorIdValid(deviceId, sensorId)) {
            mainView.onError("Device ID " + deviceId + " or Sensor ID " + sensorId + " are not valid. Please try again.");
            return;
        }

        UnlockEvent unlockEvent = new UnlockEvent(deviceId, Integer.valueOf(sensorId));
        publishSensorEvent(unlockEvent);
    }

    @Override
    public void processSensorEventMessage(SensorEvent sensorEvent) {
        mainView.onError(sensorEvent.toString());
    }

    private void publishSensorEvent(UnlockEvent unlockEvent) {
        if (unlockEventPublisher == null) {
            mainView.onError("Failed to connect to server. Please restart and try again.");
            return;
        }

        unlockEventPublisher.publish(unlockEvent);
    }

    private boolean isDeviceAndSensorIdValid(String deviceId, String sensorId) {
        try {
            Integer.valueOf(sensorId);
        } catch (NumberFormatException e) {
            return false;
        }
        return deviceId != null && !deviceId.isEmpty();
    }
}
