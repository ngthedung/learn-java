package com.hkt.module.school.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.hkt.module.school.entity.Student;

public interface StudentRepository extends CrudRepository<Student, Long>, StudentRepositoryCustom {
  
  Student getByLoginId(String loginId);
  
  List<Student> findByFirstName(String firstName);
  
}
