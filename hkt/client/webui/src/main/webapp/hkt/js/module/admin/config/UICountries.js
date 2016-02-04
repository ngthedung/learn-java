define([
  'jquery',
  'underscore',
  'backbone',
  'service/service',
  'ui/UITable',
  'ui/UIBean',
  'ui/UIPopup',
  'module/admin/config/Country'
  ], function($, _, Backbone, service, UITable, UIBean, UIPopup, Country) {
  
  var UICountries = UITable.extend({
    label: "Countries",
    config: {
      toolbar: {
        dflt: {
          actions: [
            {
              action: "onNew", icon: "add", label: "New", 
              onClick: function(thisTable) {
                var country = { name : "", provinces: [], cities: [] } ;
                var countryDetail = thisTable.UICountryDetail.init(thisTable, country, true) ;
                thisTable.viewStack.push(countryDetail) ;
              } 
            },
            {
              action: "onRefresh", icon: "refresh", label: "Refresh",
              onClick: function(thisTable) { 
                thisTable.onRefresh() ;
              }
            }
          ]
        }
      },
      
      bean: {
        label: 'Country',
        fields: [
          { label : 'Index', field : 'index', toggled : true, filterable : true, },
          { 
            label : 'Code', field : 'code', toggled : true, filterable : true,
            onClick: function(thisTable, row) {
              var country = thisTable.getItemOnCurrentPage(row) ;
              var countryDetail = thisTable.UICountryDetail.init(thisTable, country, false) ;
              thisTable.viewStack.push(countryDetail) ;
            }
          },
          { field: "name", label: "Name", defaultValue: 'Country Name', toggled: true, filterable: true, },
          { label : 'Flag', field : 'flag', toggled : true, filterable : true, },
          { label : 'Description', field : 'description', toggled : true, filterable : true, }
        ],
        actions: [
          {
            icon: "delete", label: "Delete",
            onClick: function(thisTable, row) {
              var country = thisTable.getItemOnCurrentPage(row) ;
              var deleted = service.LocaleService.deleteCountry(country).data ;
              if(deleted) {
                thisTable.removeItemOnCurrentPage(row) ;
              }
            }
          }
        ]
      }
    },
    
    onRefresh: function() {
      var countries = service.LocaleService.getCountries().data ;
      this.setBeans(countries) ;
      this.renderRows() ;
    },
    
    back: function(refresh) {
     if(refresh) this.onRefresh() ;
     this.viewStack.back() ;
    },
    
    init: function (viewStack) {
      this.viewStack = viewStack ;
      this.UICountryDetail = new Country.UICountryDetail();
      var countries = service.LocaleService.getCountries().data ;
      this.setBeans(countries) ;
      return this ;
    }
  });
  
  return UICountries ;
});
