define([ 
  'service/service',
  'ui/UIBean',
  'ui/UITable',
  'ui/UIPopup',
  'ui/UICollapsible',
], function(service, UIBean, UITable, UIPopup, UICollapsible) {
  
  var UIRestaurantTable = UIBean.extend({
    label: "Restaurant Table",
    config: {
      beans: {
        restaurantTable: {
          label: 'Restaurant Table',
          fields: [
            { field: "code", label: "Code",
              
            },
            { field: "name", label: "Name" },
            { 
              field: "responsibleGroup", label: "Responsible Group",
              custom: {
                getDisplay: function(bean) { return bean.responsibleGroup ; },
                set: function(bean, obj) { bean.responsibleGroup = obj ; },
                autocomplete: {
                  search: function(val, bean) {
                    var accountGroups = service.AccountService.findGroupByParentPathAndName(
                        bean.organizationLoginId,val).data ;
                    var result = [] ;
                    for(var i = 0; i < accountGroups.length; i++) {
                      var accGroup = accountGroups[i] ;
                      result[i] = {value: accGroup.path, label: accGroup.name + '(' + accGroup.path + ')'} ;
                    }
                    return result ;
                  }
                }
              }
            },
            { 
              field: "locationCode", label: "Location Code",
              select: {
                getOptions: function(field, bean) {
                  var locationGroup = service.RestaurantService.getLocations().data ;
                  var result = [] ;
                  for(var i = 0; i < locationGroup.length; i++) {
                    var location = locationGroup[i] ;
                    result[i] = { label: location.name , value: location.code } ;
                  }
                  return result ;
                }
              }
            },
            { field: "description", label: "Description", textarea: {} },
          ],
          edit: {
            disable: true ,
            actions: [
              {
                action:'save', label: "Save", icon: "check",
                onClick: function(thisUI) { 
                  if(!thisUI.validate()) {
                    thisUI.render() ;
                    return ;
                  }
                  var restaurantTable = thisUI.getBean('restaurantTable') ;
                  service.RestaurantService.saveTable(restaurantTable) ;
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
          },
        }
      }
    },
    
    init: function(UIParent, table, isNew) {
      this.UIParent = UIParent ;
      this.bind('restaurantTable', table, true) ;
      var tableConfig = this.getBeanConfig('restaurantTable') ;
      tableConfig.disableEditAction(false) ;
      this.getBeanState('restaurantTable').editMode = true ;
      tableConfig.disableField('code', true) ;
      return this ;
    },
    
    initWithDetail: function(table, readOnly) {
      this.bind('restaurantTable', table, true) ;
      this.setReadOnly(readOnly);
      return this ;
    },
  });
  
  var UITableDetail = UICollapsible.extend({
    label: "Table Information",
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
            var tableRestaurant = thisUI.tableRestaurant;
            service.RestaurantService.saveTable(tableRestaurant) ;
            if(thisUI.UIParent.back) thisUI.UIParent.back(false) ;
          }
        }
      ]
    },
    
    init: function(UIParent, tableRestaurant, readOnly) {
      this.clear() ;
      
      this.UIParent = UIParent ;
      this.tableRestaurant = tableRestaurant ;
      
      if(tableRestaurant.reservations == null) tableRestaurant.reservations = [] ;
      this.add(new UIRestaurantTable().initWithDetail(tableRestaurant, readOnly)) ;
      return this ;
    },
  });
  
  var Table = {
    UIRestaurantTable : UIRestaurantTable,
    UITableDetail : UITableDetail
  }
  
  return Table ;
});
