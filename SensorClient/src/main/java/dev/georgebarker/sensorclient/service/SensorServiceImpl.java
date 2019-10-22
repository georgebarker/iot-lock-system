package dev.georgebarker.sensorclient.service;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phidget22.PhidgetException;
import com.phidget22.RFID;

import dev.georgebarker.sensorclient.config.PropertyConfig;
import dev.georgebarker.sensorclient.model.SensorEvent;
import dev.georgebarker.sensorclient.publisher.SensorEventPublisher;

@Service
public class SensorServiceImpl implements SensorService {

    private static final Logger LOG = LogManager.getLogger(SensorServiceImpl.class);

    @Autowired
    private SensorEventPublisher sensorEventPublisher;

    @Autowired
    private PropertyConfig propertyConfig;

    @Override
    @PostConstruct
    public void connectToRFIDSensor() throws PhidgetException {
	LOG.info("Attempting to connect to RFID Sensor...");
	RFID rfid = null;
	try {
	    rfid = new RFID();
	    rfid.open(getOpenRfidWaitTimeMillis());
	} catch (final PhidgetException e) {
	    LOG.error("Could not open connection to an RFID Sensor in {}ms, quitting application.",
		    getOpenRfidWaitTimeMillis());
	    throw e;
	}

	LOG.info("Connected to RFID Scanner.");
	addTagListener(rfid);

    }

    private void addTagListener(final RFID rfid) {
	rfid.addTagListener(event -> {
	    sensorEventPublisher.publish(new SensorEvent(event));
	});
	LOG.info("Attached addTagListener to RFID Scanner.");
    }

    private int getOpenRfidWaitTimeMillis() {
	return propertyConfig.getOpenRfidWaitTimeMillis();
    }

}
