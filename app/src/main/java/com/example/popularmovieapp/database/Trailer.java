package com.example.popularmovieapp.database;

public class Trailer {

    private String trailerTittle;
    private String trailerUrlKey;


    private String trailerImage;

    public Trailer(String trailerTittle, String trailerUrl, String trailerImage) {
        this.trailerTittle = trailerTittle;
        this.trailerUrlKey = trailerUrl;
        this.trailerImage = trailerImage;
    }

    public String getTrailerTittle() {
        return trailerTittle;
    }

    public void setTrailerTittle(String trailerTittle) {
        this.trailerTittle = trailerTittle;
    }

    public String getTrailerUrl() {
        return trailerUrlKey;
    }

    public void setTrailerUrl(String trailerUrl) {
        this.trailerUrlKey = trailerUrl;
    }

    public String getTrailerImage() {
        return trailerImage;
    }

    public void setTrailerImage(String trailerImage) {
        this.trailerImage = trailerImage;
    }

}
