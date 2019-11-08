package dev.georgebarker.androidsensorclient.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import dev.georgebarker.androidsensorclient.model.SensorEvent;
import dev.georgebarker.androidsensorclient.presenter.MainPresenter;
import dev.georgebarker.androidsensorclient.presenter.MainPresenterImpl;
import dev.georgebarker.androidsensorclient.R;

public class MainActivity extends AppCompatActivity implements MainView {


    @BindView(R.id.edit_text_device_id)
    EditText deviceIdEditText;

    @BindView(R.id.edit_text_sensor_serial_number)
    EditText sensorIdEditText;

    @BindView(R.id.button_unlock)
    Button unlockButton;

    @BindView(R.id.main_activity_parent_view)
    View parentView;

    @BindView(R.id.recycler_view_sensor_events)
    RecyclerView sensorEventsRecyclerView;

    @BindView(R.id.text_view_waiting_for_events)
    TextView waitingForEventsTextView;

    private SensorEventRecyclerViewAdapter sensorEventRecyclerViewAdapter;

    private MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mainPresenter = new MainPresenterImpl(this);

        unlockButton.setOnClickListener(view -> mainPresenter.onUnlockButtonClicked(getDeviceId(), getSensorId()));

        sensorEventsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        sensorEventRecyclerViewAdapter = new SensorEventRecyclerViewAdapter();
        sensorEventsRecyclerView.setAdapter(sensorEventRecyclerViewAdapter);


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
        runOnUiThread(() -> {
            waitingForEventsTextView.setVisibility(View.GONE);
            sensorEventsRecyclerView.setVisibility(View.VISIBLE);
            sensorEventRecyclerViewAdapter.addSensorEvent(sensorEvent); });
    }

    private String getDeviceId() {
        return deviceIdEditText.getText().toString();
    }

    private String getSensorId() {
        return sensorIdEditText.getText().toString();
    }
}
