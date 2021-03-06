define([
  'jquery', 'underscore', 'backbone', 'moment', 'service/service', 'util/SQL',
], function($, _, Backbone, moment, service, sql) {
  var InvoiceProduct =  {
    uiconfig : {
      label: "Invoice Product", name: "invoiceProduct",
      options: [
        { 
          label: "Activity Type", name: "activityType",
          options: [
            {label: "All", value: ""},
            {label: "Payment", value: "Payment"},
            {label: "Receipt", value: "Receipt"}
          ]
        },
        { label: "Product", name: "product" }
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
            if(reportData.hasField('Month')) keyFields.push('Month') 
            keyFields.push("Product");
            keyFields.push("Quantity");
            reportData.groupByCompare(keyFields, "Activity Type", "Final Charge") ;
          }
        },
      ]
    },
    
    onReport: function(thisUI, reportState) {
      var query = new sql.SelectQuery({
        tables: ['InvoiceDetail', "InvoiceItem", 'InvoiceItemAttribute'],
        fields: [
          { expression: "InvoiceItemAttribute.value", alias: "Product" },
          { expression: "InvoiceDetail.activityType", alias: "Activity Type" },
          { expression: "sum(InvoiceItem.quantity)", alias: "Quantity", type: "integer"},
          { expression: "sum(InvoiceItem.total)", alias: "Total", type: "number" },
          { expression: "sum(InvoiceItem.discountByInvoice)", alias: "Discount By Invoice", type: "number" },
          { expression: "sum(InvoiceItem.discount)", alias: "Discount", type: "number" },
          { expression: "sum(InvoiceItem.tax)", alias: "Tax", type: "number" },
          { expression: "sum(InvoiceItem.finalCharge)", alias: "Final Charge", type: "number" }
        ],
        joins: ["InvoiceItem.invoiceId = InvoiceDetail.id", "InvoiceItemAttribute.invoiceItemId = InvoiceItem.id"],
        groupBy: ["InvoiceItemAttribute.value", "InvoiceDetail.activityType" ],
        orderBy: ["InvoiceItemAttribute.value"],
        
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
      query.join("InvoiceItemAttribute.value LIKE '%:param%'", reportState.product);
      query.join("InvoiceItemAttribute.name LIKE ':param'", "Product");
      
      var reportTable = service.AccountingService.reportQuery([query]).data ;
      thisUI.setReportData(reportTable[0]) ;
    }
  };
  
  return InvoiceProduct ;
});
