package com.example.pomodoroapp.Controller;
import com.example.pomodoroapp.HelloApplication;
import com.example.pomodoroapp.Model.*;
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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.util.List;


public class TimerController {
    public Button timerButton;

    @FXML
    private Button showSessionButton;
    @FXML
    private Button signoutButton;
    @FXML
    private Label timerLabel;
    @FXML
    public Button startPauseButton;
    @FXML
    private Button pomodoroButton;
    @FXML
    private Button shortBreakButton;
    @FXML
    private Button longBreakButton;
    @FXML
    private Button sessionButton;
    @FXML
    private Button GoToSessionButton;
    @FXML
    private final IAccountDAO accountDAO = new SqliteAccountDAO();
    @FXML
    private final IStudy_SessionDAO Study_SessionDAO = new SqliteStudy_SessionDAO();
    @FXML
    private Button settingsButton;

    @FXML
    private TextField totalTimeTextField;
    private TextField completedWorkTextField;
    private TextField sessionIdTextField;

    int totalSecondsElapsed;

    public final TimerModel model;
    private Timeline timeline;
    public boolean isRunning;
    private String formattedTime;
    private int pomodoroCountCycle;

    public TimerController() {
        this.model = new TimerModel();
        this.isRunning = false;
    }

    private void loadScene(String fxmlPath, ActionEvent event, int width, int height) {
        try {
            Parent root = FXMLLoader.load(HelloApplication.class.getResource(fxmlPath));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, width, height));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @FXML
    private void openSession(ActionEvent event) throws IOException {
        Stage stage = (Stage) GoToSessionButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("view/session.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 520, 400);
        stage.setScene(scene);
    }

    @FXML
    private void GoToTimer(ActionEvent event) throws IOException {
        loadScene("view/timer.fxml",event,520,400);
    }
    @FXML
    private void handleLoginButtonAction(ActionEvent event) throws IOException {
        loadScene("view/register.fxml",event,520,400);
    }

    @FXML
    private void openSettingsMenu() throws IOException {
        Stage stage = (Stage) settingsButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("view/settings.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 520, 400);
        stage.setScene(scene);
    }



    @FXML
    private void initialize() {
        pomodoroCountCycle = 0;
        updateTimerLabel();
        toggleHighlight(pomodoroButton);

        timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> {
                    if (model.getSeconds() > 0) {
                        model.setSeconds(model.getSeconds() - 1);
                        totalSecondsElapsed++;
                    } else if (model.getMinutes() > 0) {
                        model.setSeconds(59);
                        model.setMinutes(model.getMinutes() - 1);
                        totalSecondsElapsed++;
                    } else {
                        timeline.stop();
                    }
                    updateTimerLabel();
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
    }
    @FXML
    private void updateTimerLabel() {
        String minutes = String.format("%02d", model.getMinutes());
        String seconds = String.format("%02d", model.getSeconds());
        timerLabel.setText(minutes + ":" + seconds);

        if (isTimerFinished()) {
            goToNextMode();
        }
    }


    @FXML
    public void startPauseTimer() {
        if (!isRunning) {
            startTimer();
            startPauseButton.setText("Pause");
        } else {
            pauseTimer();
            startPauseButton.setText("Start");
            String formattedTime = displayTotalTime(totalSecondsElapsed);
            System.out.println("Total time elapsed: " + totalSecondsElapsed);
            AccountData.getInstance().addToTotalTimeElapsed(totalSecondsElapsed);
        }

        if (isTimerFinished()) {
            resetTimer();
        }
    }

    private boolean isTimerFinished() {
        return model.getMinutes() == 0 && model.getSeconds() == 0;
    }

    public void startTimer() {
        timeline.play();
        isRunning = true;
    }

    private void pauseTimer() {
        timeline.pause();
        isRunning = false;
        startPauseButton.setText("Start");
    }
    @FXML
    private String displayTotalTime(int totalSeconds) {
        int hours = totalSeconds / 3600;
        int minutes = (totalSeconds % 3600) / 60;
        int seconds = totalSeconds % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }


    @FXML
    public void resetTimer() {
        timeline.stop();
        startPauseButton.setText("Start");
        isRunning = false;
        TimerMode currentMode = model.getMode();
        updateTimerGUI(currentMode);

    }
    @FXML
    public void initPomodoro() {
        model.setMode(TimerMode.POMODORO);
        model.setMinutes(25);
        model.setSeconds(0);
    }

    @FXML
    public void initShortBreak() {
        model.setMode(TimerMode.SHORT_BREAK);
        model.setMinutes(5);
        model.setSeconds(0);
    }

    @FXML
    public void initLongBreak() {
        model.setMode(TimerMode.LONG_BREAK);
        model.setMinutes(10);
        model.setSeconds(0);
    }

    private void goToPomodoroMode() {
        initPomodoro();
        updateTimerLabel();
        toggleHighlight(pomodoroButton);
    }

    private void goToShortBreakMode() {
        initShortBreak();
        updateTimerLabel();
        toggleHighlight(shortBreakButton);
    }

    private void goToLongBreakMode() {
        initLongBreak();
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
        pauseTimer();
        updateTimerLabel();
    }

    private void goToNextMode() {

        TimerMode currentMode = model.getMode();

        switch (currentMode) {
            case POMODORO:
                pomodoroCountCycle++;
                if ((pomodoroCountCycle) % 4 == 0) {
                    goToLongBreakMode();
                }
                else {
                    goToShortBreakMode();
                }
                break;
            case SHORT_BREAK, LONG_BREAK:
                goToPomodoroMode();
                break;
        }
    }
    public void updateTimerGUI(TimerMode currentMode) {
        switch (currentMode) {
            case SHORT_BREAK:
                goToShortBreakMode();
                break;
            case LONG_BREAK:
                goToLongBreakMode();
                break;
            default:
                goToPomodoroMode();
                break;
        }
    }
    @FXML
    private void transitionToPomodoro() {
        updateTimerGUI(TimerMode.POMODORO);
    }
    @FXML
    private void transitionToShortBreak() {
        updateTimerGUI(TimerMode.SHORT_BREAK);
    }
    @FXML
    private void transitionToLongBreak() {
        updateTimerGUI(TimerMode.LONG_BREAK);
    }

    @FXML
    private void logout(ActionEvent event) {
        AccountData.getInstance().setAccountId(0);
        loadScene("view/login.fxml", event,520,400);
    }

    @FXML
    private void GoToSessions(ActionEvent event) {
        loadScene("view/ViewSessions.fxml", event,920, 720);
    }

    @FXML
    private void GoToTasklist(ActionEvent event) throws IOException {
        loadScene("view/tasklist.fxml",event,520,400);
    }
}