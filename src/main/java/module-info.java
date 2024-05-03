/** Module info for JavaFX */
module IJA{
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.base;
    requires java.xml;
    requires javafx.graphics;

    // Open and export all packages
    opens ija.ija2023.project;
    opens ija.ija2023.project.env;
    opens ija.ija2023.project.env.history;
    opens ija.ija2023.project.common;
    opens ija.ija2023.project.common.robot;
    opens ija.ija2023.project.room;

    exports ija.ija2023.project;
    exports ija.ija2023.project.env;
    exports ija.ija2023.project.env.history;
    exports ija.ija2023.project.common;
    exports ija.ija2023.project.common.robot;
    exports ija.ija2023.project.room;
}