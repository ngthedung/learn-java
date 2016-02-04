define([
  'jquery', 'underscore', 'backbone', 'moment', 'service/service', 'util/SQL',
], function($, _, Backbone, moment, service, sql) {
  var WarehouseInventory =  {
    uiconfig : {
      label: "Warehouse Inventory", name: "warehouseInventory",
      options: [ ],
      actions: [
        { 
          name: 'report', label: 'Report',
          onClick: function(thisUI, thisReport, reportState) {
            thisReport.onReport(thisUI, reportState) ;
          }
        }
      ]
    },
    
    onReport: function(thisUI, reportState) {
      var query = new sql.SelectQuery({
          tables: ['Inventory', 'InInventory', 'OutInventory', "Product"],
          fields: [
            { expression: "Inventory.warehouseCode", alias: "Warehouse ID" },
            { expression: "Product.name", alias: "Product" },
            { expression: "sum(InInventory.quantity)", alias: "In Quantity", type: "integer" },
            { expression: "sum(InInventory.checkinQuantity)", alias: "CheckinQuantity", type: "integer" },
            { expression: "sum(InInventory.total)", alias: "In Total", type: "integer" },
            { expression: "sum(InInventory.finalCharge)", alias: "In Final Charge", type: "integer" },
            { expression: "sum(OutInventory.quantity)", alias: "Out Quantity", type: "integer" },
            { expression: "sum(OutInventory.checkoutQuantity)", alias: "CheckoutQuantity", type: "integer" },
            { expression: "sum(Inventory.inStock)", alias: "In Stock" },
            { expression: "sum(OutInventory.total)", alias: "Out Total", type: "integer" },
            { expression: "sum(OutInventory.finalCharge)", alias: "Out Final Charge", type: "integer" }
          ],
          joins: [ "InInventory.inventoryId = Inventory.id", "OutInventory.inventoryId = Inventory.id", 
                   "OutInventory.inInventoryId = InInventory.id", "Product.id = Inventory.product_id" ],
          groupBy: [ "Inventory.warehouseCode", "Product.code" ],
          orderBy: [ ],
          
          groupByDate: function(type) {
            var year = { 
              expression: "EXTRACT(YEAR FROM InInventory.validFromDate) AS year", alias: "Year" 
            };
            var month = { 
              expression: "EXTRACT(MONTH FROM InInventory.validFromDate) AS month", alias: "Month" 
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
              this.join("InInventory.validFromDate >= ':param'", fdate);
            }
            if(!$.isEmptyObject(to)) {
              var tdate = moment(to, "DD/MM/YYYY").format('YYYY-MM-DD 23:59:59') ;
              this.join("InInventory.validFromDate <= ':param'", tdate);
            }
          }
      });
        
      var now = moment();
      query.groupByDate(reportState.dateGroupBy) ;
      query.rangeByDate(reportState.fromDate, reportState.toDate) ;
      
      var reportTable = service.AccountingService.reportQuery([query]).data ;
      thisUI.setReportData(reportTable[0]) ;
    }
  };
  
  return WarehouseInventory ;
});
