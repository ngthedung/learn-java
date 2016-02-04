define([
  'service/service',
  'module/Module',
  'ui/UITable',
  'ui/UIPopup',
  'ui/UIBreadcumbs',
  'ui/UICollapsible',
  'module/warehouse/Coupon',
], function(service, module, UITable, UIPopup, UIBreadcumbs, UICollapsible, Coupon) {
    
  var UICouponList = UITable.extend({
    label: "Coupons",
      config : {
        toolbar: {
          dflt: {
            actions: [
              {
                action: "onNewCoupon", icon: "add", label: "New", 
                onClick: function(thisUI) {
                  var coupon = {
                      code: "", name: "", total: ""
                  };
                  UIPopup.activate(new Coupon.UICoupon().init(thisUI, coupon, true)) ;
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
          filter: {
            fields: [
              { label: 'Search By Name', field: 'name', type: 'string', operator: 'LIKE' },
            ],
            onFilter: function(thisUI, query) {
              var result = service.PromotionService.searchCoupon(query).data ;
              var students = result.data ;
              thisUI.setBeans(students) ;
              thisUI.renderRows() ;
            },
          }
        },
      bean : {
        fields: [
          {
            label: 'Code', field: 'code', toggled: true, filterable: true,
            onClick: function(thisUI, row) {
              var coupon = thisUI.getItemOnCurrentPage(row) ;
              UIPopup.activate(new Coupon.UICoupon().init(thisUI, coupon, false)) ;
            }
          },
          { label: 'Name', field: 'name', toggled: true, filterable: true },
          { label: 'Start', field: 'dateForm', toggled: true, filterable: true },
          { label: 'End', field: 'dateTo', toggled: true, filterable: true },
          { label: 'Total', field: 'total', toggled: true, filterable: true },
          { label: 'Unit', field: 'currencyUnit', toggled: true, filterable: true },
          { label: 'Percent(%)', field: 'percent', toggled: true, filterable: true },
          { label: 'Quantity Use', field: 'quantityUse', toggled: true, filterable: true },
          { label: 'Description', field: 'description', toggled: true, filterable: true },
        ],
        actions: [
          {
            icon: "bars", label: "Registration Session",
            onClick: function(thisUI, row) { 
              var coupon = thisUI.getItemOnCurrentPage(row) ;
              UIPopup.activate(new Coupon.UICoupon().init(thisUI, coupon, false)) ;
            }
          },
          {
            icon: "delete", label: "Delete",
            onClick: function(thisUI, row) {
              var coupon = thisUI.getItemOnCurrentPage(row) ;
              var deleted =  service.PromotionService.deleteCoupon(coupon).data ;
              if(deleted) {
                thisUI.removeItemOnCurrentPage(row) ;
              }
            }
          }
        ]
      }
    },
    
    onRefresh: function() {
      var result = service.PromotionService.searchCoupon(null).data ;
      var coupons = result.data ;
      this.setBeans(coupons) ;
      this.renderRows() ;
    },
    
    back: function(refresh) {
      if (refresh) {
        this.onRefresh();
      }
      this.viewStack.back();
    },
    
    init: function (viewStack) {
      this.viewStack = viewStack ;
      var result = service.PromotionService.searchCoupon(null).data ;
      var coupons = result.data ;
      this.setBeans(coupons) ;
      return this ;
    }
    
  });
  
  
    
  var UICouponScreen = module.UIScreen.extend({
    initialize: function (options) { },
    
    activate: function() { 
      this.viewStack = new UIBreadcumbs({el: "#workspace"}) ;
      var aview = new UICouponList().init(this.viewStack);
      this.viewStack.push(aview) ;
    },
    
    deactivate: function() { }
    
  });
  
  return UICouponScreen ;
});
