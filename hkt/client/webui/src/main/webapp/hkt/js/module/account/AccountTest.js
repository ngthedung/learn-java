/**
 *  Design by Bui Trong Hieu
 */
define([
  'jquery', 
  'underscore', 
  'backbone',
  'i18n',
  'service/service',
  'ui/UITable',
  'ui/UIPopup',
  'ui/UICollapsible',
  'ui/UIBean',
  'util/DateTime',
  'module/account/AccountTestFull',
  ], function($, _, Backbone, i18n, service, UITable, UIPopup, UICollapsible, UIBean, DateTime,AccountTestFull) {  
  var res = i18n.getResource('module/account/account');
  
  var UIAccount = UIBean.extend({
	    label: res('UIAccount.label'),
	    config: {
	      beans: {
	        account: {
	          label: res('UIAccount.label'),
	          fields: [
	            {
	              field:  "type", label: res('UIAccount.field.type'),
	              select: {
	                getOptions: function(field, bean) {
	                  var options = [
	                    { label: res('UIAccount.field.type.user'), value: 'USER' },
	                    { label: res('UIAccount.field.type.organization'), value: 'ORGANIZATION'}
	                  ];
	                  return options ;
	                }
	              }
	            },
	            { 
	              field: "loginId",   label: res('UIAccount.field.loginId'), required: true,
	              validator: {name: "empty"} 
	            },
	            { 
	              field: "password",  label: res('UIAccount.field.password'), required: true,
	              validator: {name: "empty"} 
	            },
	            { 
	              field: "email", label: res('UIAccount.field.email'), required: true,
	              validator: {name: "email"} 
	            }
	          ],
	          edit: {
	            disable: true , 
	            actions: [
	              {
	                action:'save', label: res('UIAccount.action.save'), icon: "check",
	                onClick: function(thisUI, beanConfig, beanState) { 
	                  thisUI.save(beanConfig, beanState) ;
	                }
	              },
	              {
	                action:'saveAndExit', label: "Save And Exit", icon: "check",
	                onClick: function(thisUI, beanConfig, beanState) { 
	                  thisUI.saveAndExit(beanConfig, beanState) ;
	                }
	              },
	              {
	                action:'cancel', label: res('UIAccount.action.cancel'), icon: "back",
	                onClick: function(thisUI, beanConfig, beanState) { 
	                  UIPopup.closePopup() ;
	                }
	              }
	            ],
	          }
	        }
	      },
	    },
	    
	    save: function(beanConfig, beanState) {
	      if(!this.validate()) {
	        this.render() ;
	        return false ;
	      }
	      var account = beanState.bean ;
	      if (account.type == "USER") {
	        account.profiles.basic.firstName = account.loginId;
	        account.profiles.basic.lastName = account.loginId;
	      }
	      if (account.type == "ORGANIZATION") {
	        account.profiles.basic.name = account.loginId;
	        account.profiles.basic.fullName = account.loginId;
	      }
	      console.log(account);
	      service.AccountService.saveAccount(account) ;
	      this.restoreBeanState('account') ;
	      if(this.UIParent.onRefresh != null) {
	        this.UIParent.onRefresh() ;
	      }
	      return true ;
	    },
	    
	    saveAndExit: function(beanConfig, beanState) {
	      if(!this.save(beanConfig, beanState)) return ;
	      UIPopup.closePopup() ;
	    },
	    
	    init: function(UIParent, account, isConfig) {
	      this.UIParent = UIParent ;
	      this.bind('account', account) ;
	      if(isConfig){
	        var config = this.getBeanConfig('account') ;
	        config.disableEditAction(false) ;
	      }
	      this.getBeanState('account').editMode = true ;
	      return this ;
	    },
	    
	    initWithDetail: function(account, readOnly) {
	      this.bind('account', account) ;
	      var accountConfig = this.getBeanConfig('account') ;
	      accountConfig.disableField('type', true) ;
	      accountConfig.disableField('loginId', true) ;
	      this.setReadOnly(readOnly);
	      return this ;
	    },
	  });
  
  var UIAccountDetail = UICollapsible.extend({
    label: res('UIAccountDetail.label'),
    config: {
      actions: [
        { 
          action: "back", label: res('UIAccountDetail.action.back'),
          onClick: function(thisUI) {
            if(thisUI.UIParent.back) thisUI.UIParent.back(false) ; 
          }
        },
        {
          action: "save", label: res('UIAccountDetail.action.save'),
          onClick: function(thisUI) {
            thisUI.commitChange() ;
            var account = thisUI.account;
            service.AccountService.saveAccount(account) ;
            if(thisUI.UIParent.back) thisUI.UIParent.back(false) ;
          }
        }
      ]
    },
    
    init: function(UIParent, account, isNew) {
      this.clear();
      this.UIParent = UIParent ;
      this.account = account ;
      
      if(account.profiles == null) account.profiles = {} ;
      if(account.contacts == null) account.contacts = [] ;
      this.add(new UIAccount().initWithDetail(account, isNew)) ;
      if(account.type == "USER") {
        if(account.profiles.basic == null) account.profiles.basic = {} ;
        if(account.profiles.userRelationships == null) account.profiles.userRelationships = [] ;
        if(account.profiles.userEducations == null) account.profiles.userEducations = [] ;
        if(account.profiles.userWorks == null) account.profiles.userWorks = [] ;

        this.add(new AccountTestFull.UIUserBasic().init(account.profiles.basic, isNew)) ;
        this.UIUserEducation = new AccountTestFull.UIUserEducation().init(account);
        this.add(this.UIUserEducation, true) ;
        this.UIUserWork = new AccountTestFull.UIUserWork().init(account);
        this.add(this.UIUserWork, true) ;
        this.UIUserRelationship = new AccountTestFull.UIUserRelationship().init(account);
        this.add(this.UIUserRelationship, true) ;
        this.commitChange = function() {
          this.UIUserEducation.commitChange() ;
          this.UIUserWork.commitChange() ;
          this.UIUserRelationship.commitChange() ;
          this.UIContact.commitChange() ;
        };
      } else {
        if(account.profiles.basic == null) account.profiles.basic = {} ;
        if(account.profiles.businessRegistrations == null) {
          account.profiles.businessRegistrations = [] ;
        }
        this.add(new AccountTestFull.UIOrganizationBasic().init(account.profiles.basic, isNew)) ;
        this.UIBusinessRegistration = new AccountTestFull.UIBusinessRegistration().init(account);
        this.add(this.UIBusinessRegistration) ;
        this.commitChange = function() {
          this.UIBusinessRegistration.commitChange() ;
          this.UIContact.commitChange() ;
        };
      }
      this.UIContact = new AccountTestFull.UIContact().init(account);
      this.add(this.UIContact, true) ;
      return this ;
    },
  });
  
  var AccountTest = {
    UIAccount:       UIAccount,
    UIAccountDetail: UIAccountDetail
  };
  
  return AccountTest ;
});
