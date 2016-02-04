package com.hkt.module.school.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.hkt.module.core.entity.AbstractPersistable;
import com.hkt.module.school.repository.CourseSessionRepositoryListener;

@Table
@Entity
//@EntityListeners({CourseSessionRepositoryListener.class})
public class CourseSession extends AbstractPersistable<Long> {
  private static final long serialVersionUID = 1L;
  
  @NotNull
  @Column(unique = true)
  private String            sessionId;
  private String            courseCode;
  private String            teacherLoginId;
  
  public CourseSession() {
  }
  
  public CourseSession(String sessionId, Course course, Teacher teacher) {
    this.sessionId = sessionId;
    this.courseCode = course.getCode();
    this.teacherLoginId = teacher.getLoginId();
  }
  
  public String getSessionId() {
    return sessionId;
  }
  
  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }
  
  public String getCourseCode() {
    return courseCode;
  }
  
  public void setCourseCode(String courseCode) {
    this.courseCode = courseCode;
  }
  
  public String getTeacherLoginId() {
    return teacherLoginId;
  }
  
  public void setTeacherLoginId(String teacherLoginId) {
    this.teacherLoginId = teacherLoginId;
  }
  
}
