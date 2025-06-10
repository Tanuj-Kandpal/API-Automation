package Test;

import JIRAAPI.LogIn;

public class BaseRun {

    public void base(){
        LogIn logIn = new LogIn();
        logIn.createSession();
    }
}
