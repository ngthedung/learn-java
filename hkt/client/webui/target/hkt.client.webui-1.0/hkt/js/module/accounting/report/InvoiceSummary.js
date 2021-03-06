define([
  'jquery', 'underscore', 'backbone', 'moment', 'service/service', 'util/SQL',
], function($, _, Backbone, moment, service, sql) {
  var InvoiceSummary =  {
    uiconfig : {
      label: "Summary", name: "invoiceSummary",
      
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
            if(reportData.hasField('Month')) keyFields.push('Month') 
            keyFields.push("Invoice Type");
            keyFields.push("Status");
            reportData.groupByCompare(keyFields, "Activity Type", "Final Charge") ;
          }
        },
      ],
      
      options: [
        { 
          label: "Activity Type", name: "activityType",
          options: [
            {label: "All", value: ""},
            {label: "Payment", value: "Payment"},
            {label: "Received", value: "Received"}
          ]
        },
        { 
          label: "Invoice Status", name: "status",
          options: [
            {label: "All", value: ""},
            {label: "Paid", value: "Paid"},
            {label: "PartiallyPaid", value: "PartiallyPaid"}
          ]
        },
        { label: "Invoice Type", name: "invoiceType" }
      ]
    },
    
    onReport: function(thisUI, reportState) {
      var query = new sql.SelectQuery({
        tables: ['InvoiceDetail'],
        fields: [
          { expression: "InvoiceDetail.type", alias: "Invoice Type" },
          { expression: "InvoiceDetail.activityType", alias: "Activity Type" },
          { expression: "InvoiceDetail.status", alias: "Status" },
          { expression: "count(InvoiceDetail.type)", alias: "Count", type: "integer"},
          { expression: "sum(InvoiceDetail.total)", alias: "Total", type: "number" },
          { expression: "sum(InvoiceDetail.discountByItem)", alias: "Discount By Item", type: "number" },
          { expression: "sum(InvoiceDetail.discount)", alias: "Discount", type: "number" },
          { expression: "sum(InvoiceDetail.totalTax)", alias: "Tax", type: "number" },
          { expression: "sum(InvoiceDetail.finalCharge)", alias: "Final Charge", type: "number" },
          { expression: "sum(InvoiceDetail.totalPaid)", alias: "Paid", type: "number" }
        ],
        joins: [ ],
        groupBy: [
          "InvoiceDetail.type", "InvoiceDetail.status", "InvoiceDetail.activityType"
        ],
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
      query.join("InvoiceDetail.status = ':param'", reportState.status);
      query.join("InvoiceDetail.type LIKE ':param'", reportState.invoiceType);
      
      var reportTable = service.AccountingService.reportQuery([query]).data ;
      var reportData = thisUI.createReportDataByQueryResult(reportTable[0]) ;
    }
  };
  
  return InvoiceSummary ;
});
