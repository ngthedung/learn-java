ScriptRunner.require('lib/lib.js') ;

AccountService = {
  getAccountByLoginId : function(loginId) {
    var request = {
      module : 'account', service : 'AccountService', method : 'getAccountByLoginId',
      params : { loginId : loginId }
    };
    return Server.POST(request);
  },

  saveAccount : function(account) {
    var request = {
      module : 'account', service : 'AccountService', method : 'saveAccount',
      params : { account : account }
    };
    return Server.POST(request);
  },
  
  deleteAccountByLoginId : function(loginId) {
    var request = {
      module : 'account', service : 'AccountService', method : 'deleteAccountByLoginId',
      params : { loginId : loginId }
    };
    return Server.POST(request);
  },
  
  getGroupByPath : function(path) {
    var request = {
      module : 'account', service : 'AccountService', method : 'getGroupByPath',
      params : { path : path }
    };
    return Server.POST(request);
  },
  
  saveGroup : function(group) {
    var request = {
      module : 'account', service : 'AccountService', method : 'saveGroup',
      params : { group : group }
    };
    return Server.POST(request);
  },
  
  createGroup : function(parentPath, group) {
    var request = {
      module : 'account', service : 'AccountService', method : 'createGroup',
      params : { parentPath: parentPath, group : group }
    };
    return Server.POST(request);
  },
  
  deleteGroup : function(group) {
    var request = {
      module : 'account', service : 'AccountService', method : 'deleteGroup',
      params : { group : group }
    };
    return Server.POST(request);
  },
  
  
  getGroupDetailByPath : function(path) {
    var request = {
      module : 'account', service : 'AccountService', method : 'getGroupDetailByPath',
      params : { path : path }
    };
    return Server.POST(request);
  },
  
  createMembership : function(loginId, groupPath, cap) {
    var request = {
      module : 'account', service : 'AccountService', method : 'createMembership',
      params : { loginId: loginId, groupPath: groupPath, cap : cap }
    };
    return Server.POST(request);
  }
}