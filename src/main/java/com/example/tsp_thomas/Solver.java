package com.example.tsp_thomas;

import com.example.tsp_thomas.entities.City;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Solver {
    public Random rn = new Random();

    private ArrayList<City> initalSolution;
    private Controller controller;

    public Solver(ArrayList<City> initalSolution, Controller controller) {
        this.initalSolution=initalSolution;
        this.controller=controller;
    }

    public void startHillClimbing() throws InterruptedException {
        ArrayList<City>currentSolution=copy(initalSolution);
        ArrayList<City>bestSolution=copy(initalSolution);
        int bestDistance=getDistance(currentSolution);
        while (true){
            int r1=rn.nextInt((currentSolution.size()-1)-0+1)+0;
            int r2=rn.nextInt(currentSolution.size()-r1+1)+r1;
            twoOpt(currentSolution,r1,r2);
            int newDistance=getDistance(currentSolution);
            if(newDistance<bestDistance){
                bestDistance=newDistance;
                bestSolution=copy(currentSolution);
                controller.updateSolution(bestSolution);
                Thread.sleep(Settings.delay);
            }
            else{
                twoOpt(currentSolution,r1,r2);
            }
        }
    }

    public void startStepCountingHillCLimbing() throws InterruptedException {
        int L=500;
        int count=0;
        ArrayList<City>currentSolution=copy(initalSolution);
        ArrayList<City>bestSolution=copy(initalSolution);
        int bound=getDistance(bestSolution);
        while(true){
            int oldDistance=getDistance(currentSolution);
            int r1=rn.nextInt((currentSolution.size()-1)-0+1)+0;
            int r2=rn.nextInt(currentSolution.size()-r1+1)+r1;
            twoOpt(currentSolution,r1,r2);
            int newDistance=getDistance(currentSolution);
            if(newDistance<oldDistance||newDistance<bound){
                if(getDistance(currentSolution)<getDistance(bestSolution)){
                    bestSolution=copy(currentSolution);
                    controller.updateSolution(bestSolution);
                    Thread.sleep(Settings.delay);
                }
            }
            else{
                twoOpt(currentSolution,r1,r2);
            }
            count++;

            if(count>L){
                count=0;
                bound=getDistance(currentSolution);
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

    public void twoOpt(ArrayList<City>neighborSolution,int r1,int r2) {
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
