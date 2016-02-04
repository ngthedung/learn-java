define([
  'jquery',
  'service/Server',
  'service/ServerService',
  'service/AccountService',
  'service/CMSService',
  'service/HRService',
  'service/WarehouseService',
  'service/SchoolService',
  'service/AccountingService',
  'service/CustomerService',
  'service/SupplierService',
  'service/PromotionService',
  'service/RestaurantService',
  'service/LocaleService',
  'service/ProductService',
  'service/GenericOptionService'
], function($, Server, ServerService, AccountService, CMSService, HRService, WarehouseService, 
    SchoolService, AccountingService, CustomerService, SupplierService, PromotionService, 
    RestaurantService, LocaleService, ProductService, GenericOptionService) {
  var service = {
    /**@type service.Server */
    Server: Server,
    
    /**@type service.ServerService */
    ServerService: ServerService,
    
    /**@type service.AccountService */
    AccountService: AccountService,
    
    /**@type service.AccountService */
    CMSService: CMSService,
    
    /**@type service.HRService */
    HRService: HRService,

    /**@type service.WarehouseService */
    WarehouseService: WarehouseService,

    /**@type service.SchoolService */
    SchoolService: SchoolService,
    
    /**@type service.AccountingService */
    AccountingService: AccountingService,
    
    /**@type service.CustomerService */
    CustomerService: CustomerService,
    
    /**@type service.SupplierService */
    SupplierService: SupplierService,
    
    /**@type service.PromotionService */
    PromotionService: PromotionService,
    
    /**@type service.RestaurantService */
    RestaurantService: RestaurantService,
    
    /**@type service.LocaleService */
    LocaleService: LocaleService,
    
    /**@type service.ProductService */
    ProductService: ProductService,
    
    /**@type service.GenericOptionService */
    GenericOptionService: GenericOptionService
  };

  return service ;
});
