import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Operator {

	//constructor
	Operator(){}

	//methods
	// ----------------------------------- serializing and opening csv -----------------------------------\\

	// open course file (csv) method
	public void startCourseFile(ArrayList<Course>cList){
		try {
			//buffered reader and path to file
			BufferedReader br = new BufferedReader(new FileReader("./MyUniversityCourses.csv")); 

			//skip first line
			br.readLine();

			String string;
			//while there is text in the line, read it, split it, and divide it into course variables
			while ((string = br.readLine()) != null) {

				String[] values = string.split(",");

				String name = values[0];
				String id=values[1];
				int max = Integer.parseInt(values[2]);
				int curr = Integer.parseInt(values[3]);
				String listName = values[4];
				String inst = values[5];
				int sect = Integer.parseInt(values[6]);
				String loc = values[7];


				Course newCourse = new Course(name, id, max, curr, listName, inst, sect, loc);
				cList.add(newCourse);

			}
			br.close();

		}catch(FileNotFoundException e){
			System.out.println("File not found");

		}catch(IOException e){
			e.printStackTrace();
		}

	}


	//deserialization
	public void deserialize(ArrayList<Course>cList, ArrayList<Student> sList){
		//try to deserialize
		try{
			//FIS receives bytes from a file
			FileInputStream fis = new FileInputStream("src/courses.ser");

			//OIS reconstructs object
			ObjectInputStream ois = new ObjectInputStream(fis);

			//cast as an arrayList of courses
			cList = ((ArrayList<Course>)ois.readObject());

			//close streams
			ois.close();
			fis.close();
		}
		catch (FileNotFoundException fnfe){
			startCourseFile(cList);
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
			return;
		}
		catch(ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
			return;


		} finally{
			//only deserialize student file if coursefile has been deserialized
			//prevents csv from being added more than once
			try{
				FileInputStream fis = new FileInputStream("src/students.ser");
				ObjectInputStream ois = new ObjectInputStream(fis);

				//Cast as an arrayList of students
				sList = ((ArrayList<Student>)ois.readObject());
				ois.close();
				fis.close();
			}
			catch (FileNotFoundException fnfe){
			}
			catch(IOException ioe) {
				ioe.printStackTrace();
				return;
			}
			catch(ClassNotFoundException cnfe) {
				cnfe.printStackTrace();
				return;
			}
			finally{ //finally login after courses/students have been deserialized or not
				login(cList, sList);
			}
		}
	}


	//serialization
	public void serialize(ArrayList<Course>cList, ArrayList<Student> sList){
		try {
			//write data to file
			FileOutputStream fos = new FileOutputStream("./courses.ser");

			//writes objects to a stream
			ObjectOutputStream oos = new ObjectOutputStream(fos);

			//Writes course list to oos
			oos.writeObject(cList);

			//write data to new file
			fos = new FileOutputStream("./students.ser");
			oos = new ObjectOutputStream(fos);

			//student list to oos
			oos.writeObject(sList);

			//close streams
			oos.close();
			fos.close();

			System.out.println("Changes have been saved.");
		} 
		catch (FileNotFoundException fnfe){
			System.out.println("File not found");
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
		finally{System.exit(0);}
	}


	//-------------------------------------- login & user selection ------------------------------------\\

	//variable for login
	User currUser = null;

	//login method: ask for login info and enter admin/student system
	public void login(ArrayList<Course>cList, ArrayList<Student>sList){

		//check if serialized worked
		//System.out.println(cList.size());

		//variables
		//scanner
		Scanner input = new Scanner(System.in);
		//strings to check user and pass for login
		String tempU = "";
		String tempP = "";


		//loop to ask user to login
		while (currUser == null){
			System.out.printf("\nLogin: \n1. Admin \n2. Student \n3. Exit program");
			int choice = input.nextInt();
			input.nextLine();

			if (choice == 1){ //admin login
				System.out.println();
				System.out.println("Please enter username:");
				tempU = input.nextLine();

				System.out.println("Please enter password:");
				tempP = input.nextLine();
				//run method to check if user and pass is correct
				isAdmin(tempU,tempP);
			}
			else if (choice == 2){ //student login
				if (!sList.isEmpty()){
					System.out.println();
					System.out.println("Please enter username:");
					tempU = input.nextLine();

					System.out.println("Please enter password:");
					tempP = input.nextLine();

					//check user/pass for student
					isStudent(sList, tempU,tempP);

					//if no students exist, ask user to login as admin
				}else System.out.println("No students have been registered yet. Please login as Admin.");
			}
			else if (choice == 3){
				//before exiting program, serialize arraylists then in finally block, exit program
				serialize(cList, sList);
			}
			else System.out.println("Please enter a valid number.");
		}


		//run admin or studnt based on what instance current user is
		if (currUser!=null){
			if (currUser instanceof Admin){
				//System.out.println(currUser.toString());
				runAdmin(cList,sList);
			}else{
				//System.out.println(currUser.toString());
				runStudent(cList,sList);
			} 
		}
		input.close();
	}


	//check if user is admin
	public void isAdmin(String user, String pass){
		if (user.equals("Admin") && pass.equals("Admin001")){
			currUser = new Admin();
		}else{
			//if user/pass is incorrect, do not login
			System.out.printf("\nWrong username or password.");

		}
	}


	//check if user is student
	public void isStudent(ArrayList<Student> list, String user, String pass){
		boolean result = false;
		//run through array and check user name and password until user if found
		for(int k = 0; k< list.size(); k++){
			String tempU = list.get(k).getUser();
			String tempP = list.get(k).getPass();

			if(user.equals(tempU) && pass.equals(tempP)){
				currUser = list.get(k);
				result = true;
				break;
			}
		}
		if (!result) System.out.printf("\nWrong Username or Password.");
	}



	// ------------------------------------ Admin ------------------------------------- \\

	//admin method to call to admin class based on user's selections
	public void runAdmin(ArrayList<Course>cList, ArrayList<Student>sList){
		//scanner for method
		Scanner input = new Scanner(System.in);

		if (currUser instanceof Admin){
			//variables	
			//booleans for loop
			boolean admin = true;
			boolean courseManage = false;
			boolean reports = false;

			//select course management or reports (or logout)
			while (admin &&(!courseManage&&!reports)){
				//scanner
				System.out.printf("\nWhat would you like to do? \n1. Course Management"
						+ "\n2. Reports \n3. Log out\n");
				int choice = input.nextInt();
				input.nextLine();

				if (choice == 1){
					courseManage = true;
				}
				else if (choice == 2){ //student login
					reports = true;

				}
				else if(choice ==3){
					currUser = null;
					login(cList, sList);
					break;
				}
				else System.out.printf("\nPlease choose a valid selection.");


			}

			// -------- course management section ---------- \\
			while (courseManage){ //options for course manage
				System.out.printf("\nWhat would you like to do? \n1. Create a new course "
						+ "\n2. Delete a course \n3. Edit a course \n4. Display information for a course"
						+ "\n5. Register a student \n6. Return\n");

				//take user input and call selected method
				int choice = input.nextInt();
				input.nextLine();

				//variables for cases
				String name = "";
				String id = "";
				int section = 1;

				switch(choice){
				case 1: //create new course
					System.out.println("Please enter the name of the course: ");
					name = input.nextLine();

					System.out.println("Enter Course ID: ");
					id = input.nextLine();

					System.out.println("Enter Course Section: ");
					section = input.nextInt();
					input.nextLine();

					((Admin) currUser).newCourse(cList, name, id, section);
					break;


				case 2: //delete course
					System.out.println("Enter Course ID: ");
					id = input.nextLine();

					System.out.println("Enter Course Section: ");
					section = input.nextInt();
					input.nextLine();
					((Admin) currUser).delCourse(cList, id, section);
					break;


				case 3: //edit course
					System.out.println("Enter Course ID: ");
					id = input.nextLine();

					System.out.println("Enter Course Section: ");
					section = input.nextInt();
					input.nextLine();

					int course = currUser.findCourse(cList, id, section);

					//only edit course if that course exists
					if (course >= 0){
						int tempI = 0;
						String tempS = "";
						System.out.println("What would you like to edit?"
								+ "\n 1. Max number of students  \n 2. Course Instructor  \n 3. Course Location");
						choice = input.nextInt();
						input.nextLine();

						System.out.println("What would you like to change it to?");
						if (choice == 1){
							tempI = input.nextInt();
							input.nextLine();

						}else if (choice == 2){
							tempS = input.nextLine();

						} else if (choice == 3){
							tempS = input.nextLine();
						}
						//call Admin class method to edit course
						((Admin) currUser).editCourse(cList, course, choice, tempI, tempS);

					}else System.out.println("Course not Found");
					break;


				case 4: //display one course info
					System.out.println("Enter Course ID: ");
					id = input.nextLine();

					System.out.println("Enter Course Section: ");
					section = input.nextInt();
					input.nextLine();

					((Admin) currUser).displayInfo(cList, id, section);
					break;


				case 5: //register student
					System.out.println("Enter student full name: ");
					String fName = input.next();
					String lName = input.next();
					input.nextLine();
					System.out.println("Enter username for student account: ");
					String user = input.nextLine();
					System.out.println("Enter password for student account: ");
					String pass = input.nextLine();
					((Admin) currUser).registerStudent(sList, fName, lName, user, pass);

					break;


				case 6: //return to admin selection
					runAdmin(cList,sList);
					break;

				default: //int user gave is not 1-6
					System.out.println("Please choose a valid selection.");
					break;
				}


			}



			// ------------ reports section ------------- \\
			while (reports){ //call admin methods for reports
				System.out.printf("\nWould you like to do? \n1. View all courses "
						+ "\n2. View all full courses \n3. Download list of full courses"
						+ "\n4. List of students for a course \n5. List of courses for a student"
						+ "\n6. Sort courses by current number of students \n7. Return\n");

				int choice = input.nextInt();
				input.nextLine();

				switch(choice){
				case 1: //view all courses
					((Admin)currUser).viewAll(cList);
					break;

				case 2: //view all full courses
					((Admin) currUser).viewAllFull(cList);
					break;

				case 3: //write to file only full courses
					((Admin) currUser).fullToFile(cList);
					break;

				case 4: //list of students in a given course
					System.out.println("Enter Course ID: ");
					String id = input.nextLine();
					System.out.println("Enter Course Section: ");
					int section = input.nextInt();
					input.nextLine();
					((Admin) currUser).viewCourse(cList, id, section);
					break;



				case 5: //list of courses for a given student
					System.out.println("Enter student full name: ");
					String fName = input.next();
					String lName = input.next();
					((Admin) currUser).viewStudent(sList, fName, lName);
					break;



				case 6: //sort courses by # of current students
					((Admin) currUser).sortCourses(cList);
					break;


				case 7: //return to admin selection
					runAdmin(cList,sList);
					break;

				default: //input for choice was not 1-7
					System.out.println("Please choose a valid seletion.");
					break;	
				}
			}
		}
		input.close();
	}






	// -------------------------------------Student -------------------------------------- \\

	public void runStudent(ArrayList<Course>cList, ArrayList<Student>sList){
		//variables
		//scanner for method
		Scanner input = new Scanner(System.in);
		int choice = 0;

		//while the current user is a Student, run loop asking for input
		while (currUser instanceof Student){

			System.out.printf("\n What would you like to do? \n 1. View all courses "
					+ "\n 2. View all courses that are not full \n 3. Register in a course "
					+ "\n 4. Withdraw from a course \n 5. View your courses \n 6. Logout\n");

			choice = input.nextInt();
			input.nextLine();

			//variables for cases
			String name = "";
			String id = "";
			int section = 0;

			switch(choice){
			case 1: //view all courses
				currUser.viewAll(cList);
				break;


			case 2: //view courses that are not full
				((Student) currUser).viewAllNotFull(cList);
				break;


			case 3: //register for a course
				System.out.println("Enter name of course: ");
				name = input.nextLine();


				System.out.println("Enter course ID: ");
				id = input.nextLine();

				System.out.println("Enter course section: ");
				section = input.nextInt();
				input.nextLine();


				System.out.println("Enter your full name: ");
				String fName = input.next();
				String lName = input.next();

				//only register if user enters their own name (as in the system)
				if (currUser.getfName().equalsIgnoreCase(fName) && currUser.getlName().equalsIgnoreCase(lName)){
					((Student) currUser).register(cList, name, id, section, fName, lName);	
				} else System.out.println("Please enter your own name.");
				break;


			case 4: //withdraw from a course
				System.out.println("Enter name of course: ");
				name = input.nextLine();
				System.out.println("Enter your full name: ");
				fName = input.next();
				lName = input.next();

				//only withdraw if user inputs their own name
				if (currUser.getfName().equalsIgnoreCase(fName) && currUser.getlName().equalsIgnoreCase(lName))
					((Student) currUser).withdraw(cList, name, fName, lName);
				else System.out.println("Please enter your own name.");
				break;


			case 5: //view own courses
				((Student) currUser).viewSelfCourses();
				break;


			case 6: //return to login screen
				currUser = null;
				login(cList, sList);
				break;

			default: //choice input is not 1-6
				System.out.println("Please choose a valid seletion.");
				break;
			}

		}
		input.close();
	}
}
