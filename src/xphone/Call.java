package xphone;

public class Call {
	
	private int id;
	private double startTime;
	private double endTime;
	private double durationTime;
	private int noOfHandovers;
	private double speed;
	private double position;
	private double positionEndCall;
	
	public void setPositionEndCall(double positionEndCall) {
		this.positionEndCall = positionEndCall;
	}

	public Call(int id, double position, double speed, double startTime, double endTime) {
		this.id = id;
		this.position = position;
		this.speed = speed;
		this.startTime = startTime;
		this.endTime = endTime;
		this.durationTime = this.endTime - this.startTime;
		this.endTime = this.startTime + this.durationTime;
		this.positionEndCall = this.position + (this.durationTime * this.speed)/60/60;
	}
	
	public double getSpeed() {
		return speed;
	}

	public void setPosition(double position) {
		 this.position = position;
	}
	
	public double getPosition() {
		return position;
	}

	public int calculateNoOfHandovers() {
		//TODO: Beräkna hur många handovers och vilka basstationer det gäller
		return noOfHandovers;
	}
	
	public double getCallDuration() {
		return this.durationTime;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the startTime
	 */
	public double getStartTime() {
		return startTime;
	}

	/**
	 * @return the endTime
	 */
	public double getEndTime() {
		return endTime;
	}

	/**
	 * @return the noOfHandovers
	 */
	public int getNoOfHandovers() {
		return noOfHandovers;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(double endTime) {
		this.endTime = endTime;
	}
	
	public double getPositionEndCall(){
		return positionEndCall;
	}
	
	public Event generateNextEvent(double currentTime, double distanceToHandover, double distanceToEndCall) {
		if (distanceToHandover >= distanceToEndCall) {
//			System.out.println("Call " + this.id + " NEW ENDCALL " + endTime);
			return (new EndCall(id, this, endTime));
		} else {
//			System.out.println("Call " + this.id + " NEW HANDOVER AT: " + (currentTime + distanceToHandover*60*60/speed));
			return (new Handover(id, this, currentTime, currentTime + distanceToHandover*60*60/speed));
		}
		
	}
	
}
