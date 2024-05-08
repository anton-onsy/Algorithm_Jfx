package com.example.algorithm_jfx;

import javafx.animation.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.skin.ButtonSkin;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.nio.channels.Pipe;
import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;


public class StudentGradeTrackerProject extends Application {

    public static final int StdNum = 10;
    static Student[] students;
    HBox[] hBoxes;
    ObservableList<String> sortingName;
    ComboBox<String> sortingAlgorithmComboBox;
    public AnimationStage animationStage;
    Scene scene1;
    Scene scene2;
    Scene scene3;
    private static final int MAX_GRADE = 100;
    private HBox hbox;
    private int step;
    private int visualizationStep = 0;
    int counter;
    private boolean sortingActive;
    private int RECTANGLE_WIDTH =10;
    public ScrollBar scrollBar;
    public ScrollBar scrollBar2;

 private static GridPane gridPane;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        sortingName = FXCollections.observableArrayList("selection", "bubble","heap");
        students = new Student[StdNum];
Random random =new Random();
        for (int i = 0; i < StdNum; i++) {
            students[i] = new Student("Student"+ (i+1) ,(int)(Math.random() * MAX_GRADE));
        }

        gridPane=new GridPane();
        DrawGridPane(gridPane);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setMaxSize(500,11*StdNum);
       // gridPane.setMinHeight();
        hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(10);
        hbox.setPadding(new Insets(10));
        scrollBar=new ScrollBar();
        scrollBar.setOrientation(Orientation.VERTICAL);
        scrollBar.setMin(gridPane.getMinHeight());
        scrollBar.setMax(gridPane.getMaxHeight()*StdNum/10);
        scrollBar.setValue(0);
        scrollBar.adjustValue(gridPane.getMaxHeight());
        gridPane.translateYProperty().bind(scrollBar.valueProperty().negate());

        gridPane.setOnScroll(event -> {
            double deltaY = event.getDeltaY()*0.5;
            scrollBar.setValue(scrollBar.getValue() - deltaY);
        });
        scrollBar2=new ScrollBar();
        scrollBar2.setOrientation(Orientation.HORIZONTAL);
        scrollBar2.setMin(gridPane.getMinWidth());
        scrollBar2.setMax(gridPane.getMaxWidth()*StdNum/10);
        scrollBar2.setValue(0);

        // Create buttons and combo box
        Button sortButton = new Button("Sort");
        Button shuffleButton = new Button("Shuffle");
        sortingAlgorithmComboBox = new ComboBox<>(sortingName);
        sortingAlgorithmComboBox.setPromptText("Select sorting algorithm");

        sortButton.setOnAction(event -> {
            String selectedItem = sortingAlgorithmComboBox.getValue();
            if (selectedItem != null) {
                if (selectedItem.equals("selection")) {
                    sortingActive = true;
                    startSorting("Selection Sort");
                    Button b1=new Button("back");
                    b1.setStyle("-fx-background-color: #D3D3D3;-fx-text-fill: Black;");

                    GridPane g1=new GridPane();
                    g1.getChildren().addAll(hbox);
                    g1.translateXProperty().bind(scrollBar2.valueProperty().negate());
                    g1.setAlignment(Pos.BOTTOM_CENTER);
                    BorderPane borderPane=new BorderPane();
                    borderPane.setCenter(g1);
                    borderPane.setTop(b1);
                    borderPane.setBottom(scrollBar2);

                    g1.setOnScroll(event2 -> {
                        double deltaX = event2.getDeltaX()*0.5;
                        scrollBar2.setValue(scrollBar2.getValue() - deltaX);
                    });

                    primaryStage.setScene(scene2=new Scene(borderPane,500,400));
                    primaryStage.show();
                    b1.setOnAction(actionEvent -> {
                        primaryStage.setScene(scene1);
                        DrawGridPane(gridPane);
                    });
                } else if (selectedItem.equals("bubble")) {
                    sortingActive = true;
                    startSorting("Bubble Sort");
                    Button b1=new Button("back");
                    GridPane g1=new GridPane();
                    g1.getChildren().addAll(hbox);
                    g1.translateXProperty().bind(scrollBar2.valueProperty().negate());
                    g1.setAlignment(Pos.BOTTOM_CENTER);
                    BorderPane borderPane=new BorderPane();
                    borderPane.setCenter(g1);
                    borderPane.setTop(b1);
                    borderPane.setBottom(scrollBar2);
                    g1.setOnScroll(event2 -> {
                        double deltaX = event2.getDeltaX()*0.5;
                        scrollBar2.setValue(scrollBar2.getValue() - deltaX);
                    });
                    primaryStage.setScene(scene2=new Scene(borderPane, 500,400));
                    primaryStage.show();
                    b1.setOnAction(actionEvent -> {
                        primaryStage.setScene(scene1);
                        DrawGridPane(gridPane);
                    });
                }
                else if (selectedItem.equals("heap")) {
                    sortingActive = true;
                    startSorting("Heap Sort");
                    Button b1=new Button("back");
                    GridPane g1=new GridPane();
                    g1.getChildren().addAll(hbox);
                    g1.translateXProperty().bind(scrollBar2.valueProperty().negate());
                    g1.setAlignment(Pos.BOTTOM_CENTER);
                    BorderPane borderPane=new BorderPane();
                    borderPane.setCenter(g1);
                    borderPane.setTop(b1);
                    borderPane.setBottom(scrollBar2);

                    g1.setOnScroll(event2 -> {
                        double deltaX = event2.getDeltaX()*0.5;
                        scrollBar2.setValue(scrollBar2.getValue() - deltaX);
                    });

                    primaryStage.setScene(scene3=new Scene(borderPane, 500,400));
                    primaryStage.show();
                    b1.setOnAction(actionEvent -> {
                        primaryStage.setScene(scene1);
                        DrawGridPane(gridPane);
                    });
                }

            } else {
                System.out.println("No sorting algorithm selected.");
            }
        });

