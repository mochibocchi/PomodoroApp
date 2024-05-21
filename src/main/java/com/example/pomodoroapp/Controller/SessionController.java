package com.example.pomodoroapp.Controller;
import com.example.pomodoroapp.HelloApplication;
import com.example.pomodoroapp.Model.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SessionController {
    public Button TimerButton;
    public Button sessionButton;
    private final IAccountDAO accountDAO = new SqliteAccountDAO();
    private final IStudy_SessionDAO Study_SessionDAO = new SqliteStudy_SessionDAO();
    public TableView sessionTable;
    public Button ViewSessions;
    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private AnchorPane chartPane;
    @FXML
    private TextField totalTimeTextField;
    @FXML
    private TextField completedWorkTextField;
    @FXML
    private TextField sessionIdTextField;
    private int loggedInUserId;

    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private ComboBox<String> moodComboBox;

    public void setLoggedInUserId(int loggedInUserId) {
        this.loggedInUserId = loggedInUserId;
    }

    public int getLoggedInUserId() {
        return AccountData.getInstance().getAccountId();
    }

    @FXML
    public void initialize() {
        if (moodComboBox != null) {
            moodComboBox.getItems().addAll(
                    "Very Bad",
                    "Bad",
                    "Poor",
                    "Below Average",
                    "Average",
                    "Above Average",
                    "Good",
                    "Very Good",
                    "Excellent",
                    "Amazing"
            );
            moodComboBox.setValue("Average");
        }
    }




    public void addSession(ActionEvent event) throws IOException {

        int accountId = getLoggedInUserId();
        int total_time = AccountData.getInstance().getTotalTimeElapsed();
        LocalDateTime current_date = LocalDateTime.now();
        long session_date = current_date.toEpochSecond(java.time.ZoneOffset.UTC);
        String completedWork = completedWorkTextField.getText();
        int mood = Study_Session.getMood(moodComboBox.getValue());
        Study_Session study_session = new Study_Session(accountId, total_time, session_date,completedWork,mood);
        Study_SessionDAO.addStudy_Session(study_session);
        loadScene("view/timer.fxml", event, 520, 400);
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
    public void GoToTimer(ActionEvent event) throws IOException {
        loadScene("view/timer.fxml",event, 520, 400);
    }

    private void showBarGraph(List<Study_Session> sessions) {
        barChart.getData().clear();
        xAxis.setLabel("Date");
        yAxis.setLabel("Value");

        if (!sessions.isEmpty()) {
            XYChart.Series<String, Number> timeSeries = new XYChart.Series<>();
            timeSeries.setName("Time Spent (s)");

            XYChart.Series<String, Number> moodSeries = new XYChart.Series<>();
            moodSeries.setName("Mood");

            for (Study_Session session : sessions) {
                LocalDateTime sessionDate = LocalDateTime.ofEpochSecond(session.getSessionDate(), 0, java.time.ZoneOffset.UTC);
                String formattedDate = sessionDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
                XYChart.Data<String, Number> timeData = new XYChart.Data<>(formattedDate, session.getTotalTime());
                timeData.setNode(createDataNode(session.getTotalTime()));
                timeSeries.getData().add(timeData);
                XYChart.Data<String, Number> moodData = new XYChart.Data<>(formattedDate, session.getMood());
                moodData.setNode(createDataNode(session.getMood()));
                moodSeries.getData().add(moodData);
            }

            barChart.getData().addAll(timeSeries, moodSeries);
        }
    }

    private Node createDataNode(Number value) {
        Text text = new Text(String.valueOf(value));
        text.setStyle("-fx-font-size: 12px;");
        StackPane pane = new StackPane();
        pane.getChildren().add(text);
        pane.setAlignment(Pos.TOP_CENTER);
        StackPane.setMargin(text, new Insets(-15, 0, 0, 0));
        return pane;
    }

    @FXML
    public void showSessions() {
        int accountId = getLoggedInUserId();
        List<Study_Session> sessions = Study_SessionDAO.getStudySessions(accountId);
        ObservableList<Study_Session> sessionList = FXCollections.observableArrayList(sessions);
        sessionTable.getItems().clear();
        sessionTable.setItems(sessionList);
        sessionTable.setVisible(true);
        TableColumn<Study_Session, Integer> sessionIdColumn = new TableColumn<>("Session ID");
        sessionIdColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getSessionId()).asObject());

        TableColumn<Study_Session, Integer> totalTimeColumn = new TableColumn<>("Total Time(s)");
        totalTimeColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getTotalTime()).asObject());

        TableColumn<Study_Session, String> sessionDateColumn = new TableColumn<>("Session Date");
        sessionDateColumn.setCellValueFactory(cellData -> {
            LocalDateTime sessionDate = LocalDateTime.ofEpochSecond(cellData.getValue().getSessionDate(), 0, java.time.ZoneOffset.UTC);
            return new SimpleStringProperty(sessionDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        });

        TableColumn<Study_Session, String> completedWorkColumn = new TableColumn<>("Completed Work");
        completedWorkColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCompletedWork()));

        TableColumn<Study_Session, Integer> moodColumn = new TableColumn<>("Mood");
        moodColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getMood()).asObject());

        sessionTable.getColumns().setAll(sessionIdColumn, totalTimeColumn, sessionDateColumn, completedWorkColumn, moodColumn);
        showBarGraph(sessions);
    }




}
