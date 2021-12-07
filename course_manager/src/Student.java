import java.util.ArrayList;


public class Student extends User implements InterfaceStudent, java.io.Serializable{

	//separate arrayList for each student to store their enrolled courses
	ArrayList<Course> cList = new ArrayList<>();


	//constructors
	Student(){}

	Student(String newfName, String newlName, String newUser, String newPass){
		this.setfName(newfName);
		this.setlName(newlName);
		this.setUser(newUser);
		this.setPass(newPass);
	}


	//-------------------------- METHODS ------------------------\\

	//	view all courses override User class
	@Override
	public void viewAll(ArrayList<Course> ar) {
		System.out.println("\n Courses this semester \n _________________________ \n");
		super.viewAll(ar);
	}

	@Override //view all not full classes
	public void viewAllNotFull(ArrayList<Course> ar) {
		String[] fullCourses = new String [ar.size()];
		int i=0;
		for (Course c: ar){
			if(c.getCurrStudent() < c.getMaxStudent()) {
				String tempS = String.format("%s%s%s%s%s%d","Name: ", c.getName(),", ID: ", c.getId(), ", Section: ", c.getSection());
				fullCourses[i] = tempS;
				i++;
			}
		}

		for(int j =0; j < i; j++){
			System.out.println("\n"+ fullCourses[j]);
		}
	}


	@Override //register for a course
	public void register(ArrayList<Course> ar, String name, String id, int section, String fName, String lName) {
		int index = super.findCourse(ar, id, section);
		Course c = ar.get(index);

		if (c.getCurrStudent() != c.getMaxStudent()){
			//add student to course's list
			c.sList.add(this);
			c.setCurrStudent(c.getCurrStudent()+1);

			//add course to student's list
			this.cList.add(c);

			System.out.println("You have been registered.");
			//don't let student register if course is full
		}else System.out.println("Sorry, this course is full.");
	}

	@Override //withdraw from a course
	public void withdraw(ArrayList<Course> ar, String name, String fName, String lName) {
		int index = super.findCourse(ar, name, fName, lName);
		if (index >= 0){
		Course c = ar.get(index);
		//remove student from course's list
		c.sList.remove(this);
		c.setCurrStudent(c.getCurrStudent()-1);
		//remove course from student's list
		cList.remove(c);
		System.out.println("You have been withdrawn from "+ name + ".");
	} else System.out.println("Course " + name + " does not exist, or you are not registed in the course.");
	}

	@Override //view courses this student is registered in
	public void viewSelfCourses() {
		System.out.println("Currently enrolled in "
				+ "\n -------------------------------------- \n");
		for (Course c: cList){
			System.out.println(c.toString());
		}
	}

}
