package xphone;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class Log {

	public Log() {
		// TODO Auto-generated constructor stub
	}
	
	public void blockedCall(Double time) {
//		print time to file blockedCallsAt.txt
		try{
		Writer output = null;
		  String text = Double.toString(time);
		  File file = new File("write.txt");
		  output = new BufferedWriter(new FileWriter(file));
		  output.write(text);
		  output.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	
	
	public void droppedCall(double time) {
//		print time to file droppedCallsAt.txt
		try{
			  // Create file 
			FileOutputStream stream = new FileOutputStream("myfileDropped.txt");
			OutputStreamWriter out = new OutputStreamWriter(stream, "US-ASCII");
			out.write(String.valueOf(time));
			  //Close the output stream
			  out.close();
			  }catch (Exception e){//Catch exception if any
			  System.err.println("Error: " + e.getMessage());
			  }
	}
	
	
}
