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
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.hkt.client.swingexp.app.component.ExtendJComboBox;
import com.hkt.client.swingexp.app.component.ExtendJLabel;
import com.hkt.client.swingexp.app.component.ExtendJTextField;
import com.hkt.client.swingexp.app.component.MyJDateChooser;
import com.hkt.client.swingexp.app.core.MouseEventClickButtonDialog;
import com.hkt.client.swingexp.app.core.MyGroupLayout;
import com.hkt.client.swingexp.homescreen.HomeScreen;
import com.hkt.client.swingexp.model.HRModelManager;
import com.hkt.module.account.entity.Account;
import com.hkt.module.account.entity.Profile;
import com.hkt.module.config.locale.Country;
import com.hkt.module.hr.entity.Appointment;

@SuppressWarnings("serial")
public class UserRelationshipForm extends JPanel {

	private UserRelationshipTable userRelationshipTable;
	private JScrollPane scrollPaneTable;
	private List<UserRelationship> userRelationshipList;
	private UserRelationship userRelationship;
	private UserRelationshipPanel userRelationshipPanel;
	private JTextField txtRelation, txtFirstName, txtLastName, txtOccupation;
	@SuppressWarnings("rawtypes")
	private JComboBox cboGender;
	private MyJDateChooser txtBirthDay;
	private JButton btnAddUserRelationship, btnSaveUserRelationship, btnDeleteUserRelationship, btnResetUserRelationship;
	private boolean flagEdit = true;
	private JLabel lbMessage;
	private JPanel panelButton;
	private int indexUserRelationship = -1;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	private JLabel lbRelation, lbGender, lbFirstName, lbLastName, lbBirthDay, lbOccupation;
	private List<Appointment> appointments = new ArrayList<Appointment>();

	private Account account= new Account();

	public List<UserRelationship> getUserRelationshipList() {
		if (userRelationshipList == null) {
			userRelationshipList = new ArrayList<UserRelationship>();
		}
		return userRelationshipList;
	}

	public void setUserRelationshipList(List<UserRelationship> userRelationshipList) {
		this.userRelationshipList = userRelationshipList;
	}

	public UserRelationshipForm(Account account) {
		setOpaque(false);
		setBorder(BorderFactory.createEmptyBorder(20, 5, 20, 5));

		userRelationshipList = new ArrayList<UserRelationship>();

		if (account != null) {
			Profile[] profileRelations = account.getProfiles().getUserRelationships();
			if (profileRelations != null) {
				for (int i = 0; i < profileRelations.length; i++) {
					Profile profile = profileRelations[i];
					userRelationship = new UserRelationship();
					userRelationship.setAccount(account);
					userRelationship.setRelation((String) profile.get(UserRelationship.RELATION));
					userRelationship.setGender((String) profile.get(UserRelationship.GENDER));
					userRelationship.setFirstName((String) profile.get(UserRelationship.FIRST_NAME));
					userRelationship.setLastName((String) profile.get(UserRelationship.LAST_NAME));
					userRelationship.setBirthday((String) profile.get(UserRelationship.BIRTH_DAY));
					userRelationship.setOccupation((String) profile.get(UserRelationship.OCCUPATION));
					userRelationshipList.add(userRelationship);
				}
			}
		}
		userRelationshipTable = new UserRelationshipTable();
		userRelationshipTable.setName("userRelationshipTable");
		userRelationshipTable.setUserRelationships(userRelationshipList);
		userRelationshipTable.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent event) {
				userRelationshipTableMouseClicked(event);
			}
		});
		panelButton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panelButton.setOpaque(false);
		btnAddUserRelationship = new JButton("Thêm");
		btnAddUserRelationship.setPreferredSize(new Dimension(109, 35));
		btnAddUserRelationship.setName("btnAddUserRelationship");
		btnAddUserRelationship.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/save1.png")));
		btnAddUserRelationship.addMouseListener(new MouseEventClickButtonDialog("save1.png", "save1.png", "save1.png"));
		btnAddUserRelationship.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				btAddUserRelationshipActionPerformed(e);
			}
		});
		btnSaveUserRelationship = new JButton("Lưu");
		btnSaveUserRelationship.setPreferredSize(new Dimension(109, 35));
		btnSaveUserRelationship.setName("btnSaveUserRelationship");
		btnSaveUserRelationship.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/save1.png")));
		btnSaveUserRelationship.addMouseListener(new MouseEventClickButtonDialog("save1.png", "save1.png", "save1.png"));
		btnSaveUserRelationship.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				btSaveUserRelationshipActionPerformed(e);
			}
		});
		btnDeleteUserRelationship = new JButton("Xóa");
		btnDeleteUserRelationship.setPreferredSize(new Dimension(109, 35));
		btnDeleteUserRelationship.setName("btnDeleteUserRelationship");
		btnDeleteUserRelationship.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/delete1.png")));
		btnDeleteUserRelationship.addMouseListener(new MouseEventClickButtonDialog("delete1.png", "delete1.png", "delete1.png"));
		btnDeleteUserRelationship.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				btDeleteUserRelationshipActionPerformed(e);
			}
		});
		
		btnResetUserRelationship = new JButton("Viết lại");
		btnResetUserRelationship.setPreferredSize(new Dimension(109, 35));
		btnResetUserRelationship.setName("btnResetUserRelationship");
		btnResetUserRelationship.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/reset1.png")));
		btnResetUserRelationship.addMouseListener(new MouseEventClickButtonDialog("reset1.png", "reset1.png", "reset1.png"));
		btnResetUserRelationship.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				btResetUserRelationshipActionPerformed(e);
			}
		});
	

		panelButton.add(btnAddUserRelationship);
		panelButton.add(btnSaveUserRelationship);
		panelButton.add(btnDeleteUserRelationship);
		panelButton.add(btnResetUserRelationship);
		
		scrollPaneTable = new JScrollPane();
		scrollPaneTable.getViewport().setBackground(Color.white);
		scrollPaneTable.setViewportView(userRelationshipTable);

		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setLayout(new BorderLayout());
		panel.add(panelButton, BorderLayout.PAGE_START);
		panel.add(scrollPaneTable, BorderLayout.CENTER);

		userRelationshipPanel = new UserRelationshipPanel(this);
		userRelationshipPanel.setEnabledButton(true, false, false);

		setLayout(new BorderLayout());
		add(userRelationshipPanel, BorderLayout.PAGE_START);
		add(panel, BorderLayout.CENTER);
		btnDeleteUserRelationship.setVisible(false);
		btnSaveUserRelationship.setVisible(false);
