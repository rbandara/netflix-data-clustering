package org.nc.prediction;

import org.apache.log4j.Logger;
import org.nc.beans.Pair;
import org.nc.data.DataCacheFactory;
import org.nc.data.FileDataLoader;
import org.nc.data.IDataCache;

import java.io.IOException;
import java.util.List;

/**
 * @author rbandara
 *         The probe dataset looks like this
 *         111:
 *         3245,2005-12-19
 *         5666,2005-12-23
 *         6789,2005-03-14
 *         225:
 *         1234,2005-05-26
 *         3456,2005-11-07
 *         ...
 *         This class will predict the ratings for the records in probe dataset
 *         It will calculate the RMSE value
 *         <p/>
 *         TODO - complete the implementation
 */
public class ProbeDataSetPredictor {

    IPredictor predictor = null;

    public ProbeDataSetPredictor(){
//        predictor = new RatingPredictorImpl();
        predictor = new RatingPredictorNaiveImpl();
    }

    Logger logger = Logger.getLogger(this.getClass().getName());

    public void predictForProbeDataSet() throws IOException {
        FileDataLoader loader = new FileDataLoader();
        List<Pair> pairs = loader.loadProbeDataSetLight();
        IDataCache dataCache = DataCacheFactory.getDataCache();
        logger.info("Number of user,movie pairs : " + pairs.size());
        double sumOfSquaredError = 0;
        int count = 0;
        for (Pair pair : pairs) {
            count++;
//            IPredictor predictor = new RatingPredictorImpl();
            double prediction = predictor.predict(pair.getUserId(), pair.getMovieId());
            double realRating = dataCache.getRating(pair.getMovieId(),pair.getUserId());
            if(realRating == 0)
                logger.warn("dataset didn't contain the [user,movie] " +  pair.getUserId() + " , " + pair.getMovieId()  );
            logger.debug("Pair Count " + count +" Prediction :" + prediction + " Real rating : " + realRating );
            // calculate the RMSE value between the real and predicted ratings
            double squaredError = ( Math.pow((realRating - prediction),2) );
            sumOfSquaredError = sumOfSquaredError + squaredError;
        }
        double RMSE = Math.sqrt( sumOfSquaredError/pairs.size() );
        logger.info("RMSE value " + RMSE);
        logger.info("Calculated Ratings " + predictor.getRatedUserMovies().size());      //1.161663555113005


        sumOfSquaredError = 0;
        // Calculate RMSE seperately for the user,movie which the prediction was done using clustering approach
        List<Pair> ratedUserMoviesUsingClusteringApproach = predictor.getRatedUserMovies();
        for (Pair pair : ratedUserMoviesUsingClusteringApproach) {
            double prediction = pair.getRating();
            double realRating = dataCache.getRating(pair.getMovieId(),pair.getUserId());

            double squaredError = ( Math.pow((realRating - prediction),2) );
            sumOfSquaredError = sumOfSquaredError + squaredError;
        }
        RMSE = Math.sqrt( sumOfSquaredError/pairs.size() );
        logger.info("RMSE value clustered only" + RMSE);
        /*RMSE value 1.1542094155741365
        Calculated Ratings 5254
        RMSE value clustered only0.7817284534926476*/


    }

    public static void main(String[] args) {
        ProbeDataSetPredictor predictor = new ProbeDataSetPredictor();
        try {
            predictor.predictForProbeDataSet();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
