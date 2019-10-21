package dev.georgebarker.lockclient.config;

public interface PropertyConfig {

    String getBrokerUrl();

    String getUserId();

    String getLockClientTopicName();

    int getOpenMotorWaitTimeMillis();

}
