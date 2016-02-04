define([ 
  'jquery', 
  'service/service', 
  'test/Test', 
  'test/Site', 
  'test/Assert'
],function($, service, test, Site, Assert) {
  var CleanDB = new test.UnitTask({
    name: "CleanHRModule",
    description: "Drop all the data in the hr module",
    units: [
      function() { 
        service.HRService.cleanEmployeeDB() ;
      }
    ]
  }); 

  var EmployeeApiCRUD = new test.UnitTask({
    name : "EmployeeCRUDApiTask",
    description : "test call create/get/update/delete an employee",
    units : [ 
      function() {
        console.log("EmployeeCRUDTask: Start!!!") ;
        var employee = {
          loginId : 'longhkt', organizationLoginId: 'hkt'
        };
        employee = service.HRService.saveEmployee(employee).data ;
        Assert.assertEquals('longhkt', employee.loginId) ;
        
        res = service.HRService.deleteEmployeeById(employee.id) ;
        Assert.assertEquals(true, res.data) ;
        console.log("AccountApiCRUDTask: Finish!!!") ;
    }
    ]
  });
  
  var WorkPositionApiCRUD = new test.UnitTask({
    name : "WorkPositionCRUDApiTask",
    description : "test call create/get/update/delete an WorkPosition",
    units : [ function() {
      console.log("EmployeeCRUDTask: Start!!!") ;
      var employee = {
        loginId : 'longhkt', organizationLoginId: 'hkt'
      };
      employee = service.HRService.saveEmployee(employee).data ;
      
      var position = {
        loginId: 'longhkt', positionTitle: 'Leader Id', group: 'it', fromDate: '10/1/2013 00:00:00 GMT+0700', 
        toDate: '', status: 'Planed', salaryType: 'Monthly', salary: 10000000
      };
      
      position = service.HRService.saveWorkPosition(employee, position).data ;
      Assert.assertEquals('longhkt', position.loginId) ;
      
      var i = service.HRService.deleteWorkPosition(position) ;
      Assert.assertEquals(true, i.data) ;
      
      console.log("AccountApiCRUDTask: Finish!!!");
    }]
  });
  
  var DailyWorkApiCRUDTask = new test.UnitTask({
    name : "DailyWorkCRUDApiTask",
    description : "test call create/get/update/delete an DailyWork ",
    units : [ function() {
      console.log("DailyWorkCRUDTask: Start!!!") ;
      var employee = {
        loginId : 'thanghkt01', organizationLoginId: 'hkt'
      };
      employee = service.HRService.saveEmployee(employee).data ;
      var position = {
        loginId: 'thanghkt01', positionTitle: 'Leader Id', group: 'it', fromDate: '10/1/2013 00:00:00 GMT+0700', 
        toDate: '', status: 'Planed', salaryType: 'Monthly', salary: 10000000
      };
      position = service.HRService.saveWorkPosition(employee, position).data ;
      var daily = {
        loginId: 'thanghkt01', startTime: '20/12/2013 15:30:00 GMT+0700', endTime: '20/12/2013 20:30:00 GMT+0700',
        location: 'Stress Nguyen Du-Ha Noi', note: 'Support Customer'
      } ;
      
      daily = service.HRService.saveDailyWork(position, daily).data ;
      Assert.assertEquals("thanghkt01", daily.loginId) ;
      
      var result = service.HRService.deleteDailyWorkById(daily.id).data ;
      Assert.assertEquals(true, result) ;
      console.log("AccountApiCRUDTask: Finish!!!");
    }]
  });

  var GoToHrEmployeesScreen = new test.UnitTask({
    name: "GoToHrEmployeesScreen",
    description: "Click on hr and then Employees in the navigation",
    units: [
      function() { Site.Navigation.clickMenuItem("HR", "Employees") ; } ,
    ]
  });
  
  var SearchEmployeeTask = new test.UnitTask({
    name: "SearchEmployeeTask",
    description: "Search employee",
    units: [
      function() { Site.Navigation.clickMenuItem("HR", "Employees") ; } ,
      function() { Site.Workspace.tableToolbars('Login Id').clickButton("More Toolbars"); } ,
      function() {
        var controlGroup = Site.Workspace.tableToolbar("Login Id") ;
        controlGroup.inputVal('loginId', "tha*") ;
      },
      function() { Site.Workspace.tableToolbars('Login Id').clickButton("Filter"); } ,
      function() {
        var controlGroup = Site.Workspace.tableToolbar("Login Id") ;
        controlGroup.inputVal('loginId', "") ;
      },
      function() { Site.Workspace.tableToolbars('Login Id').clickButton("More Filter Option"); } ,
      function() {
        var controlGroup = Site.Workspace.tableToolbar("Login Id") ;
        controlGroup.inputVal('organizationLoginId', "hk*") ;
      },
      function() { Site.Workspace.tableToolbars('Login Id').clickButton("Filter"); } ,
    ]
  });

  var CreateLongEmployeeTask = new test.UnitTask({
    name: "CreateLongEmployeeTask",
    description: "Create Long Employee",
    units: [
      function() { Site.Workspace.tableToolbar('Login Id').clickButton("New"); } ,
      function() {
        var form = Site.Workspace.formWithText("Login Id:") ;
        form.inputVal("loginId", "$@tu.phan");
        form.inputVal("organizationLoginId", "$@hkt");
        form.inputVal("startDate", "20/02/2013 08:00:00 GMT+0700");
      },
      function() {
        Site.Workspace.toolbarWith('Employees').clickButton("Save");
      }
    ]
  });

  var EditLongEmployeeTask = new test.UnitTask({
    name: "EditLongEmployeeTask",
    description: "Edit Long Employee Task",
    units: [
     function() { Site.Workspace.tableToolbars('Login Id').clickButton("More Toolbars"); } ,
     function() {
       var controlGroup = Site.Workspace.tableToolbar("Filter") ;
       controlGroup.inputVal('loginId', "tu.*") ;
     },
     function() { Site.Workspace.tableToolbars('Login Id').clickButton("Filter"); } ,
     function() {
       var table = Site.Workspace.tableWithHeader("Organization Login Id") ;
       var row   = table.tableRowWithText("tu.phan") ;
       row.clickButton("Edit Employee") ;
     },
     
     function() {
       var form = Site.Workspace.formWithText("Login Id:") ;
       form.inputVal("leaveDate", "20/01/2014 08:00:00 GMT+0700") ;
     },
     
     function() {
       Site.Workspace.toolbarWith('Employees').clickButton("Save");
     }
   ]
  });

  var DeleteLongEmployeeTask = new test.UnitTask({
    name: "DeleteLongEmployeeTask",
    description: "Delete Long Employee Task",
    units: [
      function() {
        var controlGroup = Site.Workspace.tableToolbar("Filter") ;
        controlGroup.inputVal('loginId', "tu.*") ;
      },
      function() { Site.Workspace.tableToolbars("Login Id").clickButton("Filter"); } ,
      function() {
        var table = Site.Workspace.tableWithHeader("Start Date") ;
        var row = table.tableRowWithText("tu.phan") ;
        row.clickButton("Delete") ;
      },
      function() {
        var controlGroup = Site.Workspace.tableToolbar("Filter") ;
        controlGroup.inputVal('loginId', "") ;
      },
      function() {
        Site.Workspace.tableToolbars("Login Id").clickButton("More Toolbars") ;
      },
      function() {
        Site.Workspace.tableToolbar("Login Id").clickButton("Refresh") ;
      }
   ]
  });

  var CreateWorkPositionTask = new test.UnitTask({
    name: "CreateWorkPositionTask",
    description: "Create Work Position",
    units: [
      function() {
        var table = Site.Workspace.tableWithHeader("Organization Login Id") ;
        var row   = table.tableRowWithText("long") ;
        row.clickButton("Work Position") ;
      },
     function() { Site.Workspace.tableToolbar('Login Id').clickButton("New") ; } ,
     function() {
       var form = Site.PopupPanel.formWithText("Position Title:") ;
       form.inputVal("positionTitle", "$@Manager") ;
       form.inputVal("group", "$@hkt") ;
       form.inputVal("fromDate", "01/01/2014 08:00:00 GMT+0700") ;
       form.inputVal("toDate", "") ;
       form.inputVal("status", "Active") ;
       form.inputVal("salaryType", "Monthly") ;
       form.inputVal("salary", "5500000") ;
     },
     function() {
       Site.PopupPanel.formWithText("Position Title:").clickButton("Save");
     }
   ]
  });

  var EditWorkPositionTask = new test.UnitTask({
    name: "EditWorkPositionTask",
    description: "Edit Work Position Task",
    units: [
      function() {
        var table = Site.Workspace.tableWithHeader("Position Title") ;
        var row   = table.tableRowWithText("Leader Id") ;
        row.clickButton("Edit Position") ;
      },
      function() {
        var form = Site.PopupPanel.formWithText("Position Title:") ;
        form.inputVal("status", "Planed") ;
        form.inputVal("salary", "7700000");
      },
      function() {
        Site.PopupPanel.formWithText("Position Title:").clickButton("Save");
      }
    ]
  });

  var DeleteWorkPositionTask = new test.UnitTask({
    name: "DeleteWorkPositionTask",
    description: "Delete Work Position Task",
    units: [
      function() {
        var table = Site.Workspace.tableWithHeader("Position Title") ;
        var row   = table.tableRowWithText("Leader Id") ;
        row.clickButton("Delete") ;
      },
    ]
  });
  
  var CreateDailyWorkTask = new test.UnitTask({
    name: "CreateDailyWorkTask",
    description: "Create DailyWork information",
    units: [
      function() {
        var table = Site.Workspace.tableWithHeader("Position Title") ;
        var row   = table.tableRowWithText("10/01") ;
        row.clickButton("DailyWorks") ;
      },
      function() { Site.Workspace.tableToolbar('Id').clickButton("New") ; } ,
      function() {
        var form = Site.PopupPanel.formWithText("Login Id:") ;
        form.inputVal("startTime", "20/12/2013 15:35:22 GMT+0700") ;
        form.inputVal("endTime", "20/12/2013 20:00:00 GMT+0700") ;
        form.inputVal("note", "Support Customer");
      },
      function() {
        Site.PopupPanel.formWithText("Login Id:").clickButton("Save");
      }
    ]
  });
  
  var EditDailyWorkTask = new test.UnitTask({
    name: "EditDailyWorkTask",
    description: "Edit DailyWork Task",
    units: [
      function() {
       var table = Site.Workspace.tableWithHeader("Start Time") ;
       var row   = table.tableRowWithText("20/12/2013 15:35:22 GMT+0700") ;
       row.clickButton("Edit DailyWork") ;
      },
      function() {
        var form = Site.PopupPanel.formWithText("Login Id:") ;
        form.inputVal("startTime", "20/12/2013 15:35:22 GMT+0700") ;
        form.inputVal("endTime", "20/12/2013 20:00:00 GMT+0700") ;
        form.inputVal("note", "Ho Tro khach hang tai Cau Giay - Ha Noi");
      },
      function() {
        Site.PopupPanel.formWithText("Login Id:").clickButton("Save");
      }
    ]
  });
  
  var DeleteDailyWorkTask = new test.UnitTask({
    name: "DeleteDailyWorkTask",
    description: "Delete DailyWork Task",
    units: [
      function() {
       var table = Site.Workspace.tableWithHeader("Start Time") ;
       var row   = table.tableRowWithText("20/12/2013") ;
       row.clickButton("Delete") ;
      },
      function() { Site.Workspace.toolbarWith("Work Positions").clickButton("Work Positions") ; }
    ]
  });
  
  var GotoBackEmployeesScreen = new test.UnitTask({
    name: "GotoBackEmployeesScreen",
    description: "Goto Back Employees Screen",
    units: [
      function() { Site.Workspace.toolbarWith("Employees").clickButton("Employees") ; },
      function() { Site.Workspace.tableToolbar("Login Id").clickButton("Refresh") ; },
    ]
  });
  
  var LinkTask = new test.UnitTask({
    name: "TestLinkHR",
    description: "test link hr",
    units: [
      function() { Site.Navigation.clickMenuItem("HR", "Employees") ; } ,
      function() {
        var table = Site.Workspace.tableWithHeader("Login Id") ;
        var row   = table.tableRowWithText("thang") ;
        row.clickLink("thang") ;
      },
      function() {
        Site.Workspace.toolbarWith("Back").clickButton("Back");
      },
      function() {
        var table = Site.Workspace.tableWithHeader("Login Id") ;
        var row   = table.tableRowWithText("thang") ;
        row.clickLink("hkt") ;
      },
      function() {
        Site.Workspace.toolbarWith("Back").clickButton("Back");
      },
      function() {
        var table = Site.Workspace.tableWithHeader("Login Id") ;
        var row   = table.tableRowWithText("thang") ;
        row.clickButton("Work Position") ;
      },
      function() {
        var table = Site.Workspace.tableWithHeader("Position Title") ;
        var row   = table.tableRowWithText("10000000") ;
        row.clickLink("thang") ;
      },
      function() {
        Site.Workspace.toolbarWith("Back").clickButton("Back");
      },
      function() {
        var table = Site.Workspace.tableWithHeader("Login Id") ;
        var row   = table.tableRowWithText("thang") ;
        row.clickButton("DailyWorks") ;
      },
      function() {
        var table = Site.Workspace.tableWithHeader("Start Time") ;
        var row   = table.tableRowWithText("Ngay dau lam viec") ;
        row.clickLink("thang") ;
      },
      function() {
        Site.Workspace.toolbarWith("Back").clickButton("Back");
      }
    ]
  });
  
  var hr = {
    module: 'hr',
    
    CleanDB: CleanDB,
    
    createScenario: function(name) {
      var Scenario = new test.UnitTask({
        name: "HRScenario " + name,
        description: "Create a minimum set of data for the module",
        units: [
          function() { 
            var jsonRes = 'scenario/' + name + '/hr.json' ;
            var data = service.Server.syncGETResource(jsonRes, 'json') ;
            if(data != null) {
              service.HRService.createScenario(data) ;
            } else {
              console.log("No data for " + jsonRes) ;
            }
          }
        ]
      }); 
      return Scenario ;
    },
    
    Service: {
      api: [
        EmployeeApiCRUD, WorkPositionApiCRUD, DailyWorkApiCRUDTask
      ]
    },
    
    UI: {
      Empoyee: test.Suite.extend({
        name: 'Employee',
        description: "create/update/delete/find Employee" ,
        unitTasks: [
          GoToHrEmployeesScreen,
          CreateLongEmployeeTask, EditLongEmployeeTask, DeleteLongEmployeeTask,
          CreateWorkPositionTask, EditWorkPositionTask, 
          CreateDailyWorkTask, 
//          EditDailyWorkTask, DeleteDailyWorkTask,DeleteWorkPositionTask,
//          SearchEmployeeTask,LinkTask,
//          LinkTask, GotoBackEmployeesScreen
        ],
      }), 
    }
  };
  return hr ;
});
