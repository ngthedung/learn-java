package com.hkt.client.rest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.hkt.module.core.rest.Request;
import com.hkt.module.core.rest.Response;
import com.hkt.util.json.JSONSerializer;

public class RESTClientIntegrationTest {
  ClientContext clientContext = ClientContext.getInstance() ;
  @Test
  public void testGET() throws Exception {
    RESTClient restClient = clientContext.getBean(RESTClient.class);

    Request request = new Request("core", "PingService", "ping");
    request.setRequestAtTime(System.currentTimeMillis());
    request.setLogEnable(true);

    Response response = restClient.GET(request);
    System.out.println(JSONSerializer.INSTANCE.toString(response));
    assertEquals("Hello", response.getDataAs(String.class));
  }

  @Test
  public void testPOST() throws Exception {
    RESTClient restClient = clientContext.getBean(RESTClient.class);

    Request request = new Request("core", "PingService", "ping");
    request.setRequestAtTime(System.currentTimeMillis());
    request.setLogEnable(true);

    Response response = restClient.POST(request);
    System.out.println(JSONSerializer.INSTANCE.toString(response));
    assertEquals("Hello", response.getDataAs(String.class));
  }
}
