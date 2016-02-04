define([
  'service/service',
  'ui/UIPopup',
  'ui/UIBean',
], function(service, UIPopup, UIBean) {
  
  var UICourse= UIBean.extend({
    label: "Course",
    config: {
      beans: {
        course: {
          label: 'Course',
          fields: [
            { field: "code", label: "Code", required: true, validator: {name: "empty"} },
            { field: "name", label: "Name", required: true, validator: {name: "empty"} },
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
                  var course = thisUI.getBean('course') ;
                  service.SchoolService.saveCourse(course) ;
                  
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
    
    init: function (UIParent, course, isNew) {
      this.UIParent = UIParent ;
      this.bind('course', course) ;
      var courseConfig = this.getBeanConfig('course') ;
      courseConfig.disableEditAction(false) ;
      this.getBeanState('course').editMode = true ;
      
      courseConfig.disableField('code', !isNew) ;
      return this ;
    },
    
    initViewOnly: function (UIParent, course) {
      this.UIParent = UIParent ;
      this.bind('course', course) ;
      this.getBeanState('course').readOnly = true ;
      return this ;
    }
  });
  
  var Course = {
    UICourse : UICourse
  };
  
  return Course;

});
