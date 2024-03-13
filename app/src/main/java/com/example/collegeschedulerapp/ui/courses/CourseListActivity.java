package com.example.collegeschedulerapp.ui.courses;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.collegeschedulerapp.Models.Courses;
import com.example.collegeschedulerapp.R;


public class CourseListActivity extends AppCompatActivity {

    EditText editText_course, editText_instructor, editText_details, editText_section, editText_location, editText_date;
    ImageView imageView_save, imageView_back;
    Courses courses;
    boolean isOldCourse = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);

        imageView_save = findViewById(R.id.imageView_save);
        imageView_back = findViewById(R.id.imageView_back);
        editText_course = findViewById(R.id.editText_course);
        editText_instructor = findViewById(R.id.editText_instructor);
        editText_details = findViewById(R.id.editText_details);
        editText_section = findViewById(R.id.editText_section);
        editText_location = findViewById(R.id.editText_location);
        editText_date = findViewById(R.id.editText_date);

        courses = new Courses();

        try {
            courses = (Courses) getIntent().getSerializableExtra("old_course");
            editText_course.setText(courses.getCourse());
            editText_instructor.setText(courses.getInstructor());
            editText_details.setText(courses.getDetails());
            editText_section.setText(courses.getSection());
            editText_location.setText(courses.getLocation());
            editText_date.setText(courses.getDate());

            isOldCourse = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        imageView_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String course = editText_course.getText().toString();
                String instructor = editText_instructor.getText().toString();
                String details = editText_details.getText().toString();
                String section = editText_section.getText().toString();
                String location = editText_location.getText().toString();
                String date = editText_date.getText().toString();


                if (details.isEmpty() ) {
                    Toast.makeText(CourseListActivity.this, "Please add your meeting times!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (course.isEmpty()) {
                    Toast.makeText(CourseListActivity.this, "Please add your course!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (instructor.isEmpty()) {
                    Toast.makeText(CourseListActivity.this, "Please add instructor!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (section.isEmpty()) {
                    Toast.makeText(CourseListActivity.this, "Please add instructor!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (location.isEmpty()) {
                    Toast.makeText(CourseListActivity.this, "Please add a location!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (date.isEmpty()) {
                    Toast.makeText(CourseListActivity.this, "Please add a time and date!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!isOldCourse) {
                    courses = new Courses();
                }

                courses.setCourse(course);
                courses.setInstructor(instructor);
                courses.setDetails(details);
                courses.setSection(section);
                courses.setLocation(location);
                courses.setDate(date);

                Intent intent = new Intent();
                intent.putExtra("course", courses);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });
    }
}