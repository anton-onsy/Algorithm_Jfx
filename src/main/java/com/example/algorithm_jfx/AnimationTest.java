package com.example.algorithm_jfx;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AnimationTest extends Application {

    private static final int ARRAY_SIZE = 10;
    private static final int RECTANGLE_WIDTH = 75;
    private static final int MAX_GRADE = 100;

    private Student[] students;
    private HBox hbox;
    private int step;
    private boolean sortingActive;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        students = new Student[ARRAY_SIZE];
        for (int i = 0; i < ARRAY_SIZE; i++) {
            students[i] = new Student("Student" + (i + 1), (int) (Math.random() * MAX_GRADE));
        }

        hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(10);
        hbox.setPadding(new Insets(10));

        BorderPane root = new BorderPane();
        root.setCenter(hbox);

        Button selectionSortButton = new Button("Selection Sort");
        Button selectionSortOneStepButton = new Button("Selection Sort One Step");
        Button bubbleSortButton = new Button("Bubble Sort");
        Button shuffleButton = new Button("Shuffle");

        HBox bottomBox = new HBox(10, selectionSortButton, selectionSortOneStepButton, bubbleSortButton, shuffleButton);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(10));

        root.setBottom(bottomBox);

        Scene scene = new Scene(root, 900, 400); // Initial size set to 800x400
        primaryStage.setScene(scene);
        primaryStage.setTitle("Sorting Visualization");
        primaryStage.show();

        selectionSortButton.setOnAction(e -> {
            sortingActive = true;
            startSorting("Selection Sort");
        });

        selectionSortOneStepButton.setOnAction(e -> {
            selectionSortStepVisualization();
        });

        bubbleSortButton.setOnAction(e -> {
            sortingActive = true;
            startSorting("Bubble Sort");
        });

        shuffleButton.setOnAction(e -> {
            sortingActive = false;
            shuffleStudents();
            drawStudents();
        });

        drawStudents();
    }

    private void drawStudents() {
        hbox.getChildren().clear();
        for (int i = 0; i < ARRAY_SIZE; i++) {
            Student student = students[i];
            Rectangle rectangle = new Rectangle(RECTANGLE_WIDTH, 200 * student.getGrade() / MAX_GRADE, Color.LIGHTGREY);
            Text text = new Text(student.getName() + " (" + student.getGrade() + ")");
            text.setFill(Color.BLACK);
            StackPane stackPane = new StackPane(rectangle, text);
            stackPane.setAlignment(Pos.BOTTOM_CENTER);
            hbox.getChildren().add(stackPane);
        }
    }

    private void drawStudentsComparing() {}

    private void drawStudentsComparingJustBeforeSwapping(int firstInd, int secondInd) {
        hbox.getChildren().clear();
        for (int i = 0; i < ARRAY_SIZE; i++) {
            Student student = students[i];

            Rectangle rectangle;
            //Coloring
            if(i!=firstInd & i != secondInd){
                rectangle = new Rectangle(RECTANGLE_WIDTH, 200 * student.getGrade() / MAX_GRADE, Color.LIGHTGREY);
            } else if (i == firstInd) {
                rectangle = new Rectangle(RECTANGLE_WIDTH, 200 * student.getGrade() / MAX_GRADE, Color.RED);
            }else {
                rectangle = new Rectangle(RECTANGLE_WIDTH, 200 * student.getGrade() / MAX_GRADE, Color.BLUE);
            }



            Text text = new Text(student.getName() + " (" + student.getGrade() + ")");
            text.setFill(Color.BLACK);
            StackPane stackPane = new StackPane(rectangle, text);
            stackPane.setAlignment(Pos.BOTTOM_CENTER);
            hbox.getChildren().add(stackPane);
        }
    }


    private void drawStudentsJustAfterSwapping(int firstInd, int secondInd) {
        hbox.getChildren().clear();
        for (int i = 0; i < ARRAY_SIZE; i++) {
            Student student = students[i];

            Rectangle rectangle;
            //Coloring
            if(i!=firstInd & i != secondInd){
                rectangle = new Rectangle(RECTANGLE_WIDTH, 200 * student.getGrade() / MAX_GRADE, Color.LIGHTGREY);
            } else {
                rectangle = new Rectangle(RECTANGLE_WIDTH, 200 * student.getGrade() / MAX_GRADE, Color.GREEN);
            }



            Text text = new Text(student.getName() + " (" + student.getGrade() + ")");
            text.setFill(Color.BLACK);
            StackPane stackPane = new StackPane(rectangle, text);
            stackPane.setAlignment(Pos.BOTTOM_CENTER);
            hbox.getChildren().add(stackPane);
        }
    }


    private void startSorting(String sortingType) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> {
                    if (sortingActive && step < ARRAY_SIZE) {
                        if (sortingType.equals("Selection Sort")) {
                            selectionSortStep();
                        } else if (sortingType.equals("Bubble Sort")) {
                            bubbleSortStep();
                        }
                        drawStudents();
                        step++;
                    }
                })
        );
        timeline.setCycleCount(ARRAY_SIZE);
        timeline.play();
    }

    private void shuffleStudents() {
        // Shuffle students array
        for (int i = 0; i < ARRAY_SIZE; i++) {
            int randomIndex = (int) (Math.random() * ARRAY_SIZE);
            Student temp = students[i];
            students[i] = students[randomIndex];
            students[randomIndex] = temp;
        }
        step = 0;
    }

    private void selectionSortStep() {
        int minIndex = step;
        for (int i = step + 1; i < ARRAY_SIZE; i++) {
            if (students[i].getGrade() < students[minIndex].getGrade()) {
                minIndex = i;
            }
        }
        // Swap
        Student temp = students[minIndex];
        students[minIndex] = students[step];
        students[step] = temp;
    }

    private void selectionSortStepVisualization() {

        KeyFrame Compare = new KeyFrame(Duration.seconds(2), e -> {
            drawStudentsComparingJustBeforeSwapping(0,5);

        });

        KeyFrame Swap = new KeyFrame(Duration.seconds(2), e -> {
            int minIndex = step;
            for (int i = step + 1; i < ARRAY_SIZE; i++) {
                if (students[i].getGrade() < students[minIndex].getGrade()) {
                    minIndex = i;
                }
            }
            drawStudentsComparingJustBeforeSwapping(0,minIndex);
            // Swap
            Student temp = students[minIndex];
            students[minIndex] = students[step];
            students[step] = temp;
            //drawStudentsJustAfterSwapping(0, minIndex);
        });

        Timeline timeline = new Timeline(Compare,Swap);
        timeline.play();


//        int minIndex = step;
//        for (int i = step + 1; i < ARRAY_SIZE; i++) {
//            if (students[i].getGrade() < students[minIndex].getGrade()) {
//                minIndex = i;
//            }
//        }
//        drawStudentsComparingJustBeforeSwapping(0,minIndex);
//
//        // Swap
//        Student temp = students[minIndex];
//        students[minIndex] = students[step];
//        students[step] = temp;
//
//        drawStudentsJustAfterSwapping(0, minIndex);
    }
    private void bubbleSortStep() {
        if (step < ARRAY_SIZE - 1) {
            for (int j = 0; j < ARRAY_SIZE - step - 1; j++) {
                if (students[j].getGrade() > students[j + 1].getGrade()) {
                    // Swap
                    Student temp = students[j];
                    students[j] = students[j + 1];
                    students[j + 1] = temp;
                }
            }
        }
    }
}
