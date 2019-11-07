package dev.georgebarker.androidsensorclient.presenter;

import dev.georgebarker.androidsensorclient.model.UnlockEvent;

public interface MainPresenter {

    void onViewPrepared();

    void onUnlockButtonClicked(String deviceId, String sensorId);
}
