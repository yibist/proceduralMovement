package org.example;


import java.util.ArrayList;

public class Player {
    public double x;
    public double y;
    public double dirX;
    public double dirY;
    double speed;
    public double size;
    public ArrayList<Follower> followers;

    public Player(double x, double y, double speed, double size) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.size = size;
        followers = new ArrayList<>();
    }

    public void addFollower(double size ) {
        followers.add(new Follower(this.x, this.y, this.speed, size));
    };

    public void move(double dt) {
        double distanceX = dirX-x;
        double distanceY = dirY-y;

        double distance =  Math.sqrt(distanceX*distanceX + distanceY*distanceY);

        if (distance < 50) {return;}
        double currentSpeed = speed;


        double normX = distanceX / distance;
        double normY = distanceY / distance;

       x +=  normX * (currentSpeed * distance / 100) * dt;
       y +=  normY * (currentSpeed * distance / 100) * dt;

    }
}
