define([
  'service/service',
  'ui/UITable',
  'ui/UIPopup',
  'ui/UIBean',
  'module/warehouse/Warehouse',
  'module/warehouse/Product',
  'module/warehouse/Inventory',
  'module/warehouse/InInventories',
  'util/DateTime',
], function(service, UITable, UIPopup, UIBean, Warehouse, Product, Inventory, InInventories, DateTime) {
 
  var UIOutInventory= UIBean.extend({
    label: "Out Inventory",
    config: {
      beans: {
        outInventory: {
          label: 'Out Inventory',
          fields: [
            { 
              field : "inInventoryId", label : "InInventory Id",
              defaultValue: 0, required: true, validator: { name: 'number', from: 0, errorMsg: "Expect a number > 0" },
            },
            { 
              field : "quantity", label : "Quantity",
              defaultValue: 0, required: true, validator: { name: 'number', from: 0, errorMsg: "Expect a number > 0" }
            },
            { 
              field : "checkoutQuantity", label : "Checkout Quantity",
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
            { field : "validFromDate", label : "Valid From Date",
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
                  var outInventory = thisUI.getBean('outInventory');
                  service.WarehouseService.saveOutInventory(outInventory) ;
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
    
    init: function(UIParent, outInventory, isNew){
      this.UIParent = UIParent ;
      this.bind('outInventory', outInventory, true) ;
      
      var outInventoryConfig = this.getBeanConfig('outInventory') ;
      outInventoryConfig.disableEditAction(false) ;
      
      this.getBeanState('outInventory').editMode = true ;
      outInventoryConfig.disableField('inInventoryId', !isNew) ;
      return this ;
    }
  });
  
  var UIOutInventoryList = UITable.extend({
    label : "Out Inventories Information", 
    config: {
      toolbar: {
        dflt: {
          actions: [
            {
              action: "onNewOutInventory", icon: "add", label: "New",
              onClick: function(thisUI) { 
                var outInventory = { 
                  inventoryId : thisUI.inventoryId, inInventoryId : 0, quantity : 0, checkoutQuantity : 0, unit : ""
                };
                UIPopup.activate(new UIOutInventory().init(thisUI, outInventory, true)) ;
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
          { 
            label : 'Inventory Id', field : 'inventoryId', toggled : true, filterable : true,
            onClick: function(thisUI, row) {
              var outInventory = thisUI.getItemOnCurrentPage(row) ;
              var inventory = service.WarehouseService.getInventoryById(outInventory.inventoryId).data ;
              UIPopup.activate(new Inventory.UIInventory().initViewOnly(inventory)) ;
            }
          },
          { 
            label : 'In Inventory Id', field : 'inInventoryId', toggled : true,
            onClick: function(thisUI, row) {
              var outInventory = thisUI.getItemOnCurrentPage(row) ;
              var inInventory = service.WarehouseService.getInInventoryById(outInventory.inInventoryId).data ;
              UIPopup.activate(new InInventories.UIInInventory().initViewOnly(inInventory)) ;
            }
          },
          { label : 'Quantity', field : 'quantity', toggled : true },
          { label : 'Checkout Quantity', field : 'checkoutQuantity', toggled : true },
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
              var outInventory = thisUI.getItemOnCurrentPage(row) ;
              UIPopup.activate(new UIOutInventory().init(thisUI, outInventory, false)) ;
            }
          },
          {
            icon: "delete", label: "Delete",
            onClick: function(thisUI, row) { 
              var outInventory = thisUI.getItemOnCurrentPage(row) ;
              var deleted = service.WarehouseService.deleteOutInventory(outInventory).data ;
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
      
      this.outInventories = service.WarehouseService.findOutInventoryByInventoryId(this.inventoryId).data ;
      this.setBeans(this.outInventories) ;
      return this ;
    },
    
    onRefresh: function() {
      this.outInventories = service.WarehouseService.findOutInventoryByInventoryId(this.inventoryId).data ;
      this.setBeans(this.outInventories) ;
      this.renderRows() ;
    },
    
    back: function(refresh) {
      if(refresh) this.onRefresh() ;
      this.viewStack.back() ;
    },
    
  });
  
  var OutInventories = {
    UIOutInventoryList : UIOutInventoryList
  };
  
  return OutInventories ;
});
