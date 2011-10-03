package xphone;

public class BaseStation {

	int id;
	int channels = 0;
	int reservedChannels = 0;
	int currentUsedChannels = 0;
	int currentUsedReservedChannels = 0;
	int radius = 0;
	
	BaseStation(int id, int channels, int reservedChannels, int radius){
		this.id = id;
		this.channels = channels;
		this.reservedChannels = reservedChannels;
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
	
	public int getCurrentNumberOfCalls() {
		return currentUsedChannels;
	}
	
	public void setCurrentNumberOfCalls(int currentUsedChannels) {
		this.currentUsedChannels = currentUsedChannels;
	}
	
	public int getRadius() {
		return radius;
	}
	
	public int getCurrentUsedReservedChannels() {
		return currentUsedReservedChannels;
	}

	public void setCurrentUsedReservedChannels(int currentUsedReservedChannels) {
		this.currentUsedReservedChannels = currentUsedReservedChannels;
	}

	public void setReservedChannels(int reservedChannels) {
		this.reservedChannels = reservedChannels;
	}

	public boolean isFull() {
		return currentUsedChannels >= (channels - reservedChannels);
	}
	
	public boolean isReservationAvailable() {
		return currentUsedReservedChannels < reservedChannels;
	}
	
}
