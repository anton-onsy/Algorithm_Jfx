module com.example.algorithm_jfx {
    requires javafx.controls;
    requires javafx.fxml;
            
            requires com.dlsc.formsfx;
                        
    opens com.example.algorithm_jfx to javafx.fxml;
    exports com.example.algorithm_jfx;
}