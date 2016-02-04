package com.hkt.module.school.entity;

import java.io.Serializable;
import java.util.List;

public class TeacherDetail implements Serializable {
  private Teacher             teacher;
  private List<CourseSession> courseSessions;
  
  public TeacherDetail() {
    
  }
  
  public TeacherDetail(Teacher teacher, List<CourseSession> courseSessions) {
    this.teacher = teacher;
    this.courseSessions = courseSessions;
  }
  
  public Teacher getTeacher() {
    return teacher;
  }
  
  public void setTeacher(Teacher teacher) {
    this.teacher = teacher;
  }
  
  public List<CourseSession> getCourseSessions() {
    return courseSessions;
  }
  
  public void setCourseSessions(List<CourseSession> courseSessions) {
    this.courseSessions = courseSessions;
  }
  
}
