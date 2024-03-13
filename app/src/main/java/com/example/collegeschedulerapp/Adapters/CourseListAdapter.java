package com.example.collegeschedulerapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegeschedulerapp.ui.courses.CourseClickListener;
import com.example.collegeschedulerapp.Models.Courses;
import com.example.collegeschedulerapp.R;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CourseListAdapter extends RecyclerView.Adapter<CourseViewHolder>{
    Context context;
    List<Courses> list;
    CourseClickListener listener;

    public CourseListAdapter(Context context, List<Courses> list, CourseClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CourseViewHolder(LayoutInflater.from(context).inflate(R.layout.courses_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        holder.textView_course.setText(list.get(position).getCourse());
        holder.textView_course.setSelected(true);

        holder.textView_instructor.setText(list.get(position).getInstructor());
        holder.textView_course.setSelected(true);

        holder.textView_course_details.setText(list.get(position).getDetails());
        holder.textView_course_details.setSelected(true);

        holder.textView_section.setText(list.get(position).getSection());
        holder.textView_section.setSelected(true);

        holder.textView_location.setText(list.get(position).getLocation());
        holder.textView_location.setSelected(true);

        holder.textView_date.setText(list.get(position).getDate());
        holder.textView_date.setSelected(true);

        if (list.get(position).isPinned()) {
            holder.imageView_pin.setImageResource(R.drawable.baseline_push_pin_24);
        } else {
            holder.imageView_pin.setImageResource(0);
        }


        int color_code = getRandomColor();
        holder.course_container.setCardBackgroundColor(holder.itemView.getResources().getColor(color_code, null));

        holder.course_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(list.get(holder.getAdapterPosition()));
            }
        });
        holder.course_container.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onLongClick(list.get(holder.getAdapterPosition()), holder.course_container);
                return true;
            }
        });

        holder.imageView_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onEditClick(list.get(holder.getAdapterPosition()));
            }
        });
    }

    private int getRandomColor() {
        List<Integer> colorCode = new ArrayList<>();
        colorCode.add(R.color.color1);
        colorCode.add(R.color.color2);
        colorCode.add(R.color.color3);
        colorCode.add(R.color.color4);
        colorCode.add(R.color.color5);
        colorCode.add(R.color.color6);

        Random random = new Random();
        int random_color = random.nextInt(colorCode.size());
        return colorCode.get(random_color);



    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public void filterList(List<Courses> filteredList) {
        list = filteredList;
        notifyDataSetChanged();
    }
}
class CourseViewHolder extends RecyclerView.ViewHolder {

    CardView course_container;
    TextView textView_course, textView_instructor, textView_course_details, textView_section, textView_location, textView_date;
    ImageView imageView_pin, imageView_back, imageView_edit;
    public CourseViewHolder(@NonNull View itemView) {
        super(itemView);
        course_container = itemView.findViewById(R.id.course_container);
        textView_course = itemView.findViewById(R.id.textView_course);
        textView_instructor = itemView.findViewById(R.id.textView_instructor);
        textView_course_details = itemView.findViewById(R.id.textView_course_details);
        textView_section = itemView.findViewById(R.id.textView_section);
        textView_location = itemView.findViewById(R.id.textView_location);
        textView_date = itemView.findViewById(R.id.textView_date);
        imageView_pin = itemView.findViewById(R.id.imageView_pin);
        imageView_back = itemView.findViewById(R.id.imageView_back);
        imageView_edit = itemView.findViewById(R.id.imageView_edit);
    }


}