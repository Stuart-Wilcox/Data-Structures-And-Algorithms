package ca.uwo.eng.se2205b.lab2.model;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * Created by Stu on 2017-02-07.
 */
 class RealDepartment implements Department {
    private String name;
    private List<Student> students;
    private List<Course> courses;
    private static List<String> departments = new ArrayList<>();
    private static Department Undeclared;
//    RealDepartment(String name) throws IllegalArgumentException {
//
//    }
    static Department Undeclared(){
        if(Undeclared==null){
            Undeclared = new RealDepartment("Undeclared");
        }
        return Undeclared;
    }
    static void clearDepartmentList(){
        departments = new ArrayList<>();
    }
    RealDepartment(String n) throws IllegalArgumentException{
        if(departments.contains(n)){
            throw new RuntimeException("Department name already exists.");
        }
        name = n;
        students = new ArrayList<>();
        courses = new ArrayList<>();
        departments.add(n);
    }
    public String getName(){
        return name;
    }
    public void setName(@Nonnull String n){
        if(departments.contains(n)){
            throw new RuntimeException("Department name already exists.");
        }
        name = n;

    }
    public void enrollStudent(@Nonnull Student s){
        if(!students.contains(s)) {
            students.add(s);
        }
        if(s.getDepartment() != this) {
            s.setDepartment(this);
        }
    }
    public Student removeStudent(@Nonnull Student s){
        if(students.indexOf(s)==-1){
            throw new RuntimeException("Student does not exist in this department.");
        }
        s.setDepartment(Undeclared());
        return students.remove(students.indexOf(s));
    }
    public List<Student> getEnrolledStudents(){
        return students;
    }
    public void addCourse(@Nonnull Course c){
        if(c.getDepartment() != this){
            throw new RuntimeException("Course does not exist in this department. Can not add.");
        }
        if(!courses.contains(c)) {
            courses.add(c);
            c.setDepartment(this);
        }
    }
    public Course removeCourse(@Nonnull Course c){
        if(courses.contains(c)){
            //courses.remove(c);
            c.setDepartment(Undeclared());
        }
        return courses.remove(courses.indexOf(c));
    }
    public List<Course> getCourses(){
        return courses;
    }
    public String toString(){
        return name;
    }
    public boolean equals(Department other){
        return this.name.equals(other.getName());
    }
}
