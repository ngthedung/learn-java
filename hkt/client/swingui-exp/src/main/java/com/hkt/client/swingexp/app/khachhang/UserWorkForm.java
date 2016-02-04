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
import java.util.Date;
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
public class UserWorkForm extends JPanel {

	private UserWorkTable userWorkTable;
	private JScrollPane scrollPaneTable;
	private UserWorkPanel userWorkPanel;
	private UserWork userWork;
	private List<UserWork> userWorkList;
	private JLabel lbOrganization, lbForm, lbTo, lbPosition, lbDescription;
	private JTextField txtOrganization, txtPosition;
	private JTextArea txtDescription;
	private JPanel panelButton;
	private JButton btnAddUserWork, btnSaveUserWork, btnDeleteUserWork, btnResetUserWork;
	private MyJDateChooser txtFrom, txtTo;
	private int indexUserWork = -1;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	private boolean flagEdit = true;
	private JLabel lbMessage;

	public List<UserWork> getUserWorkList() {
		if (userWorkList == null) {
			userWorkList = new ArrayList<UserWork>();
		}
		return userWorkList;
	}

	public UserWorkForm(Account account) {
		setOpaque(false);
		setBorder(BorderFactory.createEmptyBorder(20, 5, 20, 5));
		setLayout(new BorderLayout());
		userWorkList = new ArrayList<UserWork>();

		if (account != null) {
			Profile[] profileWorks = account.getProfiles().getUserWorks();
			if (profileWorks != null) {
				for (int i = 0; i < profileWorks.length; i++) {
					Profile profile = profileWorks[i];
					userWork = new UserWork();
					userWork.setOrganization((String) profile.get(UserWork.ORGANIZATION));
					userWork.setFrom((String) profile.get(UserWork.FROM));
					userWork.setTo((String) profile.get(UserWork.TO));
					userWork.setPosition((String) profile.get(UserWork.POSITION));
					userWork.setDescription((String) profile.get(UserWork.DESCRIPTION));
					userWorkList.add(userWork);
				}
			}
		}

		userWorkTable = new UserWorkTable();
		userWorkTable.setName("userWorkTable");
		userWorkTable.setUserWorks(userWorkList);
		userWorkTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				userWorkTableMouseClicked(event);
			}
		});

		panelButton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panelButton.setOpaque(false);
		btnAddUserWork = new JButton("Thêm");
		btnAddUserWork.setPreferredSize(new Dimension(109, 35));
		btnAddUserWork.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/save1.png")));
		btnAddUserWork.addMouseListener(new MouseEventClickButtonDialog("save1.png", "save1.png", "save1.png"));
		btnAddUserWork.setName("btnAddUserWork");
		btnAddUserWork.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btAddUserWorkActionPerformed(e);
			}
		});
		btnSaveUserWork = new JButton("Lưu");
		btnSaveUserWork.setName("btnSaveUserWork");
		btnSaveUserWork.setPreferredSize(new Dimension(109, 35));
		btnSaveUserWork.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/save1.png")));
		btnSaveUserWork.addMouseListener(new MouseEventClickButtonDialog("save1.png", "save1.png", "save1.png"));
		btnSaveUserWork.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btSaveUserWorkActionPerformed(e);
			}
		});
		btnDeleteUserWork = new JButton("Xóa");
		btnDeleteUserWork.setPreferredSize(new Dimension(109, 35));
		btnDeleteUserWork.setName("btnDeleteUserWork");
		btnDeleteUserWork.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/delete1.png")));
		btnDeleteUserWork.addMouseListener(new MouseEventClickButtonDialog("delete1.png", "delete1.png", "delete1.png"));
		btnDeleteUserWork.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btDeleteUserWorkActionPerformed(e);
			}
		});
		
		btnResetUserWork = new JButton("Viết lại");
		btnResetUserWork.setPreferredSize(new Dimension(109, 35));
		btnResetUserWork.setName("btnResetUserWork");
		btnResetUserWork.setIcon(new ImageIcon(HomeScreen.class.getResource("icon/reset1.png")));
		btnResetUserWork.addMouseListener(new MouseEventClickButtonDialog("reset1.png", "reset1.png", "reset1.png"));
		btnResetUserWork.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btResetUserWorkActionPerformed(e);
			}
		});
		
		panelButton.add(btnAddUserWork);
		panelButton.add(btnSaveUserWork);
		panelButton.add(btnDeleteUserWork);
		panelButton.add(btnResetUserWork);
		
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setLayout(new BorderLayout());
		panel.add(panelButton, BorderLayout.PAGE_START);
		scrollPaneTable = new JScrollPane();
		scrollPaneTable.getViewport().setBackground(Color.white);
		scrollPaneTable.setViewportView(userWorkTable);
		panel.add(scrollPaneTable, BorderLayout.CENTER);

		userWorkPanel = new UserWorkPanel(this);
		setEnabledButton(true, false, false);

		add(userWorkPanel, BorderLayout.PAGE_START);
		add(panel, BorderLayout.CENTER);
		btnDeleteUserWork.setVisible(false);
		btnSaveUserWork.setVisible(false);
