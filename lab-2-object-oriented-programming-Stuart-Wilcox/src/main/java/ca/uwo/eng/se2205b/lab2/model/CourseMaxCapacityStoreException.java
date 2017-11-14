package ca.uwo.eng.se2205b.lab2.model;

/**
 * Created by Stu on 2017-02-07.
 */
public class CourseMaxCapacityStoreException extends RuntimeException {
    private Student student;
    private Course course;
    Student getStudent(){
        return student;
    }
    Course getCourse(){
        return course;
    }

    CourseMaxCapacityStoreException(Student s, Course c){
        super(c.getCourseCode() + ": "+c.getName() + " can not store " + s.getFirstName() + " "+s.getLastName() + ", maximum capacity reached.");
    }
}
