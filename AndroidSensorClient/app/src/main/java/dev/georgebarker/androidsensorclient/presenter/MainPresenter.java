package dev.georgebarker.androidsensorclient.presenter;

public interface MainPresenter {

    void onViewPrepared();

    void onUnlockButtonClicked(String deviceId, int roomNumber);

    void onDeviceIdConfirmButtonClicked(String deviceId);
}
