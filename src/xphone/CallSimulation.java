package xphone;

import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

public class CallSimulation {

	final double ARRIVALRATE =  0.4213;
	final double DURATION = 0.0051;

	final double MEAN = 121.495;
	final double STD = 67.5578;
	final double B = 39.9829;

	final static int NUMBEROFBASESTATIONS = 20;
	final static int LENGTHOFHIGHWAY = 40;
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

	private int droppedCalls = 0;
	private int blockedCalls = 0;
	private int endedCalls = 0;

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

		// Beräkna första samtalet
		startTime = calculateInterArrivalTime();
		endTime = startTime + calculateCallDuration();



		// Skapa första samtalet
		call = new Call(totalCalls, calculateStartPosition(), calculateSpeed(), startTime, endTime);
		startCall = new StartCall(totalCalls, call, startTime);

		if(clock>warmUp)
			totalCalls++;

		// Lägg till i eventList
		fel.insertSorted(startCall);

		do{
			// Beta av eventList
			currentEvent = fel.getFirst();
			clock = currentEvent.getTime();

			eventHandler(currentEvent);
			fel.removeFirst();
			if((clock > length) && (fel.size() <= 0)) // kolla om vi ska bryta
				break; 
		}while(felIt.hasNext());
	}

	public void eventHandler(Event ev) {
		if(ev instanceof StartCall) {
			// Hämta samtalet från eventet
			call = ev.getCall();

			// Hämta samtalets basstation
			base = highway[(int) (call.getPosition()/RADIUS)%NUMBEROFBASESTATIONS];

			if(base.isFull()){

				if(clock>warmUp)
					blockedCalls++;
			} else {

				base.allocateChannel();

				double distanceToHandover = base.getEndRadius() - call.getPosition();
				double distanceToEndCall = call.getPositionEndCall() - call.getPosition();

				// Beräkna nästa eventtyp för samtalet
				currentEvent = call.generateNextEvent(clock, distanceToHandover, distanceToEndCall);
				fel.insertSorted(currentEvent);
			}

			// Beräkna nästa samtal
			startTime = clock + calculateInterArrivalTime();
			endTime = startTime + calculateCallDuration();

			call = new Call(totalCalls, calculateStartPosition(), calculateSpeed(), startTime, endTime);
			startCall = new StartCall(totalCalls, call, startTime);

			if(clock>warmUp)
				totalCalls++;

			if(length >= startCall.getTime()){ //Om uträknad tid överskrider stängningstid, neka samtal
				fel.insertSorted(startCall);
			}
		}
		else if(ev instanceof Handover) {
			call = ev.getCall();

			if(call.getPosition() >= LENGTHOFHIGHWAY){
				call.setPositionEndCall(call.getPositionEndCall() - LENGTHOFHIGHWAY);
			}

			// Beräkna nuvarande basstation
			base = highway[(int) (call.getPosition()/RADIUS)%NUMBEROFBASESTATIONS];

			base.unAllocateChannel();

			// Beräkna var samtalet är nu
			double newPosition = (base.getEndRadius());

			int baseIndex = ((int) ((call.getPosition()/RADIUS))+1);

			base = highway[(baseIndex%NUMBEROFBASESTATIONS)];

			if(base.isFull()){
				if(base.isReservationAvailable()){

					base.allocateReservedChannel();
					call.setPosition(newPosition);

					double distanceToHandover = base.getEndRadius() - (call.getPosition()%LENGTHOFHIGHWAY);
					double distanceToEndCall = call.getPositionEndCall() - call.getPosition();

					//Beräkna nästa event församtalet
					currentEvent = call.generateNextEvent(clock, distanceToHandover, distanceToEndCall);
					fel.insertSorted(currentEvent);

				} else {
					if(clock>warmUp){
						droppedCalls++;
					}
				} 
			} else {
				base.allocateChannel();
				call.setPosition(newPosition);

				double distanceToHandover = base.getEndRadius() - (call.getPosition()%LENGTHOFHIGHWAY);

				double distanceToEndCall = call.getPositionEndCall() - call.getPosition();

				//Beräkna nästa event församtalet
				currentEvent = call.generateNextEvent(clock, distanceToHandover, distanceToEndCall);
				fel.insertSorted(currentEvent);
			}
		}
		else if(ev instanceof EndCall) {
			call = ev.getCall();
			if(clock>warmUp)
				endedCalls++;

			base = highway[(int) (call.getPositionEndCall()/RADIUS)%NUMBEROFBASESTATIONS];
			base.unAllocateChannel();
			callSink.add(call);
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

	public int getTotalCalls() {
		return totalCalls;
	}

	public int getDroppedCalls() {
		return droppedCalls;
	}

	public int getBlockedCalls() {
		return blockedCalls;
	}

	public int getEndedCalls() {
		return endedCalls;
	}

}
