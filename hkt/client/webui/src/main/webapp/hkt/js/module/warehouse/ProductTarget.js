define([
  'ui/UITable',
  'ui/UIBean',
  'ui/UIPopup',
], function(UITable, UIBean, UIPopup) {
  
  var UIProductTarget = UIBean.extend({
    label: 'Product Target',
    config: {
      beans: {
        productTarget: {
          label: 'Product Target',
          fields: [
            { 
              field: "productTargetType", label: "Type", 
              defaultValue: 'ByProductTag',
              select: {
                getOptions: function(field, bean) {
                  var options = [
                    {label: 'ByProductTag', value: 'ByProductTag'},
                    {label: 'ByProduct', value: 'ByProduct'}
                  ];
                  return options ;
                }
              }
            },
            {
              field: "productIdentifierId", label: "Product Identifier ID", required: true, validator: {name: "empty"}
            }
          ],
          edit: {
            disable: false , 
            actions: [
              {
                action:'save', label: "Save", icon: "check",
                onClick: function(thisUI) { 
                  if(!thisUI.validate()) {
                    thisUI.render() ;
                    return ;
                  }
                  var productTarget = thisUI.getBean('productTarget') ;
                  if(thisUI.isNew) thisUI.UIParent.save(productTarget) ;
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
    
    init: function(UIParent, productTarget, isNew) {
      this.isNew = isNew;
      this.UIParent = UIParent ;
      this.bind('productTarget', productTarget, true) ;
      if(isNew){ this.getBeanState('productTarget').editMode = true ; }
      return this ;
    }
  });
  
  var UIProductTargetList = UITable.extend({
    label: 'Promotion Product Target',
    config : {
      toolbar: {
        dflt: {
          actions: [
            {
              action: "onNewProductTarget", icon: "add", label: "New Product Target", 
              onClick: function(thisUI) { 
                var ProductTarget = { productTargetType : "", productIdentifierId : "" };
                UIPopup.activate(new UIProductTarget().init(thisUI, ProductTarget, true)) ;
              } 
            }
          ]
        }
      },
      bean: {
        fields: [
          { label : 'Product Type', field : 'productTargetType', toggled : true, filterable : true },
          { label : 'Product Identifier ID', field : 'productIdentifierId', toggled : true, filterable : true }
        ],
        actions: [
          {
            icon: "edit", label: "Edit Product Target",
            onClick: function (thisUI, row) { 
              var ProductTarget = thisUI.getItemOnCurrentPage(row) ;
              UIPopup.activate(new UIProductTarget().init(thisUI, ProductTarget, false)) ;
              thisUI.onEditBean(row) ;
            }
          },
          {
            icon: "delete", label: "Delete",
            onClick: function (thisUI, row) { 
              thisUI.markDeletedItemOnCurrentPage(row) ;
            }
          }
        ]
      }
    },
    
    save: function (productTarget) {
      this.listProductTarget[this.listProductTarget.length] = productTarget ; 
    },
    
    init: function(listProductTarget) {
      this.listProductTarget = listProductTarget;
      this.setBeans(this.listProductTarget) ;
      return this ;
    },
    
    onRefresh: function() { 
      this.setBeans(this.listProductTarget) ;
      this.renderRows() ;
    },
  });
  
  var ProductTarget = {
      UIProductTargetList: UIProductTargetList
  };
  
  return ProductTarget ;
});
