package xphone;

import java.util.Random;

public class Xphone {
	
	static long seed = 0;
	static int length = 0; 
	static int replications = 0;
	static int warmUp = 0;
	static int channels = 0;
	static int reservedChannels = 0;
	static Random rnd = new Random();
	static CallSimulation[] simulation;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		seed = (long)Integer.parseInt(args[0]);
		length = Integer.parseInt(args[1]);
		replications = Integer.parseInt(args[2]);
		warmUp = Integer.parseInt(args[3]);
		channels = Integer.parseInt(args[4]);
		reservedChannels = Integer.parseInt(args[5]);
		
		System.out.println("#################################################");
		System.out.println("#						#");
		System.out.println("# Welcome to xPhone!			#");
		System.out.println("#						#");
		System.out.println("#################################################");
		
		System.out.println("# Seed:					" + seed + "	#");
		System.out.println("# Replication length:			" + length + " min	#");
		System.out.println("# Replications:				" + replications + "	#");
		System.out.println("# Warm-Up:				" + warmUp + "	#");
		System.out.println("# Channels:				" + channels + "	#");
		System.out.println("# Reserved:				" + reservedChannels + "	#");

		
		simulation = new CallSimulation[replications];
		
		rnd.setSeed(seed);
		
		for (int i = 0; i < replications; i++) {
			long rndSeed = rnd.nextLong();
			simulation[i] = new CallSimulation(rndSeed, length, warmUp, channels, reservedChannels);

			//TODO: Visa resultat för varje replication!
//			System.out.println("#########################################################");
//			System.out.println("#							#");
//			System.out.println("# Replication " + (i + 1) + "					#");
//			System.out.println("#							#");
//			System.out.println("#########################################################");
//			System.out.println("#							#");
//			System.out.println("# Total customers:			" + simulation[i].getTotalCustomers() + "		#");
//			System.out.println("# Rejected customers:			" + String.format("%.5g",100*simulation[i].getPercentRejected()) + "%		#");
//			System.out.println("# Average queueing time:		" + String.format("%.5g",simulation[i].getAverageQueueTime()) + " min	#");
//			System.out.println("#########################################################");
//			System.out.println("");



		}
		//TODO: Visa medelvärden för alla körningar.
//		System.out.println("#########################################################");
//		System.out.println("#							#");
//		System.out.println("# Average of: "+ replications +" replications with			#");
//		System.out.println("#							#");
//		System.out.println("# Seed:						" + seed + "	#");
//		System.out.println("# Replications:					" + replications + "	#");
//		System.out.println("# Queue:					" + maxQueueSize + "	#");
//		System.out.println("# Replication length:				" + modelTime + " min	#");
//		System.out.println("#							#");
//		System.out.println("#########################################################");
//		System.out.println("# Total average customers: 		" + String.format("%.5g",(double) sumTotalCustomers/replications) + "		#");
//		System.out.println("# Total average percent rejected:	" +  String.format("%.5g",(double) 100*sumPercentRejected/replications) + "%		#");
//		System.out.println("# Total average average queue time:	" + String.format("%.5g",(double) sumAverageQueueTime/replications) + " min	#");
//		System.out.println("#########################################################");
	}

}
