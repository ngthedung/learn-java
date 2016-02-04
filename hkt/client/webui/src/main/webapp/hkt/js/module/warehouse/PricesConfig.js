define([ 
  'module/Module',
  'ui/UIBreadcumbs',
  'ui/UICollapsible',
  'module/warehouse/ProductPriceTypes',
  'module/warehouse/ProductTags',
], function(module, UIBreadcumbs, UICollapsible, ProductPriceTypes, ProductTags) {
  
  var UITagDetail = UICollapsible.extend({
    label: "Product Prices Config",
    config: {
      actions: [
      ]
    },
    back: function(refresh) {
      if(refresh) this.onRefresh() ;
      this.viewStack.back() ;
    },
    
    init: function(viewStack) {
      this.viewStack = viewStack ;
      this.UIProductPriceTypeList = new ProductPriceTypes.UIProductPriceTypeList().init(viewStack);
      this.add(this.UIProductPriceTypeList) ;

      this.ProductTagList = new ProductTags.UIProductTagList().init();
      this.add(this.ProductTagList) ;
      return this ; 
    }
  });
  
  var PricesConfig = module.UIScreen.extend({
    initialize: function (options) {
    },
    
    activate: function() { 
      this.viewStack = new UIBreadcumbs({el: "#workspace"}) ;
      var aview = new UITagDetail().init(this.viewStack);
      this.viewStack.push(aview) ;
    },
    
    deactivate: function() { }
    
  });
  
  return PricesConfig ;
});
