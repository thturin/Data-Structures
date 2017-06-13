import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

public class QLinkedList<T> implements Queue<T> {
	private Node head;
	private ServiceDesk desk; //i have added a service desk object within the queue list
	public QLinkedList(){head=null;setDesk(new ServiceDesk());}	
	
	public ServiceDesk getDesk() {
		return desk;
	}

	public void setDesk(ServiceDesk desk) {
		this.desk = desk;
	}
	
	@Override
	public Node<T> enQueue(T item, int id) {
		Node newNode = new Node(item);//create new node with item
		newNode.setId(id);
		//make sure time is before 5pm
		int t =localTimeToSecs(newNode);
		if(t>getDesk().getCloseT()){
			System.out.println("I am sorry. Service closes at 5:00pm");
			return null;
		}
		
		if(head==null){ //if there is nothign in the Qlist, make this node the head			
			head = newNode;
			return newNode;
		}
		Node curr = head;
		Node prev = null;
		
		while(curr!=null){
			prev= curr;
			curr=curr.getNext();			
		}
		prev.setNext(newNode);	
		return newNode;	
	}
	
	@Override
	public void deQueue() {
		head = head.getNext();		
	}
	
	@Override
	public boolean isEmpty() {
		if(head==null){
			return false;
		}
		return true;		
	}
	
	@Override
	public Node getHead() {
		head.displayNode();		
		return head;
	}
	
	public Node getLast(){
		Node curr = head;		
		while(curr.getNext()!=null){
			curr=curr.getNext();
		}
		curr.displayNode();
		return curr;
	}
	
	@Override
	public int size() {
		int count =0;
		Node curr = head;
		while(curr!=null){
			count++;
			curr=curr.getNext();
		}			
	//System.out.println("There are "+count+" nodes in the list");		
	return count;
	}
	
	public void displayQueue(){
		Node curr = head;
		while(curr!=null){
			curr.displayNode();
			curr=curr.getNext();
		}
	}	
	
	public void setHead(Node head){
		this.head=head;
	}
			
	public Node findNode(T data){
		Node curr = head;		
		while(curr!=null){
			if( (int)curr.getData()==(int)data ){
				System.out.println("found it");
				return curr;
			}
			curr=curr.getNext();
		}
		System.out.println("Sorry couldn't find that");
		return null;
	}
	
	public Node findNode(int id){
		Node curr = head;		
		while(curr!=null){
			if( (int)curr.getId()==(int)id ){
				return curr;
			}
			curr=curr.getNext();
		}
		return null;
	}
	
	public int waitTime(Node node){
		int diff = 0; int beforeCount = 0;
		Node prev = null; int wait = 0;
		Node curr = head;		
		while(curr!=node.getNext()){
			if( (int)curr.getData()<getDesk().openT ){ 
				wait = (getDesk().openT-(int)curr.getData())+(beforeCount*getDesk().serviceT);//System.out.println("wait = "+desk.openT+"-"+curr.getData()+"+"+wait);				
				beforeCount++;				
			}else{
					//System.out.println("wait = "+curr.getData()+"-"+prev.getData()+"+"+wait+"+"+desk.serviceT);
					wait = (int)curr.getData()-((int)prev.getData()+wait+getDesk().serviceT);
					if(wait>0){wait=0;}
					else{wait=wait*-1;}															
			}
			//System.out.println(wait);
			prev=curr;
			curr = curr.getNext();
		}	
		return wait;		
	}	
	
	
	
	
	
		
	public int peopleInQueue(){
		ArrayList<Integer> waitArr = new ArrayList<Integer>();
		int i=0; int count=0; int max=0;
		Node curr=head;
		while(curr!=null){			
			waitArr.add(waitTime(curr));//add wait time of current node to array
			curr=curr.getNext();			
		}
	
		for(int k = 0; k<waitArr.size()-1;k++){
			if(waitArr.get(k)>0 && waitArr.get(k+1)>0){//if current is >0 and the next person in line has a wait (>0) increment
				count++;
			}else if(waitArr.get(k)>0 && waitArr.get(k-1)>0 && waitArr.get(k+1)==0){//if the current customer is>0 but the next customer is <0, you've reached the end of the wait line
				count++;
				int temp = count;
				if(max<temp){max=temp;}
			}else if(waitArr.get(k)==0){
				count=0;
			}		
		}
		//System.out.println("The longest wait was "+ max+ " people ");
		return max;
	}
	
