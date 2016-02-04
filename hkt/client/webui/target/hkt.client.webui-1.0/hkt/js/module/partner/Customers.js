define([
    'jquery', 
    'underscore', 
    'backbone', 
    'service/service',
    'module/Module',
    'ui/UITable',
    'ui/UIPopup',
    'ui/UIBreadcumbs',
    'module/partner/Customer',
    'module/partner/Point',
    'module/partner/Credit',
    'module/partner/CustomerFull',
    'module/account/Account'
], function($, _, Backbone, service, module, UITable, UIPopup, UIBreadcumbs , Customer, Point, Credit, CustomerFull,
    Account) {
  var UICustomerList = UITable.extend({
    label: 'Customers',
    config : {
      toolbar: {
        dflt: {
          actions: [
            { 
              action: "onNewCustomer", icon: "add", label: "New",
              onClick: function(thisUI) { 
                var  customer= {
                  loginId: "", organizationLoginId: ""
                };
                UIPopup.activate(new Customer.UICustomer().init(thisUI, customer, true)) ;
              } 
            },
            { 
              action: "onCustomerIsPerson", icon: "add", label: "Is Person",
              onClick: function(thisUI) { 
                var account = { 
                  type: 'USER', loginId: " ",
                  profiles: { 
                    basic: { firstName: '', lastName: '' }
                  }
                } ; 
                var aview = new CustomerFull.UICustomerFull().init(thisUI, account);
                thisUI.viewStack.push(aview) ;
              } 
            },
            { 
              action: "onCustomerIsEnterprise", icon: "add", label: "Is Enterprise",
              onClick: function(thisUI) { 
                var account = { 
                  type: 'ORGANIZATION', loginId: " ",
                  profiles: { 
                    basic: { name: '', fullName: '' }
                  }
                } ; 
                var aview = new CustomerFull.UICustomerFull().init(thisUI, account);
                thisUI.viewStack.push(aview) ;
              } 
            },
            { 
              action: "onRefresh", icon: "refresh", label: "Refresh", 
              onClick: function(thisUI) { 
                thisUI.onRefresh();
              } 
            }
          ]
        },
      },
      bean: {
        fields: [
          { label: 'Id', field: 'id', toggled: true, filterable: true},
          { label: 'Login Id', field: 'loginId', toggled: true, filterable: true,
            onClick: function(thisUI, row) {
              var customer = thisUI.getItemOnCurrentPage(row) ;
              var account = service.AccountService.getAccountByLoginId(customer.loginId).data;
              var uiDetail = thisUI.UIAccountDetail.init(thisUI, account, true) ;
              thisUI.viewStack.push(uiDetail) ;
            }
          },
          { label: 'Organization LoginId', field: 'organizationLoginId', toggled: true, filterable: true,
            onClick: function(thisUI, row) {
              var customer = thisUI.getItemOnCurrentPage(row) ;
              var account = service.AccountService.getAccountByLoginId(customer.organizationLoginId).data;
              var uiDetail = thisUI.UIAccountDetail.init(thisUI, account, true) ;
              thisUI.viewStack.push(uiDetail) ;
            }
          },
        ],
        actions: [
          {
            icon: "bars", label: "Point",
            onClick: function(thisUI, row) { 
              var customer = thisUI.getItemOnCurrentPage(row) ;
              var pointDetail = service.CustomerService.getPointDetailByCustomer(customer.id).data;
              if(pointDetail.point == null){
                var point = {loginId: customer.loginId , customerId: customer.id, point : 0};
                point = service.CustomerService.createPoint(customer, point).data;
                pointDetail.point = point;
              }
              var uiPointDetail = new Point.UIPointDetail().init(pointDetail) ;
              thisUI.viewStack.push(uiPointDetail) ;
            }
          },
          {
            icon: "grid", label: "Credit",
            onClick: function(thisUI, row) { 
              var customer = thisUI.getItemOnCurrentPage(row) ;
              var creditDetail = service.CustomerService.getCreditDetailByCustomer(customer.id).data;
              if(creditDetail.credit == null){
                var credit = {loginId: customer.loginId , customerId: customer.id, credit : 0};
                credit = service.CustomerService.createCredit(customer, credit).data;
                creditDetail.credit = credit;
              }
              var uiCreditDetail = new Credit.UICreditDetail().init(creditDetail) ;
              thisUI.viewStack.push(uiCreditDetail) ;
            }
          },
          {
            icon: "delete", label: "Delete",
            onClick: function(thisUI, row) { 
              var customer = thisUI.getItemOnCurrentPage(row) ;
              var deleted = 
                service.CustomerService.deleteCustomer(customer).data ;
              if(deleted) {
                thisUI.removeItemOnCurrentPage(row) ;
              }
            }
          }
        ]
      }
    },
    
    init: function (viewStack) {
      this.UIAccountDetail = new Account.UIAccountDetail() ;
      this.viewStack = viewStack ;
      this.UICustomer = new Customer.UICustomer() ;
      var result = service.CustomerService.searchCustomer(null).data ;
      var customers = result.data ;
      this.setBeans(customers) ;
      return this;
    },
    
    back: function(refresh) {
      if(refresh) this.onRefresh() ;
      this.viewStack.back() ;
    },
   
    onRefresh: function() { 
      var result = service.CustomerService.searchCustomer(null).data ;
      var Customers = result.data ;
      this.setBeans(Customers) ;
      this.renderRows() ;
    } 
  });
  
  var UICustomersScreen = module.UIScreen.extend({
    initialize: function (options) { },
    
    activate: function() { 
      this.viewStack = new UIBreadcumbs({el: "#workspace"}) ;
      var aview = new UICustomerList().init(this.viewStack);
      this.viewStack.push(aview) ;
    },
    
    deactivate: function() { },
  });
  return UICustomersScreen ;
});
