package xphone;

import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

public class CallSimulation {
	
//	 final  int a = 0;
//	 final  int ba = 800;
//	 final  int c = 200;

	final double ARRIVALRATE = 0.4213;
	final double DURATION = 0.0051;
	
	final double MEAN = 121.495;
	final double STD = 67.5578;
	final double B = 39.9829;
	
	final static int NUMBEROFBASESTATIONS = 20;
	final static int LENGTHOFHIGHWAY = 40;
	final int RADIUS = 2;

	private double clock = 0;

	private Random rnd = new Random();
	
	private Log log = new Log();

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
		}while(felIt.hasNext()); // ev (felIt.hasNext())
		
//		System.out.println("Blocked: " + blockedCalls);
//		System.out.println("Dropped: " + droppedCalls);
//		System.out.println("Ended: " + endedCalls);
//		System.out.println("Total: " + totalCalls);

	}

	public void eventHandler(Event ev) {
		if(ev instanceof StartCall) {
			// Hämta samtalet från eventet
			call = ev.getCall();
//			System.out.println("-- START CALL " + call.getId() + " --");
			
//			System.out.println("Time: " + call.getStartTime());
//			System.out.println("Position: " + call.getPosition());
			
			// Hämta samtalets basstation
			base = highway[(int) (call.getPosition()/RADIUS)%NUMBEROFBASESTATIONS];
//			System.out.println("Station: " + base.id);
			
			if(base.isFull()){
//				System.out.println("Call blocked, all channel's full");
//				System.out.println(base.toString());
				if(clock>warmUp)
					blockedCalls++;
//				log.blockedCall(clock);
			} else {
//				System.out.println("Planning to end:");
//				System.out.println("Time: " + call.getEndTime());
//				System.out.println("Position: " + call.getPositionEndCall());
//				System.out.println("Station: " + ((int) (call.getPositionEndCall()/RADIUS)%NUMBEROFBASESTATIONS));
				
				base.allocateChannel();
				
				double distanceToHandover = base.getEndRadius() - call.getPosition();
				double distanceToEndCall = call.getPositionEndCall() - call.getPosition();
				
//				System.out.println("Call " + call.getId() + " distanceToHandover " + distanceToHandover);
//				System.out.println("Call " + call.getId() + " distanceToEndCall " + distanceToEndCall);
				
				// Beräkna nästa eventtyp för samtalet
				currentEvent = call.generateNextEvent(clock, distanceToHandover, distanceToEndCall);
				fel.insertSorted(currentEvent);
			}
			
//			System.out.println(base.toString()); 
//			System.out.println(" ");
			
			// Beräkna nästa samtal
			startTime = clock + calculateInterArrivalTime();
			endTime = startTime + calculateCallDuration();

			call = new Call(totalCalls, calculateStartPosition(), calculateSpeed(), startTime, endTime);
			startCall = new StartCall(totalCalls, call, startTime);
			
			
			if(length >= startCall.getTime()){ //Om uträknad tid överskrider stängningstid, neka samtal
				if(clock>warmUp)
					totalCalls++;
				fel.insertSorted(startCall);
			}
			//TODO: Stoppa ej in i fel vid slut av tid.
		}
		else if(ev instanceof Handover) {
//			System.out.println(" ");
			call = ev.getCall();
//			System.out.println(" -- HANDOVER CALL " + call.getId() + " --");
			// Hämta samtalet från eventet
			
//			System.out.println("Time " + clock + "s");
			
			if(call.getPosition() >= 40){
				call.setPositionEndCall(call.getPositionEndCall() - 40);
			}
			
//			System.out.println("Position before: " + call.getPosition() + "km");
			
			// Beräkna nuvarande basstation
			base = highway[(int) (call.getPosition()/RADIUS)%NUMBEROFBASESTATIONS];
			
			base.unAllocateChannel();
			
//			System.out.print("Base " + base.id + " --> ");
			// Beräkna var samtalet är nu
			double newPosition = (base.getEndRadius());
			
			
			int baseIndex = ((int) ((call.getPosition()/RADIUS))+1);
			
			base = highway[(baseIndex%NUMBEROFBASESTATIONS)];
			
			if(base.isFull()){
				if(base.isReservationAvailable()){
					
					base.allocateReservedChannel();
//					System.out.println(base.id);
					
					call.setPosition(newPosition);
		//			System.out.println("New position: " + call.getPosition() + "km ");
		//			System.out.println("baseIndex " + baseIndex);
		//			System.out.println("baseIndexMod " + (baseIndex%NUMBEROFBASESTATIONS));
		//			
		//			System.out.println("getEndRadius " + base.getEndRadius());
		//			System.out.println("getPosition " + call.getPosition());
					
					double distanceToHandover = base.getEndRadius() - (call.getPosition()%LENGTHOFHIGHWAY);
					
		//			System.out.println("baseEndPosition " + base.getEndRadius());
					double distanceToEndCall = call.getPositionEndCall() - call.getPosition();
					
		//			System.out.println("distanceToHandover " + distanceToHandover);
		//			System.out.println("distanceToEndCall " + distanceToEndCall);
					
					
					//Beräkna nästa event församtalet
					currentEvent = call.generateNextEvent(clock, distanceToHandover, distanceToEndCall);
					fel.insertSorted(currentEvent);
		//			avallokera kanalen i aktuell basstation 
					
		//			om nya stationen har ledig channel allokera denna
		//			och beräkna nästa handover eller endCall	
		//			om full: finns ledig reservedChannel -> använd denna och beräkna nästa handover eller endCall
		//			annars: dropCall
//					System.out.println(" ");
				} else {
					if(clock>warmUp)
						droppedCalls++;
//					log.droppedCall(clock);
//					System.out.println("Called is dropped");
				} 
			} else {
				base.allocateChannel();
				call.setPosition(newPosition);
				//			System.out.println("New position: " + call.getPosition() + "km ");
				//			System.out.println("baseIndex " + baseIndex);
				//			System.out.println("baseIndexMod " + (baseIndex%NUMBEROFBASESTATIONS));
				//			
				//			System.out.println("getEndRadius " + base.getEndRadius());
				//			System.out.println("getPosition " + call.getPosition());
							
							double distanceToHandover = base.getEndRadius() - (call.getPosition()%LENGTHOFHIGHWAY);
							
				//			System.out.println("baseEndPosition " + base.getEndRadius());
							double distanceToEndCall = call.getPositionEndCall() - call.getPosition();
							
				//			System.out.println("distanceToHandover " + distanceToHandover);
				//			System.out.println("distanceToEndCall " + distanceToEndCall);
							
							
							//Beräkna nästa event församtalet
							currentEvent = call.generateNextEvent(clock, distanceToHandover, distanceToEndCall);
							fel.insertSorted(currentEvent);
			}
		}
		else if(ev instanceof EndCall) {
//			System.out.println("-- END CALL --");
			call = ev.getCall();
			if(clock>warmUp)
				endedCalls++;
			//System.out.println("End position: " + endPosition);
			base = highway[(int) (call.getPositionEndCall()/RADIUS)%NUMBEROFBASESTATIONS];
			base.unAllocateChannel();
			callSink.add(call);
			
//			System.out.println("Call: " + call.getId() + " ended at: " + call.getEndTime());			
		}
//		System.out.println(" ");
//		System.out.println("-- AFTER --");
//		
//		for (int i = 0; i < NUMBEROFBASESTATIONS; i++) {
//			System.out.println(highway[i].toString());
//		}
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

//	public double calculate() {
//		double random = rnd.nextDouble();
//		if(random <= 0.25)
//			return a + Math.sqrt(random*(ba-a)*(c-a));
//		else
//			return ba - Math.sqrt(random*(ba-a)*(ba-c));
//	}
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
