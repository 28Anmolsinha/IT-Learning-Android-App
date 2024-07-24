package com.example.itlearning;

public class Course {

    private String imageResourceId, courseId;
    private String courseVideoCount, courseDuration, courseName;

    //for next page(playlist)
    private String videoTitle, videoLink;

    public Course(String imageResourceId, String courseVideoCount, String courseDuration, String courseName, String courseId, String videoTitle, String videoLink) {
        this.imageResourceId = imageResourceId;
        this.courseVideoCount = courseVideoCount;
        this.courseDuration = courseDuration;
        this.courseName = courseName;
        this.courseId = courseId;
        this.videoTitle = videoTitle;
        this.videoLink = videoLink;
    }

    public String getImageResourceId() {
        return imageResourceId;
    }

    public String getCourseVideoCount() {
        return courseVideoCount;
    }

    public String getCourseDuration() {
        return courseDuration;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseId(){
        return  courseId;
    }

    public String getVideoTitle(){
        return  videoTitle;
    }

    public String getVideoLink(){
        return videoLink;
    }

}
