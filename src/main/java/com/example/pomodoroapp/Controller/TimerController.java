package com.example.pomodoroapp.Controller;
import com.example.pomodoroapp.Model.TimerMode;
import com.example.pomodoroapp.Model.TimerModel;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;
public class TimerController {
    @FXML
    private Label timerLabel;
    @FXML
    private Button startPauseButton;
    @FXML
    private Button pomodoroButton;
    @FXML
    private Button shortBreakButton;
    @FXML
    private Button longBreakButton;


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
        toggleHighlight(pomodoroButton);
        timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> {
                    if (model.getSeconds() > 0) {
                        model.setSeconds(model.getSeconds() - 1);
                    } else if (model.getMinutes() > 0) {
                        model.setSeconds(59);
                        model.setMinutes(model.getMinutes() - 1);
                    }
                    else {
                        timeline.stop();
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
            nextMode();
//            startPauseButton.setText("Start");
//            isRunning = false;
//            timeline.stop();
        }
    }

    @FXML
    private void startPauseTimer() {
        if (!isRunning) {
            startTimer();
            startPauseButton.setText("Pause");
        } else {
            pauseTimer();
            startPauseButton.setText("Start");
        }

        if (isTimerFinished()) {
            resetTimer();
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
    private void resetTimer() {
        timeline.stop();
        startPauseButton.setText("Start");
        isRunning = false;
        TimerMode currentMode = model.getMode();
        setTimerMode(currentMode);

    }
    private void setTimerMode(TimerMode currentMode) {
        switch (currentMode) {
            case SHORT_BREAK:
                setShortBreak();
                break;
            case LONG_BREAK:
                setLongBreak();
                break;
            default:
                setPomodoro();
                break;
        }
    }
    @FXML
    private void setPomodoro() {
        model.setMode(TimerMode.POMODORO);
        model.setMinutes(25);
        model.setSeconds(0);
        updateTimerLabel();
        toggleHighlight(pomodoroButton);
    }

    @FXML
    private void setShortBreak() {
        model.setMode(TimerMode.SHORT_BREAK);
        model.setMinutes(5);
        model.setSeconds(0);
        updateTimerLabel();
        toggleHighlight(shortBreakButton);
    }

    @FXML
    private void setLongBreak() {
        model.setMode(TimerMode.LONG_BREAK);
        model.setMinutes(10);
        model.setSeconds(0);
        updateTimerLabel();
        toggleHighlight(longBreakButton);
    }

    private void toggleHighlight (Button button) {
        pomodoroButton.getStyleClass().remove("highlight-button");
        shortBreakButton.getStyleClass().remove("highlight-button");
        longBreakButton.getStyleClass().remove("highlight-button");
        button.getStyleClass().add("highlight-button");
    }

    @FXML
    private void skipTimer() {
        model.setMinutes(0);
        model.setSeconds(0);
        updateTimerLabel();

        if (!isRunning) {
            startPauseButton.setText("Pause");
        } else {
            pauseTimer();
            startPauseButton.setText("Start");
        }
    }

    private void nextMode() {

        TimerMode currentMode = model.getMode();

        switch (currentMode) {
            case POMODORO:
                setShortBreak();
                break;
            case SHORT_BREAK:
                setLongBreak();
                break;
            case LONG_BREAK:
                setPomodoro();
                break;
        }
    }
    @FXML
    private void openSettingsMenu() {

    }
}