package controllers;

public class Managers {

    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static TaskManager getDefaultHistory() {
        return null;
    }
}