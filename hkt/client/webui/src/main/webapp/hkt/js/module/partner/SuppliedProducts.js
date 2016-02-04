define([
  'service/service',
  'ui/UITable',
  'ui/UIBean',
  'ui/UIPopup',
  'module/partner/PriceHistory',
  'module/account/Account'
], function(service, UITable, UIBean, UIPopup, PriceHistory, Account) {
  
  var UISupplierProduct = UIBean.extend({
    label: 'SupplierProduct Item',
    config: {
      beans: {
        supplierProduct: {
          label: 'item',
          fields : [
            { field : 'code', label : "Code" },
            {  field: "name", label: "Name" },
            { 
              field: "lastPrice", label: "Price", defaultValue: 0,
              validator: { 
                name: 'number', from: 0, errorMsg: "Expect a number > 0" 
              }
            },
            { field: "note",   label: "Note", textarea: {} }
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
                  var supplierProduct = thisUI.getBean('supplierProduct');
                  if(thisUI.isNew){
                    thisUI.UIParent.save(supplierProduct) ;
                  }else{
                    service.SupplierService.createSuppliedProduct(null, supplierProduct);
                  }
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
  
    init: function(SupplierProduct, UISupplierProductList, isNew) {
      this.isNew = isNew ;
      this.UIParent = UISupplierProductList ;
      this.bind('supplierProduct', SupplierProduct) ;
      if(this.isNew){ 
        this.getBeanState('supplierProduct').editMode = true ; 
      }
      var itemConfig = this.getBeanConfig('supplierProduct') ;
      itemConfig.disableField('code', !this.isNew) ;
      return this ;
    },
  });
  
  var UISupplierProductList = UITable.extend({
    label: 'Supplier Products',
    
    config : {
      toolbar: {
        dflt: {
          actions: [
            {
              icon: "add", label: "New", 
              onClick: function(thisUI, row) { 
                var supplierProduct = {
                    code: "", name: "", lastPrice: "", note: ""
                };
                UIPopup.activate(new UISupplierProduct().init(supplierProduct, thisUI, true)) ;
              } 
            }
          ]
        }
      },
      bean: {
        fields: [
           { label : 'Login Id', field : 'supplierLoginId', toggled : true, filterable : true,
             onClick: function(thisUI, row) {
               var supplierProduct = thisUI.getItemOnCurrentPage(row) ;
               var account = service.AccountService.getAccountByLoginId(supplierProduct.supplierLoginId).data;
               var uiDetail = thisUI.UIAccountDetail.init(thisUI, account, true) ;
               thisUI.viewStack.push(uiDetail) ;
             }
           },
           { label : 'Code', field : 'code', toggled : true, filterable : true },
           { label : 'Name', field : 'name', toggled : true, filterable : true },
           { label : 'Price', field : 'lastPrice', toggled : true },
           { label : 'Note', field : 'note', toggled : true },
        ],
        actions: [
          {
            icon: "edit", label: "Edit",
            onClick: function(thisUI, row) {
              var supplierProduct = thisUI.getItemOnCurrentPage(row) ;
              UIPopup.activate(new UISupplierProduct().init(supplierProduct, thisUI, true)) ;
            }
          },
          {
            icon: "bars", label: "Price History",
            onClick: function(thisUI, row) { 
              var supplierProduct = thisUI.getItemOnCurrentPage(row) ;
              var priceHistory = new PriceHistory.UISuppliedProductDetail().init(thisUI, supplierProduct) ;
              thisUI.viewStack.push(priceHistory) ;
            }
          },
          {
            icon: "delete", label: "Delete",
            onClick: function(thisUI, row) { 
              var supplierProduct = thisUI.getItemOnCurrentPage(row) ;
              thisUI.removeItemOnCurrentPage(row) ;
              service.SupplierService.deleteSuppliedProduct(supplierProduct) ;
            }
          }
        ]
      }
    },
    
    save: function (supplierProduct) {
      service.SupplierService.createSuppliedProduct(this.Supplier, supplierProduct) ;
    },
    
    updateSuppliedProduct: function(supplierProduct) {
      service.SupplierService.updateSuppliedProduct(supplierProduct);
    },
    
    init: function(viewStack, Supplier, listSupplierProduct) {
      this.setBeans(listSupplierProduct) ;
      this.Supplier = Supplier ;
      this.viewStack = viewStack ;
      this.UIAccountDetail = new Account.UIAccountDetail() ;
      this.renderRows();
      return this ;
    },

    back: function(refresh) {
      if(refresh) this.onRefresh() ;
      this.viewStack.back() ;
    },
    
    onRefresh: function() { 
      var listSupplierProduct = service.SupplierService.getSuppliedProductBySupplier(this.Supplier.id).data;
      this.setBeans(listSupplierProduct) ;
      this.renderRows();
    },
    
  });
  
  return UISupplierProductList ;
});
