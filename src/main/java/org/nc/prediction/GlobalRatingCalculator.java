package org.nc.prediction;

import org.apache.log4j.Logger;
import org.nc.data.DataCacheFactory;
import org.nc.data.IDataCache;
import org.nc.util.CommonUtils;

import java.util.Collection;
import java.util.HashMap;


/**
 * @author rbandara
 *         This will contain methids to calculate global ratings considering the dataset as a whole
 */
public class GlobalRatingCalculator {

    Logger logger = Logger.getLogger(this.getClass().getName());

    IDataCache dataCache = null;

    public GlobalRatingCalculator() {
        dataCache = DataCacheFactory.getDataCache();
    }

    /**
     * Calculates the average rating for the movie
     * average = sum(all the ratings for the movie) / # of ratings for the movie
     *
     * @param movieId
     * @return
     */
    public double getMovieAverageRating(int movieId) {
        HashMap<Integer, Integer> ratingsMap = dataCache.getRatingsMap(movieId);
        int numberOfRatings = ratingsMap.size();
        Collection<Integer> values = ratingsMap.values();
        Integer sumOfRatings = CommonUtils.sum(values);
        return sumOfRatings / numberOfRatings;
    }

    /**
     * Calculate an average rating for the user.
     * average = sum( all the ratings of user ) / # of ratings by user
     *
     * @param userId
     * @return
     */
    public double getUserAverageRating(int userId) {
        int[] allMoviesRatedByCustomer = dataCache.getAllMoviesRatedByCustomer(userId);
        int sumOfAllRatingsByUser = 0;
        for (int i = 0; i < allMoviesRatedByCustomer.length; i++) {
            int movieId = allMoviesRatedByCustomer[i];
            byte rating = dataCache.getRating(movieId, userId);
            sumOfAllRatingsByUser = sumOfAllRatingsByUser + rating;
        }
        int numberOfRatingsByUser = allMoviesRatedByCustomer.length;
        double average = sumOfAllRatingsByUser / numberOfRatingsByUser;
        logger.debug("User Average " +  average);
        return average;
    }
}
