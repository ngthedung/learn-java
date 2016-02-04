package com.hkt.client.swingexp.app.banhang.screen.often;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.hkt.client.swingexp.app.component.ExtendJLabel;
import com.hkt.client.swingexp.app.component.ExtendJTextField;
import com.hkt.client.swingexp.app.component.MyPanel;
import com.hkt.client.swingexp.app.core.AreaJComboBox;
import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.DialogTest;
import com.hkt.client.swingexp.app.core.IDialogMain;
import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.client.swingexp.app.core.MyGroupLayout;
import com.hkt.client.swingexp.app.core.TableJComboBox;
import com.hkt.client.swingexp.app.hethong.PanelChoise;
import com.hkt.client.swingexp.app.hethong.PanelTestAll;
import com.hkt.client.swingexp.model.ProductModelManager;
import com.hkt.module.accounting.entity.InvoiceTransaction;
import com.hkt.module.product.entity.ProductPrice;
import com.hkt.module.product.entity.ProductPriceType;
import com.hkt.module.product.entity.Tax;
import com.hkt.module.restaurant.entity.Location;
import com.hkt.module.restaurant.entity.Table;
import com.hkt.util.text.DateUtil;


public class PanelTimeKara extends MyPanel implements IDialogMain {
	private ExtendJLabel lbLocation, lbTable, lbPriceType, lbPrice, lbStart, lbEnd;
	private ExtendJTextField txtPrice, txtStartH, txtEndH;
	private AreaJComboBox cboLocation;
	private TableJComboBox cboTable;
	private JComboBox<ProductPriceType> cboType;
	private JScrollPane scrollPaneTable;
	public static String permission;
	private JTable taxTable;
	private ProductPrice productPrice;
	@SuppressWarnings("unused")
	private boolean Flag;

	public PanelTimeKara() {
		init();
		reset();
	}

	private void init() {
		scrollPaneTable = new JScrollPane();
		scrollPaneTable.setOpaque(false);
		scrollPaneTable.getViewport().setOpaque(false);
		scrollPaneTable.setBorder(null);

		taxTable = new JTable();
		taxTable.setName("tbtaxTable");
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
		
		lbStart = new ExtendJLabel("Bắt đầu");
		lbEnd = new ExtendJLabel("Kết thúc");
		txtStartH = new ExtendJTextField();
		txtStartH.setToolTipText("Nhập theo chuẩn hh:mm VD 00:01");

		lbLocation = new ExtendJLabel("Khu vực");
		lbTable = new ExtendJLabel("Bàn");
		lbPriceType = new ExtendJLabel("Loại BG");
		lbPrice = new ExtendJLabel("Đơn giá");

		txtEndH = new ExtendJTextField();
		txtEndH.setToolTipText("Nhập theo chuẩn hh:mm VD 23:59");
		txtPrice = new ExtendJTextField();

		cboLocation = new AreaJComboBox();
		cboTable = new TableJComboBox();
		cboType = new JComboBox<ProductPriceType>();

		MyGroupLayout layout = new MyGroupLayout(this);
		layout.add(0, 0, lbLocation);
		layout.add(0, 1, cboLocation);
		layout.add(0, 2, lbTable);
		layout.add(0, 3, cboTable);

		layout.add(1, 0, lbPriceType);
		layout.add(1, 1, cboType);
		layout.add(1, 2, lbPrice);
		layout.add(1, 3, txtPrice);
		
		layout.add(2, 0, lbStart);
		layout.add(2, 1, txtStartH);
		layout.add(2, 2, lbEnd);
		layout.add(2, 3, txtEndH);


		// Giao diện Button cập nhật index đi cùng với scrollPaneTable
		layout.add(3, 0, scrollPaneTable);
		layout.updateGui();
		cboType.setModel(new DefaultComboBoxModel(ProductModelManager.getInstance().getAllProductPriceType().toArray()));
	
	}

