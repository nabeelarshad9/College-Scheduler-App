package com.example.collegeschedulerapp.ui.assignments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegeschedulerapp.R;
import com.example.collegeschedulerapp.databinding.FragmentAssignmentsBinding;
import com.example.collegeschedulerapp.databinding.FragmentToDoListBinding;
import com.example.collegeschedulerapp.ui.assignments.AssignmentsViewModel;
import com.example.collegeschedulerapp.ui.todolist.ToDoListViewModel;

import java.util.ArrayList;

public class AssignmentsFragment extends Fragment {
    ImageButton imgButton, bTask, bStatus, bCourse, bDuedate;
    ArrayList<TD> tds;
    RecyclerView recycleView;
    TDAdapter tdAdapter;
    int sortBy = 0;
    private FragmentAssignmentsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        ToDoListViewModel toDoListViewModel =
                new ViewModelProvider(this).get(ToDoListViewModel.class);

        binding = FragmentAssignmentsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textTodolist;
        toDoListViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
//        bStatus = binding.btnStatus;
//        bCourse = binding.btnCourse;
//        bDuedate = binding.btnDuedate;
//        bTask = binding.btnTaskname;
//
////        if (bStatus != null) {
////            Toast.makeText(requireContext(), "bStatus not null"+String.valueOf(sortBy), Toast.LENGTH_SHORT).show();
////        }
////        else{
////            Toast.makeText(requireContext(), "bStatus is null", Toast.LENGTH_SHORT).show();
////        }
//        bStatus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                loadTds(1);
//                //sortBy = 1;
//            }
//        });
//        bCourse.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                loadTds(2);
//                //sortBy = 2;
//            }
//        });
//
//        bDuedate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                loadTds(3);
//                //sortBy = 3;
//            }
//        });
//        bTask.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                loadTds(4);
//                //sortBy = 4;
//                //Log.d("ButtonClick", "Clicked on Task - "+sortBy);
//                //Toast.makeText(MainActivity.this,String.valueOf(sortBy),Toast.LENGTH_SHORT).show();
//            }
//        });
        imgButton = binding.imageButton;

        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflatr = LayoutInflater.from(requireContext());
//                binding = FragmentToDoListBinding.inflate(inflater, container, false);
//                binding = FragmentToDoListBinding.inflate(inflater, container, false);
                //View root = binding.getRoot();
//                View viewInput = binding.getRoot();
                View viewInput = inflatr.inflate(R.layout.as_input_layout,null,false);
                EditText inTaskname = viewInput.findViewById(R.id.in_taskname);
                EditText inDuedate = viewInput.findViewById(R.id.in_duedate);
                EditText inCourse = viewInput.findViewById(R.id.in_course);
                CheckBox inStatus = viewInput.findViewById(R.id.in_status);

                new AlertDialog.Builder(requireContext())
                        .setView(viewInput)
                        .setTitle("Add Assignment")
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String taskname = inTaskname.getText().toString();
                                String duedate = inDuedate.getText().toString();
                                String course = inCourse.getText().toString();
                                Boolean isComplete = inStatus.isChecked();
                                int status = isComplete ? 1 : 0;

                                TD td = new TD(taskname,duedate,course,status);
                                boolean isInserted = new ToDOHandler(requireContext()).create(td);
                                if (isInserted){
                                    Toast.makeText(requireContext(),"Assignment Saved Successfully",Toast.LENGTH_SHORT).show();
                                    loadTds(0);
                                }
                                else {
                                    Toast.makeText(requireContext(),"Assignment could not be Saved",Toast.LENGTH_SHORT).show();
                                }
                                dialog.cancel();
//                                dialogInterface.cancel();
                            }
                        }).show();
            }
        });
        recycleView = binding.recycler;
        recycleView.setLayoutManager(new LinearLayoutManager(requireContext()));
        ItemTouchHelper.SimpleCallback itc = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                new ToDOHandler(requireContext()).del(tds.get(viewHolder.getAdapterPosition()).getId());
                tds.remove(viewHolder.getAdapterPosition());
                tdAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        };
        ItemTouchHelper ITH = new ItemTouchHelper(itc);
        ITH.attachToRecyclerView(recycleView);

        loadTds(0);


        return root;
    }

    public ArrayList<TD> readTds(int sortby){
        //Toast.makeText(MainActivity.this,"readtds 1",Toast.LENGTH_SHORT).show();
        ArrayList<TD> tds = new ToDOHandler(requireContext()).readtds(sortby);
        //Toast.makeText(MainActivity.this,"readtds 2",Toast.LENGTH_SHORT).show();
        return tds;
    }
    public void loadTds(int sortby){
        //Toast.makeText(MainActivity.this,String.valueOf(sortby),Toast.LENGTH_SHORT).show();
        tds = readTds(sortby);
        //Toast.makeText(MainActivity.this,"Test 2",Toast.LENGTH_SHORT).show();
        tdAdapter = new TDAdapter(tds, requireContext(), new TDAdapter.ItemClicked() {
            @Override
            public void onClick(int position, View view) {
                edittd(tds.get(position).getId(),view);
            }
        });
        recycleView.setAdapter(tdAdapter);
    }
    private void edittd(int tdid, View view){
        ToDOHandler todoHandler = new ToDOHandler(requireContext());
//        Toast.makeText(requireContext(),"before",Toast.LENGTH_SHORT).show();
        TD td = todoHandler.readatd(tdid);
//        Toast.makeText(requireContext(),"after",Toast.LENGTH_SHORT).show();
//        Toast.makeText(requireContext(),td.getTaskname(),Toast.LENGTH_SHORT).show();
        //Toast.makeText(MainActivity.this,String.valueOf(td.getStatus()),Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(requireContext(),editTD.class);
        intent.putExtra("taskname",td.getTaskname());
        intent.putExtra("duedate",td.getDuedate());
        intent.putExtra("course",td.getCourse());
        intent.putExtra("status",td.getStatus());
        intent.putExtra("id",td.getId());
//        Toast.makeText(requireContext(),"before..1",Toast.LENGTH_SHORT).show();
//        Log.d("Error on transition", String.valueOf(view));
        ActivityOptionsCompat oc = ActivityOptionsCompat.makeSceneTransitionAnimation(requireActivity(),view, ViewCompat.getTransitionName(view));
//        Log.d("Error on transition", String.valueOf(view));
//        Toast.makeText(requireContext(),"before..11",Toast.LENGTH_SHORT).show();
        startActivityForResult(intent,1,oc.toBundle());
//        Toast.makeText(requireContext(),"after..1",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == 1){
            loadTds(0);
        }
    }


}