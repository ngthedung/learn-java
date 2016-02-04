define([
  'jquery',
  'service/Server'
], function($, Server) {
  /** @type service.PromotionService */
  
  var PromotionService = {
    
    cleanPromotionDB : function(scenario) {
      var request = {
        module : 'warehouse', service : 'PromotionService', method : 'deleteAll', 
      };
      return Server.POST(request);
    },
    
    createScenarioPromotion : function(scenario) {
      var request = {
        module : 'warehouse', service : 'PromotionService', method : 'createScenarioPromotion',
        params : { scenario : scenario } 
      };
      return Server.POST(request);
    },
    
    getPromotions : function() {
      var request = {
        module : 'warehouse', service : 'PromotionService', method : 'getPromotions',
        params : { }
      };
      return Server.POST(request);
    },
    
    savePromotion : function(promotion) {
      var request = {
        module : 'warehouse', service : 'PromotionService', method : 'savePromotion',
        params : { promotion : promotion }
      };
      return Server.POST(request);
    },
    
    deletePromotion : function(promotion) {
      var request = {
        module : 'warehouse', service : 'PromotionService', method : 'deletePromotion',
        params : { promotion : promotion }
      };
      return Server.POST(request);
    },
    
    searchPromotionUsed : function(query) {
      var request = {
        module : 'warehouse', service : 'PromotionService', method : 'searchPromotionUsed',
        params : { query : query }
      };
      return Server.POST(request);
    },
    
    savePromotionUsed : function(promotionUsed) {
      var request = {
        module : 'warehouse', service : 'PromotionService', method : 'savePromotionUsed',
        params : { promotionUsed : promotionUsed }
      };
      return Server.POST(request);
    },
    
    deletePromotionUsed : function(promotionUsed) {
      var request = {
        module : 'warehouse', service : 'PromotionService', method : 'deletePromotionUsed',
        params : { promotionUsed : promotionUsed }
      };
      return Server.POST(request);
    },
    
    findPromotionUsedByPromotionId : function(promotionId) {
      var request = {
        module : 'warehouse', service : 'PromotionService', method : 'findPromotionUsedByPromotionId',
        params : { promotionId : promotionId }
      };
      return Server.POST(request);
    },
    
    getPromotionById : function(promotionId) {
      var request = {
          module : 'warehouse', service : 'PromotionService', method : 'getPromotionById',
          params : { promotionId : promotionId }
        };
      return Server.POST(request);
    },

    searchCoupon : function(query) {
      var request = {
          module : 'warehouse', service : 'PromotionService', method : 'searchCoupon',
          params : { query : query }
        };
      return Server.POST(request);
    },

    saveCoupon : function(coupon) {
      var request = {
          module : 'warehouse', service : 'PromotionService', method : 'saveCoupon',
          params : { coupon : coupon }
        };
      return Server.POST(request);
    },
    
    deleteCoupon : function(coupon) {
      var request = {
          module : 'warehouse', service : 'PromotionService', method : 'deleteCoupon',
          params : { coupon : coupon }
        };
      return Server.POST(request);
    },
    
    getAllMenus : function() {
      var request = {
          module : 'warehouse', service : 'PromotionService', method : 'getAllMenus',
          params : { }
        };
      return Server.POST(request);
    },
    

    saveMenu : function(menu) {
      var request = {
          module : 'warehouse', service : 'PromotionService', method : 'saveMenu',
          params : { menu : menu }
        };
      return Server.POST(request);
    },
    
    deleteMenu : function(menu) {
      var request = {
          module : 'warehouse', service : 'PromotionService', method : 'deleteMenu',
          params : { menu : menu }
        };
      return Server.POST(request);
    },
    
    
  };  
  return PromotionService;
});
