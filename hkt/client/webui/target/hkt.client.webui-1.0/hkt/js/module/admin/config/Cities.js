define([ 
  'service/service',
  'ui/UITable',
  'ui/UIPopup',
  'module/admin/config/City'
], function(service, UITable, UIPopup, City) {
  
  var UICityList = UITable.extend({
    label: "Cities",
      config : {
        toolbar: {
          dflt: {
            actions: [
              {
                action: "onNewCity", icon: "add", label: "New City", 
                onClick: function(thisUI) {
                  var city = { name : "" } ;
                  UIPopup.activate(new City.UICity().init(thisUI, city, true)) ;
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
          { label : 'Index', field : 'index', toggled : true, filterable : true, },
          { 
            label : 'Code', field : 'code', toggled : true, filterable : true,
            onClick: function(thisUI, row) {
              var city = thisUI.getItemOnCurrentPage(row) ;
              UIPopup.activate(new City.UICity().init(thisUI, city, false)) ;
            }
          },
          { label : 'Name City', field : 'name', toggled : true, filterable : true, },
          { label : 'Description', field : 'description', toggled : true, filterable : true, }
        ],
        actions: [
          {
            icon: "delete", label: "Delete",
            onClick: function(thisUI, row) {
                thisUI.removeItemOnCurrentPage(row) ;
                thisUI.cities.splice(thisUI.cities.length - 1, 1) ;
            }
          }
        ]
      }
    },
    
    saveCity: function (city) {
      this.cities[this.cities.length] = city ;
    },
    
    onRefresh: function() {
      this.setBeans(this.cities) ;
      this.renderRows() ;
    },
    
    init: function (cities) {
      this.cities = cities ;
      this.setBeans(cities) ;
      return this ;
    }
    
  });
  
  var Cities = {
    UICityList : UICityList
  }
  
  return Cities ;
});
