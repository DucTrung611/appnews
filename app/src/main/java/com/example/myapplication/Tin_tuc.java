package com.example.myapplication;

public class Tin_tuc {
    String id;
    String title;
    String des;
    String pubDate;
    String link;

    public Tin_tuc() {
    }

    public Tin_tuc(String id, String title, String des, String pubDate, String link) {
        this.id = id;
        this.title = title;
        this.des = des;
        this.pubDate = pubDate;
        this.link = link;
    }


    public void setLink(String link) {
        this.link = link;
    }
}


