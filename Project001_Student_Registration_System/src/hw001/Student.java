package hw001;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Scanner;

public class Student extends User implements StudentInter,Serializable{
	private static final long serialVersionUID = -6032637955322867639L;
	ArrayList<Course>studentSchedule=new ArrayList<Course>();
	public Student(){
	}
	public Student(String first, String last, String username,String password){
		super(first,last,username,password);
	}
	public void mainMenu(Admin a){
		System.out.println("Welcome to the main menu. please choose fwrom the following options(enter no.): ");
		System.out.println("1. Display all courses (incuding full) \n2. Display open courses "
				+ "\n3. Register for a course \n4.Withdraw from course \n5. Display Schedule");
		Scanner scan = new Scanner(System.in);
		int input = scan.nextInt();
		switch(input){
		case 1: displayAllCourses(a);
			break;
		case 2: viewNotFullCourses(a);
			break;
		case 3: register(a);
			break;			
		case 4: withdraw(a);
			break;
		case 5: viewSchedule();
			break;
		}
	}		
	//course management
	public void displayAllCourses(Admin a){
		System.out.println("------------------");
		ArrayList<Course> cList = a.courseList;
		System.out.println("List of open courses available for "+this.getFullName()+":");
		for(Course c:cList){					
			System.out.println("ID: "+c.ID);	
			System.out.println("Course Name: "+c.name);
			System.out.println("Section Number: "+c.sectNum);
			System.out.println("Professor Name: "+c.professor);
			System.out.println("Location: "+c.location);
			System.out.println("Current Capacity: "+c.currentCap);
			System.out.println("-------------------");
		}
	}
	public void viewNotFullCourses(Admin a){	
		System.out.println("------------------");
		System.out.println("List of open courses available for "+this.getFullName()+":");
		for(Course c:a.courseList){
			if(c.maxCap>c.currentCap){							
						System.out.println(" ID: "+c.ID+"\n Name: "+c.name+"\n Current capacity: "
						+c.currentCap+"\n Professor: "+c.professor+"\n Section No.: "+c.sectNum);							
			}
		System.out.println("-------------------");
		}
	}
	public void register(Admin a){
		for(Course c: a.courseList){
			System.out.print("ID: "+c.ID+" | "+c.name+" | "+"Sect. No.: "+c.sectNum +"\n");	
			System.out.println("-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --");
		}	
		System.out.println("\nStudent Registration: ");
		Scanner scan = new Scanner(System.in);
		System.out.println("Please enter course name: ");
		String courseName=scan.nextLine();
		System.out.println("Please enter course section: ");
		String courseSect=scan.nextLine();		
		boolean flag = true;
		for(Course c: a.courseList){//iterate through 
			flag=true;
			if(courseName.equals(c.name) && courseSect.equals(c.sectNum)){//IF COURSE IS FULL, DO NOT ALLOW THIS STUDENT TO REGISTER, BREAK FROM LOOP			
				if(c.currentCap>=c.maxCap){
					System.out.println("I'm sorry you cannot register because capacity is full");
					break;
				}
				for(Student s: c.studentList){//LOOP THROUGH STUDENT LIST OF COURSE 
					if(this.getUsername().equals(s.getUsername())){//IF THIS STUDENT'S USERNAME EQUALS A USERNAME ALREADY IN THE LIST...
						System.out.println("You are alredy registered for this course");
						flag=false; //FLAG BECOMES FALSE
						break;//BREAK FROM LOOP
					}
				}
			if(flag!=false){//IF THE FLAG HAS NOT CHANGED(REMAINS TRUE) YOU CAN ADD THIS STUDENT TO THE COURSE STUDENT LIST AND ADD THE COURSE TO THE STUDENT'S SCHEDULE LIST
				c.addStudent(this);//add new student to studentList in course class
				studentSchedule.add(c);//also add the course from courseList to this student's schedule
				System.out.println("Thank you "+this.getFullName()+" for adding "+c.name+" to your schedule");
				break;
			}					
		}			
	}
	}
	public void withdraw(Admin a){		//WITHDRAW STUDENT FROM A COURSE IN ADMIN'S COURSE LIST 
		for(Course c: a.courseList){
			System.out.print("ID: "+c.ID+" | "+c.name+" | "+"Sect. No.: "+c.sectNum +"\n");	
			System.out.println("-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --");
		}	
		
		System.out.println("Student Withdrawal: Please enter the ID or name  of the course you would like to drop: ");
		Scanner scan = new Scanner (System.in);
		String input = scan.nextLine();				
		for(int i=0;i<studentSchedule.size();i++){
			Course c = studentSchedule.get(i);
			if(input.equals(c.ID) || input.equals(c.name)){	
				try{
				c.removeStudent(this);//this should be before below//remove student from student list in course class	
				studentSchedule.remove(c);//remove course from this student object's studentSchedule list
				System.out.println(this.getFullName()+" you have withdrawn from "+c.name);
				if(studentSchedule.size()==0){
					System.out.println("You are not enrolled in any courses");
				}
				}catch(ArrayIndexOutOfBoundsException e){
					System.out.println("There was a problem registering for your course. Please try again");
					this.register(a);				
				}
			}			
		}	
	}
	public void viewSchedule(){//VIEW SCHEDULE OF THIS STUDENT
		System.out.println("-----------------");
		System.out.println("Schedule for Student: "+this.getFullName());		
		for(Course c: studentSchedule){
			System.out.println("ID: "+c.ID);	
			System.out.println("Course Name: "+c.name);
			System.out.println("Section Number: "+c.sectNum);
			System.out.println("Professor Name: "+c.professor);
			System.out.println("Location: "+c.location);
			System.out.println("-- -- -- -- -- --");
		}	
		if(studentSchedule.size()==0){
			System.out.println("You are not enrolled in any courses");
		}
	}
	@Override
	public String toString(){
		return "Student: Name: "+this.getFullName();		
	}
}
