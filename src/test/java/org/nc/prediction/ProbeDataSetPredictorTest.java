package org.nc.prediction;

import org.junit.Test;
import org.nc.beans.Pair;
import org.nc.data.DataCacheFactory;
import org.nc.data.FileDataLoader;
import org.nc.data.IDataCache;

import java.io.IOException;
import java.util.List;

import static junit.framework.Assert.assertTrue;

/**
 * @author rbandara
 *         Test class for ProbeDatasetPredictor
 * @see ProbeDataSetPredictor
 */
public class ProbeDataSetPredictorTest {
    @Test
    public void testProbeDatasetPredictor() throws IOException {
        ProbeDataSetPredictor predictor = new ProbeDataSetPredictor();
        predictor.predictForProbeDataSet();
    }

    @Test
    public void testProbeDataset() throws IOException {
        FileDataLoader loader = new FileDataLoader();
        List<Pair> pairs = loader.loadProbeDataSet();
        // see whether all the data in the dataset is in the training set
        IDataCache dataCache = DataCacheFactory.getDataCache();
        for (Pair pair : pairs) {
            byte rating = dataCache.getRating(pair.getMovieId(), pair.getUserId());
            assertTrue(rating > 0 && rating <= 5);
        }
    }
}
