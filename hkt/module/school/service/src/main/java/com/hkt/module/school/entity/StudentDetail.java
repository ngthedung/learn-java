package com.hkt.module.school.entity;

import java.io.Serializable;
import java.util.List;

public class StudentDetail implements Serializable {
  private Student                         student;
  private List<CourseSessionRegistration> courseSessionbRegistrations;
  
  public StudentDetail() {
    
  }
  
  public StudentDetail(Student student, List<CourseSessionRegistration> courseSeesionRegistrations) {
    this.student = student;
    this.courseSessionbRegistrations = courseSeesionRegistrations;
  }
  
  public Student getStudent() {
    return student;
  }
  
  public void setStudent(Student student) {
    this.student = student;
  }
  
  public List<CourseSessionRegistration> getCourseRegistrations() {
    return courseSessionbRegistrations;
  }
  
  public void setCourseRegistrations(List<CourseSessionRegistration> courseSeesionRegistrations) {
    this.courseSessionbRegistrations = courseSeesionRegistrations;
  }
  
}
