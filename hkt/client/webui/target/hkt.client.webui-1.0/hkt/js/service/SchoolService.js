define([
  'jquery',
  'service/Server'
], function($, Server) {
  /**@type service.SchoolService */
  var SchoolService = {
    module:  'school',
    service: 'SchoolService',
    
    /**@memberOf service.SchoolService*/
    
    cleanSchoolDB : function(scenario) {
      var request = {
        module : this.module, service : this.service, method : 'deleteAll',
      };
      return Server.POST(request);
    },
    
    createScenario : function(scenario) {
      var request = {
        module : this.module, service : this.service, method : 'createScenario',
        params : { scenario : scenario }
      };
      return Server.POST(request);
    },
    
    getTeacherByLoginId : function(teacherLoginId) {
      var request = {
        module : this.module, service : this.service, method : 'getTeacherByLoginId',
        params : { teacherLoginId : teacherLoginId }
      };
      return Server.POST(request);
    },
    
    saveTeacher : function(teacher) {
      var request = {
        module : this.module, service : this.service, method : 'saveTeacher',
        params : { teacher : teacher }
      };
      return Server.POST(request);
    },
    
    deleteTeacherByLoginId : function(teacher) {
      var request = {
        module : this.module, service : this.service, method : 'deleteTeacherByLoginId',
        params : { teacher : teacher }
      };
      return Server.POST(request);
    },
    
    getAllTeacher : function () {
      var request = {
        module : this.module, service : this.service, method : 'getAllTeacher'
      };
      return Server.POST(request);
    },
    
    getStudentByLoginId : function(studentLoginId) {
      var request = {
        module : this.module, service : this.service, method : 'getStudentByLoginId',
        params : { studentLoginId : studentLoginId }
      };
      return Server.POST(request);
    },
    
    saveStudent : function(student) {
      var request = {
        module : this.module, service : this.service, method : 'saveStudent',
        params : { student : student }
      };
      return Server.POST(request);
    },
    
    deleteStudentByLoginId : function(student) {
      var request = {
        module : this.module, service : this.service, method : 'deleteStudentByLoginId',
        params : { student : student }
      };
      return Server.POST(request);
    },
    
    searchStudent : function(query) {
      var request = {
        module : this.module, service : this.service, method : 'searchStudent',
        params : { query: query }
      };
      return Server.POST(request);
    },
    
    getCourseByCode : function(courseCode) {
      var request = {
        module : this.module, service : this.service, method : 'getCourseByCode',
        params : { courseCode : courseCode }
      };
      return Server.POST(request);
    },
    
    saveCourse : function(course) {
      var request = {
        module : this.module, service : this.service, method : 'saveCourse',
        params : { course : course }
      };
      return Server.POST(request);
    },
    
    deleteCourseByCode : function(courseCode) {
      var request = {
        module : this.module, service : this.service, method : 'deleteCourseByCode',
        params : { courseCode : courseCode }
      };
      return Server.POST(request);
    },
    
    getAllCourse : function () {
      var request = {
        module : this.module, service : this.service, method : 'getAllCourse'
      };
      return Server.POST(request);
    },
    
    getSessionBySessionId : function(sessionId) {
      var request = {
        module : this.module, service : this.service, method : 'getSessionBySesionId',
        params : { sessionId : sessionId }
      };
      return Server.POST(request);
    },
    
    saveSession : function(courseSession) {
      var request = {
        module : this.module, service : this.service, method : 'saveCourseSession',
        params : { courseSession : courseSession }
      };
      return Server.POST(request);
    },
    
    deleteSessionBySessionId : function(sessionId) {
      var request = {
        module : this.module, service : this.service, method : 'deleteSessionBySessionId',
        params : { sessionId : sessionId }
      };
      return Server.POST(request);
    },
    
    findCourseSessionByCode : function(courseCode) {
      var request = {
        module : this.module, service : this.service, method : 'findCourseSessionByCode',
        params : { courseCode : courseCode }
      };
      return Server.POST(request);
    },
    
    findSessionByTeacherLoginId : function(teacherLoginId) {
      var request = {
        module : this.module, service : this.service, method : 'findSessionByTeacherLoginId',
        params : { teacherLoginId : teacherLoginId }
      };
      return Server.POST(request);
    },
    
    findSessionByCourseCode : function(courseCode) {
      var request = {
        module : this.module, service : this.service, method : 'findSessionByCourseCode',
        params : { courseCode : courseCode }
      };
      return Server.POST(request);
    },
    
    saveRegistration : function (registration) {
      var request = {
        module : this.module, service : this.service, method : 'saveRegistration',
        params : { registration : registration }
      };
      return Server.POST(request);
    },
    
    deleteSessionRegistration : function (registration) {
      var request = {
        module : this.module, service : this.service, method : 'deleteSessionRegistration',
        params : { registration : registration }
      };
      return Server.POST(request);
    },
    
    findRegistrationBySessionId : function(sessionId) {
      var request = {
        module : this.module, service : this.service, method : 'findRegistrationBySessionId',
        params : { sessionId : sessionId }
      };
      return Server.POST(request);
    },
    
    findRegistrationByStudentLoginId : function(loginId) {
      var request = {
        module : this.module, service : this.service, method : 'findRegistrationByStudentLoginId',
        params : { loginId : loginId }
      };
      return Server.POST(request);
    },
    
    searchCourseSession : function(query) {
      var request = {
          module : this.module, service : this.service, method : 'searchCourseSession',
          params : { query: query }
        };
        return Server.POST(request);
    },
    
    searchCourseRegistration : function(query) {
      var request = {
          module : this.module, service : this.service, method : 'searchCourseRegistration',
          params : { query: query }
        };
        return Server.POST(request);
    },
    
  };

  return SchoolService ;
});
