define([
  'service/service',
  'module/Module',
  'ui/UITable',
  'ui/UIPopup',
  'ui/UIBreadcumbs',
  'ui/UICollapsible',
  'module/school/Sessions',
  'module/school/Course',
], function(service, module, UITable, UIPopup, UIBreadcumbs, UICollapsible, Sessions, Course) {
  
  var UICourseSession = UICollapsible.extend({
    label: "Course Sessions", 
    config: {
      actions: [
        {
          action: 'back', label: 'Back',
          onClick: function (thisUI) {
            if (thisUI.UIParent.back) { thisUI.UIParent.back(false) ; }
          }
        },
      ]
    },
    
    init: function (UIParent, course) {
      this.UIParent = UIParent ;
      this.clear() ;
      this.add(new Course.UICourse().initViewOnly(UIParent, course), true) ;
      
      var UICourseSessionList = new Sessions.UICourseSessionList().init(UIParent, course, null) ;
      this.add(UICourseSessionList) ;
    }
  });
  
  var UICourseList = UITable.extend({
    label: "Courses",
      config : {
        toolbar: {
          dflt: {
            actions: [
              {
                action: "onNewCourse", icon: "add", label: "New", 
                onClick: function(thisUI) {
                  var course = {
                    code: "", name: ""
                  };
                  UIPopup.activate(new Course.UICourse().init(thisUI, course, true)) ;
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
            label: 'Code', field: "code", toggled: true, filterable: true,
            onClick: function(thisUI, row) {
              var course = thisUI.getItemOnCurrentPage(row) ;
              UIPopup.activate(new Course.UICourse().init(thisUI, course, false)) ;
            }
          },
          { label: 'Name', field: "name", toggled: true, filterable: true },
        ],
        actions: [
          {
            icon: "bars", label: "Course Sessions",
            onClick: function(thisUI, row) {
              var course = thisUI.getItemOnCurrentPage(row) ;
              thisUI.UICourseSession.init(thisUI, course) ;
              thisUI.viewStack.push(thisUI.UICourseSession) ;
            }
          },
          {
            icon: "delete", label: "Delete",
            onClick: function(thisUI, row) {
              var course = thisUI.getItemOnCurrentPage(row) ;
              var deleted = service.SchoolService.deleteCourseByCode(course.code).data ;
              if(deleted) {
                thisUI.removeItemOnCurrentPage(row) ;
              }
            }
          }
        ]
      }
    },
    
    onRefresh: function() {
      var courses = service.SchoolService.getAllCourse().data ;
      this.setBeans(courses) ;
      this.renderRows() ;
    },
    
    back: function(refresh) {
      if (refresh) { this.onRefresh() ; }
      this.viewStack.back();
    },
    
    init: function (viewStack) {
      this.viewStack = viewStack ;
      this.UICourseSession = new UICourseSession() ;
      var courses = service.SchoolService.getAllCourse().data ;
      this.setBeans(courses) ;
      return this ;
    }
    
  });
  
  var UICoursesScreen = module.UIScreen.extend({
    
    initialize: function (options) { },
    
    activate: function() {
      this.viewStack = new UIBreadcumbs({el: "#workspace"}) ;
      var aview = new UICourseList().init(this.viewStack) ;
      this.viewStack.push(aview) ;
    },
    
    deactivate: function() { },
    
  });
  
  return UICoursesScreen ;
});
