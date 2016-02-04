/*
 * Design by Bui Trong Hieu
 * */

package com.hkt.client.swingexp.app.banhang.screen.often;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.hkt.client.rest.DBConnection;
import com.hkt.client.swingexp.app.component.ExtendJComboBox;
import com.hkt.client.swingexp.app.component.ExtendJLabel;
import com.hkt.client.swingexp.app.component.ExtendJTextField;
import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.HKTFile;
import com.hkt.client.swingexp.app.core.IDialogMain;
import com.hkt.client.swingexp.app.core.MyGroupLayout;

public class PanelRemoteControl extends JPanel implements IDialogMain {

	private ExtendJLabel lbCode;
	private ExtendJLabel lbName;
	private ExtendJLabel lbPassword;
	private ExtendJTextField txtCode;
	private ExtendJTextField txtName;
	private ExtendJTextField txtPassword;
	private ExtendJLabel lbChoose;
	private ExtendJComboBox cbChoose;
	private String ADD = "ADD";
	private String DELETE = "DELETE";
	private Connection conn = new DBConnection().connection();
	private JScrollPane scrollPaneTable;
	private JTable tbView;

	public PanelRemoteControl() {
		this.setLayout(new BorderLayout(0, 10));
		this.setOpaque(false);
		init();
	}

