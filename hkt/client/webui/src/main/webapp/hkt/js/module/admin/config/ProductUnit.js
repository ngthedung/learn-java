define([ 
  'service/service',
  'ui/UIBean',
  'ui/UIPopup',
], function( service, UIBean, UIPopup ) {
  
  var UIProductUnit = UIBean.extend({
    label: "ProductUnit",
    config: {
      beans: {
        productUnit: {
          label: 'ProductUnit',
          fields: [
            { field: "code", label: "Code" },
            { field: "name", label: "Name" },
            { field: "priority", label: "Priority", defaultValue: 0,
              validator: { name: 'number', from: 0, errorMsg: "Expect a number" } },
            { field: "rate", label: "Rate", defaultValue: 0,
              validator: { name: 'number', from: 0, errorMsg: "Expect a number" } },
            { field: "description", label: "Description", textarea: {} }
          ],
          edit: {
            disable: true ,
            actions: [
              {
                action:'save', label: "Save", icon: "check",
                onClick: function(thisUI) { 
                  var productUnit = thisUI.getBean('productUnit') ;
                  if(thisUI.isNew) thisUI.UIParent.saveProductUnit(productUnit) ;
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
    
    init: function(UIParent, productUnit, isNew) {
      this.UIParent = UIParent ;
      this.bind('productUnit', productUnit, true) ;
      this.isNew = isNew
      var productUnitConfig = this.getBeanConfig('productUnit') ;
      
      productUnitConfig.disableEditAction(false) ;
      productUnitConfig.disableField('code', !isNew) ;
      
      this.getBeanState('productUnit').editMode = true ;
      
      return this ;
    },
  });
  
  var ProductUnit = {
      UIProductUnit : UIProductUnit
  };
  
  return ProductUnit ;
});
