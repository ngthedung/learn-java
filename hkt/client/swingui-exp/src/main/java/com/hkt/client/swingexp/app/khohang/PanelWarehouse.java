package com.hkt.client.swingexp.app.khohang;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.lucene.util.WAH8DocIdSet;
import org.hibernate.jpa.criteria.predicate.TruthValue;

import com.hkt.client.swingexp.app.component.MyPanel;
import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.DialogTest;
import com.hkt.client.swingexp.app.core.IDialogMain;
import com.hkt.client.swingexp.app.core.MyGroupLayout;
import com.hkt.client.swingexp.app.hethong.PanelRecybin;
import com.hkt.client.swingexp.app.hethong.PanelChoise;
import com.hkt.client.swingexp.app.hethong.PanelTestAll;
import com.hkt.client.swingexp.app.khohang.list.TableAddWarehouse;
import com.hkt.client.swingexp.model.ProductModelManager;
import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.client.swingexp.model.WarehouseModelManager;
import com.hkt.module.account.entity.AccountMembership.Capability;
import com.hkt.module.warehouse.entity.Warehouse;
import com.hkt.util.text.DateUtil;

@SuppressWarnings("serial")
public class PanelWarehouse extends MyPanel implements IDialogMain {

  public static String permission;
  private JLabel lbLabel, lbCode, lbDescription, lbMessenger;
  private JTextField txtLabel, txtCode;
  private JTextArea txtDescription;
  private JScrollPane scrollPaneTable;
  private TableAddWarehouse tbWarehouse;
  private Warehouse warehouse = new Warehouse();
  private boolean edit = false;
  private boolean restore = true;
  public PanelWarehouse() throws Exception {
    init();
    setOpaque(false);
    reset();
  }

  // Hàm tạo giao diện
  private void init() throws Exception {

    lbMessenger = new JLabel("");
    lbMessenger.setForeground(Color.RED);
    scrollPaneTable = new JScrollPane();
    scrollPaneTable.setOpaque(false);
    scrollPaneTable.getViewport().setOpaque(false);
    scrollPaneTable.setBorder(null);

    tbWarehouse = new TableAddWarehouse(getWarehouses());
    tbWarehouse.setName("tbWarehouse");
    tbWarehouse.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent event) {
        try {
          tbWarehouseMouseClicked(event);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });

    tbWarehouse.setPreferredScrollableViewportSize(new Dimension(200, 290));
    scrollPaneTable.setViewportView(tbWarehouse);
    lbDescription = new JLabel("Miêu tả:");
    txtDescription = new JTextArea();
    txtDescription.setPreferredSize(new Dimension(250, 53));
    txtDescription.setName("txtDescription");
    txtDescription.setDisabledTextColor(Color.black);
    lbLabel = new JLabel("Tên");
    lbCode = new JLabel("Mã");

    txtLabel = new JTextField();
    txtLabel.setName("txtLabel");
    txtLabel.setDisabledTextColor(Color.black);

    txtCode = new JTextField();
    txtCode.setName("txtCode");
    txtCode.setDisabledTextColor(Color.black);

    MyGroupLayout layout = new MyGroupLayout(this);
    layout.add(0, 0, lbLabel);
    layout.add(0, 1, txtLabel);
    layout.add(0, 2, lbCode);
    layout.add(0, 3, txtCode);

    layout.add(1, 0, lbDescription);
    layout.add(1, 1, txtDescription);
    layout.addMessenger(lbMessenger);

