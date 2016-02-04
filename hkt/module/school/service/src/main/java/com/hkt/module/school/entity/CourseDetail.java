package com.hkt.module.school.entity;

import java.io.Serializable;
import java.util.List;

public class CourseDetail implements Serializable {
  private Course              course;
  private List<CourseSession> courseSessions;
  
  public CourseDetail() {
    
  }
  
  public CourseDetail(Course course, List<CourseSession> courseSessions) {
    this.course = course;
    this.courseSessions = courseSessions;
  }
  
  public Course getCourse() {
    return course;
  }
  
  public void setCourse(Course course) {
    this.course = course;
  }
  
  public List<CourseSession> getCourseSessions() {
    return courseSessions;
  }
  
  public void setCourseSessions(List<CourseSession> courseSessions) {
    this.courseSessions = courseSessions;
  }
  
}
