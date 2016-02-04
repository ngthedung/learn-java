define([
  'jquery',
  'service/Server'
], function ($, Server) {
  /** @type service.AccountingService */
  var AccountingService = {
    module : 'accounting',
    
    service : 'AccountingService',
    
    ping : function() {
      var request = {
        module : this.module, service : this.service, method : 'ping',
        params : {}
      };
      return Server.POST(request);
    },
    
    createScenario : function(scenario) {
      var request = {
        module : 'accounting', service : 'AccountingService', method : 'createScenario',
        params : { scenario : scenario }
      };
      return Server.POST(request);
    },
    
    cleanData : function(scenario) {
      var request = {
        module : 'accounting', service : 'AccountingService', method : 'deleteAll',
      };
      return Server.POST(request);
    },
    
    getBankAccountById: function(bankAccountId) {
      var request = {
        module : 'accounting', service : 'AccountingService', method : 'getBankAccountById',
        params : { bankAccountId : bankAccountId }
      };
      return Server.POST(request);
    },
    
    saveBankAccount: function(bankAccount) {
      var request = {
        module : 'accounting', service : 'AccountingService', method : 'saveBankAccount',
        params : { bankAccount : bankAccount }
      };
      return Server.POST(request);
    },
    
    deleteBankAccountByAccountId: function(bankAccountId) {
      var request = {
        module : 'accounting', service : 'AccountingService', method : 'deleteBankAccountByAccountId',
        params : { bankAccountId : bankAccountId }
      };
      return Server.POST(request);
    },
    
    findBankAccounts: function() {
      var request = {
        module : 'accounting', service : 'AccountingService', method : 'findBankAccounts',
        params : {  }
      };
      return Server.POST(request);
    },
    
    createInvoiceDetail: function(invoiceDetail) {
      var request = {
        module : 'accounting', service : 'AccountingService', method : 'createInvoiceDetail',
        params : { invoiceDetail : invoiceDetail }
      };
      return Server.POST(request);
    },
    
    calculateInvoiceDetail: function(invoiceDetail) {
      var request = {
        module : 'accounting', service : 'AccountingService', method : 'calculateInvoiceDetail',
        params : { invoiceDetail : invoiceDetail }
      };
      return Server.POST(request);
    },
    
    updateInvoiceDetail: function(invoiceDetail) {
      var request = {
        module : 'accounting', service : 'AccountingService', method : 'updateInvoiceDetail',
        params : { invoiceDetail : invoiceDetail }
      };
      return Server.POST(request);
    },
    
    findBankAccountByBankLoginId: function(bankLoginId) {
      var request = {
        module : 'accounting', service : 'AccountingService', method : 'findBankAccountByBankLoginId',
        params : { bankLoginId : bankLoginId }
      };
      return Server.POST(request);
    },
    
    getInvoiceDetail: function(id) {
      var request = {
        module : 'accounting', service : 'AccountingService', method : 'getInvoiceDetail',
        params : { id : id }
      };
      return Server.POST(request);
    },
    
    searchInvoices: function(query) {
      var request = {
        module : 'accounting', service : 'AccountingService', method : 'searchInvoices',
        params : { query : query }
      };
      return Server.POST(request);
    },
    
    searchInvoice: function(query) {
      var request = {
        module : 'accounting', service : 'AccountingService', method : 'searchInvoice',
        params : { query : query }
      };
      return Server.POST(request);
    },
    
    deleteInvoiceById: function(id) {
      var request = {
        module : 'accounting', service : 'AccountingService', method : 'deleteInvoiceById',
        params : { id : id }
      };
      return Server.POST(request);
    },
    
    getInvoiceReportTable: function(query) {
      var request = {
        module : 'accounting', service : 'AccountingService', method : 'getInvoiceReportTable',
        params : { query : query }
      };
      return Server.POST(request);
    },
    
    reportQuery: function(queries) {
      var request = {
        module : 'accounting', service : 'AccountingService', method : 'reportQuery',
        params : { query : queries }
      };
      return Server.POST(request);
    },
  };
  
  return AccountingService;
});
