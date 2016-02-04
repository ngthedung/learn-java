define([
  'jquery', 
  'underscore', 
  'backbone',
  'service/service',
  'ui/UICollapsible',
  'ui/UITable',
  'module/account/Account'
], function($, _, Backbone, service, UICollapsible, UITable, Account) {  
  var UIWebuiACL = UITable.extend({
    label: "Webui ACL",
    config: {
      toolbar: {
        dflt: {
          actions: [
            {
              action: "onAdmin", label: "Admin", 
              onClick: function(thisTable) { 
              } 
            },
            {
              action: "onUser", label: "User", 
              onClick: function(thisTable) { 
              } 
            },
            {
              action: "onCustomer", label: "Customer", 
              onClick: function(thisTable) { 
              } 
            }
          ]
        }
      },
      
      bean: {
        label: 'Webui ACL',
        fields: [
          { 
            field: "module",   label: "Module", 
            toggled: true, filterable: true,
          },
          { 
            field: "screen",   label: "Screen", 
            toggled: true, filterable: true,
          },
          { 
            field: "group",   label: "Account Group", 
            toggled: true, filterable: true,
          },
          { 
            field: "capability",   label: "Capability", 
            toggled: true, filterable: true,
          },
          { 
            field: "tag",   label: "Tag", 
            toggled: true, filterable: true,
          },
        ],
        actions:[
          {
            icon: "check", label: "Check/Uncheck",
            onClick: function(thisTable, row) { 
              thisTable.onEditBean(row) ;
            }
          }
        ]
      }
    },
    
    init: function(uipermissions) {
      var uipermissions = [
        { module: "account", group: "hkt/webui/account", capability: "READ" },
        { module: "account", screen: "Accounts", group: "hkt/webui/account/Accounts", capability: "READ" },
        { module: "account", screen: "Groups", group: "hkt/webui/account/Groups", capability: "READ" }
      ];
      this.setBeans(uipermissions) ;
      return this ;
    }
  
  });
  
  var UIAccountACL = UICollapsible.extend({
    label: "Account ACL",
    config: {
      actions: [
        { 
          action: "back", label: "Back",
          onClick: function(thisUI) {
            if(thisUI.UIParent.back) thisUI.UIParent.back(false) ; 
          }
        },
        {
          action: "save", label: "Save",
          onClick: function(thisUI) {
           // service.AppService.savePermissionConfigs(thisUI.account.loginId, thisUI.aCLConfigs);
            if(thisUI.UIParent.back) thisUI.UIParent.back(false) ;
          }
        }
      ]
    },
    
    init: function(UIParent, account) {
      this.clear() ;
      this.account = account;
     // this.aCLConfigs = service.AppService.getPermissionConfigs(account.loginId).data;
      this.UIParent = UIParent ;
      this.add(new Account.UIAccount().initWithDetail(account, true)) ;
      this.add(new UIWebuiACL().init(this.aCLConfigs)) ;
      return this ;
    },
  });
  
  return UIAccountACL ;
});
