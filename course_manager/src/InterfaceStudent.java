import java.util.ArrayList;

public interface InterfaceStudent {
	//course management
	public void viewAllNotFull(ArrayList<Course> ar);
	public void register(ArrayList<Course> ar, String name, String id, int section, String fName, String lName);
	public void withdraw(ArrayList<Course> ar, String name, String fName, String lName);
	public void viewSelfCourses();
}
