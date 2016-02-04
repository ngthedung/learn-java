package com.hkt.module.print.repository;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;

import org.springframework.beans.factory.annotation.Autowired;

import com.hkt.module.core.entity.FilterQuery;
import com.hkt.module.core.entity.FilterResult;
import com.hkt.module.print.entity.PrintMachine;

public class PrintMachineTool {
  
  @Autowired
  PrintMachineRepository  printMachineDAO;

  public PrintMachineTool() {
      scandPrintMachine();
  }

  private void scandPrintMachine() {
      DocFlavor flavor = DocFlavor.SERVICE_FORMATTED.PRINTABLE;
      PrintService[] printServices = PrintServiceLookup.lookupPrintServices(flavor, null);
      for (int i = 0; i < printServices.length; i++) {
          String name=getPrinMachineName(printServices[i]);
          if (!checkNamePintMachine(name)) {
              PrintMachine pm = new PrintMachine();
              pm.setPrintMachineName(name);
              pm.setTemplate(" ");
              printMachineDAO.save(pm);
          }
      }
  }

  public boolean checkNamePintMachine(String namePrintMachine) {
     FilterQuery query = new FilterQuery() ;
     query.add("printMachineName", FilterQuery.Operator.STRINGEQ, namePrintMachine) ;
     List<PrintMachine> result = (List<PrintMachine>)printMachineDAO.findAll() ;
      return !result.isEmpty();
  }

  public String getPrinMachineName(PrintService printService) {
      String namePrintMachine = printService.getName();
      int index = namePrintMachine.lastIndexOf("\\");
      if (index >= 0) {
          namePrintMachine = namePrintMachine.substring(index + 1, namePrintMachine.length());
      }
      return namePrintMachine;
  }

  public String getPrinMachineAddress(PrintService printService) {
      try {
          String namePrintMachine = printService.getName();
          int index = namePrintMachine.lastIndexOf("\\");
          if (index >= 0) {
              String ips = (namePrintMachine.substring(0, index));
              int idex = ips.indexOf("\\");
              while (idex >= 0) {
                  ips = ips.substring(idex + 1, ips.length());
                  idex = ips.indexOf("\\");
              }
              return ips;
          }
          return InetAddress.getLocalHost().getLocalHost().getHostAddress();
      } catch (UnknownHostException ex) {
          return "";
      }
  }
}
