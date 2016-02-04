define([
  'service/service',
  'ui/UIBean',
  'ui/UIPopup',
], function( service, UIBean, UIPopup) {
  
  var UIGroup = UIBean.extend({
    label: "Group",
    config: {
      beans: {
        group: {
          label: 'Group',
          fields : [
            { field: "path", label: "Path" },
            { field: "name", label: "Name", required: true, validator: {name: "empty" } },
            { field: "owner", label: "Owner" ,
              custom: {
                getDisplay: function(bean) { return bean.owner == null ? null : bean.owner; },
                set: function(bean, obj) { bean.owner = obj ; },
                autocomplete: {
                  search: function(val) {
                    var accounts = service.AccountService.findAccountByLoginIdOrg(val).data ;
                    var result = [] ;
                    for(var i = 0; i < accounts.length; i++) {
                      var account = accounts[i] ;
                      result[i] = { value: account.loginId, label: account.loginId + '(' + account.email + ')'} ;
                    }
                    return result ;
                  }
                }
              }
            },
            { field: "label", label: "Label" },
            { field: "description", label: "Description", textarea: {} },
          ],
          edit: {
            disable: true,
            actions:[
              { 
                action:'save', label: "Save", icon: "check", 
                onClick: function(thisUI) {
                  if(!thisUI.validate()) {
                    thisUI.render() ;
                    return ;
                  }
                  var group = thisUI.getBean('group') ;
                  if(thisUI.isNew) {
                    service.AccountService.createGroup(thisUI.parentPath, group) ;
                  } else {
                    service.AccountService.saveGroup(group) ;
                  }
                  thisUI.Groups.onRefresh() ;
                  UIPopup.closePopup() ;
                } 
              },
              { 
                action:'cancel', label: "Cancel", icon: "back",
                onClick: function() { 
                  UIPopup.closePopup() ; 
                }
              }
            ]
          }
        }
      }
    },
    
    init: function(Groups, parentPath, group, isNew) {
      this.Groups = Groups ;
      this.parentPath = parentPath;
      this.isNew = isNew ;
      this.bind('group', group) ;
      var groupConfig = this.getBeanConfig('group') ;
      groupConfig.disableEditAction(false) ;
      
      this.getBeanState('group').editMode = true ;
      groupConfig.disableField('path', true) ;
      groupConfig.disableField('name', !isNew) ;
      return this ;
    },
  });
  
 var Group = {
     UIGroup: UIGroup
 };
  
  return Group ;
});
