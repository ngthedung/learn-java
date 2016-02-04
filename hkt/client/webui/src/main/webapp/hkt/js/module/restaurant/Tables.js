define([ 
  'service/service',
  'module/Module',
  'ui/UIBean',
  'ui/UITable',
  'ui/UIPopup',
  'ui/UIBreadcumbs',
  'module/restaurant/Table',
], function(service, module, UIBean, UITable, UIPopup, UIBreadcumbs, Table) {
  
  var UITableList = UITable.extend({
    label: "RestaurantTables",
      config : {
        toolbar: {
          dflt: {
            actions: [
              {
                action: "onNewTable", icon: "plus", label: "New", 
                onClick: function(thisUI) {
                	var today = new Date();
                	var codeString = moment(today).format('YYYYMMDDHHmmss') ;
                	var hour = moment(today).format('HH:mm') ;
                  var table = { code : codeString } ;
                  UIPopup.activate(new Table.UIRestaurantTable().init(thisUI, table, true)) ;
                } 
              },
              {
                action: "onRefresh", icon: "refresh", label: "Refresh",
                onClick: function(thisUI) { 
                  thisUI.onRefresh() ;
                }
              }
            ],
          },
        },
      bean : {
        fields: [
          { label : 'Code', field : 'code', toggled : true, filterable : true,
            onClick: function(thisUI, row) {
              var restaurantTable = thisUI.getItemOnCurrentPage(row) ;
              var locationName = restaurantTable.locatonCode;
              var locationGroup = service.RestaurantService.getLocations().data ;
              for(var j = 0; j < locationGroup.length; j++) {
                var location = locationGroup[j] ;
                if(location.name == locationName ) restaurantTable.locationCode = location.code;
              }
              UIPopup.activate(new Table.UIRestaurantTable().init(thisUI, restaurantTable, false)) ;
            }
          },
          { label : 'Name', field : 'name', toggled : true, filterable : true, },
          { label : 'Responsible Group', field : 'responsibleGroup', toggled : true, filterable : true, },
          { label : 'Location', field : 'locationCode', toggled : true, filterable : true },
          { label : 'Description', field : 'description', toggled : true, }
        ],
        actions: [
          {
            icon: "edit", label: "Edit",
            onClick: function(thisUI, row) {
              var table = thisUI.getItemOnCurrentPage(row) ;
              thisUI.UITableDetail.init(thisUI, table, false) ;
              thisUI.viewStack.push(thisUI.UITableDetail) ;
            }
          },
          {
            icon: "delete", label: "Delete",
            onClick: function(thisUI, row) {
              var restaurantTable = thisUI.getItemOnCurrentPage(row) ;
              var deleted = service.RestaurantService.deleteTable(restaurantTable).data ;
              if(deleted) {
                thisUI.removeItemOnCurrentPage(row) ;
              }
            }
          }
        ]
      }
    },
    
    onRefresh: function() {
      var tables = service.RestaurantService.getTables().data ;
      for( var i = 0; i< tables.length; i++ )
      {
      var locationCode = tables[i].locationCode;
      var locationGroup = service.RestaurantService.getLocations().data ;
      for(var j = 0; j < locationGroup.length; j++) {
        var location = locationGroup[j] ;
        if(location.code == locationCode ) tables[i].locationCode = location.name;
      }
    }
      this.setBeans(tables) ;
      this.renderRows() ;
    },
    
    back: function(refresh) {
      if(refresh) this.onRefresh() ;
      this.viewStack.back() ;
    },
    
    init: function (viewStack) {
      this.viewStack = viewStack ;
      this.UITableDetail = new Table.UITableDetail() ;
      var tables = service.RestaurantService.getTables().data ;
      for( var i = 0; i< tables.length; i++ )
      {
      var locationCode = tables[i].locationCode;
      var locationGroup = service.RestaurantService.getLocations().data ;
      for(var j = 0; j < locationGroup.length; j++) {
        var location = locationGroup[j] ;
        if(location.code == locationCode ) tables[i].locationCode = location.name;
      }
    }
      this.setBeans(tables) ;
      return this ;
    }
    
  });
  
  var UITables = module.UIScreen.extend({
    initialize: function (options) {
    },
    
    activate: function() { 
      this.viewStack = new UIBreadcumbs({el: "#workspace"}) ;
      var aview = new UITableList().init(this.viewStack);
      this.viewStack.push(aview) ;
    },
    
    deactivate: function() { }
    
  });
  
  return UITables ;
});
