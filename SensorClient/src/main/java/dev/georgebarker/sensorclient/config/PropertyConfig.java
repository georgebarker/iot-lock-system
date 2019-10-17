package dev.georgebarker.sensorclient.config;

public interface PropertyConfig {
    int getOpenRfidWaitTimeMillis();

    String getBrokerUrl();

    String getUserId();

    String getTopicName();
}
