package com.example.tsp_thomas;

import com.example.tsp_thomas.entities.City;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

public class Controller {
    Thread thread;
    int numberCities=0;
    int distance=0;
    int swaps=0;
    ArrayList<City>route=new ArrayList<>();

    @FXML
    AnchorPane myAnchorPane;

    @FXML
    TextField numberCitiesTextField;

    @FXML
    Label distanceLabel;

    @FXML
    Label swapsLabel;

    @FXML
    Label errorLabel;

    @FXML
    Pane myPane;

    @FXML
    Button startHillCLimbingButton;

    @FXML
    Button startStepCountingHillCLimbingButton;

    @FXML
    Button stopOptimizeButton;

    @FXML
    Slider mySlider;

    ArrayList<Circle>circles=new ArrayList<>();
    ArrayList<Line>lines=new ArrayList<>();





    @FXML
    public void initialize() {
        myPane.setPrefWidth(Settings.paneWidth);
        myPane.setPrefHeight(Settings.paneHeight);
        addSlider();

    }


    @FXML
    protected void clickRandomize() {
        distance=0;
        swaps=0;
        distanceLabel.setText(String.valueOf(distance));
        swapsLabel.setText(String.valueOf(swaps));
        Platform.runLater(() -> {
            myPane.getChildren().removeAll(circles);
            myPane.getChildren().removeAll(lines);
            circles=new ArrayList<>();
            lines=new ArrayList<>();
            route=new ArrayList<>();
            numberCities=0;
            errorLabel.setText("");
            try{
                numberCities=Integer.parseInt(numberCitiesTextField.getText());
            }catch(Exception e){
                errorLabel.setText("no number!!");
            }

            for(int i=0;i<numberCities;i++){
                int xCoordinate= ThreadLocalRandom.current().nextInt(Settings.circleRadius, Settings.paneWidth- Settings.circleRadius);
                int yCoordinate= ThreadLocalRandom.current().nextInt(Settings.circleRadius, Settings.paneHeight- Settings.circleRadius);
                Circle circle=new Circle(xCoordinate,yCoordinate, Settings.circleRadius);
                circle.setFill(Color.RED);
                circles.add(circle);
                myPane.getChildren().add(circle);
                route.add(new City(xCoordinate,yCoordinate));
            }
        });



    }

    @FXML
    protected void clickInitialSolution() {
        distance=0;
        swaps=0;
        distanceLabel.setText(String.valueOf(distance));
        swapsLabel.setText(String.valueOf(swaps));
        Platform.runLater(() -> {
            Collections.shuffle(route);
            myPane.getChildren().removeAll(lines);
            lines = new ArrayList<>();
            distance = 0;
            for (int i = 0; i < route.size(); i++) {
                City starting = route.get(i);
                City destination;
                if (i + 1 < route.size()) {
                    destination = route.get(i + 1);
                } else {
                    destination = route.get(0);
                }
                distance += starting.distanceToCity(destination);
                Line line = new Line(starting.getX(), starting.getY(), destination.getX(), destination.getY());
                lines.add(line);
                myPane.getChildren().add(line);
            }
            distanceLabel.setText(String.valueOf(distance));
        });
    }

    @FXML
    protected void clickStartHillCLimbing() {
        startHillCLimbingButton.setDisable(true);
        startStepCountingHillCLimbingButton.setDisable(true);
        thread=new Thread(() -> {
            Solver solver=new Solver(route,this);
            try {
                solver.startHillClimbing();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    @FXML
    protected void clickStartStepCountingHillCLimbing() {
        startHillCLimbingButton.setDisable(true);
        startStepCountingHillCLimbingButton.setDisable(true);
        thread=new Thread(() -> {
            Solver solver=new Solver(route,this);
            try {
                solver.startStepCountingHillCLimbing();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    @FXML
    protected void clickStopOptimzie() {
        startHillCLimbingButton.setDisable(false);
        startStepCountingHillCLimbingButton.setDisable(false);
        thread.stop();
    }

    public void updateSolution(ArrayList<City>bestRoute){
        Platform.runLater(() -> {
            myPane.getChildren().removeAll(lines);
            lines=new ArrayList<>();
            distance = 0;
            for (int i = 0; i < bestRoute.size(); i++) {
                City starting = bestRoute.get(i);
                City destination;
                if (i + 1 < bestRoute.size()) {
                    destination = bestRoute.get(i + 1);
                } else {
                    destination = bestRoute.get(0);
                }
                distance += starting.distanceToCity(destination);
                Line line= new Line(starting.getX(),starting.getY() ,destination.getX() , destination.getY());
                lines.add(line);
                myPane.getChildren().add(line);
            }
            distanceLabel.setText(String.valueOf(distance));
            swaps++;
            swapsLabel.setText(String.valueOf(swaps));
        });
    }


    private void addSlider() {
        mySlider.setMin(1);
        mySlider.setMax(1000);
        mySlider.setMajorTickUnit(50);
        mySlider.setBlockIncrement(50);
        mySlider.setMinorTickCount(0);

        mySlider.setValue(Settings.delay);

        // Adding Listener to value property.
        mySlider.valueProperty().addListener(
                new ChangeListener<Number>() {
                    public void changed(ObservableValue <? extends Number > observable, Number oldValue, Number newValue)
                    {
                        long delay=(long) (mySlider.getMax()-mySlider.getValue()+mySlider.getMin());
                        Settings.delay= delay;
                    }
                });
    }

}