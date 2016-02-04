define([
  'service/service',
  'ui/UIBean',
  'ui/UIPopup'
], function(service, UIBean, UIPopup) {
  var UISupplier = UIBean.extend({
    label: "Supplier",
    config: {
      beans: {
        supplier: {
          label: 'Supplier',
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
                      } ;
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
                  var supplier = thisUI.getBean('supplier') ;
                  service.SupplierService.saveSupplier(supplier) ;
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
    
    init: function(UIParent, supplier, isNew) {
      this.UIParent = UIParent;
      this.bind('supplier', supplier) ;
      var config = this.getBeanConfig('supplier') ;
      config.disableEditAction(false) ;
      if(isNew){ 
        this.getBeanState('supplier').editMode = true ; 
      }
      return this ;
    },
  });
  
  var Supplier = {
      UISupplier:  UISupplier
    };
  
  return Supplier ;
});
