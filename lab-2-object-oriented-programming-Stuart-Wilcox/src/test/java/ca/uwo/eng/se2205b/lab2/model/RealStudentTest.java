package ca.uwo.eng.se2205b.lab2.model;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by Stu on 2017-02-05.
 */
public class RealStudentTest {
        @Test
        public void name() {
            Student s = new RealStudent("k", "l", 6535885);
            s.setFirstName("Tom");
            s.setLastName("Brady");
            assertEquals("Tom", s.getFirstName());
            assertEquals("Brady", s.getLastName());
        }

        @Test
        public void nameConstructor() {
            Student s = new RealStudent("Matt", "Ryan", 1010101);
            assertEquals("Matt", s.getFirstName());
            assertEquals("Ryan", s.getLastName());
        }

        @Test
        public void nameConstructorId() {
            Student s = new RealStudent("SuperBowl", "LI", 51);
            assertEquals("SuperBowl", s.getFirstName());
            assertEquals("LI", s.getLastName());
            assertEquals(51, s.getId());
        }

        @Test
        public void dropCourseEmptyCourses() {
            Student s = new RealStudent("john", "doe", 5678);
            try {
                s.drop(0);
                fail("Missing IndexOutOfBoundsException");
            } catch (IndexOutOfBoundsException e) {

            }
        }

        @Test
        public void getDepartmentNoDepartment() {
            Student s = new RealStudent("james", "may", 8675309);
            assertEquals(s.getDepartment(), null);
        }

        @Test
        public void getIdNoId() {
            Student s = new RealStudent("Jean", "Valjean", 24601);
            assertEquals(s.getId(), 24601);
        }

        @Test
        public void fullConstructor(){
            Department d = new RealDepartment("Science");
            Student s = new RealStudent("Rick", "Shaw", 25085687, d);
            assertEquals("Rick", s.getFirstName());
            assertEquals("Shaw", s.getLastName());
            assertEquals(25085687, s.getId());
            assertEquals(d, s.getDepartment());
        }

        @Test
        public void setDepartmentRealandNull(){
            Department d = new RealDepartment("Mathematics");
            Department q = new RealDepartment("Chemistry");
            Student s = new RealStudent("tele","vision",2947564,d);
            assertEquals(d, s.getDepartment());
            s.setDepartment(q);
            assertEquals(q, s.getDepartment());
            try {
                s.setDepartment(null);
            }catch(NullPointerException e){

            }

        }

        @Test
        public void enrollRealAndNull(){
            Department d = new RealDepartment("wdefr");
            Course c = new RealCourse("Statistics for Engineers", "SS2141", d, 100);
            Student s1 = new RealStudent("ok", "tire", 56790, d);
            s1.enroll(c);
            assertEquals(s1.getCourseList().get(0), c);
            try {
                s1.enroll(null);
            }catch(NullPointerException e){
                assertEquals(e.getMessage(), "Course is null");
            }
        }

        @Test
        public void enrollCourseTwice(){
            Department d = new RealDepartment("Clock");
            Course c = new RealCourse("Statistics for Engineers", "SS2141", d, 100);
            Student s1 = new RealStudent("owen", "sound", 29387556, d);
            s1.enroll(c);
            try{
                s1.enroll(c);
            }catch(RuntimeException e){

            }
            s1.drop(c);
            s1.enroll(c);
        }

        @Test
        public void dropCourseNoCourses(){
            Department d = new RealDepartment("Statistics");
            Course c = new RealCourse("Statistics for Engineers", "SS2141", d, 100);
            Student s1 = new RealStudent("the", "who", 795, d);
            try{
                s1.drop(c);
            }
            catch(RuntimeException r){

            }
            try{
                s1.drop(0);
            }
            catch(RuntimeException r){

            }
        }
}
