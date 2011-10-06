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
	static private int sumBlockedCalls = 0;
	static private double sumDroppedCalls = 0;
	static private double sumEndedCalls = 0;
	static private double sumTotalCalls = 0;

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
			
			sumBlockedCalls += simulation[i].getBlockedCalls();
			sumDroppedCalls += simulation[i].getDroppedCalls();
			sumEndedCalls += simulation[i].getEndedCalls();
			sumTotalCalls += simulation[i].getTotalCalls();
			
			//TODO: Visa resultat för varje replication!
			System.out.println("#########################################################");
			System.out.println("#							#");
			System.out.println("# Replica. " + (i + 1) + "						#");
			System.out.println("#							#");
			System.out.println("#########################################################");
			System.out.println("#							#");
			System.out.println("# Blocked Calls:	" + simulation[i].getBlockedCalls() + "				#");
			System.out.println("# Dropped Calls:	" + simulation[i].getDroppedCalls() + "				#");
			System.out.println("# Ended Calls:		" + simulation[i].getEndedCalls() + " 				#");
			System.out.println("# Total Calls:		" + simulation[i].getTotalCalls() + "				#");
			System.out.println("#########################################################");
			System.out.println("");



		}
		//TODO: Visa medelvärden för alla körningar.
		System.out.println("#########################################################");
		System.out.println("#							#");
		System.out.println("# Average of: "+ replications +" replications with			#");
		System.out.println("#							#");
		System.out.println("# Seed:						" + seed + "	#");
		System.out.println("# Replications:					" + replications + "	#");
		System.out.println("# Channels:					" + channels + "	#");
		System.out.println("# Reserved Channels:				"  + reservedChannels +  " 	#");
		System.out.println("#							#");
		System.out.println("#########################################################");
		System.out.println("# Average total calls:			" + String.format("%.5g",(double) sumTotalCalls/replications) + "		#");
		System.out.println("# Average blocked calls:: 		" + String.format("%.5g",(double) sumBlockedCalls/replications) + "		#");
		System.out.println("# Average dropped calls:		" +  String.format("%.5g",(double) sumDroppedCalls/replications) + "		#");
//		System.out.println("# Total average Ended Calls:		" + String.format("%.5g",(double) sumEndedCalls/replications) + "		#");
		System.out.println("# Percent blocked calls:		" + String.format("%.5g",(double) 100*sumBlockedCalls/sumTotalCalls)+ " %	#");
		System.out.println("# Percent dropped calls:		" + String.format("%.5g",(double) 100*sumDroppedCalls/(sumTotalCalls-sumBlockedCalls)) + " %	#");
		System.out.println("#########################################################");
	}

}
