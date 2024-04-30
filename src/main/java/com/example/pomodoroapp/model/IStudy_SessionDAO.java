package com.example.pomodoroapp.Model;

import java.util.List;

public interface IStudy_SessionDAO {

    public void addStudy_Session(Study_Session study_session);

    public Study_Session getStudy_Session(int id);

    public List<Study_Session> getStudy_Sessions();

}
