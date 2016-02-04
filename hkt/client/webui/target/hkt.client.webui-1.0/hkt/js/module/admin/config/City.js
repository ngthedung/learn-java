define([ 
  'service/service',
  'ui/UIBean',
  'ui/UIPopup',
], function( service, UIBean, UIPopup ) {
  
  var UICity = UIBean.extend({
    label: "City",
    config: {
      beans: {
        city: {
          label: 'city',
          fields: [
            { field: "index", label: "Index" },
            { field: "code", label: "Code" },
            { field: "name", label: "Name" },
            { field: "description", label: "Description", textarea: {} }
          ],
          edit: {
            disable: true ,
            actions: [
              {
                action:'save', label: "Save", icon: "check",
                onClick: function(thisUI) { 
                  var city = thisUI.getBean('city') ;
                  if(thisUI.isNew) thisUI.UIParent.saveCity(city) ;
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
    
    init: function(UIParent, city, isNew) {
      this.UIParent = UIParent ;
      this.bind('city', city, true) ;
      this.isNew = isNew
      var cityConfig = this.getBeanConfig('city') ;
      
      cityConfig.disableEditAction(false) ;
      cityConfig.disableField('index', !isNew) ;
      cityConfig.disableField('code', !isNew) ;
      
      this.getBeanState('city').editMode = true ;
      
      return this ;
    },
  });
  
  var City = {
    UICity : UICity
  };
  
  return City ;
});
