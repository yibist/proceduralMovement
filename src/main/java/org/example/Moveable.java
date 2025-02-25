package org.example;

public abstract class Moveable {
    public double x;
    public double y;
    public double size;
    public Moveable follower;
    public Moveable following;
    public double distance;

    public void move(double dt) {

    }

    public Moveable addBodypart(double size, double distance) {
        this.follower = new Bodypart(500,500,size, distance, this);
        return this.follower;
    }
}
