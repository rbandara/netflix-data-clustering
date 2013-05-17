package org.nc.clustering;

import org.junit.Ignore;
import org.junit.Test;
import org.nc.beans.Cluster;
import org.nc.beans.Movie;

import java.util.List;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * @author rbandara
 */

public class ClusterFinderTest {

    /**
     * Pre condition : There are combination files saved in the disk
     */
    @Test
    public void testFindClusters(){
        ClusterFinder finder = new ClusterFinder();
        List<Cluster> clusters = finder.findClusters();
        assertNotNull(clusters);
        for (Cluster cluster : clusters) {
            Movie[] movies = cluster.getMovies();
            assertNotNull(movies);
            assertNotNull(cluster.getUsers());
            for (Movie movie : movies) {
                assertNotNull(movie.getMovieId());
                assertNotNull(movie.getAverageRating());
                assertTrue(movie.getAverageRating()>0 && movie.getAverageRating() < 5);
            }
        }
    }



}
