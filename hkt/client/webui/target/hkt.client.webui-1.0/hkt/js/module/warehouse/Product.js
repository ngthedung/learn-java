define([
  'service/service',
  'ui/UIBean',
  'ui/UITable',
  'ui/UICollapsible',
  'module/cms/NodeAttributes'
], function(service, UIBean, UITable, UICollapsible, NodeAttributes) {

  var UIProduct = UIBean.extend({
    label: "Product",
    config: {
      beans: {
        product: {
          label: 'Product',
          fields : [
            { field : "code", label : "Code" , required: true, validator: {name: "empty"}  },
            { field : "name", label : "Name", required: true, validator: {name: "empty"} },
            { field : "unit", label : "Unit", 
              select: {
                getOptions: function(field, bean) {
                  var options = service.LocaleService.getProductUnits().data ;
                  var result = [] ;
                  for(var i = 0; i < options.length; i++) {
                    var option = options[i] ;
                    result[i] = { label: option.name , value: option.code } ;
                  }
                  return result ;
                }
              },
            },
            { field : "maker", label : "Maker", required: true, validator: {name: "empty"} }
          ],
          edit: {
            disable: true,
            actions:[]
          }
        }
      }
    },
    
    init: function(product, isNew) {
      this.bind('product', product, true) ;
      var productConfig = this.getBeanConfig('product') ;
      productConfig.disableEditAction(false) ;
      
      this.getBeanState('product').editMode = isNew ;
      productConfig.disableField('code', !isNew) ;
      return this ;
    },
    
    initViewOnly: function(UIParent, product) {
      this.UIParent = UIParent ;
      this.bind('product', product, true) ;
      this.getBeanState('product').readOnly = true ;
      return this ;
    },
  });
  
  var UIProductTagList = UITable.extend({
    label: "ProductTags",
    config: {
      toolbar: {
        dflt: {
          actions: [
            {
              action: "onNew", icon: "add", label: "New", 
              onClick: function(thisTable) { 
                thisTable.onAddBean() ;
              } 
            }
          ]
        },
        filter: {
          fields: [
            { label: 'Label',   field: 'label', type: 'string', operator: 'EQUAL' },
          ],
          onFilter: function(thisTable, query) {
            console.log('onFilter');
          },
        }
      },
      
      bean: {
        label: 'Product Tag',
        fields: [
          { 
            field : "tag", label : "Tag", toggled: true, filterable: true,
            custom: {
              getDisplay: function(bean) {
                return bean.tag == null ? null : bean.tag ;
              },
              set: function(bean, obj) { bean.tag = obj ;},
              autocomplete: {
                search: function(val) {
                  var productTags = service.ProductService.findProductTag(val).data ;
                  var result = [] ;
                  for(var i = 0; i < productTags.length; i++) {
                    var productTag = productTags[i] ;
                    result[i] = {value: productTag.tag, label: productTag.label + ' (' + productTag.tag + ')'} ;
                  }
                  return result ;
                }
              }
            }
          }
        ],
        actions:[
          {
            icon: "delete", label: "Delete",
            onClick: function(thisTable, row) { 
              thisTable.removeItemOnCurrentPage(row) ;
            }
          }
        ]
      }
    },
    
    init: function (productTags) {
      this.setBeans(productTags) ;
      return this ;
    }
  });
  
  var UIProductDetail = UICollapsible.extend({
    label: "Product",
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
            if(!thisUI.UIProduct.validate()) {
              thisUI.render() ;
              return ;
            }
            thisUI.commitChange() ;
            var attributes = thisUI.UIAttribute.getBeans();
            var nodeAttributes = {};
            for (var i = 0; i < attributes.length; i++) {
              var attribute = attributes[i];
              nodeAttributes[attribute.name] =  attribute;
            }
            if(thisUI.isNew){ 
              var product = thisUI.productDetail.product;
              var cmsNode = {
                  node: {
                    name : product.code, path : "hkt/"+product.code, mimeType : "warehouse/products", parentId : -1, 
                    parentPath : "hkt"
                  },
                  attributes: nodeAttributes
                };
              thisUI.productDetail.cmsNode = cmsNode ; 
            }else {
              thisUI.productDetail.cmsNode.attributes = nodeAttributes;
            }
            service.ProductService.saveProductDetail(thisUI.productDetail) ;
            if(thisUI.UIParent.back) thisUI.UIParent.back(thisUI.isNew) ;
          }
        }
      ]
    },
    
    init: function(UIParent, productDetail, isNew, view) {
      this.isNew = isNew ;
      this.clear() ;
      this.UIParent = UIParent ;
      this.productDetail = productDetail;
      if (view) {
        this.UIProduct = new UIProduct().init(productDetail.product, isNew);
      } else {
        this.setActionHidden('save', true) ;
        this.UIProduct = new UIProduct().initViewOnly(UIParent, productDetail.product);
      }
      
      this.add(this.UIProduct) ;
      this.UIAttribute = new NodeAttributes().init(productDetail.cmsNode);
      if(productDetail.product.productTags==null){
        productDetail.product.productTags= [];
      }
      this.UIProductTagList = new UIProductTagList().init(productDetail.product.productTags);
      this.add(this.UIProductTagList) ;
      this.add(this.UIAttribute) ;
      this.commitChange = function() {
        this.UIProductTagList.commitChange() ;
        for(var i = 0; i<productDetail.product.productTags.length; i++ ){
          productDetail.product.productTags[i]= 
            service.ProductService.getProductTagByTag(productDetail.product.productTags[i].tag).data;
        }
      };
      return this ;
    }
  });
  
  
  
  var Product = {
    UIProduct : UIProduct,
    UIProductDetail : UIProductDetail
  };
  
  return Product ;
});
