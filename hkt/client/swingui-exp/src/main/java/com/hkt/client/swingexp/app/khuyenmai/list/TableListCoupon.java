package com.hkt.client.swingexp.app.khuyenmai.list;

import java.awt.Component;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.hkt.client.rest.ClientContext;
import com.hkt.client.rest.service.PromotionServiceClient;
import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.ITableMain;
import com.hkt.client.swingexp.app.core.ManagerAuthenticate;
import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.client.swingexp.app.core.TableObservable;
import com.hkt.client.swingexp.app.hethong.PanelChoise;
import com.hkt.client.swingexp.app.khuyenmai.PanelCoupon;
import com.hkt.client.swingexp.app.print.ReportPhieuGiamGia;
import com.hkt.client.swingexp.homescreen.HomeScreen;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.PromotionModelManager;
import com.hkt.client.swingexp.model.RestaurantModelManager;
import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.module.account.entity.AccountMembership.Capability;
import com.hkt.module.promotion.entity.Coupon;

@SuppressWarnings("serial")
public class TableListCoupon extends TableObservable implements ITableMain {
	private List<Coupon> coupons;
	private TableModelCoupon modelTable;
	private ClientContext clientContext = ClientContext.getInstance();
	private PromotionServiceClient client = clientContext.getBean(PromotionServiceClient.class);
	private String[] parameter;
	private String[] listTempletePromotion = new String[] { "Mẫu 1", "Mẫu 2", "Mẫu 3", "Mẫu 4", "Mẫu 5" };
	private int choiseTempletePromotion = 1;

	public TableListCoupon() {
		try {
			coupons = client.getCoupons();
		} catch (Exception e) {
			e.printStackTrace();
			coupons = new ArrayList<Coupon>();
		}
		modelTable = new TableModelCoupon(coupons);
		setModel(modelTable);


	}

