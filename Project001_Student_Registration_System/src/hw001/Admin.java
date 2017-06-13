package hw001;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;


public class Admin extends User implements AdminInter,Serializable{
	int i=0;//THIS I WILL BE PASSED INTO THE COURSE ID CREATION
	ArrayList <Course> courseList = new ArrayList<Course>();	
	ArrayList<Student> studentList = new ArrayList<Student>();
	public Admin(){super();}
	public Admin(String first, String last, String username,String password){
		super(first,last,username,password);	
	}			
	//serialization of admin object
	public void serialization(){//SERIALIZING THE ADMIN OBJECT WHICH WILL SERIALIZE BOTH COURSE LIST AND STUDENT LIST
		try{
			FileOutputStream fileOut= new FileOutputStream("Admin.ser", false);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(this);
			out.close();
			fileOut.close();	
		}catch(IOException i){
			i.printStackTrace();
		}
	}
	public void mainMenu(){		
		Scanner scan = new Scanner (System.in);
		System.out.println("Hello Admin 001: \nEnter No. from the menu options: \n 1.Course Management \n 2.Reports");
		int input = scan.nextInt();		
		if(input==1){
			System.out.println("COURSE MANAGEMENT \n Enter No. from the menu options: \n 1.Create new course \n 2. Delete a course \n 3. Edit a course"
					+ "\n 4. Display information of a course \n 5. Register a student");
			int input2=scan.nextInt();
			switch(input2){
			case 1: this.createCourse();
				break;
			case 2: this.deleteCourse();
				break;
			case 3: this.editCourse();
				break;
			case 4: this.displayCourse();
				break;
			case 5: this.registerStudent();
				break;
			}			
		}else if(input==2){
			System.out.println("REPORTS \n Enter No. from the menu options: \n 1. View all courses \n 2. View all courses that are full(and then write to file)"
					+ "\n 3. View registered students  \n 4. View student's course list");
			int input2 = scan.nextInt();
			switch(input2){
			case 1: this.displayAllCourses();
				break;
			case 2: try {
					this.displayFullCourses();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 3: this.displayStudentList();
				break;
			case 4: this.displayStudentsSchedule();;
				break;
			}
		}
	}		
	//course management
	public Course createCourse(){
		System.out.println("------------------");
		Scanner inIntegers = new Scanner(System.in);
		Scanner inStrings = new Scanner(System.in);
		System.out.println("Please add Course: ");
		System.out.println("Course Name: ");
		String name=inStrings.nextLine();		
		System.out.println("Professor Name: ");
		String professor=inStrings.nextLine();
		System.out.println("Location: ");
		String location=inStrings.nextLine();
		System.out.println("Max Capacity: ");
		String maxCapp=inStrings.nextLine();
		int maxCap=Integer.parseInt(maxCapp);
		System.out.println("Section Number: ");		
		String sectNum=inStrings.nextLine();			
		Course c = new Course(maxCap,sectNum,professor,location,name,i);
		courseList.add(c);	
		System.out.println("Thank you. Course "+c.name+" has been added to the system");
		i+=1;//INCREMEMNT I EVERYTIME A COURSE IS MADE
		return c;
	}	
	public void deleteCourse(){
		System.out.println("------------------");
		Scanner inIntegers = new Scanner(System.in);
		System.out.println("Delete a Course?");
		for(int i=0;i<courseList.size();i++){
			System.out.println(courseList.get(i).ID+" : "+courseList.get(i).name);
			System.out.println("--- ---------- ---");
		}
		System.out.println("Please enter course ID: ");
		String ID=inIntegers.nextLine();
		for(int k=0;k<courseList.size();k++){
			if(ID.equals(courseList.get(k).getID())){
				System.out.println("Course "+courseList.get(k).name+" has been removed from the system");
				courseList.remove(k);				
				break;
			}
		}		
	}	
	public void editCourse(){
		System.out.println("------------------");
		Scanner scan = new Scanner(System.in);
		System.out.println("Please enter course ID: ");
		String ID=scan.nextLine(); String printName = null;		
		for(Course c:courseList){
			if(ID.equals(c.getID())){		
				printName=c.getName();
				System.out.println("Course Information: \n 1.Course Name: "+c.name+"\n 2.Course ID: "+c.ID
						+"\n 3.Course Section No.: "+c.sectNum
						+ "\n 4.Max Capacity: "+c.maxCap+"\n 5.Current Capacity: "+c.currentCap+"\n 6.Professor Name: "
						+c.professor+"\n 7.Location: "+c.location);
				System.out.println("To change course. Please enter number");					
				String editNo=scan.nextLine();
				int editNoInt = Integer.parseInt(editNo);
				//Scanner editIn = new Scanner (System.in);
				switch(editNoInt){
				case 1: System.out.println("Change course name: ");
						String courseNameIn = scan.nextLine();
						c.name=courseNameIn;
					break;
				case 2: System.out.println("Change course ID: ");
						String idIn = scan.nextLine();
						c.ID=idIn;
					break;
				case 3: System.out.println("Change course section no.: ");
						String secIn =scan.nextLine();
						c.sectNum=secIn;
					break;
				case 4: System.out.println("Change max capacity: ");
						String temp = scan.nextLine();
						int maxIn = Integer.parseInt(temp);
						c.maxCap=maxIn;
					break;
				case 5: System.out.println("Change current capacity: ");
						String temp2 = scan.nextLine();
						int currentIn = Integer.parseInt(temp2);
						c.currentCap=currentIn;
					break;
				case 6: System.out.println("Change professor: ");
						String professorIn = scan.nextLine();
						c.professor=professorIn;
					break;
				case 7: System.out.println("Change location: ");
						String locationIn = scan.nextLine();
						c.location=locationIn;
					break;								
				}	
		}					
		}	
		System.out.println("Thank you for updating course "+printName);
	}		
	public void displayCourse(){//DISPLAY A COURSE
		System.out.println("------------------");
		Scanner scan = new Scanner(System.in);
		
		for(Course c: courseList){		//DISPLAY A LIST OF COURSES WITH ID
			System.out.print("ID: "+c.ID+ " | ");
			System.out.println(c.name);
			System.out.println("--- ----------- ---");
		}
		
		
		System.out.println("Please enter course ID: ");
		String input = scan.nextLine();
		boolean isValid = false;
		for(int i=0;i<courseList.size();i++){ //ITERATE THROUGH AND DISPLAY COURSE IF FOUND IN LIST 
			Course c = courseList.get(i);		
			if(input.equals(c.ID)){
			System.out.println("-------------------");
			System.out.println("ID: "+c.ID);	
			System.out.println("Course Name: "+c.name);
			System.out.println("Section Number: "+c.sectNum);
			System.out.println("Professor Name: "+c.professor);
			System.out.println("Location: "+c.location);
			System.out.println("Max Capacity: "+c.maxCap);
			System.out.println("Current Capacity: "+c.currentCap);	
			System.out.println("-------------------");
			isValid=true;
			break;
			}
		}
		if(isValid==false){System.out.println("I'm sorry, course cannot be found. Please try again");}
	}
	public void registerStudent(){
		System.out.println("------------------");
		Scanner scan = new Scanner(System.in);
		System.out.println("--------------------");
		System.out.println("Enter First Name: ");
		String first=scan.nextLine();
		System.out.println("Enter Last Name: ");
		String last=scan.nextLine();
		System.out.println("Enter Username: ");
		String username=scan.nextLine();
		System.out.println("Enter Password: ");	
		String password=scan.nextLine();
		System.out.println("--------------------");
		Student s = new Student(first,last,username,password);
		studentList.add(s);
		System.out.println("Thank you. Student "+s.getFullName()+" has been registered");
	}		
	
	public void addCourse(Course c){		//I added this method to be able to create a course and then add to admin list in order to check the program
		courseList.add(c);
	}	
	//reports
	public void displayAllCourses(){
		System.out.println("------------------");
		for(int i=0;i<courseList.size();i++){
			Course c = courseList.get(i);
			System.out.println("-------------------");
			System.out.println("ID: "+c.ID);	
			System.out.println("Course Name: "+c.name);
			System.out.println("Section Number: "+c.sectNum);
			System.out.println("Professor Name: "+c.professor);
			System.out.println("Location: "+c.location);
			System.out.println("Max Capacity: "+c.maxCap);
			System.out.println("Current Capacity: "+c.currentCap);	
			System.out.println("    ");
		}		
	}
	public void displayFullCourses() throws IOException{ //THIS METHOD WRITES A TXT FILE TO FOLDER WITH FULL CLASSES AND THEN DISPLAYS FULL COURSES TO CONSOLE
		//deserialization(this.courseList);
		System.out.println("------------------");		
		File file = new File("filledClass.txt");
		FileWriter fw= new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);	
		System.out.println("List of courses that are full: ");
		for(int i=0;i<courseList.size();i++){
			Course c = courseList.get(i);
			if(c.currentCap>=c.maxCap){
				c.currentCap=c.maxCap;
				System.out.println("-------------------");
				System.out.println("ID: "+c.ID);	
				System.out.println("Course Name: "+c.name);
				System.out.println("Section Number: "+c.sectNum);
				System.out.println("Professor Name: "+c.professor);
				System.out.println("Location: "+c.location);
				System.out.println("Max Capacity: "+c.maxCap);
				System.out.println("Current Capacity: "+c.currentCap);	
				System.out.println("-------------------");				
				try{
					if(file.exists()){
					String maxCapString=Integer.toString(c.maxCap);
					bw.write("Course ID: "+c.ID+"Course Name: "+c.name+"Max/Current Capacity: "+maxCapString);
					bw.newLine();					
					}
				}catch (IOException e){
					System.err.println("Could not write text to file");
				}
			}					
			
		}
		bw.close();	
	System.out.println("Classes that are full have been added to filledClasses.txt file");
	}
	public void displayStudentList(){
		System.out.println("------------------");
		System.out.println("Student registry: ");
		int count=0;
		for(Student s:studentList){
			count++;
			System.out.println(count+". "+s.getFullName()+" | "+s.getUsername());
		}
		System.out.println("------------------");
	}	
	public void displayStudentsSchedule(){
		System.out.println("------------------");
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter username ");
		String username = scan.nextLine();
		System.out.println("Schedule for student: "+username);
		System.out.println("	Course Names: ");

		for(Student s: studentList){
			if(username.equals(s.username)){
				for(Course c: s.studentSchedule){
					System.out.println("        "+c.name);
				}
			}
		}					
					
	}		
	@Override
	public String toString(){
		return "Admin: Name: "+this.getFullName();
	}
}
	
				
			
		
		
		
		

	
	


