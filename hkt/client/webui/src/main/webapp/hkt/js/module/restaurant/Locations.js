define([ 
  'service/service',
  'module/Module',
  'ui/UIBean',
  'ui/UITable',
  'ui/UIPopup',
  'ui/UIBreadcumbs',
  'module/restaurant/Location',
], function(service, module, UIBean, UITable, UIPopup, UIBreadcumbs, Location) {
  
  var UILocationList = UITable.extend({
    label: "Locations",
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
                  var location = { code : codeString } ;
                  UIPopup.activate(new Location.UILocation().init(thisUI, location, true)) ;
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
              var location = thisUI.getItemOnCurrentPage(row) ;
              UIPopup.activate(new Location.UILocation().init(thisUI, location, false)) ;
            }
          },
          { label : 'Name', field : 'name', toggled : true, filterable : true, },
          { label : 'Description', field : 'description', toggled : true, }
        ],
        actions: [
          {
            icon: "edit", label: "Edit",
            onClick: function(thisUI, row) {
              var location = thisUI.getItemOnCurrentPage(row) ;
              thisUI.UILocationDetail.init(thisUI, location, false) ;
              thisUI.viewStack.push(thisUI.UILocationDetail) ;
            }
          },
          {
            icon: "delete", label: "Delete",
            onClick: function(thisUI, row) {
              var location = thisUI.getItemOnCurrentPage(row) ;
              var deleted = service.RestaurantService.deleteLocation(location).data ;
              if(deleted) {
                thisUI.removeItemOnCurrentPage(row) ;
              }
            }
          }
        ]
      }
    },
    
    onRefresh: function() {
      var locations = service.RestaurantService.getLocations().data ;
      this.setBeans(locations) ;
      this.renderRows() ;
    },
    
    back: function(refresh) {
      if(refresh) this.onRefresh() ;
      this.viewStack.back() ;
    },
    
    init: function (viewStack) {
      this.viewStack = viewStack ;
      this.UILocationDetail = new Location.UILocationDetail() ;
      var locations = service.RestaurantService.getLocations().data ;
      this.setBeans(locations) ;
      return this ;
    }
    
  });
  
  var UILocations = module.UIScreen.extend({
    initialize: function (options) {
    },
    
    activate: function() { 
      this.viewStack = new UIBreadcumbs({el: "#workspace"}) ;
      var aview = new UILocationList().init(this.viewStack);
      this.viewStack.push(aview) ;
    },
    
    deactivate: function() { }
    
  });
  
  return UILocations ;
});
