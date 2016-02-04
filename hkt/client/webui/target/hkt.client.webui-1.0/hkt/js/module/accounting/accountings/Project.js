/**
 * Design by Bui Trong Hieu
 */

define([
  'jquery', 'underscore', 'backbone', 'moment', 'service/service', 'util/SQL',
], function($, _, Backbone, moment, service, sql) {
  var Project =  {
    uiconfig : {
      label: "Project", name: "projectName",
      options: [
        { 
          label: "Activity Type", name: "activityType",
          options: [
            {label: "All", value: ""},
            {label: "Payment", value: "Payment"},
            {label: "Received", value: "Received"}
          ]
        },
        { label: "Search Name", name: "search" }
      ],
      actions: [
        { 
          name: 'accounting', label: 'Accounting',
          onClick: function(thisUI, thisReport, reportState) {
            thisReport.onReport(thisUI, reportState) ;
          }
        },
        {
          name: 'compareByActivityType', label: 'Compare Activity',
          onClick: function(thisUI, thisReport, reportState) {
            var reportData = thisUI.getReportData() ;
            if(reportData == null) return ;
            var keyFields = [] ;
            if(reportData.hasField('Year')) keyFields.push('Year') ;
            if(reportData.hasField('Month')) keyFields.push('Month') ;
            keyFields.push("Name");
            reportData.groupByCompare(keyFields,"Activity Type", "Final Charge") ;
          }
        },
      ]
    },
    
    onReport: function(thisUI, reportState) {
      var query = new sql.SelectQuery({
        tables: ['InvoiceTransaction'],
        fields: [
          { expression: "InvoiceTransaction.projectCode", alias: "Code" },
          { expression: "InvoiceTransaction.activityType", alias: "Activity Type" },
          { expression: " COALESCE(SUM(InvoiceTransaction.total * InvoiceTransaction.currencyRate),0)", alias: "Total" },
        ],
        groupBy: [ "InvoiceTransaction.projectCode", "InvoiceTransaction.activityType"],
        orderBy: [ ],
        
        groupByDate: function(type) {
          var year = { 
            expression: "EXTRACT(YEAR FROM InvoiceTransaction.transactionDate) AS year", alias: "Year" 
          };
          var month = { 
            expression: "EXTRACT(MONTH FROM InvoiceTransaction.transactionDate) AS month", alias: "Month" 
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
            this.join("InvoiceTransaction.transactionDate >= ':param'", fdate);
          }
          if(!$.isEmptyObject(to)) {
            var tdate = moment(to, "DD/MM/YYYY").format('YYYY-MM-DD 23:59:59') ;
            this.join("InvoiceTransaction.transactionDate <= ':param'", tdate);
          }
        }
      });
        
      var now = moment();
      query.groupByDate(reportState.dateGroupBy) ;
      query.rangeByDate(reportState.fromDate, reportState.toDate) ;
      query.join("InvoiceTransaction.projectCode = ':param'", reportState.projectCode);
      query.join("InvoiceTransaction.activityType = ':param'", reportState.activityType);
      var reportTable = service.AccountingService.reportQuery([query]).data ;
      thisUI.setReportData(reportTable[0]) ;
    }
  };
  
  return Project ;
});
