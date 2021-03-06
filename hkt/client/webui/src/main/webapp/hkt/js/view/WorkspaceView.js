define([
  'jquery', 
  'underscore', 
  'backbone',
], function($, _, Backbone) {

  /**@type view.WorkspaceView */
  WorkspaceView = Backbone.View.extend({
    el: $("#workspace"),
    /**@type module.ModuleManager */
    moduleManager: null ,
    /**@type module.Module */
    activeModule: null, 
  
    initialize: function (options) {
      this.moduleManager = options.moduleManager;
      _.bindAll(this, 'render') ;
      this.render() ;
    },

    render: function() {
      if(this.activeModule != null) {
        this.activeModule.render() ;
      }
    },
    
    /**@memberOf view.WorkspaceView */
    activateModule: function(moduleId) {
      var array = moduleId.split('/') ;
      var moduleName = array[0] ;
      var submoduleName = array[1] ;
      this.moduleManager.activate(moduleName, submoduleName) ;
    },
    
    /**@memberOf view.WorkspaceView */
    activateMy: function() {
      this.moduleManager.activateMy() ;
    }
  });
  
  return WorkspaceView ;
});
