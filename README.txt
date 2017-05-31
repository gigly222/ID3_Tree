README

-ID3 Algorithm for Decision Trees Using Post Pruning


-This assignment was written in Java 1.8.
-Eclipse IDE was used for development.
-Include all data files used in your "src" folder so that program can find files when
executing. Or, create a new directory, add all the java and data files to the same folder. This way
when you compile and run your program, the program can find the files it needs to run.
-I am not using any ready-made libraries. Everything was written from scratch.

******************************************
To compile the code type in the following:
******************************************


javac Main.java

java Main training_set.csv validation_set.csv test_set.csv 0.2


******************************************
The order of the training_set.csv, validaiton_set.csv and test_set.csv need to be passed into
the command line in that order. The 0.2 is the pruning factor. This value can be changed based on
how many nodes you want to prune from the tree.
******************************************