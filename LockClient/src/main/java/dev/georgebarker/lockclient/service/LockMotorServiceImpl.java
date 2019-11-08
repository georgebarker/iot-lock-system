package dev.georgebarker.lockclient.service;

import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phidget22.PhidgetException;
import com.phidget22.RCServo;

import dev.georgebarker.lockclient.config.PropertyConfig;

@Service
public class LockMotorServiceImpl implements LockMotorService {

    private static final Logger LOG = LogManager.getLogger(LockMotorServiceImpl.class);
    private static final double LOCKED_POSITION_DEGREES = 90;
    private static final double UNLOCKED_POSITION_DEGREES = 0;
    private static final int UNLOCKING_WAIT_TIME_SECS = 3;

    @Autowired
    private PropertyConfig propertyConfig;

    private RCServo lockMotor;

    @PostConstruct
    @Override
    public void connectToLockMotor() throws PhidgetException {
	final int openMotorWaitTime = getOpenMotorWaitTimeMillis();
	LOG.info("Attempting to connect to lock motor, timeout set to {}ms", openMotorWaitTime);
	try {
	    lockMotor = new RCServo();
	    lockMotor.open(openMotorWaitTime);
	} catch (final PhidgetException e) {
	    LOG.error("Could not connect to lock motor in {}ms, terminating application.", openMotorWaitTime);
	    throw e;
	}

	LOG.info("Connected to lock motor with serial number {}. Setting initial position and engaging...",
		getLockSerialNumber());
	setMotorPosition(LOCKED_POSITION_DEGREES);
	lockMotor.setEngaged(true);
	LOG.info("Set initial lock motor position to locked ({} degrees) and motor is engaged.",
		LOCKED_POSITION_DEGREES);
    }

    @PreDestroy
    @Override
    public void disconnectFromLockMotor() throws PhidgetException {
	lockMotor.setEngaged(false);
	LOG.info("Disengaged from lock motor.");
    }

    @Override
    public int getLockSerialNumber() {
	try {
	    return lockMotor.getDeviceSerialNumber();
	} catch (final PhidgetException e) {
	    LOG.error("Couldn't retrieve lock motor device serial number, returning 0.", e);
	    return 0;
	}
    }

    @Override
    public void unlock() {
	try {
	    LOG.info("Setting target position to unlocked ({} degrees)...", UNLOCKED_POSITION_DEGREES);
	    setMotorPosition(UNLOCKED_POSITION_DEGREES);
	    LOG.info("Motor unlocked, waiting {} seconds before re-locking...", UNLOCKING_WAIT_TIME_SECS);
	    TimeUnit.SECONDS.sleep(UNLOCKING_WAIT_TIME_SECS);
	    LOG.info("Setting target position to locked ({} degrees)...", LOCKED_POSITION_DEGREES);
	    setMotorPosition(LOCKED_POSITION_DEGREES);
	} catch (final PhidgetException e) {
	    LOG.error("Failed to perform unlock", e);
	} catch (final InterruptedException e) {
	    LOG.error("The sleep action when waiting to re-lock the motor was interrupted", e);
	} finally {
	}
    }

    private int getOpenMotorWaitTimeMillis() {
	return propertyConfig.getOpenMotorWaitTimeMillis();
    }

    private void setMotorPosition(final double position) throws PhidgetException {
	try {
	    lockMotor.setTargetPosition(position);
	} catch (final PhidgetException e) {
	    LOG.error("Failed to set motor position to {}", position);
	    throw e;
	}
    }
}
