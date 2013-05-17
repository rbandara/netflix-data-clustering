package org.nc.data;

import com.google.common.primitives.Ints;
import org.apache.log4j.Logger;
import org.nc.exception.NonExistingMovieIdException;
import org.nc.util.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


/**
 * @author: rbandara
 * This is a DataCache implementation using the classes in java.utl package
 */
public class DataCache implements IDataCache {

    Logger logger = Logger.getLogger(this.getClass().getName());

    private HashMap<Integer, int[]> customerIdMap = null;
    private HashMap<Integer, HashMap> customerIdRatingsMap = null;

    private static DataCache instance;

    static DataCache getInstance() {
        if (instance == null)
            instance = new DataCache();
        return instance;
    }

    private DataCache() {
        customerIdMap = new HashMap<Integer, int[]>();
        customerIdRatingsMap = new HashMap<Integer, HashMap>();
    }


    public int[] getCustomerData(int movieId) {
        long t0 = System.currentTimeMillis();
        int custData[] = null;
        if (customerIdMap.get(movieId) == null) {
            FileDataLoader loader = new FileDataLoader();
            custData = loader.loadCustomerDataForMovie(movieId);
            Arrays.sort(custData);
            customerIdMap.put(movieId, custData);
            long t1 = System.currentTimeMillis();
//            logger.debug(" Getting data for movie from DISK " + movieId + " time took " + (t1-t0) +" milli seconds" );
        } else {
            custData = customerIdMap.get(movieId);
            long t1 = System.currentTimeMillis();
//            logger.debug(" Getting data for movie from CACHE " + movieId + " time took " + (t1-t0) +" milli seconds" );

        }
//        logger.debug("Size of the cache " + customerIdMap.size());
        return custData;
    }

    public byte getRating(int movieId, int customerId) {
        HashMap<Integer, Integer> ratingsMap = getRatingsMap(movieId);
        if (ratingsMap == null)
            throw new NonExistingMovieIdException();
        Integer rating = ratingsMap.get(customerId);
        if (rating != null)
            return rating.byteValue();
        else
            return 0;
    }

    public HashMap getRatingsMap(int movieId) {
        long t0 = System.currentTimeMillis();

        HashMap<Integer, Integer> custIdRatingsMap = null;
        if (customerIdRatingsMap.get(movieId) == null) {
            FileDataLoader loader = new FileDataLoader();
            custIdRatingsMap = loader.loadRatingsForMovie(movieId);
            customerIdRatingsMap.put(movieId, custIdRatingsMap);
            long t1 = System.currentTimeMillis();
            logger.debug(" Getting data for movie from DISK " + movieId + " time took " + (t1 - t0) + " milli seconds");
        } else {
            custIdRatingsMap = customerIdRatingsMap.get(movieId);
            long t1 = System.currentTimeMillis();
            //logger.debug(" Getting data for movie from CACHE " + movieId + " time took " + (t1-t0) +" milli seconds" );
        }
        //logger.debug("Size of the cache " + customerIdRatingsMap.size());

        return custIdRatingsMap;
    }

    /**
     * Gets all the movies for a given customerId
     *
     * @param customerId
     * @return movieId array
     */
    public int[] getAllMoviesRatedByCustomer(int customerId) {
        List<Integer> moviesByCustomerId = new ArrayList<Integer>();
        for (int movieId = 1; movieId <= Constants.NO_OF_MOVIES; movieId++) {
            int[] customerData = getCustomerData(movieId);
            if (Ints.contains(customerData, customerId))
                moviesByCustomerId.add(movieId);
        }
        return Ints.toArray(moviesByCustomerId);
    }
}
