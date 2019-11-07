package dev.georgebarker.androidsensorclient.publisher;

import dev.georgebarker.androidsensorclient.model.UnlockEvent;

public interface UnlockEventPublisher {
    void publish(UnlockEvent unlockEvent);
}
