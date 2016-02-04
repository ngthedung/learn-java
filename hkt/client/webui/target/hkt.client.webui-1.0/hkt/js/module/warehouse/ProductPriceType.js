define([ 
  'service/service',
  'ui/UIBean',
  'ui/UIPopup',
], function(service, UIBean, UIPopup) {
  
  var UIProductPriceType = UIBean.extend({
    label: "Product Price Type",
    config: {
      beans: {
        productPriceType: {
          label: 'Product Price Type',
          fields: [
            { field: "type", label: "Type", required: true, validator: {name: "empty"},  },
            { field: "organizationLoginId", label: "Org LoginId", required: true, validator: {name: "empty"},
              custom: {
                getDisplay: function(bean) {
                  return bean.organizationLoginId == null ? null : bean.organizationLoginId;
                },
                set: function(bean, obj) { bean.organizationLoginId = obj ; },
                
                autocomplete: {
                  search: function(val) {
                    var accounts = service.AccountService.findAccountByLoginIdOrg(val).data ;
                    var result = [] ;
                    for(var i = 0; i < accounts.length; i++) {
                      var account = accounts[i] ;
                      result[i] = {value: account.loginId, label: account.loginId + '(' + account.email + ')'} ;
                    }
                    return result ;
                  }
                }
              }
            },
            { field: "description", label: "Description", type: "textarea" },
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
                  var productPriceType = thisUI.getBean('productPriceType') ;
                  service.ProductService.saveProductPriceType(productPriceType) ;
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
        },
      },
    },
    
    init: function(UIParent, productPriceType, isNew) {
      this.UIParent = UIParent ;
      this.bind('productPriceType', productPriceType, true) ;
      
      var productPriceTypeConfig = this.getBeanConfig('productPriceType') ;
      
      productPriceTypeConfig.disableEditAction(false) ;
      this.getBeanState('productPriceType').editMode = true ;
      
      productPriceTypeConfig.disableField('organizationLoginId', !isNew) ;
      return this ;
    },
    
    initViewOnly: function(productPriceType) {
      this.bind('productPriceType', productPriceType) ;
      this.getBeanState('productPriceType').readOnly = true ;
      return this ;
    },
    
  });
  
  var ProductPriceType = {
    UIProductPriceType : UIProductPriceType
  };
  
  return ProductPriceType ;
});
