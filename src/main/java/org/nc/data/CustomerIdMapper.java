package org.nc.data;

import org.apache.commons.io.FileUtils;
import org.nc.util.Constants;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author ravindrab
 *         This maps customerId in the dataset to a sequential number.
 *         Provides helper methods to look up using customerId and the mappedId
 */
public class CustomerIdMapper {

    int[] customerIdArray = null;

    public CustomerIdMapper() {
        File customerIdFile = new File(Constants.DATA_PATH + File.separator + "mapping" + File.separator + "customerId.txt");
        String customerIdString = null;
        try {
            customerIdString = FileUtils.readFileToString(customerIdFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] customerIds = customerIdString.split("\r\n");
        int index = 0;
        customerIdArray = new int[customerIds.length];
        for (String customerId : customerIds) {
            customerIdArray[index] = Integer.parseInt(customerId);
            index++;
        }
    }

    /**
     * Get the mapped id given the real customerId
     *
     * @param mappedId
     * @return
     */
    public int getCustomerId(int mappedId) {
        return customerIdArray[mappedId];
    }

    /**
     * get the real customerId given the mapped id
     *
     * @param customerId
     * @return
     */
    public int getMappedId(int customerId) {
        return Arrays.binarySearch(customerIdArray, customerId);
    }
}
