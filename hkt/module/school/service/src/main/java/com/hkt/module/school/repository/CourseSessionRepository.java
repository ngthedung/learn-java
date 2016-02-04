package com.hkt.module.school.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import com.hkt.module.school.entity.CourseSession;

public interface CourseSessionRepository extends CrudRepository<CourseSession, Long>, CourseSessionRepositoryCustom {
  
  CourseSession getBySessionId(String sessionId);
  
  List<CourseSession> findByCourseCode(String courseCode);
  
  List<CourseSession> findByTeacherLoginId(String teacherLoginId);
  
  Page<CourseSession> findByCourseCode(String courseCode, Pageable pageable);
  
  @Modifying
  @Query(value = "DELETE FROM CourseSession WHERE sessionId = :sessionId")
  public void deleteBySessionId(@Param("sessionId") String sessionId);
  
  @Modifying
  @Query(value = "DELETE FROM CourseSession WHERE courseCode = :courseCode")
  public void deleteByCourseCode(@Param("courseCode") String courseCode);
  
  @Modifying
  @Query(value = "DELETE FROM CourseSession WHERE teacherLoginId = :teacherLoginId")
  public void deleteByTeacherLoginId(@Param("teacherLoginId") String teacherLoginId);
}
