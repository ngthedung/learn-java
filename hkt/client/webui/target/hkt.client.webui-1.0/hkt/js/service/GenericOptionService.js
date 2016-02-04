define([ 'jquery', 'service/Server' ], function($, Server) {

  /** @type service.GenericOptionService */
  var GenericOptionService = {
    /** @memberOf service.GenericOptionService */
    createScenario : function() {
      var request = {
          module : 'config', service : 'GenericOptionService', method : 'onInit',
          params : {  }
      };
      return Server.POST(request);
    },
    getOptionGroup : function(module, service, name) {
      var request = {
        module : 'config', service : 'GenericOptionService', method : 'getOptionGroup',
        params : { module: module, service : service, name: name }
      };
      return Server.POST(request);
    },
    
    saveOptionGroup : function(optionGroup) {
      var request = {
        module : 'config', service : 'GenericOptionService', method : 'saveOptionGroup',
        params: { optionGroup: optionGroup }
      };
      return Server.POST(request);
    },
    
    getOptionGroups : function() {
      var request = {
        module : 'config', service : 'GenericOptionService', method : 'getOptionGroups',
        params: { }
      };
      return Server.POST(request);
    },
    
    deleteOption : function(optionGroup, index) {
      var request = {
        module : 'config', service : 'GenericOptionService', method : 'deleteOption',
        params: {
          optionGroup : optionGroup, index : index
        }
      };
      return Server.POST(request);
    },
    
    findOptions : function(module, service, name, exp) {
      var request = {
        module : 'config', service : 'GenericOptionService', method : 'findOptions',
        params: { module : module, service : service, name : name, exp : exp }
      };
      return Server.POST(request);
    },
  };
  return GenericOptionService;
});
