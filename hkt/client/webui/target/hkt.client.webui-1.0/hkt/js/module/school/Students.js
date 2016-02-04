define([
  'service/service',
  'module/Module',
  'ui/UITable',
  'ui/UIPopup',
  'ui/UIBreadcumbs',
  'ui/UICollapsible',
  'module/school/Registrations',
  'module/school/Student',
], function(service, module, UITable, UIPopup, UIBreadcumbs, UICollapsible, Registrations, Student) {
  
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
    
    init: function (UIParent, student) {
      this.UIParent = UIParent ;
      this.clear() ;
      this.add(new Student.UIStudent().initViewOnly(UIParent, student), true) ;
      var UICourseSessionRegistrationList = new Registrations.UICourseSessionRegistrationList().init(
          UIParent, null, student) ;
      this.add(UICourseSessionRegistrationList) ;
    }
  });
  
  var UIStudentList = UITable.extend({
    label: "Students",
      config : {
        toolbar: {
          dflt: {
            actions: [
              {
                action: "onNewStudent", icon: "add", label: "New", 
                onClick: function(thisUI) {
                  var student = {
                    loginId: "", firstName: "", lastName: ""
                  };
                  UIPopup.activate(new Student.UIStudent().init(thisUI, student, true)) ;
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
              { label: 'Search By Login Id', field: 'loginId', type: 'string', operator: 'LIKE' },
              { label: 'Search By First Name', field: 'firstName', type: 'string', operator: 'LIKE' },
              { label: 'Search By Last Name', field: 'lastName', type: 'string', operator: 'LIKE' },
            ],
            onFilter: function(thisUI, query) {
              var result = service.SchoolService.searchStudent(query).data ;
              var students = result.data ;
              thisUI.setBeans(students) ;
              thisUI.renderRows() ;
            },
          }
        },
      bean : {
        fields: [
          {
            label: 'Login Id', field: 'loginId', toggled: true, filterable: true,
            onClick: function(thisUI, row) {
              var student = thisUI.getItemOnCurrentPage(row) ;
              UIPopup.activate(new Student.UIStudent().init(thisUI, student, false)) ;
            }
          },
          { label: 'First Name', field: 'firstName', toggled: true, filterable: true },
          { label: 'Last Name', field: 'lastName', toggled: true, filterable: true },
        ],
        actions: [
          {
            icon: "bars", label: "Registration Session",
            onClick: function(thisUI, row) { 
              var student = thisUI.getItemOnCurrentPage(row) ;
              thisUI.UIRegistrations.init(thisUI, student) ;
              thisUI.viewStack.push(thisUI.UIRegistrations) ;
            }
          },
          {
            icon: "delete", label: "Delete",
            onClick: function(thisUI, row) {
              var student = thisUI.getItemOnCurrentPage(row) ;
              var deleted =  service.SchoolService.deleteStudentByLoginId(student.loginId).data ;
              if(deleted) {
                thisUI.removeItemOnCurrentPage(row) ;
              }
            }
          }
        ]
      }
    },
    
    onRefresh: function() {
      var result = service.SchoolService.searchStudent(null).data ;
      var students = result.data ;
      this.setBeans(students) ;
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
      this.UIRegistrations = new UIRegistrations() ;
      var result = service.SchoolService.searchStudent(null).data ;
      var students = result.data ;
      this.setBeans(students) ;
      return this ;
    }
    
  });
  
  
    
  var UIStudentsScreen = module.UIScreen.extend({
    initialize: function (options) { },
    
    activate: function() { 
      this.viewStack = new UIBreadcumbs({el: "#workspace"}) ;
      var aview = new UIStudentList().init(this.viewStack);
      this.viewStack.push(aview) ;
    },
    
    deactivate: function() { }
    
  });
  
  return UIStudentsScreen ;
});
