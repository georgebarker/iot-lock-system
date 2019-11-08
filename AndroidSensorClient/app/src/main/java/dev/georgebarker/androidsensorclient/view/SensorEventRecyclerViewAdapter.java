package dev.georgebarker.androidsensorclient.view;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dev.georgebarker.androidsensorclient.R;
import dev.georgebarker.androidsensorclient.model.SensorEvent;

public class SensorEventRecyclerViewAdapter extends RecyclerView.Adapter<SensorEventRecyclerViewAdapter.SensorEventViewHolder> {

    private List<SensorEvent> sensorEvents = new ArrayList<>();
    private static final String UNSUCCESSFUL_BACKGROUND_COLOR = "#FFD2D2";
    private static final String SUCCESSFUL_BACKGROUND_COLOR = "#DFF2BF";

    @NonNull
    @Override
    public SensorEventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sensor_event_item_view, parent, false);
        return new SensorEventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SensorEventViewHolder holder, int position) {
        SensorEvent sensorEvent = sensorEvents.get(position);
        holder.roomNumberTextView.setText(String.valueOf(sensorEvent.getRoomNumber()));
        holder.tagIdTextView.setText(sensorEvent.getTagId());
        holder.timestampTextView.setText(sensorEvent.getTimestamp().toString());
        holder.messageTextView.setText(sensorEvent.getMessage());
        String backgroundColor = sensorEvent.isSuccessful() ? SUCCESSFUL_BACKGROUND_COLOR : UNSUCCESSFUL_BACKGROUND_COLOR;
        int successIconDrawable = sensorEvent.isSuccessful() ? R.drawable.success_button : R.drawable.error_button;
        holder.sensorEventItemLayout.setBackgroundColor(Color.parseColor(backgroundColor));
        holder.successImageView.setBackgroundResource(successIconDrawable);

    }

    @Override
    public int getItemCount() {
        return sensorEvents.size();
    }

    public void addSensorEvent(SensorEvent sensorEvent) {
        sensorEvents.add(0, sensorEvent);
        notifyDataSetChanged();
    }

    static class SensorEventViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_view_room_number)
        TextView roomNumberTextView;

        @BindView(R.id.sensor_event_item)
        LinearLayout sensorEventItemLayout;

        @BindView(R.id.text_view_tag_id)
        TextView tagIdTextView;

        @BindView(R.id.text_view_timestamp)
        TextView timestampTextView;

        @BindView(R.id.image_view_success)
        ImageView successImageView;

        @BindView(R.id.text_view_message)
        TextView messageTextView;

        SensorEventViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
