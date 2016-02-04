define([
  'jquery', 
  'underscore', 
  'backbone',
  'util/DateTime',
  'module/Module',
  'ui/UIBreadcumbs',
  'ui/UICollapsible',
  'ui/UIBean',
  'ui/UITable',
  'ui/UITreeTable',
  'service/service',
  'util/SQL',
], function($, _, Backbone, DateTime, module, UIBreadcumbs, 
            UICollabsible, UIBean, UITable, UITreeTable,service,sql) {

  var UITreeTableSample = UITreeTable.extend({
    label: "Thống kê bán hàng",
    config: {
      toolbar: {
        dflt: {
          actions: [
            {
              action: "onRefresh", icon: "refresh", label: "Refresh", 
              onClick: function(thisTable) { 
              } 
            }
          ]
        }
      },
      
      bean: {
        label: 'Thống kê',
        fields: [
          { 
            field: "quantity",   label: "Số lượng", defaultValue: 'default value', 
            toggled: true, filterable: true
          },
          { 
            field: "avg",   label: "Đơn giá trung bình", defaultValue: 'default value', 
            toggled: true, filterable: true
          },
          { 
            field: "final",   label: "Tổng thu", defaultValue: 'default value', 
            toggled: true, filterable: true
          }
        ]
      }
    },
    addChild: function(node) {
    	var bean = node.bean;
      if(node.getChildren().length==0 && bean.product ==null){
    	  var query = new sql.SelectQuery({
              tables: ["InvoiceItem i JOIN Product p ON p.code = i.productCode LEFT JOIN ProductAttribute a ON a.productId = p.id"],
              fields: [
                { expression: "SUBSTRING_INDEX(a.value, ':', " + (node.index + 1) + ") AS `parent`", alias: "parent" },
                { expression: "(SELECT w.label FROM warehouse_productTag w WHERE w.code = SUBSTRING_INDEX(`parent`, ':', -1)) AS `node`", alias: "node" },
                { expression: "(SELECT w.tag FROM warehouse_productTag w WHERE w.code = `parent`) AS `tag`", alias: "tag" },
                { expression: "SUM(i.finalCharge * i.currencyRate) AS `final`", alias: "final" },
                { expression: "SUM(i.quantity) AS `quantity`", alias: "quantity" },
                { expression: "SUM(i.finalCharge * i.currencyRate)/SUM(i.quantity)", alias: "avg" },
              ],
    	      joins: [],
              groupBy: ["`parent`"],
              orderBy: [],
            });
            query.join("a.name = ':param'", "Hàng hóa");
            query.join("i.activityType = ':param'", "Receipt");
            if(bean.parent!=null){
            	query.join("(LENGTH(SUBSTRING_INDEX(a.value, ':', " + (node.index + 1) + ")) - LENGTH(REPLACE(SUBSTRING_INDEX(a.value, ':', "
            	        + (node.index + 1) + "), ':', ''))) >= ':param'", (node.index));
            	query.join("SUBSTRING_INDEX(a.value, ':', " + (node.index) + ") = ':param'", bean.parent);
            	
      	    }
            
            var reportTable = service.AccountingService.reportQuery([query]).data ;
            var records = [] ;
            for(var i = 0; i < reportTable[0].records.length; i++) {
              var record = reportTable[0].records[i];
              records[i] = record ;
              node.addChild(records[i], records[i]["node"]);
            }
            if(bean.parent!=null){
            	this.onReportProduct(node);
            }
         }
      },
      onReportProduct: function(node) {
    	  var bean = node.bean;
          var query = new sql.SelectQuery({
            tables: ["InvoiceItem i " + "INNER JOIN product p ON p.code = i.productCode "
 		            + "INNER JOIN product_productTag t ON t.productId = p.Id "
 		            + "INNER JOIN warehouse_productTag w ON w.id = t.productTagId"],
            fields: [
              { expression: "SUM(i.finalCharge * i.currencyRate) AS `finalCharge`", alias: "final" },
              { expression: "SUM(i.quantity) AS `quantity`", alias: "quantity" },
              { expression: "SUM(i.finalCharge * i.currencyRate)/SUM(i.quantity)", alias: "avg" },
              { expression: "p.name AS `node`", alias: "node" },
              { expression: "p.code AS `product`", alias: "product" }
            ],
            joins: [],
            groupBy: ["i.productCode"],
            orderBy: [],
          });
          query.join("i.activityType = ':param'", "Receipt");
          query.join("w.tag = ':param'", bean.parent);
          
          var reportTable = service.AccountingService.reportQuery([query]).data ;
          var records = [] ;
          for(var i = 0; i < reportTable[0].records.length; i++) {
              var record = reportTable[0].records[i];
              records[i] = record ;
              node.addChild(records[i], records[i]["node"]);
            }
        },
    
    onReport: function() {
        var query = new sql.SelectQuery({
          tables: ['InvoiceItem i'],
          fields: [
            { expression: "SUM(i.finalCharge * i.currencyRate) AS `finalCharge`", alias: "final" },
            { expression: "SUM(i.quantity) AS `quantity`", alias: "quantity" },
            { expression: "SUM(i.finalCharge * i.currencyRate)/SUM(i.quantity)", alias: "avg" }
          ],
          joins: [],
          groupBy: [],
          orderBy: [],
        });
        query.join("i.activityType = ':param'", "Receipt");
        
        var reportTable = service.AccountingService.reportQuery([query]).data ;
        var records = [] ;
        for(var i = 0; i < reportTable[0].records.length; i++) {
            var record = reportTable[0].records[i];
            records[i] = record ;
            this.addNode(records[i], "HKT");
          }
      }
  });
  
  var InvoiceRestaurant = module.UIScreen.extend({
    initialize: function (options) {
    },

    activate: function() { 
      this.viewStack = new UIBreadcumbs({el: "#workspace"}) ;
      
      var uiTreeTableSample = new UITreeTableSample() ;
      uiTreeTableSample.onReport();
//      var productTags = service.ProductService.getRootTags().data ;
//      for(var i = 0; i<productTags.length; i++)
//	  { 
//    	  uiTreeTableSample.addNode(productTags[i], productTags[i].label);
//	  }

      this.viewStack.push(uiTreeTableSample) ;
    },
    
    deactivate: function() { }
    
  });
  
  return InvoiceRestaurant ;
});
