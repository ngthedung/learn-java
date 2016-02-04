define([
  'service/service',
  'module/Module',
  'ui/UITable',
  'ui/UIPopup',
  'ui/UIBreadcumbs',
  'ui/UICollapsible',
  'module/hr/Appointment',
], function(service, module, UITable, UIPopup, UIBreadcumbs, UICollapsible, Appointment) {
    
  var UIAppointmentList = UITable.extend({
    label: "Appointments",
      config : {
        toolbar: {
          dflt: {
            actions: [
              {
                action: "onNewAppointment", icon: "add", label: "New", 
                onClick: function(thisUI) {
                  var appointment = {
                      employeeLoginId: "", name: "", content: ""
                  };
                  UIPopup.activate(new Appointment.UIAppointment().init(thisUI, appointment, true)) ;
                } 
              },
              {
                action: "onRefresh", icon: "refresh", label: "Refresh",
                onClick: function(thisUI) { 
                  thisUI.onRefresh() ;
                }
              }
            ],
          },
          filter: {
            fields: [
              { label: 'Search By Employee', field: 'employeeLoginId', type: 'string', operator: 'LIKE' },
              { label: 'Search By Job Title', field: 'name', type: 'string', operator: 'LIKE' },
            ],
            onFilter: function(thisUI, query) {
              var result = service.HRService.searchAppointment(query).data ;
              var students = result.data ;
              thisUI.setBeans(students) ;
              thisUI.renderRows() ;
            },
          }
        },
      bean : {
        fields: [
          {
            label: 'Org Login Id', field: 'orgLoginId', toggled: true, filterable: true,
            onClick: function(thisUI, row) {
              var student = thisUI.getItemOnCurrentPage(row) ;
              UIPopup.activate(new Student.UIStudent().init(thisUI, student, false)) ;
            }
          },
          { label: 'Group', field: 'groupPath', toggled: true, filterable: true },
          { label: 'Employee', field: 'employeeLoginId', toggled: true, filterable: true },
          { label: 'Partner', field: 'partnerLoginId', toggled: true, filterable: true },
          { label: 'Job Title', field: 'name', toggled: true, filterable: true },
          { label: 'Date', field: 'date', toggled: true, filterable: true },
          { label: 'Status', field: 'status', toggled: true, filterable: true },
        ],
        actions: [
          {
            icon: "bars", label: "New Appointment",
            onClick: function(thisUI, row) { 
              var appointment = thisUI.getItemOnCurrentPage(row) ;
              UIPopup.activate(new Appointment.UIAppointment().init(thisUI, appointment, false)) ;
            }
          },
          {
            icon: "delete", label: "Delete",
            onClick: function(thisUI, row) {
              var appointment = thisUI.getItemOnCurrentPage(row) ;
              var deleted =  service.HRService.deleteAppointmentById(appointment.id).data ;
              if(deleted) {
                thisUI.removeItemOnCurrentPage(row) ;
              }
            }
          }
        ]
      }
    },
    
    onRefresh: function() {
      var result = service.HRService.searchAppointment(null).data ;
      var appointments = result.data ;
      this.setBeans(appointments) ;
      this.renderRows() ;
    },
    
    back: function(refresh) {
      if (refresh) {
        this.onRefresh();
      }
      this.viewStack.back();
    },
    
    init: function (viewStack) {
      this.viewStack = viewStack ;
      var result = service.HRService.searchAppointment(null).data ;
      var appointments = result.data ;
      this.setBeans(appointments) ;
      return this ;
    }
    
  });
  
  
    
  var UIAppointmentScreen = module.UIScreen.extend({
    initialize: function (options) { },
    
    activate: function() { 
      this.viewStack = new UIBreadcumbs({el: "#workspace"}) ;
      var aview = new UIAppointmentList().init(this.viewStack);
      this.viewStack.push(aview) ;
    },
    
    deactivate: function() { }
    
  });
  
  return UIAppointmentScreen ;
});
