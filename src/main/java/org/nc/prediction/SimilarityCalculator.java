package org.nc.prediction;

import com.google.common.collect.Sets;
import com.google.common.primitives.Ints;
import org.apache.log4j.Logger;

import java.util.HashSet;

/**
 * @author rbandara
 * Calculates the similarity between two arrays
 */
public class SimilarityCalculator {

    static Logger logger = Logger.getLogger(SimilarityCalculator.class.getName());

    /**
     * Assumption : If all the movies or a subset of movies(a lower value such as 30% considering the sizes)
     *  are present in a particular cluster, then that cluster most likely will be a candidate for this user to be in.
     * @param userMovies this is the array of movieIds which the user has rated
     * @param clusterMovies these are the movieIds in side cluster

     Scenarios
        size of
            clusterMovies > userMovies
            clusterMovies < userMovies
            clusterMovies = userMovies

        Logic
     *  - If clusterMovies contains all the movies in userMovies return 100%
     *
     * @return similarity value
     */
    public static double calculateSimilarity(int[] userMovies, int[] clusterMovies){
        HashSet<Integer> set1 = Sets.newHashSet(Ints.asList(userMovies));
        int size1 = set1.size();
        HashSet<Integer> set2 = Sets.newHashSet(Ints.asList(clusterMovies));
        int size2 = set2.size();
        // choose the smaller value for the denominator
        double denominator = size1 > size2 ? size2 : size1;
        boolean modified = set1.retainAll(set2);//transforms set1 into the intersection of set1 and set2.
        if(!modified){
            logger.info("there are no common entries between the clusters and the given movie");
        }
        double intersection = set1.size();

        return intersection/denominator;
    }
}
