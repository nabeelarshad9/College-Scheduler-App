package com.example.collegeschedulerapp.ui.assignments;

public class TD {

    private int id;
    private String taskname;
    private String duedate;

    private String course;
    private int status;

    public TD(String taskname, String duedate, String course, int status) {
        this.taskname = taskname;
        this.duedate = duedate;
        this.course = course;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getTaskname() {
        return taskname;
    }

    public String getDuedate() {
        return duedate;
    }

    public String getCourse() {
        return course;
    }

    public int getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }

    public void setDuedate(String duedate) {
        this.duedate = duedate;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
