package com.example.appstreaming;

import java.io.Serializable;

public class Movies implements Serializable {
    private int mid;
    private String name;
    private String genre;
    private String rdate;
    private String runtime;
    private String description;
    private String keywords_en;

    public Movies() {
    }

    private String imgpath;

    public Movies(int mid, String name, String genre, String rdate, String runtime, String description, String keywords_en, String imgpath, String videopath) {
        this.mid = mid;
        this.name = name;
        this.genre = genre;
        this.rdate = rdate;
        this.runtime = runtime;
        this.description = description;
        this.keywords_en = keywords_en;
        this.imgpath = imgpath;
        this.videopath = videopath;
    }

    private String videopath;

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getRdate() {
        return rdate;
    }

    public void setRdate(String rdate) {
        this.rdate = rdate;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKeywords_en() {
        return keywords_en;
    }

    public void setKeywords_en(String keywords_en) {
        this.keywords_en = keywords_en;
    }

    public String getImgpath() {
        return imgpath;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }

    public String getVideopath() {
        return videopath;
    }

    public void setVideopath(String videopath) {
        this.videopath = videopath;
    }
}
