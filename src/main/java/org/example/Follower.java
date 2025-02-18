package org.example;

public class Follower extends Player {

    public Follower(double x, double y, double speed, double size) {
        super(x, y, 100, size);
    }

    public void setDir(Player follower) {
        dirX = follower.x;
        dirY = follower.y;
    }


}
