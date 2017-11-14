package ca.uwo.eng.se2205b.lab2.model;

import org.junit.Test;
import org.omg.PortableInterceptor.Interceptor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Test the {@link Course} implementation
 */
public class RealCourseTest {

    /**
     * Test the name property
     */
    @Test
    public void name() {
        Department d = new RealDepartment("IOP");
        Course c = new RealCourse("Yeah", "ABC", d, 100);
        assertEquals(c.getName(), "Yeah");
        assertEquals(c.getCourseCode(), "ABC");
        c.setName("12345");
        assertEquals(c.getName(), "12345");
        c.setName(null);
        assertEquals(c.getName(), null);
    }

    /**
     * Test department interactions
     */
    @Test
    public void department() {
        Department chem = new RealDepartment("Chemistry");
        Course s = new RealCourse("Organic Chemistry", "CHEM2240", chem, 150);
        assertEquals(chem.getName(), s.getDepartment().getName());
        Department bio = new RealDepartment("Biology");
        s.setDepartment(bio);
        assertEquals(bio.getCourses().get(0).getName(), s.getName());//make sure the course is actually in bio dept
    }

    /**
     * Test that the maximum occupancy behaves properly
     */
    @Test
    public void maximumOccupancy() {
        Department d = new RealDepartment("Dept");
        Course c = new RealCourse("Name", "Code", d, 30);
        Student[] s = new Student[50];
        for(int i=0;i<20;i++){
            s[i]= new RealStudent("Joe", Integer.toString(i), i*50+2);//Joe 0, Joe 1, Joe 2, ... , Joe 19
            c.enrollStudent(s[i]);
        }
        for(int i=0;i<20;i++){
            assertEquals(c.getEnrolledStudents().get(i).getLastName(), Integer.toString(i));
        }
        for(int i=20;i<30;i++){
            s[i]=new RealStudent("Joe", Integer.toString(i), i*50+2);
            c.enrollStudent(s[i]);
        }
        try{
            s[30]=new RealStudent("Joe", "30", 9876543);
        }catch(CourseMaxCapacityStoreException e){
            assertEquals(e.getMessage(), "Code: Name can not store Joe 30, maximum capacity reached.");
        }
    }

    /**
     * Test that adding/removing students behaves
     */
    @Test
    public void students() {
        Department d = new RealDepartment("Statistics");
        Course c = new RealCourse("Stats for engineers", "SS2141", d, 230);
        Student[] s = new RealStudent[230];
        for(int i=0;i<230;i++){
            s[i]=new RealStudent("John", Integer.toString(i), 8908676+i);
            c.enrollStudent(s[i]);//enroll from the course end
        }
        assertEquals(c.getEnrolledStudents().size(), 230);
        for(int i=0;i<230;i++){
            c.removeStudent(s[i]);//remove from course end
        }
        assertEquals(c.getEnrolledStudents().size(), 0);
        for(int i =0;i<230;i++){
            s[i].enroll(c);//enroll from student end
        }
        assertEquals(c.getEnrolledStudents().size(), 230);
        for(int i=0;i<230;i++){
            s[i].drop(c);//drop from student end
        }
        assertEquals(c.getEnrolledStudents().size(), 0);
    }

}