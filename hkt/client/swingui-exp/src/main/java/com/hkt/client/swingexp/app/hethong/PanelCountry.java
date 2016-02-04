package com.hkt.client.swingexp.app.hethong;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.hkt.client.swingexp.app.component.ImageTool;
import com.hkt.client.swingexp.app.component.MyPanel;
import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.DialogTest;
import com.hkt.client.swingexp.app.core.IDialogMain;
import com.hkt.client.swingexp.app.core.MyGroupLayout;
import com.hkt.client.swingexp.model.LocaleModelManager;
import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.module.account.entity.AccountMembership.Capability;
import com.hkt.module.config.locale.Country;
import com.hkt.module.config.locale.CountryImage;
import com.hkt.util.text.DateUtil;

@SuppressWarnings("serial")
public class PanelCountry extends MyPanel implements IDialogMain {

	private JLabel lbCode, lbName, lbFlag, lbDescription, lbQuocKy,
			lbMessenger;
	private JTextField txtCode, txtName, txtFlag, txtDescription;
	private JScrollPane scrollPaneTable, scrollIndex;
	private CountriesTable countryTable;
	private Country country = new Country();
	@SuppressWarnings("unused")
	private int index = 0;
	private CountryImage countryImage;
	public static String permisson;
	private boolean restore = true;

	public PanelCountry() throws Exception {
		init();
		setOpaque(false);
		reset();
	}

