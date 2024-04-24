package com.example.algorithm_jfx;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class KeyFrameExample extends Application {

    @Override
    public void start(Stage primaryStage) {
        Rectangle rect = new Rectangle(50, 50);
        StackPane root = new StackPane(rect);
        Scene scene = new Scene(root, 200, 200);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Define KeyFrames for moving the rectangle
        KeyFrame moveRight = new KeyFrame(Duration.seconds(1), e -> {
            rect.setTranslateX(rect.getTranslateX() + 50); // Move right
        });

        KeyFrame moveUp = new KeyFrame(Duration.seconds(2), e -> {
            rect.setTranslateY(rect.getTranslateY() - 50); // Move up
        });

        KeyFrame moveLeft = new KeyFrame(Duration.seconds(3), e -> {
            rect.setTranslateX(rect.getTranslateX() - 50); // Move left
        });

        // Create a Timeline with the defined KeyFrames
        Timeline timeline = new Timeline(moveRight, moveUp, moveLeft);
        timeline.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}