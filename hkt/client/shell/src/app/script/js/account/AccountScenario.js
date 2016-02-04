ScriptRunner.require('lib/lib.js') ;
ScriptRunner.require('account/AccountService.js') ;

Console.h1('Account Scenario');

function deleteGroupIfExist(groupPath) {
  var group = AccountService.getGroupByPath(groupPath).data ; 
  if(group != null) {
    AccountService.deleteGroup(group) ;
  }
}

function createGroups(groups) {
  for(var i = 0; i < groups.length; i++) {
    var group = groups[i] ;
    var path = group.path ;
    var idx = path.lastIndexOf("/") ;
    var parentPath = null ;
    if(idx > 0) parentPath = path.substring(0, idx) ;
    AccountService.createGroup(parentPath, group) ;
  }
}

function createAccounts(accounts) {
  for(var i = 0; i < accounts.length; i++) {
    var account = accounts[i] ;
    var oldAcc = AccountService.getAccountByLoginId(account.loginId).data ;
    if(oldAcc != null) {
      AccountService.deleteAccountByLoginId(oldAcc.loginId) ;
    }
    AccountService.saveAccount(account) ;
  }
}

function createMembership(group, capability, loginIds) {
  for(var i = 0; i < loginIds.length; i++) {
    AccountService.createMembership(loginIds[i], group, capability) ;
  }
}

var groups = [
  {path: "hkt", name: "hkt", label: "HKT Organization Group"} ,
  {path: "hkt/employees", name: "employees", label: "HKT Employees Group"},
  {path: "hkt/employees/manager", name: "manager", label: "HKT Manager"},
  {path: "hkt/employees/it", name: "it", label: "HKT IT"}
] ;

var employeeAccounts = [
  {loginId: "tu.phan", password: "tu", email: "tu.phan@hkt.com"},
  {loginId: "tuan.nguyen", password: "tuan", email: "tuan.nguyen@hkt.com"},
  {loginId: "long.nguyen", password: "long", email: "long.nguyen@hkt.com"}
] ;

deleteGroupIfExist("hkt") ;
createGroups(groups) ;
createAccounts(employeeAccounts) ;
var employees = [
  "tu.phan", "tuan.nguyen", "long.nguyen"
];
createMembership("hkt/employees", "READ", employees) ;
var managers = [
  "tu.phan", "tuan.nguyen"
];

createMembership("hkt/employees/manager", "READ", managers) ;
var its = [
  "tuan.nguyen", "long.nguyen"
];
createMembership("hkt/employees/it", "READ", its) ;