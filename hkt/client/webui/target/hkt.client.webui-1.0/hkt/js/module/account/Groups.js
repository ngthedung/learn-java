define([
  'jquery', 
  'underscore', 
  'backbone',
  'service/service',
  'module/Module',
  'ui/UIBean',
  'ui/UITable',
  'ui/UIPopup',
  'ui/UIBreadcumbs',
  'module/account/Group',
  'module/account/Account',
  'module/account/Membership',
  'text!module/account/Groups.jtpl'
], function($, _, Backbone, service, module, UIBean, UITable, UIPopup, UIBreadcumbs, Group, 
    Account, Membership, GroupsTemplate) {
  
  var UIMembershipList = UITable.extend({
    label : "Membership", 
    config: {
      toolbar: {
        dflt: {
          actions: []
        }
      },
      bean: {
        fields: [
          { 
            label: 'Id', field: 'id', toggled: true,
            onClick: function(thisUI, row) { 
              console.log("edit row " + row) ;
            } 
          },
          { 
            label: 'Account', field: 'loginId', toggled: true, filterable: true,
            onClick: function(thisUI, row) { 
              var membership = thisUI.getItemOnCurrentPage(row) ;
              var account = service.AccountService.getAccountByLoginId(membership.loginId).data ;
              var view = new Account.UIAccountDetail().init(thisUI, account, false) ;
              thisUI.viewStack.push(view) ;
            }
          },
          { label: 'Capability', field: 'capability', toggled: true, filterable: true },
          { label: 'Role', field: 'role', toggled: true, filterable: true },
          { label: 'Status', field: 'status', toggled: true, filterable: true },
          { label: 'Group Path', field: 'groupPath', toggled: true, filterable: true },
        ],
        actions: [
          {
            icon: "edit", label: "Edit",
            onClick: function(thisUI, row) {
              var membership = thisUI.getItemOnCurrentPage(row) ;
              UIPopup.activate( new Membership.UIMembership().init(thisUI, membership, false)) ;
            } 
          },
          {
            icon: "bars", label: "Account Information",
            onClick: function(thisUI, row) { 
              var membership = thisUI.getItemOnCurrentPage(row) ;
              var account = service.AccountService.getAccountByLoginId(membership.loginId).data ;
              var view = new Account.UIAccountDetail().init(thisUI, account, false) ;
              thisUI.viewStack.push(view) ;
            } 
          },
          {
            icon: "delete", label: "Delete",
            onClick: function(thisUI, row) { 
              var membership = thisUI.getItemOnCurrentPage(row) ;
              var deleted = service.AccountService.deleteMembership(membership.loginId, membership.groupPath).data ;
              if(deleted) {
                thisUI.removeItemOnCurrentPage(row) ;
              }
            }
          }
        ]
      }
    },
    
    back: function(){
      this.viewStack.back() ;
    },
    
    init: function(viewStack) {
      this.viewStack = viewStack ;
    },
    
    onRefresh: function(groupPath){
      var meberships = service.AccountService.findMembershipByGroupPath(groupPath).data ;
      this.setBeans(meberships) ;
      this.renderRows() ;
    }
  });
  
  /**@type module.account.GroupsView */
  var GroupsView = Backbone.View.extend({
    label: "Groups",
    
    /**@type service.AccountService */
    AccountService: service.AccountService,
    
    initialize: function (config) {
      _.bindAll(this, 'render', 'onNewGroup', "onCloseGroupForm") ;
      this.membershipList = new UIMembershipList() ;
    },

    _template: _.template(GroupsTemplate),
    
    render: function() {
      var params = {
        group : this.currentGroupDetail.group,
        memberships: this.currentGroupDetail.memberships,
        children: this.currentGroupDetail.children
      } ;
      $(this.el).html(this._template(params));
      $(this.el).trigger("create") ;
      this.membershipList.setBeans(params.memberships) ;
      this.membershipList.setElement(this.$('.MembershipsList')).render();
    },
    
    events: {
      'click a.onNewGroup': 'onNewGroup',
      'click a.onEditGroup': 'onEditGroup',
      'click a.onDeleteGroup': 'onDeleteGroup',
      'click a.onSelectGroup': 'onSelectGroup',
      'click a.onCloseGroupForm': 'onCloseGroupForm',
      'click a.onAddMembership' : 'onAddMembership'
    },
      
    /**@memberOf module.account.GroupsView */
    onAddMembership: function(evt) {
      var parentPath = null ;
      if(this.currentGroupDetail != null && this.currentGroupDetail.group != null) {
        parentPath = this.currentGroupDetail.group.path ;
      }
      var membership = { loginId: "", groupPath: parentPath} ;
      console.log(membership) ;
      UIPopup.activate( new Membership.UIMembership().init(this, membership, true)) ;
    },
    onNewGroup: function(evt) {
      var parentPath = null ;
      if(this.currentGroupDetail != null && this.currentGroupDetail.group != null) {
        parentPath = this.currentGroupDetail.group.path ;
      }
      var group = {
        path: null, name: "", description: "a new group"
      } ;
      
      UIPopup.activate( new Group.UIGroup().init(this, parentPath, group, true));
    },
    
    onEditGroup: function(evt) {
      var group = this.currentGroupDetail.group ;
      UIPopup.activate( new Group.UIGroup().init(this, null, group, false));
    },
    
    onDeleteGroup: function(evt) {
      var group = this.currentGroupDetail.group ;
      service.AccountService.deleteGroup(group).data ;
      var idx = group.path.lastIndexOf('/') ;
      if(idx > 0)
      var parent = group.path.substring(0, idx) ;
      this.updateSelectGroup(this.AccountService.getGroupDetailByPath(parent).data) ;
      this.render() ;
    },
    
    onRefresh: function() {
      var path = null ;
      if(this.currentGroupDetail != null && this.currentGroupDetail.group != null) {
        path = this.currentGroupDetail.group.path ;
      }
      this.currentGroupDetail = this.AccountService.getGroupDetailByPath(path).data ;
      this.render() ;
    },
    
    onSelectGroup: function(evt) {
      var gpath = $(evt.target).attr('path') ;
      if(gpath == null || gpath == undefined) {
        gpath = $(evt.target).closest("a").attr('path') ;
      }
      if(gpath == '') gpath = null ;
      this.updateSelectGroup(this.AccountService.getGroupDetailByPath(gpath).data) ;
    },
    
    onCloseGroupForm: function(evt) {
      console.log("close group form") ;
    },
    
    updateSelectGroup: function(groupDetail) {
      this.currentGroupDetail = groupDetail ;
      this.membershipList.setBeans(groupDetail.memberships) ;
      this.render() ;
    },
    
    init: function(viewStack) {
      this.viewStack = viewStack ;
      this.currentGroupDetail = this.AccountService.getGroupDetailByPath(null).data ;
      this.membershipList.init(this.viewStack) ;
      this.membershipList.setBeans(this.currentGroupDetail.memberships) ;
      return this ;
    },
  });

  
  var UIGroupsScreen = module.UIScreen.extend({
    
    initialize: function (options) {
    },

    activate: function() { 
      this.viewStack = new UIBreadcumbs({el: "#workspace"}) ;
      var aview = new GroupsView().init(this.viewStack) ;
      this.viewStack.push(aview) ;
    },
    
    deactivate: function() { }
    
  });
  
  return UIGroupsScreen ;
});
