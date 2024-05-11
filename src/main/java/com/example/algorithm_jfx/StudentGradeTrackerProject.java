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
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.Random;




public class StudentGradeTrackerProject extends Application {

    public static final int StdNum = 100;
    static Student[] students;
    HBox[] hBoxes;
    ObservableList<String> sortingName;
    ComboBox<String> sortingAlgorithmComboBox;
    Scene scene1;
    Scene scene2;
    Scene scene3;
    private static final int MAX_GRADE = 100;
    private HBox hbox;
    private int step;
    private int countStep = StdNum - 1;
    private int visualizationStep = 0;
    int counter;
    int[] inputArray = new int[StdNum];
    int[] countArray;
    int[] outputArray  = new int[StdNum];
    int sortedIndex = -1;
    private boolean sortingActive;
    private int RECTANGLE_WIDTH =10;
    public ScrollBar scrollBar;
    public ScrollBar scrollBar2;
    long startTime;
    long endTime;
    long executionTime;

 private static GridPane gridPane;
    private int frameCounter = 0;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        sortingName = FXCollections.observableArrayList("selection", "bubble", "count");
        students = new Student[StdNum];
        Random random =new Random();
        for (int i = 0; i < StdNum; i++) {
            students[i] = new Student("Student"+ (i+1) ,(int)(Math.random() * MAX_GRADE));
        }
        //Initializing countSort
            //input array initialization
            for (int i = 0; i < StdNum; i++) {
                inputArray[i] = students[i].getGrade();
            }
            // Find the maximum grade in the students array
            int maxGrade = Integer.MIN_VALUE;
            for (Student student : students) {
                if (student.getGrade() > maxGrade) {
                    maxGrade = student.getGrade();
                }
            }

            // Initialize count array
            countArray = new int[maxGrade + 1];
            // Count occurrences of each grade
            for (Student student : students) {
                countArray[student.getGrade()]++;
            }

