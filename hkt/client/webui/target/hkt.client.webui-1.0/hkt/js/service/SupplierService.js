define([
  'jquery', 'service/Server'
], function($, Server) {
  /** @type service.SupplierService */
  var PartnerService = {
    /** @memberOf service.SupplierService */
    cleanPartnerDB : function(scenario) {
      var request = {
        module : 'partner', service : 'SupplierService', method : 'deleteAll', 
      };
      return Server.POST(request);
    },
    
    createScenarioSupplier : function(scenario) {
      var request = {
        module : 'partner', service : 'SupplierService', method : 'createScenarioSupplier',
        params : { scenario : scenario } 
      };
      return Server.POST(request);
    },
    
    saveSupplier : function(supplier) {
      var request = {
        module : 'partner', service : 'SupplierService', method : 'saveSupplier',
        params : { supplier : supplier }
      };
      return Server.POST(request);
    },
    
    deleteSupplier : function(supplier) {
      var request = {
        module : 'partner', service : 'SupplierService', method : 'deleteSupplier',
        params : { supplier : supplier }
      };
      return Server.POST(request);
    },
    
    searchSupplier : function(query) {
      var request = {
          module : 'partner', service : 'SupplierService', method : 'searchSupplier',
          params : { query : query }
        };
      return Server.POST(request);
    },
    
    findSuppliedProductBySupplierId : function(supplierId) {
      var request = {
        module : 'partner', service : 'SupplierService', method : 'findSuppliedProductBySupplierId',
        params : { supplierId : supplierId }
      };
      return Server.POST(request);
    },
    
    findPriceHistoryBySuppliedProductId : function(suppliedProductId){
      var request ={
        module : 'partner', service : 'SupplierService', method : 'findPriceHistoryBySuppliedProductId',
        params : { suppliedProductId : suppliedProductId }
      };
      return Server.POST(request);
    },
    
    getSuppliedProductBySupplier : function(supplierId){
      var request = {
        module : 'partner', service : 'SupplierService', method : 'getSuppliedProductBySupplier',
        params : { supplierId : supplierId }
      };
      return Server.POST(request);
    },
    
    createSuppliedProduct : function(supplier, suppliedProduct){
      var request = {
        module : 'partner', service : 'SupplierService', method : 'createSuppliedProduct',
        params : { supplier : supplier, suppliedProduct : suppliedProduct }
      };
      return Server.POST(request);
    },
    
    createPriceHistory : function(suppliedProduct, priceHistory){
      var request = {
        module : 'partner', service : 'SupplierService', method : 'createPriceHistory',
        params : { suppliedProduct : suppliedProduct, priceHistory : priceHistory }
      };
      return Server.POST(request);
    },
    
    updateSuppliedProduct : function(suppliedProduct){
      var request = {
        module : 'partner', service : 'SupplierService', method : 'updateSuppliedProduct',
        params : { suppliedProduct : suppliedProduct }
      };
      return Server.POST(request);
    },
    
    deleteSuppliedProduct : function(suppliedProduct){
      var request = {
        module : 'partner', service : 'SupplierService', method : 'deleteSuppliedProduct',
        params : { suppliedProduct : suppliedProduct }
      };
      return Server.POST(request);
    },
    
    findPriceHistoryBySuppliedProductID : function(suppliedProductId){
      var request = {
        module : 'partner', service : 'SupplierService', method : 'findPriceHistoryBySuppliedProductID',
        params : { suppliedProductId : suppliedProductId }
      };
      return Server.POST(request);
    },
    
  };
  return PartnerService;
  });
