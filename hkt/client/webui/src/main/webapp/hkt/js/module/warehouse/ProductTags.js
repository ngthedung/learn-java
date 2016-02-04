define([
  'service/service',
  'ui/UIPopup',
  'ui/UITable',
  'ui/UIBean',
  'module/Module', 
  'ui/UIBreadcumbs',
  'ui/UICollapsible',
], function(service, UIPopup, UITable, UIBean,module,UIBreadcumbs,UICollapsible) {
  
  var UIProductTag = UIBean.extend({
    label: 'Product Tag Information',
    config:  {
      beans: {
        ProductTag: {
          label : "Product Tag Information",
          fields : [
            { field : "label", label : "Label" },
            { field : "tag", label : "Tag" },
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
                  
                  var ProductTag = thisUI.getBean('ProductTag') ;
                  service.ProductService.saveProductTag(ProductTag);
                  thisUI.UIParent.onRefresh();
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

    init: function(UIParent, ProductTag, isNew) {
      this.UIParent = UIParent ;
      this.bind('ProductTag', ProductTag, true) ;
      
      var ProductTagConfig = this.getBeanConfig('ProductTag') ;
      ProductTagConfig.disableEditAction(false) ;
      this.getBeanState('ProductTag').editMode = true ;
      
      ProductTagConfig.disableField('productCode', !isNew) ;
      
      return this ;
    }
    
  });
  
  var UIProductTagList = UITable.extend({
    label: "ProductTags",
      config : {
        toolbar: {
          dflt: {
            actions: [
              {
                action: "onNewProductTag", icon: "add", label: "New", 
                onClick: function(thisUI) {
                  var ProductTag = { label : "", tag : "" } ;
                  UIPopup.activate(new UIProductTag().init(thisUI, ProductTag, true)) ;
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
          { label : 'Label', field : 'label', toggled : true, filterable : true, },
          { label : 'Tag', field : 'tag', toggled : true, filterable : true,
            onClick: function(thisUI, row) {
              var ProductTag = thisUI.getItemOnCurrentPage(row) ;
              UIPopup.activate(new UIProductTag().init(thisUI, ProductTag, false)) ;
              thisUI.onEditBean(row) ;
            }
          },
        ],
        actions: [
          {
            icon: "delete", label: "Delete",
            onClick: function(thisUI, row) {
              var productTag = thisUI.getItemOnCurrentPage(row) ;
              var deleted = service.ProductService.deleteProductTag(productTag);
              if(deleted) {
                thisUI.removeItemOnCurrentPage(row) ;
              }
            }
          }
        ]
      }
    },
    
    onRefresh: function() {
      var productTags = service.ProductService.getProductTags().data ;
      this.setBeans(productTags) ;
      this.renderRows() ;
    },

    total:function(){
      return this.to;
    },
    
    init: function () {
      var productTags = service.ProductService.getProductTags().data ;
      this.setBeans(productTags) ;
      return this ;
    }
    
  });
  
  var UIProductsScreen = module.UIScreen.extend({
	    initialize: function (options) { },
	    
	    activate: function() {
	      this.viewStack = new UIBreadcumbs({el: "#workspace"}) ;
	      var aview = new UIProductTagList().init() ;
	      this.viewStack.push(aview) ;
	    },
	    
	    deactivate: function() { }
	  });
	  
	  return UIProductsScreen ;
});
