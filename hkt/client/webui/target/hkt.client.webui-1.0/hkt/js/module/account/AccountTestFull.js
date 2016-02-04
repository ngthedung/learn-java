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
  'module/account/Account',
], function($, _, Backbone, i18n, service, UITable, UIPopup, UICollapsible, UIBean, DateTime, Account) {  
 
	var res = i18n.getResource('module/account/account');
  
  var UIUserBasic = UIBean.extend({
    label: res('UIUserBasic.label'),
    config: {
      beans: {
        basic: {
          label: 'Personal Information',
          fields: [
            { field: "firstName",   label: res('UIUserBasic.field.firstName')},
            { field: "lastName",   label: res('UIUserBasic.field.lastName')},
            { field: "gender",   label: res('UIUserBasic.field.gender'), defaultValue: "other", 
              select: {
                getOptions: function(field, bean) {
                  var optionGroup = service.GenericOptionService.getOptionGroup(
                      'config', 'LocaleService', 'genders').data ;
                  var options = optionGroup.options;
                  var result = [] ;
                  result[0] = {label: "" , value: ""};
                  for(var i = 0; i < options.length; i++) {
                    var option = options[i] ;
                    result[i+1] = { label: option.label , value: option.code } ;
                  }
                  return result ;
                }
              }
            },
            { field: "birthday", label: res('UIUserBasic.field.birthday'),
              datepicker: { 
                getDate: function(bean) { return DateTime.fromDateTimeToDDMMYYYY(bean.birthday) ; },
                setDate: function(date, bean) { bean.birthday = DateTime.fromDDMMYYYYToDateTime(date) ; },
              } 
            },
            { field: "avatar",   label: res('UIUserBasic.field.avatar')},
            { field: "weight",   label: res('UIUserBasic.field.weight'), 
              defaultValue: 0, validator: { name: 'number', from: 0, errorMsg: "Expect a number > 0" }},
            { field: "height",   label: res('UIUserBasic.field.height'), 
              defaultValue: 0, validator: { name: 'number', from: 0, errorMsg: "Expect a number > 0" }},
            { field: "personalId",   label: res('UIUserBasic.field.personalId')},
            { field: "maritalStatus",   label: res('UIUserBasic.field.maritalStatus'),
              select: {
                getOptions: function(field, bean) {
                  var optionGroup = service.GenericOptionService.getOptionGroup(
                      'config', 'LocaleService', 'marital_status').data ;
                  var options = optionGroup.options;
                  var result = [] ;
                  result[0] = {label: "" , value: ""};
                  for(var i = 0; i < options.length; i++) {
                    var option = options[i] ;
                    result[i+1] = { label: option.label , value: option.code } ;
                  }
                  return result ;
                }
              }
            },
            { field: "hobby",   label: res('UIUserBasic.field.hobby'), textarea: {}}
          ]
        }
      }
    },
    
    init: function(profile, readOnly) {
      this.bind('basic', profile, true) ; 
      this.setReadOnly(readOnly);
      return this ;
    }
  });
  
  var UIUserRelationship = UITable.extend({
    label: res('UIUserRelationship.label'),
    config: {
      toolbar: {
        dflt: {
          actions: [
            {
              action: "onNew", icon: "add", label: res('UIUserRelationship.action.onNew'), 
              onClick: function(thisTable) { 
                thisTable.onAddBean() ;
              } 
            }
          ]
        },
        filter: {
          fields: [
            { field: "relation",   label: res('UIUserRelationship.field.relation'), type: 'string', operator: 'LIKE'}
          ],
          onFilter: function(thisTable, query) {
            console.log('onFilter');
          },
        }
      },
      
      bean: {
        label: 'Relationships',
        fields: [
          { field: "relation", label: res('UIUserRelationship.field.relation'), toggled: true, filterable: true},
          { 
            field: "gender",   label: res('UIUserRelationship.field.gender'), toggled: true, filterable: true,
            select: {
              getOptions: function(field, bean) {
                var optionGroup = service.GenericOptionService.getOptionGroup(
                    'config', 'LocaleService', 'genders').data ;
                var options = optionGroup.options;
                var result = [] ;
                result[0] = {label: "" , value: ""};
                for(var i = 0; i < options.length; i++) {
                  var option = options[i] ;
                  result[i+1] = { label: option.label , value: option.code } ;
                }
                return result ;
              }
            }
          },
          { field: "firstName", label: res('UIUserRelationship.field.firstName'), toggled: true, filterable: true},
          { field: "lastName", label: res('UIUserRelationship.field.lastName'), toggled: true, filterable: true},
          { field: "birthday", label: res('UIUserRelationship.field.birthday'), toggled: true, filterable: true,
            datepicker: { 
              getDate: function(bean) {
                return DateTime.fromDateTimeToDDMMYYYY(bean.birthday) ;
              },
              setDate: function(date, bean) {
                bean.birthday = DateTime.fromDDMMYYYYToDateTime(date) ;
              },
            } 
          },
          { field: "occupation", label: res('UIUserRelationship.field.occupation'), toggled: true, filterable: true}
        ],
        actions:[
          {
            icon: "delete", label: res('UIUserRelationship.action.delete'),
            onClick: function(thisTable, row) { 
              thisTable.removeItemOnCurrentPage(row) ;
            }
          },
          {
            icon: "edit", label: res('UIUserRelationship.action.delete'),
            onClick: function(thisTable, row) { 
              thisTable.onEditBean(row) ;
            }
          }
        ]
      }
    },
    init: function(account) {
      this.account = account;
      this.setBeans(account.profiles.userRelationships) ;
      return this ;
    }
  });
  
  var UIUserEducation = UITable.extend({
    label: res('UIUserEducation.label'),
    config: {
      toolbar: {
        dflt: {
          actions: [
            {
              action: "onNew", icon: "add", label: res('UIUserEducation.action.onNew'), 
              onClick: function(thisTable) { 
                thisTable.onAddBean() ;
              } 
            }
          ]
        },
        filter: {
          fields: [
            { field: "schoolOrInstitute",   label: res('UIUserEducation.field.schoolOrInstitute'), type: 'string', operator: 'LIKE'}
          ],
          onFilter: function(thisTable, query) {
            console.log('onFilter');
          },
        }
      },
      
      bean: {
        label: 'Educations',
        fields: [
          { field: 'schoolOrInstitute', label: res('UIUserEducation.field.schoolOrInstitute'), 
            toggled: true, filterable: true},
          { field: 'from', label: res('UIUserEducation.field.from'), toggled: true, filterable: true,
            datepicker: { 
              getDate: function(bean) {
                if (bean.from == null) { bean.from = DateTime.getCurrentDate(); }
                return DateTime.fromDateTimeToDDMMYYYY(bean.from) ;
              },
              setDate: function(date, bean) {
                bean.from = DateTime.fromDDMMYYYYToDateTime(date) ;
              },
            } 
          },
          { field: 'to', label: res('UIUserEducation.field.to'), toggled: true, filterable: true,
            datepicker: {
              getDate: function(bean) {
                if (bean.to == null) { bean.to = DateTime.getCurrentDate(); }
                return DateTime.fromDateTimeToDDMMYYYY(bean.to) ;
              },
              setDate: function(date, bean) {
                bean.to = DateTime.fromDDMMYYYYToDateTime(date) ;
              },
            }
          },
          { field: 'major', label: res('UIUserEducation.field.major'), toggled: true, filterable: true},
          { 
            field: 'certificate', label: res('UIUserEducation.field.certificate'), toggled: true, filterable: true,
            select: {
              getOptions: function(field, bean) {
                var optionGroup = service.GenericOptionService.getOptionGroup(
                    'config', 'LocaleService', 'certificate').data ;
                var options = optionGroup.options;
                var result = [] ;
                result[0] = {label: "" , value: ""};
                for(var i = 0; i < options.length; i++) {
                  var option = options[i] ;
                  result[i+1] = { label: option.label , value: option.code } ;
                }
                return result ;
              }
            }
          },
          { field: 'language', label: res('UIUserEducation.field.language'), toggled: true, filterable: true,
            select: {
              getOptions: function(field, bean) {
                var optionGroup = service.GenericOptionService.getOptionGroup(
                    'config', 'LocaleService', 'language').data ;
                var options = optionGroup.options;
                var result = [] ;
                result[0] = {label: "" , value: ""};
                for(var i = 0; i < options.length; i++) {
                  var option = options[i] ;
                  result[i+1] = { label: option.label , value: option.code } ;
                }
                return result ;
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
          },
          {
            icon: "edit", label: "Modified",
            onClick: function(thisTable, row) { 
              thisTable.onEditBean(row) ;
            }
          }
        ]
      }
    },
    init: function(account) {
      this.account = account;
      this.setBeans(account.profiles.userEducations) ;
      return this ;
    }
  });
  
  var UIUserWork = UITable.extend({
    label: res('UIUserWork.label'),
    config: {
      toolbar: {
        dflt: {
          actions: [
            {
              action: "onNew", icon: "add", label: res('UIUserWork.action.onNew'),
              onClick: function(thisTable) { 
                thisTable.onAddBean() ;
              } 
            }
          ]
        },
        filter: {
          fields: [
            { field: "organization",   label: res('UIUserWork.field.organization'), type: 'string', operator: 'LIKE'}
          ],
          onFilter: function(thisTable, query) {
            console.log('onFilter');
          },
        }
      },
      
      bean: {
        label: 'Works',
        fields: [
          { field: "organization",   label: res('UIUserWork.field.organization'), toggled: true, filterable: true},
          { field: "from",   label: res('UIUserWork.field.from'), toggled: true, filterable: true,
            datepicker: {
              getDate: function(bean) {
                if (bean.from == null) { bean.from = DateTime.getCurrentDate(); }
                return DateTime.fromDateTimeToDDMMYYYY(bean.from) ;
              },
              setDate: function(date, bean) {                
                bean.from = DateTime.fromDDMMYYYYToDateTime(date) ;
              },
            }
          },
          { field: "to",   label: res('UIUserWork.field.to'), toggled: true, filterable: true,
            datepicker: {
              getDate: function(bean) {
                if (bean.to == null) { bean.to = DateTime.getCurrentDate(); }
                return DateTime.fromDateTimeToDDMMYYYY(bean.to) ;
              },
              setDate: function(date, bean) {
                bean.to = DateTime.fromDDMMYYYYToDateTime(date) ;
              },
            }
          },
          { field: "position",   label: res('UIUserWork.field.position'), toggled: true, filterable: true},
          { field: "description",   label: res('UIUserWork.field.description'), textarea: {}, toggled: true, filterable: true},
        ],
        actions:[
          {
            icon: "delete", label: res('UIUserWork.action.delete'),
            onClick: function(thisTable, row) { 
              thisTable.removeItemOnCurrentPage(row) ;
            }
          },
          {
            icon: "edit", label: res('UIUserWork.action.edit'),
            onClick: function(thisTable, row) { 
              thisTable.onEditBean(row) ;
            }
          }
        ]
      }
    },
    init: function(account) {
      this.account = account;
      this.setBeans(account.profiles.userWorks) ;
      return this ;
    }
  });
  
  var UIOrganizationBasic = UIBean.extend({
    label: res('UIOrganizationBasic.label'),
    config: {
      beans: {
        basic: {
          label: res('UIOrganizationBasic.label'),
          fields: [
            { field: "name", label: res('UIOrganizationBasic.field.name')},
            { field: "fullName", label: res('UIOrganizationBasic.field.fullName')},
            { field: "organizationType",   label: res('UIOrganizationBasic.field.organizationType'),
              select: {
                getOptions: function(field, bean) {
                  var optionGroup = service.GenericOptionService.getOptionGroup(
                      'config', 'LocaleService', 'type_restaurant').data ;
                  var options = optionGroup.options;
                  var result = [] ;
                  result[0] = {label: "" , value: ""};
                  for(var i = 0; i < options.length; i++) {
                    var option = options[i] ;
                    result[i+1] = { label: option.label , value: option.code } ;
                  }
                  return result ;
                }
              }
            },
            { field: "manager", label: res('UIOrganizationBasic.field.manager'),
              select: {
                getOptions: function(field, bean) {
                  var options = [
                    { label: res('UIOrganizationBasic.field.manager.false'), value: 'false' },
                    { label: res('UIOrganizationBasic.field.manager.true'), value: 'true'}
                  ];
                  return options ;
                }
              }
            },
            { field: "slogan", label: res('UIOrganizationBasic.field.slogan')},
            { field: "logoURL", label: res('UIOrganizationBasic.field.logoURL')},
            { field: "foundedDate", label: res('UIOrganizationBasic.field.foundedDate'),
              datepicker: {
                getDate: function(bean) {
                  return DateTime.fromDateTimeToDDMMYYYY(bean.foundedDate) ;
                },
                setDate: function(date, bean) {
                  bean.foundedDate = DateTime.fromDDMMYYYYToDateTime(date) ;
                },
              }
            },
            { field: "terminatedDate", label: res('UIOrganizationBasic.field.terminatedDate'),
              datepicker: {
                getDate: function(bean) {
                  return DateTime.fromDateTimeToDDMMYYYY(bean.terminatedDate) ;
                },
                setDate: function(date, bean) {
                  bean.terminatedDate = DateTime.fromDDMMYYYYToDateTime(date) ;
                },
              }
            },
            { field: "registrationCode", label: res('UIOrganizationBasic.field.registrationCode')},
            { field: "representative", label: res('UIOrganizationBasic.field.representative'),
              custom: {
                getDisplay: function(bean) {
                  return bean.loginId == null ? null : bean.loginId;
                },
                set: function(bean, obj) { bean.loginId = obj ;},
                
                autocomplete: {
                  search: function(val, bean) {
                    var accounts = service.AccountService.findAccountByLoginId(val).data ;
                    var result = [] ;
                    for(var i = 0; i < accounts.length; i++) {
                      var account = accounts[i] ;
                      result[i] = { 
                        value: account.loginId, 
                        label: account.loginId +
                        ' ( ' + account.profiles.basic.lastName  +' ' + account.profiles.basic.firstName + ' )'
                      } ;
                    }
                    return result ;
                  }
                }
              }
            },
            { field: "description",   label: res('UIOrganizationBasic.field.description'), textarea: {}},
          ]
        }
      }
    },
    
    init: function(profile, readOnly) {
      this.bind('basic', profile) ; 
      this.setReadOnly(readOnly);
      return this ;
    }
  });
  
  var UIBusinessRegistration = UITable.extend({
    label: res('UIBusinessRegistration.label'),
    config: {
      toolbar: {
        dflt: {
          actions: [
            {
              action: "onNew", icon: "add", label: res('UIBusinessRegistration.action.onNew'), 
              onClick: function(thisTable) { 
                thisTable.onAddBean() ;
              } 
            }
          ]
        },
        filter: {
          fields: [
            { field: "fullName.vn",   label: "Full Name In VN", type: 'string', operator: 'LIKE'}
          ],
          onFilter: function(thisTable, query) {
            console.log('onFilter');
          },
        }
      },
      
      bean: {
        label: 'Business Registrations',
        fields: [
          { field: "#",   label: "#"},
          { field: "fullName.vn", label: res('UIBusinessRegistration.field.fullName.vn'),
            toggled: true, filterable: true},
          { field: "fullName.en", label: res('UIBusinessRegistration.field.fullName.en'),
            toggled: true, filterable: true},
          { field: "registrationCode", label: res('UIBusinessRegistration.field.registrationCode'),
            toggled: true, filterable: true},
          { field: "taxRegistrationCode", label: res('UIBusinessRegistration.field.taxRegistrationCode'),
            toggled: true, filterable: true},
          { field: "representative", label: res('UIBusinessRegistration.field.representative'),
            toggled: true, filterable: true },
          { field: "charterCapital", label: res('UIBusinessRegistration.field.charterCapital'),
            toggled: true, filterable: true,
            defaultValue: 0, validator: { name: 'number', from: 0, errorMsg: "Expect a number > 0" }},
          { field: "legalCapital", label: res('UIBusinessRegistration.field.legalCapital'),
            toggled: true, filterable: true,
            defaultValue: 0, validator: { name: 'number', from: 0, errorMsg: "Expect a number > 0" }},
          { field: "domain", label:  res('UIBusinessRegistration.field.domain'),
            toggled: true, filterable: true}
        ],
        actions:[
          {
            icon: "delete", label: res('UIBusinessRegistration.action.delete'),
            onClick: function(thisTable, row) { 
              thisTable.removeItemOnCurrentPage(row) ;
            }
          },
          {
            icon: "edit", label: res('UIBusinessRegistration.action.edit'),
            onClick: function(thisTable, row) { 
              thisTable.onEditBean(row) ;
            }
          }
        ]
      }
    },
    init: function(account) {
      this.account = account;
      this.setBeans(account.profiles.businessRegistrations) ;
      return this ;
    }
  });
  
  var UIContact = UITable.extend({
    label: res('UIContact.label'),
    config: {
      toolbar: {
        dflt: {
          actions: [
            {
              action: "onNew", icon: "add", label: res('UIContact.action.onNew'),
              onClick: function(thisTable) { 
                thisTable.onAddBean() ;
              } 
            }
          ]
        },
        filter: {
          fields: [
            { field: "addressNumber",  label: "Address #", type: 'string', operator: 'LIKE'}
          ],
          onFilter: function(thisTable, query) {
            console.log('onFilter');
          },
        }
      },
      
      bean: {
        label: 'Contact',
        fields: [
          { field: "addressNumber",  label: res('UIContact.field.addressNumber'), toggled: true, filterable: true},
          { field: "street",  label: res('UIContact.field.street'), toggled: true, filterable: true},
          { field: "country",  label: res('UIContact.field.country'), toggled: true, filterable: true,
            custom: {
              getDisplay: function(bean) {
                return bean.country == null ? null : bean.country;
              },
              set: function(bean, obj) { bean.country = obj ; },
              
              autocomplete: {
                search: function(val) {
                  var countrys = service.LocaleService.findCountryByName(val).data ;
                  var result = [] ;
                  for(var i = 0; i < countrys.length; i++) {
                    var country = countrys[i] ;
                    result[i] = { value: country.name, label: country.name} ;
                  }
                  return result ;
                }
              }
            }
          },
          { field: "city",  label:  res('UIContact.field.city'), toggled: true, filterable: true,
            custom: {
              getDisplay: function(bean) {
                return bean.city == null ? null : bean.city;
              },
              set: function(bean, obj) { bean.city = obj ; },
              
              autocomplete: {
                search: function(val, bean) {
                  var citys = service.LocaleService.findCityByCountryAndName(bean.country,val).data ;
                  var result = [] ;
                  for(var i = 0; i < citys.length; i++) {
                    var city = citys[i] ;
                    result[i] = { value: city.name, label: city.name} ;
                  }
                  return result ;
                }
              }
            }
          },
          { field: "phone",  label:  res('UIContact.field.phone'), multiple: true, toggled: true, filterable: true},
          { field: "mobile",  label:  res('UIContact.field.mobile'), multiple: true, toggled: true, filterable: true},
          { field: "fax",  label:  res('UIContact.field.fax'), multiple: true, toggled: true, filterable: true},
          { field: "email",  label:  res('UIContact.field.email'), multiple: true, toggled: true, filterable: true},
          { field: "website",  label:  res('UIContact.field.website'), multiple: true, toggled: true, filterable: true}
        ],
        actions:[
          {
            icon: "delete", label:  res('UIContact.action.delete'),
            onClick: function(thisTable, row) { 
              thisTable.removeItemOnCurrentPage(row) ;
            }
          },
          {
            icon: "edit", label:  res('UIContact.action.edit'),
            onClick: function(thisTable, row) { 
              thisTable.onEditBean(row) ;
            }
          }
        ]
      }
    },
    init: function(account) {
      this.account = account;
      this.setBeans(account.contacts) ;
      return this ;
    }
  });
  
  var UIAccountTestFull = UICollapsible.extend({
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
    
    init: function(UIParent, account) {
      this.clear() ;
      this.UIParent = UIParent ;
      this.account = account ;

      if(account.profiles == null) account.profiles = {} ;
      if(account.contacts == null) account.contacts = [] ;

      this.UIAccount = new Account.UIAccount().init(this, account, true);
      this.add(new Account.UIAccount().init(this, account, true)) ;

      if(account.type == "USER") {
        if(account.profiles.basic == null) account.profiles.basic = {} ;
        if(account.profiles.userRelationships == null) account.profiles.userRelationships = [] ;
        if(account.profiles.userEducations == null) account.profiles.userEducations = [] ;
        if(account.profiles.userWorks == null) account.profiles.userWorks = [] ;

        this.add(new UIUserBasic().init(account.profiles.basic, false)) ;
        this.UIUserEducation = new UIUserEducation().init(account);
        this.add(this.UIUserEducation, true) ;
        this.UIUserWork = new UIUserWork().init(account);
        this.add(this.UIUserWork, true) ;
        this.UIUserRelationship = new UIUserRelationship().init(account);
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
        this.add(new UIOrganizationBasic().init(account.profiles.basic, false)) ;
        this.UIBusinessRegistration = new UIBusinessRegistration().init(account);
        this.add(this.UIBusinessRegistration) ;
        this.commitChange = function() {
          this.UIBusinessRegistration.commitChange() ;
          this.UIContact.commitChange() ;
        };
      }
      this.UIContact = new UIContact().init(account);
      this.add(this.UIContact, true) ;
      return this ;
    },
  });
  
  var AccountTestFull = {
	UIAccountTestFull: UIAccountTestFull,
    UIUserBasic: UIUserBasic,
    UIUserEducation: UIUserEducation,
    UIBusinessRegistration: UIBusinessRegistration,
    UIContact: UIContact,
    UIUserRelationship: UIUserRelationship,
    UIUserWork: UIUserWork,
    UIOrganizationBasic: UIOrganizationBasic
  };
  
  return AccountTestFull ;
});