	private void init() throws Exception {

		lbMessenger = new JLabel("");
		scrollPaneTable = new JScrollPane();
		scrollPaneTable.setOpaque(false);
		scrollPaneTable.getViewport().setOpaque(false);
		scrollPaneTable.setBorder(null);

		countryTable = new CountriesTable();
		countryTable.setName("tbcountryTable");

		countryTable.setCountries(getObjects());
		countryTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				try {
					tableOptionsMouseClicked(event);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		countryTable
				.setPreferredScrollableViewportSize(new Dimension(200, 290));
		scrollPaneTable.setViewportView(countryTable);

		lbName = new JLabel("Tên");
		lbCode = new JLabel("Mã");
		lbFlag = new JLabel("Quốc tịch");
		lbDescription = new JLabel("Miêu tả");

		txtCode = new JTextField();
		txtCode.setName("txtCode");
		txtCode.setDisabledTextColor(Color.black);

		txtName = new JTextField();
		txtName.setName("txtName");
		txtName.setDisabledTextColor(Color.black);

		txtFlag = new JTextField();
		txtFlag.setName("txtFlag");
		txtFlag.setDisabledTextColor(Color.black);

		txtDescription = new JTextField();
		txtDescription.setName("txtDescription");
		txtDescription.setDisabledTextColor(Color.black);

		MyGroupLayout layout = new MyGroupLayout(this);

		JPanel panelQuocKy = new JPanel();
		panelQuocKy.setOpaque(false);
		panelQuocKy.setLayout(new GridLayout());
		lbQuocKy = new JLabel("Chọn quốc kỳ", SwingConstants.CENTER);
		lbQuocKy.setBorder(BorderFactory.createEtchedBorder());
		panelQuocKy.add(lbQuocKy);
		lbQuocKy.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (txtName.isEnabled())
					if (e.getClickCount() >= 2) {
						ImageIcon image = ImageTool.getInstance().getImage();
						if (image != null) {
							lbQuocKy.setText("");
							image = ImageTool.getInstance().resize(image,
									new Dimension(150, 100));
							lbQuocKy.setIcon(image);
						} else {
							lbQuocKy.setIcon(null);
							lbQuocKy.setText("Chọn quốc kỳ");
						}
					}
			}
		});

		layout.add(0, 0, lbCode);
		layout.add(0, 1, txtCode);
		layout.add(1, 0, lbName);
		layout.add(1, 1, txtName);
		layout.add(2, 0, lbFlag);
		layout.add(2, 1, txtFlag);
		layout.addImage(lbQuocKy);
		layout.add(3, 0, lbDescription);
		layout.add(3, 1, txtDescription);
		layout.addMessenger(lbMessenger);

		// Thêm button cập nhật index
		scrollIndex = new JScrollPane();
		scrollIndex.setOpaque(false);
		scrollIndex.getViewport().setOpaque(false);
		scrollIndex.setBorder(null);
		scrollIndex.setViewportView(countryTable.getPanleButton());

		// Giao diện Button cập nhật index đi cùng với scrollPaneTable
		layout.add(4, 0, scrollIndex);
		layout.add(4, 1, scrollPaneTable);
		layout.updateGui();

	}

	protected void tableOptionsMouseClicked(MouseEvent event) throws Exception {
		country = countryTable.getSelectedBean();

		int click = 2;
		if (country.getCode().indexOf("HktTest") > 0 && event.getButton() == 3) {
			click = 1;
		}
		if (event.getClickCount() >= click) {
			setData();
			index = countryTable.getSelectedRow();
			setEnableCompoment(false);
			((DialogMain) getRootPane().getParent()).showButton(false);
		}
		refresh();
	}

	public List<Country> getObjects() throws Exception {
		return LocaleModelManager.getInstance().getCountries();
	}

	private Country getData() {
		restore = true;
		try {
			if (country == null) {
				country = new Country();
				country.setIndex((getObjects().size() + 1));
			} else {
				if (!countryTable.isEdit()) {
					country.setIndex(countryTable.getBeans().indexOf(country) + 1);
				}
			}

			if (countryImage == null) {
				countryImage = new CountryImage();
			}

			ImageIcon image = (ImageIcon) lbQuocKy.getIcon();
			if (image != null) {
				String urlImage = ImageTool.getInstance().encodeToString(
						image.getImage());
				countryImage.put("image", urlImage);
				country.setCountryImage(countryImage);
			} else {
				countryImage.put("image", null);
				country.setCountryImage(countryImage);
			}
			country.setCode(txtCode.getText());
			country.setName(txtName.getText());
			country.setFlag(txtFlag.getText());
			country.setDescription(txtDescription.getText());
			lbMessenger.setText(" ");
			return country;
		} catch (Exception e) {
			lbMessenger.setVisible(true);
			lbMessenger.setText("Lỗi định dạng dữ liệu");
			lbMessenger.setForeground(Color.red);

			e.printStackTrace();
			return new Country();
		}

	}

	private void setData() {
		try {
			txtCode.setText(country.getCode());
			txtName.setText(country.getName());
			txtFlag.setText(country.getFlag());
			txtDescription.setText(country.getDescription());
		} catch (Exception e) {
			e.printStackTrace();
		}

		countryImage = country.getCountryImage();
		if (countryImage != null) {

			try {
				String image = country.getCountryImage().get("image")
						.toString();
				if (image != null) {
					ImageIcon icon = ImageTool.getInstance().decodeToImage(
							image);
					lbQuocKy.setText("");
					lbQuocKy.setIcon(icon);
				} else {
					lbQuocKy.setIcon(null);
					lbQuocKy.setText("Chọn quốc kỳ");
				}
			} catch (Exception e) {
				lbQuocKy.setIcon(null);
				lbQuocKy.setText("Chọn quốc kỳ");
			}
		} else {
			lbQuocKy.setIcon(null);
			lbQuocKy.setText("Chọn quốc kỳ");
		}
	}

	private boolean checkData() {
		boolean check = true;

		if (txtCode.getText().equals("")) {
			lbCode.setForeground(Color.red);
			check = false;
		} else {
			lbCode.setForeground(Color.black);
		}

		if (txtName.getText().equals("")) {
			lbName.setForeground(Color.red);
			check = false;
		} else {
			lbName.setForeground(Color.black);
		}

		// Kiểm tra xem có bị trùng code hay không
		if (txtCode.isEnabled()) {
			try {
				Country c = LocaleModelManager.getInstance().getCountry(
						txtCode.getText());
				if (c != null) {
					check = false;
					lbCode.setForeground(Color.red);
					if (c.isRecycleBin()) {

						PanelRecybin panel = new PanelRecybin(
								"Mã đã bị xóa trước đó!",
								" Chọn ĐỒNG Ý để lấy lại thông tin ban đầu hoặc THOÁT và nhập mã khác!");
						panel.setName("Xoa");
						DialogResto dialog = new DialogResto(panel, false, 0,
								120);
						dialog.setName("dlXoa");
						dialog.setTitle("Quốc gia");
						dialog.setLocationRelativeTo(null);
						dialog.setModal(true);
						dialog.setVisible(true);
						if (panel.isDelete()) {
							restore = false;
							c.setRecycleBin(false);
							LocaleModelManager.getInstance().saveCountry(c);
							lbCode.setForeground(Color.black);
							return true;
						}
					}
				}

			} catch (Exception e) {
			}
			
		}
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

	private void setEnableCompoment(boolean value) {
		txtCode.setEnabled(value);
		txtName.setEnabled(value);
		txtFlag.setEnabled(value);
		txtDescription.setEnabled(value);
	}

	@Override
	public void save() throws Exception {
		if (UIConfigModelManager.getInstance().getPermission(permisson) == Capability.READ) {
			JOptionPane
					.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
			return;
		} else {
			if (checkData()) {
				if (restore)
					getData();
				if (country != null) {
					 LocaleModelManager.getInstance().saveCountry(country);
					 }
				reset();
			}
		}
	}

	@Override
	public void edit() throws Exception {
		if (UIConfigModelManager.getInstance().getPermission(permisson) == Capability.READ) {
			JOptionPane
					.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
			return;
		} else {
			setEnableCompoment(true);
			txtCode.setEnabled(false);
		}
	}

	@Override
	public void delete() throws Exception {
		if (UIConfigModelManager.getInstance().getPermission(permisson) == Capability.ADMIN) {

			String str = "Xóa quốc gia ";
			PanelChoise pnPanel = new PanelChoise(str + country + "?");
			pnPanel.setName("Xoa");
			DialogResto dialog1 = new DialogResto(pnPanel, false, 0, 80);
			dialog1.setName("dlXoa");
			dialog1.setTitle("Xóa quốc gia");
			dialog1.setLocationRelativeTo(null);
			dialog1.setModal(true);
			dialog1.setVisible(true);

			if (pnPanel.isDelete()) {
				LocaleModelManager.getInstance().deleteCountry(country);
				lbMessenger.setText("");
				reset();
			} else if (pnPanel.isDelete() == false) {
				reset();
			} else {
				lbMessenger
						.setText("Hãy chắc chắn là bạn đã chọn 1 quốc gia để xóa");
				lbMessenger.setVisible(true);
				return;
			}
		} else {
			JOptionPane
					.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
			return;
		}

	}

	@Override
	public void reset() throws Exception {
		setEnableCompoment(true);
		txtCode.setText(DateUtil.asCompactDateTimeId(new Date()));
		txtName.setText("");
		txtFlag.setText("");
		txtDescription.setText("");
		lbQuocKy.setIcon(null);
		lbQuocKy.setText("Chọn quốc kỳ");
		lbCode.setForeground(Color.black);
		lbName.setForeground(Color.black);
		lbFlag.setForeground(Color.black);
		lbDescription.setForeground(Color.black);
		lbMessenger.setText(" ");
		country = new Country();

		country = null;
		index = -1;
		loadTable();
	}

	@Override
	public void refresh() throws Exception {
		setData();
		setEnableCompoment(false);
		lbCode.setForeground(Color.black);
		lbName.setForeground(Color.black);
		lbFlag.setForeground(Color.black);
		lbDescription.setForeground(Color.black);
		lbMessenger.setText(" ");
	}

	private void loadTable() throws Exception {
		countryTable.setCountries(getObjects());
		countryTable.changeSelection(countryTable.getRowCount() - 1,
				countryTable.getRowCount() - 1, true, true);
	}

	@Override
	public void deleteDataTest() {
		if (!PanelTestAll.runAll) {
			DialogTest.getInstance().show("Xóa toàn bộ dữ liệu test ?");
			if (DialogTest.getInstance().isTest()) {
				try {
					LocaleModelManager.getInstance().deleteTest("HktTest");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
