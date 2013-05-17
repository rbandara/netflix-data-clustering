package org.nc.data;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.nc.beans.Cluster;
import org.nc.beans.Pair;
import org.nc.exception.NonExistingMovieIdException;
import org.nc.util.Constants;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author: rbandara
 * This contains various file operations dealing with netflix training dataset, probedataset
 * @see FileDataWriter
 *      <code>IDataCache</code>implementations will utilize the methods in this class to load data from the dataset.
 */
public class FileDataLoader {

    Logger logger = Logger.getLogger(this.getClass().getName());

    /**
     * @param movieId a movieId
     * @return the customers who rated the <code> movie </code>
     */
    public int[] loadCustomerDataForMovie(int movieId) {
        int[] array = null;
        try {
            FileInputStream fis = new FileInputStream(Constants.DATA_PATH + File.separator + "binary" + File.separator + "customerids" + File.separator + movieId + ".dat");
            ObjectInputStream iis = new ObjectInputStream(fis);
            array = (int[]) iis.readObject();
            iis.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return array;
    }

    /**
     * @param movieId a movieId
     * @return for a given <code>movieId</code> this will load the customers and associated ratings.
     */
    public HashMap loadRatingsForMovie(int movieId) {
        HashMap hashMap = null;
        try {
            FileInputStream fis = new FileInputStream(Constants.DATA_PATH + File.separator + "binary" + File.separator + "fullratings" + File.separator + movieId + ".dat");
            ObjectInputStream iis = new ObjectInputStream(fis);
            hashMap = (HashMap) iis.readObject();
            iis.close();
            fis.close();
        } catch (Exception e) {
            logger.debug("There is no such movieId ");
            throw new NonExistingMovieIdException();
        }
        return hashMap;
    }

    /**
     * Reads clusters from where saved in the disk and loads to the memory.
     *
     * @return the list of clusters
     */
    public List<Cluster> loadClusters() {

        List<Cluster> clusters = new ArrayList<Cluster>();
        File folder = new File(Constants.DATA_PATH + File.separator + "binary" + File.separator + "clusters");
        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".dat");
            }
        };
        File[] listOfFiles = folder.listFiles(filter);
        for (File file : listOfFiles) {
            Cluster cluster = null;
            try {
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream iis = new ObjectInputStream(fis);
                cluster = (Cluster) iis.readObject();
                iis.close();
                fis.close();
                clusters.add(cluster);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return clusters;
    }

    /**
     * Loads the probe dataset in to memory
     *
     * @return
     * @throws IOException
     */
    public List<Pair> loadProbeDataSet() throws IOException {

        CustomerIdMapper mapper = new CustomerIdMapper();

        File probeSetFile = new File(Constants.DATA_PATH + File.separator + "netflix" + File.separator + "probe.txt");
        String probeContent = FileUtils.readFileToString(probeSetFile);
        HashMap<Integer, int[]> custIdRatingMap = new HashMap<Integer, int[]>();
        // keeps the pairs in a list
        List<Pair> userMoviePairList = new ArrayList<Pair>();
        String[] lines = probeContent.split("\n");
        String movieId = null;
        for (String line : lines) {
            int index = line.indexOf(':');
            if (index != -1) {
                // pick the movieId
                movieId = line.substring(0, index);
                continue;
            } else {
                int realCustomerId = Integer.parseInt(line);
                Pair userMoviePair = new Pair(mapper.getMappedId(realCustomerId), Integer.parseInt(movieId));
                userMoviePairList.add(userMoviePair);
            }
        }
        return userMoviePairList;
    }

    /**
     * Loads a lighter version of probe dataset in to memory
     *
     * @return
     * @throws IOException
     */
    public List<Pair> loadProbeDataSetLight() throws IOException {
        return loadProbeDataSet().subList(0,10000);
    }

}
