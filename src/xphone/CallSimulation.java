package xphone;

import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

public class CallSimulation {

	final double ARRIVALRATE = 0.4213;
	final double DURATION = 0.0051;
	
	final double MEAN = 121.495;
	final double STD = 67.5578;
	final double B = 39.9829;
	
	final int NUMBEROFBASESTATIONS = 20;
	final int LENGTHOFHIGHWAY = 40;
	final int RADIUS = 2;

	private double clock = 0;

	private Random rnd = new Random();

	private double length = 0;
	private double warmUp = 0;
	private int channels = 0;
	private int reservedChannels = 0;

	private int totalCalls = 0;

	private FutureEventList fel = new FutureEventList();
	private Iterator<Event> felIt = fel.listIterator();

	private Call call;
	private Vector<Call> callSink = new Vector<Call>();
	
	private BaseStation base;
	private BaseStation[] highway = new BaseStation[NUMBEROFBASESTATIONS];

	private Event currentEvent;
	private StartCall startCall;
	private EndCall endCall;
	private Handover handover;
	
	private double startTime;
	private double endTime;

	public CallSimulation(long rndSeed, int length, int warmUp, int channels, int reservedChannels) {
		this.length = length;
		this.warmUp = warmUp;
		this.channels = channels;
		this.reservedChannels = reservedChannels;

		rnd.setSeed(rndSeed);
		
		for(int i = 0;i < NUMBEROFBASESTATIONS; i++){
			highway[i] = new BaseStation(i, this.channels, this.reservedChannels);
		}

		run();
	}

	public void run() {
		clock = 0;
		
		startTime = calculateInterArrivalTime();
		endTime = startTime + calculateCallDuration();
		
		call = new Call(totalCalls, calculateStartPosition(), calculateSpeed(), startTime, endTime);
		startCall = new StartCall(totalCalls, call, startTime);
		totalCalls++;
		
		fel.insertSorted(startCall);

		do{
			currentEvent = fel.getFirst();
			clock = currentEvent.getTime();
			
			eventHandler(currentEvent);
			fel.removeFirst();
			if((clock > length) && (fel.size() <= 0)) // kolla om vi ska bryta
				break; 
		}while(felIt.hasNext()); // ev (felIt.hasNext())


	}

	public void eventHandler(Event ev) {
		if(ev instanceof StartCall) {
			
//			When user initiates a call, a channel has to be allocated in the
//			base station covering the users current position
			
			call = ev.getCall();
			System.out.println("Call "+ call.getId() +" started at:	time: " + call.getStartTime()+ ",	position: " + call.getPosition() +",	Station: " + (int) (call.getPosition()/RADIUS)%NUMBEROFBASESTATIONS);
			
			// Check if basestation is full, if then, block call
			
			base = highway[(int) (call.getPosition()/RADIUS)%NUMBEROFBASESTATIONS];
			if(base.isFull()){
//				System.out.println("Call blocked, all channel's full");
			} else {
				System.out.println("Call "+ call.getId() +" pla to end at:	time: " + call.getEndTime() + ",	position: " + call.getPositionEndCall()  +",	Station: " + ((int) (call.getPositionEndCall()/RADIUS)%NUMBEROFBASESTATIONS));
				
				base.allocateChannel();
				
				double distanceToHandover = base.getEndRadius() - call.getPosition();
				double distanceToEndCall = call.getPositionEndCall() - call.getPosition();
				
				System.out.println("Call " + call.getId() + " distanceToHandover " + distanceToHandover);
				System.out.println("Call " + call.getId() + " distanceToEndCall " + distanceToEndCall);
				
				currentEvent = call.generateNextEvent(clock, distanceToHandover, distanceToEndCall);
				fel.insertSorted(currentEvent);
			}
			
//			System.out.println(base.toString()); 
			
			// Calculate next event
			startTime = clock + calculateInterArrivalTime();
			endTime = startTime + calculateCallDuration();

			call = new Call(totalCalls, calculateStartPosition(), calculateSpeed(), startTime, endTime);
			startCall = new StartCall(totalCalls, call, startTime);
			totalCalls++;
			
			if(length >= startCall.getTime()) //Om uträknad tid överskrider stängningstid, neka samtal
				fel.insertSorted(startCall);
			//TODO: Stoppa ej in i fel vid slut av tid.
		}
		else if(ev instanceof Handover) {
			
			call = ev.getCall();
			
			double newPosition = ((call.getPosition() + ((clock-ev.getTimeCreated())/3600*call.getSpeed())) % LENGTHOFHIGHWAY);
			
			System.out.println("Call "+ call.getId() +" new position: " + newPosition + "km at: " + ev.time+"s");
			int baseIndex = ((int) ((call.getPosition()/RADIUS))%NUMBEROFBASESTATIONS)+1;
			if (baseIndex >= 19) {
				baseIndex = 0;
			}
			System.out.println("baseIndex " + baseIndex);
			base = highway[baseIndex];
//			System.out.println("BaseId " + base.id);
			
//			System.out.println("newPosition " + newPosition);
			call.setPosition(newPosition);
			
			double distanceToHandover = base.getEndRadius() - call.getPosition();
			double distanceToEndCall = call.getPositionEndCall() - call.getPosition();
//			System.out.println("distanceToHandover " + distanceToHandover);
//			System.out.println("distanceToEndCall " + distanceToEndCall);
			
			currentEvent = call.generateNextEvent(clock, distanceToHandover, distanceToEndCall);
			fel.insertSorted(currentEvent);
//			avallokera kanalen i aktuell basstation 
			
//			om nya stationen har ledig channel allokera denna
//			och beräkna nästa handover eller endCall	
//			om full: finns ledig reservedChannel -> använd denna och beräkna nästa handover eller endCall
//			annars: dropCall
			
		}
		else if(ev instanceof EndCall) {
			call = ev.getCall();
			double endPosition = call.getPosition() + (call.getCallDuration()/60/60*call.getSpeed());
			//System.out.println("End position: " + endPosition);
			base = highway[(int) (call.getPosition()/RADIUS)%NUMBEROFBASESTATIONS];
			base.unAllocateChannel();
			callSink.add(call);
			
			System.out.println("Call: " + call.getId() + " ended at: " + call.getEndTime());			
		}
	}

	public double calculateInterArrivalTime() {
		double rndSeed = rnd.nextDouble();
		return -Math.log(rndSeed)/ARRIVALRATE;
	}

	public double calculateCallDuration() {
		double rndSeed = rnd.nextDouble();
		return -Math.log(rndSeed)/DURATION;
	}
	
	public double calculateStartPosition() {
		return rnd.nextDouble()*B;
	}
	
	public double calculateSpeed() {
		return rnd.nextGaussian()*Math.sqrt(STD) + MEAN;
	}
}
