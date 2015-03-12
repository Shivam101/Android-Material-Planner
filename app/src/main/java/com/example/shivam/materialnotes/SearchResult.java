package com.example.shivam.materialnotes;

import android.graphics.drawable.Drawable;

public class SearchResult {
    public String title;
    public Drawable icon;

    public SearchResult(String title, Drawable icon) {
       this.title = title;
       this.icon = icon;
    }
    
    @Override
    public String toString() {
        return title;
    }
    
}