	public JButton getBtnPrint() {
		JButton btnEdit = new JButton();
		btnEdit = new JButton("In phiếu");
		btnEdit.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnEdit.setMargin(new Insets(0, 0, 0, 0));
		btnEdit.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/print1.png")));
		btnEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					((JDialog) getRootPane().getParent()).setAlwaysOnTop(false);
					printPhieuGiamGia();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		return btnEdit;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public JComboBox getCboKM() {
		JComboBox btnEdit = new JComboBox();
		btnEdit.setModel(new DefaultComboBoxModel(listTempletePromotion));
		btnEdit.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				JComboBox cbo = (JComboBox) e.getSource();
				choiseTempletePromotion = cbo.getSelectedIndex() + 1;
			}
		});
		return btnEdit;
	}

	public JLabel getLbKM() {
		JLabel lb = new JLabel("Mẫu phiếu");
		lb.setFont(new Font("Tahoma", Font.BOLD, 14));
		lb.setHorizontalTextPosition(JLabel.CENTER);
		lb.setOpaque(false);
		return lb;
	}

	// In phiếu
	private void printPhieuGiamGia() throws Exception {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		ReportPhieuGiamGia reportPromotion = new ReportPhieuGiamGia();
		try {
			int tableSelectedRow = getSelectedRow();
			if (tableSelectedRow >= 0) {
				Coupon coupon = (Coupon) this.getValueAt(this.getSelectedRow(), 2);
				String unitMoney = "VND";
				String loginId = ManagerAuthenticate.getInstance().getOrganizationLoginId();
				String nameEnterprise = AccountModelManager.getInstance().getNameByLoginId(loginId);
				String phone = AccountModelManager.getInstance().getPhoneByLoginId(loginId);
				String address = AccountModelManager.getInstance().getAddressByLoginId(loginId);
				String website = AccountModelManager.getInstance().getWebsideByLoginId(loginId);
				String startPromotion = df.format(coupon.getFromDate());
				String finishPromotion = "";
				try {
					finishPromotion = df.format(coupon.getToDate());
				} catch (Exception e) {
					finishPromotion = " ";
				}
				try {
					reportPromotion.setLogoEnterprise(new ImageIcon());
				} catch (Exception e) {
					reportPromotion.setLogoEnterprise(null);
				}
				reportPromotion.setTypeTempletePromotion(choiseTempletePromotion);
				String moneyOrPercent = "";
				String maBarCodePromotion = String.valueOf(coupon.getCode());
				if (coupon.getDiscountRate() > 0) {
					moneyOrPercent = String.valueOf(coupon.getDiscount()) + "% Giá trị hóa đơn";
					parameter = new String[] { moneyOrPercent, nameEnterprise, phone, address, website, startPromotion, finishPromotion, maBarCodePromotion };
					reportPromotion.setParameterPersent(parameter);
					reportPromotion.printReportPromotion();
				} else {
					MyDouble b = new MyDouble(coupon.getDiscount());
					moneyOrPercent = b.toString();
					parameter = new String[] { moneyOrPercent, unitMoney, nameEnterprise, phone, address, website, startPromotion, finishPromotion, maBarCodePromotion };
					reportPromotion.setParameterTotal(parameter);
					reportPromotion.printReportPromotion();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Phương thức cho sự kiện click chuột vào dòng trong bảng
	@Override
	public void click() {
		Coupon coupon = (Coupon) this.getValueAt(this.getSelectedRow(), 2);
		try {
			PanelCoupon panel = new PanelCoupon();
			panel.setData(coupon);
			panel.setName("PhieuGiamGia");
			DialogMain dialog = new DialogMain(panel);
			dialog.setIcon("coupon1.png");
			dialog.setName("dlPhieuGiamGia");
			dialog.setTitle("Phiếu giảm giá");
			dialog.showButton(false);
			dialog.setModal(true);
			dialog.setLocationRelativeTo(null);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			coupons = client.getCoupons();
		} catch (Exception e) {
			e.printStackTrace();
			coupons = new ArrayList<Coupon>();
		}
		try {
			coupon = PromotionModelManager.getInstance().getCouponByCode(coupon.getCode());
			if (coupon == null || coupon.isRecycleBin()) {
				modelTable.removeRow(this.getSelectedRow());
			} else {
				modelTable.fireTableRowsUpdated(this.getSelectedRow(), this.getSelectedRow());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	// Lấy ra kích thước của list
	@Override
	public int getListSize() {
		return coupons.size();
	}

	@Override
	public List<Integer> getColumnSum() {
		List<Integer> list = new ArrayList<Integer>();
		list.add(7);
		list.add(8);
		return list;
	}

	// Load lại dữ liệu lên bảng
	@Override
	public DefaultTableModel loadTable(int index, int pageSize) {
		
		try {
			modelTable.setData(coupons.subList(index, pageSize), index);
		} catch (Exception ex) {
			modelTable = new TableModelCoupon(new ArrayList<Coupon>());
		}
		return modelTable;

	}

  @Override
  public boolean delete() {

		  if (UIConfigModelManager.getInstance().getPermission(
					PanelCoupon.permission) == Capability.ADMIN) {
				try {
					String str = "Xóa tất cả danh sách đã chọn ";
					PanelChoise panel = new PanelChoise(str + " ?");
					panel.setName("Xoa");
					DialogResto dialog = new DialogResto(panel, false, 0, 80);
					dialog.setName("dlXoa");
					dialog.setTitle("Xóa phiếu giảm giá");
					dialog.setLocationRelativeTo(null);
					dialog.setModal(true);
					dialog.setVisible(true);
					if (panel.isDelete()) {
						for (int i = 0; i < coupons.size();) {
							Coupon c = coupons.get(i);
							
							if (c.isRecycleBin()) {
								c.setRecycleBin(true);
								coupons.remove(i);
								PromotionModelManager.getInstance().deleteCoupon(c);
								
							} else {
								i++;
							}
						}
					}

				} catch (Exception ex) {
					ex.printStackTrace();
				}

				change();

			} else {
				JOptionPane
						.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
				return false;
			}
    return false;
  }
  @Override
  public boolean isDelete() {
    return true;
  }
}
