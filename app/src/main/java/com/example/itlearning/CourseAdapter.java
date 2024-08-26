package com.example.itlearning;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder>{

    Context context;
    ArrayList<Course> courseArrayList;
    RecyclerViewInterface recyclerViewInterface;
    public CourseAdapter(Context context, ArrayList<Course> courseArrayList, RecyclerViewInterface recyclerViewInterface){
        this.context = context;
        this.courseArrayList = courseArrayList;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.course_grid_item, parent, false);
        ViewHolder viewHolder =new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.courseVideoCount.setText(courseArrayList.get(position).getCourseVideoCount());
        holder.courseDuration.setText(courseArrayList.get(position).getCourseDuration());
        holder.courseName.setText(courseArrayList.get(position).getCourseName());
        //for setting up the image
        Glide.with(holder.image.getContext())
                .load(courseArrayList.get(position).getImageResourceId())
                .error(R.drawable.commonimagetoshow)
                .into(holder.image);

        //for setting up the onclick on the course item
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewInterface.courseOnItemClicked(courseArrayList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return courseArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView courseVideoCount, courseDuration, courseName;
        ImageView image;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            courseVideoCount = itemView.findViewById(R.id.course_video_count);
            courseDuration = itemView.findViewById(R.id.course_duration);
            courseName = itemView.findViewById(R.id.course_name);
            image = itemView.findViewById(R.id.course_image);
            cardView = itemView.findViewById(R.id.course_item);
        }
    }
}
