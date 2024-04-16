module com.example.pomodoroapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.pomodoroapp to javafx.fxml;
    exports com.example.pomodoroapp;
    exports com.example.pomodoroapp.controller;
    opens com.example.pomodoroapp.controller to javafx.fxml;
}