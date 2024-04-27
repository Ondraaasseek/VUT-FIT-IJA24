module IJA{
    requires javafx.controls;
    requires javafx.fxml;

    opens ija.ija2023.project to javafx.fxml;
    exports ija.ija2023.project to javafx.graphics;
}