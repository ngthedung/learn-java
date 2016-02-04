define([
  'jquery', 
  'underscore', 
  'backbone',
  'service/service',
  'module/Module',
  'ui/UITable',
  'ui/UIReport',
  'ui/UIBreadcumbs',
  'ui/UIPopup',
  'module/account/Account',
  'module/account/UIAccountACL',
  'i18n',
], function($, _, Backbone, service, module, UITable, UIReport, UIBreadcumbs, UIPopup, 
            Account, UIAccountACL, i18n) {
  
  var res = i18n.getResource('module/account/account');
  
  var AccountReport = UIReport.extend({
    label: 'Account Report',
    config: {
      charts: [
        { 
          name: 'userVsOrgBar', label: 'User vs Org Bar Chart', 
          type: 'bar',  dataUnit: '', animate: false,
          columns: ["USER", "ORGANIZATION"],
        },
        { 
          name: 'userVsOrgPie', label: 'User vs Org Pie Chart',
          type: 'pie',  dataUnit: '', animate: false,
          columns: ["USER", "ORGANIZATION"],
        },
        { 
          name: 'line', label: 'Line Chart',
          type: 'line',  dataUnit: '', animate: false,
          columns: ["USER", "ORGANIZATION"],
        }
      ]
    },
    
    init: function(data) {
      this.setHeaders(['USER','ORGANIZATION']) ;
      var orgCount = 0, userCount = 0 ;
      for(var i = 0; i < data.length; i++) {
        var rec = data[i] ;
        if(rec.type == 'USER') userCount++ ;
        else orgCount++ ;
      }
      this.addRow("Total", [userCount, orgCount]) ;
    }
  
  });
  
  var UIAccountList = UITable.extend({
    label: res('UIAccountList.label'),
    config : {
      toolbar: {
        dflt: {
          actions: [
            {
              action: "onNew", icon: "plus", label: res('UIAccountList.action.onNew'),
              onClick: function(thisUI) { 
                var account = { 
                  type : 'USER', loginId : "",
                  profiles : { basic : { firstName : '', lastName : '' } }
                };
                UIPopup.activate(new Account.UIAccount().init(thisUI, account, true)) ;
              } 
            },
            {
              action: "onRefresh", icon: "refresh", label: res('UIAccountList.action.onRefresh'), 
              onClick: function(thisUI) { 
                thisUI.onRefresh();
              } 
            },
            {
              action: "onReport", icon: "forward", label: res('UIAccountList.action.onReport'),
              onClick: function(thisUI) { 
                var uiReport = new AccountReport() ;
                uiReport.init(thisUI.getBeans());
                thisUI.viewStack.push(uiReport) ;
              } 
            }
          ],
        },
        filter: {
          fields: [
            { label: 'Search By Login Id',  field: 'loginId', type: 'string', operator: 'LIKE'},
            { label: 'Type',  field: 'type', type: 'string', operator: 'EQUAL' },
            { label: 'Email', field: 'email', operator: 'LIKE'}
          ],
          onFilter: function(thisUI, query) {
            var result = service.AccountService.filterAccount(query).data ;
            var users = result.data ;
            thisUI.setBeans(users) ;
            thisUI.renderRows() ;
          },
        },
        search: {
          onSearch: function(thisUI, query) {
            console.log(JSON.stringify(query)) ;
            var searchResult = service.AccountService.searchAccounts(query).data ;
            var beans = [] ;
            if(searchResult.records != null) {
              for(var i = 0; i < searchResult.records.length; i++) {
                beans[i] = searchResult.records[i].entity ;
              }
            }
            //console.log(JSON.stringify(searchResult)) ;
            thisUI.setBeans(beans) ;
            thisUI.renderRows() ;
          },
        }
      },
      
      bean: {
        fields: [
          { 
            label: res('UIAccountList.field.loginId'), field: 'loginId', toggled: true, filterable: true,
            onClick: function(thisUI, row) {
              var account = thisUI.getItemOnCurrentPage(row) ;
              var uiDetail = thisUI.UIAccountDetail.init(thisUI, account) ;
              thisUI.viewStack.push(uiDetail) ;
            }
          },
          { label: res('UIAccountList.field.type'), field: 'type', toggled: true, filterable: true},
          { label: res('UIAccountList.field.email'), field: 'email', toggled: true, filterable: true}
        ],
        actions: [
          {
            icon: "edit", label: "ACL",
            onClick: function(thisUI, row) { 
              var account = thisUI.getItemOnCurrentPage(row) ;
              var uiAccountACL = thisUI.UIAccountACL.init(thisUI, account) ;
              thisUI.viewStack.push(uiAccountACL) ;
              console.log('onClick ACL') ;
            }
          },
          {
            icon: "delete", label: "Delete",
            onClick: function(thisUI, row) { 
              var account = thisUI.getItemOnCurrentPage(row) ;
              var deleted = 
                service.AccountService.deleteAccountByLoginId(account.loginId).data ;
              if(deleted) {
                thisUI.removeItemOnCurrentPage(row) ;
              }
            }
          }
        ]
      }
    },
    
    onRefresh: function() { 
      var result = service.AccountService.filterAccount(null).data ;
      var users = result.data ;
      this.setBeans(users) ;
      this.renderRows() ;
    },
    
    back: function(refresh) {
      if(refresh) this.onRefresh() ;
      this.viewStack.back() ;
    },
    
    init: function (viewStack) {
      this.viewStack = viewStack ;
      this.UIAccountDetail = new Account.UIAccountDetail() ;
      this.UIAccountACL = new UIAccountACL() ;
      var result = service.AccountService.filterAccount(null).data ;
      var users = result.data ;
      this.setBeans(users) ;
      return this ;
    }
  });
    
  var UIAccountsScreen = module.UIScreen.extend({
    initialize: function (options) {
    },

    activate: function() { 
      this.viewStack = new UIBreadcumbs({el: "#workspace"}) ;
      var aview = new UIAccountList().init(this.viewStack);
      this.viewStack.push(aview) ;
    },
    
    deactivate: function() { }
    
  });
  
  return UIAccountsScreen ;
});
