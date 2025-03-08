package org.example;

import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class Controller {
    private static final long tickSpeed = 1;
    private final List<KeyCode> pressedKeys = Collections.synchronizedList(new ArrayList<>());
    private final Head head;
    private final ScheduledExecutorService executor;
    private volatile boolean running = true;
    protected final AtomicInteger gameTicks = new AtomicInteger();

    Controller(Head head) {
        this.head = head;
        this.executor = Executors.newSingleThreadScheduledExecutor();
    }

    public void startGameLogic() {
        executor.scheduleAtFixedRate(this::gameStep, 0, tickSpeed, TimeUnit.MILLISECONDS);
    }

    public void stopGameLogic() {
        running = false;
        executor.shutdown();
    }

    private void gameStep() {
        if (running) {
            gameTicks.getAndIncrement();
            double deltaTime = (double) tickSpeed / 1000;

            head.move(deltaTime);
            head.calculateSidePoints();

            Moveable moveable = head.follower;
            while (moveable != null) {
                moveable.move(deltaTime);
                moveable.calculateSidePoints();
                moveable = moveable.follower;
            }
        }
    }
}
