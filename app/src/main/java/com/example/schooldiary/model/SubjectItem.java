package com.example.schooldiary.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.example.schooldiary.utils.TypeConverterEnum;


@Entity(tableName = "Subjects")
public class SubjectItem {

    @PrimaryKey
    private String name;
    private String teacher;
    private String cab;

    @TypeConverters({TypeConverterEnum.class})
    private Subjects type;

    public SubjectItem(){}

    public SubjectItem(String name, String cab,String teacher, Subjects type){
        this.name = name;
        this.cab = cab;
        this.type = type;
        this.teacher = teacher;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
