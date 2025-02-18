package org.example;

public class Follower extends Player {

    public Follower(double x, double y, double speed, double size, int followerCount) {
        super(x, y, speed, size, followerCount);
    }

    @Override
    public void move(double dt){

        double distanceX = this.dirX-this.x;
        double distanceY = this.dirY-this.y;

        double distance =  Math.sqrt(distanceX*distanceX + distanceY*distanceY);

        double normX = distanceX / distance;
        double normY = distanceY / distance;

        if (distance < size) {return;}

        this.x +=  normX * this.speed * dt;
        this.y +=  normY * this.speed * dt;

        if (this.follower != null) {
            this.follower.dirX = x;
            this.follower.dirY = y;
            this.follower.speed = speed;
            this.follower.move(dt);
        }
    }
}
