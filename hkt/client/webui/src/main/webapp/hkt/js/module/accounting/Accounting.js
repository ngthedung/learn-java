/**
 * Design by Bui Trong Hieu
 */

define([
  'jquery', 
  'underscore', 
  'backbone', 
  'moment',
  'service/service',
  'module/Module',
  'ui/UIReportTable',
  'ui/UIBreadcumbs',
  'module/accounting/accountings/Project'
], function($, _, Backbone, moment, service, module, UIReportTable, UIBreadcumbs, Project) {
 
	var  UIAccounting = UIReportTable.extend({
    label: 'Accounting',
    
    reportGroups: [
      {
        label: "Payment",
        reports: [ Project]
      }
    ],
  });
  
  var UIAccountingScreen = module.UIScreen.extend({
    initialize: function (options) { },
    
    activate: function() { 
      this.viewStack = new UIBreadcumbs({el: "#workspace"}) ;
      var aview = new UIAccounting();
      this.viewStack.push(aview) ;
    },
    
    deactivate: function() { },
  });
  return UIAccountingScreen ;
});
