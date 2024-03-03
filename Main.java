import java.util.ArrayList;
import java.util.List;

// Course class to store course information
class Course {
    private String code;
    private String title;
    private String description;
    private int capacity;
    private List<String> schedule;
    private int enrolledStudents;

    public Course(String code, String title, String description, int capacity, List<String> schedule) {
        this.code = code;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.schedule = schedule;
        this.enrolledStudents = 0;
    }

    // Getters
    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getCapacity() {
        return capacity;
    }

    public List<String> getSchedule() {
        return schedule;
    }

    public int getEnrolledStudents() {
        return enrolledStudents;
    }

    // Method to enroll a student
    public boolean enrollStudent() {
        if (enrolledStudents < capacity) {
            enrolledStudents++;
            return true;
        }
        return false;
    }

    // Method to drop a student
    public void dropStudent() {
        if (enrolledStudents > 0) {
            enrolledStudents--;
        }
    }
}

// Student class to store student information
class Student {
    private String id;
    private String name;
    private List<String> registeredCourses;

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getRegisteredCourses() {
        return registeredCourses;
    }

    // Method to register for a course
    public void registerCourse(String courseCode) {
        registeredCourses.add(courseCode);
    }

    // Method to drop a course
    public void dropCourse(String courseCode) {
        registeredCourses.remove(courseCode);
    }
}

// CourseDatabase class to store and manage courses
class CourseDatabase {
    private List<Course> courses;

    public CourseDatabase() {
        this.courses = new ArrayList<>();
    }

    // Method to add a course
    public void addCourse(Course course) {
        courses.add(course);
    }

    // Getters
    public List<Course> getCourses() {
        return courses;
    }

    // Method to get a course by code
    public Course getCourseByCode(String code) {
        for (Course course : courses) {
            if (course.getCode().equals(code)) {
                return course;
            }
        }
        return null;
    }
}

// StudentDatabase class to store and manage students
class StudentDatabase {
    private List<Student> students;

    public StudentDatabase() {
        this.students = new ArrayList<>();
    }

    // Method to add a student
    public void addStudent(Student student) {
        students.add(student);
    }

    // Getters
    public List<Student> getStudents() {
        return students;
    }

    // Method to get a student by ID
    public Student getStudentById(String id) {
        for (Student student : students) {
            if (student.getId().equals(id)) {
                return student;
            }
        }
        return null;
    }
}

// CourseListing class to display available courses
class CourseListing {
    public static void displayAvailableCourses(CourseDatabase courseDatabase) {
        List<Course> courses = courseDatabase.getCourses();
        System.out.println("Available Courses:");
        for (Course course : courses) {
            int availableSlots = course.getCapacity() - course.getEnrolledStudents();
            System.out.println("Code: " + course.getCode() + ", Title: " + course.getTitle() +
                    ", Description: " + course.getDescription() + ", Available Slots: " + availableSlots);
        }
    }
}

// StudentRegistration class to allow students to register for courses
class StudentRegistration {
    public static void registerStudentForCourse(Student student, Course course) {
        if (course.enrollStudent()) {
            student.registerCourse(course.getCode());
            System.out.println("Student " + student.getName() + " registered for course " + course.getTitle());
        } else {
            System.out.println("Course " + course.getTitle() + " is full. Cannot register.");
        }
    }

    public static void dropCourseForStudent(Student student, Course course) {
        student.dropCourse(course.getCode());
        course.dropStudent();
        System.out.println("Student " + student.getName() + " dropped course " + course.getTitle());
    }
}

// Main class to demonstrate the functionalities
public class Main {
    public static void main(String[] args) {
        // Creating sample courses
        CourseDatabase courseDatabase = new CourseDatabase();
        Course c1 = new Course("CSE101", "Introduction to Computer Science", "Fundamentals of programming", 30, List.of("Mon, Wed, Fri"));
        Course c2 = new Course("MTH201", "Calculus", "Fundamentals of calculus", 25, List.of("Tue, Thu"));
        courseDatabase.addCourse(c1);
        courseDatabase.addCourse(c2);

        // Creating sample students
        StudentDatabase studentDatabase = new StudentDatabase();
        Student s1 = new Student("S001", "Alice");
        Student s2 = new Student("S002", "Bob");
        studentDatabase.addStudent(s1);
        studentDatabase.addStudent(s2);

        // Displaying available courses
        CourseListing.displayAvailableCourses(courseDatabase);

        // Registering students for courses
        StudentRegistration.registerStudentForCourse(s1, c1);
        StudentRegistration.registerStudentForCourse(s2, c2);
        StudentRegistration.registerStudentForCourse(s2, c1); // This will fail as the course is full

        // Displaying available courses after registration
        CourseListing.displayAvailableCourses(courseDatabase);

        // Dropping courses for students
        StudentRegistration.dropCourseForStudent(s1, c1);

        // Displaying available courses after dropping
        CourseListing.displayAvailableCourses(courseDatabase);
    }
}
