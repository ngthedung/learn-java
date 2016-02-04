define([
  'service/service',
  'ui/UICollapsible',
  'ui/UIBean',
  'module/partner/PriceHistories'
], function(service, UICollapsible, UIBean, PriceHistories ) {  
  var UISupplierProduct = UIBean.extend({
    label: "Supplied Product",
    config: {
      beans: {
        suppliedProduct: {
          label: 'SuppliedProduct',
          fields: [
            { field: "supplierLoginId",   label: "Login Id", required: true },
            { field: "code",   label: "Code", required: true },
            { field: "name",   label: "Name", required: true },
            { field: "lastPrice",   label: "Last Price", required: true },
            { field: "note",   label: "Note", required: true }
          ],
          edit: {
            disable: true , 
            actions: [],
          }
        }
      }
    },
    
    initViewOnly: function (SuppliedProduct) {
      this.bind('suppliedProduct', SuppliedProduct) ;
      this.setReadOnly(true) ;
      return this ;
    },
    
  });
  
  var UISuppliedProductDetail = UICollapsible.extend({
    label: "Supplied Product Detail",
    config: { },
    
    init: function(UIParent, SuppliedProduct) {
      this.clear() ;
      this.UIParent = UIParent ;
      this.SuppliedProduct = SuppliedProduct ;
      this.UISupplierProduct = new UISupplierProduct().initViewOnly(SuppliedProduct);
      this.add(this.UISupplierProduct) ;
      this.PriceHistories = new PriceHistories ;
      var listHistory = service.SupplierService.findPriceHistoryBySuppliedProductID(SuppliedProduct.id).data;
      this.PriceHistories.init(UIParent.viewStack, listHistory) ;
      this.add(this.PriceHistories) ;
      return this ;
    },
    
  });
  
  var PriceHistory = {
      UISupplierProduct:       UISupplierProduct,
      UISuppliedProductDetail: UISuppliedProductDetail
  };
    
  return PriceHistory ;
});
