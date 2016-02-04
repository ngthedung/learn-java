define([
  'service/service',
  'ui/UIPopup',
  'ui/UIBean',
  ], function(service, UIPopup, UIBean) {
  
  var UITeacher= UIBean.extend({
    label: "Teacher",
    config: {
      beans: {
        teacher: {
          label: 'Teacher',
          fields: [
            { field: "loginId", label: "Login Id", required: true, validator: {name: "empty"},
              autocomplete: {
                search: function(val) {
                  var accounts = service.AccountService.findAccountByLoginId(val).data ;
                  var result = [] ;
                  for(var i = 0; i < accounts.length; i++) {
                    var account = accounts[i] ;
                    result[i] = {value: account.loginId, label: account.loginId + '(' + account.email + ')'} ;
                  }
                  return result ;
                }
              }
            },
            { field: "firstName", label: "First Name", required: true, validator: {name: "empty"} },
            { field: "lastName",  label: "Last Name", required: true, validator: {name: "empty"} }
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
                  var teacher = thisUI.getBean('teacher') ;
                  service.SchoolService.saveTeacher(teacher) ;
                  
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
    
    init: function (UIParent, teacher, isNew) {
      this.UIParent = UIParent ;
      this.bind('teacher', teacher) ;
      var teacherConfig = this.getBeanConfig('teacher') ;
      teacherConfig.disableEditAction(false) ;
      
      this.getBeanState('teacher').editMode = true ;
      teacherConfig.disableField('loginId', !isNew) ;
      
      return this ;
    },
    
    initViewOnly: function (UIParent, teacher) {
      this.UIParent = UIParent ;
      this.bind('teacher', teacher) ;
      this.getBeanState('teacher').readOnly = true ;
      return this ;
    }
  });
  
  var Teacher = {
    UITeacher : UITeacher
  };
  
  return Teacher ;

});
