package org.example;

public abstract class Moveable {
    public double x;
    public double y;
    public double size;
    public Bodypart follower;
    public Moveable following;
    public double distance;
    public double[] sidePoints;

    public abstract void move(double dt);

    public Moveable addBodypart(double size, double distance) {
        this.follower = new Bodypart(500, 500, size, distance, this);
        return this.follower;
    }

    public Bodypart next(int amount) {
        Moveable moveable = this;
        for (int i = 0; i < amount; i++) {
            moveable = moveable.follower;
        }
        return (Bodypart) moveable;
    }

    public Bodypart getLastBodypart() {
        Moveable bodypart = this;
        while (bodypart.follower != null) {
            bodypart = bodypart.follower;
        }
        return (Bodypart) bodypart;
    }

    public double[] getScaledSidePoints(double scale) {
        if (this.follower != null) {
            double distanceX = this.x - this.follower.x;
            double distanceY = this.y - this.follower.y;
            double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);

            double normX = (distanceX / distance);
            double normY = (distanceY / distance);
            return new double[]{this.x + -normY * (this.size / scale), this.y + normX * (this.size / scale), this.x + normY * (this.size / scale), this.y + -normX * (this.size / scale)};
        } else {
            double distanceX = this.x - this.following.x;
            double distanceY = this.y - this.following.y;

            double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);

            double normX = (distanceX / distance);
            double normY = (distanceY / distance);

            return new double[]{this.x + normY * (this.size / scale), this.y + -normX * (this.size / scale), this.x + -normY * (this.size / scale), this.y + normX * (this.size / scale)};
        }


    }

    public abstract void calculateSidePoints();
}
