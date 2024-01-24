package com.example.schoolmngmt;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
public class ClassesModel {

    private  SimpleIntegerProperty classId;
    private SimpleStringProperty className;

    public ClassesModel(Integer classId, String className ) {
        this.classId = new SimpleIntegerProperty(classId);
        this.className = new SimpleStringProperty(className);
    }
    public int getClassId() {
        return classId.get();
    }

    public void setClassId(int classId) {
        this.classId = new SimpleIntegerProperty(classId);
    }

    public String getClassName() {
        return className.get();
    }

    public void setClassName(String className) {
        this.className = new SimpleStringProperty(className);
    }

}