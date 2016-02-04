package com.hkt.client.swing.robot.console;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import com.hkt.client.swingexp.app.core.FrameUI;
import com.hkt.module.core.script.ScriptRunner;

public class ScriptWorkSpace extends JPanel {
  private ScriptRunner      scriptRunner;
  private ShellConsole       scriptShell;
  
  public ScriptWorkSpace(Frame uiRoot) {
    setLayout(new BorderLayout());
    setPreferredSize(new Dimension(600, 500));
    scriptShell = new ShellConsole();

    JSplitPane splitPane =
      new JSplitPane(JSplitPane.VERTICAL_SPLIT, null, scriptShell);
    splitPane.setDividerLocation(350);
    splitPane.setOneTouchExpandable(true);

    add(splitPane, BorderLayout.CENTER);

    Map<String, Object> ctx = new HashMap<String, Object>() ;
    ctx.put("console", scriptShell) ;
    ctx.put("jvm", new JVMEnv()) ;
    ctx.put("frameui", new FrameUI(uiRoot)) ;
    scriptRunner = new ScriptRunner(".", ctx);
  }

  public ScriptRunner getScriptRunner() { return this.scriptRunner ; }

  public ShellConsole getScriptShell() { return this.scriptShell ; }
  
}