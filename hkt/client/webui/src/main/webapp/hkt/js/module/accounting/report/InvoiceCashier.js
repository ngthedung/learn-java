define([
  'jquery', 'underscore', 'backbone', 'moment', 'service/service', 'util/SQL',
], function($, _, Backbone, moment, service, sql) {
  var InvoiceCashier =  {
    uiconfig : {
      label: "Invoice Cashier", name: "invoiceCashier",
      options: [
        { 
          label: "Activity Type", name: "activityType",
          options: [
            {label: "All", value: ""},
            {label: "Payment", value: "Payment"},
            {label: "Receipt", value: "Receipt"}
          ]
        }
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
            var reportData = thisUI.getReportData() ;
            if(reportData == null) return ;
            var keyFields = [] ;
            if(reportData.hasField('Year')) keyFields.push('Year') ;
            if(reportData.hasField('Month')) keyFields.push('Month') ;
            keyFields.push("Employee");
            reportData.groupByCompare(keyFields, "Activity Type", "Total") ;
          }
        }
      ]
    },
    
    onReport: function(thisUI, reportState) {
      var query = new sql.SelectQuery({
          tables: ['InvoiceDetail', 'InvoiceTransaction'],
          fields: [
            { expression: "InvoiceTransaction.createdBy", alias: "Employee" },
            { expression: "InvoiceDetail.type", alias: "Invoice Type" },
            { expression: "InvoiceDetail.activityType", alias: "Activity Type" },
            { expression: "sum(InvoiceTransaction.total)", alias: "Total", type: "number" }
          ],
          joins: [ "InvoiceTransaction.invoiceId = InvoiceDetail.id" ],
          groupBy: [ "InvoiceTransaction.createdBy", "InvoiceDetail.activityType", "InvoiceDetail.type" ],
          orderBy: [ ],
          
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
        
      var now = moment();
      query.groupByDate(reportState.dateGroupBy) ;
      query.rangeByDate(reportState.fromDate, reportState.toDate) ;
      query.join("InvoiceDetail.activityType = ':param'", reportState.activityType);
      
      var reportTable = service.AccountingService.reportQuery([query]).data ;
      thisUI.setReportData(reportTable[0]) ;
    }
  };
  
  return InvoiceCashier ;
});
