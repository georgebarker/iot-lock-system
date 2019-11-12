package dev.georgebarker.androidsensorclient.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dev.georgebarker.androidsensorclient.model.Room;
import dev.georgebarker.androidsensorclient.model.SensorEvent;
import dev.georgebarker.androidsensorclient.presenter.MainPresenter;
import dev.georgebarker.androidsensorclient.presenter.MainPresenterImpl;
import dev.georgebarker.androidsensorclient.R;

public class MainActivity extends AppCompatActivity implements MainView {

    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.edit_text_device_id)
    EditText deviceIdEditText;

    @BindView(R.id.spinner_room_number)
    AppCompatSpinner roomNumberSpinner;

    @BindView(R.id.button_unlock)
    Button unlockButton;

    @BindView(R.id.button_confirm_device_id)
    Button confirmButton;

    @BindView(R.id.main_activity_parent_view)
    View parentView;

    @BindView(R.id.recycler_view_sensor_events)
    RecyclerView sensorEventsRecyclerView;

    @BindView(R.id.text_view_instructions)
    TextView instructionsTextView;

    @BindView(R.id.linear_layout_select_room_number)
    LinearLayout selectRoomNumberLinearLayout;

    private SensorEventRecyclerViewAdapter sensorEventRecyclerViewAdapter;
    ArrayAdapter<Integer> roomNumberSpinnerAdapter;

    private MainPresenter mainPresenter;

    private Integer selectedRoomNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mainPresenter = new MainPresenterImpl(this);

        confirmButton.setOnClickListener(view -> mainPresenter.onDeviceIdConfirmButtonClicked(getDeviceId()));

        unlockButton.setOnClickListener(view -> mainPresenter.onUnlockButtonClicked(getDeviceId(), selectedRoomNumber));

        setupRoomNumberSpinner();
        setupSensorEventsRecyclerView();

        mainPresenter.onViewPrepared();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onError(String message) {
        Snackbar.make(parentView, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void addSensorEvent(SensorEvent sensorEvent) {
        int roomNumber = sensorEvent.getRoomNumber();
        if (roomNumber != selectedRoomNumber) {
            Log.w(TAG, "Won't add Sensor Event, user has room " + selectedRoomNumber + "selected, but this event is for room: " + roomNumber);
        }
        runOnUiThread(() -> {
            sensorEventsRecyclerView.setVisibility(View.VISIBLE);
            sensorEventRecyclerViewAdapter.addSensorEvent(sensorEvent);
        });
    }

    @Override
    public void populateRoomsSpinner(List<Room> rooms) {
        if (rooms.isEmpty()) {
            instructionsTextView.setText("This Device ID does not have any rooms.");
            selectRoomNumberLinearLayout.setVisibility(View.INVISIBLE);
            sensorEventsRecyclerView.setVisibility(View.INVISIBLE);
            sensorEventRecyclerViewAdapter.clearSensorEvents();
        } else {
            for (Room room : rooms) {
                roomNumberSpinnerAdapter.add(room.getRoomNumber());
            }
            roomNumberSpinnerAdapter.notifyDataSetChanged();
            selectRoomNumberLinearLayout.setVisibility(View.VISIBLE);
            instructionsTextView.setText("Select a room number to see events & unlock it.");
        }
    }

    private void setupRoomNumberSpinner() {
        roomNumberSpinnerAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item);
        roomNumberSpinner.setAdapter(roomNumberSpinnerAdapter);
        roomNumberSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedRoomNumber = roomNumberSpinnerAdapter.getItem(i);
                instructionsTextView.setText("Listening for events on room " + selectedRoomNumber);
                unlockButton.setEnabled(true);
                sensorEventRecyclerViewAdapter.clearSensorEvents();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                selectedRoomNumber = null;
                unlockButton.setEnabled(false);
            }
        });
    }

    private void setupSensorEventsRecyclerView() {
        sensorEventsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        sensorEventRecyclerViewAdapter = new SensorEventRecyclerViewAdapter();
        sensorEventsRecyclerView.setAdapter(sensorEventRecyclerViewAdapter);
    }

    private String getDeviceId() {
        return deviceIdEditText.getText().toString();
    }


}