            // Modify count array to contain actual position of each grade in output array
            for (int i = 1; i < countArray.length; i++) {
                countArray[i] += countArray[i - 1];
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
                    startTime = System.currentTimeMillis();

                    sortingActive = true;
                    startSorting("Selection Sort");


                    Button b1=new Button("back");
                    Button b2=new Button("selectionVisualization");
                    HBox hVis=new HBox(b1,b2);
                    hVis.setSpacing(10);
                    b1.setStyle("-fx-background-color: #D3D3D3;-fx-text-fill: Black;");

                    GridPane g1=new GridPane();
                    g1.getChildren().addAll(hbox);
                    g1.translateXProperty().bind(scrollBar2.valueProperty().negate());
                    g1.setAlignment(Pos.BOTTOM_CENTER);
                    BorderPane borderPane=new BorderPane();
                    borderPane.setCenter(g1);
                    borderPane.setTop(hVis);
                    borderPane.setBottom(scrollBar2);

                    g1.setOnScroll(event2 -> {
                        double deltaX = event2.getDeltaX()*0.5;
                        scrollBar2.setValue(scrollBar2.getValue() - deltaX);
                    });

                    primaryStage.setScene(scene2=new Scene(borderPane,1500,400));
                    double centerX = (1920 - primaryStage.getWidth()) / 2;
                    double centerY = (1080 - primaryStage.getHeight()) / 2;
                    primaryStage.setX(centerX);
                    primaryStage.setY(centerY);
                    primaryStage.show();



                    b1.setOnAction(actionEvent -> {
                        primaryStage.setScene(scene1);
                        primaryStage.setX(centerX+500);
                        primaryStage.setY(centerY-250);
                        DrawGridPane(gridPane);
                    });
                    b2.setOnAction(actionEvent -> {
                        sortingActive = false;
                        shuffleStudents();
                        DrawGridPane(gridPane);
                        drawStudents();
                        selectionSortStepVisualization();
                    });
                } else if (selectedItem.equals("bubble")) {
                     startTime = System.currentTimeMillis();
                    sortingActive = true;
                    startSorting("Bubble Sort");

                    Button b1=new Button("back");
                    Button b2=new Button("BubbleVisualization");
                    HBox hVis=new HBox(b1,b2);
                    hVis.setSpacing(10);
                    GridPane g1=new GridPane();
                    g1.getChildren().addAll(hbox);
                    g1.translateXProperty().bind(scrollBar2.valueProperty().negate());
                    g1.setAlignment(Pos.BOTTOM_CENTER);
                    BorderPane borderPane=new BorderPane();
                    borderPane.setCenter(g1);
                    borderPane.setTop(hVis);
                    borderPane.setBottom(scrollBar2);
                    g1.setOnScroll(event2 -> {
                        double deltaX = event2.getDeltaX()*0.5;
                        scrollBar2.setValue(scrollBar2.getValue() - deltaX);
                    });
                    primaryStage.setScene(scene2=new Scene(borderPane,1500,400));
                    double centerX = (1920 - primaryStage.getWidth()) / 2;
                    double centerY = (1080 - primaryStage.getHeight()) / 2;
                    primaryStage.setX(centerX);
                    primaryStage.setY(centerY);
                    primaryStage.show();
                    b1.setOnAction(actionEvent -> {
                        primaryStage.setScene(scene1);
                        primaryStage.setX(centerX+500);
                        primaryStage.setY(centerY-250);
                        DrawGridPane(gridPane);
                    });
                    b2.setOnAction(actionEvent -> {
                        sortingActive = false;
                        shuffleStudents();
                        DrawGridPane(gridPane);
                        drawStudents();
                        bubbleSortStepVisualization();
                    });
                } else if (selectedItem.equals("count")) {
                    startTime = System.currentTimeMillis();
                    sortingActive = true;
                    startSorting("Count Sort");

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

                    primaryStage.setScene(scene2=new Scene(borderPane,1500,400));
                    double centerX = (1920 - primaryStage.getWidth()) / 2;
                    double centerY = (1080 - primaryStage.getHeight()) / 2;
                    primaryStage.setX(centerX);
                    primaryStage.setY(centerY);
                    primaryStage.show();
                    b1.setOnAction(actionEvent -> {
                        primaryStage.setScene(scene1);
                        primaryStage.setX(centerX+500);
                        primaryStage.setY(centerY-250);
                        DrawGridPane(gridPane);
                    });
                }

            }else {
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

        scene1 = new Scene(root, 500, 800);
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



            Text text = new Text("" + student.getGrade());
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



            Text text = new Text(""+student.getGrade());
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



            Text text = new Text( ""+student.getGrade());
            text.setFill(Color.BLACK);
            StackPane stackPane = new StackPane(rectangle, text);
            stackPane.setAlignment(Pos.BOTTOM_CENTER);
            hbox.getChildren().add(stackPane);
        }
    }


    private void startSorting(String sortingType) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.01), e -> {
                    if (sortingActive && step < StdNum) {
                        if (sortingType.equals("Selection Sort")) {
                            selectionSortStep();
                        } else if (sortingType.equals("Bubble Sort")) {
                            bubbleSortStep();
                        }else if(sortingType.equals("Cycle Sort")) {
                            cycleSortStep();
                        }else if(sortingType.equals("Count Sort")) {
                            countSortStep();
                        }
                        drawStudents();
//                        System.out.println("step" + step);
//                        System.out.println("count step" + countStep);
                        step++;
                        countStep--;

                    }
                })
        );
        timeline.setCycleCount(StdNum); //O(n)
        timeline.play();
        timeline.setOnFinished(actionEvent -> {

            endTime = System.currentTimeMillis();
            executionTime = endTime - startTime;

            if (sortingType.equals("Count Sort")) {
                System.out.println("Execution time: " + executionTime + " milliseconds");
            } else {
                System.out.println("Execution time: " + executionTime + " milliseconds");
            }
        }) ;

        }


    private void shuffleStudents() {
        // Shuffle students array
        for (int i = 0; i < StdNum; i++) {
            int randomIndex = (int) (Math.random() * StdNum);
            Student temp = students[i];
            students[i] = students[randomIndex];
            students[randomIndex] = temp;
        }
        //input array initialization
        for (int i = 0; i < StdNum; i++) {
            inputArray[i] = students[i].getGrade();
        }
        // Find the maximum grade in the students array
        int maxGrade = Integer.MIN_VALUE;
        for (Student student : students) {
            if (student.getGrade() > maxGrade) {
                maxGrade = student.getGrade();
            }
        }

        // Initialize count array
        countArray = new int[maxGrade + 1];
        // Count occurrences of each grade
        for (Student student : students) {
            countArray[student.getGrade()]++;
        }

        // Modify count array to contain actual position of each grade in output array
        for (int i = 1; i < countArray.length; i++) {
            countArray[i] += countArray[i - 1];
        }
        step = 0;
        countStep = StdNum - 1;
        sortedIndex = -1;
    }

    private void selectionSortStep() {
        int minIndex = step;
        int i=0;
        for (i = step + 1; i < StdNum; i++) {
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
                new KeyFrame(Duration.seconds(0.1), e -> {

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
        counter = visualizationStep + 1;
        final int[] minIndex = {visualizationStep};

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> {
                    int writes = 0;
                    int item = students[visualizationStep].getGrade();
                    int pos = visualizationStep;

                    // Find position where we put the element which is currently at visualizationStep
                    for (int i = visualizationStep + 1; i < StdNum; i++) {
                        if (students[i].getGrade() < item) {
                            pos++;
                        }
                    }

                    // If the item is already in the correct position, skip
                    if (pos == visualizationStep) {
                        counter++;
                        return;
                    }

                    // Skip elements that are already sorted
                    while (item == students[pos].getGrade()) {
                        pos++;
                    }

                    // Move the item to its correct position
                    if (pos != visualizationStep) {
                        Student temp = students[pos];
                        students[pos] = new Student("Default Name", item); // Instantiate new Student with a default name
                        item = temp.getGrade();
                        writes++;
                    }

                    // Rotate the rest of the cycle
                    while (pos != visualizationStep) {
                        pos = visualizationStep;
                        for (int i = visualizationStep + 1; i < StdNum; i++) {
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
                            Student temp = students[pos];
                            students[pos] = new Student("Default Name", item); // Instantiate new Student with a default name
                            item = temp.getGrade();
                            writes++;
                        }
                    }

                    // Update GUI
                    drawStudents();

                    System.out.println("before adding " + counter);
                    counter++;
                    System.out.println("after adding " + counter);

                    System.out.println("min Index " + minIndex[0]);

                    // Perform visualization updates
                    if (counter > StdNum) {
                        if (counter == (StdNum + 1)) {
                            drawStudentsComparingJustBeforeSwapping(visualizationStep, minIndex[0]);
                        }

                        if (counter == (StdNum + 2)) {
                            Student temp = students[minIndex[0]];
                            students[minIndex[0]] = students[visualizationStep];
                            students[visualizationStep] = temp;
                            drawStudentsJustAfterSwapping(0, minIndex[0]);
                        }
                    }
                })
        );
        timeline.setCycleCount(StdNum + 1);
        timeline.play();
    }

    private void countSortStep() {

        // Fill output array with sorted grades
        outputArray[countArray[inputArray[countStep]] - 1] = inputArray[countStep];
        sortedIndex = countArray[inputArray[countStep]] - 1;
        countArray[inputArray[countStep]]--;


        Student temp = new Student("Student"+sortedIndex, outputArray[sortedIndex]);
        students[sortedIndex] = temp;

    }
    private void bubbleSortStepVisualization() {
        System.out.println("bubble sort visualization");

        counter = visualizationStep - 1;
        frameCounter = 0;
        final boolean[] swapping = {false};
        boolean[] swapped = {false};

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(.5), e -> {
                    if(frameCounter == 0){
                        counter++;
                    }
                    System.out.println("counter after adding "+counter);

                    if(counter < StdNum & frameCounter == 0){
                        drawStudentsComparing(counter + 1, counter);
                    }

                    if(frameCounter != 0){

                        if(frameCounter == 1){
                            drawStudentsComparingJustBeforeSwapping(counter + 1, counter);
                        }

                        if(frameCounter == 2) {
                            Student temp = students[counter];
                            students[counter] = students[counter + 1];
                            students[counter + 1] = temp;
                            drawStudentsJustAfterSwapping(counter + 1, counter);
                        }
                    }

                    System.out.println("Frame counter = "+frameCounter);

                    for(int i = 0; i < 1; i++){
                        if ((students[counter].getGrade() > students[counter + 1].getGrade()) & frameCounter == 0) {
                            frameCounter++;
                            break;
                        }

                        if(frameCounter == 1){
                            frameCounter++;
                            break;
                        }

                        if(frameCounter == 2){
                            frameCounter = 0;
                        }
                    }
                })
        );
        timeline.setCycleCount(StdNum * 3);
        timeline.play();
    }
}




