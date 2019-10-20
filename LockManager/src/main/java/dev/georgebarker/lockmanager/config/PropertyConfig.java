package dev.georgebarker.lockmanager.config;

public interface PropertyConfig {

    String getBrokerUrl();

    String getUserId();

    String getSensorClientTopicName();

    String getLockClientTopicName();

}
