package com.example.tsp_thomas;

import com.example.tsp_thomas.entities.City;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Solver {
    public Random rn = new Random();

    private ArrayList<City> currentSolution;
    private Controller controller;

    public Solver(ArrayList<City> route, Controller controller) {
        currentSolution=route;
        this.controller=controller;
    }

    public void start() throws InterruptedException {
        while (true){
            ArrayList<City>neighborSolution=copy(currentSolution);
            //swapCities(neighborSolution);
            twoOpt(neighborSolution);
            if(getDistance(neighborSolution)<getDistance(currentSolution)){
                currentSolution=neighborSolution;
                controller.updateSolution(currentSolution);
                Thread.sleep(Settings.delay);
            }
        }
    }

    private ArrayList<City> copy(ArrayList<City> currentSolution) {
        ArrayList<City>neighborSolution=new ArrayList<>();
        for(City c:currentSolution){
            neighborSolution.add(new City(c));
        }
        return neighborSolution;
    }

    public void swapCities(ArrayList<City>neighborSolution) {
        int a = (int) (Math.random() * neighborSolution.size());
        int b = (int) (Math.random() * neighborSolution.size());
        City x = neighborSolution.get(a);
        City y = neighborSolution.get(b);
        neighborSolution.set(a, y);
        neighborSolution.set(b, x);
    }

    public void twoOpt(ArrayList<City>neighborSolution) {
        int r1=rn.nextInt((neighborSolution.size()-1)-0+1)+0;
        int r2=rn.nextInt(neighborSolution.size()-r1+1)+r1;
        Collections.reverse(neighborSolution.subList(r1,r2));
    }




    public int getDistance(ArrayList<City>neighborSolution) {
        int distance = 0;
        for (int index = 0; index < neighborSolution.size(); index++) {
            City starting = neighborSolution.get(index);
            City destination;
            if (index + 1 < neighborSolution.size()) {
                destination = neighborSolution.get(index + 1);
            } else {
                destination = neighborSolution.get(0);
            }
            distance += starting.distanceToCity(destination);
        }
        return distance;
    }
}
