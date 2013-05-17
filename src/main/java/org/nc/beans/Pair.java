package org.nc.beans;

/**
 * @author rbandara
 *         Represents a user,movie pair
 */
public class Pair {
    int userId;
    int movieId;
    double rating;

    public Pair(int userId, int movieId, double rating) {
        this.userId = userId;
        this.movieId = movieId;
        this.rating = rating;
    }

    public Pair(int userId, int movieId) {
        this.userId = userId;
        this.movieId = movieId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("[").append(userId).append(",").append(movieId).append("]").toString();
    }
}
