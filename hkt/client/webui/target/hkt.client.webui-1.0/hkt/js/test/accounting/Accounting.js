define([
  'jquery',
  'service/service',
  'test/Test',
  'test/Site',
  'test/Assert',
], function($, service, test, Site, Assert) {
  
  var CleanDB = new test.UnitTask({
    name: "CleanAccountingModule",
    description: "Drop all the data in the accounting module",
    units: [
      function() { 
        service.AccountingService.cleanData() ;
      }
    ]
  });
  
  var BankAccountApicCRUD = new test.UnitTask({
    name : "BankAccountCRUD",
    description : "create/get/delete an BankAccount",
    units : [
      function() {
        var bankAccount = {
        bankAccountId: "vcb-credit-hanoi-ngoquyen", currency: "vnd", type: "CreditCard",
        address: "33 Ngo Quyen, Hai Ba Trung, Ha Noi"
        };
        bankAccount = service.AccountingService.saveBankAccount(bankAccount).data ;
        Assert.assertNotNull(bankAccount) ;
        Assert.assertEquals("vcb-credit-hanoi-ngoquyen", bankAccount.bankAccountId) ;
        
        service.AccountingService.deleteBankAccountByAccountId("vcb-credit-hanoi-ngoquyen") ;
        
        bankAccount = service.AccountingService.getBankAccountById("vcb-credit-hanoi-ngoquyen").data;
        Assert.assertNull(bankAccount);
      },
    ]
  });
  
  var InvoiceApiCRUD = new test.UnitTask({
    name : "InvoiceApiCRUD",
    description : "test call create/get/delete an Invoice",
    units : [
      function() {
        var bankAccount = {
          bankAccountId: "vcb-credit-thangpm", currency: "vnd", type: "CreditCard",
          address: "33 Ngo Quyen, Hai Ba Trung, Ha Noi"
        };
        bankAccount = service.AccountingService.saveBankAccount(bankAccount).data ;
        Assert.assertNotNull(bankAccount) ;
        Assert.assertEquals("vcb-credit-thangpm", bankAccount.bankAccountId) ;
        
        var invoice = {
          type: "SAL", invoiceCode: "SALHJ-1", activityType: "Payment", total: 10000000,
          currencyUnit: "VND", status: "Paid",
          description: "Tra luong", note: "Tra luong thang 12"
        } ;
        
        var transaction = {
          transactionType: "Wire", transactionDate: "20/12/2013 08:08:08 GMT+0700", total: 10000000,
          currencyUnit: "VND", description: "Chuyen khoan tra luong", note: "Chuyen khoan"
        } ;
        
        var item = {
          itemName: "SAL-12-MONTHLY", quantity: 1, unitPrice: 10000, discount: 0, tax: 0, total: 9000000,
          currencyUnit: "VND", description: "thue thu nhap ca nhan 10%",
          reference: "entity:hr/Salary/.."
        };
        
        var items = new Array(item);
        
        var transactions = new Array(transaction);
       
        var invoiceDetail = {invoice: invoice , items: items , transactions: transactions};
        invoiceDetail = service.AccountingService.saveInvoiceDetail(invoiceDetail).data;
        Assert.assertNotNull(invoiceDetail) ;
        invoice = invoiceDetail.invoice;
        Assert.assertNotNull(invoice) ;
        Assert.assertEquals("SALHJ-1", invoice.invoiceCode) ;
        transaction = invoiceDetail.transactions[0];
        Assert.assertNotNull(transaction) ;
        Assert.assertEquals(invoice.id, transaction.invoiceId) ;
        item = invoiceDetail.items[0];
        Assert.assertNotNull(item) ;
        Assert.assertEquals(invoice.id, item.invoiceId) ;
        var res = service.AccountingService.deleteInvoiceById(invoice.id) ;
        Assert.assertTrue(res.data) ;
        
      },
    ]
  });
  
  var GoToAccountingInvoicesScreen = new test.UnitTask({
    name: "GoToAccountingInvoiceScreen",
    description: "Click on accounting",
    units: [
      function() { Site.Navigation.clickMenuItem("Accounting ", "Invoice") ; },
    ]
  });
  
  var CRUDTestInvoiceTask = new test.UnitTask({
    name: "CRUDTestInvoiceTask",
    description: "Create test invoice, edit test invoice, delete test invoice",
    units: [
      function() {
        Site.Workspace.tableToolbar('Type').clickButton("New");
        var form = Site.Workspace.formWithText("Type:") ;
        form.inputVal("type", "PROMOTION");
        form.inputVal("invoiceCode", "SALHJ-1");
        form.inputVal("activityType", "Payment");
        form.inputVal("total", 1000000);
        form.inputVal("discount", 500000);
        form.inputVal("totalPaid", 500000);
        form.inputVal("discountRate", 0);
        form.inputVal("totalTax", 0);
        form.inputVal("finalCharge", 500000);
        form.inputVal("currencyUnit", "VND");
        form.inputVal("status", "Paid");
        form.inputVal("note", "Thanh toan");
        form.inputVal("description", "Gap khach hang");
        form.inputVal("startDate", "28/3/2013 08:08:08 GMT+0700");
        form.inputVal("endDate", "28/3/2013 08:20:08 GMT+0700");
      },
      function() {
        Site.Workspace.tableToolbar('Item Code').clickButton("New Item");
        var form = Site.PopupPanel.formWithText("Item Code:") ;
        form.inputVal("itemCode", "SAL-12-MONTHLY");
        form.inputVal("itemName", "SAL-12-MONTHLY");
        form.inputVal("quantity", 1);
        form.inputVal("unitPrice", 1000000);
        form.inputVal("discount", 500000);
        form.inputVal("tax", 0);
        form.inputVal("total", 500000);
        form.inputVal("finalCharge", 500000);
        form.inputVal("currencyUnit", "VND");
        form.inputVal("description", "thue thu nhap ca nhan 10%");
        Site.PopupPanel.formWithText("Item Code:").clickButton("Save");
      },
      function() {
        Site.Workspace.tableToolbar('Bank Account Id').clickButton("New Transaction");
        var form = Site.PopupPanel.formWithText("Bank Account Id:") ;
        form.inputVal("createdBy", "$@thang");
        form.inputVal("bankAccountId", "$@vcb-ngoquyen");
        form.inputVal("transactionType", "Wire");
        form.inputVal("transactionDate", "20/12/2013 08:08:08 GMT+0700");
        form.inputVal("total", 500000);
        form.inputVal("currencyUnit", "VND");
        form.inputVal("note", "Chuyen khoan");
        form.inputVal("description", "Gap khach hang");
        Site.PopupPanel.formWithText("Bank Account Id:").clickButton("Save");
      },
      function() {
        Site.Workspace.toolbarWith('Invoices').clickButton("Save");
      },      
      //
      function() { Site.Workspace.tableToolbars('Invoice Code').clickButton("More Toolbars"); } ,
      function() { Site.Workspace.tableToolbars('Invoice Code').clickButton("More Toolbars"); } ,
      function() {
        var controlGroup = Site.Workspace.tableToolbar("Filter") ;
        controlGroup.inputVal('type', "S*") ;
      },
      function() { Site.Workspace.tableToolbars('Invoice Code').clickButton("Filter"); } ,
      //EDIT
      function() {
        var table = Site.Workspace.tableWithHeader("Invoice Code") ;
        var row   = table.tableRowWithText("SAL2") ;
        row.clickLink("SAL2") ;
      },
      function() {
        var invoiceBlock = Site.Workspace.collapsibleBlock("Invoice") ;
        invoiceBlock.toolbarWith("Edit").clickButton("Edit");
        var form = invoiceBlock.formWithText("Type:") ;
        form.inputVal("total", 20000000);
        
      },
      function() {
        var table = Site.Workspace.tableWithHeader("Item Name") ;
        var row   = table.tableRowWithText("SAL2-item 0") ;
        row.clickButton("Edit Invoice Item") ;
        var form = Site.PopupPanel.formWithText("Item Name:") ;
        form.inputVal("quantity", 2);
        Site.PopupPanel.formWithText("Item Name:").clickButton("Save");
      },
      function() {
        var table = Site.Workspace.tableWithHeader("Bank Account Id") ;
        var row   = table.tableRowWithText("vcb-ngoquyen") ;
        row.clickButton("Edit Transaction") ;
        var form = Site.PopupPanel.formWithText("Bank Account Id:") ;
        form.inputVal("total", 20000000);
        Site.PopupPanel.formWithText("Bank Account Id:").clickButton("Save");
      },
      function() {
        Site.Workspace.toolbarWith('Invoice').clickButton("Save");
      }
    ]
  });
  
  var SearchInvoiceTask = new test.UnitTask({
    name: "SearchInvoiceTask",
    description: "Search invoice",
    units: [
      function() { Site.Navigation.clickMenuItem("accounting", "Invoice") ; } ,
      function() { Site.Workspace.tableToolbars('Invoice Code').clickButton("More Toolbars"); } ,
      function() { Site.Workspace.tableToolbars('Invoice Code').clickButton("More Toolbars"); } ,
      function() {
        var controlGroup = Site.Workspace.tableToolbar("Filter") ;
        controlGroup.inputVal('type', "O*") ;
      },
      function() { Site.Workspace.tableToolbars('Invoice Code').clickButton("Filter"); } ,
      function() {
        var controlGroup = Site.Workspace.tableToolbar("Invoice Code") ;
        controlGroup.inputVal('type', "") ;
      },
      function() { Site.Workspace.tableToolbars('Invoice Code').clickButton("More Filter Options"); } ,
      function() {
        var controlGroup = Site.Workspace.tableToolbar("Invoice Code") ;
        controlGroup.inputVal('status', "Paid") ;
      },
      function() { Site.Workspace.tableToolbars('Type').clickButton("Filter"); } ,
      function() {
        var controlGroup = Site.Workspace.tableToolbar("Status") ;
        controlGroup.inputVal('status', "") ;
      },
      function() { Site.Workspace.tableToolbars('Type').clickButton("More Toolbars") ; } ,
      function() { Site.Workspace.tableToolbar('Invoice Code').clickButton("Refresh") ; } ,
    ]
  });
  
  var EditDeleteInvoiceItemTask = new test.UnitTask({
    name: "EditDeleteInvoiceItemTask",
    description: "test edit, delete invoiceItem",
    units: [
      //EDIT
      
      function() {
        var table = Site.Workspace.tableWithHeader("Invoice Code") ;
        var row   = table.tableRowWithText("SAL1") ;
        row.clickLink("SAL1") ;
      },
      function() { Site.Workspace.tableToolbar('New Item') ; },
      function() {
        var table = Site.Workspace.tableWithHeader("Item Code") ;
        var row   = table.tableRowWithText("SAL1-item 0") ;
        row.clickButton("Edit Invoice Item") ;
      },
      function() {
        var form = Site.PopupPanel.formWithText("Item Code:") ;
        form.inputVal("itemName", "Name-test");
        Site.PopupPanel.formWithText("Item Code:").clickButton("Save");
      },
      function() {
        Site.Workspace.toolbarWith('Invoice').clickButton("Save") ;
      },
      
      function() { Site.Workspace.tableToolbars('Invoice Code').clickButton("More Toolbars"); } ,
      function() { Site.Workspace.tableToolbars('Invoice Code').clickButton("More Toolbars"); } ,
      function() {
        var controlGroup = Site.Workspace.tableToolbar("Filter") ;
        controlGroup.inputVal('type', "S*") ;
      },
      function() { Site.Workspace.tableToolbars('Invoice Code').clickButton("Filter"); } ,
      //EDIT
      function() {
        var table = Site.Workspace.tableWithHeader("Invoice Code") ;
        var row   = table.tableRowWithText("SAL1") ;
        row.clickLink("SAL1") ;
      },
      
      function() { Site.Workspace.tableToolbar('New Item') ; },
      function() {
        var table = Site.Workspace.tableWithHeader("Item Code") ;
        var row   = table.tableRowWithText("SAL1-item 0") ;
        row.clickButton("Delete") ;
      },
      function() {
        Site.Workspace.toolbarWith('Invoice').clickButton("Save") ;
      },
      
      function() { Site.Workspace.tableToolbars('Invoice Code').clickButton("More Toolbars"); } ,
      function() { Site.Workspace.tableToolbars('Invoice Code').clickButton("More Toolbars"); } ,
      function() {
        var controlGroup = Site.Workspace.tableToolbar("Filter") ;
        controlGroup.inputVal('type', "S*") ;
      },
      function() { Site.Workspace.tableToolbars('Invoice Code').clickButton("Filter"); } ,
      
      function() {
        var table = Site.Workspace.tableWithHeader("Invoice Code") ;
        var row   = table.tableRowWithText("SAL2") ;
        row.clickLink("SAL2") ;
      },
    ]
  });
  
  var EditDeleteInvoiceTransactionTask = new test.UnitTask({
    name: "EditDeleteInvoiceTransactionTask",
    description: "test edit, delete invoiceTransaction",
    units: [
      function() { Site.Workspace.tableToolbar('New Transaction') ; },
      function() {
        var table = Site.Workspace.tableWithHeader("Bank Account Id") ;
        var row   = table.tableRowWithText("vcb-ngoquyen") ;
        row.clickButton("Edit Transaction") ;
      },
      function() {
        var form = Site.PopupPanel.formWithText("Bank Account Id:") ;
        form.inputVal("description", "EDIT TRANSACTION");
        Site.PopupPanel.formWithText("Bank Account Id:").clickButton("Save");
      },
      function() {
        Site.Workspace.toolbarWith('Invoice').clickButton("Save") ;
      },
      
      function() { Site.Workspace.tableToolbars('Invoice Code').clickButton("More Toolbars"); } ,
      function() { Site.Workspace.tableToolbars('Invoice Code').clickButton("More Toolbars"); } ,
      function() {
        var controlGroup = Site.Workspace.tableToolbar("Filter") ;
        controlGroup.inputVal('type', "S*") ;
      },
      function() { Site.Workspace.tableToolbars('Invoice Code').clickButton("Filter"); } ,
      
      function() {
        var table = Site.Workspace.tableWithHeader("Invoice Code") ;
        var row   = table.tableRowWithText("SAL2") ;
        row.clickLink("SAL2") ;
      },
      function() { Site.Workspace.tableToolbar('New Transaction') ; },
      function() {
        var table = Site.Workspace.tableWithHeader("Bank Account Id") ;
        var row   = table.tableRowWithText("vcb-ngoquyen") ;
        row.clickButton("Delete") ;
      },
      function() {
        Site.Workspace.toolbarWith('Invoice').clickButton("Save") ;
      },
      
      function() { Site.Workspace.tableToolbars('Invoice Code').clickButton("More Toolbars"); } ,
      function() { Site.Workspace.tableToolbars('Invoice Code').clickButton("More Toolbars"); } ,
      function() {
        var controlGroup = Site.Workspace.tableToolbar("Filter") ;
        controlGroup.inputVal('type', "S*") ;
      },
      function() { Site.Workspace.tableToolbars('Invoice Code').clickButton("Filter"); } ,
      
      function() {
        var table = Site.Workspace.tableWithHeader("Invoice Code") ;
        var row   = table.tableRowWithText("SAL2") ;
        row.clickLink("SAL2") ;
      },
      function() { Site.Workspace.toolbarWith("Invoices").clickButton("Invoices") ; },
      function() {
        var table = Site.Workspace.tableWithHeader("Invoice Code") ;
        var row   = table.tableRowWithText("SAL2") ;
        row.clickButton("Delete") ;
      },
    ]
  });
  
  var GoToAccountingBankAccountsScreen = new test.UnitTask({
    name: "GoToAccountingBankAccountsScreen",
    description: "Click on BankAccounts",
    units: [
      function() { Site.Navigation.clickMenuItem("Accounting ", "Bank accounts") ; },
    ]
  });
  
  var CRUDTestBankAccountTask = new test.UnitTask({
    name: "CRUDTestBankAccountTask",
    description: "Create test BankAccount, edit test BankAccount, delete test BankAccount",
    units: [
      function() {
        Site.Workspace.tableToolbar('Refresh').clickButton("New");
        var form = Site.PopupPanel.formWithText("Bank Account Id:") ;
        form.inputVal("bankAccountId", "techcombank-caugiay");
        form.inputVal("type", "CreditCard");
        form.inputVal("currency", "VND");
        form.inputVal("address", "335 - Cau Giay - Ha Noi");
      },
      function() {
        Site.PopupPanel.formWithText("Bank Account Id:").clickButton("Save");
      },
      //EDIT
      function() {
        var table = Site.Workspace.tableWithHeader("Bank Account Id") ;
        var row   = table.tableRowWithText("techcombank-caugiay") ;
        row.clickLink("techcombank-caugiay") ;
      },
      function() {
        var form = Site.PopupPanel.formWithText("Bank Account Id:") ;
        form.inputVal("type", "CreditCard");
        form.inputVal("currency", "USD");
        form.inputVal("address", "D1 - T9- TopCareTower - 335 - Cau Giay - Ha Noi");
        
      },
      function() {
        Site.PopupPanel.formWithText("Bank Account Id:").clickButton("Save");
      },
      function() {
        var table = Site.Workspace.tableWithHeader("Bank Account Id") ;
        var row   = table.tableRowWithText("techcombank-caugiay") ;
        row.clickButton("Delete") ;
      },
    ]
  });
  
  var GoToReportScreen = new test.UnitTask({
    name: "GoToReportScreen",
    description: "Click on Report",
    units: [
      function() { Site.Navigation.clickMenuItem("Accounting ", "Report") ; },
    ]
  });
  
  var accounting = {
      module: 'accounting',
      CleanDB: CleanDB,
      
      createScenario: function(name) {
        var Scenario = new test.UnitTask({
          name: "AccountingScenario " + name,
          description: "Create a minimum set of data for the module",
          units: [
            function() { 
              var jsonRes = 'scenario/' + name + '/accounting.json' ;
              var data = service.Server.syncGETResource(jsonRes, 'json') ;
              if(data != null) {
                service.AccountingService.createScenario(data) ;
              }
            }
          ]
        }); 
        return Scenario ;
      },
      
      Service: {
        api: [ BankAccountApicCRUD, InvoiceApiCRUD ]
      },
      
      UI: {
        Invoice: test.Suite.extend({
          name: 'Invoice',
          description: "go to screen" ,
          unitTasks: [ 
            GoToAccountingInvoicesScreen, CRUDTestInvoiceTask, SearchInvoiceTask, EditDeleteInvoiceItemTask, EditDeleteInvoiceTransactionTask
          ],
        }),
        
        Bank: test.Suite.extend({
          name: 'BankAccount',
          description: "go to screen" ,
          unitTasks: [ 
            GoToAccountingBankAccountsScreen, CRUDTestBankAccountTask
          ],
        }),
        
        Report: test.Suite.extend({
          name: 'Report',
          description: "go to screen Report" ,
          unitTasks: [ 
            GoToReportScreen
          ],
        })
      }
    };
  return accounting;
});