	public ArrayList<Integer> idleTime(){//return [current idle time, max break]
		ArrayList<Integer> breakList = new ArrayList<Integer>();
		int wait = 0; int diff = 0; int maxBreak=0;
		Node prev = head; int idleT=0;
		Node curr = head.getNext();
		while(curr!=null){
			if((int)curr.getData()<=getDesk().openT){ wait = wait + getDesk().serviceT;} //System.out.println("this is wait for a time before opening"+wait);}//if arrival time is before opening time, 
			else{
				if((int)prev.getData()< getDesk().openT){//making proper calculations if previous node comes before opening time, set it's arrival to opening time
					wait = (int)curr.getData()-(getDesk().openT+wait+getDesk().serviceT);
					if(wait>0){
						if(wait>maxBreak){maxBreak=wait;}//finding max Break
						idleT=idleT+wait;//whenever there is a positive difference between two node arrival times, that means the service desk is idle
						wait=0;}
					else{wait=wait*-1;}
				}else{
					//System.out.println("wait = "+curr.getData()+"-"+prev.getData()+"+"+wait+"+"+desk.serviceT);
					wait = (int)curr.getData()-((int)prev.getData()+wait+getDesk().serviceT);
					if(wait>0){
						if(wait>maxBreak){maxBreak=wait;}
						idleT=idleT+wait; 
						wait=0;}
					else{wait=wait*-1;}
				}
			}
			prev=curr;
			curr = curr.getNext();
		}
		
		breakList.add(idleT);breakList.add(maxBreak); 
		return breakList;		
	}	
	
	public int totalServed(){ 
		int count=0;
		Node curr = head;
		int currEndT =0;
				
		while(curr!=null && currEndT<getDesk().closeT ){
			currEndT=waitTime(curr)+getDesk().serviceT+(int)curr.getData();
			count++;
			curr=curr.getNext();
		}
		return count;
	}
	
//	public void query (String stringName) throws IOException {
//		File fileName = new File(stringName);
//		Node curr = head;
//		ArrayList<Integer>temp = new ArrayList<Integer>();
//		if(!fileName.exists()){
//			fileName.createNewFile(); 
//		}	
//		FileWriter fw = new FileWriter(fileName);
//		BufferedWriter bw = new BufferedWriter(fw);
//		temp=idleTime();//array to hold total idle time and max idle time
//		String totalServed = Integer.toString(totalServed());	
//		bw.write("NUMBER-OF-CUSTOMERS-SERVED: "+totalServed+"\n");
//		String maxBreak = Integer.toString(temp.get(1));
//		bw.write("LONGEST-BREAK-LENGTH: "+maxBreak+"\n");
//		String totalBreak = Integer.toString(temp.get(0));
//		bw.write("TOTAL-IDLE-TIME: "+totalBreak+"\n");	
//		String peopleInQ = Integer.toString(peopleInQueue());
//		bw.write("MAXIMUM-NUMBER-OF-PEOPLE-IN-QUEUE-AT-ANY-TIME: "+peopleInQ+"\n");		
//		while(curr!=null){
//			String waitTime = Integer.toString(waitTime(curr));
//			//String customer = Integer.toString(customerNum);
//			bw.write("WAITING-TIME-OF "+Integer.toString(curr.getId())+": "+waitTime+"\n");
//			//customerNum++;
//			curr=curr.getNext();
//		}
//			
//		bw.close();
//	}
	
	public int localTimeToSecs(Node n){ //CONVERT LOCAL TIME TO SECONDS
		LocalTime t =(LocalTime) n.getData(); int total=0;	
		int sec=t.getSecond();	int min=t.getMinute();int hr=t.getHour();
		total = sec;
		total = total + (min*60);
		total = total + (hr * 60* 60);
		n.setData(total);
		return total;
	}	
}





