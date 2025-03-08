package org.example;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;


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

        graphicsContext.setFill(Color.DEEPSKYBLUE);

        graphicsContext.clearRect(0, 0, Main.WIDTH, Main.HEIGHT);
        graphicsContext.fillOval(head.x - head.size / 2, head.y - head.size / 2, head.size, head.size);

        Moveable moveable = head.follower;
        while (moveable != null) {
            //graphicsContext.fillOval(moveable.x - moveable.size / 2, moveable.y - moveable.size / 2, moveable.size, moveable.size);
            if (moveable.follower == null) {
                break;
            }
            //drawPolygon(moveable);
            //graphicsContext.strokeLine(moveable.x, moveable.y, moveable.following.x, moveable.following.y);
            //drawEdge(moveable);

            moveable = moveable.follower;
        }

        graphicsContext.setFill(Color.DEEPSKYBLUE);
        graphicsContext.beginPath();
        drawSmoothCurve(head);
        //graphicsContext.fill();

        graphicsContext.setStroke(Color.LIGHTBLUE);
        graphicsContext.setLineWidth(5);
        graphicsContext.beginPath();
        drawSmoothCurve(head);
        graphicsContext.stroke();

        drawEyes();

        drawFins(head);
    }

    private void drawEyes() {
        graphicsContext.setFill(Color.WHITE);
        double[] newPoints = this.head.getScaledSidePoints(0.75);
        graphicsContext.fillOval(newPoints[0] - 5, newPoints[1] - 5, 10, 10);
        graphicsContext.fillOval(newPoints[2] - 5, newPoints[3] - 5, 10, 10);
    }


    private void drawSmoothCurve(Moveable moveable) {
        // Move to the first point
        graphicsContext.moveTo(moveable.sidePoints[0], moveable.sidePoints[1]);

        // Draw smooth curve using bezierCurveTo
        Moveable tempM = moveable.follower;
        while (tempM.follower != null) {
            double xc = (tempM.sidePoints[0] + tempM.follower.sidePoints[0]) / 2;
            double yc = (tempM.sidePoints[1] + tempM.follower.sidePoints[1]) / 2;
            graphicsContext.quadraticCurveTo(tempM.sidePoints[0], tempM.sidePoints[1], xc, yc);
            if (tempM.follower == null) break;
            tempM = tempM.follower;
        }
        while (tempM != moveable) {
            double xc = (tempM.sidePoints[2] + tempM.following.sidePoints[2]) / 2;
            double yc = (tempM.sidePoints[3] + tempM.following.sidePoints[3]) / 2;
            graphicsContext.quadraticCurveTo(tempM.sidePoints[2], tempM.sidePoints[3], xc, yc);
            tempM = tempM.following;
        }
        // Connect to the last point
        //graphicsContext.lineTo(xPoints.getLast(), yPoints.getLast());
    }


    private void drawFins(Head head) {
        drawDorsalFin(head.next(2), 4);
        graphicsContext.setFill(Color.BLACK);
        drawSideFins(head.next(4));

    }

    private void drawDorsalFin(Moveable moveable, int length) {
        graphicsContext.beginPath();
        graphicsContext.moveTo(moveable.follower.x, moveable.follower.y);
        graphicsContext.bezierCurveTo(moveable.follower.x, moveable.follower.y, moveable.next(2).x, moveable.next(2).y, moveable.next(3).x, moveable.next(3).y);
        graphicsContext.stroke();
    }

    private void drawSideFins(Bodypart bodypart) {
        double distanceX = head.x - head.follower.x;
        double distanceY = head.y - head.follower.y;

        double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);

        double normX = (distanceX / distance);
        double normY = (distanceY / distance);

        double xr = head.x + normY * (head.size / 2);
        double yr = head.y + -normX * (head.size / 2);

        double xl = head.x + -normY * (head.size / 2);
        double yl = head.y + normX * (head.size / 2);


        graphicsContext.beginPath();
        graphicsContext.moveTo(xr, yr);
        //graphicsContext.quadraticCurveTo(head.x + normY * (head.size),head.y + normX * (head.size),next follower,next follower);
        graphicsContext.fill();

    }
}