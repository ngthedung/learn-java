define([
  'jquery', 
  'underscore', 
  'backbone', 
  'moment',
  'service/service',
  'module/Module',
  'ui/UIReportTable',
  'ui/UIBreadcumbs',
  'module/accounting/report/InvoiceSummary',
  'module/accounting/report/InvoiceSalary',
  'module/accounting/report/InvoiceExpense',
  'module/accounting/report/InvoiceProduct',
  'module/accounting/report/InvoiceRestaurant',
  'module/accounting/report/InvoiceCashier',
  'module/accounting/report/WarehouseInventory'
], function($, _, Backbone, moment, service, module, UIReportTable, UIBreadcumbs, 
            InvoiceSummary, InvoiceSalary, InvoiceExpense, InvoiceProduct, InvoiceRestaurant, InvoiceCashier,
            WarehouseInventory ) {
  var  UIInvoiceReport = UIReportTable.extend({
    label: 'Invoice Report',
    
    reportGroups: [
      {
        label: "Invoice", 
        reports: [ InvoiceSummary, InvoiceExpense, InvoiceProduct] 
      },
      {
        label: "HR", 
        reports: [ InvoiceSalary ] 
      },
      {
        label: "Restaurant",
        reports: [ InvoiceRestaurant,InvoiceCashier]
      },
      {
        label: "Warehouse",
        reports: [ WarehouseInventory]
      }
    ],
  });
  
  var UIReportsScreen = module.UIScreen.extend({
    initialize: function (options) { },
    
    activate: function() { 
      this.viewStack = new UIBreadcumbs({el: "#workspace"}) ;
      var aview = new UIInvoiceReport();
      this.viewStack.push(aview) ;
    },
    
    deactivate: function() { },
  });
  return UIReportsScreen ;
});
