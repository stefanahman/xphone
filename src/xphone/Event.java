package xphone;

public class Event {
	Call call;
	double time;
	int id;

	public Event(int id,Call call, double time) {
		this.id = id;
		this.call = call;
		this.time = time;
	}

	public int getId() {
		return id;
	}
	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
	}
	
	public Call getCall(){
		return this.call;
		
	}

	public int compareTo(Event evt) {
		if(this.getTime() > evt.getTime())
			return 1;
		if(this.getTime() == evt.getTime())
			return 0;
		else
			return -1;
	}
}
