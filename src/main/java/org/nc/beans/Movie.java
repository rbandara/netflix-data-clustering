package org.nc.beans;

import java.io.Serializable;

/**
 * @author rbandara
 * Represents a rated column in movie,user matrix
 * Each column has an average rating
 */
public class Movie implements Serializable {
    int movieId;
    double averageRating;

    public Movie(int movieId, double averageRating) {
        this.movieId = movieId;
        this.averageRating = averageRating;
    }

    public int getMovieId() {
        return movieId;
    }

    public double getAverageRating() {
        return averageRating;
    }

    @Override
    public String toString() {
        return movieId+"="+averageRating;
    }
}