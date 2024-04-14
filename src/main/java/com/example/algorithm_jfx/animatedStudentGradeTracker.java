package com.example.algorithm_jfx;


import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class animatedStudentGradeTracker extends Application {

     public TableView<studentTracker> tableView;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        ObservableList<String>sortingName= FXCollections.observableArrayList("selection","bubble");
        ArrayList<studentTracker> students = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            students.add(new studentTracker("Student" + i, random.nextInt(100)));
        }

        Collections.shuffle(students);

        Button b1=new Button("sorting");
        Button b2=new Button("random sorting");
        ComboBox c1=new ComboBox(sortingName);
        c1.setPromptText("select sorting algorithm");

         tableView = new TableView<>();
         tableView.setPrefSize(400,400);
        tableView.getItems().addAll(students);
        TableColumn<studentTracker, String> NameColumn = new TableColumn<>("StudentName");
        NameColumn.setCellValueFactory(new Callback<>() {
            @Override
            public javafx.beans.value.ObservableValue<String> call(TableColumn.CellDataFeatures<studentTracker, String> param) {
                return new javafx.beans.value.ObservableValue<>() {
                    @Override
                    public void addListener(InvalidationListener invalidationListener) {

                    }

                    @Override
                    public void removeListener(InvalidationListener invalidationListener) {

                    }

                    @Override
                    public void addListener(javafx.beans.value.ChangeListener<? super String> listener) {
                    }

                    @Override
                    public void removeListener(javafx.beans.value.ChangeListener<? super String> listener) {
                    }

                    @Override
                    public String getValue() {
                        return param.getValue().getName();
                    }
                };
            }
        });
        TableColumn<studentTracker, Integer> degreeColumn = new TableColumn<>("Degree");
        degreeColumn.setCellValueFactory(new Callback<>() {
            @Override
            public javafx.beans.value.ObservableValue<Integer> call(TableColumn.CellDataFeatures<studentTracker, Integer> param) {
                return new javafx.beans.value.ObservableValue<>() {
                    @Override
                    public void addListener(InvalidationListener invalidationListener) {

                    }

                    @Override
                    public void removeListener(InvalidationListener invalidationListener) {

                    }

                    @Override
                    public void addListener(javafx.beans.value.ChangeListener<? super Integer> listener) {
                    }

                    @Override
                    public void removeListener(javafx.beans.value.ChangeListener<? super Integer> listener) {
                    }

                    @Override
                    public Integer getValue() {
                        return param.getValue().Stdgrade;
                    }
                };
            }
        });
       tableView.getColumns().addAll(NameColumn,degreeColumn);


        b1.setOnAction(event -> {

            String selectedItem = (String) c1.getValue();
            if (selectedItem != null) {
                if (selectedItem == "selection"){
                    studentTracker.selectionSortStd(students);
                    tableView.getItems().clear();
                    tableView.getItems().addAll(students);

                }
                if(selectedItem == "bubble"){
                    studentTracker.bubbleSortStd(students);
                    tableView.getItems().clear();
                    tableView.getItems().addAll(students);

                }

            } else {
                System.out.println("No action selected.");
            }
        });
        b2.setOnAction(event -> {
            tableView.getItems().clear();
            Collections.shuffle(students);
            tableView.getItems().addAll(students);

        });


        HBox h1=new HBox(c1,b1,b2);
        h1.setSpacing(20);
        VBox v1=new VBox(tableView,h1);
        v1.setSpacing(20);
        h1.setAlignment(Pos.CENTER);

        Scene scene=new Scene(v1,500,400) ;
        primaryStage.setScene(scene);
        primaryStage.show();

    }

}
