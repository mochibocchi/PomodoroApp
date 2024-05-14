package com.example.pomodoroapp;

import com.example.pomodoroapp.Controller.LoginController;
import com.example.pomodoroapp.Controller.SessionController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

    public class HelloApplication extends Application {
        @Override
        public void start(Stage stage) throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("view/login.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 520, 400);
            stage.setTitle("Pomodoro Timer");
            stage.setScene(scene);
            stage.show();
            LoginController loginController = fxmlLoader.getController();
            SessionController sessionController = new SessionController();

        }

        public static void main(String[] args) {
            launch();
        }
    }
