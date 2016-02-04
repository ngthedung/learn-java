define([
  'service/service',
  'ui/UIBean',
  'ui/UICollapsible',
  'module/warehouse/MenuItems',
  'util/DateTime',
], function(service, UIBean, UICollapsible, MenuItems, DateTime) {
  
  var UIMenu = UIBean.extend({
    label: 'Menu',
    config: {
      beans: {
        menu : {
          label: 'Menu',
          fields : [
            { 
              field : 'code', label : 'Code', required: true, validator: { name: 'empty' }
            },
            { 
              field : "name", label : "Name" },
            { field : "total", label : "Total" },
            { field : "currencyUnit", label : "Currency Unit",
              select: {
                getOptions: function(field, bean) {
                  var optionGroup = service.GenericOptionService.getOptionGroup(
                      'config', 'LocaleService', 'currency').data ;
                  var options = optionGroup.options;
                  var result = [] ;
                  for(var i = 0; i < options.length; i++) {
                    var option = options[i] ;
                    result[i] = { label: option.label , value: option.code } ;
                  }
                  return result ;
                }
              }
            },
            { 
              field : "status", label : "Status",
              defaultValue: 'Options',
              select: {
                getOptions: function(field, bean) {
                  var options = [
                     { label: 'Options', value: "Options" },
                     { label: 'Fixed', value: "Fixed" }
                  ];
                  return options ;
                }
              }
            },
            { field : "description", label : "Description", textarea: {} },
          ],
        }
      }
    },
    init: function(menu,isNew ,readOnly ) {
      this.bind('menu', menu, true) ;
      this.setReadOnly(readOnly);
      this.getBeanState('menu').editMode = isNew ;
      var menuConfig = this.getBeanConfig('menu') ;
//      menuConfig.disableEditAction(false) ;
      menuConfig.disableField('code', !isNew) ;
      return this ;
    },
    
    initViewOnly: function(UIParent, menu) {
      this.UIParent = UIParent ;
      this.bind('menu', menu) ;
      this.getBeanState('menu').readOnly = true ;
      return this ;
    }
    
  });
  
  var UIMenuDetail = UICollapsible.extend({
      label: "Menu",
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
              if(!thisUI.UIMenu.validate()) {
                thisUI.render() ;
                return ;
              }
              service.PromotionService.saveMenu(thisUI.menuDetail) ;
              if(thisUI.UIParent.back) thisUI.UIParent.back(true) ;
            }
          }
        ]
      },
      
      init: function(UIParent, menuDetail, isNew, readOnly) {
        this.isNew = isNew;
        this.clear() ;
        this.UIParent = UIParent ;
        if(readOnly) this.setActionHidden('save', true) ;
        
        this.menuDetail = menuDetail ;
        this.UIMenu = new UIMenu().init(menuDetail, isNew, readOnly);
        this.add(this.UIMenu) ;
        
        this.MenuItems = new MenuItems.UIMenuItemList().init(menuDetail.menuItems);
        this.add(this.MenuItems, true) ;
        
        return this ;
      }
  });
  
  var Menu = {
      UIMenuDetail: UIMenuDetail,
      UIMenu: UIMenu
    };
    
    return Menu ;
});
