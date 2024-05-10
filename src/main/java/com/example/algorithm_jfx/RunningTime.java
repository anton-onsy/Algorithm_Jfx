package com.example.algorithm_jfx;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Random;

public class RunningTime extends Application {
    Student[] students;
    private static final int StdNum = 1000;
    private static final int MAX_GRADE = 100;
    public GridPane gridPane;
    private HBox hbox;
    private ScrollBar scrollBar;
    private HBox[] hBoxes;
    ObservableList<String> sortingName;
    private ComboBox<String> sortingAlgorithmComboBox;
    private boolean sortingActive = false;
    private long startTime;
    private Scene scene1, scene2;
    private ScrollBar scrollBar2;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        sortingName = FXCollections.observableArrayList("selection", "bubble", "cycle", "count");
        students = new Student[StdNum];
        Random random = new Random();
        for (int i = 0; i < StdNum; i++) {
            students[i] = new Student("Student" + (i + 1), (int) (Math.random() * MAX_GRADE));
        }
        gridPane = new GridPane();
        DrawGridPane(gridPane);
        gridPane.setAlignment(Pos.CENTER);
       // gridPane.setMaxSize(500,1100);
        // gridPane.setMinHeight();
        hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(10);
        hbox.setPadding(new Insets(10));
        scrollBar = new ScrollBar();
        scrollBar.setOrientation(Orientation.VERTICAL);
        scrollBar.setMin(gridPane.getMinHeight());
        scrollBar.setMax(gridPane.getMaxHeight() * StdNum / 10);
        scrollBar.setValue(0);
        scrollBar.adjustValue(gridPane.getMaxHeight());
        gridPane.translateYProperty().bind(scrollBar.valueProperty().negate());

        gridPane.setOnScroll(event -> {
            double deltaY = event.getDeltaY() * 0.5;
            scrollBar.setValue(scrollBar.getValue() - deltaY);
        });


    Button sortButton = new Button("Sort");
    Button shuffleButton = new Button("Shuffle");
    sortingAlgorithmComboBox =new ComboBox<>(sortingName);
        sortingAlgorithmComboBox.setPromptText("Select sorting algorithm");

        sortButton.setOnAction(event ->

    {
        String selectedItem = sortingAlgorithmComboBox.getValue();
        if (selectedItem != null) {
            if (selectedItem.equals("selection")) {
                selectionSort();
                gridPane.getChildren().clear();
                DrawGridPane(gridPane);
            } else if (selectedItem.equals("bubble")) {
                bubbleSort();
                gridPane.getChildren().clear();
                DrawGridPane(gridPane);
            } else if (selectedItem.equals("cycle")) {
            } else if (selectedItem.equals("count")) {
                countSort();
                gridPane.getChildren().clear();
                DrawGridPane(gridPane);
            } else {
                System.out.println("No sorting algorithm selected.");
            }
        } else {
            System.out.println("No sorting algorithm selected.");
        }
    });

        shuffleButton.setOnAction(event ->
    {  for (int i = 0; i < StdNum; i++) {
        students[i].setGrade((int) (Math.random() * MAX_GRADE));
    }
        gridPane.getChildren().clear();
        DrawGridPane(gridPane);

    });

    // Layout configuration for buttons and combo box
    HBox controlButtons = new HBox(sortingAlgorithmComboBox, sortButton, shuffleButton);
        controlButtons.setAlignment(Pos.CENTER);
        controlButtons.setSpacing(20);
        controlButtons.setMaxSize(500,100);
        controlButtons.setMinSize(0,0);

    // Create bottom layout for buttons
    BorderPane bottomLayout = new BorderPane();
        bottomLayout.setBackground(new

    Background(new BackgroundFill(Color.WHITESMOKE, CornerRadii.EMPTY, Insets.EMPTY)));
        bottomLayout.setCenter(controlButtons);
        bottomLayout.setPadding(new

    Insets(10));

    // Create main BorderPane to hold grid and buttons
    BorderPane root = new BorderPane();

        root.setCenter(gridPane);
        root.setRight(scrollBar);
        root.setTop(bottomLayout);

