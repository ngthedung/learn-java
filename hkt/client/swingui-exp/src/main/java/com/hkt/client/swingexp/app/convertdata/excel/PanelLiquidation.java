package com.hkt.client.swingexp.app.convertdata.excel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import com.hkt.client.swingexp.app.component.GridLabelLayoutPabel;
import com.hkt.client.swingexp.app.core.DialogNotice;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.client.swingexp.app.core.MouseEventClickButtonDialog;
import com.hkt.client.swingexp.app.hethong.PanelRecybin;
import com.hkt.client.swingexp.app.hethong.PanelChoise;
import com.hkt.client.swingexp.app.hethong.Processing;
import com.hkt.client.swingexp.homescreen.HomeScreen;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.client.swingexp.model.CustomerModelManager;
import com.hkt.client.swingexp.model.HRModelManager;
import com.hkt.module.accounting.entity.Attribute;
import com.hkt.module.accounting.entity.Contributor;
import com.hkt.module.accounting.entity.Invoice;
import com.hkt.module.accounting.entity.InvoiceDetail;
import com.hkt.module.accounting.entity.InvoiceItem;
import com.hkt.module.accounting.entity.InvoiceItemAttribute;
import com.hkt.module.accounting.entity.InvoiceTransaction;
import com.hkt.module.core.entity.FilterQuery;
import com.hkt.module.core.entity.FilterQuery.Operator;
import com.hkt.module.hr.entity.Employee;
import com.hkt.module.partner.customer.entity.Customer;
import com.hkt.module.partner.customer.entity.Point;
import com.hkt.module.partner.customer.entity.PointConversionRule;
import com.hkt.module.partner.customer.entity.PointTransaction;
import com.hkt.module.product.entity.Product;
import com.hkt.module.product.entity.ProductAttribute;
import com.hkt.module.promotion.CheckOS;
import com.hkt.module.restaurant.entity.Project;
import com.hkt.module.restaurant.entity.ProjectMember;
import com.hkt.module.warehouse.entity.IdentityProduct;
import com.hkt.module.warehouse.entity.IdentityProductAttribute;
import com.hkt.module.warehouse.entity.Warehouse;
import com.hkt.module.warehouse.entity.WarehouseInventory;
import com.hkt.module.warehouse.entity.WarehouseInventoryAttribute;
import com.hkt.module.warehouse.entity.WarehouseProfile;
import com.hkt.module.warehouse.entity.WarehouseProfileAttribute;

public class PanelLiquidation extends JPanel implements IDialogResto {
  private JButton btnImport, btnLiquidation, btnSampleExcel;
  private JComboBox cbChoose;
  private JCheckBox ckOperation, ckImportExport, ckPartner, ckProduct, ckPricingTable, ckHR, ckPoint;
  private BackupTool backupTool;
  private Processing processing;
  public static String permission;

  public PanelLiquidation() {
    setOpaque(false);
    setLayout(new BorderLayout());
    add(new TopPanel(), BorderLayout.PAGE_START);
    add(new MidPanel(), BorderLayout.CENTER);
    add(new BotPanel(), BorderLayout.PAGE_END);
    btnImport.addMouseListener(new MouseEventClickButtonDialog("iconOk.png", "iconOk.png", "iconOk.png"));
    btnLiquidation.addMouseListener(new MouseEventClickButtonDialog("iconOk.png", "iconOk.png", "iconOk.png"));
    btnSampleExcel.addMouseListener(new MouseEventClickButtonDialog("iconOk.png", "iconOk.png", "iconOk.png"));
    // Handling
    backupTool = new BackupTool();
    HandlingCheckBox();
    // dialog gif image
    processing = new Processing();
    processing.setAlwaysOnTop(true);
    processing.setSize(205, 220);
    processing.dispose();
    processing.setUndecorated(true);
    processing.setLocationRelativeTo(null);
    try {
    	Runtime.getRuntime().exec("taskkill /F /IM HKTServer.exe");
    } catch (Exception e) {
    }
  }

  
  // UI

