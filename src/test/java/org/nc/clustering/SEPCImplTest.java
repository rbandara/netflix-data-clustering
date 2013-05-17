package org.nc.clustering;

import org.junit.Before;
import org.junit.Test;
import org.nc.util.Constants;

/**
 * User: rbandara
 * Date: 2/12/13
 * Time: 10:21 PM
 */
public class SEPCImplTest {

    int[] movieDataArray = null;


    @Before
    public void setUp(){

    }
    @Test
    public void testSepcPreprocessor(){
        AllMatchFinder sepcImpl = new AllMatchFinder();
        sepcImpl.findCombinations(Thread.currentThread().getName(), 10,Constants.DISCRIMINATION_SET_SIZE,6);
    }

}
