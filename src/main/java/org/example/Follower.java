package org.example;

import javafx.scene.canvas.GraphicsContext;

public class Follower extends Player {

    public Follower(double x, double y, double speed, double size, int followerCount) {
        super(x, y, speed, size, followerCount);
    }

    @Override
    public void move(double dt){

        double distanceX = this.dirX-this.x;
        double distanceY = this.dirY-this.y;

        double distance =  Math.sqrt(distanceX*distanceX + distanceY*distanceY);

        double normX = (distanceX / distance)/Math.sqrt(2);
        double normY = (distanceY / distance)/Math.sqrt(2);

        this.x = dirX - (this.size/3)*4 * normX;
        this.y = dirY - (this.size/3)*4 * normY;

        if (this.follower != null) {
            this.follower.dirX = x;
            this.follower.dirY = y;
            this.follower.move(dt);
        }
    }

    public void draw(GraphicsContext gc, double[][] prevPoints){
        calculateEdgePoints();
        gc.strokeLine(this.edgePoints[0][0], this.edgePoints[0][1], prevPoints[0][0], prevPoints[0][1]);
        gc.strokeLine(this.edgePoints[1][0], this.edgePoints[1][1], prevPoints[1][0], prevPoints[1][1]);
        if (this.follower != null) {
            this.follower.draw(gc, this.edgePoints);
        } else {
            gc.fillOval(this.x-this.size/2, this.y-this.size/2, this.size, this.size);
        }
    }

}
