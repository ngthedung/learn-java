package com.hkt.client.swingexp.app.test;

import java.util.HashSet;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import com.hkt.client.swing.robot.console.JVMEnv;
import com.hkt.client.swing.robot.console.ShellConsole;
import com.hkt.client.swingexp.app.core.FrameUI;
import com.hkt.client.swingexp.app.core.MyFrame;
import com.hkt.util.IOUtil;

public class ScriptRunnerExp {
  private HashSet<String> loadedFiles = new HashSet<String> () ;
  private ScriptEngine engine ;

  public ScriptRunnerExp( Map<String, Object> ctx) {
    ScriptEngineManager factory = new ScriptEngineManager();
    engine = factory.getEngineByName("JavaScript");
    engine.put("ScriptRunner", this) ;
    if(ctx != null) {
      for(Map.Entry<String, Object> entry : ctx.entrySet()) {
        engine.put(entry.getKey(), entry.getValue());
      }
    }
  }
  
  public ScriptRunnerExp(FrameUI frameUI) {
    ScriptEngineManager factory = new ScriptEngineManager();
    engine = factory.getEngineByName("JavaScript");
    engine.put("ScriptRunner", this) ;
    engine.put("console", new ShellConsole());
    engine.put("jvm", new JVMEnv());
    engine.put("frame", frameUI);
  }

  public void eval(String script) throws Exception {
    engine.eval(script);
  }
  
  public void require(String scriptFile) {
    if(loadedFiles.contains(scriptFile)) return ;
    try {
      String script = null ;
      if(scriptFile.startsWith("classpath:") || scriptFile.startsWith("file:")) {
        script = IOUtil.getStreamContentAsString(IOUtil.loadRes(scriptFile), "UTF-8") ;
      } else {
        script = IOUtil.getStreamContentAsString(getClass().getResourceAsStream(scriptFile), "UTF-8") ;
      }
      loadedFiles.add(scriptFile) ;
      engine.eval(script);
    } catch(Exception ex) {
      ex.printStackTrace() ;
      throw new RuntimeException(ex) ;
    }
  }
}