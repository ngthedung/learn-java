package com.hkt.client.rest;

import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;

public class ClientContext {
  static String[] res = { "classpath:META-INF/client-context.xml", }; 
  static ClientContext instance ;
  
  private GenericApplicationContext ctx ;
  
  public ClientContext() { this(res) ; }
  
  public ClientContext(String[] res) {
    this.ctx = new GenericApplicationContext();
    XmlBeanDefinitionReader xmlReader = new XmlBeanDefinitionReader(ctx);
    xmlReader.loadBeanDefinitions(res);
    ctx.refresh();
    ctx.registerShutdownHook();
  }
  
  public <T> T getBean(Class<T> type) { return ctx.getBean(type) ; }
  
  public GenericApplicationContext getApplicationContext() { return this.ctx ; }
  
  static public ClientContext getInstance() {
    if(instance == null) instance = new ClientContext() ;
    return instance ;
  }
}