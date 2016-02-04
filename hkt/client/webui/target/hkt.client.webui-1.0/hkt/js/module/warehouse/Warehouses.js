define([
  'service/service',
  'module/Module',
  'ui/UITable',
  'ui/UIPopup',
  'ui/UIBreadcumbs',
  'ui/UICollapsible',
  'module/account/Account',
  'module/warehouse/Warehouse',
  'module/warehouse/Inventories'
], function(service, module, UITable, UIPopup, UIBreadcumbs, UICollapsible, Account, Warehouse, Inventories) {
  
  var UIInventory =  UICollapsible.extend({
    label: "Inventories",
    config: {
      actions: [
        {
          action: 'back', label: 'Back',
          onClick: function (thisUI) {
            if (thisUI.UIParent.back) {
              thisUI.UIParent.back(false) ;
            }
          }
        }
      ]
    },
    
    init: function (UIParent, warehouse) {
      this.UIParent = UIParent ;
      this.clear() ;
      this.add(new Warehouse.UIWarehouse().initViewOnly(UIParent, warehouse), true) ;
      
      var UIInventoryList = new Inventories.UIInventoryList().init(UIParent, warehouse.warehouseId, null) ;
      this.add(UIInventoryList) ;
    }
  });
  
  var UIWarehouseList = UITable.extend({
    label : "Warehouse", 
    config: {
      toolbar: {
        dflt: {
          actions: [
            {
              action: "onNewWarehouse", icon: "add", label: "New",
              onClick: function(thisUI) { 
                var warehouse = { 
                  name : "", ownerId : "", ownerGroup : "", address : ""
                } ;
                UIPopup.activate(new Warehouse.UIWarehouse().init(thisUI, warehouse, true)) ;
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
          { 
            label : 'Warehouse Id', field : 'warehouseId', toggled : true, filterable : true,
            onClick: function(thisUI, row) {
              var warehouse = thisUI.getItemOnCurrentPage(row) ;
              UIPopup.activate(new Warehouse.UIWarehouse().init(thisUI, warehouse, false)) ;
            }
          },
          { label : 'Name', field : 'name', toggled : true, filterable : true },
          { 
            label : 'Owner Id', field : 'ownerId', toggled : true, filterable : true,
            onClick: function(thisUI, row) {
              var warehouse = thisUI.getItemOnCurrentPage(row) ;
              var account = service.AccountService.getAccountByLoginId(warehouse.ownerId).data ;
              var uiDetail = thisUI.UIAccountDetail.init(thisUI, account, true) ;
              thisUI.viewStack.push(uiDetail) ;
            }
          },
          { label : 'Owner Group', field : 'ownerGroup', toggled : true, filterable : true },
          { label : 'Address', field : 'address', toggled : true, filterable : true },
          { label : 'Description', field : 'description', toggled : true, filterable : true }
        ],
        actions: [
          {
            icon: "bars", label: "Inventory",
            onClick: function(thisUI, row) { 
              var warehouse = thisUI.getItemOnCurrentPage(row) ;
              thisUI.UIInventory.init(thisUI, warehouse);
              thisUI.viewStack.push(thisUI.UIInventory) ;
            }
          },
          {
            icon: "delete", label: "Delete",
            onClick: function(thisUI, row) { 
              var warehouse = thisUI.getItemOnCurrentPage(row) ;
              var deleted = service.WarehouseService.deleteWarehouseByWarehouseId(warehouse.warehouseId).data ;
              if(deleted) {
                thisUI.removeItemOnCurrentPage(row) ;
              }
            }
          }
        ]
      }
    },
    
    init: function (viewStack) {
      this.viewStack = viewStack ;
      
      this.UIAccountDetail = new Account.UIAccountDetail() ;
      this.UIInventory = new UIInventory() ;
      
      var warehouses = service.WarehouseService.findWarehouses().data ;
      this.setBeans(warehouses) ;
      this.renderRows() ;
      return this ;
    },
    
    onRefresh: function() {
      var warehouses = service.WarehouseService.findWarehouses().data ;
      this.setBeans(warehouses) ;
      this.renderRows() ;
    },
    
    back: function(refresh) {
      if(refresh) this.onRefresh() ;
      this.viewStack.back() ;
    },
    
  });
  
  var UIWarehousesScreen = module.UIScreen.extend({
    initialize: function (options) { },
    
    activate: function() { 
      this.viewStack = new UIBreadcumbs({el: "#workspace"}) ;
      var aview = new UIWarehouseList().init(this.viewStack) ;
      this.viewStack.push(aview) ;
    },
    
    deactivate: function() { }
    
  });
  
  return UIWarehousesScreen ;
});
