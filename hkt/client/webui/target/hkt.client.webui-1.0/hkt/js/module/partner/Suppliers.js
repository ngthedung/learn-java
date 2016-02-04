define([
    'jquery', 
    'underscore', 
    'backbone', 
    'service/service',
    'module/Module',
    'ui/UITable',
    'ui/UIBreadcumbs',
    'ui/UIPopup',
    'module/partner/Supplier',
    'module/partner/SuppliedProduct',
    'module/account/Account',
    'module/partner/SupplierFull'
], function($, _, Backbone, service, module, UITable,UIBreadcumbs, UIPopup, Supplier, SuppliedProduct, Account,
    SupplierFull) {
  var UISupplierList = UITable.extend({
    label: 'Suppliers',
    config : {
      toolbar: {
        dflt: {
          actions: [
            { 
              action: "onNewSupplier", icon: "add", label: "New",
              onClick: function(thisUI) { 
                var  supplier= {
                    loginId: "", organizationLoginId: ""
                };
                UIPopup.activate(new Supplier.UISupplier().init(thisUI, supplier, true)) ;
              } 
            },
            { 
              action: "onSupplierIsPerson", icon: "add", label: "Is Person",
              onClick: function(thisUI) { 
                var account = { type: 'USER', loginId: " "} ; 
                var aview = new SupplierFull.UISupplierFull().init(thisUI, account);
                thisUI.viewStack.push(aview) ;
              } 
            },
            { 
              action: "onSupplierIsEnterprise", icon: "add", label: "Is Enterprise",
              onClick: function(thisUI) { 
                var account = { type: 'ORGANIZATION', loginId: " "} ; 
                var aview = new SupplierFull.UISupplierFull().init(thisUI, account);
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
          { label: 'Login Id', field: 'loginId', toggled: true, filterable: true,
            onClick: function(thisUI, row) {
              var supplier = thisUI.getItemOnCurrentPage(row) ;
              var account = service.AccountService.getAccountByLoginId(supplier.supplierLoginId).data;
              var uiDetail = thisUI.UIAccountDetail.init(thisUI, account, true) ;
              thisUI.viewStack.push(uiDetail) ;
            }
          },
          { label: 'Organization LoginId', field: 'organizationLoginId', toggled: true, filterable: true,
            onClick: function(thisUI, row) {
              var supplier = thisUI.getItemOnCurrentPage(row) ;
              var account = service.AccountService.getAccountByLoginId(supplier.organizationLoginId).data;
              var uiDetail = thisUI.UIAccountDetail.init(thisUI, account, true) ;
              thisUI.viewStack.push(uiDetail) ;
            }
          },
        ],
        actions: [
          {
            icon: "bars", label: "Supplied Product",
            onClick: function(thisUI, row) { 
              var supplier = thisUI.getItemOnCurrentPage(row) ;
              var uiSupplierProductDetail = new SuppliedProduct.UISupplierDetail().init(thisUI, supplier) ;
              thisUI.viewStack.push(uiSupplierProductDetail) ;
            }
          },
          {
            icon: "delete", label: "Delete",
            onClick: function(thisUI, row) { 
              var Supplier = thisUI.getItemOnCurrentPage(row) ;
              var deleted = 
                service.SupplierService.deleteSupplier(Supplier).data ;
              if(deleted) {
                thisUI.removeItemOnCurrentPage(row) ;
              }
            }
          }
        ]
      }
    },
    
    init: function (viewStack) {
      this.viewStack = viewStack ;
      this.UISupplier = new Supplier.UISupplier() ;
      var result = service.SupplierService.searchSupplier(null).data ;
      var Suppliers = result.data ;
      this.UIAccountDetail = new Account.UIAccountDetail() ;
      this.setBeans(Suppliers) ;
      return this;
    },
    
    back: function(refresh) {
      if(refresh) this.onRefresh() ;
      this.viewStack.back() ;
    },
   
    onRefresh: function() { 
      var result = service.SupplierService.searchSupplier(null).data ;
      var Suppliers = result.data ;
      this.setBeans(Suppliers) ;
      this.renderRows() ;
    } 
  });
  
  var UISuppliersScreen = module.UIScreen.extend({
    initialize: function (options) { },
    
    activate: function() { 
      this.viewStack = new UIBreadcumbs({el: "#workspace"}) ;
      var aview = new UISupplierList().init(this.viewStack);
      this.viewStack.push(aview) ;
    },
    
    deactivate: function() { },
  });
  return UISuppliersScreen ;
});
