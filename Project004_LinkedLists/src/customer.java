
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;


public class customer {
	private int id;
	private LocalTime arrival;
	customer(){}
	customer (int id){this.id=id;}
	
	public int getId(){return id;}
	public void setArrival(LocalTime t){this.arrival = t;}
	public LocalTime getArrival(){return arrival;}	
	public LocalTime stringToTime(String s){
		char c = s.charAt(0);//hour that will be changed hh string format
		int n = Integer.valueOf(String.valueOf(c));//char -> integer
		if(n>1 &&n<6){			
			int newN = n+12;//new military time to be inserted
			char newNChar = Integer.toString(newN).charAt(1);
			s = s.replace(c,newNChar);//now the correct 
			String temp = "1"+s; 
			s = temp;
		}	
		///CODE ABOVE CONVERTS TIME TO MILITARY TIME IN A VERY CRUDE WAY		
		String[] arr = s.split(":");
		String temp = arr[0];
		int hr = Integer.parseInt(temp);
		temp = arr[1];		
		int min = Integer.parseInt(temp);
		temp = arr[2];
		int sec = Integer.parseInt(temp);
			
		LocalTime time = LocalTime.of(hr, min,sec);
		return time;
	}
	
	public String toString(){
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		String theDate = formatter.format(arrival);
		return theDate;
	}
	
	
}

