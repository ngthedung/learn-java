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
import com.hkt.client.swingexp.app.component.MyJDateChooser;
import com.hkt.client.swingexp.app.core.MouseEventClickButtonDialog;
import com.hkt.client.swingexp.app.core.MyGroupLayout;
import com.hkt.client.swingexp.homescreen.HomeScreen;
import com.hkt.module.account.entity.Account;
import com.hkt.module.account.entity.Profile;

@SuppressWarnings("serial")
public class UserEducationForm extends JPanel {

	private UserEducationTable userEducationTable;
	private JScrollPane scrollPaneTable;
	private JPanel panelButton;
	private JButton btnAddUserEducation, btnSaveUserEducation, btnDeleteUserEducation,btnResetUserEducation;
	private UserEducation userEducation;
	private List<UserEducation> userEducationList;
	private JLabel lbSchoolOrInstitude, lbMajor, lbFrom, lbEduTo, lbCertificate, lbLanguage, lbDescription;
	private JTextField txtSchoolOrInstitude, txtMajor, txtCertificate, txtLanguage;
	private JTextArea txtDescription;
	private MyJDateChooser txtFrom, txtTo;
	private int indexUserEducation = -1;
	private boolean flagEdit = true;
	private JLabel lbMessage;

	private UserEducationPanel userEducationPanel;

	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	public UserEducationForm(Account account) {
		setOpaque(false);
		setBorder(BorderFactory.createEmptyBorder(20, 5, 20, 5));

		userEducationList = new ArrayList<UserEducation>();

		if (account != null) {
			Profile[] profileEdus = account.getProfiles().getUserEducations();
			if (profileEdus != null) {
				for (int i = 0; i < profileEdus.length; i++) {
					Profile profile = profileEdus[i];
					userEducation = new UserEducation();
					userEducation.setSchoolOrInstitute((String) profile.get(UserEducation.SCHOOL_INSTITUTE));
					userEducation.setFrom((String) profile.get(UserEducation.FROM));
					userEducation.setTo((String) profile.get(UserEducation.TO));
					userEducation.setMajor((String) profile.get(UserEducation.MAJOF));
					userEducation.setCertificate((String) profile.get(UserEducation.CERTIFICATE));
					userEducation.setLanguage((String) profile.get(UserEducation.LANGUAGE));
					userEducationList.add(userEducation);
				}
			}
		}

		userEducationTable = new UserEducationTable();
		userEducationTable.setSize(200, 200);
		userEducationTable.setName("userEducationTable");
		userEducationTable.setUserEducations(userEducationList);
		userEducationTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				userTableTableMouseClicked(event);
			}
		});

		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setLayout(new BorderLayout());

		panelButton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panelButton.setOpaque(false);
		btnAddUserEducation = new JButton("Thêm");
		btnAddUserEducation.setPreferredSize(new Dimension(109, 35));
		btnResetUserEducation = new JButton("Viết lại");
		btnResetUserEducation.setPreferredSize(new Dimension(109, 35));
		btnAddUserEducation.setName("btnAddUserEducation");
		btnAddUserEducation.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/save1.png")));
		btnAddUserEducation.addMouseListener(new MouseEventClickButtonDialog("save1.png", "save1.png", "save1.png"));
		btnAddUserEducation.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btAddUserEducationActionPerformed(e);
			}
		});
		btnSaveUserEducation = new JButton("Lưu");
		btnSaveUserEducation.setPreferredSize(new Dimension(109, 35));
		btnSaveUserEducation.setName("btnSaveUserEducation");
		btnSaveUserEducation.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/save1.png")));
		btnSaveUserEducation.addMouseListener(new MouseEventClickButtonDialog("save1.png", "save1.png", "save1.png"));
		btnSaveUserEducation.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btSaveUserEducationActionPerformed(e);
			}
		});
		btnDeleteUserEducation = new JButton("Xóa");
		btnDeleteUserEducation.setPreferredSize(new Dimension(109, 35));
		btnDeleteUserEducation.setName("btnDeleteUserEducation");
		btnDeleteUserEducation.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/delete1.png")));
		btnDeleteUserEducation.addMouseListener(new MouseEventClickButtonDialog("delete1.png", "delete1.png", "delete1.png"));
		btnDeleteUserEducation.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btDeleteUserEducationActionPerformed(e);
			}
		});

		btnResetUserEducation = new JButton("Viết lại");
		btnResetUserEducation.setPreferredSize(new Dimension(109, 35));
		btnResetUserEducation.setName("btnResetUserEducation");
		btnResetUserEducation.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/reset1.png")));
		btnResetUserEducation.addMouseListener(new MouseEventClickButtonDialog("reset1.png", "reset1.png", "reset1.png"));
		btnResetUserEducation.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btResetEducationActionPerformed(e);
			}
		});
		
		
		
		panelButton.add(btnAddUserEducation);
		panelButton.add(btnSaveUserEducation);
		panelButton.add(btnDeleteUserEducation);
		panelButton.add(btnResetUserEducation);

		panel.add(panelButton, BorderLayout.PAGE_START);

		scrollPaneTable = new JScrollPane();
		scrollPaneTable.getViewport().setBackground(Color.white);
		scrollPaneTable.setViewportView(userEducationTable);

		panel.add(scrollPaneTable, BorderLayout.CENTER);

		userEducationPanel = new UserEducationPanel(this);
		setEnabledButton(true, false, false);

		setLayout(new BorderLayout());
		add(userEducationPanel, BorderLayout.PAGE_START);
		add(panel, BorderLayout.CENTER);
		btnSaveUserEducation.setVisible(false);
		btnDeleteUserEducation.setVisible(false);
