package org.nc.data;

import com.google.common.primitives.Ints;
import org.apache.commons.io.FileUtils;
import org.nc.util.CommonUtils;
import org.nc.util.Constants;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author rbandra
 *         This will convert the netflix text dataset in to a binary dataset.
 */
public class FileDataWriter {

    public static void main(String[] args) throws IOException {

        CustomerIdMapper mapper = new CustomerIdMapper();
        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".txt");
            }
        };

        File folder = new File(Constants.DATA_PATH + File.separator + "netflix" + File.separator + "training_set");
        File[] listOfFiles = folder.listFiles(filter);
        int numMovies = listOfFiles.length;

        for (short i = 0; i < numMovies; i++) {
            String content = FileUtils.readFileToString(listOfFiles[i]);
            ArrayList<Integer> integerArrayList = new ArrayList<Integer>();
            HashMap<Integer, Integer> custIdRatingMap = new HashMap<Integer, Integer>();
            String[] lines = content.split("\n");
            for (String line : lines) {
                if (line.indexOf(':') != -1) {
                    continue;
                } else {
                    String[] parts = line.split(",");
                    int realCustomerId = Integer.parseInt(parts[0]);
                    int rating = Integer.parseInt(parts[1]);
                    // add the customerId for this movie to an array list
                    custIdRatingMap.put(mapper.getMappedId(realCustomerId), rating);
                    integerArrayList.add(mapper.getMappedId(realCustomerId));
                }
            }
            // save the array list for this movie
            final int[] customerIdArray = Ints.toArray(integerArrayList);

            final String fileName = (i + 1) + ".dat";
            final String filePath1 = Constants.DATA_PATH + File.separator + "binary" + File.separator + "customerids" + File.separator;
            final String filePath2 = Constants.DATA_PATH + File.separator + "binary" + File.separator + "fullratings" + File.separator;

            CommonUtils.writeToFile(fileName, filePath1, customerIdArray);
            //CommonUtils.writeToFile(fileName, filePath2, custIdRatingMap);

        }
    }


}
