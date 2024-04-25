package com.example.pomodoroapp.Controller;
import com.example.pomodoroapp.Model.TimerModel;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;
public class TimerController {
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