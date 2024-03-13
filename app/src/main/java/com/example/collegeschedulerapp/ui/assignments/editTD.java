package com.example.collegeschedulerapp.ui.assignments;

import android.content.Intent;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.collegeschedulerapp.R;

public class editTD extends AppCompatActivity {
    EditText edtTaskname, edtDuedate, edtCourse;
    CheckBox edtStatus;
    Button bCancel, bSave;

    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_td);

        Intent intent = getIntent();

        linearLayout = findViewById(R.id.btn_holder);

        edtTaskname = findViewById(R.id.edt_edit_taskname);
        edtDuedate = findViewById(R.id.edt_edit_duedate);
        edtCourse = findViewById(R.id.edt_edit_course);
        edtStatus = findViewById(R.id.edt_edit_status);
        Boolean isComplete = edtStatus.isChecked();
        int status = isComplete ? 1 : 0;

        bCancel = findViewById(R.id.btnCancel);
        bSave = findViewById(R.id.btnSave);
        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean isComplete = edtStatus.isChecked();
                int status = isComplete ? 1 : 0;
                TD td = new TD(edtTaskname.getText().toString(),edtDuedate.getText().toString(),
                        edtCourse.getText().toString(),status);
                td.setId(intent.getIntExtra("id",1));
                if (new ToDOHandler(editTD.this).upd(td)){
                    Toast.makeText(editTD.this,"To Do Task Updated Successfully",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(editTD.this,"To Do Task Update Failed",Toast.LENGTH_SHORT).show();
                }
                onBackPressed();
            }
        });
        edtTaskname.setText(intent.getStringExtra("taskname"));
        edtDuedate.setText(intent.getStringExtra("duedate"));
        edtCourse.setText(intent.getStringExtra("course"));
        int taskstatus = intent.getIntExtra("status",-1);
        edtStatus.setChecked(taskstatus == 1);
    }

    @Override

    public void onBackPressed(){
        bSave.setVisibility(View.GONE);
        bCancel.setVisibility(View.GONE);
        TransitionManager.beginDelayedTransition(linearLayout);
        super.onBackPressed();
    }





}
