define([
  'service/service',
  'ui/UIPopup',
  'ui/UIBean',
  'util/DateTime',
], function(service, UIPopup, UIBean, DateTime) {
  
  var UICoupon= UIBean.extend({
    label: "Coupon",
    config: {
      beans: {
        coupon: {
          label: 'Coupon',
          fields: [
            { field: "code", label: "Code" },
            { field: "name", label: "Name" },
            { field: "total", label: "Total" },
            { field : "currencyUnit", label : "Unit", 
              select: {
                getOptions: function(field, bean) {
                  var options = service.LocaleService.getCurrencies().data ;
                  var result = [] ;
                  for(var i = 0; i < options.length; i++) {
                    var option = options[i] ;
                    result[i] = { label: option.name , value: option.code } ;
                  }
                  return result ;
                }
              },
              validator: { name: "empty" },
            },
            { field: "percent", label: "Percent(%)" },
            { field: "quantityUse",   label: "Quantity Use"},
            { field: 'dateForm', label: "Start Date", toggled: true, filterable: true,
              datepicker: { 
                getDate: function(bean) {
                  if (bean.dateForm == null) { bean.dateForm = DateTime.getCurrentDate(); }
                  return DateTime.fromDateTimeToDDMMYYYY(bean.dateForm) ;
                },
                setDate: function(dateForm, bean) {
                  bean.dateForm = DateTime.fromDDMMYYYYToDateTime(dateForm) ;
                },
              } 
            },
            { field: 'dateTo', label: "End Date", toggled: true, filterable: true,
              datepicker: { 
                getDate: function(bean) {
                  if (bean.dateTo == null) { bean.dateTo = DateTime.getCurrentDate(); }
                  return DateTime.fromDateTimeToDDMMYYYY(bean.dateTo) ;
                },
                setDate: function(dateTo, bean) {
                  bean.dateTo = DateTime.fromDDMMYYYYToDateTime(dateTo) ;
                },
              } 
            },
            { field: "description", label: "Description", textarea: {}, toggled: true, filterable: true}
          ],
          edit: {
            disable: true , 
            actions: [
              {
                action:'save', label: "Save", icon: "check",
                onClick: function (thisUI) { 
                  if (!thisUI.validate()) {
                    thisUI.render() ;
                    return ;
                  }
                  var coupon = thisUI.getBean('coupon') ;
                  service.PromotionService.saveCoupon(coupon) ;
                  if (thisUI.UIParent.onRefresh != null) {
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
    
    init: function (UIParent, coupon, isNew) {
      this.UIParent = UIParent ;
      this.bind('coupon', coupon) ;
      
      var couponConfig = this.getBeanConfig('coupon') ;
      couponConfig.disableEditAction(false) ;
      this.getBeanState('coupon').editMode = true ;
      
      couponConfig.disableField('code', !isNew) ;
      return this ;
    },
    
    initViewOnly: function (UIParent, coupon) {
      this.UIParent = UIParent ;
      this.bind('coupon', coupon) ;
      this.getBeanState('coupon').readOnly = true ;
      return this ;
    }
  });
  
  var Coupon = {
    UICoupon : UICoupon
  };
  
  return Coupon;
});
