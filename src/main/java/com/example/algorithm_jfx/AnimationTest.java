package com.example.algorithm_jfx;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AnimationTest extends Application {

    private static final int ARRAY_SIZE = 5;
    private static final int RECTANGLE_WIDTH = 100;
    private static final int MAX_GRADE = 100;

    private Student[] students;
    private HBox hbox;
    private int step;
    private int sortedElemNum;

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
        hbox.setStyle("-fx-border-color: black;");
        drawStudents();

        Scene scene = new Scene(hbox, ARRAY_SIZE * RECTANGLE_WIDTH, 150);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Sorting Visualization");
        primaryStage.show();

        // Visualize Selection Sort
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> {
                    if (step < ARRAY_SIZE) {
                        selectionSortStep();
                        drawStudents();
                        step++;
                    }
                })
        );
        timeline.setCycleCount(ARRAY_SIZE);
        timeline.play();
    }

    private void drawStudents() {
        hbox.getChildren().clear();
        for (int i = 0; i < ARRAY_SIZE; i++) {
            Student student = students[i];
            Rectangle rectangle = new Rectangle(RECTANGLE_WIDTH, 50 * student.getGrade() / MAX_GRADE, Color.GRAY);
            rectangle.setStroke(Color.WHITE);
            rectangle.setStrokeWidth(2);
            Text text = new Text(student.getName() + " (" + student.getGrade() + ")");
            text.setFill(Color.BLACK);
            StackPane stackPane = new StackPane(rectangle, text);
            stackPane.setAlignment(Pos.BOTTOM_CENTER);
            hbox.getChildren().add(stackPane);
        }
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
}
