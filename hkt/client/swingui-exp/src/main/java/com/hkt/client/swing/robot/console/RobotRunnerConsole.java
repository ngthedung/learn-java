package com.hkt.client.swing.robot.console;

import java.awt.Frame;

import javax.swing.JDialog;
import javax.swing.JFrame;

import com.hkt.module.core.script.ScriptRunner;

public class RobotRunnerConsole extends JDialog {  
  private ScriptWorkSpace   workspace ;
  
  public RobotRunnerConsole(Frame uiroot, boolean modal) {
    super((JFrame) null, modal);
    workspace = new ScriptWorkSpace(uiroot) ;
   
    pack();
    setLocationRelativeTo(null);
    setVisible(true);
  }

  public ScriptRunner getScriptRunner() {
    return workspace.getScriptRunner() ;
  }
}