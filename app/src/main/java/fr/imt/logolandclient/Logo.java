package fr.imt.logolandclient;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Logo implements Serializable {
    private String score;
    private String url;

    public Logo(String logoName, String url) {
        this.score = logoName;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
