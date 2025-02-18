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
    private Player previous;

    public Player(double x, double y, double speed, double size) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.size = size;
        followers = new ArrayList<>();
        previous = this;
    }

    public void addFollower(double size) {
        Follower f = new Follower(this.x, this.y, this.speed, size, this);
        followers.add(f);
        previous = f;

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
