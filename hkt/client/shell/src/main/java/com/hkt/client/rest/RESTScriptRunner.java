package com.hkt.client.rest;

import java.util.HashMap;

import com.hkt.module.core.script.ScriptRunner;
import com.hkt.util.CommandParser;
import com.hkt.util.IOUtil;

public class RESTScriptRunner {
  private RESTClient client ;
  private ScriptRunner runner ;
  
  public RESTScriptRunner(String baseDir) {
    ClientContext clientContext = new ClientContext() ;
    this.client = clientContext.getBean(RESTClient.class) ;
    HashMap<String, Object> ctx = new HashMap<String, Object>() ;
    ctx.put("client", client) ;
    this.runner = new ScriptRunner(baseDir, ctx) ;
  }
  
  public void run(String scriptFile) throws Exception {
    StringBuilder b = new StringBuilder() ;
    //b.append(IOUtil.getFileContentAsString("src/app/script/lib/lib.js", "UTF-8")) ;
    b.append("\n") ;
    b.append(IOUtil.getFileContentAsString(scriptFile, "UTF-8")) ;
    runner.eval(b.toString()) ;
  }
  
  static public void main(String[] args) throws Exception {
    CommandParser command = new com.hkt.util.CommandParser("Run:") ;
    command.addMandatoryOption("file",    true, "The js script file") ;
    command.addMandatoryOption("baseDir", true, "The root directory that contains all the necessary script") ;
    if(!command.parse(args)) return ;
    command.printHelp() ;
    String file = command.getOption("file", null) ;
    String baseDir = command.getOption("baseDir", ".") ;
    RESTScriptRunner runner = new RESTScriptRunner(baseDir) ;
    runner.run(file) ;
  }
}