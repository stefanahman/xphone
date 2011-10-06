package xphone;

public class Handover extends Event {
	
	public Handover(int id, Call call, double timeCreated, double timeToRun) {
		super(id, call, timeToRun);
		this.timeCreated = timeCreated;
	}
	
}
