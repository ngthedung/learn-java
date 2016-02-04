/**
 *  Design by Bui Trong Hieu
 */
define([ 
  'service/service',
  'ui/UIBean',
  'ui/UITable',
  'ui/UIPopup',
  'ui/UICollapsible',
  'util/DateTime',
], function(service, UIBean, UITable, UIPopup, UICollapsible, DateTime) {
  
  var UIRestaurantShift = UIBean.extend({
    label: "Restaurant Shift",
    config: {
      beans: {
        restaurantShift: {
          label: 'Restaurant Shift',
          fields: [
            { field: "code", label: "Code", required: true
            },    
            { field: "name", label: "*Name",  toggled: true, filterable: true,
            	validator: {name: "empty"} 
            },
            { field: "store", label: "Store", defaultValue: "other", toggled: true, filterable: true,
                select: {
                    getOptions: function(field, bean) {
                      var optionGroup = service.GenericOptionService.getOptionGroup( 'restaurant', 'RestaurantService', 'store').data ;  
                      var options = optionGroup.options;
                      var result = [] ;
                      result[0] = {label: "" , value: ""};
                      for(var i = 0; i < options.length; i++) {
                        var option = options[i] ;
                        result[i+1] = { label: option.label , value: option.code } ;
                      }
                      return result ;
                    }
                  }
            },
            { field: "hourStart", label: "*Start", toggled: true, filterable: true,
            	validator: {name: "empty"},	
            	datepicker: { 
                     getDate: function(bean) {
                       if (bean.hourStart == null) { bean.hourStart = DateTime.getCurrentDate(); }
                       return DateTime.fromDateTimeToDDMMYYYY(bean.hourStart) ;
                     },
                     setDate: function(date, bean) {
                       bean.hourStart = DateTime.fromDDMMYYYYToDateTime(date) ;
                     },
                   } 
            },
            { field: "hourEnd", label: "*End", toggled: true, filterable: true,
//            	validator: {name: "empty"}	
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
                  var restaurantShift = thisUI.getBean('restaurantShift') ;
                  
                  service.RestaurantService.saveShift(restaurantShift) ;
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
    
    init: function(UIParent, shift, isNew) {
      this.UIParent = UIParent ;
      this.bind('restaurantShift', shift, true) ;
      var shiftConfig = this.getBeanConfig('restaurantShift') ;
      shiftConfig.disableEditAction(false) ;
      this.getBeanState('restaurantShift').editMode = true ;
      shiftConfig.disableField('code', true) ;
      return this ;
    },
    
    initWithDetail: function(shift, readOnly) {
      this.bind('restaurantShift', shift, true) ;
      this.setReadOnly(readOnly);
      return this ;
    },
  });
  
  var UIShiftDetail = UICollapsible.extend({
    label: "Shift Information",
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
            var shiftRestaurant = thisUI.shiftRestaurant;
            service.RestaurantService.saveShift(shiftRestaurant) ;
            if(thisUI.UIParent.back) thisUI.UIParent.back(false) ;
          }
        }
      ]
    },
        
    init: function(UIParent, shiftRestaurant, readOnly) {
      this.clear() ;
      
      this.UIParent = UIParent ;
      this.shiftRestaurant = shiftRestaurant ;
      this.add(new UIRestaurantShift().initWithDetail(shiftRestaurant, readOnly)) ;
      return this ;
    },
  });
  
  var Shift = {
    UIRestaurantShift : UIRestaurantShift,
    UIShiftDetail : UIShiftDetail
  }
  
  return Shift ;
});
