package xphone;

public class Xphone {
	
	static long seed = 0;
	static int length = 0; 
	static int replications = 0;
	static int warmUp = 0;
	static int channels = 0;
	static int reserved = 0;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		seed = (long)Integer.parseInt(args[0]);
		length = Integer.parseInt(args[1]);
		replications = Integer.parseInt(args[2]);
		
	}

}
