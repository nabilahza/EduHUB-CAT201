package com.example.schoolmngmt;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
public class SubjectModel {

    private final SimpleIntegerProperty subjectId;
    private final SimpleStringProperty subjectName;

    public SubjectModel(Integer subjectId, String subjectName ) {
        this.subjectId = new SimpleIntegerProperty(subjectId);
        this.subjectName = new SimpleStringProperty(subjectName);
    }
    public int getSubjectId() {
        return subjectId.get();
    }

    public void setSubjectId(int subjectId) {
        this.subjectId.set(subjectId);
    }

    public String getSubjectName() {
        return subjectName.get();
    }

    public void setSubjectName(String subjectName) {
        this.subjectName.set(subjectName);
    }

}
