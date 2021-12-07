import java.util.ArrayList;

public class Course implements Comparable<Course>, java.io.Serializable{

	//variables
	//separate arrayList for each course that holds the students enrolled in the course
	ArrayList<Student> sList = new ArrayList<>();

	private String name;
	private String id;
	private int maxStudent;
	private int currStudent;
	private String listOfNames;
	private String instructor;
	private int section;
	private String location;


	//constructors
	Course(){}

	//when initializing program for first time
	Course(String newName, String newId, int newMax, int newCurr, String newList, String newInst, int newSect, String newLoc){
		this.name = newName;
		this.id = newId;
		this.maxStudent = newMax;
		this.currStudent = newCurr;
		this.listOfNames = newList;
		this.instructor = newInst;
		this.section = newSect;
		this.location = newLoc;
	}

	//to create new course
	Course(String newName, String newId, int newSect){
		this.name = newName;
		this.id = newId;
		this.section = newSect;
	}


	//---------------- METHODS --------------------\\
	//setters & getters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getMaxStudent() {
		return maxStudent;
	}

	public void setMaxStudent(int maxStudent) {
		this.maxStudent = maxStudent;
	}

	public int getCurrStudent() {
		return currStudent;
	}

	public void setCurrStudent(int currStudent) {
		this.currStudent = currStudent;
	}

	//no setter
	public String getListOfNames() {
		this.listOfNames = "Current students: ";
		String addS = "";
		for (Student s: sList){

			//only add commas after first student
			if (sList.indexOf(s)!=0){
				addS = ", " + s.getfName() + " " + s.getlName();
			} else addS = s.getfName() + " " + s.getlName();

			this.listOfNames = listOfNames + addS;
		}
		return listOfNames;
	}


	public String getInstructor() {
		return instructor;
	}

	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}

	public int getSection() {
		return section;
	}

	public void setSection(int section) {
		this.section = section;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}


	//method that prints all info a course has (minus list of enrolled students)
	public String toBigString(){
		String text = String.format("%n %s%s%n %s%s%n %s%d%n %s%d%n %s%s%n %s%d%n %s%s%n",
				"Name:  ",this.name, "ID:  ",this.id, "Maximum Students:  ",this.maxStudent, "Current Students:  ", this.currStudent,
				"Instructor: ", this.instructor, "Section:  ", this.section, "Location:  ", this.location);
		return text;
	}

	@Override //overriding .toString to print course info
	public String toString(){
		String text = String.format("%s%s%s%s%s%d","Name: ", this.name,", ID: ", this.id, ", Section: ", this.section);
		return text;
	}

	@Override //comparable interface method
	public int compareTo(Course c) {
		if (currStudent == c.currStudent) return 0;
		else if (currStudent < c.currStudent) return -1;
		else return 1;
	}

}
