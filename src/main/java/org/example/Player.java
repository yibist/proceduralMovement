package org.example;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

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

        this.edgePoints[0][0] = this.x + -normY*(this.size/2);
        this.edgePoints[0][1] = this.y + normX*(this.size/2);

        this.edgePoints[1][0] = this.x + normY*(this.size/2);
        this.edgePoints[1][1] = this.y + -normX*(this.size/2);
    }

    public void draw(GraphicsContext gc){
        gc.fillOval(this.x-this.size/2, this.y-this.size/2, this.size, this.size);
        if (this.follower != null) {
            this.follower.draw(gc, this.edgePoints);
        }
    }
}
