package org.nc.prediction;

import org.nc.beans.Pair;

import java.util.List;

/**
 * @author rbandara
 * The contract all the prediction algorithms has to implement
 */
public interface IPredictor {
    /**
     * Predicts the rating for the given userId and the movieId
     * @param userId
     * @param movieId
     * @return the predicted rating
     */
    public double predict(int userId, int movieId);
    public List<Pair> getRatedUserMovies();
}
