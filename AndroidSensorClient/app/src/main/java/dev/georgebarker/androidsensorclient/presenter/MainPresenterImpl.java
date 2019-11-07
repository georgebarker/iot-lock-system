package dev.georgebarker.androidsensorclient.presenter;

import org.eclipse.paho.client.mqttv3.MqttException;

import dev.georgebarker.androidsensorclient.model.UnlockEvent;
import dev.georgebarker.androidsensorclient.publisher.UnlockEventPublisher;
import dev.georgebarker.androidsensorclient.publisher.UnlockEventPublisherImpl;
import dev.georgebarker.androidsensorclient.view.MainView;

public class MainPresenterImpl implements MainPresenter {

    private MainView mainView;
    private UnlockEventPublisher unlockEventPublisher;

    public MainPresenterImpl(MainView mainView) {
        this.mainView = mainView;
    }

    @Override
    public void onViewPrepared() {
        try {
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