        shuffleButton.setOnAction(event -> {
            sortingActive = false;
            shuffleStudents();
            DrawGridPane(gridPane);
            drawStudents();
        });

        // Layout configuration for buttons and combo box
        HBox controlButtons = new HBox(sortingAlgorithmComboBox, sortButton, shuffleButton);
        controlButtons.setAlignment(Pos.CENTER);
        controlButtons.setSpacing(20);
        controlButtons.setMaxSize(500, 100);
        controlButtons.setMinSize(0,0);

        // Create bottom layout for buttons
        BorderPane bottomLayout = new BorderPane();
        bottomLayout.setBackground(new Background(new BackgroundFill(Color.WHITESMOKE, CornerRadii.EMPTY, Insets.EMPTY)));
        bottomLayout.setCenter(controlButtons);
        bottomLayout.setPadding(new Insets(10));

        // Create main BorderPane to hold grid and buttons
        BorderPane root = new BorderPane();

        root.setCenter(gridPane);
        root.setRight(scrollBar);
        root.setTop(bottomLayout);

        scene1 = new Scene(root, 500, 400);
        primaryStage.setScene(scene1);
        primaryStage.show();

    }

//    private void animateBubbleSort(ArrayList<studentTracker> students) {
//        Timeline timeline = new Timeline();
//        boolean swapped;
//        do {
//            swapped = false;
//            for (int i = 0; i < students.size() - 1; i++) {
//                if (students.get(i).getGrade() > students.get(i + 1).getGrade()) {
//                    Collections.swap(students, i, i + 1);
//
//                    swapped = true;
//                }
//            }
//        } while (swapped);
//        timeline.play();
//    }
//
//
//    private void animateSelectionSort(ArrayList<studentTracker> students) {
//        for (int i = 0; i < students.size() - 1; i++) {
//            int minIndex = i;
//            int swapIndex=minIndex;
//            for (int j = i + 1; j < students.size(); j++) {
//                if (students.get(j).getGrade() < students.get(minIndex).getGrade()) {
//                    swapIndex = j;
//                } else if (students.get(j).getGrade() > students.get(minIndex).getGrade()) {
//                    continue;
//                }
//                else
//                {
//                    break;
//                }
//            }
//            if (swapIndex == minIndex){
//                break;
//            }
//            if(minIndex!=swapIndex) {
//              AnimationSelectionGrades(minIndex,swapIndex);
//
//
//            }
//        }
//
//
//    }


    public GridPane DrawGridPane (GridPane gridPane){

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
            if(i<=8) {
                 studentNameLabel = new Label(students[i].getName());
                 gradeLabelValue = new Label("  "+Integer.toString(students[i].getGrade()));
            }else{
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
    public void AnimationSelectionGrades(Integer minIndex,Integer swapIndex){
        Timeline timeline=new Timeline(new KeyFrame(Duration.millis(1),actionEvent ->{
            for (int i=0;i<students.length;i++) {
                hBoxes[minIndex].setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));

                HBox temp = hBoxes[minIndex];
                hBoxes[minIndex] = hBoxes[swapIndex];
                hBoxes[swapIndex] = temp;
                hBoxes[minIndex].setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));

            }

        } ));
