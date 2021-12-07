import java.util.ArrayList;

public class SystemDriver {
	public static void main(String[] args){
		//arraylist for courses
		ArrayList<Course> courseList = new ArrayList<>();

		//arraylist for students
		ArrayList<Student> studentList = new ArrayList<>();

		Operator op = new Operator();
		op.deserialize(courseList, studentList);

	}
}
