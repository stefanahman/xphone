package xphone;

import java.util.Random;

public final class RandomGaussian {
	  
	  public static void main(String... aArgs){
	    RandomGaussian gaussian = new RandomGaussian();
	    double MEAN = 121.5578f; 
	    double VARIANCE = 67.5578f;
	    for (int idx = 1; idx <= 10; ++idx){
	      log(" " + gaussian.getGaussian(MEAN, VARIANCE));
	    }
	  }
	    
	  private Random fRandom = new Random();
	  
	  private double getGaussian(double aMean, double aVariance){
	      return aMean + fRandom.nextGaussian() * aVariance;
	  }

	  private static void log(Object aMsg){
	    System.out.println(String.valueOf(aMsg));
	  }
	} 

