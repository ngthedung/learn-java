define([
  'jquery', 
  'underscore', 
  'backbone'
], function($, _, Backbone) {
  var UIPopup = Backbone.View.extend({
    el: "#PopupPanel",
    
    initialize: function (options) {
    },
    
    activate: function(view) {
      $(this.el).off();
      var instance = this ;
      view.clopsePopup = function() {
        instance.clopsePopup() ;
      };
      this.view = view ;
      this.render() ;
    },
    
    _template: _.template(
      "<div>" +
      "  <h3 style='border-bottom: 1px solid; margin: 0px'><%=title%></h3>" +
      "  <div class='UIPopupContent'></div>" +
      "</div>"
    ),
    
    render: function() {
      var params = {
        title: this.view.label  
      } ;
      $(this.el).html(this._template(params));
      $(this.el).trigger("create") ;
      
      this.view.setElement(this.$('.UIPopupContent')).render();
      $(this.el).popup( "open", {});
    },
    
    
    closePopup: function() {
      $(this.el).popup("close") ;
    }
  });
  
  return new UIPopup() ;
});
