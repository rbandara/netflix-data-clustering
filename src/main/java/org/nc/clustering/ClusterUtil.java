package org.nc.clustering;

import org.nc.beans.Cluster;
import org.nc.beans.Movie;
import org.nc.util.CommonUtils;

import java.util.*;

/**
 * @author rbandara
 *         Contains operations which are helpful
 */
public class ClusterUtil {
    /**
     * This will calculate an average rating for a movie from a list of similar clusters.
     * Ex : if the test movie mx belongs to cluster A,B and C here we will see what are the ratings in the A,B,C for
     * movie mx and take the average of it.
     *
     * @param movieId  the movieId we are interested in knowing the rating
     * @param clusters the clusters that are most probable candidates for movie mx to be in
     * @return calculated rating for movie mx
     */
    public static double calculateAverageRating(int movieId, List<Cluster> clusters) {
        List<Double> clusterAverageRatingList = new ArrayList<Double>();
        for (Cluster cluster : clusters) {
            Movie[] movies = cluster.getMovies();
            for (Movie aMovie : movies) {
                int movieIdInCluster = aMovie.getMovieId();
                if (movieIdInCluster == movieId) {
                    // ok, this is the movie we are interested in. Get the rating.
                    double averageRating = aMovie.getAverageRating();
                    clusterAverageRatingList.add(averageRating);
                }
            }
        }
        return CommonUtils.getAverageOfDoubleList(clusterAverageRatingList);
    }

    /**
     * This function takes a map and a integer limit value ( N ) and returns top N values from the map
     * Note we are using a TreeMap as the map since it needs an ordered map.
     *
     * @param map a ordered map which contains similarity value as the key and the corresponding cluster as the value
     * @param N   denotes the number of values needed
     * @return the top N values from a ordered map
     */
    public static List<Cluster> findMaxSimilarityClusters(TreeMap<Double, Cluster> map, int N) {
        NavigableMap<Double, Cluster> reverseMap = map.descendingMap();
        List<Cluster> topClusters = new ArrayList<Cluster>(N);
        int count = 0;
        for (Map.Entry<Double, Cluster> entry : reverseMap.entrySet()) {
            topClusters.add(entry.getValue());
            count++;
            if (count == N) break;
        }
        return topClusters;
    }


}
