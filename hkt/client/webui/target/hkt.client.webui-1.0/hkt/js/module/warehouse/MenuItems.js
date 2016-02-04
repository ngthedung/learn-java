define([
  'service/service',
  'ui/UITable',
  'ui/UIBean',
  'ui/UIPopup',
], function(service, UITable, UIBean, UIPopup) {
  
  var UIMenuItem = UIBean.extend({
    label: 'Menu Item',
    config: {
      beans: {
        menuItem: {
          label: 'Menu Item',
          fields: [
            { field : "name", label : "Name" },
            { 
              field : "groupType", label : "Group",
              defaultValue: 'Appetizer',
              select: {
                getOptions: function(field, bean) {                  
                  var options = [
                     { label: 'Appetizer', value: "Appetizer" },
                     { label: 'MainDishes', value: "MainDishes" },
                     { label: 'Dessert', value: "Dessert" }
                  ];
                  return options ;
                }
              }
            },
            { 
              field : "menuItemType", label : "Item Type",
              defaultValue: 'ByMenuItemRef',
              select: {
                getOptions: function(field, bean) {
                  var options = [
                     { label: 'ByMenuItemRef', value: "ByMenuItemRef" },
                     { label: 'ByProduct', value: "ByProduct" }
                  ];
                  return options ;
                }
              }
            },
            { field : "identifierId", label : "Product",  required: true, validator: {name: "empty"},
              custom: {
                getDisplay: function(bean) {
                  return bean.identifierId == null ? null : bean.identifierId;
                },
                set: function(bean, obj) { bean.identifierId = obj ; },
                autocomplete: {
                  search: function(val) {
                    var products = service.ProductService.findProductByCode(val).data ;
                    var result = [] ;
                    for(var i = 0; i < products.length; i++) {
                      var product = products[i] ;
                      result[i] = {value: product.code, label: product.code + ' (' + product.name + ')'} ;
                    }
                    return result ;
                  }
                }
              }
            },
            { field : "description", label : "Description", textarea: {} },
          ],
          edit: {
            disable: false , 
            actions: [
              {
                action:'save', label: "Save", icon: "check",
                onClick: function(thisUI) { 
                  if(!thisUI.validate()) {
                    thisUI.render() ;
                    return ;
                  }
                  var menuItem = thisUI.getBean('menuItem') ;
                  if(thisUI.isNew) thisUI.UIParent.save(menuItem) ;
                  if(thisUI.UIParent.onRefresh != null) {
                    thisUI.UIParent.onRefresh() ;
                  }
                  UIPopup.closePopup() ;
                }
              },
              {
                action:'cancel', label: "Cancel", icon: "back",
                onClick: function() { 
                  UIPopup.closePopup() ;
                }
              }
            ],
          }
        }
      }
    },
    
    init: function(UIParent, menuItem, isNew) {
      this.isNew = isNew;
      this.UIParent = UIParent ;
      this.bind('menuItem', menuItem, true) ;
      
      var menuConfig = this.getBeanConfig('menuItem') ;
      menuConfig.disableEditAction(false) ;
      this.getBeanState('menuItem').editMode = true ;
      
//      if(isNew){ this.getBeanState('menuItem').editMode = true ; }
      return this ;
    }
  });  
  
  var UIMenuItemList = UITable.extend({
    label: 'Menu Item List',
    config : {
      toolbar: {
        dflt: {
          actions: [
            {
              action: "onNewMenuItem", icon: "add", label: "New Menu Item", 
              onClick: function(thisUI) { 
                var menuItem = { name : "", description : "" };
                UIPopup.activate(new UIMenuItem().init(thisUI, menuItem, true)) ;
              } 
            }
          ]
        }
      },
      bean: {
        fields: [
          { label : 'Name', field : 'name', toggled : true, filterable : true },
          { label : 'Group', field : 'groupType', toggled : true, filterable : true },
          { label : 'Type', field : 'menuItemType', toggled : true, filterable : true },
          { label : 'identifierId', field : 'identifierId', toggled : true, filterable : true },
          { label : 'Description', field : 'description', toggled : true, filterable : true }
        ],
        actions: [
          {
            icon: "edit", label: "Menu Item",
            onClick: function (thisUI, row) { 
              var menuItem = thisUI.getItemOnCurrentPage(row) ;
              UIPopup.activate(new UIMenuItem().init(thisUI, menuItem, false)) ;
//              thisUI.onEditBean(row) ;
            }
          },
          {
            icon: "delete", label: "Delete",
            onClick: function (thisUI, row) { 
              thisUI.markDeletedItemOnCurrentPage(row) ;
            }
          }
        ]
      }
    },
    
    save: function (menuItem) {
      this.listMenuItem[this.listMenuItem.length] = menuItem ; 
    },
    
    init: function(listMenuItem) {
      this.listMenuItem = listMenuItem;
      this.setBeans(this.listMenuItem) ;
      return this ;
    },
    
    onRefresh: function() { 
      this.setBeans(this.listMenuItem) ;
      this.renderRows() ;
    },
  });
  
  var MenuItem = {
      UIMenuItemList: UIMenuItemList
  };
  
  return MenuItem ;
});
