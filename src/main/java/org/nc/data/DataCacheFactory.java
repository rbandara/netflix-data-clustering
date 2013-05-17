package org.nc.data;

/**
 * @author rbandara
 *         Factory class which will instantiate the needed implementation
 */
public class DataCacheFactory {
    public static IDataCache getDataCache() {
        return ModifiedDataCache.getInstance();
    }
}
