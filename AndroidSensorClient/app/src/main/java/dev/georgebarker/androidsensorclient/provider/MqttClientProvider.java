package dev.georgebarker.androidsensorclient.provider;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import dev.georgebarker.androidsensorclient.R;

public class MqttClientProvider {

    private static final String TAG = MqttClientProvider.class.getSimpleName();

    private static MqttClient mqttClient;

    private MqttClientProvider() {
        // can't create
    }

    public static MqttClient getMqttClient(Context context) throws MqttException {
        if (mqttClient == null) {
            String brokerUrl = getBrokerUrl(context);
            String userId = getUserId(context);
            Log.i(TAG, "Setting up MQTT Client using Broker URL: " + brokerUrl + " and User ID: " + userId + "...");
            try {

                mqttClient = new MqttClient(brokerUrl, userId, new MemoryPersistence());
                mqttClient.connect();
            } catch (final MqttException e) {
                Log.e(TAG, "Client could not be created using Broker URL: " + brokerUrl + " and User ID: " + userId + ".", e);
                throw e;
            }

            Log.i(TAG, "Created MQTT Client successfully.");
        }
        return mqttClient;

    }

    private static String getBrokerUrl(Context context) {
        return context.getString(R.string.broker_url);
    }

    private static String getUserId(Context context) {
        return context.getString(R.string.user_id);
    }

}
