import java.io.Serializable;
import java.util.ArrayList;

public abstract class User implements Serializable{

	//variables
	private String user;
	private String pass;
	private String fName;
	private String lName;


	//setters and getters
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}



	//----------- METHODS FOR BOTH ADMIN AND STUDENT ------------\\
	//view all courses
	public void viewAll(ArrayList<Course> ar) {
		for(Course c: ar){
			System.out.println(c.toString());
		}
	}


	//find course in arrayList and return its index
	public int findCourse(ArrayList<Course> ar, String id, int section){
		int element=-1;
		for(Course c: ar){
			if (id.equals(c.getId())&&(section == c.getSection())){
				element = ar.indexOf(c);
				break;
			}
		}
		return (element);
	}

	//overrloaded find course method for when id and section are not given
	public int findCourse(ArrayList<Course> ar, String cName, String fName, String lName){
		int element=-1;
		boolean found = false;
		for(Course c: ar){
			if (cName.equals(c.getName())){ //if input name is same as course name check for enrolled student
				found = findStudent(c, fName, lName);
				if (found){ //if student is enrolled, return the course index
					element = ar.indexOf(c);
					break;
				}
			}
		}
		return (element);
	}

	//private method called only by above findCourse method
	//tries to find given student in the course's enrolled students list
	private boolean findStudent(Course c, String fName, String lName){
		//boolean result = false;
		for (Student s: c.sList){
			if (s.getfName().equalsIgnoreCase(fName)&&s.getlName().equalsIgnoreCase(lName)){
				return true;
			}
		}
		return false;
	}

	
	//check if the course already exists
	public boolean courseExists(ArrayList<Course> ar, String name, String id, int section){
		for(Course c: ar){
			if(name.equalsIgnoreCase(c.getName())&& id.equalsIgnoreCase(c.getId())&&section == c.getSection()){
				return true;
			}
		}
		return false;
	}
	
	//check if student account already exists
	public boolean studentExists(ArrayList<Student> ar, String user, String pass){
		
		for(Student s: ar){
			if(user.equals(s.getUser()) && pass.equals(s.getPass())){
				return true;
			}
		}
		return false;
	}
}
