package com.example.algorithm_jfx;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class StudentGradeTrackerProject extends Application {

    public TableView<studentTracker> tableView;
    ArrayList<studentTracker> students;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        ObservableList<String> sortingName = FXCollections.observableArrayList("selection", "bubble");
        students = new ArrayList<>();


        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            students.add(new studentTracker("Student" + i, random.nextInt(100)));

        }


        Collections.shuffle(students);

        Button b1 = new Button("sorting");
        Button b2 = new Button("random sorting");
        ComboBox c1 = new ComboBox(sortingName);
        c1.setPromptText("select sorting algorithm");

        tableView = new TableView<>();
        tableView.setPrefSize(400, 400);
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
        tableView.getColumns().addAll(NameColumn, degreeColumn);


        b1.setOnAction(event -> {

            String selectedItem = (String) c1.getValue();
            if (selectedItem != null) {
                if (selectedItem == "selection") {
                    /// sorting by names \\\
//                    studentTracker.selectionSortStd(students);
//                    tableView.getItems().clear();
//                    tableView.getItems().addAll(students);

                    /// sorting by grades \\\
                    // animateSelectionSort();
                    animateSelectionSort();
                }
                if (selectedItem == "bubble") {
                    /// sorting by names \\\
//                    studentTracker.selectionSortStd(students);
//                    tableView.getItems().clear();
//                    tableView.getItems().addAll(students);
                    /// sorting by grades \\\
                    animateBubbleSort();
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


        HBox h1 = new HBox(c1, b1, b2);
        h1.setSpacing(20);
        VBox v1 = new VBox(tableView, h1);
        v1.setSpacing(20);
        h1.setAlignment(Pos.CENTER);

        Scene scene = new Scene(v1, 500, 400);
        primaryStage.setScene(scene);
        primaryStage.show();

    }


    //////////////////////sorting by students grades \\\\\\\\\\\\\\\\\\\\\\\\\
    private void animateBubbleSort() {

        Timeline timeline = new Timeline();
        // timeline.setCycleCount(timeline.INDEFINITE);
        boolean swapped;
        do {
            swapped = false;
            for (int i = 0; i < students.size() - 1; i++) {
                if (students.get(i).getGrade() > students.get(i + 1).getGrade()) {
                    Collections.swap(students, i, i + 1);
                    final int index = i;
                    timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(.5), event -> {
                        // Update TableView items to reflect the swap
                        tableView.getItems().clear();
                        tableView.getItems().addAll(students);
                        tableView.getSelectionModel().clearSelection();
                        tableView.getSelectionModel().select(index);
                    }));
                    swapped = true;
                }
            }
        } while (swapped);
        timeline.play();
    }

    private void animateSelectionSort() {
        Timeline timeline = new Timeline();
        //timeline.setCycleCount(Timelin);
        for (int i = 0; i < students.size() - 1; i++) {
            final int minIndex = i;
            timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), event -> {
                // Select current minimum index
                tableView.getSelectionModel().clearSelection();
                tableView.getSelectionModel().select(minIndex);
                // Find minimum element in unsorted portion
                int minAge = students.get(minIndex).getGrade();
                int swapIndex = minIndex;
                for (int j = minIndex + 1; j < students.size(); j++) {
                    if (students.get(j).getGrade() < minAge) {
                        minAge = students.get(j).getGrade();
                        swapIndex = j;
                    }
                }
                // Swap elements
                if (swapIndex != minIndex) {
                    Collections.swap(students, minIndex, swapIndex);
                    // Update TableView items to reflect the swap
                    tableView.getItems().clear();
                    tableView.getItems().addAll(students);
                }
            }));
        }
        timeline.play();
    }


}