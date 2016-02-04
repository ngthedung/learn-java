define([
  'service/service',
  'ui/UITable',
  'ui/UIPopup',
  'ui/UICollapsible',
  'module/school/Course',
  'module/school/Registrations',
  'module/school/Teacher',
  'module/school/Session',
], function(service, UITable, UIPopup, UICollapsible, Course, Registrations, Teacher, Session) {

  var UIRegistrations = UICollapsible.extend({
    label: "Course Session Registrations", 
    config: {
      actions: [
        {
          action: 'back', label: 'Back',
          onClick: function (thisUI) {
            if (thisUI.UIParent.back) {
              thisUI.UIParent.back(false) ;
            }
          }
        },
      ]
    },
    
    init: function (UIParent, session) {
      this.UIParent = UIParent ;
      this.clear() ;
      if (UIParent.course != null) {
        this.add(new Course.UICourse().initViewOnly(UIParent, UIParent.course), true) ;
      }
      if (UIParent.teacher != null) {
        this.add(new Teacher.UITeacher().initViewOnly(UIParent, UIParent.teacher), true) ;
      }
      
      this.add(new Session.UISession().initViewOnly(UIParent, session), true) ;
      
      var UICourseSessionRegistrationList = new Registrations.UICourseSessionRegistrationList().init(
          UIParent, session, null) ;
      this.add(UICourseSessionRegistrationList) ;
    }
  });
  
  var UICourseSessionList = UITable.extend({
    label: "Course Session Information",
      config : {
        toolbar: {
          dflt: {
            actions: [
              {
                action: "onNewCourseSession", icon: "add", label: "New", 
                onClick: function(thisUI) {
                  var courseSession = {
                    sessionId: "", courseCode: "", teacherLoginId: ""
                  };
                  
                  if (thisUI.course != null) {
                    courseSession.courseCode = thisUI.course.code ;
                  }
                  
                  if (thisUI.teacher != null) {
                    courseSession.teacherLoginId = thisUI.teacher.loginId ;
                  }
                  
                  UIPopup.activate(new Session.UISession().init(thisUI, courseSession, true)) ;
                } 
              },
              {
                action: "onRefresh", icon: "refresh", label: "Refresh",
                onClick: function(thisUI) { 
                  thisUI.onRefresh() ;
                }
              }
            ],
          },
          filter: {
            fields: [
              { label: 'Search By Teacher Id',  field: 'teacherLoginId', type: 'string', operator: 'LIKE'},
              { label: 'Search By Session Id',  field: 'sessionId', type: 'string', operator: 'LIKE'},
              { label: 'Search By Course Code',  field: 'courseCode', type: 'string', operator: 'LIKE'}
            ],
            onFilter: function(thisUI, query) {
              var result = service.SchoolService.searchCourseSession(query).data ;
              var sessions = result.data ;
              thisUI.setBeans(sessions) ;
              thisUI.renderRows() ;
            },
          },
        },
      bean : {
        fields: [
          {
            label: 'Session Id', field: "sessionId", toggled: true, filterable: true,
            onClick: function(thisUI, row) {
              var courseSession = thisUI.getItemOnCurrentPage(row) ;
              UIPopup.activate(new Session.UISession().init(thisUI, courseSession, false)) ;
            }
          },
          {
            label: 'Course Code', field: "courseCode", toggled: true, filterable: true,
            onClick: function(thisUI, row) {
              var courseSession = thisUI.getItemOnCurrentPage(row) ;
              var course = service.SchoolService.getCourseByCode(courseSession.courseCode).data ;
              UIPopup.activate(new Course.UICourse().initViewOnly(thisUI, course)) ;
            }
          },
          {
            label: 'Teacher Id', field: "teacherLoginId", toggled: true, filterable: true,
            onClick: function(thisUI, row) {
              var courseSession = thisUI.getItemOnCurrentPage(row) ;
              var teacher = service.SchoolService.getTeacherByLoginId(courseSession.teacherLoginId).data ;
              UIPopup.activate(new Teacher.UITeacher().initViewOnly(thisUI, teacher)) ;
            }
          },
        ],
        actions: [
          {
            icon: "bars", label: "Registrations",
            onClick: function(thisUI, row) {
              var session = thisUI.getItemOnCurrentPage(row) ;
              thisUI.UIRegistrations.init(thisUI, session) ;
              thisUI.viewStack.push(thisUI.UIRegistrations) ;
            }
          },
          {
            icon: "delete", label: "Delete Session",
            onClick: function(thisUI, row) {
              var courseSession = thisUI.getItemOnCurrentPage(row) ;
              var deleted = service.SchoolService.deleteSessionBySessionId(courseSession.sessionId).data ;
              if(deleted) {
                thisUI.removeItemOnCurrentPage(row) ;
              }
            }
          }
        ]
      }
    },
    
    init: function (UIParent, course, teacher) {
      this.UIParent = UIParent ;
      this.viewStack = UIParent.viewStack ;
      this.course = course ;
      this.teacher = teacher ;
      this.UIRegistrations = new UIRegistrations() ;
      
      var sessions = null ;
      if (course != null) {
        sessions = service.SchoolService.findSessionByCourseCode(course.code).data ;
      }
      if (teacher != null) {
        sessions = service.SchoolService.findSessionByTeacherLoginId(teacher.loginId).data ;
      }
      this.setBeans(sessions) ;
      return this ;
    },
    
    onRefresh: function() {
      if (this.course != null) {
        this.sessions = service.SchoolService.findSessionByCourseCode(this.course.code).data ;
      }
      if (this.teacher != null) {
        this.sessions = service.SchoolService.findSessionByTeacherLoginId(this.teacher.loginId).data ;
      }
      this.setBeans(this.sessions) ;
      this.renderRows() ;
    },
    
    back: function(refresh) {
      if (refresh) { this.onRefresh() ; }
      this.viewStack.back();
    },
    
  });
  
  var Sessions = {
    UICourseSessionList : UICourseSessionList,
  };
  
  return Sessions;
});
