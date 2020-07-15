package com.bitbybit.vahana.utils;

import com.bitbybit.vahana.gettersetter.BookGS;
import com.bitbybit.vahana.gettersetter.BookingsGS;
import com.bitbybit.vahana.gettersetter.HotspotGS;
import com.bitbybit.vahana.gettersetter.SearchGS;

import java.util.ArrayList;

public class Data {

    private static Data data;
    private SearchGS searchGS;
    private ArrayList<BookGS> bookGS;
    public static final String url = "http://127.0.0.1:5000/app/";
    public static final String authcheck_url = url + "signin";
    public static final String signup_url = url + "signup";

    private String from, to, date;
    private BookingsGS tempbookingsGS;
    public static final String station_url = url + "station/";
    public static final String search_url = url + "search";
    public static final String book_url = url + "book";
    public static final String bookings_url = url + "booked";
    public static final String hotspot_url = url + "hotspot";
    public static final String safeflag_url = url + "safeflag";
    private ArrayList<HotspotGS> hotspotGS;

    public ArrayList<HotspotGS> getHotspotGS() {
        return hotspotGS;
    }

    public void setHotspotGS(ArrayList<HotspotGS> hotspotGS) {
        this.hotspotGS = hotspotGS;
    }

    private Data() {
    }

    public static synchronized Data getInstance() {

        if (data == null) {
            data = new Data();
        }
        return data;
    }

    public SearchGS getSearchGS() {
        return searchGS;
    }

    public void setSearchGS(SearchGS searchGS) {
        this.searchGS = searchGS;
    }

    public ArrayList<BookGS> getBookGS() {
        return bookGS;
    }

    public void setBookGS(ArrayList<BookGS> bookGS) {
        this.bookGS = bookGS;
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

    public BookingsGS getTempbookingsGS() {
        return tempbookingsGS;
    }

    public void setTempbookingsGS(BookingsGS tempbookingsGS) {
        this.tempbookingsGS = tempbookingsGS;
    }

}
