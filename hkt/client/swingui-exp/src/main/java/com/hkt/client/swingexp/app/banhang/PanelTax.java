package com.hkt.client.swingexp.app.banhang;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.hkt.client.swingexp.app.component.ExtendJLabel;
import com.hkt.client.swingexp.app.component.ExtendJTextField;
import com.hkt.client.swingexp.app.component.MyPanel;
import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.DialogTest;
import com.hkt.client.swingexp.app.core.IDialogMain;
import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.client.swingexp.app.core.MyGroupLayout;
import com.hkt.client.swingexp.app.hethong.PanelRecybin;
import com.hkt.client.swingexp.app.hethong.PanelChoise;
import com.hkt.client.swingexp.app.hethong.PanelTestAll;
import com.hkt.client.swingexp.app.khuvucbanhang.TableListEmployee;
import com.hkt.client.swingexp.model.ProductModelManager;
import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.client.swingexp.model.WarehouseModelManager;
import com.hkt.module.account.entity.AccountMembership.Capability;
import com.hkt.module.product.entity.Tax;
import com.hkt.util.text.DateUtil;

/** GIAO DIỆN ::: THUẾ HÀNG HÓA */
@SuppressWarnings("serial")
public class PanelTax extends MyPanel implements IDialogMain {
	private ExtendJLabel lbCode, lbName, lbPercent, lbDescription, lbMessenger;
	private ExtendJTextField txtCode, txtName, txtPercent, txtDescription;
	private JScrollPane scrollPaneTable, scrollIndex;
	public static String permission;
	private TaxTable taxTable;
	private Tax tax = new Tax();
	private boolean restore = true;
	@SuppressWarnings("unused")
	private boolean Flag;

	public PanelTax() {
		init();
		reset();
	}

