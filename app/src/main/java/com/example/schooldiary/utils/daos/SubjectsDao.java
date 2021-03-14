package com.example.schooldiary.utils.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.schooldiary.model.SubjectItem;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.Subject;

import io.reactivex.Flowable;

@Dao
public interface SubjectsDao {

    @Query("SELECT * FROM Subjects")
    Flowable<List<SubjectItem>> getSubjects();

    @Insert
    void addSubject(SubjectItem subject);

    @Delete
    void deleteSubject(SubjectItem subject);
}
