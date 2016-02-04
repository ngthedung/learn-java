package com.hkt.client.rest.service;

import java.util.List;

import javax.print.PrintService;

import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hkt.client.rest.RESTClient;
import com.hkt.module.core.rest.Request;
import com.hkt.module.core.rest.Response;
import com.hkt.module.print.entity.PrintMachine;

@Component
public class PrintMachineServiceClient {
  @Autowired
  private RESTClient client;

  public PrintMachine savePrintMachine(PrintMachine printMachine) throws Exception {
    Request req = create("savePrintMachine");
    req.addParam("printMachine", printMachine);
    Response res = client.POST(req);
    return res.getDataAs(PrintMachine.class);
  }

  public List<PrintMachine> getPrintMachines() throws Exception {
    Request req = create("getPrintMachines");
    Response res = client.POST(req);
    return res.getDataAs(new TypeReference<List<PrintMachine>>() {
    });
  }

  public List<PrintService> getPrintServices(List<PrintMachine> printMachines) throws Exception {
    Request req = create("getPrintServices");
    req.addParam("printMachines", printMachines);
    Response res = client.POST(req);
    return res.getDataAs(new TypeReference<List<PrintService>>() {
    });
  }

  public List<PrintMachine> getPrintMachinesOfProduct(String productCode) throws Exception {
    Request req = create("getPrintMachinesOfProduct");
    req.addParam("productCode", productCode);
    Response res = client.POST(req);
    return res.getDataAs(new TypeReference<List<PrintMachine>>() {
    });
  }

  public List<PrintMachine> getPrintMachinesOfTable(String tableCode, String locationCode, String path) throws Exception {
    Request req = create("getPrintMachinesOfTable");
    req.addParam("tableCode", tableCode);
    req.addParam("locationCode", locationCode);
    req.addParam("path", path);
    Response res = client.POST(req);
    return res.getDataAs(new TypeReference<List<PrintMachine>>() {
    });
  }
  
  public PrintMachine getPrintMachineByPrintMachineName(String printMachineName) throws Exception{
    Request req = create("getPrintMachineByPrintMachineName");
    req.addParam("printMachineName", printMachineName);
    Response res = client.POST(req);
    return res.getDataAs(PrintMachine.class);
  }

  public boolean deleteAll() throws Exception {
    Request req = create("deleteAll");
    client.POST(req);
    return true;
  }

  public void scandPrintMachine() throws Exception {
    Request req = create("scandPrintMachine");
    client.POST(req);
  }

  Request create(String method) {
    return new Request("print", "PrintMachineService", method);
  }
}
