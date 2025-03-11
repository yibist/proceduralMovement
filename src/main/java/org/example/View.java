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

        graphicsContext.clearRect(0, 0, Main.WIDTH, Main.HEIGHT);

        graphicsContext.setFill(Color.DEEPSKYBLUE);
        graphicsContext.beginPath();
        drawSmoothCurve(head);
        graphicsContext.fill();

        graphicsContext.setStroke(Color.LIGHTBLUE);
        graphicsContext.setLineWidth(5);
        graphicsContext.beginPath();
        drawSmoothCurve(head);
        graphicsContext.stroke();

        drawFins(head);

        //graphicsContext.fillOval(head.x- head.size/2, head.y- head.size/2, head.size, head.size);
        drawEyes();
    }

    private void drawEyes() {
        graphicsContext.setFill(Color.WHITE);
        double[] newPoints = this.head.getScaledSidePoints(3);
        graphicsContext.fillOval(newPoints[0]-5, newPoints[1]-5, 10, 10);
        graphicsContext.fillOval(newPoints[2]-5, newPoints[3]-5, 10, 10);
    }


    private void drawSmoothCurve(Moveable moveable) {
        // Move to the first point

        // Draw smooth curve using bezierCurveTo
        double xc, yc;
        double firstXc = (moveable.sidePoints[0] + moveable.follower.sidePoints[0]) / 2;
        double firstYc = (moveable.sidePoints[1] + moveable.follower.sidePoints[1]) / 2;
        graphicsContext.moveTo(firstXc, firstYc);
        Moveable tempM = moveable.follower;
        while (tempM.follower != null) {
            xc = (tempM.sidePoints[0] + tempM.follower.sidePoints[0]) / 2;
            yc = (tempM.sidePoints[1] + tempM.follower.sidePoints[1]) / 2;
            graphicsContext.quadraticCurveTo(tempM.sidePoints[0], tempM.sidePoints[1], xc, yc);
            tempM = tempM.follower;
        }

        xc = (tempM.sidePoints[0] + tempM.sidePoints[2]) / 2;
        yc = (tempM.sidePoints[1] + tempM.sidePoints[3]) / 2;
        tempM = moveable.getLastBodypart();
        graphicsContext.quadraticCurveTo(tempM.sidePoints[0], tempM.sidePoints[1], xc, yc);

        while (tempM != moveable) {
            xc = (tempM.sidePoints[2] + tempM.following.sidePoints[2]) / 2;
            yc = (tempM.sidePoints[3] + tempM.following.sidePoints[3]) / 2;
            graphicsContext.quadraticCurveTo(tempM.sidePoints[2], tempM.sidePoints[3], xc, yc);
            tempM = tempM.following;
        }


        graphicsContext.quadraticCurveTo((moveable.follower.x - moveable.x) * -3 + moveable.x, (moveable.follower.y - moveable.y) * -3 + moveable.y, firstXc, firstYc);
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
        graphicsContext.setFill(Color.BLACK);
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

