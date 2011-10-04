package xphone;

public class Call {
	
	private int id;
	private double startTime;
	private double endTime;
	private double durationTime;
	private int noOfHandovers;
	private double speed;
	private double position;
	
	public Call(int id, double position, double speed, double startTime, double endTime) {
		this.id = id;
		this.position = position;
		this.speed = speed;
		this.startTime = startTime;
		this.endTime = endTime;
		this.durationTime = this.getCallDuration();
		this.endTime = this.startTime + this.durationTime;
	}
	
	public double getSpeed() {
		return speed;
	}

	public double getStartPosition() {
		return position;
	}

	public int calculateNoOfHandovers() {
		//TODO: Beräkna hur många handovers och vilka basstationer det gäller
		return noOfHandovers;
	}
	
	public double getCallDuration() {
		return (this.endTime-this.startTime);
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
	
}
