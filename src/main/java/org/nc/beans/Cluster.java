package org.nc.beans;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author: rbandara
 * This represents a Cluster. A cluster contains a set of movies and a set of users
 * Each user,movie is associated with a rating
 */
public class Cluster implements Serializable {

    static final long serialVersionUID = 0L;

    private int[] users;
    private Movie[] movies;

    public Cluster(int[] users, Movie[] movies) {
        this.users = users;
        this.movies = movies;
    }

    public int[] getUsers() {
        return users;
    }

    public void setUsers(int[] users) {
        this.users = users;
    }

    public Movie[] getMovies() {
        return movies;
    }

    public void setMovies(Movie[] movies) {
        this.movies = movies;
    }

    public int[] getMovieIds() {
        int[] movieIds = new int[movies.length];
        int i = 0;
        for (Movie m : movies) {
            int movieId = m.getMovieId();
            movieIds[i] = movieId;
            i++;
        }
        return movieIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cluster cluster = (Cluster) o;

        if (!Arrays.equals(movies, cluster.movies)) return false;
        if (!Arrays.equals(users, cluster.users)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(users);
        result = 31 * result + Arrays.hashCode(movies);
        return result;
    }

    @Override
    public String toString() {
        return "Users :" + Arrays.toString(users) + " Movies :" + Arrays.toString(movies);
    }
}
