module IJA{
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;
    requires javafx.base;
    requires java.xml;
    requires jdk.internal.le;

    opens ija.ija2023.project to javafx.fxml;
    exports ija.ija2023.project.env to javafx.graphics;
}