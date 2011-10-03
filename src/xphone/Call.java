package xphone;

public class Call {
	
	private int id;
	private double startTime;
	private double endTime;
	private double durationTime;
	private int noOfHandovers;	
	
	public Call(int id, double startTime, double endTime) {
		this.id = id;
		this.startTime = startTime;
		this.endTime = endTime;
		this.durationTime = this.calculateCallDuration();
		this.endTime = this.startTime + this.durationTime;
	}
	
	public int calculateNoOfHandovers() {
		//TODO: Beräkna hur många handovers och vilka basstationer det gäller
		return noOfHandovers;
	}
	
	public double calculateCallDuration() {
		
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
