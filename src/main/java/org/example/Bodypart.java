package org.example;

public class Bodypart extends Moveable {
    private static final double MIN_RAD = Math.toRadians(160);

    public Bodypart(double x, double y, double size, double distance, Moveable following) {
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

        double rad = getRad();

        if (rad < MIN_RAD) {
            enforceMinAngle();
        }
    }

    private double getRad() {
        double v1x = this.follower.x - this.x;
        double v1y = this.follower.y - this.y;
        double v2x = this.following.x - this.x;
        double v2y = this.following.y - this.y;

        double dotProduct = (v1x * v2x) + (v1y * v2y);
        double magnitudeV1 = Math.sqrt(v1x * v1x + v1y * v1y);
        double magnitudeV2 = Math.sqrt(v2x * v2x + v2y * v2y);

        double cosTheta = dotProduct / (magnitudeV1 * magnitudeV2);

        cosTheta = Math.max(-1.0, Math.min(1.0, cosTheta));


        return Math.acos(cosTheta);
    }


    private void enforceMinAngle() {
        // Vectors from 'this' to its neighbors
        double v1x = this.follower.x - this.x;
        double v1y = this.follower.y - this.y;
        double v2x = this.following.x - this.x;
        double v2y = this.following.y - this.y;

        // Normalize the vectors
        double len1 = Math.sqrt(v1x * v1x + v1y * v1y);
        double len2 = Math.sqrt(v2x * v2x + v2y * v2y);
        v1x /= len1;
        v1y /= len1;
        v2x /= len2;
        v2y /= len2;

        // Calculate the current angle between the vectors
        double dotProduct = v1x * v2x + v1y * v2y;
        double currentAngle = Math.acos(dotProduct);

        // If the current angle is already greater than or equal to the minimum angle, do nothing
        if (currentAngle >= MIN_RAD) {
            return;
        }

        // Calculate the cross product to determine the orientation (clockwise or counterclockwise)
        double crossProduct = v1x * v2y - v1y * v2x;

        // Determine the direction of rotation (clockwise or counterclockwise)
        double rotationDirection = crossProduct > 0 ? -1 : 1;

        // Calculate the required rotation to enforce the minimum angle
        double angleDifference = MIN_RAD - currentAngle;
        double rotationAngle = rotationDirection * angleDifference / 2;

        // Rotate the vector from 'this' to 'following' by the required angle
        double cosRot = Math.cos(rotationAngle);
        double sinRot = Math.sin(rotationAngle);

        double newV1x = v1x * cosRot - v1y * sinRot;
        double newV1y = v1x * sinRot + v1y * cosRot;

        // Calculate the new position for 'follower' based on the rotated vector
        double newFollowerX = this.x + newV1x * len1;
        double newFollowerY = this.y + newV1y * len1;

        // Update the position of 'follower'
        this.follower.x = newFollowerX;
        this.follower.y = newFollowerY;

    }
}
