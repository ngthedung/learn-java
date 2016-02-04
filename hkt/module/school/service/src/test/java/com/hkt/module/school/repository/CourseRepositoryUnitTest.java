package com.hkt.module.school.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.hkt.module.school.AbstractUnitTest;
import com.hkt.module.school.entity.Course;

@Transactional
public class CourseRepositoryUnitTest extends AbstractUnitTest {
  @Autowired
  CourseRepository repository;
  
  @Test
  public void testCrud() {
    Course course = new Course("geography101", "Geography");
    course = repository.save(course);
    assertEquals(course, repository.getByCode("geography101"));
    
    course.setName("GeoraphyABC");
    assertEquals("GeoraphyABC", repository.findOne(course.getId()).getName());
    assertEquals(1, repository.count());
    
    repository.delete(course);
    assertNull(repository.findOne(course.getId()));
    assertEquals(0, repository.count());
  }
  
  @Test
  public void testFind() {
    for (int i = 0; i < 5; i++) {
      repository.save(createCourse("course" + i, "test course" + i));
    }
    List<Course> list = (List<Course>)repository.findAll();
    assertEquals(5, list.size());
  }
  
}
