package dev.georgebarker.lockmanager.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.georgebarker.lockmanager.model.SensorEvent;
import dev.georgebarker.lockmanager.model.TagSensorCombination;
import dev.georgebarker.lockmanager.model.TagSensorCombinationId;
import dev.georgebarker.lockmanager.publisher.SensorEventPublisher;
import dev.georgebarker.lockmanager.repository.SensorEventRepository;
import dev.georgebarker.lockmanager.repository.TagSensorCombinationRepository;

@ExtendWith(MockitoExtension.class)
public class SensorEventServiceImplTest {

    private static final String VALID_MESSAGE_JSON = "{\"timestampMillis\":1571575612637,\"sensorSerialNumber\":388496,\"tagId\":\"valid-tag\"}";
    private static final String INVALID_MESSAGE_JSON = "{\"timestampMillis\":1571575612637,\"sensorSerialNumber\":999,\"tagId\":\"invalid-tag\"}";

    private MqttMessage message;
    private SensorEvent sensorEvent;

    @Mock
    TagSensorCombinationRepository tagSensorCombinationRepository;

    @Mock
    SensorEventRepository sensorEventRepository;

    @Mock
    SensorEventPublisher sensorEventPublisher;

    @InjectMocks
    SensorEventService sensorEventService = new SensorEventServiceImpl();

    @Test
    public void processMessageWithValidCombination() {
	givenIHaveAMessageWithAValidCombination();
	whenIProcessTheMessage();
	thenASuccessfulEventIsRecorded();
	thenTheEventIsPublished();
    }

    @Test
    public void processMessageWithInvalidCombination() {
	givenIHaveAMessageWithAnInvalidCombination();
	whenIProcessTheMessage();
	thenAnUnsuccessfulEventIsRecorded();
	thenTheEventIsNotPublished();
    }

    private void givenIHaveAMessageWithAnInvalidCombination() {
	message = new MqttMessage();
	message.setPayload(INVALID_MESSAGE_JSON.getBytes());
	final TagSensorCombination tagSensorCombination = createTagSensorCombination("invalid-tag", 999);
	Mockito.when(tagSensorCombinationRepository.findById(tagSensorCombination.getTagSensorCombinationId()))
		.thenReturn(Optional.empty());
    }

    private void givenIHaveAMessageWithAValidCombination() {
	message = new MqttMessage();
	message.setPayload(VALID_MESSAGE_JSON.getBytes());
	final TagSensorCombination tagSensorCombination = createTagSensorCombination("valid-tag", 388496);
	Mockito.when(tagSensorCombinationRepository.findById(tagSensorCombination.getTagSensorCombinationId()))
		.thenReturn(Optional.of(tagSensorCombination));
    }

    private void whenIProcessTheMessage() {
	sensorEventService.processSensorEventMessage(message);
    }

    private void thenASuccessfulEventIsRecorded() {
	final ArgumentCaptor<SensorEvent> argument = ArgumentCaptor.forClass(SensorEvent.class);

	Mockito.verify(sensorEventRepository).save(argument.capture());
	assertEquals(true, argument.getValue().isSuccessful());

    }

    private void thenAnUnsuccessfulEventIsRecorded() {
	final ArgumentCaptor<SensorEvent> argument = ArgumentCaptor.forClass(SensorEvent.class);
	Mockito.verify(sensorEventRepository).save(argument.capture());
	sensorEvent = argument.getValue();
	assertEquals(false, sensorEvent.isSuccessful());

    }

    private void thenTheEventIsPublished() {
	Mockito.verify(sensorEventPublisher).publish(sensorEvent);
    }

    private void thenTheEventIsNotPublished() {
	Mockito.verify(sensorEventPublisher, Mockito.never()).publish(Mockito.any());
    }

    private TagSensorCombination createTagSensorCombination(final String tagId, final int sensorSerialNumber) {
	final TagSensorCombinationId id = new TagSensorCombinationId(tagId, sensorSerialNumber);
	final TagSensorCombination tagSensorCombination = new TagSensorCombination();
	tagSensorCombination.setTagSensorCombinationId(id);
	tagSensorCombination.setDisabled(false);

	return tagSensorCombination;
    }

}
