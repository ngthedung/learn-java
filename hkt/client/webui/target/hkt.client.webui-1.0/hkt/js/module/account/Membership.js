define([
  'service/service',
  'ui/UIPopup',
  'ui/UICollapsible',
  'ui/UIBean',
], function(service, UIPopup, UICollapsible, UIBean) {  
  var UIMembership = UIBean.extend({
    label: "Membership",
    config: {
      beans: {
        membership: {
          label: 'Membership',
          fields: [
            { 
              field: "loginId", label: "Login Id", required: true, validator: {name: "empty"},
              custom: {
                getDisplay: function(bean) {
                  return bean.loginId == null ? null : bean.loginId;
                },
                set: function(bean, obj) { bean.loginId = obj ; },
                
                autocomplete: {
                  search: function(val) {
                    var accounts = service.AccountService.findAccountByLoginId(val).data ;
                    var result = [] ;
                    for(var i = 0; i < accounts.length; i++) {
                      var account = accounts[i] ;
                      result[i] = { value: account.loginId, 
                        label: account.loginId + 
                        ' ( ' + account.profiles.basic.lastName  +' ' + account.profiles.basic.firstName + ' )'
                      };
                    }
                    return result ;
                  }
                }
              }
            },
            {
              field:  "capability", label: "Capability", defaultValue: 'READ',
              select: {
                getOptions: function(field, bean) {
                  var options = [
                    { label: 'READ', value: 'READ' },
                    { label: 'WRITE', value: 'WRITE' },
                    { label: 'ADMIN', value: 'ADMIN' }
                  ];
                  return options ;
                }
              }
            },
            {
              field:  "status", label: "Status", defaultValue: 'ACTIVE',
              select: {
                getOptions: function(field, bean) {
                  var options = [
                    { label: 'ACTIVE', value: 'ACTIVE' },
                    { label: 'INACTIVE', value: 'INACTIVE' },
                    { label: 'SUSPENDED', value: 'SUSPENDED' }
                  ];
                  return options ;
                }
              }
            },
            { field: "role",  label: "Role", required: true, validator: {name: "empty"},
              select: {
                getOptions: function(field, bean) {
                  var optionGroup = service.GenericOptionService.getOptionGroup(
                      'hr', 'HRService', 'work-position').data ;
                  var options = optionGroup.options;
                  var result = [] ;
                  for(var i = 0; i < options.length; i++) {
                    var option = options[i] ;
                    result[i] = { label: option.label , value: option.code } ;
                  }
                  return result ;
                }
              }
            },
            { field: "groupPath",  label: "GroupPath", required: true, validator: {name: "empty"} }
          ],
          edit: {
            disable: true , 
            actions: [
              {
                action:'save', label: "Save", icon: "check",
                onClick: function(thisUI, beanConfig, beanState) { 
                  if(!thisUI.validate()) {
                    thisUI.render() ;
                    return ;
                  }
                  var membership = thisUI.getBean('membership') ;
                  membership = service.AccountService.saveAccountMembership(membership).data ;
                  if(thisUI.UIParent.onRefresh != null) {
                    thisUI.UIParent.onRefresh(membership.groupPath) ;
                  }
                  UIPopup.closePopup() ;
                }
              },
              {
                action:'cancel', label: "Cancel", icon: "back",
                onClick: function(thisUI, beanConfig, beanState) { 
                  UIPopup.closePopup() ;
                }
              }
            ],
          }
        }
      }
    },
    
    init: function(UIParent, membership, isNew) {
      this.UIParent = UIParent ;
      this.bind('membership', membership, true) ;
      
      var config = this.getBeanConfig('membership') ;
      config.disableEditAction(false) ;
      
      this.getBeanState('membership').editMode = true ;
      config.disableField('groupPath', true) ;
      config.disableField('loginId', !isNew) ;
      return this ;
    },
    
  });
  
  var Membership = {
    UIMembership : UIMembership,
  };
  
  return Membership ;
});
