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
	static private double sumTotalCalls = 0;
	static private double testblocked = 0;
	static private double testdropped = 0;
	

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
		
		System.out.println("#########################################################");
		System.out.println("#							#");
		System.out.println("# Welcome to xPhone!					#");
		System.out.println("#							#");
		System.out.println("#########################################################");
		System.out.println("");
		System.out.println(" Seed:			" + seed);
		System.out.println(" Replication length:	" + length + " sec.");
		System.out.println(" Replications:		" + replications);
		System.out.println(" Warm-Up:		" + warmUp + " sec.");
		System.out.println(" Channels:		" + channels);
		System.out.println(" Reserved:		" + reservedChannels);
		System.out.println("");
		System.out.println("#########################################################");
		System.out.println("");
		simulation = new CallSimulation[replications];
		
		rnd.setSeed(seed);
		
		for (int i = 0; i < replications; i++) {
			long rndSeed = rnd.nextLong();
			simulation[i] = new CallSimulation(rndSeed, length, warmUp, channels, reservedChannels);
			
			sumBlockedCalls += simulation[i].getBlockedCalls();
			sumDroppedCalls += simulation[i].getDroppedCalls();
			sumTotalCalls += simulation[i].getTotalCalls();
			testblocked += (double) 100*simulation[i].getBlockedCalls()/simulation[i].getTotalCalls();
			testdropped += (double) 100*simulation[i].getDroppedCalls()/(simulation[i].getTotalCalls()-simulation[i].getBlockedCalls());
			
			//TODO: Visa resultat för varje replication!
			System.out.println("#########################################################");
			System.out.println("#							#");
			System.out.println("# Replication. " + (i + 1) + "					#");
			System.out.println("#							#");
			System.out.println("#########################################################");
			System.out.println("");
			System.out.println(" Total Calls:		" + simulation[i].getTotalCalls());
			System.out.println(" Blocked Calls:		" + simulation[i].getBlockedCalls());
			System.out.println(" Dropped Calls:		" + simulation[i].getDroppedCalls());
//			System.out.println(" Ended Calls:			" + simulation[i].getEndedCalls());
			System.out.println(" Percent Blocked Calls:	" + String.format("%.5g",(double) 100*simulation[i].getBlockedCalls()/simulation[i].getTotalCalls()) + " %");
			System.out.println(" Percent Dropped Calls:	" + String.format("%.5g",(double) 100*simulation[i].getDroppedCalls()/(simulation[i].getTotalCalls()-simulation[i].getBlockedCalls())) + " %");
			System.out.println("");
			System.out.println("#########################################################");
			System.out.println("");



		}
		//TODO: Visa medelvärden för alla körningar.
		System.out.println("#########################################################");
		System.out.println("");
		System.out.println(" Average of: "+ replications +" replications with");
		System.out.println("");
		System.out.println(" Seed:			" + seed);
		System.out.println(" Replications:		" + replications);
		System.out.println(" Warm-up:	"  + warmUp);
		System.out.println(" Channels:		" + channels);
		System.out.println(" Reserved Channels:	"  + reservedChannels);
		System.out.println("");
		System.out.println("#########################################################");
		System.out.println("");
		System.out.println(" Average total calls:	" + String.format("%.5g",(double) sumTotalCalls/replications));
		System.out.println(" Average blocked calls: " + String.format("%.5g",(double) sumBlockedCalls/replications));
		System.out.println(" Average dropped calls:	" +  String.format("%.5g",(double) sumDroppedCalls/replications));
		System.out.println(" Percent blocked calls:	" + String.format("%.5g",(double) testblocked/replications)+ " %");
		System.out.println(" Percent dropped calls:	" + String.format("%.5g",(double) testdropped/replications) + " %");
		System.out.println("");
		System.out.println("#########################################################");
	}

}
