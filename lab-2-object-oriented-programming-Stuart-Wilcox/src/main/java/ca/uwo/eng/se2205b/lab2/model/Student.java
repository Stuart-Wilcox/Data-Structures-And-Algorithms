package ca.uwo.eng.se2205b.lab2.model;
import java.util.*;
/**
 * Created by Stu on 2017-02-03.
 */
public interface Student {

    void enroll(Course c);
    Course drop(Course c);
    Course drop(int index);
    Department setDepartment(Department d);
    Department getDepartment();
    void setId(long ID);
    long getId();
    void setFirstName(String fname);
    String getFirstName();
    void setLastName(String lname);
    String getLastName();
    List<Course> getCourseList();
}
