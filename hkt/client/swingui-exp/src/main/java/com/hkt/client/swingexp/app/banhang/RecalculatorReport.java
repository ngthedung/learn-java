package com.hkt.client.swingexp.app.banhang;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import com.hkt.client.swingexp.app.component.ExtendJLabel;
import com.hkt.client.swingexp.app.component.MyJDateChooser;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.client.swingexp.app.core.MyGroupLayout;
import com.hkt.client.swingexp.app.main.Main;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.module.warehouse.entity.WarehouseInventory;

@SuppressWarnings("serial")
public class RecalculatorReport extends JPanel implements IDialogResto {
  private JLabel lblOwner, lbDate, lbStartDate, lbEndDate;
  @SuppressWarnings("rawtypes")
  private JComboBox cbOwner, cbDate;
  private MyJDateChooser txtStartDate, txtEndDate;
  // private Processing processing;
  public static String permission;
  private boolean warehouse = false;
  private boolean quantitative = false;

  @SuppressWarnings({ "unchecked", "rawtypes" })
  public RecalculatorReport() {
    this.setBackground(new Color(255, 255, 255));
    System.out.println(Calendar.getInstance().get(Calendar.DAY_OF_WEEK) + "   "
        + Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
    // processing = new Processing();
    lblOwner = new ExtendJLabel(" Loại");
    lbDate = new ExtendJLabel("Tính từ");

    lbStartDate = new ExtendJLabel("Từ ngày");
    lbEndDate = new ExtendJLabel("Đến ngày");

    txtStartDate = new MyJDateChooser();
    txtStartDate.setDateFormatString("dd/MM/yyyy");


    txtEndDate = new MyJDateChooser();
    txtEndDate.setDateFormatString("dd/MM/yyyy");


    List<String> accOrg = new ArrayList<String>();
    accOrg.add("Định lượng");
    accOrg.add("Kho thực");
    accOrg.add("Tất cả");
    cbOwner = new JComboBox(accOrg.toArray());

    cbDate = new JComboBox();
    cbDate.setModel(new DefaultComboBoxModel(new String[] { "Hôm nay", "Hôm qua", "Hôm kia", "Đầu tuần này",
        "Đầu tháng này", "Đầu năm nay", "Khác" }));
    cbDate.addItemListener(new ItemListener() {

      @Override
      public void itemStateChanged(ItemEvent e) {
        checkCombo();

      }
    });

    getContentPanel();
    addKeyBindings(this);
  }

  private void getContentPanel() {
    setOpaque(false);
    MyGroupLayout layout = new MyGroupLayout(this);
    layout.add(0, 0, lblOwner);
    layout.add(0, 1, cbOwner);
    layout.add(1, 0, lbDate);
    layout.add(1, 1, cbDate);
    layout.add(2, 0, lbStartDate);
    layout.add(2, 1, txtStartDate);
    layout.add(2, 2, lbEndDate);
    layout.add(2, 3, txtEndDate);
    layout.updateGui();

  }

  private void checkCombo() {
    if (cbDate.getSelectedItem().equals("Khác")) {
      txtStartDate.setEnabled(true);
      txtEndDate.setEnabled(true);
      txtStartDate.setDate(new Date());
      txtEndDate.setDate(new Date());

    } else {
      txtStartDate.setEnabled(false);
      txtEndDate.setEnabled(false);
      txtStartDate.setDate(null);
      txtEndDate.setDate(null);

    }
  }

  @Override
  public void Save() throws Exception {
  	AccountingModelManager.getInstance().setList(new ArrayList<WarehouseInventory>());
    if(cbOwner.getSelectedIndex()==0){
      quantitative = true;
    }
    if(cbOwner.getSelectedIndex()==1){
      warehouse = true;
    }
    if(cbOwner.getSelectedIndex()==2){
      warehouse = true;
      quantitative = true;
    }
    // processing.setVisible(true);
    // try {
    // new SwingWorker<Void, Void>() {
    // @Override
    // protected Void doInBackground() throws Exception {
    // // Tính thờ gian query
    Thread t = new Thread(){
      @Override
      public void run() {
      	AccountingModelManager.getInstance().updateQuantityIdentityProduct();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Main.labelStatus.setText("Đang tính lại thống kê kho:");
        long startTime = System.currentTimeMillis();
        if (cbDate.getSelectedIndex() == 6) {
          if (txtStartDate.getDate() != null && txtEndDate.getDate() != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(txtStartDate.getDate());
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 01);

            Calendar c1 = Calendar.getInstance();
            c1.setTime(txtEndDate.getDate());
            c1.set(Calendar.HOUR_OF_DAY, 23);
            c1.set(Calendar.MINUTE, 59);
            while (c.before(c1)) {
              AccountingModelManager.getInstance().caculate(c.getTime(), warehouse, quantitative);
              c.add(Calendar.DAY_OF_MONTH, 1);
            }
          }
        } else {
          int type = Calendar.DAY_OF_MONTH;
          Calendar c = Calendar.getInstance();
          if (cbDate.getSelectedIndex() == 1) {
            c.add(Calendar.DAY_OF_MONTH, -1);
          }
          if (cbDate.getSelectedIndex() == 2) {
            c.add(Calendar.DAY_OF_MONTH, -2);
          }
          if (cbDate.getSelectedIndex() == 3) {
            c.add(Calendar.DAY_OF_WEEK, -(Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1));
            type = Calendar.DAY_OF_WEEK;
          }
          if (cbDate.getSelectedIndex() == 4) {
            c.add(Calendar.DAY_OF_MONTH, -(Calendar.getInstance().get(Calendar.DAY_OF_MONTH) - 1));
          }
          if (cbDate.getSelectedIndex() == 5) {
            c.add(Calendar.DAY_OF_YEAR, -(Calendar.getInstance().get(Calendar.DAY_OF_YEAR) - 1));
            type = Calendar.DAY_OF_YEAR;
          }
          c.set(Calendar.HOUR_OF_DAY, 0);
          c.set(Calendar.MINUTE, 01);

          Calendar c1 = Calendar.getInstance();
          c1.setTime(new Date());
          c1.set(Calendar.HOUR_OF_DAY, 23);
          c1.set(Calendar.MINUTE, 59);
          while (c.before(c1)) {
            Main.labelMessenger.setText("Đang tính ngày: "+df.format(c.getTime()));
            AccountingModelManager.getInstance().caculate(c.getTime(), warehouse, quantitative);
            c.add(type, 1);
           
          }
        }

        // Kết quả thời gian query:
        long endTime = System.currentTimeMillis();
        NumberFormat formatter = new DecimalFormat("#0.00000");
        String time = formatter.format((endTime - startTime) / 1000d);
        Main.labelMessenger.setText("Đã tính xong thống kê, tổng thời gian tính: "+time);
        try {
          Thread.sleep(60000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        Main.labelMessenger.setText("");
        Main.labelStatus.setText("");
      }
    };
    t.start();
    ((JDialog)getRootPane().getParent()).dispose();
    // System.out.println("###$$$ TIME RUN QUERY: " + time);
    //
    // return null;
    // };
    //
    // @Override
    // protected void done() {
    // processing.setVisible(false);
    // };
    // }.execute();
    // } catch (Exception ex) {
    // processing.setVisible(false);
    // JOptionPane.showMessageDialog(null, "Quá trình thao tác phát sinh lỗi!" +
    // ex.toString());
    // }
  }
  private void addKeyBindings(JComponent jc) {

		jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0, false), "F2");
		jc.getActionMap().put("F2", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				try {
					PanelCreateDataTest recalculatorReport = new PanelCreateDataTest();
					DialogResto dialog = new DialogResto(recalculatorReport, false, 0, 140);
					dialog.setIcon("datban1.png");
					dialog.setLocationRelativeTo(null);
					dialog.setTitle("Tao du lieu mau");
					dialog.setModal(true);
					dialog.setVisible(true);
					
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
  }

}
