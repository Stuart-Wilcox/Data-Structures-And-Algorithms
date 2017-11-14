package ca.uwo.eng.se2205b.lab2.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Test the {@link Department} implementation.
 */
public class RealDepartmentTest {

    /**
     * Test the name property
     */
    @Test
    public void name() {
        RealDepartment.clearDepartmentList();
        Department d = new RealDepartment("ABCD");
        assertEquals("ABCD", d.getName());
        d=RealDepartment.Undeclared();
        assertEquals(d.getName(), RealDepartment.Undeclared().getName());
        d = new RealDepartment("Kinesiology");
        d.setName("Physiology");
        assertEquals(d.getName(), "Physiology");
    }

    /**
     * Test course changes
     */
    @Test
    public void courses() {
        RealDepartment.clearDepartmentList();
        Department d = new RealDepartment("A");
        Course c = new RealCourse("Eng", "ES1050", new RealDepartment("O"), 100);
        try {
            d.addCourse(c);//should throw exception since c is in a different department
        }catch(RuntimeException e){
            assertEquals(e.getMessage(), "Course does not exist in this department. Can not add.");
        }
        c.setDepartment(d);//change the department to work
        d.addCourse(c);
        assertEquals(d.getCourses().get(0), c);//make sure its actually there

        Course[] t = new Course[40];
        for(int i=0;i<40;i++){
            t[i]=new RealCourse("Course", "Thing"+Integer.toString(i), d, 100);
            d.addCourse(t[i]);//add 40 more courses to the department
        }
        assertEquals(d.getCourses().size(), 41);//make sure they are all there

        d.removeCourse(c);
        assertEquals(d.getCourses().size(), 40);//make sure remove one works
        for(int i=0;i<40;i++){
            d.removeCourse(t[i]);//remove the 40 remaining courses
            assertEquals(t[i].getDepartment(), RealDepartment.Undeclared());//make sure the course is out of the department
        }
        assertEquals(d.getCourses().size(), 0);//make sure list is empty

    }

    /**
     * Test student changes
     */
    @Test
    public void students() {
        RealDepartment.clearDepartmentList();
        Department d = new RealDepartment("Actuarial Science");
        Student s = new RealStudent("Jimmy", "Zou", 250884942, d);
        d.enrollStudent(s);
        assertEquals(d.getEnrolledStudents().get(0), s);
        assertEquals(s.getDepartment(), d);
        Student t = new RealStudent("Srivatsan", "Srinivasan", 250834643);
        d.enrollStudent(t);
        assertTrue(d.getEnrolledStudents().contains(t));

        d.removeStudent(t);
        d.removeStudent(s);
        assertEquals(d.getEnrolledStudents().size(), 0);

        d.enrollStudent(s);
        d.enrollStudent(s);
        assertEquals(d.getEnrolledStudents().size(), 1);//student was added twice but only exists once

        d.removeStudent(s);
        assertEquals(s.getDepartment(), RealDepartment.Undeclared());//removing students puts them in the Undeclared department
    }
}