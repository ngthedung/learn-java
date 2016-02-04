define([ 
  'service/service',
  'ui/UIBean',
  'ui/UIPopup',
], function( service, UIBean, UIPopup ) {
  
  var UITax = UIBean.extend({
    label: "Tax",
    config: {
      beans: {
        tax: {
          label: 'Tax',
          fields: [
            { field: "name", label: "Name" },
            { field: "percent", label: "Percent" },
            { field: "description", label: "Description", type: "textarea" }
          ],
          edit: {
            disable: true ,
            actions: [
              {
                action:'save', label: "Save", icon: "check",
                onClick: function(thisUI) { 
                  var tax = thisUI.getBean('tax') ;
                  service.ProductService.saveTax(tax) ;
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
    
    init: function(UIParent, tax, isNew) {
      this.UIParent = UIParent ;
      this.bind('tax', tax, true) ;
      
      var taxConfig = this.getBeanConfig('tax') ;
      
      taxConfig.disableEditAction(false) ;
      this.getBeanState('tax').editMode = true ;
      
      return this ;
    },
  });
  
  var Tax = {
    UITax : UITax
  };
  
  return Tax ;
});
