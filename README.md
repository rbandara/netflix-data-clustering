clustering-netflix-data
=======================

This is done as part of my masters degree in University of Washington Bothell

Running CombinationFinder
=========================
To build the jar file with dependecies
    mvn clean compile assembly:single

To run the jar file
    java -cp sepc.jar org.nc.clustering.CombinatoinFinderMain 4 1000000 6 2&

     Parameters <Number Of Threads> " +
                "<Iterations per thread> " +
                "<discrimination set size (s)> " +
                "<Minimum movies per cluster>"


 nohup java -cp sepc.jar org.nc.clustering.CombinatoinFinderMain 4 1000000 6 2&

 Running ClusterFinder
 =======================

 java -cp sepc.jar org.nc.clustering.ClusterFinder

 java -Xmx2048m -cp cluster-finder.jar org.nc.clustering.ClusterFinder
