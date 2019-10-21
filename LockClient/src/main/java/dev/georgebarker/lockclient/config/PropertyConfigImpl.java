package dev.georgebarker.lockclient.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PropertyConfigImpl implements PropertyConfig {

    @Value("${lockclient.broker-url}")
    private String brokerUrl;

    @Value("${lockclient.user-id}")
    private String userId;

    @Value("${lockclient.lock-client-topic-name}")
    private String lockClientTopicName;

    @Value("${lockclient.open-motor-wait-time-millis}")
    private int openMotorWaitTimeMillis;

    @Override
    public String getBrokerUrl() {
	return brokerUrl;
    }

    @Override
    public String getUserId() {
	return userId;
    }

    @Override
    public String getLockClientTopicName() {
	return lockClientTopicName;
    }

    @Override
    public int getOpenMotorWaitTimeMillis() {
	return openMotorWaitTimeMillis;
    }
}
