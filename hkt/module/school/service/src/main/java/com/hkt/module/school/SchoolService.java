package com.hkt.module.school;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hkt.module.core.ServiceCallback;
import com.hkt.module.core.entity.FilterQuery;
import com.hkt.module.core.entity.FilterResult;
import com.hkt.module.school.entity.Course;
import com.hkt.module.school.entity.CourseSession;
import com.hkt.module.school.entity.CourseSessionRegistration;
import com.hkt.module.school.entity.Student;
import com.hkt.module.school.entity.Teacher;
import com.hkt.module.school.repository.CourseRepository;
import com.hkt.module.school.repository.CourseSessionRegistrationRepository;
import com.hkt.module.school.repository.CourseSessionRepository;
import com.hkt.module.school.repository.StudentRepository;
import com.hkt.module.school.repository.TeacherRepository;
import com.hkt.module.school.util.SchoolScenario;

@Service("SchoolService")
public class SchoolService {
  @Autowired
  private CourseSessionRegistrationRepository registrationRepo;
  
  @Autowired
  private CourseSessionRepository             sessionRepo;
  
  @Autowired
  private CourseRepository                    courseRepo;
  
  @Autowired
  private StudentRepository                   studentRepo;
  
  @Autowired
  private TeacherRepository                   teacherRepo;
  
  @Transactional(readOnly = true)
  public Course getCourseByCode(String code) {
    return courseRepo.getByCode(code);
  }
  
  @Transactional(readOnly = true)
  public Iterable<Course> getAllCourse() {
    return courseRepo.findAll();
  }
  
  @Transactional
  public boolean deleteCourseByCode(String code) {
    Course course = courseRepo.getByCode(code);
    return deleteCourse(course, null);
  }
  
  @Transactional
  public boolean deleteCourse(Course course, ServiceCallback<SchoolService> callback) {
    courseRepo.delete(course);
    List<CourseSession> courseSessions = sessionRepo.findByCourseCode(course.getCode());
    sessionRepo.deleteByCourseCode(course.getCode());
    for (CourseSession courseSession : courseSessions) {
      registrationRepo.deleteByCourseSessionId(courseSession.getSessionId());
    }
    if (callback != null) {
      callback.callback(this);
    }
    return true;
  }
  
  @Transactional
  public Course saveCourse(Course course) {
    return courseRepo.save(course);
  }
  
  @Transactional(readOnly = true)
  public Student getStudentByLoginId(String loginId) {
    return studentRepo.getByLoginId(loginId);
  }
  
  @Transactional(readOnly = true)
  public FilterResult<Student> searchStudents(FilterQuery query) {
    if (query == null) {
      query = new FilterQuery();
    }
    return studentRepo.search(query);
  }
  
  @Transactional
  public boolean deleteStudentByLoginId(String loginId) {
    Student student = studentRepo.getByLoginId(loginId);
    return deleteStudent(student, null);
  }
  
  @Transactional
  public boolean deleteStudent(Student student, ServiceCallback<SchoolService> callback) {
    studentRepo.delete(student);
    List<CourseSessionRegistration> courseSessionRegistrations = registrationRepo.findByStudentLoginId(student
        .getLoginId());
    registrationRepo.deleteByStudentLoginId(student.getLoginId());
    for (CourseSessionRegistration registration : courseSessionRegistrations) {
      sessionRepo.deleteBySessionId(registration.getCourseSessionId());
    }
    if (callback != null) {
      callback.callback(this);
    }
    return true;
  }
  
  @Transactional
  public Student saveStudent(Student student) {
    return studentRepo.save(student);
  }
  
  @Transactional(readOnly = true)
  public Teacher getTeacherByLoginId(String loginId) {
    return teacherRepo.getByLoginId(loginId);
  }
  
  @Transactional(readOnly = true)
  public Iterable<Teacher> getAllTeacher() {
    return teacherRepo.findAll();
  }
  
  @Transactional
  public boolean deleteTeacherByLoginId(String loginId) {
    Teacher teacher = teacherRepo.getByLoginId(loginId);
    return deleteTeacher(teacher, null);
  }
  
  @Transactional
  public boolean deleteTeacher(Teacher teacher, ServiceCallback<SchoolService> callback) {
    teacherRepo.delete(teacher);
    List<CourseSession> courseSessions = sessionRepo.findByTeacherLoginId(teacher.getLoginId());
    sessionRepo.deleteByTeacherLoginId(teacher.getLoginId());
    for (CourseSession session : courseSessions) {
      registrationRepo.deleteByCourseSessionId(session.getSessionId());
    }
    if (callback != null) {
      callback.callback(this);
    }
    return true;
  }
  
