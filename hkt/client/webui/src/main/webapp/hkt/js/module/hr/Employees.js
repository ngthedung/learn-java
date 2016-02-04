define([ 
  'service/service',
  'module/Module',
  'ui/UICollapsible',
  'ui/UITable',
  'ui/UIBreadcumbs',
  'ui/UIPopup',
  'module/hr/Employee',
  'module/hr/WorkPositions',
  'module/hr/EmployeeFull',
  'module/account/Account',
  'module/cms/Nodes',
  'module/cms/UICMSNode',
  'i18n',
  'util/DateTime',
], function(service, module, UICollapsible, UITable, UIBreadcumbs, UIPopup, Employee, 
    WorkPositions,EmployeeFull, Account, CMSNodes, UICMSNode, i18n, DateTime) {
  var res = i18n.getResource('module/hr/hr');
  
  var UIEmployeeContracts = UICollapsible.extend({
    label: "", 
    config: {
      actions: []
    },
    
    init: function(viewStack, employee) {
      this.clear() ;
      this.label = "Contracts For " + employee.loginId ;
      this.add(new Employee.UIEmployee().initViewOnly(this, employee)) ;
      
      var UIContracts = new CMSNodes.UINodeList() ;
      UIContracts.label = "Contracts" ;
      UIContracts.onViewNode = function(node) {
        var nodeDetail = service.CMSService.getNodeDetail(node.path).data ;
        var uiContract = new UICMSNode({
          label: res('UIContract.label'),
          isNew: false,
          mimeType: "employee/contract", 
          nodeDetail: nodeDetail, 
          back: function() { viewStack.back() ; }
        }) ;
        viewStack.push(uiContract) ;
      };
      var contracts = service.HRService.getEmployeeContracts(employee).data  ;
      if(contracts == null) contracts = [] ;
      UIContracts.init(contracts) ;
      this.add(UIContracts) ;
    }
  }) ;
  
  var UIWorkPositions = UICollapsible.extend({
    label: res('UIWorkPosition.label'),
    config: {
      actions: [
        {
          action: 'back', label: res('UIContract.action.back'),
          onClick: function (thisUI) {
            if (thisUI.UIParent.back) {
              thisUI.UIParent.back(false) ;
            }
          }
        },
      ]
    },
    
    init: function (UIParent, employee) {
      this.UIParent = UIParent ;
      this.clear() ;
      this.add(new Employee.UIEmployee().initViewOnly(employee), true);
      
      var UIWorkPositionList = new WorkPositions.UIWorkPositionList() ;
      UIWorkPositionList.init(UIParent, employee) ;
      this.add(UIWorkPositionList) ;
    }
  });
  
  var UIEmployeeList = UITable.extend({
    label: res('UIEmployeeList.label'),
      config : {
        toolbar: {
          dflt: {
            actions: [
              {
                action: "onNewEmployee", icon: "add", label: res('UIEmployeeList.action.onNewEmployee'),
                onClick: function(thisUI) {
                  var employee = {
                    loginId: "", organizationLoginId: "", 
                    startDate: DateTime.getCurrentDate(), leaveDate: DateTime.getCurrentDate()
                  } ;
                  var uiDetail = new Employee.UIEmployeeDetail().init(thisUI, employee, true) ;
                  thisUI.viewStack.push(uiDetail) ;
                } 
              },
              {
                action: "onNewFullEmployee", icon: "add", label: res('UIEmployeeList.action.onNewFullEmployee'),
                onClick: function(thisUI) {
                  var account = { type: 'USER', loginId: " "} ; 
                  var aview = new EmployeeFull.UIEmployeeFull().init(thisUI, account);
                  thisUI.viewStack.push(aview) ;
                } 
              },
              {
                action: "onRefresh", icon: "refresh", label: res('UIEmployeeList.action.onRefresh'),
                onClick: function(thisUI) { 
                  thisUI.onRefresh() ;
                }
              }
            ],
          },
          filter: {
            fields: [
              { label: 'Search By Login Id',  field: 'loginId', type: 'string', operator: 'LIKE'},
              { label: 'Search By Organization Login Id',  field: 'organizationLoginId', type: 'string', operator: 'LIKE'}
            ],
            onFilter: function(thisUI, query) {
              var result = service.HRService.searchEmployee(query).data ;
              var users = result.data ;
              thisUI.setBeans(users) ;
              thisUI.renderRows() ;
            },
          }
        },
      bean : {
        fields: [
          { label : res('UIEmployee.field.loginId'), field : 'loginId', toggled : true, filterable : true,
            onClick: function(thisUI, row) {
              var employee = thisUI.getItemOnCurrentPage(row) ;
              var account = service.AccountService.getAccountByLoginId(employee.loginId).data;
              var uiDetail = thisUI.UIAccountDetail.init(thisUI, account, true) ;
              thisUI.viewStack.push(uiDetail) ;
            }
          },
          { label : res('UIEmployee.field.organizationLoginId'), field : 'organizationLoginId', toggled : true, filterable : true,
            onClick: function(thisUI, row) {
              var employee = thisUI.getItemOnCurrentPage(row) ;
              var account = service.AccountService.getAccountByLoginId(employee.organizationLoginId).data;
              var uiDetail = thisUI.UIAccountDetail.init(thisUI, account, true) ;
              thisUI.viewStack.push(uiDetail) ;
            }
          },
          { label : res('UIEmployee.field.startDate'), field : 'startDate', toggled : true, filterable : true },
          { label : res('UIEmployee.field.leaveDate'), field : 'leaveDate', toggled : true, filterable : true },
        ],
        actions: [
          {
            icon: "edit", label: "Edit Employee",
            onClick: function(thisUI, row) {
              var employee = thisUI.getItemOnCurrentPage(row) ;
              var uiDetail = new Employee.UIEmployeeDetail().init(thisUI, employee, false) ;
              thisUI.viewStack.push(uiDetail) ;
            }
          },
          {
            icon: "grid", label: "Work Position",
            onClick: function(thisUI, row) {
              var employee = thisUI.getItemOnCurrentPage(row) ;
              thisUI.UIWorkPositions.init(thisUI, employee) ;
              thisUI.viewStack.push(thisUI.UIWorkPositions) ;
            }
          },
          {
            icon: "bars", label: "Contracts",
            onClick: function(thisUI, row) {
              var employee = thisUI.getItemOnCurrentPage(row) ;
              thisUI.UIEmployeeContracts.init(thisUI.viewStack, employee) ;
              thisUI.viewStack.push(thisUI.UIEmployeeContracts) ;
            }
          },
          {
            icon: "delete", label: "Delete",
            onClick: function(thisUI, row) {
              var employee = thisUI.getItemOnCurrentPage(row) ;
              var deleted = service.HRService.deleteEmployeeById(employee.id).data ;
              if(deleted) {
                thisUI.removeItemOnCurrentPage(row) ;
              }
            }
          }
        ]
      }
    },
    
    onRefresh: function() {
      var result = service.HRService.searchEmployee(null).data ;
      var employees = result.data;
      this.setBeans(employees) ;
      this.renderRows() ;
    },
    
    back: function(refresh) {
      if(refresh) this.onRefresh() ;
      this.viewStack.back() ;
    },
    
    init: function (viewStack) {
      this.viewStack = viewStack ;
      this.UIAccountDetail = new Account.UIAccountDetail() ;
      this.UIEmployeeContracts = new UIEmployeeContracts() ;
      this.UIWorkPositions = new UIWorkPositions() ;
      
      var result = service.HRService.searchEmployee(null).data ;
      var employees = result.data ;
      this.setBeans(employees) ;
      return this ;
    }
    
  });
  
  var UIEmployeesScreen = module.UIScreen.extend({
    initialize: function (options) {
    },
    
    activate: function() { 
      this.viewStack = new UIBreadcumbs({el: "#workspace"}) ;
      var aview = new UIEmployeeList().init(this.viewStack);
      this.viewStack.push(aview) ;
    },
    
    deactivate: function() { }
    
  });
  
  return UIEmployeesScreen ;
});
