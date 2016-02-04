define([
  'jquery'
], function($) {
  /**@type service.Server */
  Server = {
    serverUrl: ROOT_CONTEXT + '/js/',

    /**@memberOf service.Server */
    syncGETResource : function(path, dataType) {
      var returnData = null ;
      $.ajax({ 
        type: "GET",
        dataType: dataType,
        url:  path,
        async: false ,
        error: function(data) {
          console.trace() ;
        },
        success: function(data) {  returnData = data ; }
      });
      return returnData ;
    },
    
    /**@memberOf service.Server */
    syncGET : function(path, params) {
      var returnData = null ;
      $.ajax({ 
        type: "GET",
        dataType: "json",
        url: this.serverUrl + path,
        data: params ,
        async: false ,
        error: function(data) {  
          console.debug("Error: \n" + JSON.stringify(data)) ; 
        },
        success: function(data) {  returnData = data ; }
      });
      return returnData ;
    },
    
    /**@memberOf service.Server */
    syncPOST : function(path, postData) {
      var returnData = null ;
      $.ajax({ 
        async: false ,
        type: "POST",
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        url: this.serverUrl + path,
        data:  JSON.stringify(postData) ,
        error: function(data) {  
          console.debug("Error: \n" + JSON.stringify(data)) ; 
        },
        success: function(data) {  
          returnData = data ; 
        }
      });
      return returnData ;
    },
    
    /**@memberOf service.Server */
    GET : function(request) {
      var returnData = null ;
      $.ajax({ 
        type: "GET",
        dataType: "json",
        url: '../rest/get?req=' + JSON.stringify(request),
        data: params ,
        async: false ,
        error: function(data) {  
          console.debug("Error: \n" + JSON.stringify(data)) ; 
        },
        success: function(data) {  returnData = data ; }
      });
      return returnData ;
    },
    
    /**@memberOf service.Server */
    POST : function(request) {
      var returnData = null ;
      $.ajax({ 
        async: false ,
        type: "POST",
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        url: "../rest/post",
        data:  JSON.stringify(request) ,
        error: function(data) {  
          console.debug("Error: \n" + JSON.stringify(data)) ; 
        },
        success: function(data) {  
          returnData = data ; 
        }
      });
      return returnData ;
    },
  };
  return Server ;
});
