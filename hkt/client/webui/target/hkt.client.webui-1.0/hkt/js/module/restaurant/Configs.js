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
          label: "Locations", name: "locations",
          onSelect: function(thisUI, tabConfig) {
            // TODO : Location
          }
        },
        { 
          label: "Stores", name: "stores",
          onSelect: function(thisUI, tabConfig) {
            var uiTab = new UIOption().init("restaurant", "RestaurantService", "store", "Store") ;
            thisUI.setSelectedTab(tabConfig.name, uiTab) ;
          }
        },
        { 
          label: "The Cashier", name: "theCashier",
          onSelect: function(thisUI, tabConfig) {
            var uiTab = new UIOption().init("restaurant", "RestaurantService", "the-cashier", "The Cashier") ;
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
