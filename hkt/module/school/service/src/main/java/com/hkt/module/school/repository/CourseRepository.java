package com.hkt.module.school.repository;

import org.springframework.data.repository.CrudRepository;
import com.hkt.module.school.entity.Course;

public interface CourseRepository extends CrudRepository<Course, Long> {
  Course getByCode(String courseCode);
  
}
