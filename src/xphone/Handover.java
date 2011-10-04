package xphone;

public class Handover extends Event {
	
	private BaseStation currentBaseStation;
	private BaseStation nextBaseStation;
	
	
	
	public Handover(int id, Call call, double time, BaseStation currentBaseStation, BaseStation nextBaseStation) {
		super(id, call, time);
		// TODO Auto-generated constructor stub
	}

	public BaseStation getCurrentBaseStation() {
		return currentBaseStation;
	}

	public BaseStation getNextBaseStation() {
		return nextBaseStation;
	}

}
