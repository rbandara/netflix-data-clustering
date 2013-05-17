package org.nc.prediction;

import junit.framework.Assert;
import org.junit.Test;
import org.nc.util.Constants;

/**
 * @author rbandara
 */
public class GlobalRatingCalculatorTest {
    @Test
    public void testGetAverageRatingByMovie(){
        GlobalRatingCalculator ratingCalculator = new GlobalRatingCalculator();
        for (int i = 1; i <= Constants.NO_OF_MOVIES; i++) {
            double averageRating = ratingCalculator.getMovieAverageRating(i);
            Assert.assertTrue(averageRating > 0 && averageRating <= 5);
        }
    }

    @Test
    public void testGetAverageRatingByUser(){
        GlobalRatingCalculator ratingCalculator = new GlobalRatingCalculator();
        for (int i = 1; i <= Constants.NO_OF_USERS; i++) {
            double averageRating = ratingCalculator.getUserAverageRating(i);
            Assert.assertTrue(averageRating > 0 && averageRating <= 5);
            if(i == 1000) break;
        }
    }
}
