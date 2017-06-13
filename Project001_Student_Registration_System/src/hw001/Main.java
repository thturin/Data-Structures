package hw001;
/**
 * Course Registration System By Tatiana Turin                     Anasse Bari Spring '17-Data Structures
 * 
 * This is a program that allows a user to create student and administrator accounts(in this homework assignment only one administrator was needed). 
 * A student or admin is able to view courses within the system . A student can register and withdraw from a course and an admin can edit and create courses 
 * as well as register students. Most of my code is not in the main class but in Admin. Instead of having my Student and Course Arraylist be in the main, I've put them 
 * in the Admin class. I did this because 1. we only had to create one Admin, it wouldn't have worked otherwise and 2.I serialize and de-serialize the Admin object which 
 * means I have one less .ser file to manage. 
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Scanner;
public class Main {
	public static void main(String[] args) throws IOException, ClassNotFoundException {	
		Admin a = new Admin("Admin","","Admin01","adminpass");		
		File serCourses = new File("Admin.ser");
		if(!serCourses.isFile()){//IF THE .SER FILE IS NOT THERE... ADD CSV FILE TO COURSE LIST ..
			inCSVFile(a);
		}								
		Scanner scan = new Scanner(System.in);
		boolean flag=true;
		while(flag){ ///              A CONTINIOUS ITERATION OF THE MAIN MENU					
			if(serCourses.isFile()){//IF THE ADMIN.SER IS CREATED, YOU CAN DESERIALIZE THE FILE 
				a=deserialization();		//ADMIN OBJECT A'S ADDRESS IS NOW THE DESERIALIZED OBJECT (ADMIN)		
			}						
			login(a);//LOGIN TO DETERMINE IF ADMIN OR STUDENT MAIN MENU
			System.out.println("- - - - - -");
			System.out.println("1.Exit \n2.Continue");
			System.out.println("- - - - - -");
			String input = scan.nextLine();
			if(input.equals("1")||input.equals("Exit")||input.equals("exit")){
				a.serialization();
				flag=false;
			}
			a.serialization();//SERIALIZE THE ADMIN OBJECT 
		}
	}	
	public static Admin deserialization() throws ClassNotFoundException{ //HERE I DESERIALIZE THE ADMIN A
		Admin a = null;
		try{
			FileInputStream fileIn = new FileInputStream("Admin.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			a=(Admin)in.readObject();
			in.close();//in2.close();
			fileIn.close();//fileIn2.close();		
		}catch(IOException i){
			i.printStackTrace();
			
		}
		return a;
	}		
	public static void login(Admin a){		
		System.out.println("\t\t\t\t\t\t\t\tWelcome to course registration for NYU users");
		System.out.println("Please choose from the following options: ");
		System.out.println("Are you a student or administrator(enter 's' for student or 'a' for admin)?");
		Scanner scan = new Scanner(System.in);
		String input = scan.nextLine();
			if(input.equals("a")){
				System.out.println("Hello Admin, please enter username: ");
				String username = scan.nextLine();
				System.out.println("please enter password: ");
				String password = scan.nextLine();				
				if(username.equals(a.username) && password.equals(a.password)){
					System.out.println("Thank you "+a.username);
					a.mainMenu();
				}else{
					System.out.println("Incorrect username or password");
				}
			}if(input.equals("s")){
				System.out.println("Hello Student, please enter username: ");
				String username=scan.nextLine();
				System.out.println("please enter password: ");
				String password=scan.nextLine();
				boolean isValid=false;
				for(Student s: a.studentList){	
						if(username.equals(s.username) && password.equals(s.password)){			
							System.out.println("Welcome "+s.first+" "+s.last+"\n");
							s.mainMenu(a);
							isValid=true;
						}					
				}
				if(isValid==false){System.out.println("Incorrect username or password");}
			}
			
	}				
	public static ArrayList<Course> inCSVFile(Admin a){ //this is my static method that reads the universitycourses.csv file 		
		String file = "MyUniversityCourses.csv";
		BufferedReader br = null;
		String line="";
		String splitBy = ",";
		try{
			br = new BufferedReader(new FileReader(file));
			br.readLine();//skip first line
			while((line = br.readLine()) != null){
				String[] courseInfoLine = line.split(splitBy);
				//System.out.println(courseInfoLine);				
					Course c = new Course();				
					c.name=courseInfoLine[0];
					c.ID=courseInfoLine[1];
					c.professor=courseInfoLine[5];
					c.sectNum=courseInfoLine[6];
			  		c.location=courseInfoLine[7];
			  		//c.studentList=courseInfoLine[4];
					int tempMax = Integer.parseInt(courseInfoLine[2]);
					c.maxCap=tempMax;
					int tempCurrent = Integer.parseInt(courseInfoLine[3]);
					c.currentCap=tempCurrent;
					a.courseList.add(c);				
			}
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			if(br!=null){
				try{
					br.close();				
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		}		
		return a.courseList;
	}

}



