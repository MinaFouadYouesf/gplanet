package com.example.mina.androidtaskmina;

public class pagesObject {
    private int from,to;
    private String uID;

    pagesObject(){ }

    public void setFrom(int from) {
        this.from = from;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public String getuID() {
        return uID;
    }
}
