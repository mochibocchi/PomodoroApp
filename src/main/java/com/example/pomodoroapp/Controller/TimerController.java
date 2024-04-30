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
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TimerController {
    @FXML
    public Button loginButton;
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
    private final IAccountDAO accountDAO = new SqliteAccountDAO();
    @FXML
    private final IStudy_SessionDAO Study_SessionDAO = new SqliteStudy_SessionDAO();
    @FXML
    private Button settingsButton;

    @FXML
    private TextField totalTimeTextField;
    private TextField completedWorkTextField;
    private int loggedInUserId;
    private int lastSessionId;

    int totalSecondsElapsed;

    public void setLoggedInUserId(int loggedInUserId) {
        this.loggedInUserId = loggedInUserId;
        lastSessionId = getLastSessionId();
    }

    public final TimerModel model;
    private Timeline timeline;
    public boolean isRunning;

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

    private void handleSessionButtonAction(ActionEvent event) {

    }
    @FXML
    private void addSession(ActionEvent event) {
        int sessionId = ++lastSessionId;
        int total_time = 21;
        String completedWork = "Stuff";
        Study_Session study_session = new Study_Session(loggedInUserId, sessionId, total_time, completedWork);
        Study_SessionDAO.addStudy_Session(study_session);
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
            System.out.println("Total time elapsed: " + formattedTime);
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
                initShortBreak();
                updateTimerLabel();
                toggleHighlight(shortBreakButton);
                break;
            case SHORT_BREAK:
                initLongBreak();
                updateTimerLabel();
                toggleHighlight(longBreakButton);
                break;
            case LONG_BREAK:
                initPomodoro();
                updateTimerLabel();
                toggleHighlight(pomodoroButton);
                break;
        }
    }
    public void updateTimerGUI(TimerMode currentMode) {
        switch (currentMode) {
            case SHORT_BREAK:
                initShortBreak();
                updateTimerLabel();
                toggleHighlight(shortBreakButton);
                break;
            case LONG_BREAK:
                initLongBreak();
                updateTimerLabel();
                toggleHighlight(longBreakButton);
                break;
            default:
                initPomodoro();
                updateTimerLabel();
                toggleHighlight(pomodoroButton);
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
    private int getLastSessionId() {
        int maxSessionId = 0;
        try {
            Connection connection = SqliteConnection.getInstance();
            String query = "SELECT MAX(sessionId) AS maxSessionId FROM study_sessions WHERE accountId = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, loggedInUserId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                maxSessionId = rs.getInt("maxSessionId");
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maxSessionId;
    }
}