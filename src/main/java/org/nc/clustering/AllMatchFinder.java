package org.nc.clustering;

import com.google.common.primitives.Ints;
import org.apache.log4j.Logger;
import org.nc.data.DataCacheFactory;
import org.nc.data.IDataCache;
import org.nc.util.Constants;
import org.nc.util.RandomUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This will go through the dataset and find the user movie combinations that are
 * possible candidates for clusters. This will consider a user, movie to be in the cluster
 * if they have rated only.
 * <p/>
 * Will not look at the ratings, but it will see whether all the users has rated all the movies
 * in the randomly selected possible cluster
 */
public class AllMatchFinder implements IMatchFinder {

    Logger logger = Logger.getLogger(this.getClass().getName());
    /**
     * This will first select random set of users, then find the movies all users has rated.
     * Saves the combination to a binary file.
     *
     * @param parameter to identify which thread is the executing thread
     * @iterations number of iterations this will run
     * @discriminationSetSize size chosen for number of random users.
     */
    public void findCombinations(String param,final int iterations,final int discriminationSetSize, final int minimumMoviesPerACluster) {
        //logger.debug("finding combinations from thread " + param + "iterations " + iterations + " s=" + discriminationSetSize) ;
        // Get an instance of datacache
        IDataCache cache = DataCacheFactory.getDataCache();
        // select some  number ( s ) of random users

        long time1 = System.currentTimeMillis();

        // List of found combinations so far
        List<ArrayList<int[]>> combinationsList = new ArrayList<ArrayList<int[]>>();

        for (int i = 1; i < iterations; i++) {

            // discriminating set
            int[] users = RandomUtil.getRandomArray(Constants.NO_OF_USERS, discriminationSetSize);
            Arrays.sort(users);

            ArrayList<Integer> movieList = new ArrayList<Integer>();

            int[] ratings = null;
            // for each movie get the customer ratings
            for (int movieId = 1; movieId <= Constants.NO_OF_MOVIES; movieId++) {

                ratings = cache.getCustomerData(movieId);
                // we have two sorted arrays {users,ratings}
                // see the elements in 'users' array is present in the 'ratings' array.
                boolean contains = false;
                for (int userId : users) {
                    // all the users has to be present in the ratings
                    contains = Ints.contains(ratings, userId);
                    if (!contains)
                        break;
                }
                if (contains) {
                    movieList.add(movieId);
                }

            }
            ArrayList<int[]> combinationList = new ArrayList<int[]>();
            if (!movieList.isEmpty() && movieList.size() > minimumMoviesPerACluster) {
                logger.debug(param + " iteration "+i + " C " + Arrays.toString(users) + " D " + movieList.toString());
                combinationList.add(users);
                combinationList.add(Ints.toArray(movieList));
                if (!combinationsList.contains(combinationList)) {
                    combinationsList.add(combinationList);
                    // write the cluster to disk
                    writeToDisk(param, combinationList, discriminationSetSize);
                } else
                    logger.debug("Duplicate combination found");

            }


        }

        long time2 = System.currentTimeMillis();

        logger.debug(param + "| Iterations " + iterations + " Time took " + (time2 - time1) / 1000 + " seconds ");
    }

    /**
     * This will write the found combination to the disk
     *
     * @param param
     * @param combinationList
     * @param discriminationSetSize
     */
    private void writeToDisk(String param, ArrayList<int[]> combinationList, int discriminationSetSize) {

        try {
            final String fileName = param + "_s_" + discriminationSetSize + "_" + System.currentTimeMillis() + ".dat";
            String filePath = Constants.DATA_PATH + File.separator + "binary" + File.separator + "combinations" + File.separator + fileName;
            File file = new File(filePath);
            if (!file.exists())
                file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(combinationList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
