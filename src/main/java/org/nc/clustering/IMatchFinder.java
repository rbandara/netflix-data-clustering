package org.nc.clustering;

/**
 * @author rbandara
 *
 * This is the Contract which all the combination finder classes has to implement
 *
 * CombinationFinders can have varying logics. One could consider all the user, movie pairs
 * that has a rating ( i.e with out ignoring the non rated movies )
 * Another implementation could ignore <b>some</b> of the non rated movies in the cluster.
 *
 */
public interface IMatchFinder {
    /**
     *
     * @param param a String parameter to identify the current thread
     * @param iterations Number of iterations the algorithm will run
     * @param discriminationSetSize the value for 's' in SEPC language
     */
    public void findCombinations(String param, int iterations, int discriminationSetSize,int minimumMoviesPerACluster);
}
