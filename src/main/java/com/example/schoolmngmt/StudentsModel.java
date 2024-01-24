package com.example.schoolmngmt;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class StudentsModel {

    private SimpleIntegerProperty studentId;
    private SimpleStringProperty studentName;
    private SimpleStringProperty className;
    public StudentsModel(Integer studentId, String studentName, String className) {
        this.studentId = new SimpleIntegerProperty(studentId);
        this.studentName = new SimpleStringProperty(studentName);
        this.className = new SimpleStringProperty(className);
    }

    public int getStudentId() {
        return studentId.get();
    }

    public void setStudentId(int studentId) {
        this.studentId = new SimpleIntegerProperty(studentId);
    }

    public String getStudentName() {
        return studentName.get();
    }

    public void setStudentName(String studentName) {
        this.studentName = new SimpleStringProperty(studentName);
    }

    public String getClassName() {
        return className.get();
    }

    public void setClassName(String className) {
        this.className = new SimpleStringProperty(className);
    }
}