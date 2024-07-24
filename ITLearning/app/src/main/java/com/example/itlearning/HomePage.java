package com.example.itlearning;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomePage extends AppCompatActivity implements RecyclerViewInterface, TestViewInterface{

    Button logoutButton;
    FirebaseAuth mAuth;
    FirebaseUser user;
    RecyclerView courseRecyclerView, testRecyclerView;
    DatabaseReference databaseReference;
    public static ArrayList<Question> questions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        //accessing all the buttons and views
        logoutButton = findViewById(R.id.logout_button);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        courseRecyclerView = findViewById(R.id.horizontal_grid_view);
        courseRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        testRecyclerView = findViewById(R.id.horizontal_test_view);
        testRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        if (user == null)
        {
            Intent intent =new Intent(getApplicationContext(), LoginPage.class);
            startActivity(intent);
            finish();
        }else{

        }

        //arraylist to show course recycler view data from firebase
        final ArrayList<Course> courses = new ArrayList<>();
        final CourseAdapter adapter = new CourseAdapter(this, courses, this::courseOnItemClicked);
        courseRecyclerView.setAdapter(adapter);

        //arraylist to show test recycler view data from firebase
        final ArrayList<Test> tests = new ArrayList<>();
        final TestAdapter testAdapter = new TestAdapter(this, tests, this::testOnItemClicked);
        testRecyclerView.setAdapter(testAdapter);

        //Database of course
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Courses");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String courseName = String.valueOf(snapshot.child("courseName").getValue());
                    String courseVideoCount = String.valueOf(snapshot.child("courseVideoCount").getValue());
                    String courseDuration = String.valueOf(snapshot.child("courseDuration").getValue());
                    String imageUrl = String.valueOf(snapshot.child("imageUrl").getValue());
                    String courseId = String.valueOf(snapshot.child("courseId").getValue());

                    //for next page(Playlist)
                    String videoTitle = String.valueOf(snapshot.child("videoTitle").getValue());
                    String videoLink = String.valueOf(snapshot.child("videoLink").getValue());
                    courses.add(new Course(imageUrl, courseVideoCount, courseDuration, courseName, courseId, videoTitle, videoLink));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Database of test
        DatabaseReference testReference = FirebaseDatabase.getInstance().getReference("Tests");
        testReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                    Test obj = snapshot.getValue(Test.class);
                    tests.add(obj);
                }
                testAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //logging out using the logout button
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent =new Intent(getApplicationContext(), LoginPage.class);
                startActivity(intent);
                finish();
            }
        });
    }

    //click listener for the courses
    @Override
    public void courseOnItemClicked(Course course) {
        Intent intent = new Intent(this, PlaylistPage.class);
        intent.putExtra("course", course.getCourseId());
        intent.putExtra("videoTitle", course.getVideoTitle());
        intent.putExtra("videoLink", course.getVideoLink());
        startActivity(intent);
    }

    //click listener for the tests
    @Override
    public void testOnItemClicked(Test test) {
        //so that the prev data in questions array list gets deleted and then add new data
        questions.clear();
        addData(test.getTestId());
    }

    //Adding the data for mcq test in the next activity
    private void addData(String str){
        databaseReference = FirebaseDatabase.getInstance().getReference("MCQ").child(str);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Question question = snapshot.getValue(Question.class);
                    questions.add(question);
                }
                Intent intent = new Intent(HomePage.this, MCQTestPage.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}