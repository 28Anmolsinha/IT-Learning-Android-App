package com.example.itlearning;

public class Playlist {

    private String videoId, videoTitle, videoTime, videoLink;

    public Playlist(String videoId, String videoTitle, String videoTime, String videoLink) {
        this.videoId = videoId;
        this.videoTitle = videoTitle;
        this.videoTime = videoTime;
        this.videoLink = videoLink;
    }

    public String getVideoId() {
        return videoId;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public String getVideoTime() {
        return videoTime;
    }

    public String getVideoLink() {
        return videoLink;
    }
}
