define([
  'service/service',
  'ui/UITable',
  'ui/UIPopup',
  'ui/UIBean',
  'ui/UICollapsible',
  'module/warehouse/Inventory',
  'module/warehouse/Warehouse',
  'module/warehouse/Product',
  'util/DateTime',
], function(service, UITable, UIPopup, UIBean, UICollapsible, Inventory, Warehouse, Product, DateTime) {
  
  var UIInInventory= UIBean.extend({
    label: "InInventory",
    config: {
      beans: {
        inInventory: {
          label: 'InInventory',
          fields: [
            { 
              field : "quantity", label : "Quantity", 
              defaultValue: 0, required: true, validator: { name: 'number', from: 0, errorMsg: "Expect a number > 0" } 
            },
            { 
              field : "checkinQuantity", label : "Check In Quantity", 
              defaultValue: 0, required: true, validator: { name: 'number', from: 0, errorMsg: "Expect a number > 0" } 
            },
            { field : "unit", label : "Unit", 
              select: {
                getOptions: function(field, bean) {
                  var options = service.LocaleService.getProductUnits().data ;
                  var result = [] ;
                  for(var i = 0; i < options.length; i++) {
                    var option = options[i] ;
                    result[i] = { label: option.name , value: option.code } ;
                  }
                  return result ;
                }
              }
            },
            { 
              field : "validFromDate", label : "Valid From Date",
              datepicker: {
                getDate: function(bean) {
                  return DateTime.fromDateTimeToDDMMYYYY(bean.validFromDate) ;
                },
                setDate: function(date, bean) {
                  bean.validFromDate = DateTime.fromDDMMYYYYToDateTime(date) ;
                },
              }
            },
            { field : "expireDate", label : "Expire Date",
              datepicker: {
                getDate: function(bean) {
                  return DateTime.fromDateTimeToDDMMYYYY(bean.expireDate) ;
                },
                setDate: function(date, bean) {
                  bean.expireDate = DateTime.fromDDMMYYYYToDateTime(date) ;
                },
              }
            },
            { 
              field : "total", label : "Total",
              defaultValue: 0, required: true, validator: { name: 'number', from: 0, errorMsg: "Expect a number > 0" }
            },
            { 
              field : "finalCharge", label : "Final Charge",
              defaultValue: 0, required: true, validator: { name: 'number', from: 0, errorMsg: "Expect a number > 0" }
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
                  var inInventory = thisUI.getBean('inInventory');
                  service.WarehouseService.saveInInventory(inInventory) ;
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
    
    initViewOnly: function(inInventory) {
      this.bind('inInventory', inInventory) ;
      this.getBeanState('inInventory').readOnly = true ;
      return this ;
    },
    
    init: function (UIParent, inInventory, isNew) {
      this.UIParent = UIParent ;
      this.bind('inInventory', inInventory, true) ;
      
      var inInventoryConfig = this.getBeanConfig('inInventory') ;
      inInventoryConfig.disableEditAction(false) ;
      
      this.getBeanState('inInventory').editMode = true ;
      return this ;
    }
  });
  
  var UIInInventoryList = UITable.extend({
    label : "In Inventories Information", 
    config: {
      toolbar: {
        dflt: {
          actions: [
            {
              action: "onNewInInventory", icon: "add", label: "New",
              onClick: function(thisUI) { 
                var inInventory = { inventoryId : thisUI.inventoryId, quantity : 0, checkinQuantity : 0, unit : "" } ;
                UIPopup.activate(new UIInInventory().init(thisUI, inInventory, true)) ;
              }
            },
            {
              action: "onRefresh", icon: "refresh", label: "Refresh",
              onClick: function(thisUI) {
                thisUI.onRefresh() ;
              }
            }
          ]
        },
      },
      bean: {
        fields: [
          { label : 'Id', field : 'id', toggled : true, filterable : true, },
          { label : 'Inventory Id', field : 'inventoryId', toggled : true, filterable : true },
          { label : 'Quantity', field : 'quantity', toggled : true },
          { label : 'Checkin Quantity', field : 'checkinQuantity', toggled : true },
          { label : 'Unit', field : 'unit', toggled : true },
          { label : 'Valid From Date', field : 'validFromDate', toggled : true },
          { label : 'Expire Date', field : 'expireDate', toggled : true },
          { label : 'Total', field : 'total', toggled : true },
          { label : 'Final Charge', field : 'finalCharge', toggled : true },
        ],
        actions: [
          {
            icon: "edit", label: "Edit",
            onClick: function(thisUI, row) { 
              var inInventory = thisUI.getItemOnCurrentPage(row) ;
              UIPopup.activate(new UIInInventory().init(thisUI, inInventory, false)) ;
            }
          },
          {
            icon: "delete", label: "Delete",
            onClick: function(thisUI, row) { 
              var inInventory = thisUI.getItemOnCurrentPage(row) ;
              var deleted = service.WarehouseService.deleteInInventory(inInventory).data ;
              if(deleted) {
                thisUI.removeItemOnCurrentPage(row) ;
              }
            }
          }
        ]
      }
    },
    
    init: function (UIParent, inventoryId) {
      this.viewStack = UIParent.viewStack ;
      this.inventoryId = inventoryId ;
      this.inInventories = service.WarehouseService.findInInventoryByInventoryId(this.inventoryId).data ;
      this.setBeans(this.inInventories) ;
      return this ;
    },
    
    onRefresh: function() {
      this.inInventories = service.WarehouseService.findInInventoryByInventoryId(this.inventoryId).data ;
      this.setBeans(this.inInventories) ;
      this.renderRows() ;
    },
    
    back: function(refresh) {
      if(refresh) this.onRefresh() ;
      this.viewStack.back() ;
    },
    
  });
  
  var InInventories = {
    UIInInventoryList : UIInInventoryList,
    UIInInventory : UIInInventory
  };
  
  return InInventories ;
});
