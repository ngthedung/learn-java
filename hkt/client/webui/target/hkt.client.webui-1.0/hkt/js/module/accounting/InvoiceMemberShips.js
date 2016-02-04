/**
 *  Edit By Bui Trong Hieu
 */
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
  'ui/UIPopup',
  'service/service',
  'util/SQL',
], function($, _, Backbone, DateTime, module, UIBreadcumbs, 
            UICollabsible, UIBean, UITable, UITreeTable,UIPopup, service,sql) {

  var UIInvoiceMemberShip = UITreeTable.extend({
    label: "Thống kê bán hàng",
    config: {
      toolbar: {
        dflt: {
          actions: [
            {
            	action: "onNewInvoice", icon: "add", label: "Thống kê",
            	onClick: function(thisUI){
            		
            	}
            },
            {
              action: "onRefresh", icon: "refresh", label: "Refresh", 
              onClick: function(thisUI) { 
            	  thisUI.onRefresh();
              } 
            }
          ]
        }
      },
      
      bean: {
        label: 'Thống kê',
        fields: [
          { 
        	  field: "final", label: "Tổng thu", defaultValue: 'default value', 
            toggled: true, filterable: true
          },
          { 
        	  field: "total", label: "Thực thu", defaultValue: 'default value', 
            toggled: true, filterable: true
          },
        ]
      }
    },
    
    addChild: function(node) {
    	var bean = node.bean;
      if(node.getChildren().length==0 && bean.parent ==null){
    	 var query = new sql.SelectQuery({
              tables: 
            	  ["((SELECT a.total  as `total`, b.final as `final`, (CASE WHEN (a.path1 IS NULL) THEN b.path1 ELSE a.path1 END) AS `path3` " +
                   "FROM (SELECT SUBSTRING_INDEX(i.projectCode, '/', -1) AS `path1`, SUM(i.total * i.currencyRate) AS `total` " +
                   "FROM InvoiceDetail i  " +
                   "WHERE i.ActivityType = 'Receipt' " +
                   "GROUP BY  path1 ) AS `a` " +
                   "RIGHT JOIN " +
                   "(SELECT SUBSTRING_INDEX(t.projectCode, '/', -1) AS `path1`, SUM(t.total * t.currencyRate) AS `final` " +
                   "FROM InvoiceTransaction t " +
                   "WHERE  t.ActivityType = 'Receipt' " +
                   "GROUP BY  path1 ) AS `b` on a.path1 = b.path1) " +
                   "UNION " +
                   "(SELECT a.total  as `total`, b.final as `final`, (CASE WHEN (a.path1 IS NULL) THEN b.path1 ELSE a.path1 END) AS `path3` " +
                   "FROM " +
                   "(SELECT SUBSTRING_INDEX(i.projectCode, '/', -1) AS `path1`, SUM(i.total * i.currencyRate) AS `total`  " +
                   "FROM InvoiceDetail i WHERE i.ActivityType = 'Receipt' " +
                   "GROUP BY  path1 ) AS `a` " +
                   "LEFT JOIN " +
                   "(SELECT SUBSTRING_INDEX(t.projectCode, '/', -1) AS `path1`, SUM(t.total * t.currencyRate) AS `final` " +
                   "FROM InvoiceTransaction t " +
                   " WHERE t.ActivityType = 'Receipt' " +
                   "GROUP BY  path1 ) AS `b` on a.path1 = b.path1))  AS `ec` " +
                   "INNER JOIN restaurant_project rp ON ec.path3 = rp.code " +
                   " INNER JOIN restaurant_projectMember rpm ON rpm.projectId = rp.id "],
              fields: [
                { expression: "rpm.employeeCode AS `code3`", alias: "parent" },
                { expression: "(SELECT e.name FROM employee e WHERE e.code = `code3` ) AS `node`", alias: "node" },
                { expression: "COALESCE(sum(ec.final * (rpm.percent/100)), 0) as `final`", alias: "final" },
                { expression: "COALESCE(sum(ec.total * (rpm.percent/100)), 0) as `total`", alias: "total" }
              ],
    	      joins: [],
              groupBy: ["rpm.employeeCode "],
              orderBy: ["`node` "],
            });
            query.join("ec.path3 NOT LIKE 'project-other' ");
            console.log([query]);
            var reportTable = service.AccountingService.reportQuery([query]).data ;
            var records = [] ;
            for(var i = 0; i < reportTable[0].records.length; i++) {
              var record = reportTable[0].records[i];
              records[i] = record ;
              node.addChild(records[i], records[i]["node"]);
            }
         }
         else{
        	 var query = new sql.SelectQuery({
                 tables: 
               	  ["((SELECT a.total  as `total`, b.final as `final`, (CASE WHEN (a.path1 IS NULL) THEN b.path1 ELSE a.path1 END) AS `path3` " +
               	  		"FROM " +
               	  		"(SELECT SUBSTRING_INDEX(i.projectCode, '/', -1) AS `path1`, SUM(i.total * i.currencyRate) AS `total` " +
               	  		"FROM InvoiceDetail i " +
               	  		" WHERE i.ActivityType = 'Receipt' " +
               	  		"GROUP BY  path1 ) AS `a` " +
               	  		"RIGHT JOIN " +
               	  		"(SELECT SUBSTRING_INDEX(t.projectCode, '/', -1) AS `path1`, SUM(t.total * t.currencyRate) AS `final` " +
               	  		"FROM InvoiceTransaction t WHERE t.ActivityType = 'Receipt' " +
               	  		"GROUP BY  path1 ) AS `b` ON a.path1 = b.path1) " +
               	  		"UNION " +
               	  		"(SELECT  a.total  as `total`, b.final as `final`, " +
               	  		"(CASE WHEN (a.path1 IS NULL) THEN b.path1 ELSE a.path1 END) AS `path3`" +
               	  		"FROM (SELECT SUBSTRING_INDEX(i.projectCode, '/', -1) AS `path1`, SUM(i.total * i.currencyRate) AS `total` " +
               	  		"FROM InvoiceDetail i " +
               	  		"WHERE i.ActivityType = 'Receipt' " +
               	  		"GROUP BY  path1 ) AS `a` " +
               	  		"LEFT JOIN " +
               	  		"(SELECT SUBSTRING_INDEX(t.projectCode, '/', -1) AS `path1`, SUM(t.total * t.currencyRate) AS `final` " +
               	  		"FROM InvoiceTransaction t " +
               	  		"WHERE t.ActivityType = 'Receipt' " +
               	  		"GROUP BY  path1 ) AS `b` " +
               	  		"ON a.path1 = b.path1) ) AS `ec1` " +
               	  		"INNER JOIN " +
               	  		"(SELECT rp.code AS `path1`, rp.name AS `name`, " +
               	  		"(SELECT e.name FROM employee e WHERE e.code = rpm.employeeCode ) AS `node`, " +
               	  		"rpm.percent AS `percent`, " +
               	  		"rpm.employeeCode as `ecode` " +
               	  		"from restaurant_project rp " +
               	  		"INNER JOIN restaurant_projectMember rpm ON rpm.projectId = rp.id " +
               	  		"WHERE rpm.employeeCode = '"+ bean.parent +"') AS `ec2`ON ec1.path3 = ec2.path1 "],
                 fields: [
                   { expression: "(CASE WHEN (ec1.path3 IS NULL) THEN ec2.path1 ELSE ec1.path3 END) AS `path4`", alias: "parent" },
                   { expression: "(SELECT p.name FROM  restaurant_project p WHERE p.code = `path4`) AS `node`", alias: "node" },
                   { expression: "COALESCE(SUM(ec1.final), 0) as `final`", alias: "final" },
                   { expression: "COALESCE(SUM(ec1.total), 0) as `total`", alias: "total" }
                 ],
       	      joins: [],
                 groupBy: ["(CASE WHEN (ec1.path3 IS NULL) THEN ec2.path1 ELSE ec1.path3 END)  "],
                 orderBy: ["`node` "],
               });
//               query.join("ec.path3 NOT LIKE 'project-other' ");
             
             var reportTable = service.AccountingService.reportQuery([query]).data ;
             var records = [] ;
             for(var i = 0; i < reportTable[0].records.length; i++) {
                 var record = reportTable[0].records[i];
                 records[i] = record ;
                 node.addChild(records[i], records[i]["node"]);
               }
         }
      },
//      onReportProduct: function(node) {
//    	  var bean = node.bean;
//    	  var query = new sql.SelectQuery({
//              tables: 
//            	  ["((SELECT a.total  as `total`, b.final as `final`, (CASE WHEN (a.path1 IS NULL) THEN b.path1 ELSE a.path1 END) AS `path3` " +
//            	  		"FROM " +
//            	  		"(SELECT SUBSTRING_INDEX(i.projectCode, '/', -1) AS `path1`, SUM(i.total * i.currencyRate) AS `total` " +
//            	  		"FROM InvoiceDetail i " +
//            	  		" WHERE i.ActivityType = 'Receipt' " +
//            	  		"GROUP BY  path1 ) AS `a` " +
//            	  		"RIGHT JOIN " +
//            	  		"(SELECT SUBSTRING_INDEX(t.projectCode, '/', -1) AS `path1`, SUM(t.total * t.currencyRate) AS `final` " +
//            	  		"FROM InvoiceTransaction t WHERE t.ActivityType = 'Receipt' " +
//            	  		"GROUP BY  path1 ) AS `b` ON a.path1 = b.path1) " +
//            	  		"UNION " +
//            	  		"(SELECT  a.total  as `total`, b.final as `final`, " +
//            	  		"(CASE WHEN (a.path1 IS NULL) THEN b.path1 ELSE a.path1 END) AS `path3`" +
//            	  		"FROM (SELECT SUBSTRING_INDEX(i.projectCode, '/', -1) AS `path1`, SUM(i.total * i.currencyRate) AS `total` " +
//            	  		"FROM InvoiceDetail i " +
//            	  		"WHERE i.ActivityType = 'Receipt' " +
//            	  		"GROUP BY  path1 ) AS `a` " +
//            	  		"LEFT JOIN " +
//            	  		"(SELECT SUBSTRING_INDEX(t.projectCode, '/', -1) AS `path1`, SUM(t.total * t.currencyRate) AS `final` " +
//            	  		"FROM InvoiceTransaction t " +
//            	  		"WHERE t.ActivityType = 'Receipt' " +
//            	  		"GROUP BY  path1 ) AS `b` " +
//            	  		"ON a.path1 = b.path1) ) AS `ec1` " +
//            	  		"INNER JOIN " +
//            	  		"(SELECT rp.code AS `path1`, rp.name AS `name`, " +
//            	  		"(SELECT e.name FROM employee e WHERE e.code = rpm.employeeCode ) AS `node`, " +
//            	  		"rpm.percent AS `percent`, " +
//            	  		"rpm.employeeCode as `ecode` " +
//            	  		"from restaurant_project rp " +
//            	  		"INNER JOIN restaurant_projectMember rpm ON rpm.projectId = rp.id " +
//            	  		"WHERE rpm.employeeCode = '"+ bean.parent +"') AS `ec2`ON ec1.path3 = ec2.path1 "],
//              fields: [
//                { expression: "(CASE WHEN (ec1.path3 IS NULL) THEN ec2.path1 ELSE ec1.path3 END) AS `path4`", alias: "parent" },
//                { expression: "(SELECT p.name FROM  restaurant_project p WHERE p.code = `path4`) AS `node`", alias: "node" },
//                { expression: "COALESCE(SUM(ec1.final), 0) as `final`", alias: "final" },
//                { expression: "COALESCE(SUM(ec1.total), 0) as `total`", alias: "total" }
//              ],
//    	      joins: [],
//              groupBy: ["(CASE WHEN (ec1.path3 IS NULL) THEN ec2.path1 ELSE ec1.path3 END)  "],
//              orderBy: ["`node` "],
//            });
////            query.join("ec.path3 NOT LIKE 'project-other' ");
//          
//          var reportTable = service.AccountingService.reportQuery([query]).data ;
//          var records = [] ;
//          for(var i = 0; i < reportTable[0].records.length; i++) {
//              var record = reportTable[0].records[i];
//              records[i] = record ;
//              node.addChild(records[i], records[i]["node"]);
//            }
//        },
    
    onReport: function(invoice) {
    	this.startDate = invoice.startDate;
        this.endDate = invoice.endDate;
        var d = DateTime.parseDate(this.startDate);
    	  d.setDate(d.getDate()-1);
    	  d.setHours(23);
    	  d.setMinutes(59);
    	  var d1 = DateTime.parseDate(this.endDate);
    	  d1.setHours(23);
    	  d1.setMinutes(59);
    	  var start = DateTime.formatDateSql(d);
    	  var end = DateTime.formatDateSql(d)
    	var activityType = invoice.activityType;
        var query = new sql.SelectQuery({
          tables: ['InvoiceDetail i INNER JOIN InvoiceTransaction it ON it.invoiceId = i.id'],
          fields: [
            { expression: "COALESCE(SUM(it.total * it.currencyRate),0) AS `total`", alias: "total" },
            { expression: "COALESCE(SUM(i.finalCharge * i.currencyRate),0) AS `final`", alias: "final" },
          ],
          joins: [],
          groupBy: [],
          orderBy: [],
        });
        query.join("i.startDate < :param", end);
        query.join("i.startDate > :param", start);
        query.join("i.activityType = ':param'", activityType);
        console.log(query);
        var reportTable = service.AccountingService.reportQuery([query]).data ;
        var records = [] ;
        for(var i = 0; i < reportTable[0].records.length; i++) {
            var record = reportTable[0].records[i];
            console.log(record);
            records[i] = record ;
            this.addNode(records[i], "HKT");
          }
      }
  });
  
    
  var UIStatisticalFilters = UIBean.extend({
	    label: 'Lọc',
	    config: {
	      beans: {
	        invoice: {
	          label: 'Lọc',
	          name: 'invoice',
	          fields: [  
              { field : "startDate", label : "Từ ngày",
                datepicker: {
                  getDate: function(bean) {
                    return DateTime.fromDateTimeToDDMMYYYY(bean.startDate) ;
                  },
                  setDate: function(date, bean) {
                    bean.startDate = DateTime.fromDDMMYYYYToDateTime(date) ;
                  },
                }
              },
              { 
                field : "endDate", label : "Đến ngày",
                datepicker: {
                  getDate: function(bean) {
                    return DateTime.fromDateTimeToDDMMYYYY(bean.endDate) ;
                  },
                  setDate: function(date, bean) {
                    bean.endDate = DateTime.fromDDMMYYYYToDateTime(date) ;
                  },
                }
              },
              {
                field : "activityType", label : "Loại",
                defaultValue: "Receipt",
                select: {
                    getOptions: function(field, bean) {
                      var options = [
                        { label: "Thu", value: 'Receipt' },
                        { label: "Chi", value: 'Payment' },
                      ];
                      return options ;
                    }
                  }
                  
                },
	          ],
	          edit: {
	              disable: false , 
	              actions: [
	                {
	                  action:'save', label: "Đồng ý", icon: "check",
	                  onClick: function(thisUI) { 
	                	  
	                	  var invoice = thisUI.getBean('invoice') ;
	                	  this.viewStack = new UIBreadcumbs({el: "#workspace"}) ;
	                	  thisUI.InvoiceMemberShip.onReport(invoice);
	                      this.viewStack.push(thisUI.InvoiceMemberShip) ;
	                	  UIPopup.closePopup() ;
	                  }
	                },
	                {
	                  action:'cancel', label: "Thoát", icon: "back",
	                  onClick: function() { 
	                    UIPopup.closePopup() ;
	                  }
	                }
	              ],
	            }
	        }
	      }
	    },
	    
	    init: function(UIParent, attribute, isNew) {
	      this.isNew = isNew;
	      this.UIParent = UIParent ;
	      this.InvoiceMemberShip = new UIInvoiceMemberShip();
	      this.bind('invoice', attribute, true) ;
	      if(isNew){ this.getBeanState('invoice').editMode = true ; }
	      return this ;
	    }
	  });
  
  var InvoiceMemberShips = module.UIScreen.extend({
    initialize: function (options) {
    },

    activate: function() { 
      this.viewStack = new UIBreadcumbs({el: "#workspace"}) ;
      
      var invoice = { startDate : DateTime.fromDateTimeToDDMMYYYY(DateTime.getCurrentDate()), 
  			endDate : DateTime.fromDateTimeToDDMMYYYY(DateTime.getCurrentDate()) };
      
//      var uiTreeTableSample = new UIInvoiceMemberShip() ;
//      uiTreeTableSample.onReport();
      UIPopup.activate(new UIStatisticalFilters().init(this, invoice, true)) ;
    },
    
    deactivate: function() { }
    
  });
  
  return InvoiceMemberShips ;
});
