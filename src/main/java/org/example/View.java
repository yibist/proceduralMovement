package org.example;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.List;


public class View {
    private final GraphicsContext graphicsContext;
    private final Player player;
    private long clock;

    View(GraphicsContext graphicsContext, Player player) {
        this.graphicsContext = graphicsContext;
        this.player = player;
    }

    /**
     * Start rendering the relevant images for the objects on screen.
     */
    public void startRendering() {
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                render();
            }
        };
        animationTimer.start();
    }

    /**
     * Draws the objects on the screen
     */
    private void render() {
        //System.out.println(1000/(System.currentTimeMillis()-clock));
        clock = System.currentTimeMillis();;
        graphicsContext.clearRect(0, 0, Main.WIDTH, Main.HEIGHT);

        graphicsContext.fillOval(player.x - player.size/2, player.y - player.size/2, player.size,player.size);
    }
}