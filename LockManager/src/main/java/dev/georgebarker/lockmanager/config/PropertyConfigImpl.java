package dev.georgebarker.lockmanager.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PropertyConfigImpl implements PropertyConfig {

    @Value("${lockmanager.broker-url}")
    private String brokerUrl;

    @Value("${lockmanager.user-id}")
    private String userId;

    @Value("${lockmanager.topic-name}")
    private String topicName;

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
