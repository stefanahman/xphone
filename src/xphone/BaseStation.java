package xphone;

public class BaseStation {

	int id;
	int channels = 0;
	int reservedChannels = 0;
	int currentUsedChannels = 0;
	int currentUsedReservedChannels = 0;
	int radius = 2;
	
	BaseStation(int id, int channels, int reservedChannels){
		this.id = id;
		this.channels = channels;
		this.reservedChannels = reservedChannels;
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
	
	public int getCurrentUsedChannels() {
		return currentUsedChannels;
	}
	
	public void setCurrentUsedChannels(int currentUsedChannels) {
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
	
	public int getStartRadius(){
		return this.id*this.radius;
	}
	
	public int getEndRadius(){
		return this.id*this.radius+this.radius;
	}
	
	public String toString(){
		String out = "";
		out += "Basestation id: " + this.id + "\n";
		out += "Range: " + getStartRadius() + " - " + getEndRadius() + "\n";
		out += "Used std. channels: " + getCurrentUsedChannels() + "\n";
		out += "Used res. channels: " + getCurrentUsedReservedChannels() + "\n";
		return out;
		
	}
}
