package xphone;

import java.util.Iterator;
import java.util.Random;

public class CallSimulation {

	//TODO: Lägg till värden
	final double ARRIVALRATE = 0.4213;
	final double DURATION = 0.0051;

	private double clock = 0;

	private Random rnd = new Random();

	private double length = 0;
	private double warmUp = 0;
	private int channels = 0;
	private int reservedChannels = 0;

	private int totalCalls = 0;

	private FutureEventList fel = new FutureEventList();
//	private Iterator<Event> felIt = fel.listIterator();

	private Call call;
	private Call[] calls;

	private Event currentEvent;
	private StartCall startCall;
	private EndCall endCall;
	private Handover handover;



	public CallSimulation(long rndSeed, int length, int warmUp, int channels, int reservedChannels) {
		this.length = length;
		this.warmUp = warmUp;
		this.channels = channels;
		this.reservedChannels = reservedChannels;

		rnd.setSeed(rndSeed);

		run();
	}

	public void run() {
		clock = 0;
		totalCalls++;
		double startTime = clock + calculateInterArrivalTime();
		double endTime = clock + calculateCallDuration();

		call = new Call(totalCalls, clock + startTime, clock + endTime);
		startCall = new StartCall(totalCalls, startTime);
		fel.insertSorted(startCall);

		do{
			currentEvent = fel.getFirst();
			clock= currentEvent.getTime();

			eventHandler(currentEvent);
			fel.removeFirst();
			if((clock > length) && (fel.size() <= 0)) // kolla om vi ska bryta
				break; 
		}while(!fel.isEmpty()); // ev (felIt.hasNext())


	}

	public void eventHandler(Event ev) {
		if(ev instanceof StartCall) {
//			When user initiates a call, a channel has to be allocated in the
//			base station covering the users current position
			
//			om basstation är full block call. 
			
		}
		else if(ev instanceof Handover) {
			
//			avallokera kanalen i aktuell basstation 
			
//			om nya stationen har ledig channel allokera denna
//			och beräkna nästa handover eller endCall	
//			om full: finns ledig reservedChannel -> använd denna och beräkna nästa handover eller endCall
//			annars: dropCall
			
		}
		else if(ev instanceof EndCall) {
			
//			When the user finishes the call:
//			-a channel in the current base station is freed
			
		}
	}

	public double calculateInterArrivalTime() {
		double rndSeed = rnd.nextDouble();
		double iat = -Math.log(rndSeed)/ARRIVALRATE;
		return iat;
	}

	public double calculateCallDuration() {
		double rndSeed = rnd.nextDouble();
		double cd = -Math.log(rndSeed)/DURATION;
		return cd;
	}
}
