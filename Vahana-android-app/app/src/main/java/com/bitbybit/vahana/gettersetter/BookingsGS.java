package com.bitbybit.vahana.gettersetter;

import java.util.ArrayList;

public class BookingsGS {

    private String from, to, date, seats, url;
    private int id;
    private ArrayList<BookGS> detailList;

    public int getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<BookGS> getDetailList() {
        return detailList;
    }

    public void setDetailList(ArrayList<BookGS> detailList) {
        this.detailList = detailList;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSeats() {
        return seats;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }
}
