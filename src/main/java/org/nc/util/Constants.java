package org.nc.util;

import java.io.File;

/**
 * @author rbandara
 *         Contains the constant values used
 */
public class Constants {

    // Algorithm specific variables
    public static final int DISCRIMINATION_SET_SIZE = 6; // this is s
    public static final int WIDTH = 1; // this is w
    public static final int ITERATIONS = 10000000; // this is n

    public static final int NO_OF_USERS = 480189;
    public static final int NO_OF_MOVIES = 17770;
    public static final int NO_OF_RECORDS = 100480507;

    public static final int NO_OF_THREADS = 4;
    public static final String DATA_PATH = ".." + File.separator + "data";
    public static final int NO_OF_CLUSTERS_TO_SELECT = 5;
    public static final int MIN_NUMBER_OF_MOVIES_IN_CLUSTER = 2;

}
