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

    public void updateDir(double dx, double dy, double maxAngle) {
        //arccos((U * V) / (|U| * |V|))
        double targetVectorX = dx - x;
        double targetVectorY = dy - y;

        double currentVectorX = dirX - x;
        double currentVectorY = dirY - y;

        double targetVectorLength = Math.sqrt(targetVectorX*targetVectorX + targetVectorY*targetVectorY);
        double currentVectorLength = Math.sqrt(currentVectorX*currentVectorX + currentVectorY*currentVectorY);

        double dotProduct = currentVectorX * targetVectorX + currentVectorY * targetVectorY;

        double angleRadians = Math.acos(dotProduct/(targetVectorLength*currentVectorLength));

        double angleDegrees = Math.toDegrees(angleRadians);

        if (angleDegrees > maxAngle) {
            System.out.println("Target vector x: " + targetVectorX + " y: " + targetVectorY);
            System.out.println("Current vector x: " + currentVectorX + " y: " + currentVectorY);
            System.out.println("Target Vector length: " + targetVectorLength);
            System.out.println("Current Vector length: " + currentVectorLength);
            System.out.println("Dot Product " + dotProduct);
            System.out.println("Radians " + angleRadians);
            System.out.println("Degrees " + angleDegrees);

            double crossProduct = currentVectorX * targetVectorY - currentVectorY * targetVectorX;
            boolean turnRight = crossProduct > 0;
            double maxTurnRadians = Math.toRadians(maxAngle);
            double cosTheta = Math.cos(maxTurnRadians);
            double sinTheta = Math.sin(maxTurnRadians);

            double newDirX;
            double newDirY;
            if (turnRight) {
                newDirX = currentVectorX * cosTheta - currentVectorY * sinTheta;
                newDirY = currentVectorX * sinTheta + currentVectorY * cosTheta;
            } else {
                // Rotate left by maxTurnAngle
                newDirX = currentVectorX * cosTheta + currentVectorY * sinTheta;
                newDirY = -currentVectorX * sinTheta + currentVectorY * cosTheta;
            }
            dirX = newDirX;
            dirY = newDirY;
        } else {
            // If within the allowed angle, directly set the direction to the target
            dirX = targetVectorX;
            dirY = targetVectorY;
        }

        dirX = dx;
        dirY = dy;
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
            follower.updateDir(this.x, this.y, 40);
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
        gc.fillOval(this.x-5, this.y-5, 10, 10);
        gc.strokeLine(this.x, this.y, this.dirX, this.dirY);
        drawArc(gc, this.edgePoints[1], this.edgePoints[0]);
        if (this.follower != null) {
            this.follower.draw(gc, this.edgePoints);
        } else {
            drawArc(gc, this.edgePoints[0], this.edgePoints[1]);
        }
    }

    protected void drawArc(GraphicsContext gc, double[] p1, double[] p2) {
        double centerX = (p1[0] + p2[0]) / 2;
        double centerY = (p1[1] + p2[1]) / 2;

        double radius = Math.hypot(p2[0] - p1[0], p2[1] - p1[1]) / 2;

        double angleRadians = Math.atan2(p2[1] - p1[1], p2[0] - p1[0]);
        double angleDegrees = -Math.toDegrees(angleRadians);

        gc.strokeArc(centerX - radius, centerY - radius, radius * 2, radius * 2, angleDegrees, 180, ArcType.OPEN);
    }
}