//		btnResetUserRelationship.setVisible(false);
	}


	private void addUserRelationshipToTable(UserRelationship userRelationship) {
		this.userRelationshipList.add(userRelationship);
	}

	public void deleteUserRelationshipFromTable(int indexUserRelationship) {
		this.userRelationshipList.remove(indexUserRelationship);
	}

	protected void userRelationshipTableMouseClicked(MouseEvent event) {
		if (userRelationshipTable.getSelectedRow() < 0) {
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
				userRelationship = userRelationshipTable.getSelectedBean();
				userRelationshipPanel.setUserRelationship(userRelationship);
				userRelationshipPanel.setIndexUserRelationship(userRelationshipList.indexOf(userRelationship));
				userRelationshipPanel.setEnabledButton(false, true, true);
				txtRelation.setEnabled(false);
			}
		}
	}

	private void loadUserRelationshipTable() {
		userRelationshipTable.setUserRelationships(userRelationshipList);
	}

	public void saveUserRelationshipToTable(UserRelationship relationshipSave, int indexUserRelationship) {
		userRelationship = userRelationshipList.get(indexUserRelationship);

		userRelationship.setRelation(relationshipSave.getRelation());
		userRelationship.setGender(relationshipSave.getGender());
		userRelationship.setFirstName(relationshipSave.getFirstName());
		userRelationship.setLastName(relationshipSave.getLastName());
		userRelationship.setBirthday(relationshipSave.getBirthday());
		userRelationship.setOccupation(relationshipSave.getOccupation());

	}

	public void cleanUserRelationship() {
		userRelationship = new UserRelationship();
		userRelationshipPanel.setUserRelationship(userRelationship);
		userRelationshipList = new ArrayList<UserRelationship>();
		loadUserRelationshipTable();
	}

	/** Phan panel thong tin */
	private class UserRelationshipPanel extends JPanel {

		private Object[] dataGender = { "Nam", "Nữ", "Khác" };

		private UserRelationship userRelationship;
		@SuppressWarnings("unused")
		private UserRelationshipForm userRelationshipForm;

		@SuppressWarnings({ "rawtypes", "unchecked" })
		public UserRelationshipPanel(UserRelationshipForm userRelationshipForm) {
			setOpaque(false);
			this.userRelationshipForm = userRelationshipForm;

			lbRelation = new ExtendJLabel("Quan hệ");
			lbGender = new ExtendJLabel("Giới tính");
			lbFirstName = new ExtendJLabel("Tên");
			lbLastName = new ExtendJLabel("Họ");
			lbBirthDay = new ExtendJLabel("Ngày sinh");
			lbOccupation = new ExtendJLabel("Nghề");
			lbMessage = new JLabel();

			txtRelation = new ExtendJTextField(27);
			txtRelation.setName("txtRelation");
			txtFirstName = new ExtendJTextField(27);
			txtFirstName.setName("txtFirstName");
			txtLastName = new ExtendJTextField(27);
			txtLastName.setName("txtLastName");
			txtBirthDay = new MyJDateChooser();
			txtBirthDay.setDate(null);
			txtOccupation = new ExtendJTextField(27);
			txtOccupation.setName("txtOccupation");

			cboGender = new ExtendJComboBox();
			cboGender.setName("cboGenderRelationship");
			cboGender.setModel(new DefaultComboBoxModel(dataGender));
			MyGroupLayout layout = new MyGroupLayout(this);
			layout.add(0, 0, lbRelation);
			layout.add(0, 1, txtRelation);
			layout.add(0, 2, lbGender);
			layout.add(0, 3, cboGender);

			layout.add(1, 0, lbLastName);
			layout.add(1, 1, txtLastName);
			layout.add(1, 2, lbFirstName);
			layout.add(1, 3, txtFirstName);

			layout.add(2, 0, lbBirthDay);
			layout.add(2, 1, txtBirthDay);
			layout.add(2, 2, lbOccupation);
			layout.add(2, 3, txtOccupation);
			layout.addMessenger(lbMessage);
			layout.updateGui();
		}

		@SuppressWarnings("unused")
		public UserRelationship getUserRelationship() {
			userRelationship = new UserRelationship();

			userRelationship.setRelation(txtRelation.getText());
			userRelationship.setGender((String) cboGender.getSelectedItem());
			userRelationship.setFirstName(txtFirstName.getText());
			userRelationship.setLastName(txtLastName.getText());
			try {
				userRelationship.setBirthday(df.format(txtBirthDay.getDate()));
			} catch (Exception e) {
				userRelationship.setBirthday("");
			}
			userRelationship.setOccupation(txtOccupation.getText());
			return userRelationship;
		}

		public void setUserRelationship(UserRelationship userRelationship) {
			this.userRelationship = userRelationship;
			txtRelation.setText(userRelationship.getRelation());

			for (int i = 0; i < cboGender.getItemCount(); i++) {
				if (cboGender.getSelectedItem() instanceof Country && ((String) cboGender.getItemAt(i)).equals(userRelationship.getGender())) {
					cboGender.setSelectedIndex(i);
					break;
				}
			}

			txtFirstName.setText(userRelationship.getFirstName());
			txtLastName.setText(userRelationship.getLastName());

			try {
				txtBirthDay.setDate(df.parse(userRelationship.getBirthday()));
			} catch (Exception e) {
				txtBirthDay.setDate(null);
			}

			txtOccupation.setText(userRelationship.getOccupation());

		}

		protected void setIndexUserRelationship(int index) {
			indexUserRelationship = index;
		}

		protected void setEnabledButton(boolean btnAdd, boolean btnSave, boolean btnDelete) {
			btnAddUserRelationship.setVisible(btnAdd);
			btnSaveUserRelationship.setVisible(btnSave);
			btnDeleteUserRelationship.setVisible(btnDelete);
		}
	}

	public void setEdit(boolean flag) {
		txtFirstName.setEditable(flag);
		txtLastName.setEditable(flag);
		txtOccupation.setEditable(flag);
		txtRelation.setEditable(flag);
		txtBirthDay.setEnabled(flag);
		cboGender.setEnabled(flag);
		btnAddUserRelationship.setVisible(flag);
		btnResetUserRelationship.setVisible(flag);
		btnSaveUserRelationship.setVisible(false);
		btnDeleteUserRelationship.setVisible(false);
		this.flagEdit = flag;
		
	}

	protected void btAddUserRelationshipActionPerformed(ActionEvent e) {
		if (checkData()) {
			userRelationship = getUserRelationship();
			addUserRelationshipToTable(userRelationship);
			loadUserRelationshipTable();
			userRelationship = new UserRelationship();
			setUserRelationship(userRelationship);
			setEnabledButton(true, false, false);
		}
	}

	protected void btSaveUserRelationshipActionPerformed(ActionEvent e) {
		if (indexUserRelationship < 0) {
			return;
		}
		userRelationship = getUserRelationship();
		saveUserRelationshipToTable(userRelationship, indexUserRelationship);
		loadUserRelationshipTable();
		userRelationship = new UserRelationship();
		setUserRelationship(userRelationship);
		setEnabledButton(true, false, false);
	}

	protected void btDeleteUserRelationshipActionPerformed(ActionEvent e) {
		if (indexUserRelationship < 0) {
			return;
		}
		try {
			Account account = userRelationshipTable.getSelectedBean().getAccount();
			List<UserRelationship> userRelaitonships = getUserRelationshipList();
			Profile[] profileRelationships = new Profile[userRelaitonships.size()];
			if (userRelaitonships.size() > 0) {
				for (int i = 0; i < profileRelationships.length; i++) {
					if(account.getProfiles().getBasic().get(userRelaitonships.get(i).getRelation()) !=null)
					{
						Appointment appointment=HRModelManager.getInstance().getAppointmentById(Long.parseLong(account.getProfiles().getBasic().get(userRelaitonships.get(i).getRelation()).toString()));	
						appointments.add(appointment);
					}
					
				}
			}
		} catch (Exception e2) {
			// TODO: handle exception
		}

		userRelationshipTable.getSelectedBean();
		deleteUserRelationshipFromTable(indexUserRelationship);
		loadUserRelationshipTable();
		userRelationship = new UserRelationship();
		setUserRelationship(userRelationship);
		setEnabledButton(true, false, false);
	}
	protected void btResetUserRelationshipActionPerformed(ActionEvent e) {
		txtFirstName.setText("");
		txtLastName.setText("");
		txtOccupation.setText("");
		txtRelation.setText("");
	}
	
	private boolean checkData() {
		if (txtRelation.getText().trim().length() == 0) {
			lbRelation.setForeground(Color.red);
			lbMessage.setText("Nhập tên quan hệ !");
			lbMessage.setForeground(Color.red);
			return false;
		} else {
			lbRelation.setForeground(Color.black);
		}
		if (txtFirstName.getText().trim().length() == 0) {
			lbFirstName.setForeground(Color.red);
			lbMessage.setText("Nhập tên người quan hệ !");
			lbMessage.setForeground(Color.red);
			return false;
		} else {
			lbFirstName.setForeground(Color.black);
		}
		if (txtLastName.getText().trim().length() == 0) {
			lbMessage.setText("Nhập tên người quan hệ !");
			return false;
		}
		lbMessage.setText("");
		return true;
	}

	public UserRelationship getUserRelationship() {
		userRelationship = new UserRelationship();
		userRelationship.setRelation(txtRelation.getText());
		userRelationship.setGender((String) cboGender.getSelectedItem());
		userRelationship.setFirstName(txtFirstName.getText());
		userRelationship.setLastName(txtLastName.getText());
		try {
			userRelationship.setBirthday(df.format(txtBirthDay.getDate()));
		} catch (Exception e) {
			userRelationship.setBirthday("");
		}
		userRelationship.setOccupation(txtOccupation.getText());
		return userRelationship;
	}

	public void setUserRelationship(UserRelationship userRelationship) {
		this.userRelationship = userRelationship;
		txtRelation.setText(userRelationship.getRelation());

		for (int i = 0; i < cboGender.getItemCount(); i++) {
			if (cboGender.getSelectedItem() instanceof Country && ((String) cboGender.getItemAt(i)).equals(userRelationship.getGender())) {
				cboGender.setSelectedIndex(i);
				break;
			}
		}

		txtFirstName.setText(userRelationship.getFirstName());
		txtLastName.setText(userRelationship.getLastName());

		try {
			txtBirthDay.setDate(df.parse(userRelationship.getBirthday()));
		} catch (Exception e) {
			txtBirthDay.setDate(null);
		}

		txtOccupation.setText(userRelationship.getOccupation());

	}

	protected void setIndexUserRelationship(int index) {
		this.indexUserRelationship = index;
	}

	protected void setEnabledButton(boolean btnAdd, boolean btnSave, boolean btnDelete) {
		btnAddUserRelationship.setVisible(btnAdd);
		btnSaveUserRelationship.setVisible(btnSave);
		btnDeleteUserRelationship.setVisible(btnDelete);
	}
	public void setEnabletxtRelation(boolean Relation)
	{
		txtRelation.setEnabled(Relation);
	}

	public JTextField getTxtRelation() {
		return txtRelation;
	}

	public void setTxtRelation(JTextField txtRelation) {
		this.txtRelation = txtRelation;
	}

	public List<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(List<Appointment> appointments) {
		this.appointments = appointments;
	}


	
}