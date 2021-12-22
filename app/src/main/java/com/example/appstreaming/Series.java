package com.example.appstreaming;

public class Series {
    private int sid;
    private String name;
    private String genre;
    private String rdate;
    private String runtime;
    private String description;
    private String keywords_en;
    private int episods;

    public int getEpisods() {
        return episods;
    }

    public void setEpisods(int episods) {
        this.episods = episods;
    }

    public Series() {
    }

    private String imgpath;

    public Series(int sid, String name, String genre, String rdate, String runtime, String description, String keywords_en, String imgpath, int seasons) {
        this.sid = sid;
        this.name = name;
        this.genre = genre;
        this.rdate = rdate;
        this.runtime = runtime;
        this.description = description;
        this.keywords_en = keywords_en;
        this.imgpath = imgpath;
        this.seasons = seasons;
    }

    private int seasons;

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
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

    public int getSeasons() {
        return seasons;
    }

    public void setSeasons(int seasons) {
        this.seasons = seasons;
    }
}