	private void init() {
		lbMessenger = new ExtendJLabel("");
		lbMessenger.setForeground(Color.red);
		scrollPaneTable = new JScrollPane();
		scrollPaneTable.setOpaque(false);
		scrollPaneTable.getViewport().setOpaque(false);
		scrollPaneTable.setBorder(null);

		taxTable = new TaxTable();
		taxTable.setName("tbtaxTable");

		taxTable.setTaxs(getObjects());
		taxTable.setName("tbtaxTable");
		taxTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				try {
					tableOptionsMouseClicked(event);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		taxTable.setPreferredScrollableViewportSize(new Dimension(200, 290));
		scrollPaneTable.setViewportView(taxTable);
		
		lbDescription = new ExtendJLabel("Miêu tả");
		txtDescription = new ExtendJTextField();
		txtDescription.setPreferredSize(new Dimension(250, 53));
		txtDescription.setName("txtDescription");

		lbCode = new ExtendJLabel("Mã thuế");
		lbName = new ExtendJLabel("Tên thuế");
		lbPercent = new ExtendJLabel("Tỷ lệ (%)");
		lbDescription = new ExtendJLabel("Miêu tả");

		txtCode = new ExtendJTextField();
		txtCode.setHorizontalAlignment(JTextField.RIGHT);
		txtCode.setName("txtCode");

		txtName = new ExtendJTextField();
		txtName.setName("txtName");

		txtPercent = new ExtendJTextField();
		txtPercent.setName("txtPercent");
		txtPercent.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

		txtDescription = new ExtendJTextField();
		txtDescription.setName("txtDescription");

		MyGroupLayout layout = new MyGroupLayout(this);
		layout.add(0, 0, lbCode);
		layout.add(0, 1, txtCode);
		layout.add(0, 2, lbName);
		layout.add(0, 3, txtName);

		layout.add(1, 0, lbPercent);
		layout.add(1, 1, txtPercent);
		layout.add(1, 2, lbDescription);
		layout.add(1, 3, txtDescription);
		layout.addMessenger(lbMessenger);

		// Thêm button cập nhật index
		scrollIndex = new JScrollPane();
		scrollIndex.setOpaque(false);
		scrollIndex.getViewport().setOpaque(false);
		scrollIndex.setBorder(null);
		scrollIndex.setViewportView(taxTable.getPanleButton());

		// Giao diện Button cập nhật index đi cùng với scrollPaneTable
		layout.add(2, 0, scrollIndex);
		layout.add(2, 1, scrollPaneTable);
		layout.updateGui();
		// txtCode.addMouseListener(new MouseAdapter() {
		// @Override
		// public void mouseClicked(MouseEvent e) {
		// OpenVitualkeyboard.getInstancce().vitualTextKeyboard(txtCode);
		// }
		// });
	}

	protected void tableOptionsMouseClicked(MouseEvent event) throws Exception {
		tax = taxTable.getSelectedBean();
		int click = 2;
		if (tax.getName().indexOf("HktTest") > 0 && event.getButton() == 3)
			click = 1;
		if (event.getClickCount() >= click) {
			setData();
			setEnableCompoment(false);
			((DialogMain) getRootPane().getParent()).showButton(false);
		}
		refresh();
	}

	private Tax getData() {
//		restore = true;
//		if (tax == null) {
//			tax = new Tax();
//			Flag = true;
//		}
//		tax.setCode(txtCode.getText());
//		tax.setName(txtName.getText());
//		tax.setIndex((getObjects().size() + 1));
//		tax.setDescription(txtDescription.getText());
//		tax.setPercent(MyDouble.parseDouble(txtPercent.getText()));
//		return tax;
		restore = true;
		try {
			if (tax == null) {
				tax = new Tax();
				Flag = true;
			}
			tax.setCode(txtCode.getText());
			tax.setName(txtName.getText());

			 if (txtPercent.getText().length() > 0) {
			tax.setPercent(Double.parseDouble(txtPercent.getText()));
			 } else {
			 lbPercent.setForeground(Color.red);
			 }
			tax.setDescription(txtDescription.getText());
			lbMessenger.setText(" ");

			return tax;
		} catch (Exception e) {

			if (tax.getCode() == null | tax.getCode().isEmpty() | tax.getName() == null | tax.getName().isEmpty()
					| tax.getPercent() == 0d) {

				if (tax.getCode().isEmpty()) {
					lbCode.setForeground(Color.red);
				} else {
					lbCode.setForeground(Color.black);
				}
				if (tax.getName().isEmpty()) {
					lbName.setForeground(Color.red);
				} else {
					lbName.setForeground(Color.black);
				}
				if (tax.getPercent() == 0d) {
					lbPercent.setForeground(Color.red);
				} else {
					lbPercent.setForeground(Color.black);
				}

				lbMessenger.setText("Dữ liệu vùng đỏ bị lỗi");
				lbMessenger.setForeground(Color.red);
				lbMessenger.setVisible(true);
				return new Tax();
			}

			lbMessenger.setText("Lỗi định dạng dữ liệu");
			lbMessenger.setForeground(Color.red);
			lbMessenger.setVisible(true);
			if (lbMessenger.getText().equals("Lỗi định dạng dữ liệu")) {
				lbPercent.setForeground(Color.red);
			}
			return new Tax();
		}
	}

	private void setData() {
		txtCode.setText(tax.getCode());
		txtName.setText(tax.getName());
		txtPercent.setText(MyDouble.valueOf(tax.getPercent()).toString());
		txtDescription.setText(tax.getDescription());
	}

	private boolean checkData() {
		boolean check = true;
		/** Kiểm tra trường mã khác trống hoặc khác null */
		if (txtCode.getText() == null || txtCode.getText().isEmpty()) {
			lbCode.setForeground(Color.red);
			check = false;
		} else {
			lbCode.setForeground(Color.black);
		}

		/** Kiểm tra trường tên khác trống hoặc khác null */
		if (txtName.getText() == null || txtName.getText().isEmpty()) {
			lbName.setForeground(Color.red);
			check = false;
		} else {
			lbName.setForeground(Color.black);
		}

		/** Kiểm tra trường % khác trống + khác null + khác định dạng chữ */
		if (txtPercent.getText() == null || txtPercent.getText().isEmpty()) {
			lbPercent.setForeground(Color.red);
			check = false;
		} else {
			// Kiểm tra định dạng chữ
			try {
				MyDouble.parseDouble(txtPercent.getText());
				lbPercent.setForeground(Color.black);
			} catch (Exception ex) {
				lbPercent.setForeground(Color.red);
				check = false;
			}
		}
		// Kiểm tra trường mã đã tồn tại chưa?
		if (txtCode.isEnabled()) {
			try {
				Tax t = ProductModelManager.getInstance().getTaxByCode(
						txtCode.getText());
				if (t != null) {
					check = false;
					lbCode.setForeground(Color.red);
					if (t.isRecycleBin()) {
						PanelRecybin panel = new PanelRecybin(
								"Mã đã bị xóa trước đó!",
								" Chọn ĐỒNG Ý để lấy lại thông tin ban đầu hoặc THOÁT và nhập mã khác!");
						panel.setName("Xoa");
						DialogResto dialog = new DialogResto(panel, false, 0,
								120);
						dialog.setName("dlXoa");
						dialog.setTitle("Thuế hàng hóa");
						dialog.setLocationRelativeTo(null);
						dialog.setModal(true);
						dialog.setVisible(true);
						if (panel.isDelete()) {
							restore = false;
							t.setRecycleBin(false);
							ProductModelManager.getInstance().saveTax(t);
							lbCode.setForeground(Color.black);
							reset();
							return true;
						}
					}
				}
			} catch (Exception e) {
			}

		}

		/** Thông báo kết quả kiểm tra ra màn hình nếu có lỗi */
		if (!check) {
			lbMessenger.setText("Dữ liệu vùng đỏ bị lỗi");
			lbMessenger.setForeground(Color.red);
			lbMessenger.setVisible(true);
			return false;
		} else {
			lbMessenger.setText(" ");
			return true;
		}
	}

	@Override
	public void save() throws Exception {
		if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.READ) {
			JOptionPane
					.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
		} else {
//			// Kiểm tra dữ liệu sau trước khi đổ dữ liệu vào đối tượng
//			if (checkData()) {
//				if (restore){
//					getData();
//				if (tax != null) {
//					ProductModelManager.getInstance().saveTax(tax);
//					Flag = true;
//				}
//				reset();
//				}
//			}
			
			// Lấy dữ liệu đổ vào đối tượng
		      tax = getData();
		      // Kiểm tra các trường dữ liệu sau khi đổ dữ liệu vào
		      if (!checkData()) {
		        return;
		      }
		      if(restore){
		    	  if (tax != null) {
		    		  ProductModelManager.getInstance().saveTax(tax);
		    	        reset();
		    	      }
		      } 
		}
	}

