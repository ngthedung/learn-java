define([
  'service/service',
  'ui/UIBean',
  'ui/UIPopup',
], function(service, UIBean, UIPopup) {
  
  var UIWarehouse = UIBean.extend({
    label: 'Warehouse',
    config: {
      beans: {
        warehouse : {
          fields : [
            { field : 'warehouseId', label : 'Warehouse Id', required: true, validator: { name: 'empty' } },
            { field : "name", label : "Name", required: true, validator: { name: "empty" } },
            { field : "ownerId", label : "Owner Id", required: true, validator: { name: "empty" },
              custom: {
                getDisplay: function(bean) {
                  return bean.ownerId == null ? null : bean.ownerId ;
                },
                set: function(bean, obj) { bean.ownerId = obj ; },
                
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
            { field : "ownerGroup", label : "Owner Group",
              custom: {
                getDisplay: function(bean) {
                  return bean.ownerGroup == null ? null : bean.ownerGroup ;
                },
                set: function(bean, obj) { bean.ownerGroup = obj ; },
                
                autocomplete: {
                  search: function(val, bean) {
                    var accountGroups = service.AccountService.findGroupByParentPathAndName(bean.ownerId,val).data ;
                    var result = [] ;
                    for(var i = 0; i < accountGroups.length; i++) {
                      var accGroup = accountGroups[i] ;
                      result[i] = {value: accGroup.path, label: accGroup.name + '(' + accGroup.path + ')'} ;
                    }
                    return result ;
                  }
                }
              }
            },
            { field : "address", label : "Address", },
            { field : "description", label : "Descripton", textarea: {} }
          ],
          edit: {
            disable: true , 
            actions: [
              {
                action:'save', label: "Save", icon: "check", 
                onClick: function(thisUI) {
                  var warehouse = thisUI.getBean('warehouse');
                  service.WarehouseService.saveWarehouse(warehouse) ;
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
              },
            ],
          },
          view: {
            disable: false,
            actions: [
              {
                action:'cancel', label: "Cancel", icon: "back",
                onClick: function() {
                  UIPopup.closePopup() ;
                }
              },
            ]
          }
        }
      }
    },
    init: function(UIParent, warehouse, isNew) {
      this.UIParent = UIParent ;
      this.bind('warehouse', warehouse) ;
      var warehouseConfig = this.getBeanConfig('warehouse') ;
      warehouseConfig.disableEditAction(false) ;
      
      this.getBeanState('warehouse').editMode = true ;
      warehouseConfig.disableField('warehouseId', !isNew) ;
      return this ;
    },
    
    initViewOnly: function(UIParent, warehouse) {
      this.UIParent = UIParent ;
      this.bind('warehouse', warehouse) ;
      this.getBeanState('warehouse').readOnly = true ;
      return this ;
    },
    
  });
  
  var Warehouse = {
    UIWarehouse: UIWarehouse
  };
  
  return Warehouse ;
});
