To compile our java code type the following command in the terminal when standing in the folder with the source code:

"javac -classpath . Xphone.java"


To run our simulation from a terminal type the following line when standing in the folder with the compiled code: 

“java xphone/Xphone seed length replications warm-up channels reserved”

Required
seed: long to use for random number generation.
length: integer for the duration of each replication in seconds.
replications: integer for the number of replications.
warm-up: warm-up period in seconds.
channels: number of channels in each base station.
reserved: number of reserved channels for handovers.

Example: "java xphone/Xphone 1 10000 100 1000 10 0"