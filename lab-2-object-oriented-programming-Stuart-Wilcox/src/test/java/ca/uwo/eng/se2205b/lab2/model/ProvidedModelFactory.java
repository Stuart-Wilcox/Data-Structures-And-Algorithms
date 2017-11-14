package ca.uwo.eng.se2205b.lab2.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Test fixture used to create a test model for test cases.
 */
public class ProvidedModelFactory {

    /**
     * Creates the model shown in
     * <a href="https://uwoece-se2205b-2017.github.io/labs/02-oop-serialization#question-0">Q1 Deliverable</a>.
     *
     * @return List of Department values
     */
    public static List<Department> createModel() {
        //create the departments
        Department CEE = new RealDepartment("Civil and Environmental Engineering");
        Department ECE = new RealDepartment("Electrical and Computer Engineering");
        Department AM = new RealDepartment("Applied Math");

        //create the courses
        Course AM1413 = new RealCourse("Calculus", "AM1413", AM, 6);
        Course ES1022 = new RealCourse("Statics", "ES1022", CEE, 6);
        Course ES1036 = new RealCourse("Programming Fundamentals I", "ES1036", ECE, 5);
        Course SE2205 = new RealCourse("Data Structures and Algorithms", "SE2205", ECE, 10);

        //create the students (and add them to correct departments)
        Student jsmith1 = new RealStudent("John", "Smith", 1111, CEE);
        Student smclachlan2 = new RealStudent("Sarah", "McLachlan", 2222, ECE);
        Student gwilder3 = new RealStudent("Gene", "Wilder", 3333, ECE);
        Student rweasley4 = new RealStudent("Ron", "Weasley", 4444, ECE);
        Student mpham5 = new RealStudent("Minh", "Pham", 5555, ECE);
        Student gtakei6 = new RealStudent("George", "Takei", 6666, AM);
        Student rnader7 = new RealStudent("Ralph", "Nader", 7777, AM);
        Student jtarzan8 = new RealStudent("Jane", "Tarzan", 8888, ECE);

        //enroll students in the proper courses
        AM1413.enrollStudent(jsmith1);
        AM1413.enrollStudent(smclachlan2);
        AM1413.enrollStudent(gwilder3);
        AM1413.enrollStudent(mpham5);
        AM1413.enrollStudent(gtakei6);
        AM1413.enrollStudent(rnader7);
        ES1022.enrollStudent(jsmith1);
        ES1022.enrollStudent(rweasley4);
        ES1022.enrollStudent(mpham5);
        ES1022.enrollStudent(rnader7);
        ES1036.enrollStudent(smclachlan2);
        ES1036.enrollStudent(rnader7);
        SE2205.enrollStudent(smclachlan2);
        SE2205.enrollStudent(gwilder3);
        SE2205.enrollStudent(rweasley4);
        SE2205.enrollStudent(gtakei6);
        SE2205.enrollStudent(rnader7);

        //build a list to return
        List<Department> departmentList = new ArrayList<>();
        departmentList.add(CEE);
        departmentList.add(ECE);
        departmentList.add(AM);

        return departmentList;
    }

   }
