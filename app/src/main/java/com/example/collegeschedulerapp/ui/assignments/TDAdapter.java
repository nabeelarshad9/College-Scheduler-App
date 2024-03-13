package com.example.collegeschedulerapp.ui.assignments;


import android.content.Context;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegeschedulerapp.R;

import java.util.ArrayList;

public class TDAdapter extends RecyclerView.Adapter<TDAdapter.TDHolder> {

    ArrayList<TD> todos;
    Context context;
    ItemClicked itemClicked;
    ViewGroup parent;
    private int lsp =-1;
    public TDAdapter(ArrayList<TD> AL, Context context, ItemClicked itemClicked){
        todos = AL;
        this.context = context;
        this.itemClicked = itemClicked;
    }
    @NonNull
    @Override
    public TDHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.note_container_layout,parent,false);
        this.parent = parent;
        return new TDHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TDHolder holder, int position) {
        holder.taskname.setText(todos.get(position).getTaskname());
        holder.duedate.setText(todos.get(position).getDuedate());
        holder.course.setText(todos.get(position).getCourse());
        //holder.status.setText(todos.get(position).getStatus());
        int taskstatus = todos.get(position).getStatus();
        holder.statuschk.setChecked(taskstatus == 1);
    }

    @Override
    public int getItemCount() {
        return todos.size();
    }

    class TDHolder extends RecyclerView.ViewHolder{

        TextView taskname;
        TextView duedate;
        TextView course;
        CheckBox statuschk;
        ImageView imgEdit;
        public TDHolder(@NonNull final View itemView) {
            super(itemView);
            taskname = itemView.findViewById(R.id.txt_td_taskname);
            duedate = itemView.findViewById(R.id.txt_td_duedate);
            course = itemView.findViewById(R.id.txt_td_course);
            statuschk = itemView.findViewById(R.id.txt_td_status);
            Boolean isComplete = statuschk.isChecked();
            int status = isComplete ? 1 : 0;
//            title = itemView.findViewById(R.id.txt_td_taskname);


            imgEdit = itemView.findViewById(R.id.img_td_edit);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (duedate.getMaxLines() == 1){
                        duedate.setMaxLines(Integer.MAX_VALUE);
                    }
                    else {
                        duedate.setMaxLines(1);
                    }
                    notifyDataSetChanged();
                    TransitionManager.beginDelayedTransition(parent);
                }
            });

            imgEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClicked.onClick(getAdapterPosition(),itemView);
                }
            });
        }
    }
    public interface ItemClicked{
        void onClick(int position,View view);
    }
}

