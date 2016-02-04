define([
  'service/service',
  'ui/UICollapsible',
  'ui/UIBean',
  'module/partner/SuppliedProducts',
], function(service, UICollapsible, UIBean, SupplierProducts) {  
  var UISupplier = UIBean.extend({
    label: "Supplier",
    config: {
      beans: {
        supplier: {
          label: 'Supplier',
          fields: [
            { 
              field: "supplierLoginId",   label: "Login Id", required: true,
              validator: {name: "empty"}
            },
            { 
              field: "organizationLoginId",   label: "Organization Login Id", required: true,
              validator: {name: "empty"} 
            }
          ],
          edit: {
            disable: true , 
            actions: [],
          }
        }
      }
    },
    
    initViewOnly: function (Supplier) {
      this.bind('supplier', Supplier) ;
      this.setReadOnly(true) ;
      return this ;
    },
    
  });
  
  var UISupplierDetail = UICollapsible.extend({
    label: "Supplied Product",
    config: { },
    
    init: function(UIParent, Supplier) {
      this.clear() ;
      this.UIParent = UIParent ;
      this.Supplier = Supplier ;
      this.UISupplier = new UISupplier().initViewOnly(Supplier);
      this.add(this.UISupplier) ;
      
      this.SupplierProducts = new SupplierProducts ;
      var listSuppliedProduct = service.SupplierService.getSuppliedProductBySupplier(Supplier.id).data;
      this.SupplierProducts.init(UIParent.viewStack, Supplier, listSuppliedProduct) ;
      this.add(this.SupplierProducts) ;
      return this ;
    },
    
  });
  
  var SuppliedProduct = {
      UISupplier:       UISupplier,
      UISupplierDetail: UISupplierDetail
  };
    
  return SuppliedProduct ;
});
