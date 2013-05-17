package org.nc.prediction;

import com.google.common.primitives.Ints;
import org.apache.log4j.Logger;
import org.nc.beans.Cluster;
import org.nc.clustering.ClusterUtil;
import org.nc.data.DataCacheFactory;
import org.nc.data.FileDataLoader;
import org.nc.data.IDataCache;
import org.nc.util.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * @author rbandara
 *         This class is responsible for providing functions to predict movie rating for a given user and a movie
 */

public class RatingPredictorImpl implements IPredictor{

    Logger logger = Logger.getLogger(this.getClass().getName());

    List<Cluster> clusters = null;
    IDataCache cache = null;
    GlobalRatingCalculator globalRatingCalculator = null;
    int ratingsCalculated = 0;

    public RatingPredictorImpl() {
        cache = DataCacheFactory.getDataCache();
        FileDataLoader fileDataLoader = new FileDataLoader();
        this.clusters = fileDataLoader.loadClusters();
        globalRatingCalculator = new GlobalRatingCalculator();
    }

    /**
     * This predicts a rating for a give user and a movie considering the clusters found in a previous stage
     * <p/>
     * ALGORITHM
     * 1. Find the movies this user has rated
     * 2. See what are the clusters contain these set of movies ( find the best matching cluster for this user )
     * 3. From those clusters select the clusters which 'movieId' is present
     * a.) If there are clusters found
     * Take the average rating for mx as the rating
     * b.) If there are no clusters take the average rating as the movie rating
     *
     * @param userId
     * @param movieId
     * @return rating
     */
    public double predict(int userId, int movieId) {


        // Step 1 : Find the other movies this user rated
        int[] allMoviesRatedByCustomer = cache.getAllMoviesRatedByCustomer(userId);
        //logger.debug(" Number of other movies rated by customer :" + allMoviesRatedByCustomer.length);


        // Step 2: Find out the clusters that contains the given movieId
        ArrayList<Cluster> selectedClusters = new ArrayList<Cluster>();
        int i = 0;
        for (Cluster cluster : clusters) {
            i++;
            int[] movieIdsInCluster = cluster.getMovieIds();
            // choose the clusters which contains the <code>movieId</code>
            //logger.debug("Looking at movie " + movieId + " in cluster " + i + " " + Arrays.toString(movieIdsInCluster));
            boolean contains = Ints.contains(movieIdsInCluster, movieId);
            if (contains)
                selectedClusters.add(cluster);
        }

        if (selectedClusters.isEmpty()) {
            // the movie we are interested is not in any of the clusters.
            // in this case return the average for the movie as the rating
            return globalRatingCalculator.getMovieAverageRating(movieId);

        } else {
            logger.debug("CALCULATING RATING ");
            ratingsCalculated++;
            // We have gone through all the clusters and find out what clusters contain the movieId
            // Now go through the selected cluster list and see what are the clusters most similar to the movieId
            TreeMap<Double, Cluster> similarityMap = new TreeMap<Double, Cluster>();
            for (Cluster selectedCluster : selectedClusters) {
                int[] movieIdsInCluster = selectedCluster.getMovieIds();
                // Need to see how similar this array to <code>allMoviesRatedByCustomer</code>
                double similarity = SimilarityCalculator.calculateSimilarity(allMoviesRatedByCustomer, movieIdsInCluster);
                if (similarity != 0.0) {
                    logger.debug("Similarity :" + similarity);
                    similarityMap.put(similarity, selectedCluster);
                }
            }

            // get the cluster with the max similarity value
            logger.debug("Selecting the top " + Constants.NO_OF_CLUSTERS_TO_SELECT + " similar clusters");
            List<Cluster> maxSimilarityClusters = ClusterUtil.findMaxSimilarityClusters(similarityMap, Constants.NO_OF_CLUSTERS_TO_SELECT);
            // now we have found out the cluster list with the maximum similarity
            // We need to find the best rating
            // 1. We can look at the rating given to the movie in those clusters and take the average of the ratings
            // Get the average rating of <code> clusterAverageRatingList </code>
            double rating = ClusterUtil.calculateAverageRating(movieId, maxSimilarityClusters);
            return rating;
        }
    }

    public int getRatingsCalculated() {
        return ratingsCalculated;
    }
}



