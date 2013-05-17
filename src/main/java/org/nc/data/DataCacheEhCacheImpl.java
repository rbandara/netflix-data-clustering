package org.nc.data;

import com.google.common.primitives.Ints;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.apache.log4j.Logger;
import org.nc.exception.NonExistingMovieIdException;
import org.nc.util.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * ehCache<a>http://ehcache.org/</a> based Data cache implementation.
 *
 * @rbandara
 */
public class DataCacheEhCacheImpl implements IDataCache {

    Logger logger = Logger.getLogger(this.getClass().getName());

    private static DataCacheEhCacheImpl instance;

    int counter = 0;

    static DataCacheEhCacheImpl getInstance() {
        if (instance == null)
            instance = new DataCacheEhCacheImpl();
        return instance;
    }

    private static String CUST_ID_CACHE = "CUST_ID_CACHE";
    private static String CUST_ID_RATING_CACHE = "CUST_ID_RATING_CACHE";

    public DataCacheEhCacheImpl() {
        CacheManager.getInstance().addCache(CUST_ID_CACHE);
        CacheManager.getInstance().addCache(CUST_ID_RATING_CACHE);


    }

    @Override
    public int[] getCustomerData(int movieId) {
        long t0 = System.currentTimeMillis();
        int custData[] = null;
        if (getCustIdCache().get(movieId) == null) {
            FileDataLoader loader = new FileDataLoader();
            custData = loader.loadCustomerDataForMovie(movieId);
            Arrays.sort(custData);
            getCustIdCache().put(new Element(movieId, custData));
            long t1 = System.currentTimeMillis();
            // logger.debug(" Getting data for movie from DISK " + movieId + " time took " + (t1-t0) +" milli seconds" );
        } else {
            Element element = getCustIdCache().get(movieId);
            custData = (int[]) element.getObjectValue();
            long t1 = System.currentTimeMillis();
            //logger.debug(" Getting data for movie from CACHE " + movieId + " time took " + (t1-t0) +" milli seconds" );

        }
        //logger.debug("Size of the cache " + getCustIdCache.getCachesize());
        return custData;
    }

    @Override
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

    @Override
    public HashMap getRatingsMap(int movieId) {
        counter++;
        long t0 = System.currentTimeMillis();

        HashMap<Integer, Integer> custIdRatingsMap = null;
        if (getCustIdRatingCache().get(movieId) == null) {
            FileDataLoader loader = new FileDataLoader();
            custIdRatingsMap = loader.loadRatingsForMovie(movieId);
            getCustIdRatingCache().put(new Element(movieId, custIdRatingsMap));
            long t1 = System.currentTimeMillis();
            logger.debug(" from DISK " + movieId + " time took " + (t1 - t0) + " milli seconds");
        } else {
            Element element = getCustIdRatingCache().get(movieId);
            custIdRatingsMap = (HashMap<Integer, Integer>) element.getObjectValue();

            long t1 = System.currentTimeMillis();
            //logger.debug(" from CACHE " + movieId);
        }
        //logger.info("Size of the cache " + getCustIdRatingCache().getKeys().size());
        if (counter % 100 == 0) {
            Ehcache myCache =
                    CacheManager.getInstance().getEhcache(CUST_ID_RATING_CACHE);
            logger.debug("Cache name: " + myCache.getName());
            logger.debug("Local Heap size in MB: " + myCache.getStatistics().getLocalHeapSizeInBytes() / (1024 * 1024));
            logger.debug("Hit count: " + myCache.getStatistics().cacheHitCount());
            logger.debug("Miss count: " + myCache.getStatistics().cacheMissCount());
            logger.debug("Miss count (because expired): " + myCache.getStatistics().cacheMissExpiredCount());
        }
        return custIdRatingsMap;
    }

    @Override
    public int[] getAllMoviesRatedByCustomer(int customerId) {
        List<Integer> moviesByCustomerId = new ArrayList<Integer>();
        for (int movieId = 1; movieId <= Constants.NO_OF_MOVIES; movieId++) {
            int[] customerData = getCustomerData(movieId);
            if (Ints.contains(customerData, customerId))
                moviesByCustomerId.add(movieId);
        }
        return Ints.toArray(moviesByCustomerId);
    }

    private Cache getCustIdCache() {
        return getCache(CUST_ID_CACHE);
    }

    private Cache getCustIdRatingCache() {
        return getCache(CUST_ID_RATING_CACHE);
    }

    private Cache getCache(String id) {
        return CacheManager.getInstance().getCache(id);
    }

}
