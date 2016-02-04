define([
  'jquery', 'service/Server'
], function($, Server) {
  /** @type service.CustomerService */
  var CustomerService = {
    /** @memberOf service.CustomerService */
    cleanPartnerDB : function(scenario) {
      var request = {
        module : 'partner', service : 'CustomerService', method : 'deleteAll', 
      };
      return Server.POST(request);
    },
    
    /** @memberOf service.CustomerService */
    createScenarioCustomer : function(scenario) {
      var request = {
        module : 'partner', service : 'CustomerService', method : 'createScenarioCustomer',
        params : { scenario : scenario } 
      };
      return Server.POST(request);
    },
    
    saveCustomer : function(customer) {
      var request = {
        module : 'partner', service : 'CustomerService', method : 'saveCustomer',
        params : { customer : customer }
      };
      return Server.POST(request);
    },
    
    deleteCustomer : function(customer) {
      var request = {
        module : 'partner', service : 'CustomerService', method : 'deleteCustomer',
        params : { customer : customer }
      };
      return Server.POST(request);
    },
    
    searchCustomer : function(query) {
      var request = {
        module : 'partner', service : 'CustomerService', method : 'searchCustomer',
        params : { query: query }
      };
      return Server.POST(request);
    },
    
    findCustomerByCustomerLoginId: function(customerLoginId) {
      var request = {
        module : 'partner', service : 'CustomerService', method : 'findCustomerByCustomerLoginId',
        params : { customerLoginId: customerLoginId }
      };
      return Server.POST(request);
    },
       
    savePointDetail : function(pointDetail) {
      var request = {
        module : 'partner', service : 'CustomerService', method : 'savePointDetail',
        params : { pointDetail : pointDetail }
      };
      return Server.POST(request);
    },
    
    getPointDetailById : function(pointId) {
      var request = {
        module : 'partner', service : 'CustomerService', method : 'getPointDetailById',
        params : { pointId : pointId }
      };
      return Server.POST(request);
    },
    
    getPointByCustomerId : function(customerId) {
      var request = {
        module : 'partner', service : 'CustomerService', method : 'getPointByCustomerId',
        params : { customerId : customerId }
      };
      return Server.POST(request);
    },
    
    saveCreditDetail : function(creditDetail) {
      var request = {
        module : 'partner', service : 'CustomerService', method : 'saveCreditDetail',
        params : { creditDetail : creditDetail }
      };
      return Server.POST(request);
    },
    
    getCreditDetailById : function(creditId) {
      var request = {
        module : 'partner', service : 'CustomerService', method : 'getCreditDetailById',
        params : { creditId : creditId }
      };
      return Server.POST(request);
    },
    
    getCreditByCustomerId : function(customerId) {
      var request = {
        module : 'partner', service : 'CustomerService', method : 'getCreditByCustomerId',
        params : { customerId : customerId }
      };
      return Server.POST(request);
    },
        
    getPointDetailByCustomer : function(customerId){
      var request = {
        module : 'partner', service : 'CustomerService', method : 'getPointDetailByCustomer',
        params : { customerId : customerId }
      };
      return Server.POST(request);
    },
    
    savePointTransaction : function(pointTransaction){
      var request = {
        module : 'partner', service : 'CustomerService', method : 'savePointTransaction',
        params : { pointTransaction : pointTransaction }
      };
      return Server.POST(request);
    },
    
    deletePointTransaction : function(pointTransaction){
      var request = {
        module : 'partner', service : 'CustomerService', method : 'deletePointTransaction',
        params : { pointTransaction : pointTransaction }
      };
      return Server.POST(request);
    },
      
    getCreditDetailByCustomer : function(customerId){
      var request = {
        module : 'partner', service : 'CustomerService', method : 'getCreditDetailByCustomer',
        params : { customerId : customerId }
      };
      return Server.POST(request);
    },
    
    saveCreditTransaction : function(creditTransaction){
      var request = {
        module : 'partner', service : 'CustomerService', method : 'saveCreditTransaction',
        params : { creditTransaction : creditTransaction }
      };
      return Server.POST(request);
    },
    
    deleteCreditTransaction : function(creditTransaction){
      var request = {
        module : 'partner', service : 'CustomerService', method : 'deleteCreditTransaction',
        params : { creditTransaction : creditTransaction }
      };
      return Server.POST(request);
    },
    
    createPoint : function( customer, point){
      var request = {
        module : 'partner', service : 'CustomerService', method : 'createPoint',
        params : { customer: customer , point : point}
      };
      return Server.POST(request);
    },
      
    createCredit : function(customer, credit){
      var request = {
        module : 'partner', service : 'CustomerService', method : 'createCredit',
        params : { customer : customer, credit : credit}
      };
      return Server.POST(request);
    },
    
    getPointConversionRules : function(){
      var request = {
        module : 'partner', service : 'CustomerService', method : 'getPointConversionRules',
        params : { }
      };
      return Server.POST(request);
    },
    
    savePointConversionRatio : function(pointRatio){
      var request = {
        module : 'partner', service : 'CustomerService', method : 'savePointConversionRatio',
        params : { pointRatio : pointRatio }
      };
      return Server.POST(request);
    },
    
    deletePointConversionRule : function(pointRatio){
      var request = {
        module : 'partner', service : 'CustomerService', method : 'deletePointConversionRule',
        params : { pointRatio : pointRatio }
      };
      return Server.POST(request);
    },
    
    findPointConversionRuleByName : function(name) {
      var request = {
        module : 'partner', service : 'CustomerService', method : 'findPointConversionRuleByName',
        params : { name : name }
      };
      return Server.POST(request);
    }
    
  };
  return CustomerService;
  });
