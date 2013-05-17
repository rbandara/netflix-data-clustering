package org.nc.prediction;

/**
 * @author rbandara
 * Naive implementation of the IPredictor
 * Assigns user average rating as the movie rating
 */
public class RatingPredictorNaiveImpl implements IPredictor {
    GlobalRatingCalculator ratingCalculator=null;

    public RatingPredictorNaiveImpl() {
        ratingCalculator = new GlobalRatingCalculator();
    }

    @Override
    public double predict(int userId, int movieId) {
        return ratingCalculator.getMovieAverageRating(movieId);
    }

    @Override
    public int getRatingsCalculated() {
        return 0;
    }
}
