define([
  'service/service',
  'ui/UITable',
  'ui/UIBean',
  'ui/UIPopup',
  'module/school/Student',
  'module/school/Session',
], function(service, UITable, UIBean, UIPopup, Student, Session) {
  
  var UICourseSessionRegistration= UIBean.extend({
    label: "Course Session Registration",
    config: {
      beans: {
        registration: {
          label: 'Course Session Registration',
          fields: [
            { field: "courseSessionId", label: "Course SessionId", required: true, validator: {name: "empty"} },
            { field: "studentLoginId", label: "Student LoginId", required: true, validator: {name: "empty"} },
            {
              field: 'status',  label: 'Status' ,
              option: [
                { label: 'Registered', value: "REGISTERED" },
                { label: 'Accepted', value: "ACCEPTED" },
                { label: 'Dropped', value: "DROPPED" }
              ]
            }
          ],
          edit: {
            disable: true , 
            actions: [
              {
                action:'save', label: "Save", icon: "check",
                onClick: function(thisUI) { 
                  if(!thisUI.validate()) {
                    thisUI.render() ;
                    return ;
                  }
                  var registration = thisUI.getBean('registration') ;
                  service.SchoolService.saveRegistration(registration) ;
                  if(thisUI.UIParent.onRefresh != null) {
                    thisUI.UIParent.onRefresh() ;
                  }
                  UIPopup.closePopup() ;
                }
              },
              {
                action:'cancel', label: "Cancel", icon: "back",
                onClick: function() { 
                  UIPopup.closePopup() ;
                }
              }
            ],
          }
        }
      }
    },
    
    init: function(UIParent, registration, isNew) {
      this.UIParent = UIParent ;
      this.bind('registration', registration) ;
      var config = this.getBeanConfig('registration') ;
      config.disableEditAction(false) ;
      this.getBeanState('registration').editMode = true ;
      var registrationConfig = this.getBeanConfig('registration') ;
      
      if (registration.courseSessionId.length != 0) {
        registrationConfig.disableField('courseSessionId', true) ;
        registrationConfig.disableField('studentLoginId', !isNew) ;
        
      } else if (registration.studentLoginId.length != 0) {
        registrationConfig.disableField('studentLoginId', true) ;
        registrationConfig.disableField('courseSessionId', !isNew) ;
      }
      
      return this ;
    },
  });
  
  var UICourseSessionRegistrationList = UITable.extend({
    label: "Registrations Information",
      config : {
        toolbar: {
          dflt: {
            actions: [
              {
                action: "onNewRegistration", icon: "add", label: "New", 
                onClick: function(thisUI) {
                  var registration = {
                    studentLoginId: "", courseSessionId: "", status: "REGISTERED"
                  } ;
                  if (thisUI.student != null) {
                    registration.studentLoginId = thisUI.student.loginId ;
                  }
                  if (thisUI.session != null) {
                    registration.courseSessionId = thisUI.session.sessionId ;
                  }
                  UIPopup.activate(new UICourseSessionRegistration().init(thisUI, registration, true)) ;
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
              { label: 'Search By Student Id', field: 'studentLoginId', type: 'string', operator: 'LIKE' },
              { label: 'Search By Session Id', field: 'courseSessionId', type: 'string', operator: 'LIKE' },
              { label: 'Search By Status', field: 'status', type: 'string', operator: 'EQUAL' }
            ],
            onFilter: function(thisUI, query) {
              var result = service.SchoolService.searchCourseRegistration(query).data ;
              var registrations = result.data ;
              thisUI.setBeans(registrations) ;
              thisUI.renderRows() ;
            }
          }
        },
      bean : {
        fields: [
          { label: 'Id', field: "id", toggled: true, filterable: true, },
          {
            label: 'Session Id', field: "courseSessionId", toggled: true, filterable: true,
            onClick: function(thisUI, row) {
              var registration = thisUI.getItemOnCurrentPage(row) ;
              var courseSession = service.SchoolService.getSessionBySessionId(registration.courseSessionId).data ;
              UIPopup.activate(new Session.UISession().initViewOnly(thisUI, courseSession)) ;
            }
          },
          {
            label: 'Student Id', field: "studentLoginId", toggled: true, filterable: true,
            onClick: function(thisUI, row) {
              var registration = thisUI.getItemOnCurrentPage(row) ;
              var student = service.SchoolService.getStudentByLoginId(registration.studentLoginId).data ;
              UIPopup.activate(new Student.UIStudent().initViewOnly(thisUI, student)) ;
            }
          },
          { label: 'Status', field: "status", toggled: true, filterable: true },
        ],
        actions: [
          {
            icon: 'edit', label: 'Edit Registration',
            onClick: function (thisUI, row) {
              var registration = thisUI.getItemOnCurrentPage(row) ;
              UIPopup.activate(new UICourseSessionRegistration().init(thisUI, registration, false)) ;
            }
          },
          {
            icon: "delete", label: "Delete Registration",
            onClick: function(thisUI, row) {
              var registration = thisUI.getItemOnCurrentPage(row) ;
              var deleted = service.SchoolService.deleteSessionRegistration(registration).data ;
              if(deleted) {
                thisUI.removeItemOnCurrentPage(row) ;
              }
            }
          }
        ]
      }
    },
    
    init: function (UIParent, session, student) {
      this.UIParent = UIParent ;
      this.viewStack = UIParent.viewStack ;
      this.session = session ;
      this.student = student ;
      
      var registrations = null ;
      if (session != null) {
        registrations = service.SchoolService.findRegistrationBySessionId(session.sessionId).data ;
      }
      if (student != null) {
        registrations = service.SchoolService.findRegistrationByStudentLoginId(student.loginId).data ;
      }
      this.setBeans(registrations) ;
      return this ;
    },
    
    onRefresh: function() {
      if (this.session != null) {
        this.registrations = service.SchoolService.findRegistrationBySessionId(this.session.sessionId).data ;
      }
      if (this.student != null) {
        this.registrations = service.SchoolService.findRegistrationByStudentLoginId(this.student.loginId).data ;
      }
      this.setBeans(this.registrations) ;
      this.renderRows() ;
    },
  });
  
  var Registrations = {
    UICourseSessionRegistrationList : UICourseSessionRegistrationList ,
    UICourseSessionRegistration : UICourseSessionRegistration
  };
  
  return Registrations;
});
