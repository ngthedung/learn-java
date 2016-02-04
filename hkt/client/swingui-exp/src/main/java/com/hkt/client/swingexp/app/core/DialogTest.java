package com.hkt.client.swingexp.app.core;

public class DialogTest {
  private boolean test;
  private static DialogTest dialogTest;

  public static DialogTest getInstance() {
    if (dialogTest == null) {
      dialogTest = new DialogTest();
    }
    return dialogTest;
  }

  public DialogTest() {

  }

  public void show(String txt) {
    PanelTest panel = PanelTest.getInstance();
    panel.setString(txt);
    DialogResto dialog = new DialogResto(panel, false, 0, 100);
    dialog.setTitle("Chương trình chạy test");
    dialog.setLocationRelativeTo(null);
    dialog.setModal(true);
    dialog.setVisible(true);
    test = panel.isTest();
    panel = null;
    System.gc();
  }

  public boolean isTest() {
    return test;
  }
  
  
}
