ScriptRunner.require("src/main/resources/script/robot.js");

function loginAsHKT(frameui) {
  var panel = frameui.panel("LoginPOSScreen") ;
  panel.fieldSet("loginId", "hkt") ;
  panel.fieldSet("password", "hkt") ;
  panel.buttonClick("Login");
}

function openInvoice(frameui) {
  var tableStatus1 = frameui.table("TableStatus1") ;
  tableStatus1.doubleClickRow("A02") ;
}

function clickPOSTab(frameui) {
  var tabbedPane = frameui.tabbedPane("MainTabs") ;
  tabbedPane.clickTab("POS") ;
}

var robot = new Robot() ;
robot.add("Login", "", loginAsHKT);
robot.add("Open Invoice", "Open the invoice for table A01", openInvoice);
robot.add("Click POS Tab", "Click POS Tab", clickPOSTab);
console.clear() ;
robot.run() ;
robot.report() ;
