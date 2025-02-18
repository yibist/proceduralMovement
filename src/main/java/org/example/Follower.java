package org.example;

public class Follower extends Player {
    Player following;

    public Follower(double x, double y, double speed, double size, Player following) {
        super(x, y, 100, size);
        this.following = following;
    }

    public void setDir(Player follower) {
        dirX = follower.x;
        dirY = follower.y;
    }

    @Override
    public void move(double dt) {
        double distanceX = dirX - x;
        double distanceY = dirY - y;

        double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);
        double normX = distanceX / distance;
        double normY = distanceY / distance;


        x = dirX - (this.size/2 + following.size/2) * normX;

        y = dirY - (this.size/2 + following.size/2) * normY;
    }
}
