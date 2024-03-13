package com.example.collegeschedulerapp.ui.courses;

import androidx.cardview.widget.CardView;

import com.example.collegeschedulerapp.Models.Courses;

public interface CourseClickListener {
    void onClick(Courses course);
    void onLongClick(Courses courses, CardView cardView);

    void onEditClick(Courses courses);

}
