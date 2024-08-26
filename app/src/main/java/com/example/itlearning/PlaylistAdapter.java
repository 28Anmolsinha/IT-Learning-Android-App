package com.example.itlearning;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.ViewHolder>{

    Context context;
    ArrayList<Playlist> playlistArrayList;
    private RecyclerViewInterface2 recyclerViewInterface;

    public PlaylistAdapter(Context context, ArrayList<Playlist> playlistArrayList, RecyclerViewInterface2 recyclerViewInterface){
        this.context = context;
        this.playlistArrayList = playlistArrayList;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.playlist_item, parent, false);
        PlaylistAdapter.ViewHolder viewHolder =new PlaylistAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.videoId.setText(playlistArrayList.get(position).getVideoId());
        holder.videoTitle.setText(playlistArrayList.get(position).getVideoTitle());
        holder.videoTime.setText(playlistArrayList.get(position).getVideoTime());

        //for setting up the onclick on the playlist item
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewInterface.playlistOnItemClicked(playlistArrayList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return playlistArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView videoId, videoTitle, videoTime;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            videoId = itemView.findViewById(R.id.video_Id);
            videoTitle = itemView.findViewById(R.id.video_title);
            videoTime = itemView.findViewById(R.id.video_time);
            cardView = itemView.findViewById(R.id.playlist_item);
        }
    }
}
