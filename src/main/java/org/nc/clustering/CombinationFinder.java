package org.nc.clustering;

import org.apache.log4j.Logger;

/**
 * @author rbandara
 *         This is the 'runner' which will run in parallel to find combinations in the dataset.
 */
public class CombinationFinder implements Runnable {

    Logger logger = Logger.getLogger(this.getClass().getName());

    int discriminationSetSize = 0;
    int iterationsPerThread = 0;
    int minimumMoviesPerACluster = 0;


    public CombinationFinder(int discriminationSetSize, int iterationsPerThread, int minimumMoviesPerACluster) {
        this.discriminationSetSize = discriminationSetSize;
        this.iterationsPerThread = iterationsPerThread;
        this.minimumMoviesPerACluster = minimumMoviesPerACluster;
    }

    @Override
    public void run() {
        logger.info(" Starting thread " + Thread.currentThread().getName() + " with parameter s=" + discriminationSetSize);
        IMatchFinder preProcessor = new AllMatchFinder();
        preProcessor.findCombinations(Thread.currentThread().getName(), iterationsPerThread, discriminationSetSize, minimumMoviesPerACluster);
    }
}
