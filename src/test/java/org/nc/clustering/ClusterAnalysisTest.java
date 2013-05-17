package org.nc.clustering;

import com.google.common.primitives.Ints;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.nc.beans.Cluster;
import org.nc.beans.Movie;
import org.nc.data.FileDataLoader;
import org.nc.util.CommonUtils;
import org.nc.util.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * @author rbandara
 */
public class ClusterAnalysisTest {

    Logger logger = Logger.getLogger(this.getClass().getName());

    FileDataLoader loader = null;
    List<Cluster> clusters = null;

    @Before
    public void init() {
        loader = new FileDataLoader();
        clusters = loader.loadClusters();

    }

    @Test
    public void analizeClusters() {
        System.out.println("There are " + clusters.size() + " clusters ");
        int NUM_MOVIES = 10;
        int count = 0;
        System.out.println(" Number of movies in a cluster ");
        for (Cluster cluster : clusters) {
            Movie[] movies = cluster.getMovies();
            if (movies.length > NUM_MOVIES) {
                count++;
            }
        }
        System.out.println(" There are " + count + " clusters having more than " + NUM_MOVIES + " movies");
    }

    @Test
    public void checkForDuplicates() {
        HashMap<Cluster, Integer> hashMap = new HashMap<Cluster, Integer>();
        int count = 1;
        for (Cluster cluster : clusters) {
            if (hashMap.get(cluster) == null)
                hashMap.put(cluster, count);
        }
        logger.info("There are " + (hashMap.size() - clusters.size()) + " duplicates ");
    }

    @Test
    public void findMoviesThatDoesNotBelongToAnyOfTheClustersTest() {
        // We need to find the movieIds that don't exist in the clusters.
        List<Integer> notClusteredMovies = new ArrayList<Integer>();
        List<Integer> movieCountList = new ArrayList<Integer>();

        for (int i = 1; i <= Constants.NO_OF_MOVIES; i++) {

            boolean contains = false;
            int movieCount = 0;
            for (Cluster cluster : clusters) {
                int[] movieIds = cluster.getMovieIds();
                contains = Ints.contains(movieIds, i);
                // if found ewxit from the inner loop, since we know the movie is there in one of the clusters.
                if (contains) {
                    movieCount++;
                    break;
                }
            }
            if (movieCount != 0) {
                logger.debug(" Movie Id " + i + " was in  " + movieCount + " clusters ");
                movieCountList.add(movieCount);
            }

            if (!contains) {
//                logger.debug("Movie Id " + i + " is not in any of the clusters ");
                notClusteredMovies.add(i);
            }
        }

        logger.debug("===============================================================");
        logger.debug(" Movies those didn't belong to any of the clusters " + notClusteredMovies.size());
        double averageOfIntList = CommonUtils.getAverageOfIntList(movieCountList);
        logger.debug(" Average number of movies in a cluster: " + averageOfIntList);
        logger.debug(" Max number of movies in a cluster: " + Collections.max(movieCountList));
    }


    @Test
    public void test() {
        int notInClusterMovies = 0;
        for (int i = 1; i <= Constants.NO_OF_MOVIES; i++) {
            int clusterCount = 0;
            for (Cluster cluster : clusters) {
                int[] movieIds = cluster.getMovieIds();
                boolean contains = Ints.contains(movieIds, i);
                if (contains) {
                    //logger.debug("Movie Id " + i + " is in cluster " + cluster);
                    clusterCount++;
                }
            }
            if (clusterCount == 0) {
                notInClusterMovies++;
            } else {
                logger.debug("Cluster count for movie " + i + " = " + clusterCount);
            }
        }
        logger.debug(" There are " + notInClusterMovies + " movies which are not found in any of the clusters");
    }

    @Test
    public void analizeUserBasedClusters(){
        // We need to find the userIds that don't exist in the clusters.
        List<Integer> notClusteredUsers = new ArrayList<Integer>();
        List<Integer> userCountList = new ArrayList<Integer>();

        for (int i = 1; i <= Constants.NO_OF_USERS; i++) {

            boolean contains = false;
            int userCount = 0;
            for (Cluster cluster : clusters) {
                int[] userIds = cluster.getUsers();
                contains = Ints.contains(userIds, i);
                // if found ewxit from the inner loop, since we know the movie is there in one of the clusters.
                if (contains) {
                    break;
                }
            }
            if (!contains) {
                logger.debug("None of the clusters contain user " + i );
                notClusteredUsers.add(i);
            }
        }

        logger.debug(" Users that didn't belong to any of the clusters " + notClusteredUsers.size());
    }
}
