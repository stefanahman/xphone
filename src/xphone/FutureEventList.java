package xphone;
import java.util.LinkedList;


public class FutureEventList extends LinkedList<Event>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FutureEventList() {
		super();
	}
	
	public void insertSorted(Event evt) {
		if(this.isEmpty()){
			this.add(evt);
		}
		else{
			for(int i = this.size()-1; i >= 0; i--) {
				int ans = evt.compareTo(this.get(i));
				if(ans >= 0) {
					this.add(i+1, evt);
					break;
				}
			}
		}
	}
}