package org.example;

public abstract class Moveable {
    public double x;
    public double y;
    public double size;
    public Moveable follower;
    public Moveable following;
    public double distance;
    public double[] sidePoints;

    public abstract void move(double dt);

    public Moveable addBodypart(double size, double distance) {
        this.follower = new Bodypart(500,500,size, distance, this);
        return this.follower;
    }

    public Bodypart next(int amount) {
        Moveable moveable = this;
        for (int i = 0; i < amount; i++) {
            moveable = moveable.follower;
        }
        return (Bodypart) moveable;
    }

    public double[] getScaledSidePoints(double scale) {
        return sidePoints;
    }

    public abstract void calculateSidePoints();
}
