package com.example.collegeschedulerapp.ui.courses;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.collegeschedulerapp.Adapters.CourseListAdapter;
import com.example.collegeschedulerapp.Database.RoomDB;
import com.example.collegeschedulerapp.Models.Courses;
import com.example.collegeschedulerapp.R;
import com.example.collegeschedulerapp.databinding.FragmentCoursesBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.util.ArrayList;
import java.util.List;

public class CourseFragment extends Fragment implements PopupMenu.OnMenuItemClickListener {

    RecyclerView recyclerView;
    CourseListAdapter courseListAdapter;
    List<Courses> courses = new ArrayList<>();
    RoomDB database;
    FloatingActionButton fab_add;

    SearchView searchView_home;
    Courses selectedCourse;

    private FragmentCoursesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        CoursesViewModel coursesViewModel = new ViewModelProvider(this).get(CoursesViewModel.class);

        binding = FragmentCoursesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        recyclerView = root.findViewById(R.id.menu_courses_list);
        fab_add = root.findViewById(R.id.fab_add);
        searchView_home = root.findViewById(R.id.searchView_home);

        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CourseListActivity.class);
                startActivityForResult(intent, 101);
            }
        });

        searchView_home.setQueryHint("Search course or instructor...");
        searchView_home.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });

        searchView_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView_home.setIconified(false);
            }
        });


        // Initialize RecyclerView and fetch data from the database
        initRecyclerView();

        return root;
    }

    private void loadDataFromDatabase() {
        database = RoomDB.getInstance(requireContext());
        courses.clear();
        courses.addAll(database.mainDAO().getAllCoursesOrdered());
        updateRecycler(courses);
    }

    private void initRecyclerView() {
        // Fetch data from the database
        loadDataFromDatabase();

        // Set up RecyclerView
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
        courseListAdapter = new CourseListAdapter(requireContext(), courses, courseClickListener);
        recyclerView.setAdapter(courseListAdapter);
    }

    private void filter(String newText) {
        List<Courses> filteredList = new ArrayList<>();
        for (Courses singleCourse : courses) {
            if (singleCourse.getCourse().toLowerCase().contains(newText.toLowerCase())
                    || singleCourse.getInstructor().toLowerCase().contains(newText.toLowerCase())) {
                filteredList.add(singleCourse);
            }
        }
        courseListAdapter.filterList(filteredList);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 101) {
            if (resultCode == Activity.RESULT_OK) {
                Courses new_courses = (Courses) data.getSerializableExtra("course");
                database.mainDAO().insert(new_courses);
                courses.clear();
                courses.addAll(database.mainDAO().getAllCoursesOrdered());
                courseListAdapter.notifyDataSetChanged();
            }
        } else if (requestCode == 102) {
            if (resultCode == Activity.RESULT_OK) {
                Courses new_courses = (Courses) data.getSerializableExtra("course");
                database.mainDAO().update(new_courses.getID(), new_courses.getCourse(),
                        new_courses.getInstructor(), new_courses.getDetails(), new_courses.getSection(),
                        new_courses.getLocation(), new_courses.getDate());
                courses.clear();
                courses.addAll(database.mainDAO().getAllCoursesOrdered());
                courseListAdapter.notifyDataSetChanged();
            }
        }
    }

    private void updateRecycler(List<Courses> courses) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
        courseListAdapter = new CourseListAdapter(requireContext(), courses, courseClickListener);
        recyclerView.setAdapter(courseListAdapter);
    }

    private final CourseClickListener courseClickListener = new CourseClickListener() {
        @Override
        public void onClick(Courses courses) {
            Intent intent = new Intent(getActivity(), CourseListActivity.class);
            intent.putExtra("old_course", courses);
            startActivityForResult(intent, 102);
        }

        @Override
        public void onLongClick(Courses courses, CardView cardView) {
            selectedCourse = new Courses();
            selectedCourse = courses;
            showPopup(cardView);
        }

        @Override
        public void onEditClick(Courses courses) {
            showEditConfirmationDialog(courses);
        }
    };

    private void showPopup(CardView cardView) {
        PopupMenu popupMenu = new PopupMenu(requireContext(), cardView);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.show();

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.pin) {
            if (selectedCourse.isPinned()) {
                database.mainDAO().pin(selectedCourse.getID(), false);
                Toast.makeText(getContext(), "Unpinned!", Toast.LENGTH_SHORT).show();
            } else {
                database.mainDAO().pin(selectedCourse.getID(), true);
                Toast.makeText(getContext(), "Pinned!", Toast.LENGTH_SHORT).show();
            }

            List<Courses> allCourses = database.mainDAO().getAllCoursesOrdered();


            courses.clear();
            courses.addAll(allCourses);
            courseListAdapter.notifyDataSetChanged();
            return true;
        } else if (item.getItemId() == R.id.delete) {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("Confirm Deletion");
            builder.setMessage("Are you sure you want to delete this course?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // User clicked Yes, proceed with deletion
                    database.mainDAO().delete(selectedCourse);

                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            courses.clear();
                            courses.addAll(database.mainDAO().getAllCoursesOrdered());
                            courseListAdapter.notifyDataSetChanged();
                        }
                    });
                    Toast.makeText(requireContext(), "Course Deleted!", Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // User clicked No, do nothing or provide feedback
                    Toast.makeText(requireContext(), "Deletion Cancelled", Toast.LENGTH_SHORT).show();
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            return true;
        }
        return false;
    }
    private void showEditConfirmationDialog (Courses editedCourse){
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Confirm Edit");
        builder.setMessage("Are you sure you want to edit this course?");


        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // User clicked Yes, proceed with editing
                Intent intent = new Intent(getActivity(), CourseListActivity.class);
                intent.putExtra("old_course", editedCourse);
                startActivityForResult(intent, 102);
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // User clicked No, do nothing
                Toast.makeText(getContext(), "Edit Cancelled", Toast.LENGTH_SHORT).show();
            }
        });

        // Show the AlertDialog
        builder.show();

    }
}

