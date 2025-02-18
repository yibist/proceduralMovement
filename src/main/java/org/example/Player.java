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

    public Player(int x, int y, double speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
    }
    public void move(double dt) {
        double distanceX = dirX-x;
        double distanceY = dirY-y;

        double distance =  Math.sqrt(distanceX*distanceX + distanceY*distanceY);

        if (distance < 50) {return;}
        double currentSpeed = speed;


        System.out.println(distance + " " + currentSpeed);

        double normX = distanceX / distance;
        double normY = distanceY / distance;

       x +=  normX * (currentSpeed * distance / 100);
       y +=  normY * (currentSpeed * distance / 100);

    }
}
