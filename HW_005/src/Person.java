import java.util.ArrayList;

public class Person implements Comparable <Person>{
	private int ssn;
	private String first;
	private String last;
	private ArrayList<Integer> friends;
	private Person dad;
	private Person mom;
	private TreeGeneric<Person> friendTree;
	
	public Person(){}
	
	public Person(int ssn){
		this.ssn=ssn;
	}
	public Person (int ssn, String noFirst, String noLast){
		this.ssn=ssn;
		this.first = noFirst;
		this.last = noLast;
	}
	
	public Person(int ssn,String first, String last, ArrayList<Integer> friends){
		this.ssn=ssn;
		this.first=first;
		this.last=last;
		this.friends=friends;
	}
	public Person(int ssn, String first, String last, ArrayList<Integer>friends, Person dad, Person mom){
		this.ssn=ssn;
		this.first=first;
		this.last=last;
		this.friends=friends;
		this.dad=dad;
		this.mom=mom;
	}
		
	public void displayPerson(){
		System.out.println(this.first+" "+this.last+"\nSSN: "+this.ssn+"\nMother: "+
	this.getmom().getFirst()+" "+this.getmom().getLast()+" ssn-> "+this.getmom().getSsn()+"\nDad: "+
	this.getdad().getFirst()+" "+this.getdad().getLast()+" ssn-> "+this.getdad().getSsn()+"\n");			
	}
	
	public void setFriendTree(TreeGeneric<Person> friendTree){
		this.friendTree=friendTree;
	}
	
	public TreeGeneric<Person> getFriendTree(){
		return friendTree;
	}

	public int getSsn() {
		return ssn;
	}
	public void setSsn(int ssn) {
		this.ssn = ssn;
	}
	public String getFirst() {
		return first;
	}
	public void setFirst(String first) {
		this.first = first;
	}
	public String getLast() {
		return last;
	}
	public void setLast(String last) {
		this.last = last;
	}
	public ArrayList<Integer> getFriends() {
		return friends;
	}
	public void setFriends(ArrayList<Integer> friends) {
		this.friends = friends;
	}
	public Person getdad() {
		return dad;
	}
	public void setdad(Person dad) {
		this.dad = dad;
	}
	public Person getmom() {
		return mom;
	}
	public void setmom(Person mom) {
		this.mom = mom;
	}
	

	@Override
	public int compareTo(Person o) {
		if(ssn>o.getSsn()){
			return 1;
		}else if(ssn<o.getSsn()){
			return -1;
		}		
		return 0;
	}	
}
