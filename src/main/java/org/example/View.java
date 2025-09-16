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

        drawSideFins(head.next(2), 5.5, 45);
        drawSideFins(head.next(7), 4, 45);

        Moveable moveable = head.follower;
        while (moveable != null) {
            //graphicsContext.fillOval(moveable.x - moveable.size / 2, moveable.y - moveable.size / 2, moveable.size, moveable.size);
            moveable = moveable.follower;
        }


        drawSmoothCurve(head);
        drawDorsalFin(head.next(2), 4);
        //graphicsContext.fillOval(head.x- head.size/2, head.y- head.size/2, head.size, head.size);
        drawEyes();





    }

    private void drawEyes() {
        graphicsContext.setFill(Color.WHITE);
        double[] newPoints = this.head.getScaledSidePoints(3);
        graphicsContext.fillOval(newPoints[0] - 5, newPoints[1] - 5, 10, 10);
        graphicsContext.fillOval(newPoints[2] - 5, newPoints[3] - 5, 10, 10);
    }


    private void drawSmoothCurve(Moveable moveable) {
        graphicsContext.setFill(Color.DEEPSKYBLUE);
        graphicsContext.setStroke(Color.LIGHTBLUE);
        graphicsContext.setLineWidth(5);
        graphicsContext.beginPath();

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

        graphicsContext.fill();
        graphicsContext.stroke();
    }

    private void drawDorsalFin(Moveable moveable, int length) {
        graphicsContext.beginPath();
        graphicsContext.moveTo(moveable.follower.x, moveable.follower.y);
        graphicsContext.bezierCurveTo(moveable.follower.x, moveable.follower.y, moveable.next(2).x, moveable.next(2).y, moveable.next(3).x, moveable.next(3).y);
        graphicsContext.stroke();
    }

    private void drawSideFins(Bodypart bodypart, double finLength, double rotationAngle) {
        graphicsContext.setFill(Color.DEEPSKYBLUE);
        graphicsContext.setStroke(Color.LIGHTBLUE);
        graphicsContext.setLineWidth(5);
        rotationAngle = Math.toRadians(rotationAngle);
        double x = bodypart.follower.x - bodypart.x;
        double y = bodypart.follower.y - bodypart.y;

        double len1 = Math.sqrt(x * x + y * y);
        x /= len1;
        y /= len1;

        double cosRot = Math.cos(rotationAngle);
        double sinRot = Math.sin(rotationAngle);

        double nx1 = x * cosRot - y * sinRot;
        double ny1 = x * sinRot + y * cosRot;

        double nx3 = x * -cosRot - y * -sinRot;
        double ny3 = x * -sinRot + y * -cosRot;

        cosRot = Math.cos(Math.PI - rotationAngle);
        sinRot = Math.sin(Math.PI - rotationAngle);

        double nx2 = x * cosRot - y * sinRot;
        double ny2 = x * sinRot + y * cosRot;

        double nx4 = x * -cosRot - y * -sinRot;
        double ny4 = x * -sinRot + y * -cosRot;


        double p1x = bodypart.x + nx1 * len1;
        double p1y = bodypart.y + ny1 * len1;
        double p1xExtended = bodypart.x + nx1 * len1 * finLength;
        double p1yExtended = bodypart.y + ny1 * len1 * finLength;
        double p2x = bodypart.x + nx2 * len1;
        double p2y = bodypart.y + ny2 * len1;


        double p3x = bodypart.x + nx3 * len1;
        double p3y = bodypart.y + ny3 * len1;
        double p3xExtended = bodypart.x + nx4 * len1 * finLength;
        double p3yExtended = bodypart.y + ny4 * len1 * finLength;
        double p4x = bodypart.x + nx4 * len1;
        double p4y = bodypart.y + ny4 * len1;


        graphicsContext.beginPath();
        graphicsContext.moveTo(p1x, p1y);
        graphicsContext.quadraticCurveTo(p1xExtended, p1yExtended, p2x, p2y);
        graphicsContext.fill();
        graphicsContext.stroke();


        graphicsContext.beginPath();
        graphicsContext.moveTo(p3x, p3y);
        graphicsContext.quadraticCurveTo(p3xExtended, p3yExtended, p4x, p4y);
        graphicsContext.fill();
        graphicsContext.stroke();



        /*
        Paint temp = graphicsContext.getFill();
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillOval(bodypart.x - bodypart.size / 2, bodypart.y - bodypart.size / 2, bodypart.size, bodypart.size);
        graphicsContext.setFill(Color.WHITESMOKE);
        graphicsContext.fillOval(p1x - 5, p1y - 5, 10, 10);
        graphicsContext.fillOval(p1xExtended - 5, p1yExtended - 5, 10, 10);
        graphicsContext.fillOval(p2x - 5, p2y - 5, 10, 10);
        graphicsContext.fillOval(p3x - 5, p3y - 5, 10, 10);
        graphicsContext.fillOval(p3xExtended - 5, p3yExtended - 5, 10, 10);
        graphicsContext.fillOval(p4x - 5, p4y - 5, 10, 10);
        graphicsContext.setFill(temp);
        */
        
    }
}

