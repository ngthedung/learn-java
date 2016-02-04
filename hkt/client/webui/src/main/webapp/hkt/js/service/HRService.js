define([
  'jquery', 'service/Server'
], function($, Server) {
  
  /** @type service.HRService */
  var HRService = {
    /** @memberOf service.HRService */
    cleanEmployeeDB : function(scenario) {
      var request = {
        module : 'hr', service : 'HRService', method : 'deleteAll', 
      };
      return Server.POST(request);
    },
    
    createScenario : function(scenario) {
      var request = {
        module : 'hr', service : 'HRService', method : 'createScenario',
        params : { scenario : scenario } 
      };
      return Server.POST(request);
    },
    
    saveEmployee : function(employee) {
      var request = {
        module : 'hr', service : 'HRService', method : 'saveEmployee',
        params : { employee : employee }
      };
      return Server.POST(request);
    },
    
    deleteEmployeeById : function(id) {
      var request = {
        module : 'hr', service : 'HRService', method : 'deleteEmployeeById',
        params : { id : id }
      };
      return Server.POST(request);
    },
    
    saveWorkPosition : function(employee, position) {
      var request = {
        module : 'hr', service : 'HRService', method : 'saveWorkPosition',
        params : { employee : employee, position : position }
      };
      return Server.POST(request);
    },
    
    deleteWorkPosition : function(position) {
      var request = {
        module : 'hr', service : 'HRService', method : 'deleteWorkPosition',
        params : { position : position }
      };
      return Server.POST(request);
    },
    
    findWorkPositionByEmployeeId : function(employeeId) {
      var request = {
        module : 'hr', service : 'HRService', method : 'findWorkPositionByEmployeeId',
        params : { employeeId : employeeId }
      };
      return Server.POST(request);
    },
    
    saveDailyWork : function(position, dailyWork) {
      var request = {
        module : 'hr', service : 'HRService', method : 'saveDailyWork',
        params : { position : position, dailyWork : dailyWork }
      };
      return Server.POST(request);
    },
    
    deleteDailyWorkById : function(id) {
      var request = {
          module : 'hr', service : 'HRService', method : 'deleteDailyWorkById',
          params : { id : id }
        };
      return Server.POST(request);
    },
    
    findDailyWorkByPositionId : function(positionId) {
      var request = {
        module : 'hr', service : 'HRService', method : 'findDailyWorkByPositionId',
        params : { positionId : positionId }
      };
      return Server.POST(request);
    },
    
    searchEmployee : function(query) {
      var request = {
          module : 'hr', service : 'HRService', method : 'searchEmployee',
          params : { query: query }
        };
        return Server.POST(request);
    },
    
    getEmployeeByid : function(id){
      var request ={
          module : 'hr', service : 'HRService', method : 'getEmployeeByid',
          params : { id : id }
      };
      return Server.POST(request);
    },
    
    getWorkPositionById : function(id){
      var request ={
        module : 'hr', service : 'HRService', method : 'getWorkPositionById',
        params : { id : id }
      };
      return Server.POST(request);
    },
    
    getEmployeeContracts : function(employee){
      var request ={
        module : 'hr', service : 'HRService', method : 'getEmployeeContracts',
        params : { employee : employee }
      };
      return Server.POST(request);
    },
    
    searchAppointment : function(query) {
      var request = {
          module : 'hr', service : 'HRService', method : 'searchAppointment',
          params : { query: query }
        };
        return Server.POST(request);
    },
    
    saveAppointment : function(appointment) {
      var request = {
        module : 'hr', service : 'HRService', method : 'saveAppointment',
        params : { appointment : appointment }
      };
      return Server.POST(request);
    },
    
    deleteAppointmentById : function(id) {
      var request = {
        module : 'hr', service : 'HRService', method : 'deleteAppointmentById',
        params : { id : id }
      };
      return Server.POST(request);
    },
  };
  return HRService;
  });
