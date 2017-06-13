package hw001;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Course implements Serializable{
	private static final long serialVersionUID = -3280915166369478415L;
	//public static int i=0;
	String ID;
	int maxCap;
	int currentCap;
	ArrayList<Student>studentList;
	String professor;
	String name;
	String sectNum;
	String location;
	
	public Course(){
		this.studentList=new ArrayList<Student>(currentCap);
	}
	public Course(int maxCap,String sectNum,String professor,String location, String name, int i){
		i+=1;
		String temp = Integer.toString(i);		
		this.ID="100"+temp;
		this.maxCap=maxCap;
		this.professor=professor;
		this.sectNum=sectNum;
		this.location=location;
		this.currentCap=0;
				
		this.name=name;
		this.studentList=new ArrayList<Student>(currentCap);
	}
	
	public String getID(){return ID;}
	public int getMaxCap(){return maxCap;}
	public int getCurrentCap(){return currentCap;}
	public ArrayList<Student> getStudentList(){return studentList;}
	public String getProfessor(){return professor;}
	public String getSectNum(){return sectNum;}
	public String getLocation(){return location;}
	public String getName(){return name;}	
	public void setID(String x){this.ID=x;}
	public void setMaxCap(int x){this.maxCap=x;}
	public void setCurrentCap(int x){this.currentCap=x;}
	public void setProfessor(String x){this.professor=x;}
	public void setSectNum(String x){this.sectNum=x;}
	public void setLocation(String x){this.location=x;}
	public void setName(String x){this.name=x;}
	public void addStudent(Student x){	
		studentList.add(x);
		currentCap+=1;			
	}
	public void removeStudent(Student x){
		studentList.remove(x);
		currentCap-=1;
	}

}
