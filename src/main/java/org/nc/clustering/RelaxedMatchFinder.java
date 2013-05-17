package org.nc.clustering;

/**
 * @author rbandara
 *
 * This will go through the dataset and find the user movie combinations that are
 * possible candidates for clusters. This will consider a user, movie to be in the cluster
 * even if all the users hasn't rated in the discriminating set.
 *
 */
public class RelaxedMatchFinder implements IMatchFinder {
    @Override
    public void findCombinations(String param, int iterations, int discriminationSetSize, int minimumMoviesPerACluster) {

    }
}
