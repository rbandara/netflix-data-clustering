package org.nc.util;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: 235911
 * Date: 2/2/13
 * Time: 11:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class RandomUtilTest {
    @Test
    public void testGetRandomUserList(){
        RandomUtil rUtil = new RandomUtil();
        int[] randomUsersArray = rUtil.getRandomArray(Constants.NO_OF_MOVIES, 10);
        assertEquals(10, randomUsersArray.length);
    }
}
