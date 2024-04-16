module com.example.pomodoroapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.pomodoroapp to javafx.fxml;
    exports com.example.pomodoroapp;
}