package com.hkt.module.school.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import com.hkt.module.school.entity.CourseSessionRegistration;

public interface CourseSessionRegistrationRepository extends CrudRepository<CourseSessionRegistration, Long>,
    CourseSessionRegistrationRepositoryCustom {
  List<CourseSessionRegistration> findByCourseSessionId(String courseSessionId);
  
  List<CourseSessionRegistration> findByStudentLoginId(String studentLoginId);
  
  @Modifying
  @Query(value = "DELETE FROM CourseSessionRegistration WHERE studentLoginId = :studentLoginId")
  public void deleteByStudentLoginId(@Param("studentLoginId") String studentLoginId);
  
  @Modifying
  @Query(value = "DELETE FROM CourseSessionRegistration WHERE courseSessionId = :courseSessionId")
  public void deleteByCourseSessionId(@Param("courseSessionId") String courseSessionId);
  
}
