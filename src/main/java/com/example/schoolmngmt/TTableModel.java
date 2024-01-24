package com.example.schoolmngmt;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class TTableModel {

    private SimpleIntegerProperty tableId;
    private SimpleStringProperty subjectName;
    private SimpleStringProperty className;
    private SimpleStringProperty day;
    private SimpleStringProperty subjectTime;
    private SimpleStringProperty location;
    public TTableModel( Integer tableId,String subjectName,String className, String day, String subjectTime, String location) {

        this.tableId= new SimpleIntegerProperty(tableId);
        this.subjectName = new SimpleStringProperty(subjectName);
        this.className = new SimpleStringProperty(className);
        this.day = new SimpleStringProperty(day);
        this.subjectTime = new SimpleStringProperty(subjectTime);
        this.location = new SimpleStringProperty(location);
    }

    public int getTableId(){return tableId.get();}

    public void setTableId(int tableId) { this.tableId= new SimpleIntegerProperty(tableId);}

    public String getSubjectName() {
        return subjectName.get();
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = new SimpleStringProperty(subjectName);
    }

    public String getDay() {
        return day.get();
    }

    public void setDay(String day) {
        this.day = new SimpleStringProperty(day);
    }

    public String getSubjectTime() {
        return subjectTime.get();
    }

    public void setSubjectTime(String subjectTime) {
        this.subjectTime = new SimpleStringProperty(subjectTime);
    }
    public String getClassName() {
        return className.get();
    }

    public void setClassName(String className) {
        this.className = new SimpleStringProperty(className);
    }

    public String getLocation() {
        return location.get();
    }

    public void setLocation(String location) {
        this.location = new SimpleStringProperty(location);
    }
}