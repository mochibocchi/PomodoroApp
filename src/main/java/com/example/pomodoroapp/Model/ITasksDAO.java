package com.example.pomodoroapp.Model;

import java.util.List;

public interface ITasksDAO {
    void addTask(Study_Tasks study_tasks);
    List<Study_Tasks> getTasks(int accountId);
}
