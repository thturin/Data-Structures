import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.time.LocalTime;
import java.util.ArrayList;

public class Program {

	public static void main(String[] args) throws IOException {
		String  inFile = args[0];
		//String  inFile = "src/customersfile.txt";
	
		String outFile = args[1];
		//String outFile = "queriesfile.txt";
		
		ArrayList<customer> customerList = new ArrayList<customer>();
		QLinkedList QList = new QLinkedList();
		int serviceTime = 0;
		
		inFile(inFile,serviceTime,customerList);//converting my customersfile.txt into a list of customers	
		serviceTime=serviceTimeIn(inFile);///set service time

		customerToNodeList(customerList, QList); //converting my list of customers to a queue list
		QList.getDesk().setServiceT(serviceTime);
		queryFile("queriesfile.txt",QList);
		
		//QList.query(outFile);
	}
	
	
	public static void customerToNodeList(ArrayList<customer> cList, QLinkedList QList){ //convert list of customers to nodes in a linked list		
		int id=1;
		for(int i=0;i<cList.size();i++){					
			QList.enQueue(cList.get(i).getArrival(), id);
			id++;
		}
	}
	
	
	public static int serviceTimeIn(String inFile) throws IOException{
		BufferedReader br = null;
		String line="";
		br=new BufferedReader(new FileReader(inFile));
		line = br.readLine();
		int serviceTime=Integer.parseInt(line);
		return serviceTime;
	}
	public static void inFile(String f, int serviceTime, ArrayList<customer> customerList) throws IOException, FileNotFoundException{
		BufferedReader br = null;
		String line="";
		String splitBy=":"; int count = 1;
		try{
			br= new BufferedReader(new FileReader(f));	
			line = br.readLine(); //first line
			br.readLine();//read over '300' line 
			String[] temp = line.split(splitBy);//convert line to string[]		
			//serviceTime = Integer.parseInt(temp[0]);//move single element "300" into service time integer
			while((line=br.readLine())!=null){
				
				if(line.charAt(0)==('A') ){//if line # is even (containing arrival time)		
					customer c = new customer(count/2);//create a new customer with id #
					String stringey = line;				
					stringey=stringey.replace("ARRIVAL-TIME: ", "");
					LocalTime t = c.stringToTime(stringey);					
					c.setArrival(t);
					customerList.add(c);
					br.readLine();
				}			
				count++;
			}		
		}catch(IOException e){
			System.out.println("There was an error");
			e.printStackTrace();
		}
	}
//	
	public static void queryFile(String f, QLinkedList QList){
		BufferedReader br = null;
		String line="";
		ArrayList<Integer> breakT = QList.idleTime();
		
		try{
			br= new BufferedReader(new FileReader(f));	
			while((line=br.readLine())!=null){
				if(line.contains("NUMBER-OF-CUSTOMERS-SERVED: ")){
					System.out.println("NUMBER-OF-CUSTOMERS-SERVED: "+QList.totalServed());
				}
				if(line.contains("LONGEST-BREAK-LENGTH: ")){
					System.out.println("LONGEST-BREAK-LENGTH: "+breakT.get(1));
				}
				
				if(line.contains("TOTAL-IDLE-TIME: ")){
					System.out.println("TOTAL-IDLE-TIME: "+breakT.get(0));
				}				
				if(line.contains("MAXIMUM-NUMBER-OF-PEOPLE-IN-QUEUE-AT-ANY-TIME: ")){
					System.out.println("MAXIMUM-NUMBER-OF-PEOPLE-IN-QUEUE-AT-ANY-TIME: "+QList.peopleInQueue());
				}
				
				if(line.contains("WAITING-TIME-OF ")){
					int customerNum = Integer.parseInt(line.substring(16,17));
					Node temp = QList.findNode(customerNum);					
					System.out.println("WAITING-TIME-OF "+customerNum+": "+QList.waitTime(temp));
				}
				
			}		
		}catch(IOException e){
			System.out.println("There was an error");
			e.printStackTrace();
		}
	}

	
}
