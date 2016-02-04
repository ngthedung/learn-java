/**
 * Design by Bui Trong Hieu
 */
define([ 
  'service/service',
  'module/Module',
  'ui/UIBean',
  'ui/UITable',
  'ui/UIPopup',
  'ui/UIBreadcumbs',
  'module/restaurant/Shift',
], function(service, module, UIBean, UITable, UIPopup, UIBreadcumbs, Shift) {
  
  var UIShiftList = UITable.extend({
    label: "RestaurantShifts",
      config : {
        toolbar: {
          dflt: {
            actions: [
              {
                action: "onNewShift", icon: "plus", label: "New", 
                onClick: function(thisUI) {
                	var today = new Date();
                	var codeString = moment(today).format('YYYYMMDDHHmmss') ;
                	var hour = moment(today).format('HH:mm') ;
                  var shift = { code : codeString } ;
                  UIPopup.activate(new Shift.UIRestaurantShift().init(thisUI, shift, true)) ;
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
	              var code = thisUI.getItemOnCurrentPage(row) ;
	                var storeCode = code.store;
	                var optionGroup = service.GenericOptionService.getOptionGroup( 'restaurant', 'RestaurantService', 'store').data ;  
	                var options = optionGroup.options;
	                for(var j = 0; j < options.length; j++) {
	                  var option = options[j] ;
	                  if(option.label == storeCode ) code.store = option.code;
	                }
	              UIPopup.activate(new Shift.UIRestaurantShift().init(thisUI, code, false)) ;
	            }
		},
          { label : 'Name', field : 'name', toggled : true, filterable : true,
          },
          { label : 'Store', field : 'store', toggled : true, filterable : true, 
          },
          { label : 'Start', field : 'hourStart', toggled : true, filterable : true },
          { label : 'End', field : 'hourEnd', toggled : true, filterable : true },
          { label : 'Description', field : 'description', toggled : true, }
        ],
        actions: [
          {
            icon: "edit", label: "Edit",
            onClick: function(thisUI, row) {
              var code = thisUI.getItemOnCurrentPage(row) ;
              thisUI.UIShiftDetail.init(thisUI, code, false) ;
              thisUI.viewStack.push(thisUI.UIShiftDetail) ;
            }
          },
          {
            icon: "delete", label: "Delete",
            onClick: function(thisUI, row) {
              var restaurantShift = thisUI.getItemOnCurrentPage(row) ;
              var deleted = service.RestaurantService.deleteShift(restaurantShift).data ;
              if(deleted) {
                thisUI.removeItemOnCurrentPage(row) ;
              }
            }
          }
        ]
      }
    },
    
    onRefresh: function() {
      var shifts = service.RestaurantService.getAllShift().data ;
      for( var i = 0; i< shifts.length; i++ )
      {
      var storeCode = shifts[i].store;
      var optionGroup = service.GenericOptionService.getOptionGroup( 'restaurant', 'RestaurantService', 'store').data ;  
      var options = optionGroup.options;
      for(var j = 0; j < options.length; j++) {
        var option = options[j] ;
        if(option.code == storeCode ) shifts[i].store = option.label;
      }
    }
      this.setBeans(shifts) ;
      this.renderRows() ;
    },
    
    back: function(refresh) {
      if(refresh) this.onRefresh() ;
      this.viewStack.back() ;
    },
    
    init: function (viewStack) {
      var shifts = service.RestaurantService.getAllShift().data ;
      for( var i = 0; i< shifts.length; i++ )
      {
      var storeCode = shifts[i].store;
      var optionGroup = service.GenericOptionService.getOptionGroup( 'restaurant', 'RestaurantService', 'store').data ;  
      var options = optionGroup.options;
      for(var j = 0; j < options.length; j++) {
        var option = options[j] ;
        if(option.code == storeCode ) shifts[i].store = option.label;
      }
    }
      this.UIShiftDetail = new Shift.UIShiftDetail() ;
      this.setBeans(shifts) ;
      this.viewStack = viewStack ;
      return this ;
    }
  });
  
  var UIShifts = module.UIScreen.extend({
    initialize: function (options) {
    },
    
    activate: function() { 
      this.viewStack = new UIBreadcumbs({el: "#workspace"}) ;
      var aview = new UIShiftList().init(this.viewStack);
      this.viewStack.push(aview) ;
    },
    
    deactivate: function() { }
    
  });
  
  return UIShifts ;
});

