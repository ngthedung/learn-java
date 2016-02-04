define([
  'service/service',
  'ui/UIBean',
  'ui/UIPopup',
], function(service, UIBean, UIPopup) {
  
  var UIInventory = UIBean.extend({
    label: 'Inventory',
    config: {
      beans: {
        inventory : {
          fields : [
            { 
              field : "warehouseCode", label : "Warehouse Code", required: true, validator: {name: "empty"},
              custom: {
                getDisplay: function(bean) {
                  return bean.warehouseCode == null ? null : bean.warehouseCode ;
                },
                set: function(bean, obj) { bean.product = obj ; },
                
                autocomplete: {
                  search: function(val) {
                    var warehouses = service.WarehouseService.findWarehouseByWarehouseId(val).data ;
                    var result = [] ;
                    for (var i = 0; i < warehouses.length; i++) {
                      var warehouse = warehouses[i] ;
                      result[i] = { value: warehouse.warehouseId, label: warehouse.warehouseId } ;
                    }
                    return result ;
                  }
                }
              }
            },
            { 
              field : "product", label : "Product", required: true, validator: {name: "empty"},
              custom: {
                getDisplay: function(bean) {
                  return bean.product == null ? null : bean.product.code ;
                },
                set: function(bean, obj) { bean.product = obj ; },
                autocomplete: {
                  search: function(val) {
                    var products = service.ProductService.findProductByCode(val).data ;
                    var result = [] ;
                    for(var i = 0; i < products.length; i++) {
                      var product = products[i] ;
                      result[i] = {value: product, label: product.code + ' (' + product.name + ')'} ;
                    }
                    return result ;
                  }
                }
              }
            },
            { 
              field : "inStock", label : "in Stock",
              defaultValue: 0, required: true, validator: { name: 'number', from: 0, errorMsg: "Expect a number > 0" }
            },
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
                  var inventory = thisUI.getBean('inventory');
                  service.WarehouseService.saveInventory(inventory) ;
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
          }
        }
      }
    },
    
    initViewOnly: function(inventory) {
      this.bind('inventory', inventory) ;
      this.getBeanState('inventory').readOnly = true ;
      return this ;
    },
    
    init: function (UIParent, inventory, isNew) {
      this.UIParent = UIParent ;
      this.bind('inventory', inventory, true) ;
      
      var inventoryConfig = this.getBeanConfig('inventory') ;
      inventoryConfig.disableEditAction(false) ;
      
      this.getBeanState('inventory').editMode = true ;
      inventoryConfig.disableField('warehouseCode', !isNew) ;
      return this ;
    }
  });
  
  var Inventory = {
    UIInventory : UIInventory
  };
  
  return Inventory ;
});
