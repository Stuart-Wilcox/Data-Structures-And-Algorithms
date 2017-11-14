package ca.uwo.eng.se2205b.lab2.model;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Stu on 2017-02-03.
 */
public class RealStudent implements Student{

    private String firstName;
    private String lastName;
    private List<Course> courses;
    private Department department;
    private long id;
    private static List<Long> idList;

//    RealStudent(String firstName,
//                String lastName,
//                long studentId,
//                @Nullable Department department)
//            throws IllegalArguementException {
//
//    }


    RealStudent(@Nonnull String fn, @Nonnull String ln, @Nonnull long ID){
        if(fn.length()==0 || ln.length()==0||ID<0){
            throw new IllegalArgumentException();
        }
        if(idList==null){
            idList = new ArrayList<>();
        }
        firstName = fn;
        lastName = ln;
        setId(ID);
        courses = new ArrayList<>();
    }
    RealStudent(@Nonnull String fn, @Nonnull String ln, @Nonnull long ID, @Nullable Department dept) throws IllegalArgumentException{
        if(fn.length()==0||ln.length()==0||ID<0||dept==null){
            throw new IllegalArgumentException();
        }
        if(idList==null){
            idList = new ArrayList<>();
        }
        firstName = fn;
        lastName = ln;
        setId(ID);
        setDepartment(dept);
        courses = new ArrayList<>();
    }
    public void enroll(@Nonnull Course c){
        checkNotNull(c, "Course is null");
        if(!courses.contains(c)){
            courses.add(c);
        }
        if(!c.getEnrolledStudents().contains(this)){
            c.enrollStudent(this);
        }
    }
    public Course drop(int index){
        checkNotNull(index);
        if(index<0||index>=courses.size()){
            throw new IndexOutOfBoundsException();
        }
        return drop(courses.get(index));
    }
    public Course drop(@Nonnull Course c){
        if(courses.indexOf(c)==-1){
            throw new RuntimeException("Student is not enrolled in the course. Unable to drop.");
        }
        Course returnVal;
        if(courses.contains(c)){
            returnVal = courses.remove(courses.indexOf(c));
            if(c.getEnrolledStudents().contains(this)){
                c.removeStudent(this);
            }
            return returnVal;
        }
        return null;
    }
    public Department setDepartment(@Nonnull Department d){
        Department r = this.department;
        this.department = d;
        d.enrollStudent(this);
        return r;
    }
    public Department getDepartment(){
        return department;
    }
    public void setId(long ID){
        checkNotNull(ID);
        if(ID<0){
            throw new IllegalArgumentException("ID value must be a positive number");
        }
        if(idList.contains(ID)){
            throw new IllegalArgumentException("ID already exists, unable to add.");
        }
        if(idList.contains(id)){
            idList.remove(id);
        }
        idList.add(ID);
        this.id = ID;
    }
    public long getId(){
        return id;
    }
    public void setFirstName(@Nonnull String fname){
        if(fname.trim().length()==0|| fname==null){
            throw new IllegalArgumentException();
        }
        firstName = fname;
    }
    public void setLastName(@Nonnull String lname){
        if(lname.trim().length()==0||lname==null){
            throw new IllegalArgumentException();
        }
        lastName = lname;
    }
    public String getFirstName(){
        return firstName;
    }
    public String getLastName(){
        return lastName;
    }
    public List<Course> getCourseList(){
        return courses;
    }
    public String toString(){
        return this.firstName + " " + this.lastName + " : " + Long.toString(id);
    }
    public boolean equals(Student other){
        return id == other.getId();
    }
}