  private class TopPanel extends JPanel {
    private TopPanel() {
      setOpaque(false);
      setLayout(new FlowLayout(FlowLayout.LEADING));
      btnImport = new JButton("Nhập thêm dữ liệu từ file Excel");
      btnImport.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
          doImportFromFile();

        }
      });
      btnImport.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/iconOk.png")));
      add(btnImport);
      btnLiquidation = new JButton("Thanh lý");
      btnLiquidation.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/iconOk.png")));
      btnLiquidation.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
          String chooseOption = cbChoose.getSelectedItem().toString();
          if (chooseOption.equals("Toàn bộ dữ liệu")) {
            processing.setVisible(true);
            try {
              new SwingWorker<Void, Void>() {

                @Override
                protected Void doInBackground() throws Exception {
                  DropDB("hktdb", "root", "root");
                  return null;
                };

                @Override
                protected void done() {
                  try {
                    String hkt = com.hkt.module.promotion.HKTFile.getDirectory("Database") + "/mysql/bin/mysql";
                    Process runtimeProcess;
                    try {
                        runtimeProcess = Runtime.getRuntime().exec(hkt+" -u root -proot -e \"create database hktdb CHARACTER SET utf8 COLLATE utf8_general_ci\"");
                     System.out.println(1); 
                    } catch (Exception ex) {
                      ex.printStackTrace();
                    }
                    CheckOS checkOS = new CheckOS();
                    if (checkOS.isWindows()) {
                      System.out.println(1);
                      Runtime.getRuntime().exec("taskkill /F /IM HKTServer.exe");
                    	Runtime.getRuntime().exec("taskkill /F /IM HKTSoft.exe");
                      Runtime.getRuntime().exec("taskkill /F /IM java.exe");
                    }
                    System.exit(0);
                  } catch (Exception e2) {
                  }
                  JOptionPane.showMessageDialog(null, "Thành công!");
                };
              }.execute();

            } catch (Exception ex) {
              JOptionPane.showMessageDialog(null, ex);
              JOptionPane.showMessageDialog(null, "Failed!");
            }
          } else if (chooseOption.equals("Từng phần")) {
            try {
              dropTableOption();
            } catch (Exception e1) {
              e1.printStackTrace();
            }
            // TODO
          }
        }
      });
      add(btnLiquidation);
      String[] chooseCombobox = { "Toàn bộ dữ liệu", "Từng phần" };
      cbChoose = new JComboBox(chooseCombobox);
      cbChoose.addItemListener(new ItemListener() {

        @Override
        public void itemStateChanged(ItemEvent e) {
          HandlingCheckBox();
        }
      });
      cbChoose.setPreferredSize(new Dimension(200, 35));
      add(cbChoose);
    }
  }

  private class MidPanel extends GridLabelLayoutPabel {
    private MidPanel() {
      setOpaque(false);
      ckOperation = new JCheckBox("Nghiệp vụ + Dự án + Biên lai + XN Kho");
      ckOperation.addItemListener(new ItemListener() {
  		
  		@Override
  		public void itemStateChanged(ItemEvent e) {
          if (ckOperation.isSelected()) {
            ckImportExport.setSelected(true);
            ckImportExport.setEnabled(false);
          } else {
            ckImportExport.setEnabled(true);
            ckImportExport.setSelected(false);
          }

        }
      });
      ckOperation.setOpaque(false);
      add(0, 0, ckOperation);
      ckImportExport = new JCheckBox("Xuất nhập kho");
      
      ckImportExport.setOpaque(false);
      add(0, 1, ckImportExport);
      ckPartner = new JCheckBox("Đối tác");
      ckPartner.setOpaque(false);
      add(0, 2, ckPartner);
      ckProduct = new JCheckBox("SP + Nhóm SP + Định lượng + BG");
      ckProduct.addItemListener(new ItemListener() {
		
		@Override
		public void itemStateChanged(ItemEvent e) {
			 if (ckProduct.isSelected()) {
		            ckPricingTable.setSelected(true);
		            ckPricingTable.setEnabled(false);
		            ckOperation.setSelected(true);
		            ckOperation.setEnabled(false);
		          } else {
		            ckPricingTable.setEnabled(true);
		            ckPricingTable.setSelected(false);
		            ckOperation.setSelected(false);
		            ckOperation.setEnabled(true);
		          }
			
		}
	});
      ckProduct.setOpaque(false);
      add(1, 0, ckProduct);
      ckPricingTable = new JCheckBox("Bảng giá");
      ckPricingTable.setOpaque(false);
      add(1, 1, ckPricingTable);
      ckHR = new JCheckBox("Nhân sự + Account");
      ckHR.setOpaque(false);
      ckHR.setOpaque(false);
      add(1, 2, ckHR);
      ckPoint = new JCheckBox("TK Định Lượng");
      ckPoint.setOpaque(false);
      ckPoint.setOpaque(false);
      add(1, 3, ckPoint);
    }
  }

  private class BotPanel extends JPanel {
    private BotPanel() {
      setBorder(BorderFactory.createTitledBorder("Hướng dẫn"));
      setOpaque(false);
      setLayout(new GridLayout());
      add(new LeftPanel());
      add(new RightPanel());
    }

    private class LeftPanel extends GridLabelLayoutPabel {
      private LeftPanel() {
        setOpaque(false);
        JLabel label1 = new JLabel("1.Nhập thêm dữ liệu từ Excel");
        add(0, 0, label1);
        add(1, 0, "   + File Excel phải có định dạng *.xls hoặc *.xlsx");
        add(2, 0, "   + File Excel phải có cấu trúc như mẫu quy định");
        JPanel panelButton = new JPanel();
        panelButton.setOpaque(false);
        panelButton.setLayout(new FlowLayout(FlowLayout.LEADING));
        btnSampleExcel = new JButton("File Excel mẫu");
        btnSampleExcel.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/iconOk.png")));
        panelButton.add(btnSampleExcel);
        add(3, 0, panelButton);
      }
    }

    private class RightPanel extends GridLabelLayoutPabel {
      private RightPanel() {
        setOpaque(false);
        JLabel label1 = new JLabel("2.Thanh lý dữ liệu");
        add(0, 0, label1);
        add(1, 0, "   + Thanh lý toàn bộ sẽ xóa trắng dữ liệu hiện có");
        add(2, 0, "   + Thanh lý và trả về dữ liệu mẫu sẽ trả về dữ liệu ban đầu");
        JLabel label2 = new JLabel("Chú ý: Dữ liệu bị xóa sẽ không thể khôi phục lại");
        label2.setFont(new Font("Tahoma", 14, Font.BOLD));
        label2.setForeground(Color.red);
        add(3, 0, label2);
      }
    }
  }

  // CODE xu ly

  private void doImportFromFile() {
	  String str = "Đồng ý để tiếp tục!";
      PanelChoise panel = new PanelChoise(str);
      panel.setName("Xoa");
      DialogResto dialog = new DialogResto(panel, false, 0, 80);
      dialog.setName("dlXoa");
      dialog.setTitle("Khôi phục hệ thống");
      dialog.setLocationRelativeTo(null);
      dialog.setModal(true);
      dialog.setVisible(true);
      if (panel.isDelete()) {
      JFileChooser fileChooser = new JFileChooser();
      String[] extension = { ".xlsx", ".xls" };
      fileChooser.setFileFilter(new FileFilterExtension(extension));
      int i = fileChooser.showOpenDialog(this);
      if (i == JFileChooser.APPROVE_OPTION) {
        File file = fileChooser.getSelectedFile();
        final String fileURL = file.getAbsolutePath();
        System.out.println("fileURL  " + fileURL);
        try {
          processing.setVisible(true);
          new SwingWorker<Void, Void>() {

            @Override
            protected Void doInBackground() throws Exception {
              backupTool.runImport(fileURL);
              return null;
            };

            @Override
            protected void done() {
              processing.setVisible(false);
              DialogNotice.getInstace().show();
            };
          }.execute();

        } catch (Exception ex) {
          this.setEnabled(true);
          JOptionPane.showMessageDialog(null, ex);
          JOptionPane.showMessageDialog(null, "Failed!");
        }
      }
    }
  }

  @Override
  public void Save() throws Exception {
    ((JDialog) this.getRootPane().getParent()).dispose();

  }

  private void HandlingCheckBox() {
    String choose = cbChoose.getSelectedItem().toString();
    if (choose.equals("Toàn bộ dữ liệu")) {
      ckOperation.setEnabled(false);
      ckImportExport.setEnabled(false);
      ckPartner.setEnabled(false);
      ckProduct.setEnabled(false);
      ckPricingTable.setEnabled(false);
      ckHR.setEnabled(false);
      ckPoint.setEnabled(false);
    } else {
      ckOperation.setEnabled(true);
      ckImportExport.setEnabled(true);
      ckPartner.setEnabled(true);
      ckProduct.setEnabled(true);
      ckPricingTable.setEnabled(true);
      ckHR.setEnabled(true);
      ckPoint.setEnabled(true);
    }
  }

  public static boolean DropDB(String dbName, String dbUserName, String dbPassword) {
    String hkt = com.hkt.module.promotion.HKTFile.getDirectory("Database") + "/mysql/bin/mysql";
    String executeCmd = hkt + " -u " + dbUserName + " --password=" + dbPassword + " -D " + dbName + " -e "
        + "\"DROP DATABASE " + dbName + "\"";
    Process runtimeProcess;
    try {
      System.out.println(executeCmd);
      runtimeProcess = Runtime.getRuntime().exec(executeCmd);
      int processComplete = runtimeProcess.waitFor();

      if (processComplete == 0) {
        System.out.println("Drop successfully");
        runtimeProcess = Runtime.getRuntime().exec(hkt+" -u root -proot -e create database hktdb");
        return true;
      } else {
        System.out.println("Could not drop");
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return false;
  }

  public void dropTableOption() throws Exception {
    if (ckOperation.isSelected()) {
      AccountingModelManager.getInstance().dropTable(InvoiceItemAttribute.class);
      AccountingModelManager.getInstance().dropTable(InvoiceItem.class);
      AccountingModelManager.getInstance().dropTable(Attribute.class);
      AccountingModelManager.getInstance().dropTable(InvoiceTransaction.class);
      AccountingModelManager.getInstance().dropTable(Contributor.class);
      AccountingModelManager.getInstance().dropTable(InvoiceDetail.class);
      AccountingModelManager.getInstance().dropTable(IdentityProductAttribute.class);
      AccountingModelManager.getInstance().dropTable(IdentityProduct.class);
      AccountingModelManager.getInstance().dropTable(WarehouseInventory.class);
      AccountingModelManager.getInstance().dropTable(WarehouseProfile.class);
      AccountingModelManager.getInstance().dropTable(Project.class);
      AccountingModelManager.getInstance().dropTable(ProjectMember.class);

    }
    if (ckImportExport.isSelected()) {
      // IdentityProduct
    	AccountingModelManager.getInstance().dropTable(Warehouse.class);
      AccountingModelManager.getInstance().dropTable(IdentityProductAttribute.class);
      AccountingModelManager.getInstance().dropTable(IdentityProduct.class);
      AccountingModelManager.getInstance().dropTable(WarehouseInventoryAttribute.class);
      AccountingModelManager.getInstance().dropTable(WarehouseInventory.class);
      AccountingModelManager.getInstance().dropTable(WarehouseProfileAttribute.class);
      AccountingModelManager.getInstance().dropTable(WarehouseProfile.class);
    }
    if (ckProduct.isSelected()) {
      // Delete invoices

      // IdentityProduct
      AccountingModelManager.getInstance().dropTable(IdentityProductAttribute.class);
      AccountingModelManager.getInstance().dropTable(IdentityProduct.class);
      // Warehouse
      AccountingModelManager.getInstance().dropTable(Warehouse.class);
      // restaurant_recipe
      AccountingModelManager.getInstance().dropTable("restaurant_recipeIngredient");
      AccountingModelManager.getInstance().dropTable("restaurant_recipe");
      // Pricing table
      AccountingModelManager.getInstance().dropTable("warehouse_productPrice");
      AccountingModelManager.getInstance().dropTable("warehouse_productPriceType");
      // Product
      AccountingModelManager.getInstance().dropTable("product_productTag");
      AccountingModelManager.getInstance().dropTable(ProductAttribute.class);
      AccountingModelManager.getInstance().dropTable("Product_MenuItemRef");
      AccountingModelManager.getInstance().dropTable(Product.class);
      // ProductTag
      AccountingModelManager.getInstance().dropTable("PrintMachine_ProductTag");
      AccountingModelManager.getInstance().dropTable("warehouse_productTag");
    }
    if (ckPricingTable.isSelected()) {
      // Pricing table
      AccountingModelManager.getInstance().dropTable("warehouse_productPrice");
      AccountingModelManager.getInstance().dropTable("warehouse_productPriceType");
    }
    // Customer
    if (ckPartner.isSelected()) {
      List<Customer> customers = CustomerModelManager.getInstance().getCustomers(false);
      for (Customer customer : customers) {
        AccountModelManager.getInstance().deleteAccountByLoginId(customer.getLoginId());
      }
      AccountingModelManager.getInstance().dropTable(Customer.class);
    }
    // Employee
    if (ckHR.isSelected()) {
      List<Employee> employees = HRModelManager.getInstance().getEmployees();
      for (Employee employee : employees) {
        AccountModelManager.getInstance().deleteAccountByLoginId(employee.getLoginId());
      }
      AccountingModelManager.getInstance().dropTable(Employee.class);
    }
    // Point
    if (ckPoint.isSelected()) {
    	AccountingModelManager.getInstance().dropTable(WarehouseInventoryAttribute.class);
      AccountingModelManager.getInstance().dropTable(WarehouseInventory.class);
      AccountingModelManager.getInstance().dropTable(WarehouseProfileAttribute.class);
      AccountingModelManager.getInstance().dropTable(WarehouseProfile.class);
    }
    // Barcode

    CheckOS checkOS = new CheckOS();
    if (checkOS.isWindows()) {
    	Runtime.getRuntime().exec("taskkill /F /IM HKTServer.exe");
    	Runtime.getRuntime().exec("taskkill /F /IM HKT Soft.exe");
      Runtime.getRuntime().exec("taskkill /F /IM java.exe");
    }
    System.out.println(2);
    System.exit(0);
  }
}
