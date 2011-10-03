package xphone;

public class Event {
	double time;
	int id;

	public Event(int id, double time) {
		this.id = id;
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

	public int compareTo(Event evt) {
		if(this.getTime() > evt.getTime())
			return 1;
		if(this.getTime() == evt.getTime())
			return 0;
		else
			return -1;
	}
}
