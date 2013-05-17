package org.nc.clustering;

import org.junit.Before;
import org.junit.Test;
import org.nc.beans.Cluster;
import org.nc.beans.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import static org.junit.Assert.assertTrue;

/**
 * @author rbandara
 */
public class ClusterUtilTest {

    Cluster c1, c2, c3;
    TreeMap<Double, Cluster> map = new TreeMap<Double, Cluster>();


    @Before
    public void init() {
        c1 = new Cluster(new int[]{1, 2}, new Movie[]{new Movie(1, 3.5), new Movie(2, 3.3)});
        c2 = new Cluster(new int[]{3, 4}, new Movie[]{new Movie(3, 3), new Movie(4, 1)});
        c3 = new Cluster(new int[]{5, 6}, new Movie[]{new Movie(3, 4), new Movie(5, 3.5), new Movie(6, 3.3)});
        map.put(0.1, c1);
        map.put(0.8, c2);
        map.put(0.3, c3);

    }

    @Test
    public void testFindMaxSimilarityClusters() throws Exception {
        List<Cluster> maxSimilarityClusters = ClusterUtil.findMaxSimilarityClusters(map, 2);

        List<Cluster> expectedList = new ArrayList<Cluster>();
        expectedList.add(c2);
        expectedList.add(c3);

        assertTrue(maxSimilarityClusters.equals(expectedList));

    }

    @Test
    public void testCalculateAverageRating() {
        List<Cluster> maxSimilarityClusers = new ArrayList<Cluster>();
        maxSimilarityClusers.add(c2);
        maxSimilarityClusers.add(c3);
        double r = ClusterUtil.calculateAverageRating(3, maxSimilarityClusers);
        // ratings for movie 3 in clusters c2=3 and c3=4
        assertTrue(r == 3.5);
    }
}