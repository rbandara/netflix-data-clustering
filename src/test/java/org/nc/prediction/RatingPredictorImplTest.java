package org.nc.prediction;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.nc.util.RandomUtil;

import static org.junit.Assert.assertTrue;

/**
 * @author rbandara
 */
public class RatingPredictorImplTest {

    Logger logger = Logger.getLogger(this.getClass().getName());

    RatingPredictorImpl predictor;
    @Before
    public void setUp() throws Exception {
        predictor = new RatingPredictorImpl();
    }


    @Test
    public void testPredictForGivenUser() {
        int userId = 9;
        int movieId = 2862;
        double prediction = predictor.predict(userId, movieId);
        logger.debug("Prediction " + prediction);
        assertTrue(prediction > 0 && prediction < 6);
    }

    @Ignore
    @Test
    public void testPredictForGivenUsers() {
        for (int i = 0; i < 10; i++) {
            int userId = RandomUtil.getRandomUserId();
            int movieId = RandomUtil.getRandomMovieId();
            double prediction = predictor.predict(userId, movieId);
            logger.debug("prediction = " + prediction);
            assertTrue(prediction > 0 && prediction < 6);
        }
    }
}
