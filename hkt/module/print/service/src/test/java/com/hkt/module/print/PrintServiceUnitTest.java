package com.hkt.module.print;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hkt.module.account.AccountService;
import com.hkt.module.account.util.AccountScenario;
import com.hkt.module.cms.CMSService;
import com.hkt.module.core.ServiceCallback;
import com.hkt.module.print.entity.PrintMachine;
import com.hkt.module.product.ProductService;
import com.hkt.module.product.entity.ProductTag;
import com.hkt.module.product.util.ProductScenario;

public class PrintServiceUnitTest extends AbstractUnitTest {
  static ServiceCallback<PrintMachineService> FAIL_CALLBACK = new ServiceCallback<PrintMachineService>() {
    public void callback(PrintMachineService service) {
      throw new RuntimeException("Fail. Expect a rollback if method annotate with the Transactionnal");
    }
  };
  @Autowired
  private PrintMachineService service;
  @Autowired
  private ProductService      productService;

  @Autowired
  private AccountService accountService;
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
  public void cleandb() throws Exception {
    service.deleteAll();
    accountService.deleteAll();
    productService.deleteAll();
    cmsService.deleteAll();
  }

 

  @Test
  public void testPrintMachine() {
    assertTrue(service.getPrintMachinesOfProduct("iphone5s").size()>0);
  }
}