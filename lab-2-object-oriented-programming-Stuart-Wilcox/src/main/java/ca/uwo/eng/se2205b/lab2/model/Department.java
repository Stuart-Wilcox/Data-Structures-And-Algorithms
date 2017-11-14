package ca.uwo.eng.se2205b.lab2.model;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Department in a University
 */
public interface Department {

    /**
     * Get the unique name of the Department
     * @return Non-{@code null} name
     */
    String getName();

    /**
     * Return the unique name of the department.
     * @param name Non-{@code null} name
     *
     * @throws IllegalArgumentException if {@code name} is empty
     */
    void setName(@Nonnull String name);

    /**
     * Enroll a student in the Department.
     * @param student Non-{@code null} student to add.
     */
    void enrollStudent(@Nonnull Student student);

    /**
     * Remove a {@link Student} from a {@code Department}
     * @param student Removed student
     * @return The Student instance, if they were removed, otherwise {@code null}.
     */
    Student removeStudent(@Nonnull Student student);

    /**
     * Get all of the currently enrolled students
     * @return Non-{@code null} {@code List} of {@link Student}s, it may be empty.
     */
    List<Student> getEnrolledStudents();

    /**
     * Adds a course to a department
     * @param course Course to add
     */
    void addCourse(@Nonnull Course course);

    /**
     * Removes a course from the {@code Department}
     * @param course Course to remove
     * @return Instance removed, {@code null} if none are removed
     */
    Course removeCourse(@Nonnull Course course);

    /**
     * Get all of the courses in the Department
     * @return List of all courses, may be empty, but never {@code null}
     */
    List<Course> getCourses();

}
