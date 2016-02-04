package com.hkt.client.rest;

import org.junit.Test;

public class RESTScriptRunnerIntegrationTest {
  @Test
  public void test() throws Exception {
    RESTScriptRunner runner = new RESTScriptRunner("src/app/script/js") ;
    //runner.run("src/app/script/js/hello.js") ;
    runner.run("src/app/script/js/account/AccountScenario.js") ;
  }
}