package hw001;

import java.io.IOException;

public interface AdminInter {
	public void deleteCourse();
	public void editCourse();
	public void displayCourse();
	public void registerStudent();
	public void addCourse(Course c);
	public void displayAllCourses();
	public void displayFullCourses() throws IOException;
	public void displayStudentList();
	public void displayStudentsSchedule();
}
