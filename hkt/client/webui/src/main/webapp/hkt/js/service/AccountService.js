define([
  'jquery',
  'service/Server'
], function($, Server) {
  /**@type service.AccountService */
  var AccountService = {
    /**@memberOf service.AccountService*/
    cleanAccountDB : function(scenario) {
      var request = { 
        module : 'account', service : 'AccountService', method : 'deleteAll',
      };
      return Server.POST(request);
    },

    /**@memberOf service.AccountService*/
    createScenario : function(scenario) {
      var request = {
        module : 'account', service : 'AccountService', method : 'createScenario',
        params : { scenario : scenario }
      };
      return Server.POST(request);
    },

    /**@memberOf service.AccountService*/
    getAccountByLoginId : function(loginId) {
      var request = {
        module : 'account', service : 'AccountService', method : 'getAccountByLoginId',
        params : { loginId : loginId }
      };
      return Server.POST(request);
    },
    
    /**@memberOf service.AccountService*/
    saveAccount : function(account) {
      var request = {
        module : 'account', service : 'AccountService', method : 'saveAccount',
        params : { account : account }
      };
      return Server.POST(request);
    },
    
    /**@memberOf service.AccountService*/
    deleteAccountByLoginId : function(loginId) {
      var request = {
        module : 'account', service : 'AccountService', method : 'deleteAccountByLoginId',
        params : { loginId : loginId }
      };
      return Server.POST(request);
    },
    
    /**@memberOf service.AccountService*/
    findAccountByLoginId : function(loginId) {
      var request = {
        module : 'account', service : 'AccountService', method : 'findAccountByLoginId',
        params : { logingId: loginId }
      };
      return Server.POST(request);
    },
    
    /**@memberOf service.AccountService*/
    filterAccount : function(query) {
      var request = {
        module : 'account', service : 'AccountService', method : 'filterAccount',
        params : { query: query }
      };
      return Server.POST(request);
    },
    
    /**@memberOf service.AccountService*/
    searchAccounts : function(query) {
      var request = {
        module : 'account', service : 'AccountService', method : 'searchAccounts',
        params : { query: query }
      };
      return Server.POST(request);
    },
    
    /**@memberOf service.AccountService*/
    getGroupByPath : function(path) {
      var request = {
        module : 'account', service : 'AccountService', method : 'getGroupByPath',
        params : { path : path }
      };
      return Server.POST(request);
    },
    
    /**@memberOf service.AccountService*/
    saveGroup : function(group) {
      var request = {
        module : 'account', service : 'AccountService', method : 'saveGroup',
        params : { group : group }
      };
      return Server.POST(request);
    },
    
    /**@memberOf service.AccountService*/
    deleteGroup : function(group) {
      var request = {
        module : 'account', service : 'AccountService', method : 'deleteGroup',
        params : { group : group }
      };
      return Server.POST(request);
    },
    
    /**@memberOf service.AccountService*/
    createGroup : function(parentPath, group) {
      var request = {
        module : 'account', service : 'AccountService', method : 'createGroup',
        params : { parentPath: parentPath, group : group }
      };
      return Server.POST(request);
    },
    
    /**@memberOf service.AccountService*/
    getGroupDetailByPath : function(path) {
      var request = {
        module : 'account', service : 'AccountService', method : 'getGroupDetailByPath',
        params : { path : path }
      };
      return Server.POST(request);
    },
    
    /**@memberOf service.AccountService*/
    createMembership : function(loginId, groupPath, cap) {
      var request = {
        module : 'account', service : 'AccountService', method : 'createMembership',
        params : { loginId: loginId, groupPath: groupPath, cap : cap }
      };
      return Server.POST(request);
    },
    
    saveAccountMembership : function(accountMembership) {
      var request = {
        module : 'account', service : 'AccountService', method : 'saveAccountMembership',
        params : { accountMembership : accountMembership }
      };
      return Server.POST(request);
    },
    
    /**@memberOf service.AccountService*/
    deleteMembership : function(loginId, groupPath) {
      var request = {
        module : 'account', service : 'AccountService', method : 'deleteMembership',
        params : { loginId: loginId, groupPath: groupPath}
      };
      return Server.POST(request);
    },
    
    findMembershipByGroupPath : function(path) {
      var request = {
        module : 'account', service : 'AccountService', method : 'findMembershipByGroupPath',
        params : { path: path }
      };
      return Server.POST(request);
    },
    
    /**@memberOf service.AccountService*/
    findGroupByName : function(name) {
      var request = {
        module : 'account', service : 'AccountService', method : 'findGroupByName',
        params : {name: name}
      };
      return Server.POST(request);
    },
    /**@memberOf service.AccountService*/
    findGroupByParentPathAndName : function(parentPath, name) {
      var request = {
        module : 'account', service : 'AccountService', method : 'findGroupByParentPathAndName',
        params : { parentPath: parentPath, name: name}
      };
      return Server.POST(request);
    },
    

    /**@memberOf service.AccountService*/
    findAccountByLoginIdUser : function(loginId) {
      var request = {
        module : 'account', service : 'AccountService', method : 'findAccountByLoginIdUser',
        params : { logingId: loginId }
      };
      return Server.POST(request);
    },
    
    /**@memberOf service.AccountService*/
    findAccountByLoginIdOrg : function(loginId) {
      var request = {
        module : 'account', service : 'AccountService', method : 'findAccountByLoginIdOrg',
        params : { logingId: loginId }
      };
      return Server.POST(request);
    },
  };
  
  return AccountService ;
});
