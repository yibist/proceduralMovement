package org.example;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

class Controller {
    private final Player player;
    private final ScheduledExecutorService executor;
    private volatile boolean running = true;

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
            double deltaTime = 0.016; // Approx. 60 FPS
            player.updateDir(Main.MouseX, Main.MouseY);
            player.move(deltaTime);
        }
    }
}
