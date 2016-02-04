package com.hkt.module.school.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.hkt.module.core.entity.FilterQuery;
import com.hkt.module.school.AbstractUnitTest;
import com.hkt.module.school.entity.Course;
import com.hkt.module.school.entity.CourseSession;
import com.hkt.module.school.entity.CourseSessionRegistration;
import com.hkt.module.school.entity.Student;
import com.hkt.module.school.entity.CourseSessionRegistration.Status;
import com.hkt.module.school.entity.Teacher;

@Transactional
public class CourseSessionRegistrationRepositoryUnitTest extends AbstractUnitTest {
  @Autowired
  CourseSessionRegistrationRepository repository;
  
  @Test
  public void testCrud() {
    Student student = new Student("manh.student", "Manh", "Nguyen Van");
    Course course = new Course("math101", "Math");
    Teacher teacher = new Teacher("cuong.teacher", "Cuong", "Le Van");
    CourseSession courseSession = new CourseSession("math101-012013", course, teacher);
    
    CourseSessionRegistration courseSessionReg = new CourseSessionRegistration(courseSession, student);
    courseSessionReg.setStatus(Status.REGISTERED);
    courseSessionReg = repository.save(courseSessionReg);
    assertEquals(courseSessionReg, repository.findOne(courseSessionReg.getId()));
    
    courseSessionReg.setStatus(Status.DROPPED);
    assertEquals(Status.DROPPED, repository.findOne(courseSessionReg.getId()).getStatus());
    assertEquals(1, repository.count());
    
    repository.delete(courseSessionReg);
    assertNull(repository.findOne(courseSessionReg.getId()));
    assertEquals(0, repository.count());
  }
  
  @Test
  public void testFind() {
    for (int i = 0; i < 5; i++) {
      Student student = new Student("minh" + i + ".student", "first" + i, "last" + i);
      Course course = new Course("math101", "Math");
      Teacher teacher = new Teacher("cuong.teacher", "Cuong", "Le Van");
      CourseSession courseSession = new CourseSession("math101-012013", course, teacher);
      repository.save(createCourseSessionRegistration(courseSession, student));
    }
    assertEquals(5, repository.findByCourseSessionId("math101-012013").size());
    assertEquals(1, repository.findByStudentLoginId("minh0.student").size());
    repository.deleteByCourseSessionId("math101-012013");
    assertEquals(0, repository.findByCourseSessionId("math101-012013").size());
  }
  
  @Test
  public void testSearch() {
    for (int i = 0; i < 5; i++) {
      Student student = new Student("minh" + i + ".student", "first" + i, "last" + i);
      Course course = new Course("math101", "Math");
      Teacher teacher = new Teacher("cuong.teacher", "Cuong", "Le Van");
      CourseSession courseSession = new CourseSession("math101-012013", course, teacher);
      repository.save(createCourseSessionRegistration(courseSession, student));
    }
    FilterQuery query = new FilterQuery();
    query.add("status", FilterQuery.Operator.EQUAL, "REGISTERED");
    assertEquals(5, repository.search(query).getData().size());
  }
  
  @Test
  public void testDelete() {
    for (int i = 0; i < 5; i++) {
      Student student = new Student("minh" + i + ".student", "first" + i, "last" + i);
      Course course = new Course("math101", "Math");
      Teacher teacher = new Teacher("cuong.teacher", "Cuong", "Le Van");
      CourseSession courseSession = new CourseSession("math101-012013", course, teacher);
      repository.save(createCourseSessionRegistration(courseSession, student));
    }
    repository.deleteByCourseSessionId("math101-012013");
    assertEquals(0, repository.count());
    
    for (int i = 0; i < 5; i++) {
      Student student = new Student("minh" + i + ".student", "first" + i, "last" + i);
      Course course = new Course("math101", "Math");
      Teacher teacher = new Teacher("cuong.teacher", "Cuong", "Le Van");
      CourseSession courseSession = new CourseSession("math101-012013", course, teacher);
      repository.save(createCourseSessionRegistration(courseSession, student));
    }
    repository.deleteByStudentLoginId("minh0.student");
    assertEquals(4, repository.count());
  }
}