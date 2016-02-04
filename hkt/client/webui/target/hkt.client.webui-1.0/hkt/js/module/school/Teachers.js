define([
  'service/service',
  'module/Module',
  'ui/UITable',
  'ui/UIPopup',
  'ui/UIBreadcumbs',
  'ui/UICollapsible',
  'module/school/Sessions',
  'module/school/Teacher'
  ], function(service, module, UITable, UIPopup, UIBreadcumbs, UICollapsible, Sessions, Teacher) {
  
  var UICourseSession = UICollapsible.extend({
    label: "Course Sessions", 
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
    
    init: function (UIParent, teacher) {
      this.UIParent = UIParent ;
      this.clear() ;
      this.add(new Teacher.UITeacher().initViewOnly(UIParent, teacher), true) ;
      
      var UICourseSessionList = new Sessions.UICourseSessionList().init(UIParent, null, teacher) ;
      this.add(UICourseSessionList) ;
    }
  });
  
  var UITeacherList = UITable.extend({
    label: "Teachers",
      config : {
        toolbar: {
          dflt: {
            actions: [
              {
                action: "onNewTeacher", icon: "add", label: "New", 
                onClick: function(thisUI) {
                  var teacher = {
                    loginId: "", firstName: "", lastName: ""
                  } ;
                  UIPopup.activate(new Teacher.UITeacher().init(thisUI, teacher, true)) ;
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
        },
      bean : {
        fields: [
          {
            label: 'Login Id', field: 'loginId', toggled: true, filterable: true,
            onClick: function(thisUI, row) {
              var teacher = thisUI.getItemOnCurrentPage(row) ;
              UIPopup.activate(new Teacher.UITeacher().init(thisUI, teacher, false)) ;
            }
          },
          { label: 'First Name', field: 'firstName', toggled: true, filterable: true },
          { label: 'Last Name', field: 'lastName', toggled: true, filterable: true },
        ],
        actions: [
          {
            icon: "bars", label: "Course Session",
            onClick: function(thisUI, row) {
              var teacher = thisUI.getItemOnCurrentPage(row) ;
              thisUI.UICourseSession.init(thisUI, teacher) ;
              thisUI.viewStack.push(thisUI.UICourseSession) ;
            }
          },
          {
            icon: "delete", label: "Delete",
            onClick: function(thisUI, row) {
              var teacher = thisUI.getItemOnCurrentPage(row) ;
              var deleted = service.SchoolService.deleteTeacherByLoginId(teacher.loginId).data ;
              if(deleted) {
                thisUI.removeItemOnCurrentPage(row) ;
              }
            }
          }
        ]
      }
    },
    
    onRefresh: function() {
      var teachers = service.SchoolService.getAllTeacher().data ;
      this.setBeans(teachers) ;
      this.renderRows() ;
    },
    
    back: function(refresh) {
      if (refresh) {
        this.onRefresh();
      }
      this.viewStack.back();
    },
    
    init: function (viewStack) {
      this.viewStack = viewStack ;
      this.UICourseSession = new UICourseSession() ;
      var teachers = service.SchoolService.getAllTeacher().data ;
      this.setBeans(teachers) ;
      return this ;
    }
    
  });
    
  var UITeachersScreen = module.UIScreen.extend({
    initialize: function (options) { },
    activate: function() { 
      this.viewStack = new UIBreadcumbs({el: "#workspace"}) ;
      var aview = new UITeacherList().init(this.viewStack) ;
      this.viewStack.push(aview) ;
    },
    deactivate: function() { }
  });
  
  return UITeachersScreen ;
});
