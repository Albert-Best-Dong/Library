package com.example.albert.library.model;

import java.util.Date;

public class Book {
    private String bookName;
    private int suitableAge;
    private int pic;

    private boolean history;
    private boolean suspense;
    private boolean literacy;

    public Book(String bookName, int suitableAge, int pic, boolean history, boolean suspense, boolean literacy) {
        this.bookName = bookName;
        this.suitableAge = suitableAge;
        this.pic = pic;
        this.history = history;
        this.suspense = suspense;
        this.literacy = literacy;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }

    public boolean isHistory() {
        return history;
    }

    public void setHistory(boolean history) {
        this.history = history;
    }

    public boolean isSuspense() {
        return suspense;
    }

    public void setSuspense(boolean suspense) {
        this.suspense = suspense;
    }

    public boolean isLiteracy() {
        return literacy;
    }

    public void setLiteracy(boolean literacy) {
        this.literacy = literacy;
    }

    public int getSuitableAge() {
        return suitableAge;
    }

    public void setSuitableAge(int suitableAge) {
        this.suitableAge = suitableAge;
    }
}
