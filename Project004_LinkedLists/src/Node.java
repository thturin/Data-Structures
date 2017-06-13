public class Node <T> {
	private T data;
	private int id;
	private Node next;
	
	Node(){}
	Node(T data){this.data=data;this.next=null;}
	Node(T data, Node next){this.data=data;this.next=next;}
	
	T getData(){return data;}
	public void setData(T data){this.data=data;}
	Node getNext(){return next;}
	public void setNext(Node nextNode){this.next=nextNode;}		
	public void displayNode(){System.out.println("This node contains: "+this.getData());}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
}
