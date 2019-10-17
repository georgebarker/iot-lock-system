package dev.georgebarker.sensorclient.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PropertyConfigImpl implements PropertyConfig {

    @Value("${sensorclient.open-rfid-wait-time-millis}")
    private int openRfidWaitTimeMillis;

    @Value("${sensorclient.broker-url}")
    private String brokerUrl;

    @Value("${sensorclient.user-id}")
    private String userId;

    @Value("${sensorclient.topic-name}")
    private String topicName;

    @Override
    public int getOpenRfidWaitTimeMillis() {
	return openRfidWaitTimeMillis;
    }

    @Override
    public String getBrokerUrl() {
	return brokerUrl;
    }

    @Override
    public String getUserId() {
	return userId;
    }

    @Override
    public String getTopicName() {
	return topicName;
    }

}
