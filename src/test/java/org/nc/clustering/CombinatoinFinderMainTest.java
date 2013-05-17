package org.nc.clustering;

import org.junit.Test;
import org.nc.util.Constants;

/**
 * @author rbandara
 */
public class CombinatoinFinderMainTest {
    @Test
    public void test() throws InterruptedException {
        CombinatoinFinderMain.main(new String[]{String.valueOf(Constants.NO_OF_THREADS), String.valueOf(100), String.valueOf(Constants.DISCRIMINATION_SET_SIZE),String.valueOf(Constants.MIN_NUMBER_OF_MOVIES_IN_CLUSTER)});
    }
}
