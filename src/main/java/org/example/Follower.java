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

        this.x = dirX - (this.size/2) * normX;
        this.y = dirY - (this.size/2) * normY;
        calculateEdgePoints();

        if (this.follower != null) {
            this.follower.updateDir(this.x, this.y, 150);
            this.follower.move(dt);
        }
    }

    public void draw(GraphicsContext gc, double[][] prevPoints){
        gc.strokeLine(this.edgePoints[0][0], this.edgePoints[0][1], prevPoints[0][0], prevPoints[0][1]);
        gc.strokeLine(this.edgePoints[1][0], this.edgePoints[1][1], prevPoints[1][0], prevPoints[1][1]);
        gc.strokeLine(this.x, this.y, this.dirX, this.dirY);

        gc.fillOval(this.x-5, this.y-5, 10, 10);

        /*
        drawCurve(gc, this.edgePoints[0], prevPoints[0], true);
        drawCurve(gc, this.edgePoints[1], prevPoints[1], false);
        */

        if (this.follower != null) {
            this.follower.draw(gc, this.edgePoints);
        } else {
            drawArc(gc, this.edgePoints[0], this.edgePoints[1] );
        }
    }

    /*
    protected void drawCurve(GraphicsContext gc, double[] p1, double[] p2, boolean inverted){
        double centerX = (p1[0] + p2[0]) / 2;
        double centerY = (p1[1] + p2[1]) / 2;

        double distance = Math.hypot(p2[0] - p1[0], p2[1] - p1[1]);

        double radius = distance / 2;

        double angleRadians = Math.atan2(p2[1] - p1[1], p2[0] - p1[0]);

        double angleDegrees = -Math.toDegrees(angleRadians);

        if(inverted){
            gc.strokeArc(
                    centerX - radius,         // Top-left X of the bounding box
                    centerY - radius,         // Top-left Y of the bounding box
                    radius * 2,               // Width of the bounding box (diameter)
                    radius * 2,               // Height of the bounding box (diameter)
                    angleDegrees-180,        // Start angle (ensures arc orientation follows direction from p1 to p2)
                    180,                      // Sweep angle (half-circle)
                    javafx.scene.shape.ArcType.OPEN
            );
        } else {
            gc.strokeArc(
                    centerX - radius,         // Top-left X of the bounding box
                    centerY - radius,         // Top-left Y of the bounding box
                    radius * 2,               // Width of the bounding box (diameter)
                    radius * 2,               // Height of the bounding box (diameter)
                    (angleDegrees-4)/2,        // Start angle (ensures arc orientation follows direction from p1 to p2)
                    4,                      // Sweep angle (half-circle)
                    javafx.scene.shape.ArcType.OPEN
            );
        }
    }*/

}
