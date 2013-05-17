package org.nc.clustering;

import org.apache.log4j.Logger;
import org.nc.beans.Cluster;
import org.nc.beans.Movie;
import org.nc.data.DataCacheFactory;
import org.nc.data.IDataCache;
import org.nc.util.CommonUtils;
import org.nc.util.Constants;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author rbandara
 * This reads in the combinations and spits out the clusters
 */
public class ClusterFinder {

    Logger logger = Logger.getLogger(this.getClass().getName());

    public List<Cluster> findClusters() {

        List<Cluster> clusters = new ArrayList<Cluster>();

        IDataCache cache = DataCacheFactory.getDataCache();
        File folder = new File(Constants.DATA_PATH + File.separator+"binary"+ File.separator +"combinations");
        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".dat");
            }
        };

        File[] listOfFiles = folder.listFiles(filter);
        logger.info(" There are " + listOfFiles.length + " combinations ");
        int numFilesProcessed = 0;
        for (File file : listOfFiles) {
            numFilesProcessed++;
            /* A list to keep possible congregating dimensions ( D ) */
            ArrayList<Movie> congregatingDimensions = new ArrayList<Movie>();
            /* A list to keep the ratings associated with congregating dimensions */
            ArrayList<List<Integer>> ratings = new ArrayList<List<Integer>>();

            ArrayList<int[]> userMovieList = null;
            try {
                FileInputStream fis = new FileInputStream(file.getAbsoluteFile());
                ObjectInputStream iis = new ObjectInputStream(fis);
                userMovieList = (ArrayList<int[]>) iis.readObject();
                fis.close();
                iis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            //file.renameTo(new File(file.getName().replace(".dat",".used")));
            file.delete();

            /* These corresponds to users and movies in a cluster*/
            int[] users = userMovieList.get(0);
            int[] movies = userMovieList.get(1);


            List<Integer> byMovieRatingList = null;
            for (int movieId : movies) {
                byMovieRatingList = new ArrayList();
                for (int user : users) {
                    byte rating = cache.getRating(movieId, user);
                    /* This will contain ratings for a single movie*/
                    byMovieRatingList.add(Integer.valueOf(rating));
                }

                // find the min and max of values in the list
                if (!byMovieRatingList.isEmpty()) {
                    final Integer max = Collections.max(byMovieRatingList);
                    final Integer min = Collections.min(byMovieRatingList);
                    int span = max - min;
                    // there has to be a way to store the average rating for the movie along with movieId
                    // It is not clear whether we can take the average or any other measure such as 'mode'
                    // for now, lets store the mean along with the movieId
                    // So this value is like the mean rating for the movieId in the congregating set.
                    // we will store that information in to a bean, let's name it as MovieRating which is declared
                    // locally as an inner as of now.
                    if (span <= Constants.WIDTH) {
                        Movie movie = new Movie(movieId,(max+min)/2.0);
                        congregatingDimensions.add(movie);
                        ratings.add(byMovieRatingList);
                    }
                }

            }

            if (!congregatingDimensions.isEmpty()) {
                logger.debug("C " + Arrays.toString(users));
                logger.debug("D " + congregatingDimensions.toString());
                logger.debug("R " + ratings.toString() + "\n");
                Cluster cluster = new Cluster(users, congregatingDimensions.toArray(new Movie[congregatingDimensions.size()]));
                String fileName = System.currentTimeMillis() + ".dat";
                final String filePath = Constants.DATA_PATH + File.separator+ "binary"+File.separator+"clusters";
                CommonUtils.writeToFile(fileName, filePath, cluster);
                clusters.add(cluster);
                logger.debug("Clusters found so far : " + clusters.size());
            }

            logger.debug("Files processed : " + numFilesProcessed);
        }
        logger.info("Found " + clusters.size() + " clusters ");
        return clusters;

    }


    public static void main(String[] args) {
        ClusterFinder clusterFinder = new ClusterFinder();
        clusterFinder.findClusters();

    }

}
