package org.nc.clustering;

import org.apache.log4j.Logger;

/**
 * @author rbandara
 *         This is the main class which calls the CombinationFinder.
 */
public class CombinatoinFinderMain {
    static Logger logger = Logger.getLogger(CombinatoinFinderMain.class.getName());

    public static void main(String[] args) throws InterruptedException {
        if (args != null && args.length == 4) {
            int noOfThreads = Integer.parseInt(args[0]);
            int iterationsPerThread = Integer.parseInt(args[1]);
            int discriminationSetSize = Integer.parseInt(args[2]);
            int minimumMoviesPerACluster = Integer.parseInt(args[3]);
            Thread[] worker = new Thread[noOfThreads];
            Runnable runner = new CombinationFinder(discriminationSetSize, iterationsPerThread, minimumMoviesPerACluster);
            // create threads
            for (int i = 0; i < noOfThreads; i++) {
                worker[i] = new Thread(runner);
                worker[i].start();
            }
            // wait and finish
            for (int i = 0; i < noOfThreads; i++) {
                worker[i].join();
                worker[i] = null;
            }
        } else
            logger.debug("usage  : CombinatoinFinderMain <Number Of Threads> " +
                    "<Iterations per thread> " +
                    "<discrimination set size (s)> " +
                    "<Minimum movies per cluster>");
    }
}
