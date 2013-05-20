This project is done as my capstone project in University of Washington Bothell Masters program.

clustering-netflix-data
=======================
The main idea of this project is to come up with a solution to a movie recommender system using a clustering approach.
As the clustering approach I plan to use a high dimensional clustering approach (SEPC) algorithm and the as the dataset
I will be using Netflix movie data set. In the Netflix data challenge (http://www.netflixprize.com) there have been few
teams who used clustering approaches. As far as the Netflix challenge blog reveals they have been using traditional clustering techniques.

The code consistes of three main componenets
1. CombinationFinder
2. ClusterFinder
3. Predictor
Each component is able to run as an individual program.

CombinationFinder
=================
To build the jar file with dependecies
    mvn clean compile assembly:single

To run the jar file
    java -cp sepc.jar org.nc.clustering.CombinatoinFinderMain 4 1000000 6 2&

     Parameters <Number Of Threads> " +
                "<Iterations per thread> " +
                "<discrimination set size (s)> " +
                "<Minimum movies per cluster>"


nohup java -cp sepc.jar org.nc.clustering.CombinatoinFinderMain 4 1000000 6 2&

ClusterFinder
=============

 java -cp sepc.jar org.nc.clustering.ClusterFinder

 java -Xmx2048m -cp cluster-finder.jar org.nc.clustering.ClusterFinder

Netflix dataset
=================================
The dataset consists of several text files.

movie_titles.txt
----------------
This file contains titles of the 17770 movies in the ascending order of their Movie ID used to identify the movie in
the matrix along with its year of release and the name of the movie as a ASCII character string.

probe.txt
---------
This file has a list of “Movie ID followed by multiple User IDs” for which the training set has predictions.
One can use this file to test the accuracy of an algorithm. The Netflix website describes this file as a substitute to
frequent query of “scoring oracle”. It also mentions that the query points in this file have similar characteristics to
the qualifying subset of ratings for which no ratings are provided in the training data. Netflix’s Cinematch system
produces a RMSE of 0.9474 on the probe.txt data set.

qualifying.txt
--------------
This file has structure similar to the probe.txt but the ratings for these “Movie ID, User ID” pairs are not in the
training data set provided.
