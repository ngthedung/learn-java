package com.hkt.module.print.rest;

import java.util.List;

import org.codehaus.jackson.type.TypeReference;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hkt.module.account.AccountService;
import com.hkt.module.account.util.AccountScenario;
import com.hkt.module.cms.CMSService;
import com.hkt.module.core.entity.FilterQuery;
import com.hkt.module.core.entity.FilterResult;
import com.hkt.module.core.rest.Request;
import com.hkt.module.core.rest.Response;
import com.hkt.module.core.rest.RestService;
import com.hkt.module.print.AbstractUnitTest;
import com.hkt.module.print.PrintMachineService;
import com.hkt.module.print.entity.PrintMachine;
import com.hkt.module.product.ProductService;
import com.hkt.module.product.entity.ProductTag;
import com.hkt.module.product.util.ProductScenario;

public class PrintServiceDispatcherUnitTest extends AbstractUnitTest {

  @Autowired
  RestService restService;

  @Autowired
  PrintMachineService service;

  @Autowired
  private AccountService accountService;

  @Autowired
  private ProductService productService;

  
  @Autowired
  private CMSService cmsService;

  @Before
  public void setup() throws Exception {
    accountService.createScenario(AccountScenario.loadTest());
    productService.createScenario(ProductScenario.loadTest()) ;
    List<ProductTag> productTags = productService.getProductTags();
    service.scandPrintMachine();
    List<PrintMachine> printMachines = service.getPrintMachines();
    for(PrintMachine printMachine: printMachines){
      printMachine.setProductTags(productTags);
      service.savePrintMachine(printMachine);
    }
  }

  @After
  public void tearDown() throws Exception {
    service.deleteAll();
    accountService.deleteAll();
    productService.deleteAll();
    cmsService.deleteAll();
  }

  @Test
  public void testRestService() throws Exception {
    Request req = new Request("print", "PrintMachineService", "getPrintMachines");
    Response res = restService.dispatch(req);

    System.out.println(res);
  }
}