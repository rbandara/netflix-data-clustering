package org.nc.prediction;

import org.junit.Test;

import static junit.framework.Assert.assertTrue;

/**
 * @author rbandara
 *
 *         What are the scenarios that could happend?
 *
 *         1. Cluster size > user movie size
 *         ex : cluster having 5 movies and user movie size is 3
 *         Should see whether all user movies can be fitted in to the cluster.
 *         similarity = intersection / user movie size
 *
 *         2. Cluster size = user movie size
 *         ex : cluster having 5 movies and user movie size is 5
 *         similarity = intersection / user movie size
 *         <p/>
 *
 *         3. Cluster size < user movie size
 *         ex : cluster having 5 movies and user movie size is 25
 *         similarity  = intersection / cluster size
 */

public class SimilarityCalculatorTest {
    int[] clusterMovies = null;
    int[] userMovies = null;

    @Test
    public void testCalculateSimilarity_EqualSize_AllMatch() throws Exception {
        clusterMovies = new int[]{1, 4, 5, 6, 7, 8};
        userMovies = new int[]{1, 4, 5, 6, 7, 8};

        double v = SimilarityCalculator.calculateSimilarity(clusterMovies, userMovies);
        assertTrue(v == 1.0);
    }

    @Test
    public void testCalculateSimilarity_EqualSize_LessMatch() throws Exception {
        clusterMovies = new int[]{1, 4, 5, 6, 7, 8};
        userMovies = new int[]{1, 4, 5, 6};

        double v = SimilarityCalculator.calculateSimilarity(clusterMovies, userMovies);
        assertTrue(v == 1.0);
    }

    @Test
    public void testCalculateSimilarity_ClusterSize_Greater_All_Match() throws Exception {
        clusterMovies = new int[]{1, 4, 5, 6, 7, 8};
        userMovies = new int[]{1, 4, 5};

        double v = SimilarityCalculator.calculateSimilarity(clusterMovies, userMovies);
        assertTrue(v == 1.0);
    }

    @Test
    public void testCalculateSimilarity_ClusterSize_Greater_Partial_Match() throws Exception {
        clusterMovies = new int[]{1, 4, 5, 6, 7, 8};
        userMovies = new int[]{1,8, 14, 35};

        double v = SimilarityCalculator.calculateSimilarity(clusterMovies, userMovies);
        assertTrue(v == 0.5);
    }

    @Test
    public void testCalculateSimilarity_ClusterSize_Greater_No_Match() throws Exception {
        clusterMovies = new int[]{1, 4, 5, 6, 7, 8};
        userMovies = new int[]{9, 14, 35};

        double v = SimilarityCalculator.calculateSimilarity(clusterMovies, userMovies);
        assertTrue(v == 0);
    }

    @Test
    public void testCalculateSimilarity_User_Movie_Size_Greater_PartialMatch() throws Exception {
        clusterMovies = new int[]{1, 4, 5, 6, 7, 8};
        userMovies = new int[]{1, 4, 5, 34, 45, 64, 65, 45, 34, 43, 122, 432, 545, 643, 3433};//15

        double v = SimilarityCalculator.calculateSimilarity(clusterMovies, userMovies);
        assertTrue(v == 0.5);//1/5
    }

    @Test
    public void testCalculateSimilarity_User_Movie_Size_Greater_All_Match() throws Exception {
        clusterMovies = new int[]{1, 4, 5, 6, 7, 8};
        userMovies = new int[]{1, 4, 5, 6, 7, 8, 65, 45, 34, 43, 122, 432, 545, 643, 3433};//15

        double v = SimilarityCalculator.calculateSimilarity(clusterMovies, userMovies);
        assertTrue(v == 1);//1/5
    }

    @Test
    public void testCalculateSimilarity_User_Movie_Size_Greater_No_Match() throws Exception {
        clusterMovies = new int[]{1, 4, 5, 6, 7, 8};
        userMovies = new int[]{10, 40, 58, 69, 71, 82, 65, 45, 34, 43, 122, 432, 545, 643, 3433};//15

        double v = SimilarityCalculator.calculateSimilarity(clusterMovies, userMovies);
        assertTrue(v == 0);//1/5
    }

}