//		btnResetUserEducation.setVisible(false);
	}

	private void loadUserEducationTable() {
		userEducationTable.setUserEducations(userEducationList);
	}

	public void saveUserEducationToTable(UserEducation educationSave, int indexUserEducation) {
		userEducation = userEducationList.get(indexUserEducation);

		userEducation.setSchoolOrInstitute(educationSave.getSchoolOrInstitute());
		userEducation.setMajor(educationSave.getMajor());
		userEducation.setFrom(educationSave.getFrom());
		userEducation.setTo(educationSave.getTo());
		userEducation.setCertificate(educationSave.getCertificate());
		userEducation.setLanguage(educationSave.getLanguage());

	}

	public void cleanUserEducation() {
		userEducation = new UserEducation();
		setUserEducation(userEducation);
		userEducationList = new ArrayList<UserEducation>();
		loadUserEducationTable();
	}

	public class UserEducationPanel extends JPanel {
		/** EduPanel */

		public UserEducationPanel(UserEducationForm userEducationForm) {
			this.setPreferredSize(new Dimension(this.getWidth(), 250));
			MyGroupLayout groupLayout = new MyGroupLayout(this);
			setOpaque(false);
			lbSchoolOrInstitude = new ExtendJLabel("Trường");
			lbMajor = new ExtendJLabel("Ngành học");
			lbFrom = new ExtendJLabel("Từ");
			lbCertificate = new ExtendJLabel("Bằng cấp");
			lbEduTo = new ExtendJLabel("Đến");
			lbLanguage = new ExtendJLabel("Ngoại ngữ");

			lbDescription = new ExtendJLabel("Miêu tả");

			txtSchoolOrInstitude = new ExtendJTextField(27);
			txtSchoolOrInstitude.setName("txtSchoolOrInstitude");
			txtMajor = new ExtendJTextField(27);
			txtMajor.setName("txtMajor");
			txtFrom = new MyJDateChooser();
			txtTo = new MyJDateChooser();
			txtCertificate = new ExtendJTextField(27);
			txtCertificate.setName("txtCertificate");
			txtLanguage = new ExtendJTextField(27);
			txtLanguage.setName("txtLanguage");
			txtDescription = new ExtendJTextArea();
			txtDescription.setName("txtDescription");
			txtDescription.setBorder(javax.swing.BorderFactory.createEtchedBorder());
			lbMessage = new JLabel();

			groupLayout.add(0, 0, lbSchoolOrInstitude);
			groupLayout.add(0, 1, txtSchoolOrInstitude);
			groupLayout.add(0, 2, lbMajor);
			groupLayout.add(0, 3, txtMajor);
			groupLayout.add(1, 0, lbFrom);
			groupLayout.add(1, 1, txtFrom);
			groupLayout.add(1, 2, lbCertificate);
			groupLayout.add(1, 3, txtCertificate);
			groupLayout.add(2, 0, lbEduTo);
			groupLayout.add(2, 1, txtTo);
			groupLayout.add(2, 2, lbLanguage);
			groupLayout.add(2, 3, txtLanguage);
			groupLayout.add(3, 0, lbDescription);
			groupLayout.addMessenger(lbMessage);
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setViewportView(txtDescription);
			groupLayout.add(3, 1, scrollPane);
			groupLayout.updateGui();
		}

		@SuppressWarnings("unused")
		private void btSaveUserEducationActionPerformed(ActionEvent e) {
			if (indexUserEducation < 0) {
				return;
			}
			userEducation = getUserEducation();
			saveUserEducationToTable(userEducation, indexUserEducation);
			loadUserEducationTable();
			userEducation = new UserEducation();
			setUserEducation(userEducation);
			setEnabledButton(true, false, false);
		}

		@SuppressWarnings("unused")
		private void btDeleteUserEducationActionPerformed(ActionEvent e) {
			if (indexUserEducation < 0) {
				return;
			}
			deleteUserEducationFromTable(indexUserEducation);
			loadUserEducationTable();
			userEducation = new UserEducation();
			setUserEducation(userEducation);
			setEnabledButton(true, false, false);
		}

		@SuppressWarnings("unused")
		private void btAddUserEducationActionPerformed(ActionEvent e) {
			userEducation = getUserEducation();
			addUserEducationToTable(userEducation);
			loadUserEducationTable();
			userEducation = new UserEducation();
			setUserEducation(userEducation);
			setEnabledButton(true, false, false);
		}

	}

	private void setEnabledButton(boolean btnAdd, boolean btnSave, boolean btnDelete) {
		this.btnAddUserEducation.setVisible(btnAdd);
		this.btnSaveUserEducation.setVisible(btnSave);
		this.btnDeleteUserEducation.setVisible(btnDelete);	
	}

	private void btSaveUserEducationActionPerformed(ActionEvent e) {
		if (indexUserEducation < 0) {
			return;
		}
		if (checkData()) {
			userEducation = getUserEducation();
			saveUserEducationToTable(userEducation, indexUserEducation);
			loadUserEducationTable();
			userEducation = new UserEducation();
			setUserEducation(userEducation);
			setEnabledButton(true, false, false);
		}
	}

	private void btDeleteUserEducationActionPerformed(ActionEvent e) {
		if (indexUserEducation < 0) {
			return;
		}
		deleteUserEducationFromTable(indexUserEducation);
		loadUserEducationTable();
		userEducation = new UserEducation();
		setUserEducation(userEducation);
		setEnabledButton(true, false, false);
	}

	private boolean checkData() {
		if (txtSchoolOrInstitude.getText().trim().length() == 0) {
			lbSchoolOrInstitude.setForeground(Color.red);
			lbMessage.setText("Nhập tên trường !");
			lbMessage.setForeground(Color.red);
			return false;
		}
		lbSchoolOrInstitude.setForeground(Color.black);
		lbMessage.setText("");
		return true;
	}

	private void btAddUserEducationActionPerformed(ActionEvent e) {
		if (checkData()) {
			userEducation = getUserEducation();
			addUserEducationToTable(userEducation);
			loadUserEducationTable();
			userEducation = new UserEducation();
			setUserEducation(userEducation);
			setEnabledButton(true, false, false);
		}
	}

	private void btResetEducationActionPerformed(ActionEvent e) {
		txtSchoolOrInstitude.setText("");
		txtMajor.setText("");
		txtCertificate.setText("");
		txtLanguage.setText("");
		txtDescription.setText("");
	}
	
	private void addUserEducationToTable(UserEducation userEducation) {
		this.userEducationList.add(userEducation);
	}

	private void deleteUserEducationFromTable(int indexUserEducation) {
		this.userEducationList.remove(indexUserEducation);
	}

	private void userTableTableMouseClicked(MouseEvent event) {
		if (userEducationTable.getSelectedRow() < 0) {
			return;
		}
		int click = 2;
		JTable ta = (JTable) event.getSource();
		String value = ta.getValueAt(ta.getSelectedRow(), ta.getSelectedColumn()).toString();
		if (value.indexOf("HktTest") >= 0 && event.getButton() == 3) {
			click = 1;
		}
		if (event.getClickCount() >= click) {
			if (flagEdit) {
				userEducation = userEducationTable.getSelectedBean();
				setUserEducation(userEducation);
				setIndexUserEducation(userEducationList.indexOf(userEducation));
				setEnabledButton(false, true, true);
			}
		}
	}

	@SuppressWarnings("unused")
	private void setUserEducationList(List<UserEducation> userEducationList) {
		this.userEducationList = userEducationList;
	}

	public List<UserEducation> getUserEducationList() {
		if (userEducationList == null) {
			userEducationList = new ArrayList<UserEducation>();
		}
		return userEducationList;
	}

	public UserEducation getUserEducation() {
		userEducation = new UserEducation();
		userEducation.setSchoolOrInstitute(txtSchoolOrInstitude.getText());

		try {
			userEducation.setFrom(df.format(txtFrom.getDate()));
		} catch (Exception e) {
			userEducation.setFrom("");
		}

		try {
			userEducation.setTo(df.format(txtTo.getDate()));
		} catch (Exception e) {
			userEducation.setTo("");
		}

		userEducation.setMajor(txtMajor.getText());
		userEducation.setCertificate(txtCertificate.getText());
		userEducation.setLanguage(txtLanguage.getText());
		userEducation.setDescription(txtDescription.getText());
		return userEducation;
	}

	public void setUserEducation(UserEducation userEducation) {
		this.userEducation = userEducation;
		txtSchoolOrInstitude.setText(userEducation.getSchoolOrInstitute());
		txtMajor.setText(userEducation.getMajor());

		try {
			txtFrom.setDate(df.parse(userEducation.getFrom()));
		} catch (Exception e) {
			txtFrom.setDate(null);
		}

		try {
			txtTo.setDate(df.parse(userEducation.getTo()));
		} catch (Exception e) {
			txtTo.setDate(null);
		}

		txtCertificate.setText(userEducation.getCertificate());
		txtLanguage.setText(userEducation.getLanguage());
		txtDescription.setText(userEducation.getDescription());
	}

	protected void setIndexUserEducation(int index) {
		this.indexUserEducation = index;
	}

	public void setEdit(boolean flag) {
		txtCertificate.setEditable(flag);
		txtDescription.setEditable(flag);
		txtFrom.setEnabled(flag);
		txtLanguage.setEditable(flag);
		txtMajor.setEditable(flag);
		txtSchoolOrInstitude.setEditable(flag);
		txtTo.setEnabled(flag);
		btnAddUserEducation.setVisible(flag);
		btnResetUserEducation.setVisible(flag);
		btnSaveUserEducation.setVisible(false);
		btnDeleteUserEducation.setVisible(false);
		this.flagEdit = flag;
	}

}