//		btnResetUserWork.setVisible(false);
	}

	protected void btnRefresh(ActionEvent e) {
		txtOrganization.setText("");
		txtPosition.setText("");
		txtFrom.setDate(new Date());
		txtTo.setDate(null);
		txtDescription.setText("");
	}

	private void loadUserWorkTable() {
		userWorkTable.setUserWorks(userWorkList);
	}

	public void saveUserWorkToTable(UserWork workSave, int indexUserWork) {

		userWork = userWorkList.get(indexUserWork);

		userWork.setOrganization(workSave.getOrganization());
		userWork.setFrom(workSave.getFrom());
		userWork.setTo(workSave.getTo());
		userWork.setPosition(workSave.getPosition());
		userWork.setDescription(workSave.getDescription());

	}

	public void cleanUserWork() {
		userWork = new UserWork();
		setUserWork(userWork);
		userWorkList = new ArrayList<UserWork>();
		loadUserWorkTable();
	}

	private class UserWorkPanel extends JPanel {

		public UserWorkPanel(UserWorkForm userWorkForm) {
			this.setPreferredSize(new Dimension(this.getWidth(), 250));
			setOpaque(false);

			lbOrganization = new ExtendJLabel("Công ty");
			lbForm = new ExtendJLabel("Từ");
			lbTo = new ExtendJLabel("Đến");
			lbPosition = new ExtendJLabel("Vị trí");
			lbDescription = new ExtendJLabel("Nội dung");

			txtOrganization = new ExtendJTextField();
			txtOrganization.setName("txtOrganization");
			txtFrom = new MyJDateChooser();
			txtTo = new MyJDateChooser();
			txtTo.setDate(null);
			txtPosition = new ExtendJTextField();
			txtPosition.setName("txtPosition");
			lbMessage = new JLabel();
			txtDescription = new ExtendJTextArea();
			txtDescription.setName("txtDescription");

			MyGroupLayout groupLayout = new MyGroupLayout(this);

			groupLayout.add(0, 0, lbOrganization);
			groupLayout.add(0, 1, txtOrganization);
			groupLayout.add(0, 2, lbPosition);
			groupLayout.add(0, 3, txtPosition);

			groupLayout.add(1, 0, lbForm);
			groupLayout.add(1, 1, txtFrom);
			groupLayout.add(1, 2, lbTo);
			groupLayout.add(1, 3, txtTo);

			groupLayout.add(2, 0, lbDescription);
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setViewportView(txtDescription);
			groupLayout.add(2, 1, scrollPane);

			groupLayout.addMessenger(lbMessage);
			groupLayout.updateGui();
		}

	}

	protected void setIndexUserWork(int index) {
		this.indexUserWork = index;
	}

	protected void setEnabledButton(boolean btnAdd, boolean btnSave, boolean btnDelete) {
		this.btnAddUserWork.setVisible(btnAdd);
		this.btnSaveUserWork.setVisible(btnSave);
		this.btnDeleteUserWork.setVisible(btnDelete);
	}

	private void addUserWorkToTable(UserWork userWork) {
		this.userWorkList.add(userWork);
	}

	public void deleteUserWorkFromTable(int indexUserWork) {
		this.userWorkList.remove(indexUserWork);
	}

	protected void userWorkTableMouseClicked(MouseEvent event) {
		if (userWorkTable.getSelectedRow() < 0) {
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
				userWork = userWorkTable.getSelectedBean();
				setUserWork(userWork);
				setIndexUserWork(userWorkList.indexOf(userWork));
				setEnabledButton(false, true, true);
			}
		}
	}

	public void setUserWorkList(List<UserWork> userWorkList) {
		this.userWorkList = userWorkList;
	}

	public void setUserWork(UserWork userWork) {
		txtOrganization.setText(userWork.getOrganization());
		try {
			txtFrom.setDate(df.parse(userWork.getFrom()));
		} catch (Exception e) {
			txtFrom.setDate(null);
		}
		try {
			txtTo.setDate(df.parse(userWork.getFrom()));
		} catch (Exception e) {
			txtTo.setDate(null);
		}
		txtPosition.setText(userWork.getPosition());
		txtDescription.setText(userWork.getDescription());
	}

	protected void btSaveUserWorkActionPerformed(ActionEvent e) {
		if (indexUserWork < 0) {
			return;
		}
		userWork = getUserWork();
		saveUserWorkToTable(userWork, indexUserWork);
		loadUserWorkTable();
		userWork = new UserWork();
		setUserWork(userWork);
		setEnabledButton(true, false, false);
	}

	protected void btDeleteUserWorkActionPerformed(ActionEvent e) {
		if (indexUserWork < 0) {
			return;
		}
		deleteUserWorkFromTable(indexUserWork);
		loadUserWorkTable();
		userWork = new UserWork();
		setUserWork(userWork);
		setEnabledButton(true, false, false);
	}
	
	protected void btResetUserWorkActionPerformed(ActionEvent e) {
		txtDescription.setText("");
		txtPosition.setText("");
		txtOrganization.setText("");
	}

	private boolean checkData() {
		if (txtOrganization.getText().trim().length() == 0) {
			lbOrganization.setForeground(Color.red);
			lbMessage.setText("Nhập tên công ty!");
			lbMessage.setForeground(Color.red);
			return false;
		} else {
			lbOrganization.setForeground(Color.black);
		}
		if (txtPosition.getText().trim().length() == 0) {
			lbPosition.setForeground(Color.red);
			lbMessage.setText("Nhập vị trí làm việc!");
			lbMessage.setForeground(Color.red);
			return false;
		} else {
			lbPosition.setForeground(Color.black);
		}
		lbMessage.setText("");
		return true;
	}

	protected void btAddUserWorkActionPerformed(ActionEvent e) {
		if (checkData()) {
			userWork = getUserWork();
			addUserWorkToTable(userWork);
			loadUserWorkTable();
			userWork = new UserWork();
			setUserWork(userWork);
			setEnabledButton(true, false, false);
		}
	}

	public UserWork getUserWork() {
		userWork = new UserWork();

		userWork.setOrganization(txtOrganization.getText());
		try {
			userWork.setFrom(df.format(txtFrom.getDate()));
		} catch (Exception e) {
			userWork.setFrom("");
		}
		try {
			userWork.setTo(df.format(txtTo.getDate()));
		} catch (Exception e) {
			userWork.setTo("");
		}
		userWork.setPosition(txtPosition.getText());
		userWork.setDescription(txtDescription.getText());
		return userWork;

	}

	public void setEdit(boolean flag) {
		txtDescription.setEditable(flag);
		txtFrom.setEnabled(flag);
		txtOrganization.setEditable(flag);
		txtPosition.setEditable(flag);
		txtTo.setEnabled(flag);
		btnAddUserWork.setVisible(flag);
		btnResetUserWork.setVisible(flag);
		btnSaveUserWork.setVisible(false);
		btnDeleteUserWork.setVisible(false);
		this.flagEdit = flag;
	}

}