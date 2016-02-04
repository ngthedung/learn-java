define([ 'jquery', 'service/Server' ], function($, Server) {

  /** @type service.LocaleService */
  var LocaleService = {
    /** @memberOf service.LocaleService */
    
    createScenario : function() {
      var request = {
        module : 'config', service : 'LocaleService', method : 'onInit',
        params : {  }
      };
      return Server.POST(request);
    },
    
    cleanLocaleDB : function() {
      var request = {
        module : 'config', service : 'LocaleService', method : 'removeAll',
        params : {  }
      };
      return Server.POST(request);
    },
    
    getCurrencies : function() {
      var request = {
        module : 'config', service : 'LocaleService', method : 'getCurrencies',
        params: { }
      };
      return Server.POST(request);
    },
    
    saveCurrency : function(currency) {
      var request = {
        module : 'config', service : 'LocaleService', method : 'saveCurrency',
        params: { currency: currency }
      };
      return Server.POST(request);
    },
    
    deleteCurrency : function(currency) {
      var request = {
        module : 'config', service : 'LocaleService', method : 'deleteCurrency',
        params: { currency: currency }
      };
      return Server.POST(request);
    },
    
    getProductUnits : function() {
      var request = {
        module : 'config', service : 'LocaleService', method : 'getProductUnits',
        params: { }
      };
      return Server.POST(request);
    },
    
    saveProductUnit : function(productUnit) {
      var request = {
        module : 'config', service : 'LocaleService', method : 'saveProductUnit',
        params: { productUnit: productUnit }
      };
      return Server.POST(request);
    },
    
    deleteProductUnit : function(productUnit) {
      var request = {
        module : 'config', service : 'LocaleService', method : 'deleteProductUnit',
        params: { productUnit: productUnit }
      };
      return Server.POST(request);
    },
    
    findCityByCountryAndName : function(country, name) {
      var request = {
        module : 'config', service : 'LocaleService', method : 'findCityByCountryAndName',
        params: {country: country, name: name}
      };
      return Server.POST(request);
    },
    
    getCountries : function() {
      var request = {
        module : 'config', service : 'LocaleService', method : 'getCountries',
        params: { }
      };
      return Server.POST(request);
    },
    
    saveCountry : function(country) {
      var request = {
        module : 'config', service : 'LocaleService', method : 'saveCountry',
        params: { country: country }
      };
      return Server.POST(request);
    },
    
    deleteCountry : function(country) {
      var request = {
        module : 'config', service : 'LocaleService', method : 'deleteCountry',
        params: { country: country }
      };
      return Server.POST(request);
    },
    
    findCountryByName : function(name) {
      var request = {
        module : 'config', service : 'LocaleService', method : 'findCountryByName',
        params: {name: name}
      };
      return Server.POST(request);
    },
  };
  return LocaleService;
});
