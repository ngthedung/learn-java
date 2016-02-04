define([
  'jquery', 
  'underscore', 
  'backbone',
  'module/Module',
  'ui/UIBreadcumbs',
  'ui/UITabbedPane',
  'module/admin/config/UIOption',
], function($, _, Backbone, module, UIBreadcumbs, UITabbedPane, UIOption) {
  
  var UIConfigs = UITabbedPane.extend({
    label: 'Configs',
    config: {
      tabs: [
        { 
          label: "ContributorRoles", name: "roles",
          onSelect: function(thisUI, tabConfig) {
            var uiTab = new UIOption().init("accounting", "AccountingService", "contributor_role", "ContributorRoles") ;
            thisUI.setSelectedTab(tabConfig.name, uiTab) ;
          }
        },
        { 
          label: "TypeInvoices", name: "typeInvoices",
          onSelect: function(thisUI, tabConfig) {
            var uiTab = new UIOption().init("accounting", "AccountingService", "type_invoice", "TypeInvoices") ;
            thisUI.setSelectedTab(tabConfig.name, uiTab) ;
          }
        }
      ]
    },
    
    init: function(viewStack) {
      this.viewStack = viewStack ;
    }
  });
  
  var UIConfigScreen = module.UIScreen.extend({
    initialize: function (options) {
    },
    
    activate: function() { 
      this.viewStack = new UIBreadcumbs({el: "#workspace"}) ;
      var view = new UIConfigs() ;
      view.init(this.viewStack) ;
      this.viewStack.push(view) ;
    },
    
    deactivate: function() { }
    
  });
  
  return UIConfigScreen ;
});
