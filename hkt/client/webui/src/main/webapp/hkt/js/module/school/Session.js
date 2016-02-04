define([
  'service/service',
  'ui/UIPopup',
  'ui/UIBean',
], function(service, UIPopup, UIBean) {
  
  var UISession= UIBean.extend({
    label: "Course Session",
    config: {
      beans: {
        session: {
          label: 'Course Session',
          fields: [
            { field: "sessionId", label: "Session Id", required: true, validator: {name: "empty"} },
            { field: "courseCode", label: "Course Code", required: true, validator: {name: "empty"} },
            { field: "teacherLoginId", label: "Teacher LoginId", required: true, validator: {name: "empty"},
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
                  var session = thisUI.getBean('session') ;
                  service.SchoolService.saveSession(session) ;
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
    
    init: function (UIParent, courseSession, isNew) {
      this.UIParent = UIParent ;
      this.bind('session', courseSession) ;
      var config = this.getBeanConfig('session') ;
      config.disableEditAction(false) ;
      this.getBeanState('session').editMode = true ;
      var sessionConfig = this.getBeanConfig('session') ;
      
      if (UIParent.course != null) {
        sessionConfig.disableField('sessionId', !isNew) ;
        sessionConfig.disableField('courseCode', true) ;
        sessionConfig.disableField('teacherLoginId', false) ;
        
      } else if(UIParent.teacher != null) {
        sessionConfig.disableField('sessionId', !isNew) ;
        sessionConfig.disableField('teacherLoginId', true) ;
        sessionConfig.disableField('courseCode', false) ;
      }
      return this ;
    },
    
    initViewOnly: function (UIParent, courseSession) {
      this.UIParent = UIParent ;
      this.bind('session', courseSession) ;
      this.getBeanState('session').readOnly = true ;
      return this ;
    }
  });
  
  var Session = {
    UISession : UISession
  };
  
  return Session;

});
