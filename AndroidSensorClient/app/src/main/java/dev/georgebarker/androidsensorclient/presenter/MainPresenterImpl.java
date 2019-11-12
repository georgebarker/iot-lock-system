package dev.georgebarker.androidsensorclient.presenter;

import android.util.Log;

import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.List;

import dev.georgebarker.androidsensorclient.listener.SensorEventListenerImpl;
import dev.georgebarker.androidsensorclient.listener.SensorEventSubscriber;
import dev.georgebarker.androidsensorclient.model.Room;
import dev.georgebarker.androidsensorclient.model.SensorEvent;
import dev.georgebarker.androidsensorclient.model.UnlockEvent;
import dev.georgebarker.androidsensorclient.publisher.UnlockEventPublisher;
import dev.georgebarker.androidsensorclient.publisher.UnlockEventPublisherImpl;
import dev.georgebarker.androidsensorclient.service.RoomService;
import dev.georgebarker.androidsensorclient.view.MainView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainPresenterImpl implements MainPresenter, SensorEventSubscriber {

    private static final String TAG = MainPresenterImpl.class.getSimpleName();
    private MainView mainView;
    private UnlockEventPublisher unlockEventPublisher;
    private RoomService roomService;

    public MainPresenterImpl(MainView mainView) {
        this.mainView = mainView;
    }

    @Override
    public void onViewPrepared() {
        try {
            new SensorEventListenerImpl(mainView.getContext(), this);
            unlockEventPublisher = new UnlockEventPublisherImpl(mainView.getContext());
        } catch (MqttException e) {
            mainView.onError("Failed to connect to server. Please restart and try again.");
            e.printStackTrace();
        }

        roomService = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8787/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RoomService.class);
    }

    @Override
    public void onUnlockButtonClicked(String deviceId, int roomNumber) {
        if (!isDeviceIdValid(deviceId)) {
            mainView.onError("Device ID cannot be empty!");
            return;
        }

        UnlockEvent unlockEvent = new UnlockEvent(deviceId, roomNumber);
        publishSensorEvent(unlockEvent);
    }

    @Override
    public void onDeviceIdConfirmButtonClicked(String deviceId) {
        if (!isDeviceIdValid(deviceId)) {
            mainView.onError("Device ID cannot be empty!");
            return;
        }

        Call<List<Room>> roomsCall = roomService.findRoomsForDeviceId(deviceId);
        roomsCall.enqueue(new Callback<List<Room>>() {
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "Call was successful!");
                    mainView.populateRoomsSpinner(response.body());
                } else {
                    Log.w(TAG, "Call with Device ID " + deviceId + "failed, status code " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Room>> call, Throwable t) {
                mainView.onError("Failed to retrieve rooms for Device ID " + deviceId);
                Log.e(TAG, "Failed", t);
            }
        });
    }

    @Override
    public void processSensorEvent(SensorEvent sensorEvent) {
        mainView.addSensorEvent(sensorEvent);
    }

    private void publishSensorEvent(UnlockEvent unlockEvent) {
        if (unlockEventPublisher == null) {
            mainView.onError("Failed to connect to server. Please restart and try again.");
            return;
        }

        unlockEventPublisher.publish(unlockEvent);
    }

    private boolean isDeviceIdValid(String deviceId) {
        return deviceId != null && !deviceId.isEmpty();
    }
}