    // Giao diện Button cập nhật index đi cùng với scrollPaneTable
    layout.add(2, 0, new JLabel());
    layout.add(2, 1, scrollPaneTable);
    layout.updateGui();
  }

  // Hàm click vào 1 dòng để chỉnh sửa đối tượng
  protected void tbWarehouseMouseClicked(MouseEvent event) throws Exception {
    warehouse = tbWarehouse.getSelectedBean();
    int click = 2;
    if (warehouse.getName().indexOf("HktTest") > 0 && event.getButton() == 3) {
      click = 1;
    }
    if (event.getClickCount() >= click) {
      setData();
      setEnableCompoment(false);
      ((DialogMain) getRootPane().getParent()).showButton(false);
    }
    refresh();
  }

  @Override
  public void save() throws Exception {
    if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.READ) {
      JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
      return;
    } else {
      // Lấy dữ liệu đổ vào đối tượng
      warehouse = getData();
      // Kiểm tra các trường dữ liệu sau khi đổ dữ liệu vào
      if (!checkData()) {
        return;
      }
      if(restore){
    	  if (warehouse != null) {
    	        WarehouseModelManager.getInstance().saveWarehouse(warehouse);
    	        reset();
    	      }
      }      
    }
  }

  private boolean checkData() {
//    boolean check = true;
    // Biến đếm số lỗi phát sinh -> tăng lên khi gặp lỗi
 	int countError = 0;
 	boolean codeError = false;
//    if (warehouse.getWarehouseId().isEmpty()) {
//      lbCode.setForeground(Color.red);
//      countError++;
//    } else {
//      lbCode.setForeground(Color.black);
//    }
//    if (warehouse.getName().isEmpty()) {
//      lbLabel.setForeground(Color.red);
//      countError++;
//    } else {
//      lbLabel.setForeground(Color.black);
//    }
    if (!txtLabel.getText().equals("") && txtLabel.getText() != null) {
		lbLabel.setForeground(new Color(51, 51, 51));
	} else {
		lbLabel.setForeground(Color.RED);
		countError++;
	}
    // Kiểm tra xem có bị trùng code hay không
    if (txtCode.isEnabled()) {
    	if(!txtCode.getText().equals("") && !txtCode.getText().isEmpty()){
    		lbCode.setForeground(new Color(51, 51, 51));
    		try {
				Warehouse w = WarehouseModelManager.getInstance().getWarehouseByWarehouseId(txtCode.getText());
				if(w!=null){
					lbCode.setForeground(Color.RED);
					codeError = true;
					countError++;
					if (w.isRecycleBin()) {
						PanelRecybin panel = new PanelRecybin("Mã đã bị xóa trước đó!", " Chọn ĐỒNG Ý để lấy lại thông tin ban đầu hoặc THOÁT và nhập mã khác!");
						panel.setName("Recybin");
						DialogResto dialog = new DialogResto(panel, false, 0, 120);
						dialog.setName("dlRecybin");
						dialog.setTitle("Thêm kho mới");
						dialog.setLocationRelativeTo(null);
						dialog.setModal(true);
						dialog.setVisible(true);
						if (panel.isDelete()) {
							restore = false;
							w.setRecycleBin(false);
							WarehouseModelManager.getInstance().saveWarehouse(w);
							reset();
							return true;
						}
				   }
			  } 
			} catch (Exception e) {
				
				e.printStackTrace();
			}
    	}else {
			lbCode.setForeground(Color.RED);
			countError++;
		}
//    	
//      try {
//    	  
//        if (WarehouseModelManager.getInstance().getWarehouseByWarehouseId(txtCode.getText()) != null) {
//          check = false;
//          lbCode.setForeground(Color.red);
//        }
//      } catch (Exception e) {
//        e.printStackTrace();
//      }
    }

     // Kết thúc kiểm tra hiện thông báo nếu có lỗi
 		if (countError > 0) {
 			if (codeError)
 				lbMessenger.setText("Kiểm tra mã bị trùng - lỗi báo đỏ");
 			else
 				lbMessenger.setText("Vui lòng kiểm tra lỗi báo đỏ");
 			return false;
 		} else {
 			lbMessenger.setText("");
 			return true;
 		}
//    
//    if (!check) {
//      lbMessenger.setText("Dữ liệu vùng đỏ bị lỗi");
//      lbMessenger.setForeground(Color.red);
//      lbMessenger.setVisible(true);
//      return false;
//    } else {
//      lbMessenger.setText(" ");
//      return true;
//    }
  }

  // Hàm cho phép sửa giao diện (enable các giao diện đã disible trước đó)
  @Override
  public void edit() throws Exception {
    if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.READ) {
      JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
      return;
    } else {
      setEnableCompoment(true);
      txtCode.setEnabled(false);
      txtCode.setForeground(Color.black);
      ;
    }
  }

  // Hàm xóa đối tượng
  @Override
  public void delete() throws Exception {
    if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.ADMIN) {
      String str = "Xóa Kho ";
      PanelChoise pnPanel = new PanelChoise(str + warehouse + "?");
      pnPanel.setName("Xoa");
      DialogResto dialog1 = new DialogResto(pnPanel, false, 0, 80);
      dialog1.setName("dlXoa");
      dialog1.setTitle("Xóa kho");
      dialog1.setLocationRelativeTo(null);
      dialog1.setModal(true);
      dialog1.setVisible(true);

      if (pnPanel.isDelete()) {
        WarehouseModelManager.getInstance().deleteWarehouseByWarehouseId(warehouse.getWarehouseId());
        loadTable();
        reset();
      } else if (pnPanel.isDelete() == false) {
        reset();
      }
    } else {
      JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
    }
  }

  // Hàm reset lại giao diện
  @Override
  public void reset() throws Exception {
    setEnableCompoment(true);
    txtLabel.setText("");
    txtCode.setText(DateUtil.asCompactDateTimeId(new Date()));
    txtDescription.setText("");

    lbLabel.setForeground(new Color(51, 51, 51));
	lbCode.setForeground(new Color(51, 51, 51));
    lbDescription.setForeground(new Color(51, 51, 51));
    lbMessenger.setText(" ");

    warehouse = new Warehouse();
    loadTable();

  }

  @Override
  public void refresh() throws Exception {
    setData();
    setEnableCompoment(false);
    lbLabel.setForeground(Color.black);
    lbCode.setForeground(Color.black);
    lbMessenger.setText(" ");
  }

  private void loadTable() throws Exception {
    warehouse.getWarehouseId();
    tbWarehouse.setData(getWarehouses());
  }

  private Warehouse getData() {
	  restore = true;
    try {
      if (warehouse == null) {
        warehouse = new Warehouse();
      }
      warehouse.setWarehouseId(txtCode.getText());
      warehouse.setName(txtLabel.getText());
      warehouse.setDescription(txtDescription.getText());
      lbMessenger.setText(" ");
      return warehouse;
    } catch (Exception e) {
      lbMessenger.setVisible(true);
      lbMessenger.setText("Lỗi định dạng dữ liệu");
      e.printStackTrace();
      return new Warehouse();
    }
  }

  private void setData() {
    try {

      txtCode.setText(warehouse.getWarehouseId());
      txtLabel.setText(warehouse.getName());
      txtDescription.setText(warehouse.getDescription());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void setEnableCompoment(boolean value) {
    txtLabel.setEnabled(value);
    txtCode.setEnabled(value);
    txtDescription.setEnabled(value);
  }

  private List<Warehouse> getWarehouses() throws Exception {

    return WarehouseModelManager.getInstance().findWarehouses();
  }

  @Override
  public void deleteDataTest() {
    if (!PanelTestAll.runAll) {
      DialogTest.getInstance().show("Xóa toàn bộ dữ liệu test ?");
      if (DialogTest.getInstance().isTest()) {
        try {
          WarehouseModelManager.getInstance().deleteTest("HktTest");
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
  }
}
