package com.example.collegeschedulerapp.Database;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import static androidx.room.OnConflictStrategy.REPLACE;

import com.example.collegeschedulerapp.Models.Courses;

import java.util.List;

@Dao
public interface MainDAO {
    @Insert(onConflict = REPLACE)
    void insert(Courses courses);

    @Query("SELECT * FROM courses ORDER BY pinned DESC, id DESC")
    List<Courses> getAllCoursesOrdered();

    @Query("UPDATE courses SET course = :course, instructor = :instructor, details = :details, section = :section, location = :location, date = :date WHERE ID = :id")
    void update(int id, String course, String instructor, String details, String section, String location, String date);

    @Delete
    void delete(Courses courses);

    @Query("UPDATE courses SET pinned = :pin WHERE ID = :id")
    void pin(int id, boolean pin);


}
