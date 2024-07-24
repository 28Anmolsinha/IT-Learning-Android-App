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

import java.util.ArrayList;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.ViewHolder>{

    Context context;
    ArrayList<Test> testArrayList;
    TestViewInterface testViewInterface;

    public TestAdapter(Context context, ArrayList<Test> testArrayList, TestViewInterface testViewInterface){
        this.context = context;
        this.testArrayList = testArrayList;
        this.testViewInterface = testViewInterface;
    }
    @NonNull
    @Override
    public TestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.test_item, parent, false);
        TestAdapter.ViewHolder viewHolder =new TestAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TestAdapter.ViewHolder holder, int position) {
        holder.testTitle.setText(testArrayList.get(position).getTestTitle());

        //for setting up the onclick on the test item
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testViewInterface.testOnItemClicked(testArrayList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return testArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView testTitle;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            testTitle = itemView.findViewById(R.id.test_title);
            cardView = itemView.findViewById(R.id.test_item);
        }
    }
}
