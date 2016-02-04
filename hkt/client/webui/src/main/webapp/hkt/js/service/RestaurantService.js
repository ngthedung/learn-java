define([
  'jquery',
  'service/Server'
], function ($, Server) {
  /** @type service.RestaurantService */
  var RestaurantService = {
//    module : 'restaurant',
//    
//    service : 'RestaurantService',
		 cleanRestaurantDB: function(scenario){
			 var request = { module : 'restaurant', service : 'RestaurantService', method : 'deleteAll', };
		      return Server.POST(request);
		 },
    
    createScenario : function(scenario) {
      var request = {
        module : 'restaurant', service : 'RestaurantService', method : 'createScenario',
        params : { scenario : scenario }
      };
      return Server.POST(request);
    },
    
    cleanRestaurantDB : function(scenario) {
      var request = {
        module : 'restaurant', service : 'RestaurantService', method : 'deleteAll',
      };
      return Server.POST(request);
    },
    
    saveRecipe : function(recipe) {
      var request = {
        module : 'restaurant', service : 'RestaurantService', method : 'saveRecipe',
        params : { recipe : recipe }
      };
      return Server.POST(request);
    },
    
    deleteRecipe : function(recipe) {
      var request = {
        module : 'restaurant', service : 'RestaurantService', method : 'deleteRecipe',
        params : { recipe : recipe }
      };
      return Server.POST(request);
    },
    
    filterRecipe : function(query) {
      var request = {
        module : 'restaurant', service : 'RestaurantService', method : 'filterRecipe',
        params : { query : query }
      };
      return Server.POST(request);
    },
    
    getTables : function() {
      var request = {
        module : 'restaurant', service : 'RestaurantService', method : 'getTables',
        params : {  }
      };
      return Server.POST(request);
    },
    
    saveTable : function(table) {
      var request = {
        module : 'restaurant', service : 'RestaurantService', method : 'saveTable',
        params : { table : table }
      };
      return Server.POST(request);
    },
    
    deleteTable : function(table) {
      var request = {
        module : 'restaurant', service : 'RestaurantService', method : 'deleteTable',
        params : { table : table }
      };
      return Server.POST(request);
    },
    
    getAllShift : function() {
    	var request = {
    			module : 'restaurant', service : 'RestaurantService', method : 'getAllShift',
    			params: { }
    	};
    	return Server.POST(request);
    },
    
    saveShift : function(shift) {
        var request = {
          module : 'restaurant', service : 'RestaurantService', method : 'saveShift',
          params : { shift : shift }
        };
        return Server.POST(request);
      },

    deleteShift : function	(shift) {
		var request = {
				module: 'restaurant', service : 'RestaurantService', method : 'deleteShift',
				params: {shift : shift}
		};
		return Server.POST(request);
	},
      
      getShiftByCode : function(code){
  		var request = {
				module: 'restaurant', service : 'RestaurantService', method : 'getShiftByCode',
				params: {code : code}
		};
  		return Server.POST(request);
      },
	
      getLocations : function(){
    	  var request = {
  				module: 'restaurant', service : 'RestaurantService', method : 'getLocations',
  				params: { }
  		};
    	  return Server.POST(request);
      },
      
      saveLocation: function(location){
    	  var request = {
    				module: 'restaurant', service : 'RestaurantService', method : 'saveLocation',
    				params: {location : location }
    		};
      	  return Server.POST(request); 
      },
      
      findLocationByCode: function(locationCode){
    	  var request = {
  				module: 'restaurant', service : 'RestaurantService', method : 'findLocationByCode',
  				params: {location : location }
  		};
    	  return Server.POST(request); 
      },
      
      deleteLocation: function(location){
    	  var request = {
    				module: 'restaurant', service : 'RestaurantService', method : 'deleteLocation',
    				params: {location : location }
    		};
      	  return Server.POST(request); 
      }
	
  };
  
  return RestaurantService;
});
