package com.example.algorithm_jfx;

import javafx.animation.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;



public class StudentGradeTrackerProject extends Application {

    public static final int StdNum = 3;
    ArrayList<studentTracker> students;
    HBox[] hBoxes;
    ObservableList<String> sortingName;
    ComboBox<String> sortingAlgorithmComboBox;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        sortingName = FXCollections.observableArrayList("selection", "bubble");
        students = new ArrayList<>();

        // Create grid for student data
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(20);
        gridPane.setVgap(10);

        Label nameLabel = new Label("Student Name");
        Label gradeLabel = new Label("Grade");
        HBox h1=new HBox();
        h1.getChildren().addAll(nameLabel,gradeLabel);
        nameLabel.setStyle("-fx-font-weight: bold;");
        gradeLabel.setStyle("-fx-font-weight: bold;");
        h1.setSpacing(50);
        gridPane.getChildren().addAll(h1);
        hBoxes=new HBox[StdNum];
        Random random = new Random();
        for (int i = 0; i < StdNum; i++) {
            students.add(new studentTracker("Student" + i, random.nextInt(100)));
            Label studentNameLabel = new Label(students.get(i).getName());
            Label gradeLabelValue = new Label(Integer.toString(students.get(i).getGrade()));
            HBox hBox= new HBox();
            hBox.getChildren().addAll(studentNameLabel, gradeLabelValue);
            hBox.setSpacing(85);
            hBoxes[i]=hBox;
            gridPane.add(hBox, 0, i+1);
            if (i % 2 == 0) {
                hBox.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, null)));
            } else {
                hBox.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
            }
        }

        Collections.shuffle(students);

        // Create buttons and combo box
        Button sortButton = new Button("Sort");
        Button shuffleButton = new Button("Shuffle");
        sortingAlgorithmComboBox = new ComboBox<>(sortingName);
        sortingAlgorithmComboBox.setPromptText("Select sorting algorithm");

        sortButton.setOnAction(event -> {
            String selectedItem = sortingAlgorithmComboBox.getValue();
            if (selectedItem != null) {
                if (selectedItem.equals("selection")) {
                    animateSelectionSort(students,gridPane);
                } else if (selectedItem.equals("bubble")) {
                    animateBubbleSort(students);
                }
            } else {
                System.out.println("No sorting algorithm selected.");
            }
        });

        shuffleButton.setOnAction(event -> {

            Collections.shuffle(students);
            //updateGridView(gridPane);
        });

        // Layout configuration for buttons and combo box
        HBox controlButtons = new HBox(sortingAlgorithmComboBox, sortButton, shuffleButton);
        controlButtons.setAlignment(Pos.CENTER);
        controlButtons.setSpacing(20);

        // Create bottom layout for buttons
        BorderPane bottomLayout = new BorderPane();
        bottomLayout.setCenter(controlButtons);
        bottomLayout.setPadding(new Insets(10));

        // Create main BorderPane to hold grid and buttons
        BorderPane root = new BorderPane();
        root.setCenter(gridPane);
        root.setBottom(bottomLayout);

        Scene scene = new Scene(root, 500, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void animateBubbleSort(ArrayList<studentTracker> students) {
        Timeline timeline = new Timeline();
        boolean swapped;
        do {
            swapped = false;
            for (int i = 0; i < students.size() - 1; i++) {
                if (students.get(i).getGrade() > students.get(i + 1).getGrade()) {
                    Collections.swap(students, i, i + 1);

                    swapped = true;
                }
            }
        } while (swapped);
        timeline.play();
    }




    private void animateSelectionSort(ArrayList<studentTracker> students, GridPane gridPane) {
        Timeline timeline = new Timeline();
        timeline.setCycleCount(students.size());
        for (int i = 0; i < students.size() - 1; i++) {
            final int minIndex = i;
            timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(i), event -> {
                int minGrade = students.get(minIndex).getGrade();
                int swapIndex = minIndex;
                for (int j = minIndex + 1; j < students.size(); j++) {
                    if (students.get(j).getGrade() < minGrade) {
                        System.out.println("area 1");
                        minGrade = students.get(j).getGrade();
                        swapIndex = j;
                    }
                }
                if (swapIndex != minIndex) {
                    // Swap students
                    System.out.println("area 2");
                    Collections.swap(students, minIndex, swapIndex);
                    // Animate hBoxes
                    TranslateTransition tt1 = new TranslateTransition(Duration.seconds(1), hBoxes[minIndex]);
                    TranslateTransition tt2 = new TranslateTransition(Duration.seconds(1), hBoxes[swapIndex]);

                    tt1.setToY((swapIndex - minIndex) * hBoxes[minIndex].getHeight());
                    tt2.setToY((minIndex - swapIndex) * hBoxes[swapIndex].getHeight());
                    tt1.play();
                    tt2.play();

                    int finalSwapIndex = swapIndex;
                  tt1.setOnFinished(e -> {
                        hBoxes[minIndex].setTranslateY(0);
                        gridPane.getChildren().remove(hBoxes[minIndex]);
                        gridPane.add(hBoxes[minIndex], 0, finalSwapIndex + 1);
                    });
                    tt2.setOnFinished(e -> {
                        hBoxes[finalSwapIndex].setTranslateY(0);
                        gridPane.getChildren().remove(hBoxes[finalSwapIndex]);
                        gridPane.add(hBoxes[finalSwapIndex], 0, minIndex + 1);
                    });
                    // Swap hBoxes
                    HBox temp = hBoxes[minIndex];
                    hBoxes[minIndex] = hBoxes[swapIndex];
                    hBoxes[swapIndex] = temp;
                }
            }));
        }
        timeline.play();
    }


}
