package org.example;

public class Bodypart extends Moveable {

    public Bodypart(double x, double y, double size, double distance,Moveable following) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.distance = distance;
        this.following = following;
    }

    @Override
    public void move(double dt) {
        double distanceX = this.x - following.x;
        double distanceY = this.y - following.y;
        double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);
        double normX = distanceX / distance;
        double normY = distanceY / distance;

        this.x = following.x + this.distance * normX;
        this.y = following.y + this.distance * normY;

        if (this.follower == null) {
            return;
        }

        double angle = getAngle();

        System.out.println(Math.toDegrees(angle));
    }

    private double getAngle() {
        double v1x = this.follower.x - this.x;
        double v1y = this.follower.y - this.y;
        double v2x = this.following.x - this.x;
        double v2y = this.following.y - this.y;

        double dotProduct = (v1x * v2x) + (v1y * v2y);
        double magnitudeV1 = Math.sqrt(v1x * v1x + v1y * v1y);
        double magnitudeV2 = Math.sqrt(v2x * v2x + v2y * v2y);

        if (magnitudeV1 == 0 || magnitudeV2 == 0) {
            throw new IllegalArgumentException("One of the vectors has zero length!");
        }
        double cosTheta = dotProduct / (magnitudeV1 * magnitudeV2);

        cosTheta = Math.max(-1.0, Math.min(1.0, cosTheta));

        return Math.acos(cosTheta);
    }
}
