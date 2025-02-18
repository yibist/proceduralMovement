package org.example;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class Controller {
    private final List<KeyCode> pressedKeys = Collections.synchronizedList(new ArrayList<>());
    private final Player player;
    private final ScheduledExecutorService executor;
    private volatile boolean running = true;
    protected final AtomicInteger gameTicks = new AtomicInteger();

    Controller(Player player) {
        this.player = player;
        this.executor = Executors.newSingleThreadScheduledExecutor();
    }

    public void startGameLogic() {
        // Run the game logic at a fixed rate
        executor.scheduleAtFixedRate(this::gameStep, 0, 16, TimeUnit.MILLISECONDS); // 16ms ≈ 60 updates per second
    }

    public void stopGameLogic() {
        running = false;
        executor.shutdown();
    }

    private void gameStep() {
        if (running) {
            // Update the model (logic)
            gameTicks.getAndIncrement();


            double deltaTime = 0.016; // Approx. 60 FPS
            player.move(deltaTime);
            Player previous = player;

            for (Follower follower: player.followers) {
                follower.setDir(previous);
                follower.move(deltaTime);
                previous = follower;
            }
        }
    }
}
