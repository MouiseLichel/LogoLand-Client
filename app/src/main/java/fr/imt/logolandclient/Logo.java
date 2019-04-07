package fr.imt.logolandclient;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Logo implements Serializable {
    private String score;
    private String url;
    private boolean isValid = true;

    public Logo(String logoName, String url) {
        this.score = logoName;
        this.url = url;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
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
