/** Module info for JavaFX */
module IJA{
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.base;
    requires java.xml;
    requires javafx.graphics;

    opens ija.ija2023.project to javafx.fxml;
    exports ija.ija2023.project.env to javafx.graphics;
}