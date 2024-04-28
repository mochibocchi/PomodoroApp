package com.example.pomodoroapp.Controller;
import com.example.pomodoroapp.HelloApplication;
import com.example.pomodoroapp.Model.TimerModel;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class TimerController {
    @FXML
    public Button loginButton;
    @FXML
    private Label timerLabel;
    @FXML
    private Button startPauseButton;

    private final TimerModel model;
    private Timeline timeline;
    private boolean isRunning;

    public TimerController() {
        this.model = new TimerModel();
        this.isRunning = false;
    }

    private Parent loadFXML(String fxmlPath) throws IOException {
        URL url = getClass().getResource(fxmlPath);
        if (url == null) {
            throw new RuntimeException("Failed to load FXML file. URL is null for " + fxmlPath);
        }
        FXMLLoader loader = new FXMLLoader(url);
        return loader.load();
    }

    @FXML
    private void handleLoginButtonAction(ActionEvent event) {
            try {
                // Change the scene to login.fxml
                Parent loginRoot = FXMLLoader.load(HelloApplication.class.getResource("view/register.fxml"));
                Scene scene = new Scene(loginRoot);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }


    @FXML
    private void initialize() {
        updateTimerLabel();

        timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> {
                    if (model.getSeconds() > 0) {
                        model.setSeconds(model.getSeconds() - 1);
                    } else if (model.getMinutes() > 0) {
                        model.setSeconds(59);
                        model.setMinutes(model.getMinutes() - 1);
                    } else {
                        timeline.stop(); // finish timer
                    }
                    updateTimerLabel();
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    private void updateTimerLabel() {
        String minutes = String.format("%02d", model.getMinutes());
        String seconds = String.format("%02d", model.getSeconds());
        timerLabel.setText(minutes + ":" + seconds);

        if (isTimerFinished())
        {
            startPauseButton.setText("Start");
            isRunning = false;
            timeline.stop();
        }
    }

    @FXML
    private void startPauseTimer(ActionEvent event) {
        if (!isRunning) {
            startTimer();
            startPauseButton.setText("Pause");
        } else {
            pauseTimer();
            startPauseButton.setText("Start");
        }

        if (isTimerFinished()) {
            resetTimer(event);
        }
    }

    private boolean isTimerFinished() {
        return model.getMinutes() == 0 && model.getSeconds() == 0;
    }

    private void startTimer() {
        timeline.play();
        isRunning = true;
    }

    private void pauseTimer() {
        timeline.pause();
        isRunning = false;
    }

    @FXML
    private void resetTimer(ActionEvent event) {
        timeline.stop();
        model.setMinutes(25);
        model.setSeconds(0);
        updateTimerLabel();
        startPauseButton.setText("Start");
        isRunning = false;
    }
}