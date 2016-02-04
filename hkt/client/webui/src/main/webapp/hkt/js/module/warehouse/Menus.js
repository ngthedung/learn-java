define([
  'service/service',
  'module/Module',
  'ui/UIPopup',
  'ui/UITable',
  'ui/UIBreadcumbs',
  'ui/UICollapsible',
  'module/warehouse/Menu',
  'util/DateTime',
], function (service, module, UIPopup, UITable, UIBreadcumbs, UICollapsible, Menu, DateTime) {  

  var UIMenuList = UITable.extend({
    label: 'Menus',
    config : {
      toolbar: {
        dflt: {
          actions: [
            { 
              action: "onNewMenu", icon: "add", label: "New",
              onClick: function(thisUI) {
                var menu = {
                  name : "", code : "", menuItems : []
                };
                var uiDetail = new Menu.UIMenuDetail().init(thisUI, menu, true, false) ;
                thisUI.viewStack.push(uiDetail) ;
              } 
            },
            { 
              action: "onRefresh", icon: "refresh", label: "Refresh", 
              onClick: function(thisUI) {
                thisUI.onRefresh() ;
              } 
            }
          ]
        },
      },
      bean: {
        fields: [
          { label: 'Code', field: 'code', toggled: true, filterable: true },
          { label: 'Name', field: 'name', toggled: true, filterable: true },
          { label: 'Total', field: 'total', toggled: true, filterable: true },
          { label: 'Unit', field: 'currencyUnit', toggled: true, filterable: true },
          { label: 'Status', field: 'status', toggled: true, filterable: true },
          { label: 'Description', field: 'description' },
        ],
        actions: [
          {
            icon: "edit", label: "Edit Menu",
            onClick: function(thisUI, row) {
              var menu = thisUI.getItemOnCurrentPage(row) ;
              var uiDetail = new Menu.UIMenuDetail().init(thisUI, menu, true, false) ;
              thisUI.viewStack.push(uiDetail) ;
            }
          },
          {
            icon: "delete", label: "Delete",
            onClick: function(thisUI, row) { 
              var menu = thisUI.getItemOnCurrentPage(row) ;
              var deleted = service.PromotionService.deleteMenu(menu).data ;
              if (deleted) {
                thisUI.removeItemOnCurrentPage(row) ;
              }
            }
          }
        ]
      }
    },
    
    init: function (viewStack) {
      this.viewStack = viewStack ;
      this.UIMenuDetail = new Menu.UIMenuDetail() ;
      var menus = service.PromotionService.getAllMenus().data ;
      this.setBeans(menus) ;
      return this;
    },
   
    onRefresh: function() { 
      var menus = service.PromotionService.getAllMenus().data ;
      this.setBeans(menus) ;
      this.renderRows() ;
    },
    
    back: function(refresh) {
      if (refresh) {
        this.onRefresh() ;
      }
      this.viewStack.back() ;
    }
  });
  
  var UIMenuScreen = module.UIScreen.extend({
    initialize: function (options) { },
    
    activate: function() { 
      this.viewStack = new UIBreadcumbs({el: "#workspace"}) ;
      var aview = new UIMenuList().init(this.viewStack);
      this.viewStack.push(aview) ;
    },
    
    deactivate: function() { },
  });
  return UIMenuScreen ;
});
