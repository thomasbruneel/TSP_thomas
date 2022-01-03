package com.example.tsp_thomas.entities;

public class City {

    private int x;
    private int y;

    public City(int x,int y) {
        this.x=x;
        this.y=y;
    }

    public City(City other){
        this.x=other.x;
        this.y=other.y;
    }

    public double distanceToCity(City city) {
        int x = Math.abs(getX() - city.getX());
        int y = Math.abs(getY() - city.getY());
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}