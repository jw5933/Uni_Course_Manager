import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Admin extends User implements InterfaceAdmin {

	//constructor
	Admin(){
		this.setUser("Admin");
		this.setPass("Admin001");
	}


	// ---------------------------------- COURSE MANAGEMENT -----------------------------------//

	@Override //create a new course
	public void newCourse(ArrayList<Course> ar, String name, String id, int section) {
		if (!super.courseExists(ar, name, id, section)){
			ar.add(new Course(name,id,section));
			System.out.println("Course " + name + " ID " + id + " Section " + section + " has been added.");
		} else System.out.println("That course already exists.");

	}


	@Override  //delete a course (by id and section)
	public void delCourse(ArrayList<Course> ar, String id, int section) {
		//find course by calling super method
		int index = super.findCourse(ar,id,section);
		if (index >=0){
			ar.remove(index);
			System.out.println("Course ID " + id + " Section " + section + " has been removed.");
		} else System.out.println("That course does not exist.");

	}

	@Override //edit a course section
	public void editCourse(ArrayList<Course> ar, int course, int choice, int max, String iOrL){
		Course c = ar.get(course);

		if (choice == 1){ //set max student
			c.setMaxStudent(max);
			System.out.println("Maximum number of students for Course " + c.getName() + " has been set to " + max + ".");
		}else if (choice == 2){ //set instructor
			c.setInstructor(iOrL);
			System.out.println("Instructor for Course " + c.getName() + " has been set to " + iOrL + ".");
		}else if (choice ==3){ //set location
			c.setLocation(iOrL);
			System.out.println("Location for Course " + c.getName() + " has been set to " + iOrL +".");
		}else System.out.println("Please enter valid selection.");
	}


	@Override //display all information for a course section
	public void displayInfo(ArrayList<Course> ar, String id, int section) {
		int index = super.findCourse(ar,id,section);

		//if course exists, display info
		if (index >=0){
			Course c = ar.get(index);
			System.out.println(c.toBigString());

		} else System.out.println("That course does not exist.");
	}


	@Override //register a student without assigning a course
	public void registerStudent(ArrayList<Student> ar, String fName, String lName, String user, String pass) {

		//check if student account already exists, if it does, do not register student
		if (super.studentExists(ar, user, pass)){
			System.out.println("ERROR: Student account already exists.");
		}else{
			System.out.println("Student " + fName + " " + lName + " has been registered.");
			ar.add(new Student(fName, lName, user, pass));
		}
	}





	// ---------------------------------- REPORTS -----------------------------------//

	//	view all courses override User class
	@Override
	public void viewAll(ArrayList<Course> ar) {
		System.out.printf("\n\nCurrent Courses \n_________________________\n");
		super.viewAll(ar);
	}

	@Override //show all courses that are full
	public void viewAllFull(ArrayList<Course> ar) {
		String[] fullCourses = new String [ar.size()];
		int i=0;
		for (Course c: ar){
			if(c.getCurrStudent() == c.getMaxStudent()) {
				String tempS = c.toString();
				fullCourses[i] = tempS;
				i++;
			}
		}

		for(int j =0; j < i; j++){
			System.out.println("\n"+ fullCourses[j]);
		}



	}

	@Override //write only full classes to a file
	public void fullToFile(ArrayList<Course> ar) {
		String[] fullCourses = new String [ar.size()];
		int i=1;
		for (Course c: ar){
			if(c.getCurrStudent() == c.getMaxStudent()) {
				String tempS = c.toBigString();
				fullCourses[i-1] = tempS;
				i++;
			}
		}

		try { //try to create the file
			File file = new File("./fullcourse.txt");

			if (file.createNewFile()) {
				try {
					FileWriter output = new FileWriter("./fullcourse.txt");
					for(int j =0; j < i; j++){
						if (fullCourses[j] != null) output.write(fullCourses[j]+"\n");
					}
					output.close();
					System.out.println("Report has been downloaded.");
				} catch (IOException e) {
					e.printStackTrace();
				}

			} else { //if file already exits, delete the file then recreate it
				file.delete();
				fullToFile(ar);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override //view the names of students registered in a course
	public void viewCourse(ArrayList<Course> ar, String id, int section) {
		int index = super.findCourse(ar,id,section);
		Course c = ar.get(index);
		String tempS = c.getListOfNames();
		System.out.println(tempS);
	}



	@Override //view the list of courses that a given student is registered in
	public void viewStudent(ArrayList <Student> ar, String fName, String lName) {
		for(Student s: ar){
			if (fName.equalsIgnoreCase(s.getfName())&& lName.equalsIgnoreCase(s.getlName()) ){
				s.viewSelfCourses();
				break;
			}
		}
	}


	@Override //sort the courses by current number of students
	public void sortCourses(ArrayList<Course> ar) {
		Collections.sort(ar);
		System.out.println("Courses have been sorted by current number of students.");
	}

}
