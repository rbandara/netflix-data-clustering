package org.nc.data;

import java.util.HashMap;

/**
 * @author rbandara
 *         This is the interface which all the DataCaches should implement.
 */
public interface IDataCache {
    /**
     * @param movieId a movieId
     * @return customers who rated the <code>movieId</code>
     */
    public int[] getCustomerData(int movieId);

    /**
     * Gives the rating for the given movieId and customerId
     *
     * @param movieId    a movieId
     * @param customerId a customerId ( This is the mapped Id, not the real customerId )
     * @return the rating for the movie with <code>movieId</code> and the customer with <code>customerId</code>
     */
    public byte getRating(int movieId, int customerId);

    /**
     * For a given movieId this will give a map which contains customerIds and their ratings
     *
     * @param movieId a movieId
     * @return a HashMap containing the customers who rated the given <code>movieId</code> and their ratings
     */
    public HashMap<Integer, Integer> getRatingsMap(int movieId);

    /**
     * @param customerId a customerId ( this is the mapped id )
     * @return all the movies which the given <code>customerId</code> has rated
     */
    public int[] getAllMoviesRatedByCustomer(int customerId);
}
