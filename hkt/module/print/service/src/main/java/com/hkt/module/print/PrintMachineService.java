package com.hkt.module.print;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hkt.module.core.entity.FilterQuery;
import com.hkt.module.core.entity.FilterResult;
import com.hkt.module.print.entity.PrintMachine;
import com.hkt.module.print.repository.PrintMachineRepository;
import com.hkt.module.product.ProductService;
import com.hkt.module.product.entity.Product;
import com.hkt.module.product.entity.ProductTag;

@Service("PrintMachineService")
public class PrintMachineService {
  @Autowired
  private PrintMachineRepository printMachineRepository;
  
  @Autowired
  private ProductService productService;

  private DocFlavor flavor;
  private PrintService[] printServices;

  public PrintMachineService() {
    flavor = DocFlavor.SERVICE_FORMATTED.PRINTABLE;
    printServices = PrintServiceLookup.lookupPrintServices(flavor, null);
  }
  
  @PostConstruct
  public void onInit() {
    try {
      scandPrintMachine();
    } catch (Exception e) {
    }
   
  }

  @Transactional
  public PrintMachine savePrintMachine(PrintMachine printMachine) {
    return printMachineRepository.save(printMachine);
  }
  
  @Transactional
  public PrintMachine getPrintMachineByPrintMachineName(String printMachineName){
    return printMachineRepository.getPrintMachineByPrintMachineName(printMachineName);
  }
  

  @Transactional(readOnly = true)
  public List<PrintMachine> getPrintMachines() {
    return (List<PrintMachine>)printMachineRepository.findAll();
  }

  @Transactional(readOnly = true)
  public List<PrintService> getPrintServices(List<PrintMachine> printMachines) {
    List<PrintService> list = new ArrayList<PrintService>();
    for (int i = 0; i < printMachines.size(); i++) {
      for (int j = 0; j < printServices.length; j++) {
        String name = getPrinMachineName(printServices[j]);
        if (name.equals(printMachines.get(i).getPrintMachineName()) && !list.contains(printServices[j])) {
          list.add(printServices[j]);
          break;
        }
      }
    }
    return list;
  }
  
  @Transactional(readOnly = true)
  public PrintService getPrintService(PrintMachine printMachine) {
      for (int j = 0; j < printServices.length; j++) {
        String name = getPrinMachineName(printServices[j]);
        if (name.equals(printMachine.getPrintMachineName())) {
          return printServices[j];
        }
      } 
    return null;
  }
  
  @Transactional(readOnly = true)
  public List<PrintMachine> getPrintMachinesOfProduct(String productCode) {
    Product product = productService.getProductByCode(productCode);
    List<String> tags = new ArrayList<String>();
    for(ProductTag productTag : product.getProductTags()){
      tags.add(productTag.getTag());
    }
    List<PrintMachine> printMachines = printMachineRepository.findPrintMachineByTags(tags);
    return printMachines;
  }
  
  
  @Transactional(readOnly = true)
  public List<PrintMachine> getPrintMachinesOfTable(String tableCode, String locationCode, String path) {
    List<PrintMachine> printMachines = printMachineRepository.findPrintMachineByTable(tableCode);
    if(printMachines==null || printMachines.isEmpty()){
      printMachines = printMachineRepository.findPrintMachineByLocation(locationCode);
    }
    if(printMachines==null || printMachines.isEmpty()){
      printMachines = printMachineRepository.findPrintMachineByDepartment(path);
    }
    return printMachines;
  }
  
  @Transactional(readOnly = true)
  public List<PrintMachine> getPrintMachinesOfDepartment(String path) {
    List<PrintMachine> printMachines = printMachineRepository.findPrintMachineByDepartment(path);
    return printMachines;
  }
  
  @Transactional
  public void deleteAll(){
    printMachineRepository.deleteAll();
   
  }
  
  public void scandPrintMachine() {
    for (int i = 0; i < printServices.length; i++) {
        String name=getPrinMachineName(printServices[i]);
        if (!checkNamePintMachine(name)) {
            PrintMachine pm = new PrintMachine();
            pm.setPrintMachineName(name);
            pm.setTemplate(" ");
            printMachineRepository.save(pm);
        }
    }
}

public boolean checkNamePintMachine(String namePrintMachine) {
    return printMachineRepository.getPrintMachineByPrintMachineName(namePrintMachine)!=null;
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