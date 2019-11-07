package dev.georgebarker.androidsensorclient.listener;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import dev.georgebarker.androidsensorclient.R;
import dev.georgebarker.androidsensorclient.model.SensorEvent;
import dev.georgebarker.androidsensorclient.provider.MqttClientProvider;

public class SensorEventListenerImpl extends BaseMqttListener implements SensorEventListener {

    private static final String TAG = SensorEventListenerImpl.class.getSimpleName();


    private MqttClient mqttClient;
    private SensorEventSubscriber sensorEventSubscriber;
    private Context context;
    private Gson gson = new Gson();

    public SensorEventListenerImpl(Context context, SensorEventSubscriber sensorEventSubscriber) throws MqttException {
        this.context = context;
        this.mqttClient = MqttClientProvider.getMqttClient(context);
        this.sensorEventSubscriber = sensorEventSubscriber;
        subscribeToTopic();
    }

    public void subscribeToTopic() throws MqttException {
	final String topicName = getTopicName();
	Log.i(TAG, "Attempting to subscribe to topic: " + topicName + "...");
	try {
	    mqttClient.setCallback(this);
	    mqttClient.subscribe(topicName);
        Log.i(TAG, "Subscribed to topic " + topicName + ", listening for messages...");
	} catch (final MqttException e) {
        Log.e(TAG, "Failed to subscribe to topic " + topicName + ".");
        throw e;
	}
    }

    @Override
    public void messageArrived(final String topic, final MqttMessage message) {
	Log.i(TAG, "Received " + message + " on topic " + topic + ", processing sensor event message...");
	SensorEvent sensorEvent = transformMessageIntoSensorEvent(message);
        sensorEventSubscriber.processSensorEventMessage(sensorEvent);
    }

    private String getTopicName() {
	return context.getString(R.string.lock_client_topic_name);
    }

    private SensorEvent transformMessageIntoSensorEvent(MqttMessage message) {
        return gson.fromJson(message.toString(), SensorEvent.class);
    }

}