	public void load() {
		DefaultTableModel model = (DefaultTableModel) tbView.getModel();
		try {
			int row = model.getRowCount();
			while (row > 0) {
				model.removeRow(row - 1);
				row--;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		String select = "select code, name, password from Account";

		try {
			Statement ps = conn.createStatement();
			ResultSet rs = ps.executeQuery(select);
			while (rs.next()) {
				String code = rs.getString("code");
				String name = rs.getString("name");
				String password = rs.getString("password");
				model.addRow(new Object[] { code, name, password });
			}
			rs.close();
			ps.close();
		} catch (SQLException ex) {
			// Logger.getLogger(ViewAccount.class.getName()).log(Level.SEVERE, null,
			// ex);
		}

	}

	private void init() {
		lbChoose = new ExtendJLabel("Cấu hình");
		lbCode = new ExtendJLabel("Mã");
		lbName = new ExtendJLabel("Tài khoản");
		lbPassword = new ExtendJLabel("Mật khẩu");
		txtCode = new ExtendJTextField();
		txtName = new ExtendJTextField();
		txtPassword = new ExtendJTextField();
		cbChoose = new ExtendJComboBox();
		String[] str = { "Máy chủ", "Máy trạm" };
		cbChoose.setModel(new DefaultComboBoxModel(str));
		cbChoose.setSelectedIndex(0);
		scrollPaneTable = new JScrollPane();
		tbView = new JTable();
		tbView.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {

		}, new String[] { "Mã", "Tên đăng nhập", "Mật khẩu" }

		) {
			boolean[] canEdit = new boolean[] { false, false, false };

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		scrollPaneTable.setViewportView(tbView);

		MyGroupLayout layout = new MyGroupLayout(this);
		layout.add(0, 0, lbChoose);
		layout.add(0, 1, cbChoose);
		layout.add(1, 0, lbName);
		layout.add(1, 1, txtName);
		layout.add(2, 0, lbPassword);
		layout.add(2, 1, txtPassword);
		layout.add(3, 0, lbCode);
		layout.add(3, 1, txtCode);
		layout.add(4, 0, scrollPaneTable);
		layout.updateGui();
		tbView.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tbView.setRowHeight(23);
		load();
		tbView.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				txtCode.setText(tbView.getValueAt(tbView.getSelectedRow(), 0).toString());
				txtName.setText(tbView.getValueAt(tbView.getSelectedRow(), 1).toString());
				txtPassword.setText(tbView.getValueAt(tbView.getSelectedRow(), 2).toString());
				((DialogMain) getRootPane().getParent()).showButton(false);
			}
		});
	}

	private void enabled(boolean flag) {
		txtCode.setEnabled(flag);
		txtName.setEnabled(flag);
		txtPassword.setEnabled(flag);
	}

	@Override
	public void save() throws Exception {
		if (cbChoose.getSelectedItem().toString().endsWith("Máy trạm")) {
			Connection conn = new DBConnection().connection();
			try {
				String link = "";
				String select = "select link from Account where code=? and name=? and password=?";
				PreparedStatement ps = conn.prepareStatement(select);
				ps.setString(1, txtCode.getText());
				ps.setString(2, txtName.getText());
				ps.setString(3, txtPassword.getText());
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					link = rs.getString("link");
				} else {
					JOptionPane.showMessageDialog(null, "Không có dữ liệu, bạn kiểm tra lại thông tin nhập!");
				}
				DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = builderFactory.newDocumentBuilder();
				Document doc = builder.newDocument();
				Element account = doc.createElement("Account");
				Element ip = doc.createElement("Link");
				ip.setTextContent(link);
				account.appendChild(ip);
				doc.appendChild(account);
				TransformerFactory factory = TransformerFactory.newInstance();
				Transformer trans = factory.newTransformer();
				trans.setOutputProperty("indent", "yes");
				trans.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
				trans.transform(new DOMSource(doc), new StreamResult(HKTFile.getFile("Database", "ip.xml").getAbsolutePath()));
        createXML1();
				JOptionPane.showMessageDialog(null, "Thêm thành công");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				String ip = ipConfig();
				String insert = "INSERT INTO Account (code,name,password,link) VALUES('" + txtCode.getText() + "','"
				    + txtName.getText() + "','" + txtPassword.getText() + "','" + ip + "')";
				theQuery(ADD, "Thêm thành công", insert);
				load();
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Thêm thất bại");
			}
		}

	}

	private String ipConfig() {
		String ip = "";
		try {
			URL whatismyip = new URL("http://checkip.amazonaws.com");
			BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
			ip = in.readLine(); // you get the IP as a String
		} catch (Exception e) {
		}
		return ip;
	}

	@Override
	public void edit() throws Exception {

	}

	@Override
	public void delete() throws Exception {
		try {
			String delete = "DELETE FROM Account WHERE code = '" + txtCode.getText() + "'";
			theQuery(DELETE, "Xóa thành công!", delete);
			load();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Xóa thất bại!");
		}

	}

	@Override
	public void reset() throws Exception {
		txtCode.setText("");
		txtName.setText("");
		txtPassword.setText("");

	}

	@Override
	public void refresh() throws Exception {
		// TODO Auto-generated method stub

	}

	private void theQuery(String xet, String message, String query) {
		try {
			String select = "select code from Account where code=?";
			PreparedStatement ps = conn.prepareStatement(select);
			ps.setString(1, txtCode.getText());
			ResultSet rs = ps.executeQuery();
			Statement stmt = (Statement) conn.createStatement();

			if (xet == ADD) {
				if (rs.next()) {
					JOptionPane.showMessageDialog(null, "Đã có dữ liêu về đối  tượng này, không thể thêm được");
				} else {
					int selectedOption = JOptionPane.showConfirmDialog(null, "Chắc chắn thêm?", "Choose",
					    JOptionPane.YES_NO_OPTION);
					if (selectedOption == JOptionPane.YES_OPTION) {
						stmt.executeUpdate(query);
						JOptionPane.showMessageDialog(null, message);
						createXML();
					}
					reset();
				}
			}
			if (xet == DELETE) {
				if (!rs.next()) {
					JOptionPane.showMessageDialog(null, "Không có dữ liệu để xóa, bạn kiểm tra lại đoạn đoạn mã!");

				} else {
					int selectedOption = JOptionPane.showConfirmDialog(null, "Chắc chắn xóa?", "Choose",
					    JOptionPane.YES_NO_OPTION);
					if (selectedOption == JOptionPane.YES_OPTION) {
						stmt.executeUpdate(query);
						JOptionPane.showMessageDialog(null, message);
					}
					reset();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void createXML() {
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document doc1 = builder.newDocument();
			Document doc2 = builder.newDocument();
			Element account1 = doc1.createElement("Account");
			Element account2 = doc2.createElement("Account");
			Element code = doc1.createElement("Code");
			Element link = doc2.createElement("Link");
			code.setTextContent(txtCode.getText());
			account1.appendChild(code);
			doc1.appendChild(account1);
			link.setTextContent(ipConfig());
			account2.appendChild(link);
			doc2.appendChild(account2);
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer trans = factory.newTransformer();
			trans.setOutputProperty("indent", "yes");
			trans.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			trans.transform(new DOMSource(doc1), new StreamResult(HKTFile.getFile("Database", "code.xml").getAbsolutePath()));
			trans.transform(new DOMSource(doc2), new StreamResult(HKTFile.getFile("Database", "link.xml").getAbsolutePath()));
		} catch (Exception e) {
			// Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, e);
		}
	}
	private void createXML1() {
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document doc1 = builder.newDocument();
			Document doc2 = builder.newDocument();
			Element account1 = doc1.createElement("Account");
			Element account2 = doc2.createElement("Account");
			Element code = doc1.createElement("Code");
			Element link = doc2.createElement("Link");
			code.setTextContent(txtCode.getText());
			account1.appendChild(code);
			doc1.appendChild(account1);
			link.setTextContent(ipConfig());
			account2.appendChild(link);
			doc2.appendChild(account2);
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer trans = factory.newTransformer();
			trans.setOutputProperty("indent", "yes");
			trans.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			trans.transform(new DOMSource(doc1), new StreamResult(HKTFile.getFile("Database", "code.xml").getAbsolutePath()));
		} catch (Exception e) {
			// Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, e);
		}
	}

}
