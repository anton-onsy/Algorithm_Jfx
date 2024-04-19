package com.example.algorithm_jfx;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class StudentGradeTrackerProject extends Application {

    ArrayList<studentTracker> students;
    ObservableList<String> sortingName;
    ComboBox<String> sortingAlgorithmComboBox;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        sortingName = FXCollections.observableArrayList("selection", "bubble");
        students = new ArrayList<>();

        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            students.add(new studentTracker("Student" + i, random.nextInt(100)));
        }

        Collections.shuffle(students);

        Button sortButton = new Button("Sort");
        Button shuffleButton = new Button("Shuffle");
        sortingAlgorithmComboBox = new ComboBox<>(sortingName);
        sortingAlgorithmComboBox.setPromptText("Select sorting algorithm");

        sortButton.setOnAction(event -> {
            String selectedItem = sortingAlgorithmComboBox.getValue();
            if (selectedItem != null) {
                if (selectedItem.equals("selection")) {
                    animateSelectionSort();
                } else if (selectedItem.equals("bubble")) {
                    animateBubbleSort();
                }
            } else {
                System.out.println("No sorting algorithm selected.");
            }
        });

        shuffleButton.setOnAction(event -> {
            Collections.shuffle(students);
            updateGridView(primaryStage);
        });

        HBox controlButtons = new HBox(sortingAlgorithmComboBox, sortButton, shuffleButton);
        controlButtons.setAlignment(Pos.CENTER);
        controlButtons.setSpacing(20);

        VBox v1 = new VBox(controlButtons);
        v1.setAlignment(Pos.CENTER);
        v1.setSpacing(20);

        Scene scene = new Scene(v1, 500, 400);
        primaryStage.setScene(scene);
        primaryStage.show();

        updateGridView(primaryStage);
    }

    private void updateGridView(Stage primaryStage) {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(20);
        gridPane.setVgap(10);

        Label nameLabel = new Label("Student Name");
        Label gradeLabel = new Label("Grade");
        nameLabel.setStyle("-fx-font-weight: bold;");
        gradeLabel.setStyle("-fx-font-weight: bold;");
        gridPane.add(nameLabel, 0, 0);
        gridPane.add(gradeLabel, 1, 0);

        for (int i = 0; i < students.size(); i++) {
            Label studentNameLabel = new Label(students.get(i).getName());
            Label gradeLabelValue = new Label(Integer.toString(students.get(i).getGrade()));
            HBox hbox = new HBox(20);
            hbox.getChildren().addAll(studentNameLabel, gradeLabelValue);
            if (i % 2 == 0) {
                hbox.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, null)));
            } else {
                hbox.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
            }
            gridPane.add(hbox, 0, i + 1, 8, 1);
        }

        primaryStage.setScene(new Scene(gridPane, 320, 200));
    }

    private void animateBubbleSort() {
        Timeline timeline = new Timeline();
        boolean swapped;
        do {
            swapped = false;
            for (int i = 0; i < students.size() - 1; i++) {
                if (students.get(i).getGrade() > students.get(i + 1).getGrade()) {
                    Collections.swap(students, i, i + 1);
                    updateGridView((Stage) sortingAlgorithmComboBox.getScene().getWindow());
                    swapped = true;
                }
            }
        } while (swapped);
        timeline.play();
    }

    private void animateSelectionSort() {
        Timeline timeline = new Timeline();
        for (int i = 0; i < students.size() - 1; i++) {
            final int minIndex = i;
            timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), event -> {
                int minAge = students.get(minIndex).getGrade();
                int swapIndex = minIndex;
                for (int j = minIndex + 1; j < students.size(); j++) {
                    if (students.get(j).getGrade() < minAge) {
                        minAge = students.get(j).getGrade();
                        swapIndex = j;
                    }
                }
                if (swapIndex != minIndex) {
                    Collections.swap(students, minIndex, swapIndex);
                    updateGridView((Stage) sortingAlgorithmComboBox.getScene().getWindow());
                }
            }));
        }
        timeline.play();
    }
}