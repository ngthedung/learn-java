define([
  'jquery', 'underscore', 'backbone', 'moment', 'service/service', 'util/SQL',
], function($, _, Backbone, moment, service, sql) {
  var InvoiceExpense =  {
    uiconfig : {
      label: "Invoice Expense", name: "invoiceExpense",
      options: [
         { 
           label: "Activity Type", name: "activityType",
           options: [
             {label: "All", value: ""},
             {label: "Payment", value: "Payment"},
             {label: "Receipt", value: "Receipt"}
           ]
         },
         { 
           label: "Expense Type", name: "ExpenseType",
           options: [
             {label: "Expense Department", value: "Expense Department"},
             {label: "Expense Employee", value: "Expense Employee"},
             {label: "Expense Customer", value: "Expense Customer"}
           ]
         },
        { label: "Search", name: "search" }
      ],
      actions: [
        { 
          name: 'report', label: 'Report',
          onClick: function(thisUI, thisReport, reportState) {
            thisReport.onReport(thisUI, reportState) ;
          }
        },
        {
          name: 'compareByActivityType', label: 'Compare Activity',
          onClick: function(thisUI, thisReport, reportState) {
            var name;
            if(reportState.ExpenseType=="Expense Employee"){
              name = "Employee";
            }else {
              if(reportState.ExpenseType=="Expense Customer"){
                name = "Customer";
              }else {
                name = "Department";
                reportState.ExpenseType = "Expense Department";
              }
            }
            var reportData = thisUI.getReportData() ;
            if(reportData == null) return ;
            var keyFields = [] ;
            if(reportData.hasField('Year')) keyFields.push('Year') ;
            if(reportData.hasField('Month')) keyFields.push('Month') ;
            keyFields.push(name);
            reportData.groupByCompare(keyFields, "Activity Type", "Final Charge") ;
          }
        },
      ]
    },
    
    onReport: function(thisUI, reportState) {
      var total, actual;
      if(reportState.activityType=="Receipt"){
        total = "Total Expense";
        actual = "Actual Expense";
      }else {
        if(reportState.activityType=="Payment"){
          total = "Total Cost";
          actual = "Actual Cost";
        }else {
          total = "Total";
          actual = "Actual";
        }
      }
      
      var name;
      if(reportState.ExpenseType=="Expense Employee"){
        name = "Employee";
      }else {
        if(reportState.ExpenseType=="Expense Customer"){
          name = "Customer";
        }else {
          name = "Department";
          reportState.ExpenseType = "Expense Department";
        }
      }
      
      var query = new sql.SelectQuery({
        tables: ['InvoiceDetail', 'Contributor'],
        fields: [
          { expression: "Contributor.identifierId", alias: name },
          { expression: "InvoiceDetail.type", alias: "Invoice Type" },
          { expression: "InvoiceDetail.activityType", alias: "Activity Type" },
          { expression: "InvoiceDetail.status", alias: "Status" },
          { expression: "count(InvoiceDetail.type)", alias: "Invoice", type: "integer"},
          { expression: "sum(InvoiceDetail.total)", alias: "Total", type: "number" },
          { expression: "sum(InvoiceDetail.discountByItem)", alias: "Discount By Item", type: "number" },
          { expression: "sum(InvoiceDetail.discount)", alias: "Discount", type: "number" },
          { expression: "sum(InvoiceDetail.totalTax)", alias: "Tax", type: "number" },
          { expression: "sum(InvoiceDetail.finalCharge)", alias: "Final Charge", type: "number" },
          { expression: "sum(InvoiceDetail.totalPaid)", alias: "Paid", type: "number" }
        ],
        joins: ["Contributor.invoiceId=InvoiceDetail.id"],
        groupBy: ["InvoiceDetail.activityType", "InvoiceDetail.type", "InvoiceDetail.status","Contributor.identifierId"],
        orderBy: [],
        
        groupByDate: function(type) {
          var year = { 
            expression: "EXTRACT(YEAR FROM InvoiceDetail.startDate) AS year", alias: "Year" 
          };
          var month = { 
            expression: "EXTRACT(MONTH FROM InvoiceDetail.startDate) AS month", alias: "Month" 
          };
          if('yearly' == type) {
            this.fieldPrepend(year) ;
            this.groupByPrepend("year") ;
            this.orderByPrepend("year") ;
          } else if('monthly' == type) {
            this.fieldPrepend([year, month]) ;
            this.groupByPrepend(["year", "month"]) ;
            this.orderByPrepend(["year", "month"]) ;
          }
        },
        
        rangeByDate: function(from, to) {
          if(!$.isEmptyObject(from)) {
            var fdate = moment(from, "DD/MM/YYYY").format('YYYY-MM-DD 00:00:00') ;
            this.join("InvoiceDetail.startDate >= ':param'", fdate);
          }
          if(!$.isEmptyObject(to)) {
            var tdate = moment(to, "DD/MM/YYYY").format('YYYY-MM-DD 23:59:59') ;
            this.join("InvoiceDetail.endDate <= ':param'", tdate);
          }
        }
      });
        
      query.groupByDate(reportState.dateGroupBy) ;
      query.rangeByDate(reportState.fromDate, reportState.toDate) ;
      query.join("InvoiceDetail.activityType = ':param'", reportState.activityType);
      query.join("Contributor.identifierId LIKE ':param'", reportState.search);
      if(reportState.ExpenseType=="Expense Department"){
        query.join("Contributor.role = ':param'", "Department");
      }else {
        if(reportState.ExpenseType=="Expense Employee"){
          query.join("Contributor.type = ':param'", "User");
          query.join("Contributor.role != ':param'", "Đối tác");
        }else {
          query.join("Contributor.role = ':param'", "Đối tác");
        }
      }
      
      
      var reportTable = service.AccountingService.reportQuery([query]).data ;
      thisUI.setReportData(reportTable[0]) ;
    }
  };
  
  return InvoiceExpense ;
});
