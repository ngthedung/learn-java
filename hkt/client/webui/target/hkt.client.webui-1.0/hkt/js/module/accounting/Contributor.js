define([
  'service/service',
  'ui/UITable',
  'ui/UIBean',
  'ui/UIPopup',
  'module/account/Account',
  'module/account/Group'
], function(service, UITable, UIBean, UIPopup, Account, Group) {
  
  var UIContributor = UIBean.extend({
    label: 'Contributor',
    config: {
      beans: {
        contributor: {
          label: 'contributor',
          fields: [
            {
              field : "type", label : "Type" , defaultValue: 'User',
              select: {
                getOptions: function(field, bean) {
                  var options = [
                    { label: 'User', value: 'User' },
                    { label: 'Group', value: 'Group' }
                  ];
                  return options ;
                }
              }
            },       
            { field: "identifierId", label: "Identifier Id", required: true, validator: {name: "empty"},
              custom : {
                getDisplay: function(bean) { return bean.identifierId ; },
                set: function(bean, obj) { bean.identifierId = obj ;},
                autocomplete: {
                  search: function(val) {
                    var accounts = service.AccountService.findAccountByLoginIdUser(val).data ;
                    var result = [] ;
                    for (var i = 0; i < accounts.length; i++) {
                      var account = accounts[i] ;
                      result[i] = {value: account.loginId, label: account.loginId + '(' + account.email + ')'} ;
                    }
                    return result ;
                  }
                }
              }
            },
            { 
              field: "role", label: "Role", required: true, validator: {name: "empty"},
              select: {
                getOptions: function(field, bean) {
                  var optionGroup = service.GenericOptionService.getOptionGroup(
                      'accounting', 'AccountingService', 'contributor_role').data ;
                  var options = optionGroup.options;
                  var result = [] ;
                  for(var i = 0; i < options.length; i++) {
                    var option = options[i] ;
                    result[i] = { label: option.label , value: option.code } ;
                  }
                  return result ;
                }
              }
            },
            { 
              field: "percent", label: "Percent",
              defaultValue: 0, required: true, validator: { name: 'number', from: 0, errorMsg: "Expect a number > 0" }
            },
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
                  var contributor = thisUI.getBean('contributor') ;
                  if (thisUI.isNew) thisUI.UIParent.save(contributor) ;
                  if (thisUI.UIParent.onRefresh != null) {
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
    
    init: function(UIParent, contributor, isNew) {
      this.isNew = isNew;
      this.UIParent = UIParent ;
      this.bind('contributor', contributor, true) ;
      if (isNew) { 
        this.getBeanState('contributor').editMode = true ; 
      }
      return this ;
    },
    total:function(){
        return 0;
      }
  });
    
  var UIContributorList = UITable.extend({
    label: 'Invoice contributor',
    config : {
      toolbar: {
        dflt: {
          actions: [
            {
              action: "onNewcontributor", icon: "add", label: "New contributor", 
              onClick: function(thisUI) { 
                var contributor = { identifierId : "", role : "" };
                UIPopup.activate(new UIContributor().init(thisUI, contributor, true)) ;
              } 
            }
          ]
        }
      },
      bean: {
        fields: [
          { label : 'Type', field : 'type', toggled : true, filterable : true },
          { label : 'Identifier Id', field : 'identifierId', toggled : true, filterable : true,
            onClick: function(thisUI, row) {
              var contributor = thisUI.getItemOnCurrentPage(row) ;
              if(contributor.type== "User"){
                var account = service.AccountService.getAccountByLoginId(contributor.identifierId).data ;
                var uiDetail = thisUI.UIAccountDetail.init(thisUI, account, true) ;
                thisUI.viewStack.push(uiDetail) ;
              }else{
                var group = service.AccountService.getGroupByPath(contributor.identifierId).data ;
                UIPopup.activate( new Group.UIGroup().init(this, null, group, false));
              }
            }
          }, 
          { label : 'Role', field : 'role', toggled : true, filterable : true },
          { label : 'Percent', field : 'percent', toggled : true, filterable : true }
        ],
        actions: [
          {
            icon: "edit", label: "Edit Contributor",
            onClick: function (thisUI, row) { 
              var contributor = thisUI.getItemOnCurrentPage(row) ;
              UIPopup.activate(new UIContributor().init(thisUI, contributor, false)) ;
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
    
    save: function (contributor) {
      this.listContributor[this.listContributor.length] = contributor ; 
    },
    
    total:function(){
          return 0;
        },
    init: function(viewStack, listContributor) {
      this.viewStack = viewStack;
      this.UIAccountDetail = new Account.UIAccountDetail() ;
      this.listContributor = listContributor;
      this.setBeans(this.listContributor) ;
      return this ;
    },
    
    onRefresh: function() { 
      this.setBeans(this.listContributor) ;
      this.renderRows() ;
    },
    
  });
  
  var InvoiceContributor = {
    UIContributorList: UIContributorList
  };
  
  return InvoiceContributor ;
});
