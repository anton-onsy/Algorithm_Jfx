package com.example.algorithm_jfx;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;


public class AnimationStage {
    private static final int ARRAY_SIZE = 5;
    private static final int RECTANGLE_WIDTH =100 ;
    private static final int MAX_GRADE = 100;
     public Student [] students;
    public HBox hbox;
    private int step;

    public void AnimationStage ()
    {

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> {
                    if (step < ARRAY_SIZE) {
                        selectionSortStep();
                        drawStudents(StudentGradeTrackerProject.students);
                        step++;
                    }
                })
        );
        timeline.setCycleCount(ARRAY_SIZE);
        timeline.play();
    }
    public HBox drawStudents(Student[] students) {
        hbox.getChildren().clear();
        for (int i = 0; i < ARRAY_SIZE; i++) {
            Rectangle rectangle = new Rectangle(RECTANGLE_WIDTH, 50 * students[i].getGrade() / MAX_GRADE, Color.GRAY);
            rectangle.setStroke(Color.WHITE);
            rectangle.setStrokeWidth(2);
            rectangle.setHeight(students[i].getGrade());
            Text text = new Text(students[i].getName() + " (" + students[i].getGrade() + ")");
            text.setFill(Color.BLACK);
            StackPane stackPane = new StackPane(rectangle, text);
            stackPane.setAlignment(Pos.BOTTOM_CENTER);
            stackPane.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
            hbox.getChildren().add(stackPane);
        }
        return hbox;
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
