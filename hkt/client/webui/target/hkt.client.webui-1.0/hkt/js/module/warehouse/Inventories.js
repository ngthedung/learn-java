define([
  'service/service',
  'ui/UITable',
  'ui/UIPopup',
  'ui/UIBean',
  'ui/UICollapsible',
  'module/warehouse/Warehouse',
  'module/warehouse/InInventories',
  'module/warehouse/OutInventories',
  'module/warehouse/Inventory',
], function(service, UITable, UIPopup, UIBean, UICollapsible, Warehouse, InInventories, OutInventories, Inventory) {
  
  var UIInInventoryDetail = UICollapsible.extend({
    label: "In Inventories", 
    config: {
      actions: [
        {
          action: 'back', label: 'Back',
          onClick: function (thisUI) {
            if (thisUI.UIParent.back) {
              thisUI.UIParent.back(false) ;
            }
          }
        },
      ]
    },
    
    init: function (UIParent, inventory) {
      this.clear() ;
      this.UIParent = UIParent ;
      
      this.UIInventory = new Inventory.UIInventory().initViewOnly(inventory) ;
      this.add(this.UIInventory, true) ;
      
      var UIInInventoryList = new InInventories.UIInInventoryList().init(UIParent, inventory.id) ;
      this.add(UIInInventoryList) ;
    }
  });
  
  var UIOutInventoryDetail = UICollapsible.extend({
    label: "Out Inventories", 
    config: {
      actions: [
        {
          action: 'back', label: 'Back',
          onClick: function (thisUI) {
            if (thisUI.UIParent.back) {
              thisUI.UIParent.back(false) ;
            }
          }
        },
      ]
    },
    
    init: function(UIParent, inventory) {
      this.clear() ;
      this.UIParent = UIParent ;
      
      this.UIInventory = new Inventory.UIInventory().initViewOnly(inventory) ;
      this.add(this.UIInventory, true) ;
      
      var UIOutInventoryList = new OutInventories.UIOutInventoryList().init(UIParent, inventory.id) ;
      this.add(UIOutInventoryList) ;
    }
  });
  
  var UIInventoryList = UITable.extend({
    label : "Inventory Information", 
    config: {
      toolbar: {
        dflt: {
          actions: [
            {
              action: "onNewInventory", icon: "add", label: "New",
              onClick: function(thisUI) { 
                var inventory = { warehouseCode: thisUI.warehouseCode };
                UIPopup.activate(new Inventory.UIInventory().init(thisUI, inventory, true)) ;
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
            label : 'Id', field : 'id', toggled : true, filterable : true,
            onClick: function(thisUI, row) {
              var inventory = thisUI.getItemOnCurrentPage(row) ;
              UIPopup.activate(new Inventory.UIInventory().init(thisUI, inventory, false)) ;
            }
          },
          {
            label : 'Warehouse Code', field : 'warehouseCode', toggled : true, filterable : true,
            onClick: function(thisUI, row) {
              var inventory = thisUI.getItemOnCurrentPage(row) ;
              var warehouse = service.WarehouseService.getWarehouseByWarehouseId(inventory.warehouseCode).data ;
              UIPopup.activate(new Warehouse.UIWarehouse().initViewOnly(thisUI, warehouse)) ;
            }
          },
          { label : 'Product Code', field : 'inStock', toggled : true, filterable : true },
          { label : 'In Stock', field : 'inStock', toggled : true, filterable : true }
        ],
        actions: [
          {
            icon: "edit", label: "Edit Inventory",
            onClick: function(thisUI, row) { 
              var inventory = thisUI.getItemOnCurrentPage(row) ;
              UIPopup.activate(new Inventory.UIInventory().init(thisUI, inventory, false)) ;
            }
          },
          {
            icon: "bars", label: "In Inventory",
            onClick: function(thisUI, row) { 
              var inventory = thisUI.getItemOnCurrentPage(row) ;
              thisUI.UIInInventoryDetail.init(thisUI, inventory) ;
              thisUI.viewStack.push(thisUI.UIInInventoryDetail) ;
            }
          },
          {
            icon: "grid", label: "Out Inventory",
            onClick: function(thisUI, row) { 
              var inventory = thisUI.getItemOnCurrentPage(row) ;
              thisUI.UIOutInventoryDetail.init(thisUI, inventory) ;
              thisUI.viewStack.push(thisUI.UIOutInventoryDetail) ;
            }
          },
          {
            icon: "delete", label: "Delete",
            onClick: function(thisUI, row) { 
              var inventory = thisUI.getItemOnCurrentPage(row) ;
              var deleted =  service.WarehouseService.deleteInventory(inventory).data ;
              if(deleted) {
                thisUI.removeItemOnCurrentPage(row) ;
              }
            }
          }
        ]
      }
    },
    
    init: function (UIParent, warehouseCode, productcode) {
      this.viewStack = UIParent.viewStack ;
      this.UIInInventoryDetail = new UIInInventoryDetail() ;
      this.UIOutInventoryDetail = new UIOutInventoryDetail() ;
      
      this.warehouseCode = warehouseCode ;
      this.productcode = productcode ;
      this.inventories = null;
     
      if (warehouseCode != null ) {
        this.inventories = service.WarehouseService.findInventoriesByWarehouseCode(this.warehouseCode).data ;
      }
      if (productcode != null) {
        this.inventories = service.WarehouseService.findInventoriesByProductCode(this.productcode).data ;
      }
      
      this.setBeans(this.inventories) ;
      return this ;
    },
    
    onRefresh: function() {
      if (this.warehouseCode != null ) {
        this.inventories = service.WarehouseService.findInventoriesByWarehouseCode(this.warehouseCode).data ;
      }
      if (this.productcode != null ) {
        this.inventories = service.WarehouseService.findInventoriesByProductCode(this.productcode).data ;
      }
      
      this.setBeans(this.inventories) ;
      this.renderRows() ;
    },
    
    back: function(refresh) {
      if(refresh) this.onRefresh() ;
      this.viewStack.back() ;
    },
    
  });
  
  var Inventories = {
    UIInventoryList : UIInventoryList
  } ;
  
  return Inventories ;

});
