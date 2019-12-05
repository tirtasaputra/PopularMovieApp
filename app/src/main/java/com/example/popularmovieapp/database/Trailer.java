package com.example.popularmovieapp.database;

public class Trailer {

    private String trailerTittle;
    private String trailerUrlKey;

    public Trailer(String trailerTittle, String trailerUrl) {
        this.trailerTittle = trailerTittle;
        this.trailerUrlKey = trailerUrl;
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

}
