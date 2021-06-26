module graphics {
    requires javafx.controls;
    requires javafx.fxml;
    requires jfxutils;
    requires com.jfoenix;

    opens graph.app to javafx.fxml;
    opens graph.controllers to javafx.fxml;
    exports graph.app;
    exports graph.controllers;
    exports linear;
    exports slau.methods;
    exports slau.utils;
    exports expression;
    exports newton;
    exports newton.quasi;
    exports newton.utils;
}