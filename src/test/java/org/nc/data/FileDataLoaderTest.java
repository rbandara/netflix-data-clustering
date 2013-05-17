package org.nc.data;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;


/**
 * @author rbandara
 */
public class FileDataLoaderTest {
    @Test
    public void testLoadCustomerDataForMovie() throws Exception {
        FileDataLoader dataLoader = new FileDataLoader();
        assertNotNull(dataLoader.loadCustomerDataForMovie(1));
    }

    @Test
    public void testLoadRatingsForMovie() throws Exception {
        FileDataLoader dataLoader = new FileDataLoader();
        assertNotNull(dataLoader.loadRatingsForMovie(1));
    }

    @Test
    public void testLoadClusters() throws Exception {
        FileDataLoader dataLoader = new FileDataLoader();
        assertNotNull(dataLoader.loadClusters());
    }

    @Test
    public void testLoadProbeSet() throws Exception {
        FileDataLoader dataLoader = new FileDataLoader();
        assertNotNull(dataLoader.loadProbeDataSet());
    }
}
