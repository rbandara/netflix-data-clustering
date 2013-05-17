package org.nc.data;

import com.google.common.primitives.Ints;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.nc.exception.NonExistingMovieIdException;
import org.nc.util.CommonUtils;
import org.nc.util.Constants;
import org.nc.util.RandomUtil;

import java.util.*;

import static junit.framework.Assert.*;

/**
 * @author: rbandara
 */
public class DataCacheTest {

    Logger logger = Logger.getLogger(this.getClass().getName());

    IDataCache cache = null;

    @Before
    public void setUp() {
        cache = DataCacheFactory.getDataCache();
    }

    @Test
    public void testGetCustomerData_Default() throws Exception {
        final int[] customerData = cache.getCustomerData(1);
        assertEquals(547, customerData.length);
    }

    @Test
    public void testGetManyCustomerData() throws Exception {
        for (int i = 1; i <= 1000; i++) {
            assertNotNull(cache.getCustomerData(i));
        }
    }

    @Test
    public void testGetDuplicatedCustomerData() throws Exception {
        for (int i = 1; i <= 1000; i++) {
            assertNotNull(cache.getCustomerData(i));
            assertNotNull(cache.getCustomerData(1000 - (i - 1)));
        }
    }

    @Test
    public void testGetCustomerData_Colt() throws Exception {
        DataCacheColt cache = DataCacheColt.getInstance();
        final int[] customerData = cache.getCustomerData(1);
        assertEquals(547, customerData.length);
    }

    @Test
    public void testCacheForNotExistingCustomer() {
        // customer 1 hasn't rated movie 1
        byte rating = cache.getRating(1, 1);
        assertEquals(0, rating);
    }

    @Test
    public void testCacheForExistingCustomer() {
        // customer 90330 has rated movie 1
        byte rating = cache.getRating(1, 90330);
        assertTrue(rating > 0 && rating < 6);
    }

    @Test(expected = NonExistingMovieIdException.class)
    public void testCacheForNotExistingMovie() {
        byte rating = cache.getRating(19809, 1);
    }

    @Test
    public void testGetRatingsMap() {
        HashMap ratingsMap = cache.getRatingsMap(1);
        assertNotNull(ratingsMap);
    }

    @Test
    public void testGetRatingsMaps() {
        long time0 = System.currentTimeMillis();
        int[] ratings = null;
        for (int iteration = 1; iteration < 100; iteration++) {

            int[] users = RandomUtil.getRandomArray(Constants.NO_OF_USERS, 5);
            Arrays.sort(users);

            long time1 = System.currentTimeMillis();

            for (int movieId = 1; movieId <= Constants.NO_OF_MOVIES; movieId++) {
                ratings = cache.getCustomerData(movieId);

                boolean contains = false;
                for (int userId : users) {
                    contains = Ints.contains(ratings, userId);
                    if (!contains)
                        break;
                }
                if (contains)
                    logger.debug("Iteration : " + iteration + " Users " + Arrays.toString(users) + " have rated Movie " + movieId);
                // we have two sorted arrays {users,ratings}
                // see the elements in 'users' array is present in the 'ratings' array.
            }

            long time2 = System.currentTimeMillis();
//            logger.debug("Time took for iteration " + iteration + " = "+ (time2-time1) + " millies");

        }

        long time3 = System.currentTimeMillis();

        logger.debug("Time took for all the iterations " + (time3 - time0) + " millies");
//        logger.debug("Size of the datacache : " + cache.getCustomerDataCacheSize());

    }

    @Ignore("This is taking time")
    @Test
    public void getAllMoviesRatedByCustomerTest() {
        List<Integer> listOfMoviesRatedByCustomer = new ArrayList<Integer>(Constants.NO_OF_USERS);
        for (int i = 1; i <= Constants.NO_OF_USERS; i++) {
            long time1 = System.currentTimeMillis();
            int[] allMoviesRatedByCustomer = cache.getAllMoviesRatedByCustomer(i);
            listOfMoviesRatedByCustomer.add(allMoviesRatedByCustomer.length);
            long time2 = System.currentTimeMillis();
            logger.debug(" Time took for customerId " + i + " : " + (time2 - time1) + " millies");
        }
        logger.debug(Collections.min(listOfMoviesRatedByCustomer));
        logger.debug(Collections.max(listOfMoviesRatedByCustomer));
        logger.debug(CommonUtils.getAverageOfIntList(listOfMoviesRatedByCustomer));
    }


}
