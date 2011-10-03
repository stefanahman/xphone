package xphone;

import java.util.Random;

import com.sun.org.apache.bcel.internal.generic.NEW;

import cern.jet.random.Normal;
import cern.jet.random.engine.RandomEngine;


public final class RandomGaussian2 {
	
	static double mean = 121.495;
	static double std = 67.5578;
	static double b = 39.9829;
	static Random generator = new Random();
	public static void main(String[] args) {
	
		double s = generator.nextDouble();
		for(int i = 0;i < 800; i++) {
			s = generator.nextDouble();
			System.out.println(s*b);
		}
		double t = generator.nextGaussian();
		for(int i = 0;i < 800; i++) {
			t = generator.nextGaussian();
			System.out.println(t*Math.sqrt(std) + mean);
		}
	}
} 

