package xphone;

public class Handover extends Event {
	
	public Handover(int id, Call call,double timeCreated, double time) {
		super(id, call, time);
		// TODO Auto-generated constructor stub
		this.timeCreated = timeCreated;
	}
	
}
