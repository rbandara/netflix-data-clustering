package org.nc.data;

import cern.colt.map.OpenIntObjectHashMap;

import java.util.HashMap;

/**
 * @author: rbandara
 * Colt<a>http://acs.lbl.gov/software/colt/</a> based DataCache implementation.
 */
public class DataCacheColt implements IDataCache {

    private OpenIntObjectHashMap customerIdMap = new OpenIntObjectHashMap();


    private static DataCacheColt instance;

    static DataCacheColt getInstance() {
        if (instance == null)
            instance = new DataCacheColt();
        return instance;
    }

    private DataCacheColt() {
        customerIdMap = new OpenIntObjectHashMap();
    }

    public int[] getCustomerData(int movieId) {

        if (customerIdMap.get(movieId) == null) {
            FileDataLoader loader = new FileDataLoader();
            final int[] customerData = loader.loadCustomerDataForMovie(movieId);
            customerIdMap.put(movieId, customerData);
            return customerData;
        } else {
            return (int[]) customerIdMap.get(movieId);
        }
    }

    @Override
    public int[] getAllMoviesRatedByCustomer(int customerId) {
        return new int[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public HashMap getRatingsMap(int movieId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public byte getRating(int movieId, int customerId) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