timeline.setCycleCount(students.length);
timeline.play();
    }

    private void drawStudents() {
        hbox.getChildren().clear();
        for (int i = 0; i < StdNum; i++) {
            Student student = students[i];
            Rectangle rectangle = new Rectangle(RECTANGLE_WIDTH, 200 * student.getGrade() / MAX_GRADE, Color.LIGHTGREY);
            Text text = new Text(""+student.getGrade());
            text.setFill(Color.BLACK);
            StackPane stackPane = new StackPane(rectangle, text);
            stackPane.setAlignment(Pos.BOTTOM_CENTER);
            hbox.getChildren().add(stackPane);
        }
    }

    private void drawStudentsComparing(int firstInd, int secondInd) {
        hbox.getChildren().clear();
        for (int i = 0; i < StdNum; i++) {
            Student student = students[i];

            Rectangle rectangle = null;
            //Coloring
            if(i != firstInd & i != secondInd){
                rectangle = new Rectangle(RECTANGLE_WIDTH, 200 * student.getGrade() / MAX_GRADE, Color.LIGHTGREY);
            } else if (i == firstInd) {
                rectangle = new Rectangle(RECTANGLE_WIDTH, 200 * student.getGrade() / MAX_GRADE, Color.RED);
            }else if(i == secondInd){
                rectangle = new Rectangle(RECTANGLE_WIDTH, 200 * student.getGrade() / MAX_GRADE, Color.BLUE);
            }



            Text text = new Text(student.getName() + " (" + student.getGrade() + ")");
            text.setFill(Color.BLACK);
            StackPane stackPane = new StackPane(rectangle, text);
            stackPane.setAlignment(Pos.BOTTOM_CENTER);
            hbox.getChildren().add(stackPane);
        }
    }

    private void drawStudentsComparingJustBeforeSwapping(int firstInd, int secondInd) {
        hbox.getChildren().clear();
        for (int i = 0; i < StdNum; i++) {
            Student student = students[i];

            Rectangle rectangle;
            //Coloring
            if(i != firstInd & i != secondInd){
                rectangle = new Rectangle(RECTANGLE_WIDTH, 200 * student.getGrade() / MAX_GRADE, Color.LIGHTGREY);
            } else {
                rectangle = new Rectangle(RECTANGLE_WIDTH, 200 * student.getGrade() / MAX_GRADE, Color.RED);
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
        for (int i = 0; i < StdNum; i++) {
            Student student = students[i];

            Rectangle rectangle;
            //Coloring
            if(i != firstInd & i != secondInd){
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
                new KeyFrame(Duration.seconds(0.5), e -> {
                    if (sortingActive && step < StdNum) {
                        if (sortingType.equals("Selection Sort")) {
                            selectionSortStep();
                        } else if (sortingType.equals("Bubble Sort")) {
                            bubbleSortStep();
                        }else if(sortingType.equals("Heap Sort")) {
                            heapSortStep() ;
                        }
                        drawStudents();
                        System.out.println(step);
                        step++;

                    }
                })
        );
        timeline.setCycleCount(StdNum);
        timeline.play();
    }

    private void shuffleStudents() {
        // Shuffle students array
        for (int i = 0; i < StdNum; i++) {
            int randomIndex = (int) (Math.random() * StdNum);
            Student temp = students[i];
            students[i] = students[randomIndex];
            students[randomIndex] = temp;
        }
        step = 0;
    }

    private void selectionSortStep() {
        int minIndex = step;
        for (int i = step + 1; i < StdNum; i++) {
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
        counter = visualizationStep+1;
        final int[] minIndex = {visualizationStep};
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> {

                    if(counter<StdNum){
                        drawStudentsComparing(visualizationStep, counter);
                        if (students[counter].getGrade() < students[minIndex[0]].getGrade()) {
                            minIndex[0] = counter;
                        }
                    }

                    System.out.println("before adding "+counter);
                    counter++;
                    System.out.println("after adding "+counter);

                    System.out.println("min Index "+ minIndex[0]);

                    if(counter > (StdNum)){

                        if(counter == (StdNum+1)){
                            drawStudentsComparingJustBeforeSwapping(visualizationStep, minIndex[0]);

                        }

                        if(counter == (StdNum+2)) {
                            Student temp = students[minIndex[0]];
                            students[minIndex[0]] = students[visualizationStep];
                            students[visualizationStep] = temp;
                            drawStudentsJustAfterSwapping(0, minIndex[0]);
                        }
                    }



                })
        );
        timeline.setCycleCount(StdNum+1);
        timeline.play();

//        if(counter == (ARRAY_SIZE+2)) {
//            visualizationStep += 1;
//        }

//        int minIndex = visualizationStep;
//        for (int i = visualizationStep + 1; i < ARRAY_SIZE; i++) {
//            if (students[i].getGrade() < students[minIndex].getGrade()) {
//                minIndex = i;
//            }
//        }
//        drawStudentsComparingJustBeforeSwapping(0,minIndex);
//
//        // Swap
//        Student temp = students[minIndex];
//        students[minIndex] = students[visualizationStep];
//        students[visualizationStep] = temp;
//
//        drawStudentsJustAfterSwapping(0, minIndex);

    }
    private void bubbleSortStep() {
        if (step < StdNum - 1) {
            for (int j = 0; j < StdNum - step - 1; j++) {
                if (students[j].getGrade() > students[j + 1].getGrade()) {
                    // Swap
                    Student temp = students[j];
                    students[j] = students[j + 1];
                    students[j + 1] = temp;
                }
            }
        }
    }
    /*
  private void cycleSortStep() {
        int writes = 0;

        // Traverse array elements and put them in the right place
        for (int cycleStart = 0; cycleStart < StdNum - 1; cycleStart++) {
            int item = students[cycleStart].getGrade();
            int pos = cycleStart;

            // Find position where we put the element which is currently at cycleStart
            for (int i = cycleStart + 1; i < StdNum; i++) {
                if (students[i].getGrade() < item) {
                    pos++;
                }
            }

            // If the item is already in the correct position, skip
            if (pos == cycleStart) {
                continue;
            }

            // Skip elements that are already sorted
            while (item == students[pos].getGrade()) {
                pos++;
            }

            // Move the item to its correct position
            if (pos != cycleStart) {
                int temp = students[pos].getGrade();
                students[pos].setGrade(item);
                item = temp;
                writes++;
            }

            // Rotate the rest of the cycle
            while (pos != cycleStart) {
                pos = cycleStart;
                for (int i = cycleStart + 1; i < StdNum; i++) {
                    if (students[i].getGrade() < item) {
                        pos++;
                    }
                }

                // Skip elements that are already sorted
                while (item == students[pos].getGrade()) {
                    pos++;
                }

                // Move the item to its correct position
                if (item != students[pos].getGrade()) {
                    int temp = students[pos].getGrade();
                    students[pos].setGrade(item);
                    item = temp;
                    writes++;
                }
            }

            // Update GUI
            drawStudents();
        }
    }
    //
    private void cycleSortStepVisualization() {
        counter = 0; // Initialize the counter

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> {
                    if (counter < StdNum - 1) {
                        int item = students[counter].getGrade();
                        int pos = counter;

                        // Find position to put the element
                        for (int i = counter + 1; i < StdNum; i++) {
                            if (students[i].getGrade() < item) {
                                pos++;
                            }
                        }

                        // If the item is already in the correct position, skip
                        if (pos == counter) {
                            counter++;
                            return;
                        }

                        // Skip elements that are already sorted
                        while (item == students[pos].getGrade()) {
                            pos++;
                        }

                        // Move the item to its correct position
                        if (pos != counter) {
                            Student temp = students[pos];
                            students[pos] = students[counter];
                            students[counter] = temp;
                            // Visualize comparison and swapping
                            drawStudentsComparingJustBeforeSwapping(counter, pos);
                            drawStudentsJustAfterSwapping(counter, pos);
                        }
                    }

                    counter++;
                })
        );
        timeline.setCycleCount(StdNum - 1); // We don't need to visualize the last step
        timeline.play();
    }

*/
    private void heapSortStep() {
        int minIndex = step;
        int left = 2 * step + 1;
        int right = 2 * step + 2;

        if (left < StdNum && students[left].getGrade() > students[minIndex].getGrade()) {
            minIndex = left;
        }

        if (right < StdNum && students[right].getGrade() > students[minIndex].getGrade()) {
            minIndex = right;
        }

        // Swap
        if (minIndex != step) {
            Student temp = students[minIndex];
            students[minIndex] = students[step];
            students[step] = temp;
            drawStudentsJustAfterSwapping(step, minIndex);
        }
    }

    private void heapSortStepVisualization() {
       int[] currentIndex = {visualizationStep};
      int[] minIndex = {currentIndex[0]};
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> {
                    if (currentIndex[0] < StdNum / 2) {
                        drawStudentsComparing(currentIndex[0], 2 * currentIndex[0] + 1);
                        drawStudentsComparing(currentIndex[0], 2 * currentIndex[0] + 2);
                        if (2 * currentIndex[0] + 1 < StdNum && students[2 * currentIndex[0] + 1].getGrade() > students[minIndex[0]].getGrade()) {
                            minIndex[0] = 2 * currentIndex[0] + 1;
                        }
                        if (2 * currentIndex[0] + 2 < StdNum && students[2 * currentIndex[0] + 2].getGrade() > students[minIndex[0]].getGrade()) {
                            minIndex[0] = 2 * currentIndex[0] + 2;
                        }
                        currentIndex[0]++;
                    } else {
                        if (currentIndex[0] == StdNum / 2 + 1) {
                            drawStudentsComparingJustBeforeSwapping(visualizationStep, minIndex[0]);
                        }
                        if (currentIndex[0] == StdNum / 2 + 2) {
                            heapSortStep();
                            drawStudentsJustAfterSwapping(visualizationStep, minIndex[0]);
                        }
                    }
                })
        );
        timeline.setCycleCount(StdNum / 2 + 2);
        timeline.play();
    }

}


