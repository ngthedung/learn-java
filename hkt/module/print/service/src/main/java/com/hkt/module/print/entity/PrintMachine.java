package com.hkt.module.print.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.hkt.module.account.entity.Account;
import com.hkt.module.account.entity.AccountGroup;
import com.hkt.module.core.entity.AbstractPersistable;
import com.hkt.module.product.entity.ProductTag;
import com.hkt.module.promotion.entity.ProductTarget;
import com.hkt.module.restaurant.entity.Location;

@Table
@Entity
public class PrintMachine extends AbstractPersistable<Long> {
  private static final long serialVersionUID = 1L;
  public static final String FIELD_PRINT_MACHINE_NAME = "printMachineName";
  public static final String FIELD_PRINT_MACHINE_CODE = "printMachineCode";
  public static final String TEMPLATE_A4 = "A4";
  public static final String TEMPLATE_A5 = "A5";
  public static final String TEMPLATE_A6 = "A6";
  public static final String TEMPLATE_K80 = "K80";
  public static final String TEMPLATE_K57 = "K57";

  @NotNull
  private String printMachineName;
  private String printMachineCode;
  private String template;
  private String description;

  @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE, CascadeType.REFRESH })
  @JoinTable(name = "PrintMachine_Location", joinColumns = { @JoinColumn(name = "printMachineId", referencedColumnName = "id", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "locationId", referencedColumnName = "id", nullable = false) })
  private List<Location> locations;

  @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE, CascadeType.REFRESH })
  @JoinTable(name = "PrintMachine_Table", joinColumns = { @JoinColumn(name = "printMachineId", referencedColumnName = "id", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "tableId", referencedColumnName = "id", nullable = false) })
  private List<com.hkt.module.restaurant.entity.Table> tables;

  @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE, CascadeType.REFRESH })
  @JoinTable(name = "PrintMachine_ProductTag", joinColumns = { @JoinColumn(name = "printMachineId", referencedColumnName = "id", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "productTagId", referencedColumnName = "id", nullable = false) })
  private List<ProductTag> productTags;

  @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE, CascadeType.REFRESH })
  @JoinTable(name = "PrintMachine_AccountGroup", joinColumns = { @JoinColumn(name = "printMachineId", referencedColumnName = "id", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "accountGroupId", referencedColumnName = "id", nullable = false) })
  private List<AccountGroup> accountGroups;

  public PrintMachine() {
  }

  public PrintMachine(Long id) {
    this.setId(id);
  }

  public List<AccountGroup> getAccountGroups() {
    return accountGroups;
  }

  public void setAccountGroups(List<AccountGroup> accountGroups) {
    this.accountGroups = accountGroups;
  }

  public String getPrintMachineName() {
    return printMachineName;
  }

  public void setPrintMachineName(String printMachineName) {
    this.printMachineName = printMachineName;
  }

  public String getPrintMachineCode() {
    return printMachineCode;
  }

  public void setPrintMachineCode(String printMachineCode) {
    this.printMachineCode = printMachineCode;
  }

  public String getTemplate() {
    return template;
  }

  public void setTemplate(String template) {
    this.template = template;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<ProductTag> getProductTags() {
    return productTags;
  }

  public void setProductTags(List<ProductTag> productTags) {
    this.productTags = productTags;
  }

  public List<Location> getLocations() {
    return locations;
  }

  public void setLocations(List<Location> locations) {
    this.locations = locations;
  }

  public List<com.hkt.module.restaurant.entity.Table> getTables() {
    return tables;
  }

  public void setTables(List<com.hkt.module.restaurant.entity.Table> tables) {
    this.tables = tables;
  }

  @Override
  public String toString() {
    return printMachineName;
  }

}
