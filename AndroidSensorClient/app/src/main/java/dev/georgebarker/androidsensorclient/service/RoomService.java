package dev.georgebarker.androidsensorclient.service;

import java.util.List;

import dev.georgebarker.androidsensorclient.model.Room;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RoomService {
    @GET("rooms/{deviceId}")
    Call<List<Room>> findRoomsForDeviceId(@Path("deviceId") String deviceId);
}
