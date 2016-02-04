package com.hkt.module.school.repository;

import org.springframework.data.repository.CrudRepository;

import com.hkt.module.school.entity.Teacher;

public interface TeacherRepository extends CrudRepository<Teacher, Long> {
  
  Teacher getByLoginId(String loginId);
  
}
