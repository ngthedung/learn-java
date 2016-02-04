package com.hkt.client.swingexp.app.khachhang;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.hkt.client.swingexp.app.component.ExtendJLabel;
import com.hkt.client.swingexp.app.component.ExtendJTextArea;
import com.hkt.client.swingexp.app.component.ExtendJTextField;
import com.hkt.client.swingexp.app.core.MouseEventClickButtonDialog;
import com.hkt.client.swingexp.app.core.MyGroupLayout;
import com.hkt.client.swingexp.homescreen.HomeScreen;
import com.hkt.module.account.entity.Account;
import com.hkt.module.account.entity.Profile;


@SuppressWarnings("serial")
public class BusinessRegistrationForm extends JPanel {

	private BusinessRegistrationTable businessRegistrationTable;
	private JScrollPane scrollPaneTable;
	private BusinessRegistrationPanel businessRegistrationPanel;
	private JButton btnAddBusinessRegistration, btnSaveBusinessRegistration, btnDeleteBusinessRegistration, btnResetBusinessRegistration;
	private BusinessRegistration businessRegistration;
	private List<BusinessRegistration> businessRegistrationList;
	private JTextField txtFullNameVN, txtFullNameEN, txtRegistrationCode, txtTaxRegistrationCode, txtRepresentative, txtCharterCapital,
			txtlegalCapital, txtDomain;
	private JTextArea txtDescription;
	private boolean flag = true;
	private JPanel panelButton;

	public List<BusinessRegistration> getBusinessRegistrationList() {
		if (businessRegistrationList == null) {
			businessRegistrationList = new ArrayList<BusinessRegistration>();
		}
		return businessRegistrationList;
	}

	public void setBusinessRegistrationList(List<BusinessRegistration> businessRegistrationList) {
		this.businessRegistrationList = businessRegistrationList;
	}

	public BusinessRegistrationForm(Account account) throws Exception {
		setOpaque(false);
		setBorder(BorderFactory.createEmptyBorder(20, 5, 20, 5));
		setName("pnlDSRegistration");
		businessRegistrationList = new ArrayList<BusinessRegistration>();

		if (account != null) {
			Profile[] profileBusinesss = account.getProfiles().getBusinessRegistrations();
			if (profileBusinesss != null) {
				for (int i = 0; i < profileBusinesss.length; i++) {
					Profile profile = profileBusinesss[i];
					businessRegistration = new BusinessRegistration();
					businessRegistration.setFullNameVN((String) profile.get(BusinessRegistration.FULL_nameVN));
					businessRegistration.setFullNameEN((String) profile.get(BusinessRegistration.FULL_nameEN));
					businessRegistration.setRegistrationCode((String) profile.get(BusinessRegistration.REGISTRATION_CODE));
					businessRegistration.setTaxRegistrationCode((String) profile.get(BusinessRegistration.TAX_REGISTRATION_CODE));
					businessRegistration.setRepresentative((String) profile.get(BusinessRegistration.REPRESENTATIVE));
					businessRegistration.setCharterCapital((String) profile.get(BusinessRegistration.CHAPTER_CAPITAL));
					businessRegistration.setLegalCapital((String) profile.get(BusinessRegistration.LEGAL_CAPITAL));
					businessRegistration.setDomain((String) profile.get(BusinessRegistration.DOMAIN));
					businessRegistrationList.add(businessRegistration);
				}
			}
		}

		businessRegistrationTable = new BusinessRegistrationTable();
		businessRegistrationTable.setName("tableBusinessRegistration");
		businessRegistrationTable.setBusinessRegistrations(businessRegistrationList);
		businessRegistrationTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				businessRegistrationTableMouseClicked(event);
			}
		});

		panelButton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panelButton.setOpaque(false);
		btnAddBusinessRegistration = new JButton("Thêm");
		btnAddBusinessRegistration.setPreferredSize(new Dimension(109, 35));
		btnAddBusinessRegistration.setName("btnAddBusinessRegistration");
		btnAddBusinessRegistration.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/save1.png")));
		btnAddBusinessRegistration.addMouseListener(new MouseEventClickButtonDialog("save1.png", "save1.png", "save1.png"));
		
		btnSaveBusinessRegistration = new JButton("Lưu");
		btnSaveBusinessRegistration.setPreferredSize(new Dimension(109, 35));
		btnSaveBusinessRegistration.setName("btnSaveBusinessRegistration");
		btnSaveBusinessRegistration.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/save1.png")));
		btnSaveBusinessRegistration.addMouseListener(new MouseEventClickButtonDialog("save1.png", "save1.png", "save1.png"));
		
		btnDeleteBusinessRegistration = new JButton("Xóa");
		btnDeleteBusinessRegistration.setPreferredSize(new Dimension(109, 35));
		btnDeleteBusinessRegistration.setName("btnDeleteBusinessRegistration");
		btnDeleteBusinessRegistration.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/delete1.png")));
		btnDeleteBusinessRegistration.addMouseListener(new MouseEventClickButtonDialog("delete1.png", "delete1.png", "delete1.png"));
		
		
		btnResetBusinessRegistration = new JButton("Viết lại");
		btnResetBusinessRegistration.setPreferredSize(new Dimension(109, 35));
		btnResetBusinessRegistration.setName("btnResetBusinessRegistration");
		btnResetBusinessRegistration.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/reset1.png")));
		btnResetBusinessRegistration.addMouseListener(new MouseEventClickButtonDialog("reset1.png", "reset1.png", "reset1.png"));
		
		panelButton.add(btnAddBusinessRegistration);
		panelButton.add(btnSaveBusinessRegistration);
		panelButton.add(btnDeleteBusinessRegistration);
		panelButton.add(btnResetBusinessRegistration);
		
		scrollPaneTable = new JScrollPane();
		scrollPaneTable.getViewport().setBackground(Color.white);
		scrollPaneTable.setViewportView(businessRegistrationTable);
		
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setLayout(new BorderLayout());
		panel.add(panelButton, BorderLayout.PAGE_START);
		panel.add(scrollPaneTable, BorderLayout.CENTER);

		businessRegistrationPanel = new BusinessRegistrationPanel(this);
		businessRegistrationPanel.setEnabledButton(true, false, false);
		setLayout(new BorderLayout());
		add(businessRegistrationPanel, BorderLayout.NORTH);
		add(panel, BorderLayout.CENTER);
		btnDeleteBusinessRegistration.setVisible(false);
		btnSaveBusinessRegistration.setVisible(false);
