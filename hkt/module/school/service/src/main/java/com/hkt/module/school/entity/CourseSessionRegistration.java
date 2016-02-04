package com.hkt.module.school.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import com.hkt.module.core.entity.AbstractPersistable;

@Table
@Entity
public class CourseSessionRegistration extends AbstractPersistable<Long> {
  private static final long serialVersionUID = 1L;
  
  public static enum Status {
    REGISTERED, ACCEPTED, DROPPED
  };
  
  private String courseSessionId;
  private String studentLoginId;
  private Status status;
  
  public CourseSessionRegistration() {
    
  }
  
  public CourseSessionRegistration(CourseSession session, Student student) {
    this.courseSessionId = session.getSessionId();
    this.studentLoginId = student.getLoginId();
  }
  
  public String getCourseSessionId() {
    return courseSessionId;
  }
  
  public void setCourseSessionId(String sessionId) {
    this.courseSessionId = sessionId;
  }
  
  public String getStudentLoginId() {
    return studentLoginId;
  }
  
  public void setStudentLoginId(String studentLoginId) {
    this.studentLoginId = studentLoginId;
  }
  
  public Status getStatus() {
    return status;
  }
  
  public void setStatus(Status status) {
    this.status = status;
  }
}
