define([
  'service/service',
  'ui/UIBean',
  'ui/UIPopup',
], function(service, UIBean, UIPopup) {  
  var UICustomer = UIBean.extend({
    label: "Customer",
    config: {
      beans: {
        customer: {
          label: 'Customer',
          fields: [
            { field: "loginId", label: "Login Id", required: true, validator: {name: "empty"},
              custom: {
                getDisplay: function(bean) {
                  return bean.loginId == null ? null : bean.loginId;
                },
                set: function(bean, obj) { bean.loginId = obj ; },
                autocomplete: {
                  search: function(val) {
                    var accounts = service.AccountService.findAccountByLoginIdUser(val).data ;
                    var result = [] ;
                    for(var i = 0; i < accounts.length; i++) {
                      var account = accounts[i] ;
                      result[i] = {
                        value: account.loginId, 
                        label: account.loginId + 
                        ' ( ' + account.profiles.basic.lastName  +' ' + account.profiles.basic.firstName + ' )'
                      };
                    }
                    return result ;
                  }
                }
              }
            },
            { field: "organizationLoginId", label: "Organization LoginId", required: true, validator: {name: "empty"},
              custom: {
                getDisplay: function(bean) {
                  return bean.organizationLoginId == null ? null : bean.organizationLoginId;
                },
                
                set: function(bean, obj) { bean.organizationLoginId = obj ; },
                
                autocomplete: {
                  search: function(val) {
                    var accounts = service.AccountService.findAccountByLoginIdOrg(val).data ;
                    var result = [] ;
                    for(var i = 0; i < accounts.length; i++) {
                      var account = accounts[i] ;
                      result[i] = {value: account.loginId, label: account.loginId + '(' + account.email + ')'} ;
                    }
                    return result ;
                  }
                }
              }
            },
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
                  var customer = thisUI.getBean('customer') ;
                  service.CustomerService.saveCustomer(customer) ;
                  if(thisUI.UIParent.onRefresh != null) {
                    thisUI.UIParent.onRefresh() ;
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
    
    init: function(UIParent, customer, isNew) {
      this.UIParent = UIParent;
      this.bind('customer', customer) ;
      var config = this.getBeanConfig('customer') ;
      config.disableEditAction(false) ;
      if(isNew){ 
        this.getBeanState('customer').editMode = true ; 
      }
      return this ;
    },
  });
  
  var Customer = {
    UICustomer:  UICustomer
  };
      
  return Customer ;
});
