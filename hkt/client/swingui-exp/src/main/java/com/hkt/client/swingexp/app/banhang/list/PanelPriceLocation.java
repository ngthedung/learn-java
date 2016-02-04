package com.hkt.client.swingexp.app.banhang.list;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.client.swingexp.model.ProductModelManager;
import com.hkt.client.swingexp.model.RestaurantModelManager;
import com.hkt.module.product.entity.ProductPriceType;
import com.hkt.module.restaurant.entity.Location;
import com.hkt.module.restaurant.entity.LocationAttribute;

public class PanelPriceLocation extends JPanel implements IDialogResto {

	private List<TextfieldLocation> listTextfieldLocation;
	private TextfieldLocation txtKhuVuc;
	private JComboBox cboLoaiBangGia;
	private List<Location> listLocation = new ArrayList<Location>();
	private Location listLoca = new Location();
	private List<ProductPriceType> listProductPriceType = new ArrayList<ProductPriceType>();
	private DefaultComboBoxModel listPriceType;
	private ProductPriceType ppt;

	public PanelPriceLocation() {

		setLayout(new BorderLayout());
		JScrollPane scrollPane = new JScrollPane();

		// Lấy ra danh sách khu vực
		try {
			listLocation = RestaurantModelManager.getInstance().getLocations();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Lay Danh Sach ty gia
		try {
			listProductPriceType = ProductModelManager.getInstance().getProductPriceTypes();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Tao Panel Cha
		setOpaque(false);
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());

		listTextfieldLocation = new ArrayList<TextfieldLocation>();
		for (int i = 0; i < listLocation.size(); i++) {
			if (i <= 9) {
				panel.setPreferredSize(new Dimension(450, 250));
			} else {
				panel.setPreferredSize(new Dimension(450, 600));
			}
			try {
				txtKhuVuc = new TextfieldLocation(listLocation.get(i));
				txtKhuVuc.setEnabled(false);
				txtKhuVuc.setSize(new Dimension(20, 23));
				txtKhuVuc.setPreferredSize(new Dimension(200, 23));
				txtKhuVuc.setFont(new java.awt.Font("Tahoma", 0, 16));

				cboLoaiBangGia = new JComboBox();
				txtKhuVuc.setComboBox(cboLoaiBangGia);
				cboLoaiBangGia.setPreferredSize(new Dimension(200, 23));
				cboLoaiBangGia.setFont(new java.awt.Font("Tahoma", 0, 16));
				listPriceType = new DefaultComboBoxModel(listProductPriceType.toArray());
				cboLoaiBangGia.setModel(listPriceType);

				ProductPriceType ppt = null;
				List<LocationAttribute> listLocalAttribute = listLocation.get(i).getLocationAttributes();
				for (LocationAttribute l : listLocalAttribute) {
					if (l.getName().equals("ProductPriceType")) {
						ppt = ProductModelManager.getInstance().getProductPriceTypeByType(l.getValue());
						break;
					}
				}

				cboLoaiBangGia.setSelectedItem(ppt);
				listTextfieldLocation.add(txtKhuVuc);

				// add đối tượng vào panel
				panel.setOpaque(false);
				panel.add(txtKhuVuc);
				panel.add(cboLoaiBangGia);
				 this.add(panel);
				scrollPane.add(panel);
				scrollPane.getViewport().setOpaque(false);
				scrollPane.setOpaque(false);
				scrollPane.setBorder(null);
				scrollPane.setViewportView(panel);

				add(scrollPane, BorderLayout.CENTER);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void Save() throws Exception {
		for (int i = 0; i < listTextfieldLocation.size(); i++) {
			Location local = listTextfieldLocation.get(i).getLocal();
			ProductPriceType productPriceType = ((ProductPriceType) listTextfieldLocation.get(i).getComboBox()
			    .getSelectedItem());
			List<LocationAttribute> listLocalAttribute = local.getLocationAttributes();
			boolean check = false;
			for (LocationAttribute l : listLocalAttribute) {
				if (l.getName().equals("ProductPriceType")) {
					l.setValue(productPriceType.getType());
					check = true;
					break;
				}
			}
			if (!check) {
				LocationAttribute localAtt = new LocationAttribute();
				localAtt.setValue(productPriceType.getType());
				localAtt.setName("ProductPriceType");
				listLocalAttribute.add(localAtt);
			}
			RestaurantModelManager.getInstance().saveLocation(local);
		}
		((JDialog) getRootPane().getParent()).dispose();
	}
}


@SuppressWarnings("serial")
class TextfieldLocation extends JTextField {
	private Location local;
	private JComboBox comboBox;

	public TextfieldLocation(Location _local) {
		this.local = _local;
		this.setSize(new Dimension(20, 23));
		this.setPreferredSize(new Dimension(200, 23));
		this.setFont(new java.awt.Font("Tahoma", 0, 16));

		this.setText(_local.getName());
	}

	public Location getLocal() {
		return local;
	}

	public void setLocal(Location local) {
		this.local = local;
	}

	public JComboBox getComboBox() {
		return comboBox;
	}

	public void setComboBox(JComboBox comboBox) {
		this.comboBox = comboBox;
	}
}