	@Override
	public void edit() throws Exception {
		if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.READ) {
			JOptionPane
					.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
		} else {
			setEnableCompoment(true);
			txtCode.setEnabled(false);
			Flag = false;
			restore = true;
		}
	}

	@Override
	public void delete() throws Exception {
		if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.ADMIN) {
			String str = "Xóa thuế hàng hóa ";
			PanelChoise pnPanel = new PanelChoise(str + tax + "?");
			pnPanel.setName("Xoa");
			DialogResto dialog1 = new DialogResto(pnPanel, false, 0, 80);
			dialog1.setName("dlXoa");
			dialog1.setTitle("Xóa thuế hàng hóa");
			dialog1.setLocationRelativeTo(null);
			dialog1.setModal(true);
			dialog1.setVisible(true);

			if (pnPanel.isDelete()) {
				ProductModelManager.getInstance().deleteTax(tax);
				lbMessenger.setText("");
			}
			reset();
		} else {
			JOptionPane
					.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
		}

	}

	@Override
	public void reset() {
		txtCode.setText(DateUtil.asCompactDateTimeId(new Date()));
		txtName.setText("");
		txtPercent.setText("");
		txtDescription.setText("");

		lbCode.setForeground(Color.black);
		lbName.setForeground(Color.black);
		lbPercent.setForeground(Color.black);
		lbMessenger.setText(" ");
		tax = new Tax();
		loadTable();
		setEnableCompoment(true);
	}

	@Override
	public void refresh() throws Exception {
		setData();
		setEnableCompoment(false);
		lbCode.setForeground(Color.black);
		lbName.setForeground(Color.black);
		lbPercent.setForeground(Color.black);
		lbMessenger.setText(" ");
	}

	public List<Tax> getObjects() {
		try {
			return ProductModelManager.getInstance().getTaxs();
		} catch (Exception e) {
			return new ArrayList<Tax>();
		}
	}

	public void setObject(Tax object) throws Exception {
		this.tax = object;
		if (object == null) {
			tax = new Tax();
		}
		setData();
	}

	private void loadTable() {
		taxTable.setTaxs(getObjects());
	}

	private void setEnableCompoment(boolean value) {
		txtCode.setEnabled(value);
		txtName.setEnabled(value);
		txtPercent.setEnabled(value);
		txtDescription.setEnabled(value);
	}
	
	  @Override
	  public void createDataTest() {
		  
		  for (int i = 4; i <=25; i++){
		  tax = new Tax();
		  tax.setCode("Mã HktTest" + i);
		  tax.setName("Thuế HktTest" + i);
		  tax.setPercent(4 + i);
		  tax.setDescription("Miêu tả HktTest" + i);
		  try {
			ProductModelManager.getInstance().saveTax(tax);
		} catch (Exception e) {
			e.printStackTrace();
		}
		  }
	  }

	@Override
	public void deleteDataTest() {
		if (!PanelTestAll.runAll) {
			DialogTest.getInstance().show("Xóa toàn bộ dữ liệu test ?");
			if (DialogTest.getInstance().isTest()) {
				try {
//					for( int i = )
					ProductModelManager.getInstance().deleteTest("HktTest");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
