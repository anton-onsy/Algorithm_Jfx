package com.example.algorithm_jfx;
import java.util.ArrayList;

public class studentGrades {
    ArrayList<Integer> grades;


    public  studentGrades (ArrayList<Integer> grades){
        this.grades=grades;
    }

    public ArrayList<Integer> getGrades() {
        return grades;
    }

    ///////////////////grades sorting Algorithm\\\\\\\\\\\\\\\\\\\\\\\
    //////////////////// selection sort\\\\\\\\\\\\\\\\\\\\
    public static void selectionSortG(ArrayList<Integer> grades) {
        int n = grades.size();
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (grades.get(j) < grades.get(minIndex)) {
                    minIndex = j;
                }
            }
            // Swap
            int temp = grades.get(i);
            grades.set(i, grades.get(minIndex));
            grades.set(minIndex, temp);
        }
    }
    //////////////////// Bubble sort\\\\\\\\\\\\\\\\\\\\
    public static void bubbleSortG(ArrayList<Integer> grades) {
        int n = grades.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (grades.get(j) > grades.get(j + 1)) {
                    // Swap
                    int temp = grades.get(j);
                    grades.set(j, grades.get(j + 1));
                    grades.set(j + 1, temp);
                }
            }
        }
    }

    ////////////////////////////WE CAN ADD MANY SORTING ALGORITHM AS WE LIKE \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
}