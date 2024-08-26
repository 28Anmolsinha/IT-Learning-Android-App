package com.example.itlearning;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;

public class PlaylistPage extends AppCompatActivity {

    RecyclerView playlistRecyclerView;
    String youtubeVideoLink, youtubeVideoTitle;
    TextView title;
    YouTubePlayerView youTubePlayerView;
    Intent prevIntent;
    Button fullScreenButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist_page);

        title = findViewById(R.id.current_video_title);
        playlistRecyclerView = findViewById(R.id.playlist_recycler_view);
        playlistRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        fullScreenButton = findViewById(R.id.full_screen_button);

        final ArrayList<Playlist> playlist = new ArrayList<>();
        final PlaylistAdapter adapter = new PlaylistAdapter(this, playlist, this::playlistOnItemClicked);
        playlistRecyclerView.setAdapter(adapter);

        //previous page info
        prevIntent = getIntent();
        youtubeVideoTitle = prevIntent.getStringExtra("videoTitle");
        title.setText(youtubeVideoTitle);
        youtubeVideoLink = prevIntent.getStringExtra("videoLink");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Playlist").child(prevIntent.getStringExtra("course"));
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String videoId = String.valueOf(snapshot.child("videoId").getValue());
                    String videoTitle = String.valueOf(snapshot.child("videoTitle").getValue());
                    String videoTime = String.valueOf(snapshot.child("videoTime").getValue());
                    String videoLink = String.valueOf(snapshot.child("videoLink").getValue());

                    playlist.add(new Playlist(videoId, videoTitle, videoTime, videoLink));

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Youtube playing video
        youTubePlayerView = findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId = youtubeVideoLink;
                youTubePlayer.loadVideo(videoId, 0);
            }
        });

        fullScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PlaylistPage.this, FullScreenVideo.class);
                intent.putExtra("videoId", youtubeVideoLink);
                startActivity(intent);

            }
        });
    }

    public void playlistOnItemClicked(Playlist playlist) {
        youtubeVideoTitle = playlist.getVideoTitle();
        youtubeVideoLink = playlist.getVideoLink();

        Intent intent = new Intent(this, PlaylistPage.class);
        intent.putExtra("course", prevIntent.getStringExtra("course"));
        intent.putExtra("videoTitle", youtubeVideoTitle);
        intent.putExtra("videoLink", youtubeVideoLink);
        startActivity(intent);
        overridePendingTransition(0, 0);          //For removing the swipe animation
        finish();                                                  //For not coming back to same activity

    }
}