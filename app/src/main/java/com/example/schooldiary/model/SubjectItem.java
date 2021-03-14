package com.example.schooldiary.model;

import androidx.room.Entity;

@Entity(tableName = "Subjects")
public class SubjectItem {
    private String name;
    private String notes;
    private String teacher;
    private String cab;
    private Subjects type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getCab() {
        return cab;
    }

    public void setCab(String cab) {
        this.cab = cab;
    }

    public Subjects getType() {
        return type;
    }

    public void setType(Subjects type) {
        this.type = type;
    }

    //сюда же добавим учителя
}
