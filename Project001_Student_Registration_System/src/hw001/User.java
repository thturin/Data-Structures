package hw001;

import java.io.Serializable;

public abstract class User implements Serializable{
	private static final long serialVersionUID = -750186843355552734L;
	String first;
	String last;
	String username;
	String password;
	
	public User(){}
	public User(String first,String last, String username, String password){
		this.first=first;
		this.last=last;
		this.username=username;
		this.password=password;
	}
	
	public String getFirst(){return first;}
	public String getFullName(){return first+" "+last;}
	public String getLast(){return last;}
	public String getUsername(){return username;}
	public String getPassword(){return password;}
	
	public void setFirst(String x){first=x;}
	public void setLast(String x){last=x;}
	public void setUsername(String x){username=x;}
	public void setPassword(String x){password=x;}
	
	public  String toString(){
		return "User: Name:"+this.getFullName();
	}
		

		
		
}
	
	
	
	