    scene1 =new

    Scene(root, 500,400);
        primaryStage.setScene(scene1);
        primaryStage.show();
}

    public GridPane DrawGridPane(GridPane gridPane){

            gridPane.setPadding(new Insets(10));
            gridPane.setHgap(20);
            gridPane.setVgap(10);

            Label nameLabel = new Label("Student Name");
            Label gradeLabel = new Label("Grade");
            HBox h1 = new HBox();
            h1.getChildren().addAll(nameLabel, gradeLabel);
            nameLabel.setStyle("-fx-font-weight: bold;");
            gradeLabel.setStyle("-fx-font-weight: bold;");
            h1.setSpacing(50);
            gridPane.getChildren().addAll(h1);
            hBoxes = new HBox[StdNum];

            for (int i = 0; i < StdNum; i++) {
//            students[i].setName("Student"+"i");
//            students[i].setGrade((int) (Math.random() * StdNum));
                Label studentNameLabel;
                Label gradeLabelValue;
                if (i <= 8) {
                    studentNameLabel = new Label(students[i].getName());
                    gradeLabelValue = new Label("  " + Integer.toString(students[i].getGrade()));
                } else {
                    studentNameLabel = new Label(students[i].getName());
                    gradeLabelValue = new Label(Integer.toString(students[i].getGrade()));
                }
                HBox hBox = new HBox();
                hBox.getChildren().addAll(studentNameLabel, gradeLabelValue);
                hBox.setSpacing(85);
                hBoxes[i] = hBox;
                gridPane.add(hBox, 0, i + 1);
                if (i % 2 == 0) {
                    hBox.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, null)));
                } else {
                    hBox.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
                }
            }
            return gridPane;


        }
    public void selectionSort() {
        startTime = System.nanoTime();
        for (int i = 0; i < StdNum - 1; i++) {
            // Find the index of the minimum grade from the subarray[i...StdNum]
            int minIndex = i;
            for (int j = i + 1; j < StdNum; j++) {
                if (students[j].getGrade() < students[minIndex].getGrade()) {
                    minIndex = j;
                }
            }

            // Swap students[i] and students[minIndex]
            Student temp = students[i];
            students[i] = students[minIndex];
            students[minIndex] = temp;
        }
        long endTime = System.nanoTime();
        System.out.println("Selection sort running time: " + (endTime - startTime) + " nanoseconds");
    }
    public void bubbleSort() {
        startTime = System.nanoTime();
        for (int i = 0; i < StdNum - 1; i++) {
            for (int j = 0; j < StdNum - i - 1; j++) {
                if (students[j].getGrade() > students[j + 1].getGrade()) {
                    // Swap students[j] and students[j + 1]
                    Student temp = students[j];
                    students[j] = students[j + 1];
                    students[j + 1] = temp;
                }
            }
        }
        long endTime = System.nanoTime();
        System.out.println("Bubble sort running time: " + (endTime - startTime) + " nanoseconds");
    }
    public void countSort() {
        startTime = System.nanoTime();
        int maxGrade = 0;
        for (int i = 0; i < StdNum; i++) {
            if (students[i].getGrade() > maxGrade) {
                maxGrade = students[i].getGrade();
            }
        }

        // Initialize count array
        int[] count = new int[maxGrade + 1];

        // Store count of each grade
        for (int i = 0; i < StdNum; i++) {
            count[students[i].getGrade()]++;
        }

        // Change count[i] so that count[i] now contains actual position of this grade in output array
        for (int i = 1; i <= maxGrade; i++) {
            count[i] += count[i - 1];
        }

        // Build the output student array
        Student[] output = new Student[StdNum];
        for (int i = StdNum - 1; i >= 0; i--) {
            output[count[students[i].getGrade()] - 1] = students[i];
            count[students[i].getGrade()]--;
        }

        // Copy the output array to students[], so that students[] now contains sorted students by grade
        for (int i = 0; i < StdNum; i++) {
            students[i] = output[i];
        }
        long endTime = System.nanoTime();
        System.out.println("Count sort running time: " + (endTime - startTime) + " nanoseconds");
    }


}

