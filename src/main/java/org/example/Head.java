package org.example;

import java.util.ArrayList;

public class Head extends Moveable {
    double speed;

    public Head(double x, double y, double speed, double size, double distance) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.size = size;
        this.distance = distance;
        this.following = null;
    }

    @Override
    public void move(double dt) {
        double distanceX = this.x - Main.mouseX;
        double distanceY = this.y - Main.mouseY;
        double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);
        double normX = distanceX / distance;
        double normY = distanceY / distance;

        if (Math.abs(distance) < this.distance / 2) {
            return;
        }
        this.x -= normX * speed * dt;
        this.y -= normY * speed * dt;


    }
}
