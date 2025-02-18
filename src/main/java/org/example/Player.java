package org.example;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.ArcType;

public class Player {
    public double x;
    public double y;
    public double dirX;
    public double dirY;
    double speed;
    public double size;
    public Follower follower = null;

    double[][] edgePoints = new double[2][2];

    public Player(double x, double y, double speed, double size, int followerCount) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.size = size;
        if (followerCount > 0) {
            follower = new Follower(this.x, this.y, speed, size-(size/20), followerCount-1);
        }
    }

    public void move(double dt) {
        double distanceX = this.dirX-x;
        double distanceY = this.dirY-y;

        double distance =  Math.sqrt(distanceX*distanceX + distanceY*distanceY);

        if (distance <= size/2) {return;}
        double currentSpeed = this.speed;


        double normX = (distanceX / distance);
        double normY = (distanceY / distance);

        currentSpeed *= (currentSpeed * distance / 100);

        this.x +=  normX * currentSpeed * dt;
        this.y +=  normY * currentSpeed * dt;
        calculateEdgePoints();

        if (this.follower != null) {
            this.follower.dirX = this.x;
            this.follower.dirY = this.y;
            this.follower.move(dt);
        }
    }

    protected void calculateEdgePoints() {
        double distanceX = this.dirX-x;
        double distanceY = this.dirY-y;

        double distance =  Math.sqrt(distanceX*distanceX + distanceY*distanceY);

        double normX = (distanceX / distance);
        double normY = (distanceY / distance);

        double halfSize = this.size/2;

        this.edgePoints[0][0] = this.x + -normY*halfSize;
        this.edgePoints[0][1] = this.y + normX*halfSize;

        this.edgePoints[1][0] = this.x + normY*halfSize;
        this.edgePoints[1][1] = this.y + -normX*halfSize;
    }

    public void draw(GraphicsContext gc){
        drawArc(gc, this.edgePoints[1], this.edgePoints[0], (this.size/3)*4);
        if (this.follower != null) {
            this.follower.draw(gc, this.edgePoints);
        }
    }

    protected void drawArc(GraphicsContext gc, double[] p1, double[] p2, double height) {
        double centerX = (p1[0] + p2[0]) / 2;
        double centerY = (p1[1] + p2[1]) / 2;

        double radius = Math.hypot(p2[0] - p1[0], p2[1] - p1[1]) / 2;

        double angleRadians = Math.atan2(p2[1] - p1[1], p2[0] - p1[0]);
        double angleDegrees = -Math.toDegrees(angleRadians);

        if (height == 0) {
            gc.strokeArc(centerX - radius, centerY - radius, radius * 2, radius * 2, angleDegrees, 180, ArcType.OPEN);
        } else {
            gc.strokeArc(centerX - radius, centerY - radius, radius * 2, height, angleDegrees, 180, ArcType.OPEN);
        }
    }

    protected void drawArc(GraphicsContext gc, double[] p1, double[] p2){
        drawArc(gc, p1, p2, 0);
    }
}
