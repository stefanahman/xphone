package xphone;

public class BaseStation {

	int id;
	int channels;
	int reservedChannels;
	int currentNumberOfCalls;
	int radius;
	
	BaseStation(int id, int channels, int radius){
		this.id = id;
		this.channels = channels;
		this.radius = radius;
	}
	
	public int getId() {
		return id;
	}

	public int getChannels() {
		return channels;
	}

	public int getReservedChannels() {
		return reservedChannels;
	}
	
	public void setReservedChannels(int reservedChannels) {
		this.reservedChannels = reservedChannels;
	}
	
	public int getCurrentNumberOfCalls() {
		return currentNumberOfCalls;
	}
	
	public void setCurrentNumberOfCalls(int currentNumberOfCalls) {
		this.currentNumberOfCalls = currentNumberOfCalls;
	}
	
	public int getRadius() {
		return radius;
	}
	
	
}
