package com.hkt.module.school;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hkt.module.account.AccountService;
import com.hkt.module.account.util.AccountScenario;
import com.hkt.module.core.ServiceCallback;
import com.hkt.module.school.entity.Course;
import com.hkt.module.school.entity.CourseSession;
import com.hkt.module.school.entity.CourseSessionRegistration;
import com.hkt.module.school.entity.CourseSessionRegistration.Status;
import com.hkt.module.school.entity.Student;
import com.hkt.module.school.entity.Teacher;
import com.hkt.module.school.util.SchoolScenario;
import com.hkt.util.json.JSONSerializer;

public class SchoolServiceUnitTest extends AbstractUnitTest {
  static ServiceCallback<SchoolService> FAIL_CALLBACK = new ServiceCallback<SchoolService>() {
    public void callback(SchoolService service) {
      throw new RuntimeException("Fail. Expect a rollback if method annotate with the Transactionnal");
    }
  };

  @Autowired
  SchoolService service;
  
  @Autowired
  AccountService accountService;

  private SchoolScenario scenario;

  @Before
  public void setup() throws Exception {
    accountService.createScenario(AccountScenario.loadTest());
    scenario = SchoolScenario.loadTest();
    service.createScenario(scenario);
  }

  @After
  public void testDown() throws Exception {
    service.deleteAll();
    accountService.deleteAll();
  }

  @Test
  public void testSerialization() throws IOException {
    assertTrue(scenario.getCourses().size() > 0);
    assertTrue(scenario.getTeachers().size() > 0);
    assertTrue(scenario.getStudents().size() > 0);
    assertTrue(scenario.getCourseSessions().size() > 0);
    assertTrue(scenario.getCourseSessionRegistrations().size() > 0);
    System.out.println(JSONSerializer.INSTANCE.toString(scenario));
  }

  @Test
  public void testRegistration() {
    Course course = new Course("history101", "History");
    course = service.saveCourse(course);
    List<Course> listCourses = (List<Course>)service.getAllCourse(); 
    assertEquals(4, listCourses.size());

//    Student studentThang = new Student("thang.student", "Thang", "Nguyen Van");
//    studentThang = service.saveStudent(studentThang);
    assertEquals(5, service.searchStudents(null).getData().size());

//    Teacher teacherLong = new Teacher("long.teacher", "Long", "Nguyen Trung");
//    teacherLong = service.saveTeacher(teacherLong);
    List<Teacher> listTeachers = (List<Teacher>)service.getAllTeacher(); 
    assertEquals(3, listTeachers.size());

//    CourseSession session1TeacherLong = new CourseSession("history101-012013", course, teacherLong);
//    session1TeacherLong = service.saveCourseSession(session1TeacherLong);

//    CourseSession session2TeacherLong = new CourseSession("history101-092013", course, teacherLong);
//    session2TeacherLong = service.saveCourseSession(session2TeacherLong);
//    assertEquals(2, service.findSessionByTeacherLoginId(teacherLong.getLoginId()).size());

//    CourseSessionRegistration courseRegisThang = new CourseSessionRegistration(session1TeacherLong, studentThang);
//    courseRegisThang.setStatus(Status.REGISTERED);
//    courseRegisThang = service.saveRegistration(courseRegisThang);

    Student studentNgoc = service.getStudentByLoginId("school.ngoc.student");
    Student studentThuy = service.getStudentByLoginId("school.thuy.student");

//    CourseSessionRegistration courseRegisNgoc = new CourseSessionRegistration(session1TeacherLong, studentNgoc);
//    courseRegisNgoc.setStatus(Status.REGISTERED);
//    courseRegisNgoc = service.saveRegistration(courseRegisNgoc);

//    CourseSessionRegistration courseRegisThuy = new CourseSessionRegistration(session1TeacherLong, studentThuy);
//    courseRegisThuy.setStatus(Status.REGISTERED);
//    courseRegisThuy = service.saveRegistration(courseRegisThuy);


//    try {
//      service.deleteRegistration(courseRegisThuy, FAIL_CALLBACK);
//      service.deleteCourse(course, FAIL_CALLBACK);
//      service.deleteStudent(studentThuy, FAIL_CALLBACK);
//      service.deleteTeacher(teacherLong, FAIL_CALLBACK);
//    } catch (Throwable t) {
//      System.out.println("Fail callback exception: " + t.getMessage());
//    }
  }

}