  @Transactional
  public Teacher saveTeacher(Teacher teacher) {
    return teacherRepo.save(teacher);
  }
  
  @Transactional(readOnly = true)
  public CourseSession getSessionBySesionId(String sessionId) {
    return sessionRepo.getBySessionId(sessionId);
  }
  
  @Transactional(readOnly = true)
  public List<CourseSession> findCourseSessionByCode(String courseCode) {
    return sessionRepo.findByCourseCode(courseCode);
  }
  
  @Transactional(readOnly = true)
  public List<CourseSession> findSessionByTeacherLoginId(String teacherLoginId) {
    return sessionRepo.findByTeacherLoginId(teacherLoginId);
  }
  
  @Transactional(readOnly = true)
  public List<CourseSession> findSessionByCourseCode(String courseCode) {
    return sessionRepo.findByCourseCode(courseCode);
  }
  
  @Transactional
  public boolean deleteSessionBySessionId(String sessionId) {
    CourseSession session = sessionRepo.getBySessionId(sessionId);
    return deleteSession(session, null);
  }
  
  @Transactional
  public boolean deleteSession(CourseSession session, ServiceCallback<SchoolService> callback) {
    sessionRepo.delete(session);
    if (callback != null) {
      callback.callback(this);
    }
    return true;
  }
  
  @Transactional
  public CourseSession saveCourseSession(CourseSession session) {
    return sessionRepo.save(session);
  }
  
  @Transactional(readOnly = true)
  public List<CourseSessionRegistration> findRegistrationBySessionId(String sessionId) {
    return registrationRepo.findByCourseSessionId(sessionId);
  }
  
  @Transactional(readOnly = true)
  public List<CourseSessionRegistration> findRegistrationByStudentLoginId(String loginId) {
    return registrationRepo.findByStudentLoginId(loginId);
  }
  
  @Transactional
  public boolean deleteSessionRegistration(CourseSessionRegistration registration) {
    return deleteRegistration(registration, null);
  }
  
  @Transactional
  public boolean deleteRegistration(CourseSessionRegistration registration, ServiceCallback<SchoolService> callback) {
    registrationRepo.delete(registration);
    if (callback != null) {
      callback.callback(this);
    }
    return true;
  }
  
  @Transactional
  public CourseSessionRegistration saveRegistration(CourseSessionRegistration registration) {
    return registrationRepo.save(registration);
  }
  
  @Transactional
  public void deleteAll() {
    registrationRepo.deleteAll();
    sessionRepo.deleteAll();
    courseRepo.deleteAll();
    studentRepo.deleteAll();
    teacherRepo.deleteAll();
  }
  
  private void saveCourses(List<Course> courses) {
    if (courses != null) {
      for (Course course : courses)
        saveCourse(course);
    }
  }
  
  private void saveStudents(List<Student> students) {
    if (students != null) {
      for (Student student : students) {
        saveStudent(student);
      }
    }
  }
  
  private void saveTeachers(List<Teacher> teachers) {
    if (teachers != null) {
      for (Teacher teacher : teachers) {
        saveTeacher(teacher);
      }
    }
  }
  
  private void saveCourseSessions(List<CourseSession> sessions) {
    if (sessions != null) {
      for (CourseSession session : sessions) {
        saveCourseSession(session);
      }
    }
  }
  
  private void saveCourseSessionRegistrations(List<CourseSessionRegistration> registrations) {
    if (registrations != null) {
      for (CourseSessionRegistration registration : registrations) {
        saveRegistration(registration);
      }
    }
  }
  
  @Transactional
  public void createScenario(SchoolScenario senario) {
    saveCourses(senario.getCourses());
    saveStudents(senario.getStudents());
    saveTeachers(senario.getTeachers());
    saveCourseSessions(senario.getCourseSessions());
    saveCourseSessionRegistrations(senario.getCourseSessionRegistrations());
  }
  
  @Transactional(readOnly = true)
  public FilterResult<Student> searchStudent(FilterQuery query) {
    if (query == null)
      query = new FilterQuery();
    return studentRepo.search(query);
  }
  
  @Transactional(readOnly = true)
  public FilterResult<CourseSession> searchCourseSession(FilterQuery query) {
    if (query == null)
      query = new FilterQuery();
    return sessionRepo.search(query);
  }
  
  @Transactional(readOnly = true)
  public FilterResult<CourseSessionRegistration> searchCourseRegistration(FilterQuery query) {
    if (query == null)
      query = new FilterQuery();
    return registrationRepo.search(query);
  }
}
