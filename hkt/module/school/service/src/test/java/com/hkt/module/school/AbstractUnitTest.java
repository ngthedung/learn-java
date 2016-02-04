package com.hkt.module.school;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import com.hkt.module.school.entity.Course;
import com.hkt.module.school.entity.CourseSession;
import com.hkt.module.school.entity.CourseSessionRegistration;
import com.hkt.module.school.entity.CourseSessionRegistration.Status;
import com.hkt.module.school.entity.Student;
import com.hkt.module.school.entity.Teacher;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
    "classpath:META-INF/spring/module.core.service.xml",
    "classpath:META-INF/spring/module.account.service.xml", 
    "classpath:META-INF/spring/module.school.service.xml" 
})
@TransactionConfiguration(defaultRollback = true)
public abstract class AbstractUnitTest {
  
  public Course createCourse(String code, String name) {
    Course course = new Course();
    course.setCode(code);
    course.setName(name);
    return course;
  }
  
  public Student createStudent(String loginId, String firstName, String lastName) {
    Student student = new Student();
    student.setLoginId(loginId);
    student.setFirstName(firstName);
    student.setLastName(lastName);
    return student;
  }
  
  public Teacher createTeacher(String loginId, String firstName, String lastName) {
    Teacher teacher = new Teacher();
    teacher.setLoginId(loginId);
    teacher.setFirstName(firstName);
    teacher.setLastName(lastName);
    return teacher;
  }
  
  public CourseSession createCourseSession(String sessionId, String courseCode, String teacherLoginId) {
    CourseSession coursesession = new CourseSession();
    coursesession.setSessionId(sessionId);
    coursesession.setCourseCode(courseCode);
    coursesession.setTeacherLoginId(teacherLoginId);
    return coursesession;
  }
  
  public CourseSessionRegistration createCourseSessionRegistration(CourseSession courseSession, Student student) {
    CourseSessionRegistration courseRegis = new CourseSessionRegistration();
    courseRegis.setCourseSessionId(courseSession.getSessionId());
    courseRegis.setStudentLoginId(student.getLoginId());
    courseRegis.setStatus(Status.REGISTERED);
    return courseRegis;
  }
}
