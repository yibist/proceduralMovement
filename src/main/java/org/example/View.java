package org.example;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class View {
    private final GraphicsContext graphicsContext;
    private final Head head;
    private long clock;

    View(GraphicsContext graphicsContext, Head head) {
        this.graphicsContext = graphicsContext;
        this.head = head;
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
        //clock = System.currentTimeMillis();

        graphicsContext.setFill(Color.DARKBLUE);

        graphicsContext.clearRect(0, 0, Main.WIDTH, Main.HEIGHT);

        graphicsContext.fillOval(head.x - head.size / 2, head.y - head.size / 2, head.size, head.size);


        Moveable moveable = head.follower;

        while (moveable !=null) {
            graphicsContext.fillOval(moveable.x - moveable.size / 2, moveable.y - moveable.size / 2, moveable.size, moveable.size);
            graphicsContext.setStroke(Color.PURPLE);


            //drawPolygon(moveable);

            graphicsContext.strokeLine(moveable.x, moveable.y, moveable.following.x, moveable.following.y);
            moveable = moveable.follower;
        }
        drawEyes();
    }

    private void drawPolygon(Moveable moveable) {
        double distanceX = moveable.x - moveable.following.x;
        double distanceY = moveable.y - moveable.following.y;

        double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);

        double normX = (distanceX / distance);
        double normY = (distanceY / distance);

        double px1 = moveable.x + -normY * (moveable.size / 2);
        double py1 = moveable.y + normX * (moveable.size / 2);

        double px2 = moveable.x + normY * (moveable.size / 2);
        double py2 = moveable.y + -normX * (moveable.size / 2);

        double px3 = moveable.following.x + -normY * (moveable.following.size / 2);
        double py3 = moveable.following.y + normX * (moveable.following.size / 2);

        double px4 = moveable.following.x + normY * (moveable.following.size / 2);
        double py4 = moveable.following.y + -normX * (moveable.following.size / 2);
        graphicsContext.fillPolygon(new double[]{px1, px3, px4, px2}, new double[]{py1, py3, py4, py2}, 4);
    }

    private void drawEyes() {
        graphicsContext.setFill(Color.WHITE);
        double distanceX = head.x - head.follower.x;
        double distanceY = head.y - head.follower.y;

        double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);

        double normX = (distanceX / distance);
        double normY = (distanceY / distance);

        double px1 = head.x + -normY * (head.size / 2.5);
        double py1 = head.y + normX * (head.size / 2.5);

        double px2 = head.x + normY * (head.size / 2.5);
        double py2 = head.y + -normX * (head.size / 2.5);


        graphicsContext.fillOval(px1-5,py1-5, 10, 10);
        graphicsContext.fillOval(px2-5,py2-5, 10, 10);
    }
}