	protected void tableOptionsMouseClicked(MouseEvent event) throws Exception {
		productPrice = (ProductPrice)taxTable.getValueAt(taxTable.getSelectedRow(), 0);
		int click = 2;
		if (event.getClickCount() >= click) {
			refresh();
			setEnableCompoment(false);
			((DialogMain) getRootPane().getParent()).showButton(false);
		}
		refresh();
	}

	
	@Override
	public void save() throws Exception {
		try {
			if(txtStartH.getText().trim().length()!=5
					|| txtEndH.getText().trim().length()!=5){
				JOptionPane.showMessageDialog(null, "Nhập giờ chưa đúng chuẩn yêu cầu nhập lại");
				return;
			}
			DateFormat df = new SimpleDateFormat("hh:mm");
			df.parse(txtStartH.getText());
			df.parse(txtEndH.getText());
			if(productPrice==null){
				productPrice = new ProductPrice();
				productPrice.setCode(DateUtil.asCompactDateTimeId(new Date()));
			}
			productPrice.setProduct(ProductModelManager.getInstance().getProductKara());
			productPrice.setCreatedBy(txtStartH.getText());
			productPrice.setModifiedBy(txtEndH.getText());
			productPrice.setPrice(MyDouble.parseDouble(txtPrice.getText()));
			productPrice.setCurrencyUnit("VND");
			if(cboLocation.getSelectedItem()!=null){
				productPrice.setOrganizationLoginId(((Location)cboLocation.getSelectedItem()).getCode());
			}
			if(cboTable.getSelectedItem()!=null){
				productPrice.setDescription((((Table)cboTable.getSelectedItem()).getCode()));
			}
			productPrice.setProductPriceType((ProductPriceType)cboType.getSelectedItem());
			ProductModelManager.getInstance().saveProductPrice(productPrice);
			reset();
    } catch (Exception e) {
    	JOptionPane.showMessageDialog(null, "Nhập giờ chưa đúng chuẩn yêu cầu nhập lại");
    }
		
	}

	@Override
	public void edit() throws Exception {
			setEnableCompoment(true);
	}

	@Override
	public void delete() throws Exception {
		String str = "Xóa thuế giá ";
		PanelChoise pnPanel = new PanelChoise(str + productPrice + "?");
		pnPanel.setName("Xoa");
		DialogResto dialog1 = new DialogResto(pnPanel, false, 0, 80);
		dialog1.setName("dlXoa");
		dialog1.setTitle("Xóa giá");
		dialog1.setLocationRelativeTo(null);
		dialog1.setModal(true);
		dialog1.setVisible(true);

		if (pnPanel.isDelete()) {
			ProductModelManager.getInstance().deleteProductPrice(productPrice);
		}
		reset();

	}

	@Override
	public void reset() {
		txtEndH.setText("");
		txtPrice.setText("0");
		txtStartH.setText("");
		productPrice = null;
		loadTable();
		setEnableCompoment(true);
	}

	@Override
	public void refresh() throws Exception {
		setEnableCompoment(false);
		txtPrice.setText(new MyDouble(productPrice.getPrice()).toString());
		txtStartH.setText(productPrice.getCreatedBy());
		txtEndH.setText(productPrice.getModifiedBy());
		cboLocation.setSelectedLocation(productPrice.getOrganizationLoginId());
		for(int i = 0; i< cboTable.getItemCount(); i++){
			if(cboTable.getItemAt(i)!=null&&((Table)cboTable.getItemAt(i)).getCode().equals(productPrice.getDescription())){
				cboTable.setSelectedIndex(i);
				break;
			}
		}
		for(int i = 0; i< cboType.getItemCount(); i++){
			if(((ProductPriceType)cboType.getItemAt(i)).getType().equals(productPrice.getProductPriceType().getType())){
				cboType.setSelectedIndex(i);
				break;
			}
		}
	}



	private void loadTable() {
		List<ProductPrice> list = ProductModelManager.getInstance().getProductPriceByProductCode(
				ProductModelManager.getInstance().getProductKara().getCode());
		taxTable.setModel(new TableModelKara(list));
	}

	private void setEnableCompoment(boolean value) {
		txtEndH.setEnabled(value);
		txtPrice.setEnabled(value);
		txtStartH.setEnabled(value);
		cboLocation.setEnabled(value);
		cboTable.setEnabled(value);
		cboType.setEnabled(value);
		
	}
}
