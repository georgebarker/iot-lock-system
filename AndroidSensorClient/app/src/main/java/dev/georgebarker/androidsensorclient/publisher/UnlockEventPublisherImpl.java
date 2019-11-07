package dev.georgebarker.androidsensorclient.publisher;


import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;

import dev.georgebarker.androidsensorclient.R;
import dev.georgebarker.androidsensorclient.provider.MqttClientProvider;
import dev.georgebarker.androidsensorclient.model.UnlockEvent;

public class UnlockEventPublisherImpl implements UnlockEventPublisher {

    private static final String TAG = UnlockEventPublisherImpl.class.getSimpleName();

    private MqttClient mqttClient;
    private Context context;
    private Gson gson = new Gson();

    public UnlockEventPublisherImpl(Context context) throws MqttException {
        this.context = context;
        mqttClient = MqttClientProvider.getMqttClient(context);
    }

    @Override
    public void publish(final UnlockEvent unlockEvent) {
        final String sensorEventJson = convertSensorEventToJson(unlockEvent);
        final String topicName = getTopicName();

        Log.i(TAG, "Publishing on topic: " + topicName + " Sensor Event: " + unlockEvent + " using JSON: " + sensorEventJson + "...");
        try {
            publishMessageToTopic(new MqttMessage(sensorEventJson.getBytes()), topicName);
        } catch (final MqttException e) {
            Log.e(TAG, "Failed to publish on topic: " + topicName + " Sensor Event: " + unlockEvent + " using JSON: " + sensorEventJson + ".");
            return;
        }
        Log.i(TAG, "Successfully published on topic: " + topicName + " Sensor Event: " + unlockEvent + " using JSON: " + sensorEventJson + ".");
    }

    private void publishMessageToTopic(final MqttMessage message, final String topicName) throws MqttException {
        final MqttTopic topic = mqttClient.getTopic(topicName);
        topic.publish(message);
    }

    private String convertSensorEventToJson(final UnlockEvent unlockEvent) {
        return gson.toJson(unlockEvent);
    }

    private String getTopicName() {
        return context.getString(R.string.sensor_client_topic_name);
    }
}
