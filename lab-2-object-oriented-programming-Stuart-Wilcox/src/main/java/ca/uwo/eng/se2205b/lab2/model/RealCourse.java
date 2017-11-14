package ca.uwo.eng.se2205b.lab2.model;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Stu on 2017-02-07.
 */
class RealCourse implements Course{
    private String name;
    private String courseCode;
    private Department department;
    private int maxOccupancy;
    private List<Student> students;

//    RealCourse(String name,
//               String courseCode,
//               @Nullable Department department,
//               int maxOccupancy)
//            throws IllegalArguementException {
//
//    }

    RealCourse(String n, String c, @Nullable Department d, int m) throws IllegalArgumentException{
        name = n;
        courseCode = c;
        setDepartment(d);
        maxOccupancy = m;
        students = new ArrayList<>();
    }

    public String getCourseCode(){
        return courseCode;
    }
    public void setName(@Nonnull String n){
        name = n;
    }
    public String getName(){
        return name;
    }
    public void setDepartment(Department d){
        checkNotNull(d);
        if(!d.getCourses().contains(this)){
            department = d;
            d.addCourse(this);
        }
    }
    public Department getDepartment(){
        return department;
    }
    public int getMaximumOccupancy(){
        return maxOccupancy;
    }
    public void setMaxOccupancy(int i){
        checkNotNull(i);
        if(i<0){
            throw new IndexOutOfBoundsException("Maximum occupancy must be a positive number");
        }
        maxOccupancy=i;}
    public void enrollStudent(@Nonnull Student s){
        if(students.size()==maxOccupancy){
            throw new CourseMaxCapacityStoreException(s, this);
        }
        if(!students.contains(s)){
            students.add(s);
        }
        s.enroll(this);
    }
    public Student removeStudent(@Nonnull Student s){
        Student w = null;
        if(students.contains(s)) {
            w = students.remove(students.indexOf(s));
        }
        if(s.getCourseList().contains(this)){
            s.drop(this);
        }
        return w;
    }
    public List<Student> getEnrolledStudents(){
        return students;
    }
    public int getSpotsLeft(){
        return maxOccupancy-students.size();
    }
    public String toString(){
        return courseCode+" : " + name;
    }
    public boolean equals(Course other){
        return this.courseCode.equals(other.getCourseCode());
    }
}
