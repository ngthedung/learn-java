define([
  'service/service',
  'ui/UIPopup',
  'ui/UIBean',
], function(service, UIPopup, UIBean) {
  
  var UIStudent= UIBean.extend({
    label: "Student",
    config: {
      beans: {
        student: {
          label: 'Student',
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
                onClick: function (thisUI) { 
                  if (!thisUI.validate()) {
                    thisUI.render() ;
                    return ;
                  }
                  var student = thisUI.getBean('student') ;
                  service.SchoolService.saveStudent(student) ;
                  if (thisUI.UIParent.onRefresh != null) {
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
    
    init: function (UIParent, student, isNew) {
      this.UIParent = UIParent ;
      this.bind('student', student) ;
      
      var studentConfig = this.getBeanConfig('student') ;
      studentConfig.disableEditAction(false) ;
      this.getBeanState('student').editMode = true ;
      
      studentConfig.disableField('loginId', !isNew) ;
      return this ;
    },
    
    initViewOnly: function (UIParent, student) {
      this.UIParent = UIParent ;
      this.bind('student', student) ;
      this.getBeanState('student').readOnly = true ;
      return this ;
    }
  });
  
  var Student = {
    UIStudent : UIStudent
  };
  
  return Student;
});
