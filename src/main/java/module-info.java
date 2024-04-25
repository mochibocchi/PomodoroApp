module com.example.pomodoroapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.pomodoroapp to javafx.fxml;
    exports com.example.pomodoroapp;
    exports com.example.pomodoroapp.Controller;
    opens com.example.pomodoroapp.Controller to javafx.fxml;
    exports com.example.pomodoroapp.Model;
    opens com.example.pomodoroapp.Model to javafx.fxml;
}