package dev.georgebarker.lockclient.service;

import com.phidget22.PhidgetException;

public interface LockMotorService {

    void connectToLockMotor() throws PhidgetException;

    void disconnectFromLockMotor() throws PhidgetException;

    int getLockSerialNumber();

    void unlock();

}
