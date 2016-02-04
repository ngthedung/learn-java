package com.hkt.module.school.util;

import java.util.List;

import com.hkt.module.school.entity.Course;
import com.hkt.module.school.entity.CourseSession;
import com.hkt.module.school.entity.CourseSessionRegistration;
import com.hkt.module.school.entity.Student;
import com.hkt.module.school.entity.Teacher;
import com.hkt.util.IOUtil;
import com.hkt.util.json.JSONReader;

public class SchoolScenario {
  private List<Course>                    courses;
  private List<Student>                   students;
  private List<Teacher>                   teachers;
  private List<CourseSession>             courseSessions;
  private List<CourseSessionRegistration> courseSessionRegistrations;
  
  public List<Course> getCourses() {
    return courses;
  }
  
  public void setCourses(List<Course> courses) {
    this.courses = courses;
  }
  
  public List<Student> getStudents() {
    return students;
  }
  
  public void setStudents(List<Student> students) {
    this.students = students;
  }
  
  public List<Teacher> getTeachers() {
    return teachers;
  }
  
  public void setTeachers(List<Teacher> teachers) {
    this.teachers = teachers;
  }
  
  public List<CourseSession> getCourseSessions() {
    return courseSessions;
  }
  
  public void setCourseSessions(List<CourseSession> courseSessions) {
    this.courseSessions = courseSessions;
  }
  
  public List<CourseSessionRegistration> getCourseSessionRegistrations() {
    return courseSessionRegistrations;
  }
  
  public void setCourseSessionRegistrations(List<CourseSessionRegistration> courseSessionRegistrations) {
    this.courseSessionRegistrations = courseSessionRegistrations;
  }
  
  static public SchoolScenario load(String res) throws Exception {
    JSONReader reader = new JSONReader(IOUtil.loadRes(res)) ;
    SchoolScenario scenario = reader.read(SchoolScenario.class) ;
    return scenario ;
  }
  
  static public SchoolScenario loadTest() throws Exception {
    return load("classpath:scenario/school.json") ;
  }
}