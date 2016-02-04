define([ 
  'jquery', 
  'service/Server' 
], function($, Server) {
  /** @type service.WarehouseService */
  var WarehouseService = {
     /**@memberOf service.WarehouseService */
    cleanWarehouseDB : function(scenario) {
      var request = { module : 'warehouse', service : 'WarehouseService', method : 'deleteAll', };
      return Server.POST(request);
    },
    
     /**@memberOf service.WarehouseService */
    createScenario : function(scenario) {
      var request = { 
        module : 'warehouse', service : 'WarehouseService', method : 'createScenario',
        params : { scenario : scenario }
      };
      return Server.POST(request);
    },
    
    getWarehouseByWarehouseId : function(warehouseId) {
      var request = { 
        module : 'warehouse', service : 'WarehouseService', method : 'getWarehouseByWarehouseId',
        params : { warehouseId : warehouseId } 
      };
      return Server.POST(request);
    },
    
    saveWarehouse : function(Warehouse) {
      var request = { 
        module : 'warehouse', service : 'WarehouseService', method : 'saveWarehouse',
        params : { Warehouse : Warehouse }
      };
      return Server.POST(request);
    },
    
    deleteWarehouseByWarehouseId : function(warehouseId) {
      var request = { 
        module : 'warehouse', service : 'WarehouseService', method : 'deleteWarehouseByWarehouseId',
        params : { warehouseId : warehouseId }
      };
      return Server.POST(request);
    },
    
    findWarehouses : function() {
      var request = { 
        module : 'warehouse', service : 'WarehouseService', method : 'findWarehouses',
        params : { }
      };
      return Server.POST(request);
    },
    
    findWarehouseByWarehouseId : function(name) {
      var request = { 
        module : 'warehouse', service : 'WarehouseService', method : 'findWarehouseByWarehouseId',
        params : { name : name}
      };
      return Server.POST(request);
    },
    
    getInventoryById : function(id) {
      var request = { 
        module : 'warehouse', service : 'WarehouseService', method : 'getInventoryById',
        params : { id : id}
      };
      return Server.POST(request);
    },
    
    saveInventory : function(inventory) {
      var request = { 
        module : 'warehouse', service : 'WarehouseService', method : 'saveInventory',
        params : { inventory : inventory}
      };
      return Server.POST(request);
    },
    
    deleteInventory : function(inventory) {
      var request = { 
        module : 'warehouse', service : 'WarehouseService', method : 'deleteInventory',
        params : { inventory : inventory }
      };
      return Server.POST(request);
    },
    
    findInventoriesByWarehouseCode : function(warehouseCode) {
      var request = { 
        module : 'warehouse', service : 'WarehouseService', method : 'findInventoriesByWarehouseCode',
        params : { warehouseCode : warehouseCode }
      };
      return Server.POST(request);
    },
    
    findInventoriesByProductCode : function(productCode) {
      var request = { 
        module : 'warehouse', service : 'WarehouseService', method : 'findInventoriesByProductCode',
        params : { productCode : productCode }
      };
      return Server.POST(request);
    },
    
    getInInventoryById : function(id) {
      var request = { 
        module : 'warehouse', service : 'WarehouseService', method : 'getInInventoryById',
        params : { id : id }
      };
      return Server.POST(request);
    },
    
    saveInInventory : function(inInventory) {
      var request = { 
        module : 'warehouse', service : 'WarehouseService', method : 'saveInInventory',
        params : { inInventory : inInventory }
      };
      return Server.POST(request);
    },
    
    deleteInInventory : function(inInventory) {
      var request = { 
        module : 'warehouse', service : 'WarehouseService', method : 'deleteInInventory',
        params : { inInventory : inInventory }
      };
      return Server.POST(request);
    },
    
    findInInventoryByInventoryId : function(inventoryId) {
      var request = { 
        module : 'warehouse', service : 'WarehouseService', method : 'findInInventoryByInventoryId',
        params : { inventoryId : inventoryId }
      };
      return Server.POST(request);
    },
    
    saveOutInventory : function(outInventory) {
      var request = { 
        module : 'warehouse', service : 'WarehouseService', method : 'saveOutInventory',
        params : { outInventory : outInventory }
      };
      return Server.POST(request);
    },
    
    deleteOutInventory : function(outInventory) {
      var request = { 
        module : 'warehouse', service : 'WarehouseService', method : 'deleteOutInventory',
        params : { outInventory : outInventory }
      };
      return Server.POST(request);
    },
    
    findOutInventoryByInventoryId : function(inventoryId) {
      var request = { 
        module : 'warehouse', service : 'WarehouseService', method : 'findOutInventoryByInventoryId',
        params : { inventoryId : inventoryId }
      };
      return Server.POST(request);
    },
    
  };

  return WarehouseService;
});