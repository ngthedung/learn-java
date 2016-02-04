package com.hkt.client.swingexp.model;

import java.util.List;

import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;

import com.hkt.client.rest.ClientContext;
import com.hkt.client.rest.service.PrintMachineServiceClient;
import com.hkt.module.print.entity.PrintMachine;

public class PrintMachineModelManager {

  private static PrintMachineModelManager instance;
  private ClientContext clientContext = ClientContext.getInstance();
  private PrintMachineServiceClient client = clientContext.getBean(PrintMachineServiceClient.class);
  private PrintService[] printServices;
  private DocFlavor flavor;

  public PrintMachineModelManager() {
    flavor = DocFlavor.SERVICE_FORMATTED.PRINTABLE;
    printServices = PrintServiceLookup.lookupPrintServices(flavor, null);
    try {
      scandPrintMachine();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void scandPrintMachine() throws Exception {
    for (int i = 0; i < printServices.length; i++) {
      String name = getPrinMachineName(printServices[i]);
      if (!checkNamePintMachine(name)) {
        PrintMachine pm = new PrintMachine();
        pm.setPrintMachineName(name);
        pm.setTemplate(" ");
        client.savePrintMachine(pm);
      }
    }
  }

  public boolean checkNamePintMachine(String namePrintMachine) throws Exception {
    return client.getPrintMachineByPrintMachineName(namePrintMachine) != null;
  }

  public static PrintMachineModelManager getInstance() {
    if (instance == null) {
      instance = new PrintMachineModelManager();
    }
    return instance;
  }

  public PrintMachine savePrintMachine(PrintMachine printMachine) throws Exception {
    return client.savePrintMachine(printMachine);
  }

  public List<PrintMachine> getPrintMachines() throws Exception {
    return client.getPrintMachines();
  }

  public List<PrintService> getPrintServices(List<PrintMachine> printMachines) throws Exception {
    return client.getPrintServices(printMachines);
  }

  public List<PrintMachine> getPrintMachinesOfProduct(String productCode) throws Exception {
    return client.getPrintMachinesOfProduct(productCode);
  }

  public List<PrintMachine> getPrintMachinesOfTable(String tableCode, String locationCode, String path) throws Exception {
    return client.getPrintMachinesOfTable(tableCode, locationCode, path);
  }

  public PrintService getPrintService(PrintMachine printMachine) throws Exception {
    for (int j = 0; j < printServices.length; j++) {
      String name = getPrinMachineName(printServices[j]);
      if (name.equals(printMachine.getPrintMachineName())) {
        return printServices[j];
      }
    }
    return null;
  }

  private String getPrinMachineName(PrintService printService) {
    String namePrintMachine = printService.getName();
    int index = namePrintMachine.lastIndexOf("\\");
    if (index >= 0) {
      namePrintMachine = namePrintMachine.substring(index + 1, namePrintMachine.length());
    }
    return namePrintMachine;
  }

  public boolean deleteAll() throws Exception {
    return client.deleteAll();
  }

}
