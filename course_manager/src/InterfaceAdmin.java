import java.util.ArrayList;

public interface InterfaceAdmin {

	//course management
	public void newCourse(ArrayList<Course> ar, String name, String id, int section);
	public void delCourse(ArrayList<Course> ar, String id, int section);
	public void editCourse(ArrayList<Course> ar, int course, int choice, int max, String instOrLoc);
	public void displayInfo(ArrayList<Course> ar, String id, int section);
	public void registerStudent(ArrayList <Student> ar, String fName, String lName, String user, String pass);

	//reports
	public void viewAllFull(ArrayList<Course> ar);
	public void fullToFile(ArrayList<Course> ar);
	public void viewCourse(ArrayList<Course> ar, String id, int section);
	public void viewStudent(ArrayList <Student> ar, String fName, String lName);
	public void sortCourses(ArrayList<Course> ar);
}
