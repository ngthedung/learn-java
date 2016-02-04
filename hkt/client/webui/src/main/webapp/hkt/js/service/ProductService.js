define([ 
  'jquery', 
  'service/Server' 
], function($, Server) {
  /** @type service.ProductService */
  var ProductService = {
     /**@memberOf service.ProductService */
    cleanProductDB : function(scenario) {
      var request = { module : 'warehouse', service : 'ProductService', method : 'deleteAll', };
      return Server.POST(request);
    },
    
     /**@memberOf service.ProductService */
    createScenario : function(scenario) {
      var request = { 
        module : 'warehouse', service : 'ProductService', method : 'createScenario',
        params : { scenario : scenario }
      };
      return Server.POST(request);
    },
    
    deleteProductByCode : function(code) {
      var request = {
        module : 'warehouse', service : 'ProductService', method : 'deleteProductByCode',
        params : { code : code } 
      };
      return Server.POST(request);
    },
    
    filterProduct : function(query) {
      var request = {
        module : 'warehouse', service : 'ProductService', method : 'filterProduct',
        params : { query : query }
      };
      return Server.POST(request);
    },
    
    findProductByCode : function(code) {
      var request = {
        module : 'warehouse', service : 'ProductService', method : 'findProductByCode',
        params : { code : code }
      };
      return Server.POST(request);
    },
    
    findProductByNameOrCode : function(name) {
        var request = {
                module : 'warehouse', service : 'ProductService', method : 'findProductByNameOrCode',
                params : { name : name }
              };
              return Server.POST(request);
            },
    
    findProductByName: function(name) {
        var request = {
                module : 'warehouse', service : 'ProductService', method : 'findProductByName',
                params : { name : name }
              };
              return Server.POST(request);
    },
    
    searchProducts : function(query){
      var request = {
        module : 'warehouse', service : 'ProductService', method : 'searchProducts',
        params : { query : query }
      };
      return Server.POST(request);
    },
    
    getProductDetailByCode : function(code) {
      var request = {
        module : 'warehouse', service : 'ProductService', method : 'getProductDetailByCode',
        params : { code : code }
      };
      return Server.POST(request);
    },
    
    saveProductDetail : function(pDetail) {
      var request = {
        module : 'warehouse', service : 'ProductService', method : 'saveProductDetail',
        params : { pDetail : pDetail }
      };
      return Server.POST(request);
    },
    
    saveProductPrice : function(productPrice) {
      var request = {
        module : 'warehouse', service : 'ProductService', method : 'saveProductPrice',
        params : { productPrice : productPrice }
      };
      return Server.POST(request);
    },
    
    deleteProductPrice : function(productPrice) {
      var request = {
        module : 'warehouse', service : 'ProductService', method : 'deleteProductPrice',
        params : { productPrice : productPrice }
      };
      return Server.POST(request);
    },
    
    searchByProductPriceType : function(type) {
      var request = {
        module : 'warehouse', service : 'ProductService', method : 'searchByProductPriceType',
        params : { type : type }
      };
      return Server.POST(request);
    },
    
    getProductPriceTypes : function() {
      var request = {
        module : 'warehouse', service : 'ProductService', method : 'getProductPriceTypes',
        params : {  }
      };
      return Server.POST(request);
    },
    
    searchByType : function(type) {
      var request = {
        module : 'warehouse', service : 'ProductService', method : 'searchByType',
        params : { type : type }
      };
      return Server.POST(request);
    },
    
    saveProductPriceType : function(productPriceType) {
      var request = {
        module : 'warehouse', service : 'ProductService', method : 'saveProductPriceType',
        params : { productPriceType : productPriceType }
      };
      return Server.POST(request);
    },
    
    deleteProductPriceType : function(productPriceType) {
      var request = {
        module : 'warehouse', service : 'ProductService', method : 'deleteProductPriceType',
        params : { productPriceType : productPriceType }
      };
      return Server.POST(request);
    },
    
    getTaxs : function() {
      var request = {
        module : 'warehouse', service : 'ProductService', method : 'getTaxs',
        params : {  }
      };
      return Server.POST(request);
    },
    
    saveTax : function(tax) {
      var request = {
        module : 'warehouse', service : 'ProductService', method : 'saveTax',
        params : { tax : tax }
      };
      return Server.POST(request);
    },
    
    deleteTax : function(tax) {
      var request = {
        module : 'warehouse', service : 'ProductService', method : 'deleteTax',
        params : { tax : tax }
      };
      return Server.POST(request);
    },
    
    findTaxByName : function(name) {
      var request = {
        module : 'warehouse', service : 'ProductService', method : 'findTaxByName',
        params : { name : name }
      };
      return Server.POST(request);
    },
    
    saveProductTag : function(productTag) {
      var request = {
        module : 'warehouse', service : 'ProductService', method : 'saveProductTag',
        params : { productTag : productTag }
      };
      return Server.POST(request);
    },
    
    deleteProductTag : function(productTag) {
      var request = {
        module : 'warehouse', service : 'ProductService', method : 'deleteProductTag',
        params : { productTag : productTag }
      };
      return Server.POST(request);
    },
    
    getProductTags : function() {
      var request = {
        module : 'warehouse', service : 'ProductService', method : 'getProductTags',
        params : {  }
      };
      return Server.POST(request);
    },
    
    getRootTags : function() {
        var request = {
          module : 'warehouse', service : 'ProductService', method : 'getRootTags',
          params : {  }
        };
        return Server.POST(request);
      },
    
    findProductTag : function(search) {
      var request = {
        module : 'warehouse', service : 'ProductService', method : 'findProductTag',
        params : { search: search }
      };
      return Server.POST(request);
    },
    
    getChildProductTag : function(tag) {
        var request = {
          module : 'warehouse', service : 'ProductService', method : 'getChildProductTag',
          params : { tag: tag }
        };
        return Server.POST(request);
      },
    
    getProductTagByTag : function(tag) {
      var request = {
        module : 'warehouse', service : 'ProductService', method : 'getProductTagByTag',
        params : { tag: tag }
      };
      return Server.POST(request);
    }
  };

  return ProductService;
});