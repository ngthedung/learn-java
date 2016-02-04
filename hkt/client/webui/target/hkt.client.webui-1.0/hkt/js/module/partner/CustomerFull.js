define([
  'jquery', 
  'underscore', 
  'backbone',
  'module/Module',
  'ui/UIBreadcumbs',
  'service/service',
  'ui/UITable',
  'ui/UIPopup',
  'ui/UICollapsible',
  'ui/UIBean',
  'module/account/Account',
], function($, _, Backbone,module, UIBreadcumbs, service, UITable, UIPopup, UICollapsible, UIBean, Account) {  
  var UICustomer = UIBean.extend({
    label: "Customer",
    config: {
      beans: {
        customer: {
          label: 'Customer',
          fields: [
            { 
              field: "organizationLoginId",  label: "Organization Login Id",
              custom: {
                getDisplay: function(bean) {
                  return bean.organizationLoginId == null ? null : bean.organizationLoginId;
                },
                set: function(bean, obj) { bean.organizationLoginId = obj ;},
                
                autocomplete: {
                  search: function(val) {
                    var accounts = service.AccountService.findAccountByLoginIdOrg(val).data ;
                    var result = [] ;
                    for(var i = 0; i < accounts.length; i++) {
                      var account = accounts[i] ;
                      result[i] = { value: account.loginId, label: account.loginId + '(' + account.email + ')'} ;
                    }
                    return result ;
                  }
                }
              }
            }
          ],
          edit: {
            disable: true ,
            actions: [
            ],
          },
        }
      }
    },
    
    init: function(customer) {
      this.bind('customer', customer) ;
      var employeeConfig = this.getBeanConfig('customer') ;
      employeeConfig.disableEditAction(false) ;
      this.getBeanState('customer').editMode = true ;     
      return this ;
    },
    save: function(account){
      var customer = this.getBean('customer') ;
      customer.loginId = account.loginId;
      customer = service.CustomerService.saveCustomer(customer).data;
      return customer;
    }
  });

  var UICustomerFull = UICollapsible.extend({
    label: "Customer Information",
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
            thisUI.commitChange() ;
            var account = thisUI.account;
            
            if (account.type == "USER") {
              if ( account.profiles.basic.firstName == "" ) { account.profiles.basic.firstName = account.loginId ; }
              if ( account.profiles.basic.lastName == "" ) { account.profiles.basic.lastName = account.loginId ; }
            }
            if (account.type == "ORGANIZATION") {
              if ( account.profiles.basic.name == "" ) { account.profiles.basic.name = account.loginId ; }
              if ( account.profiles.basic.fullName == "" ) { account.profiles.basic.fullName = account.loginId ; }
            }
            account = service.AccountService.saveAccount(account).data ;
            thisUI.UICustomer.save(account);
            if(thisUI.UIParent.back) thisUI.UIParent.back(true) ;
            
          }
        }
      ]
    },
    
    init: function(UIParent, account) {
      this.clear() ;
      this.UIParent = UIParent;
      this.account = account ;
      if(account.profiles == null) account.profiles = {} ;
      if(account.contacts == null) account.contacts = [] ;
      
      this.add(new Account.UIAccount().init(this, account, false)) ;
      if(account.profiles.basic == null) account.profiles.basic = {} ;
      if(account.profiles.userRelationships == null) account.profiles.userRelationships = [] ;
      if(account.profiles.userEducations == null) account.profiles.userEducations = [] ;
      if(account.profiles.userWorks == null) account.profiles.userWorks = [] ;
      var customer = {};
      this.UICustomer = new UICustomer().init(customer);
      this.add(this.UICustomer) ;
      if(account.type == "USER") {
        this.add(new Account.UIUserBasic().init(account.profiles.basic, false)) ;
        this.UIUserEducation = new Account.UIUserEducation().init(account);
        this.add(this.UIUserEducation, true) ;
        this.UIUserWork = new Account.UIUserWork().init(account);
        this.add(this.UIUserWork, true) ;
        this.UIUserRelationship = new Account.UIUserRelationship().init(account);
        this.add(this.UIUserRelationship, true) ;
        this.commitChange = function() {
          this.UIUserEducation.commitChange() ;
          this.UIUserWork.commitChange() ;
          this.UIUserRelationship.commitChange() ;
          this.UIContact.commitChange() ;
        };
      }else {
        if(account.profiles.basic == null) account.profiles.basic = {} ;
        if(account.profiles.businessRegistrations == null) {
          account.profiles.businessRegistrations = [] ;
        }
        this.add(new Account.UIOrganizationBasic().init(account.profiles.basic, false)) ;
        this.UIBusinessRegistration = new Account.UIBusinessRegistration().init(account);
        this.add(this.UIBusinessRegistration) ;
        this.commitChange = function() {
          this.UIBusinessRegistration.commitChange() ;
          this.UIContact.commitChange() ;
        };
      }
     
      this.UIContact = new Account.UIContact().init(account);
      this.add(this.UIContact, true) ;
      return this ;
    },
  });
  
  var CustomerFull = {
      UICustomerFull: UICustomerFull,
    };
  
  return CustomerFull ;
});
