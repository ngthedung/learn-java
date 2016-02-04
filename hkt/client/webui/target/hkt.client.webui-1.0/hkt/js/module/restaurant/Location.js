define([ 
  'service/service',
  'ui/UIBean',
  'ui/UITable',
  'ui/UIPopup',
  'ui/UICollapsible',
], function(service, UIBean, UITable, UIPopup, UICollapsible) {
  
  var UILocation = UIBean.extend({
    label: "Location",
    config: {
      beans: {
        location: {
          label: 'Location',
          fields: [
            { field: "code", label: "Code",},
            { field: "name", label: "Name" },
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
                  var location = thisUI.getBean('location') ;
                  service.RestaurantService.saveLocation(location) ;
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
    
    init: function(UIParent, location, isNew) {
      this.UIParent = UIParent ;
      this.bind('location', location, true) ;
      var locationConfig = this.getBeanConfig('location') ;
      locationConfig.disableEditAction(false) ;
      this.getBeanState('location').editMode = true ;
      locationConfig.disableField('code', true) ;
      return this ;
    },
    
    initWithDetail: function(table, readOnly) {
      this.bind('restaurantTable', table, true) ;
      this.setReadOnly(readOnly);
      return this ;
    },
  });
  
  var UILocationDetail = UICollapsible.extend({
    label: "Location Information",
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
            var location = thisUI.location;
            service.RestaurantService.saveLocation(location) ;
            if(thisUI.UIParent.back) thisUI.UIParent.back(false) ;
          }
        }
      ]
    },
    
    init: function(UIParent, location, readOnly) {
      this.clear() ;
      
      this.UIParent = UIParent ;
      this.location = location ;
      this.add(new UILocation().initWithDetail(location, readOnly)) ;
      return this ;
    },
  });
  
  var Location = {
    UILocation : UILocation,
    UILocationDetail : UILocationDetail
  }
  
  return Location ;
});
