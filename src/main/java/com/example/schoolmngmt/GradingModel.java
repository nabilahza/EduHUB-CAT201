package com.example.schoolmngmt;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class GradingModel {

    private SimpleIntegerProperty gradingId;
    private SimpleStringProperty studentName;

        private SimpleStringProperty subject;
    private SimpleStringProperty testName;
    private SimpleStringProperty grade;
    public GradingModel(Integer gradingId,  String subject,String studentName,String testName,String grade) {
        this.gradingId = new SimpleIntegerProperty(gradingId);
        this.studentName = new SimpleStringProperty(studentName);
        this.subject = new SimpleStringProperty(subject);
        this.testName = new SimpleStringProperty(testName);
        this.grade = new SimpleStringProperty(grade);
    }

    public int getGradingId() {
        return gradingId.get();
    }

    public void setGradingId(int gradingId) {
        this.gradingId = new SimpleIntegerProperty(gradingId);
    }

    public String getStudentName() {
        return studentName.get();
    }

    public void setStudentName(String studentName) {
        this.studentName = new SimpleStringProperty(studentName);
    }

    public String getSubject() {
        return subject.get();
    }

    public void setSubject(String subject) {this.subject = new SimpleStringProperty(subject); }

    public String getTestName() {
        return testName.get();
    }

    public void setTestName(String testName) {
        this.testName = new SimpleStringProperty(testName);
    }
    public String getGrade() {
        return grade.get();
    }

    public void setGrade(String grade) {
        this.grade = new SimpleStringProperty(grade);
    }

}