//		btnResetBusinessRegistration.setVisible(false);
	}

	protected void btnRefresh(ActionEvent e) {
		txtCharterCapital.setText("");
		txtDescription.setText("");
		txtDomain.setText("");
		txtFullNameEN.setText("");
		txtFullNameVN.setText("");
		txtlegalCapital.setText("");
		txtRegistrationCode.setText("");
		txtRepresentative.setText("");
		txtTaxRegistrationCode.setText("");
		
		
	}

	private void addBusinessRegistrationToTable(BusinessRegistration businessRegistration) {
		this.businessRegistrationList.add(businessRegistration);
	}

	public void deleteBusinessRegistraionFromTable(int indexBusinessRegistration) {
		this.businessRegistrationList.remove(indexBusinessRegistration);
	}

	protected void businessRegistrationTableMouseClicked(MouseEvent event) {
		if (businessRegistrationTable.getSelectedRow() < 0) {
			return;
		}
		int click = 2;
		JTable ta = (JTable) event.getSource();
		String value = ta.getValueAt(ta.getSelectedRow(), ta.getSelectedColumn()).toString();
		if (value.indexOf("HktTest") >= 0 && event.getButton() == 3) {
			click = 1;
		}
		if (event.getClickCount() >= click) {
			if (flag) {
				businessRegistration = businessRegistrationTable.getSelectedBean();
				businessRegistrationPanel.setBusinessRegistration(businessRegistration);
				businessRegistrationPanel.setIndexBusinessRegistration(businessRegistrationList.indexOf(businessRegistration));
				businessRegistrationPanel.setEnabledButton(false, true, true);
			}
		}
	}

	private void loadBusinessRegistraionTable() {
		businessRegistrationTable.setBusinessRegistrations(businessRegistrationList);
	}

	public void saveBusinessRegistrationToTable(BusinessRegistration businessRegis, int index) {
		businessRegistration = businessRegistrationList.get(index);

		businessRegistration.setFullNameVN(businessRegis.getFullNameVN());
		businessRegistration.setFullNameEN(businessRegis.getFullNameEN());
		businessRegistration.setRegistrationCode(businessRegis.getRegistrationCode());
		businessRegistration.setTaxRegistrationCode(businessRegis.getTaxRegistrationCode());
		businessRegistration.setRepresentative(businessRegis.getRepresentative());
		businessRegistration.setCharterCapital(businessRegis.getCharterCapital());
		businessRegistration.setLegalCapital(businessRegis.getLegalCapital());
		businessRegistration.setDomain(businessRegis.getDomain());

	}

	public void cleanBusinessRegistration() {
		businessRegistration = new BusinessRegistration();
		businessRegistrationPanel.setBusinessRegistration(businessRegistration);
		businessRegistrationList = new ArrayList<BusinessRegistration>();
		loadBusinessRegistraionTable();
	}

	public class BusinessRegistrationPanel extends JPanel {

		/** BusinessRegistrationPanel */
		private JLabel lbFullNameVN, lbFullNameEN, lbRegistrationCode, lbTaxRegistrationCode, lbRepresentative, lbCharterCapital, lblegalCapital,
				lbDomain, lbDescription, lbMessage;

		@SuppressWarnings("unused")
		private JPanel panelButton;

		private int indexBusinessRegistration = -1;
		private BusinessRegistration businessRegistration;
		private BusinessRegistrationForm businessRegistrationForm;

		@SuppressWarnings("unused")
		private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

		public BusinessRegistrationPanel(BusinessRegistrationForm businessRegisForm) {
			 this.setPreferredSize(new Dimension(this.getWidth(), 280));

			btnAddBusinessRegistration.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					btAddUserEducationActionPerformed(e);
				}
			});

			btnSaveBusinessRegistration.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					btSaveUserEducationActionPerformed(e);
				}
			});

			btnDeleteBusinessRegistration.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					btDeleteUserEducationActionPerformed(e);
				}
			});

			btnResetBusinessRegistration.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					btResetUserEducationActionPerformed(e);
				}
			});
			
			
			MyGroupLayout groupLayout = new MyGroupLayout(this);
			setOpaque(false);
			this.businessRegistrationForm = businessRegisForm;

			lbFullNameVN = new ExtendJLabel("Tên TV");
			lbFullNameEN = new ExtendJLabel("Tên TA");
			lbRegistrationCode = new ExtendJLabel("Mã đăng kí");
			lbTaxRegistrationCode = new ExtendJLabel("Mã số thuế");
			lbRepresentative = new ExtendJLabel("Người ĐD");
			lbCharterCapital = new ExtendJLabel("Vốn điều lệ");
			lblegalCapital = new ExtendJLabel("Vốn PĐ");
			lbDomain = new ExtendJLabel("Lĩnh vực KD");

			lbDescription = new ExtendJLabel("Miêu tả:");
			lbMessage = new JLabel();

			txtFullNameVN = new ExtendJTextField();
			txtFullNameVN.setName("txtFullNameVN");
			txtFullNameEN = new ExtendJTextField();
			txtFullNameEN.setName("txtFullNameEN");
			txtRegistrationCode = new ExtendJTextField();
			txtRegistrationCode.setName("txtCodeRegis");
			txtTaxRegistrationCode = new ExtendJTextField();
			txtTaxRegistrationCode.setName("txtTaxCode");
			txtRepresentative = new ExtendJTextField();
			txtRepresentative.setName("txtPersionActive");
			txtCharterCapital = new ExtendJTextField();
			txtCharterCapital.setName("txtCharterCapital");
			txtlegalCapital = new ExtendJTextField();
			txtlegalCapital.setName("txtlegalCapital");
			txtDomain = new ExtendJTextField();
			txtDomain.setName("txtDomain");
			txtDescription = new ExtendJTextArea(5, 30);
			txtDescription.setName("txtDescription");
			txtDescription.setBorder(javax.swing.BorderFactory.createEtchedBorder());

			groupLayout.add(0, 0, lbFullNameVN);
			groupLayout.add(0, 1, txtFullNameVN);
			groupLayout.add(0, 2, lbRegistrationCode);
			groupLayout.add(0, 3, txtRegistrationCode);

			groupLayout.add(1, 0, lbFullNameEN);
			groupLayout.add(1, 1, txtFullNameEN);
			groupLayout.add(1, 2, lbTaxRegistrationCode);
			groupLayout.add(1, 3, txtTaxRegistrationCode);

			groupLayout.add(2, 0, lbRepresentative);
			groupLayout.add(2, 1, txtRepresentative);
			groupLayout.add(2, 2, lbCharterCapital);
			groupLayout.add(2, 3, txtCharterCapital);

			groupLayout.add(3, 0, lbDomain);
			groupLayout.add(3, 1, txtDomain);
			groupLayout.add(3, 2, lblegalCapital);
			groupLayout.add(3, 3, txtlegalCapital);
			groupLayout.add(4, 0, lbDescription);

			groupLayout.addMessenger(lbMessage);
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setViewportView(txtDescription);
			groupLayout.add(4, 1, scrollPane);
			
			groupLayout.updateGui();

		}

		protected void btSaveUserEducationActionPerformed(ActionEvent e) {
			if (indexBusinessRegistration < 0) {
				return;
			}
			businessRegistration = getBusinessRegistration();
			businessRegistrationForm.saveBusinessRegistrationToTable(businessRegistration, indexBusinessRegistration);
			businessRegistrationForm.loadBusinessRegistraionTable();
			businessRegistration = new BusinessRegistration();
			setBusinessRegistration(businessRegistration);
			setEnabledButton(true, false, false);
		}

		protected void btDeleteUserEducationActionPerformed(ActionEvent e) {
			if (indexBusinessRegistration < 0) {
				return;
			}
			businessRegistrationForm.deleteBusinessRegistraionFromTable(indexBusinessRegistration);
			businessRegistrationForm.loadBusinessRegistraionTable();
			businessRegistration = new BusinessRegistration();
			setBusinessRegistration(businessRegistration);
			setEnabledButton(true, false, false);
		}

		protected void btResetUserEducationActionPerformed(ActionEvent e) {
			txtCharterCapital.setText("");
			txtDescription.setText("");
			txtDomain.setText("");
			txtFullNameEN.setText("");
			txtFullNameVN.setText("");
			txtlegalCapital.setText("");
			txtRegistrationCode.setText("");
			txtTaxRegistrationCode.setText("");
			txtRepresentative.setText("");
		}
		
		protected void btAddUserEducationActionPerformed(ActionEvent e) {
			if (checkdata()) {
			
			businessRegistration = getBusinessRegistration();
			businessRegistrationForm.addBusinessRegistrationToTable(businessRegistration);
			businessRegistrationForm.loadBusinessRegistraionTable();
			businessRegistration = new BusinessRegistration();
			setBusinessRegistration(businessRegistration);

			setEnabledButton(true, false, false);

			}
		}

		public BusinessRegistration getBusinessRegistration() {
			businessRegistration = new BusinessRegistration();
			businessRegistration.setFullNameVN(txtFullNameVN.getText());
			businessRegistration.setFullNameEN(txtFullNameEN.getText());
			businessRegistration.setRegistrationCode(txtRegistrationCode.getText());
			businessRegistration.setTaxRegistrationCode(txtTaxRegistrationCode.getText());
			businessRegistration.setRepresentative(txtRepresentative.getText());
			businessRegistration.setCharterCapital(txtCharterCapital.getText());
			businessRegistration.setLegalCapital(txtlegalCapital.getText());
			businessRegistration.setDomain(txtDomain.getText());
			businessRegistration.setDescription(txtDescription.getText());
			return businessRegistration;
		}

		public void setBusinessRegistration(BusinessRegistration businessRegistration) {
			this.businessRegistration = businessRegistration;
			txtFullNameVN.setText(businessRegistration.getFullNameVN());
			txtFullNameEN.setText(businessRegistration.getFullNameEN());
			txtRegistrationCode.setText(businessRegistration.getRegistrationCode());
			txtTaxRegistrationCode.setText(businessRegistration.getTaxRegistrationCode());
			txtRepresentative.setText(businessRegistration.getRepresentative());
			txtCharterCapital.setText(businessRegistration.getCharterCapital());
			txtlegalCapital.setText(businessRegistration.getLegalCapital());
			txtDomain.setText(businessRegistration.getDomain());
			txtDescription.setText(businessRegistration.getDescription());
		}
		
		private boolean checkdata(){
			if (txtlegalCapital.getText().trim().length() != 0 || txtCharterCapital.getText().trim().length() != 0) {
				String pattern = "([\\d,\\s])*";
				Pattern p = Pattern.compile(pattern);
				Matcher m = p.matcher(txtlegalCapital.getText());
				if (!m.matches()) {
					lbMessage.setText("Sai định dạng vốn pháp định!");
					return false;
				}
			}
			
			if (txtCharterCapital.getText().trim().length() != 0) {
				String pattern = "([\\d,\\s])*";
				Pattern p = Pattern.compile(pattern);
				Matcher m = p.matcher(txtCharterCapital.getText());
				if (!m.matches()) {
					lbMessage.setText("Sai định dạng vốn điều lệ!");
					return false;
				}
			}
			lbMessage.setText("");
			return true;
			
		}

		protected void setIndexBusinessRegistration(int index) {
			this.indexBusinessRegistration = index;
		}

		protected void setEnabledButton(boolean btnAdd, boolean btnSave, boolean btnDelete) {
			btnAddBusinessRegistration.setVisible(btnAdd);
			btnSaveBusinessRegistration.setVisible(btnSave);
			btnDeleteBusinessRegistration.setVisible(btnDelete);
		}
	}

	public void setEdit(boolean flag) {
		txtCharterCapital.setEnabled(flag);
		txtDescription.setEnabled(flag);
		txtDomain.setEnabled(flag);
		txtFullNameEN.setEnabled(flag);
		txtFullNameVN.setEnabled(flag);
		txtlegalCapital.setEnabled(flag);
		txtRegistrationCode.setEnabled(flag);
		txtRepresentative.setEnabled(flag);
		txtTaxRegistrationCode.setEnabled(flag);
		btnAddBusinessRegistration.setVisible(flag);
		btnResetBusinessRegistration.setVisible(flag);
		btnSaveBusinessRegistration.setVisible(false);
		btnDeleteBusinessRegistration.setVisible(false);
		this.flag = flag;
	}
}
