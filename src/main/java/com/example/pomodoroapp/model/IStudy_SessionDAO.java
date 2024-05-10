package com.example.pomodoroapp.Model;

import java.util.List;

public interface IStudy_SessionDAO {



    void addStudy_Session(Study_Session study_session);
    List<Study_Session> getStudySessions(int accountId);


}
