package com.hkt.client.swingexp.app.hethong;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.hkt.client.swingexp.app.component.MyPanel;
import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.DialogTest;
import com.hkt.client.swingexp.app.core.IDialogMain;
import com.hkt.client.swingexp.app.core.MyGroupLayout;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.client.swingexp.model.GenericOptionModelManager;
import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.module.account.entity.AccountMembership.Capability;
import com.hkt.module.config.generic.Option;
import com.hkt.module.config.generic.OptionGroup;
import com.hkt.util.text.DateUtil;

/*
 * author: longnt
 */

@SuppressWarnings("serial")
public class PanelOption extends MyPanel implements IDialogMain {

	private JLabel lbLabel, lbCode, lbDescription, lbMessenger;
	private JTextField txtLabel, txtCode;
	private JTextArea txtDescription;
	private JScrollPane scrollPaneTable, scrollIndex;
	private OptionTable tableOptions;
	private Option option = null;
	private OptionGroup optionGroup;
	private String module, service, name;
	private int index = 0;
	public static String permission1, permission2, permission3, permission4;

	public PanelOption(String module, String service, String name) {
		this.module = module;
		this.service = service;
		this.name = name;
		init();
		setOpaque(false);
		reset();
	}

	// Hàm tạo giao diện
	private void init() {
		createBeginTest();
		lbMessenger = new JLabel("");
		scrollPaneTable = new JScrollPane();
		scrollPaneTable.setOpaque(false);
		scrollPaneTable.getViewport().setOpaque(false);
		scrollPaneTable.setBorder(null);

		tableOptions = new OptionTable();
		tableOptions.setName("tbOptions");
		try {
			tableOptions.setOptions(getObjects());
		} catch (Exception e) {

		}

		tableOptions.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				try {
					tableOptionsMouseClicked(event);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		tableOptions.setPreferredScrollableViewportSize(new Dimension(200, 290));
		scrollPaneTable.setViewportView(tableOptions);
		lbDescription = new JLabel("Miêu tả:");
		txtDescription = new JTextArea();
		txtDescription.setPreferredSize(new Dimension(250, 53));
		txtDescription.setName("txtDescription");
		lbLabel = new JLabel("Tên");
		lbCode = new JLabel("Mã");

		txtLabel = new JTextField();
		txtLabel.setName("txtLabel");

		txtCode = new JTextField();
		txtCode.setName("txtCode");

		MyGroupLayout layout = new MyGroupLayout(this);
		layout.add(0, 0, lbLabel);
		layout.add(0, 1, txtLabel);
		layout.add(0, 2, lbCode);
		layout.add(0, 3, txtCode);

		layout.add(1, 0, lbDescription);
		layout.add(1, 1, txtDescription);
		layout.addMessenger(lbMessenger);

		// Thêm button cập nhật index
		scrollIndex = new JScrollPane();
		scrollIndex.setOpaque(false);
		scrollIndex.getViewport().setOpaque(false);
		scrollIndex.setBorder(null);
		scrollIndex.setViewportView(tableOptions.getPanleButton());

		// Giao diện Button cập nhật index đi cùng với scrollPaneTable
		layout.add(2, 0, scrollIndex);
		layout.add(2, 1, scrollPaneTable);
		layout.updateGui();
	}

	// Hàm click vào 1 dòng để chỉnh sửa đối tượng
	protected void tableOptionsMouseClicked(MouseEvent event) throws Exception {
		option = tableOptions.getSelectedBean();
		int click = 2;
		if (option.getLabel().indexOf("HktTest") > 0 && event.getButton() == 3) {
			click = 1;
		} else {
			option = null;
		}
		if (event.getClickCount() >= click) {
			option = tableOptions.getSelectedBean();
			if (option != null && option.getCode()!=null) {
				setData();
				index = tableOptions.getSelectedRow();
				setEnableCompoment(false);
				((DialogMain) getRootPane().getParent()).showButton(false);
			}
		}
		//refresh();
	}

	// Hàm lưu đối tượng
	@Override
	public void save() throws Exception {
		checkPermission();
	}

	private void checkPermission() {
		// Lấy dữ liệu đổ vào đối tượng
		if (checkData()) {
				getData();

			if (option != null) {
				if (optionGroup.getOption(option.getCode()) == null) {
					optionGroup.getOptions().add(option);
				} else {
					optionGroup.getOption(option.getCode()).setLabel(option.getLabel());
					optionGroup.getOption(option.getCode()).setPriority(option.getPriority());
					optionGroup.getOption(option.getCode()).setDescription(option.getDescription());
				}
				try {
					optionGroup = GenericOptionModelManager.getInstance().saveOptionGroup(optionGroup);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			reset();
		}

	}

	// Hàm kiểm tra các trường dữ liệu
	private boolean checkData() {
		boolean check = true;

		if (txtCode.getText().equals("")) {
			lbCode.setForeground(Color.red);
			check = false;
		} else {
			lbCode.setForeground(Color.black);
		}
		if (txtLabel.getText().equals("")) {
			lbLabel.setForeground(Color.red);
			check = false;
		} else {
			lbLabel.setForeground(Color.black);
		}

		// Kiểm tra xem có bị trùng code hay không
		if (txtCode.isEnabled()) {
			try {
				Option op = GenericOptionModelManager.getInstance().getOptionGroup(module, service, name)
				    .getOption(txtCode.getText());
				if (op != null) {
					check = false;
					lbCode.setForeground(Color.red);
					if (op.isRecycleBin()) {
						PanelRecybin panel = new PanelRecybin("Mã đã bị xóa trước đó!",
						    " Chọn ĐỒNG Ý để lấy lại thông tin ban đầu hoặc THOÁT và nhập mã khác!");
						panel.setName("Xoa");
						DialogResto dialog = new DialogResto(panel, false, 0, 120);
						dialog.setName("dlXoa");
						dialog.setTitle("Xóa");
						dialog.setLocationRelativeTo(null);
						dialog.setModal(true);
						dialog.setVisible(true);
						if (panel.isDelete()) {
							op.setRecycleBin(false);
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

	// Hàm cho phép sửa giao diện (enable các giao diện đã disible trước đó)
	@Override
	public void edit() throws Exception {
		setEnableCompoment(true);
		txtCode.setEnabled(false);
	}

	// Hàm xóa đối tượng
	@Override
	public void delete() throws Exception {
		showDelete();
	}

	private void showDelete() throws Exception {
		optionGroup = GenericOptionModelManager.getInstance().getOptionGroup(module, service, name);
		String str = "Xóa ";
		PanelChoise pnPanel = new PanelChoise(str + option + "?");
		pnPanel.setName("Xoa");
		DialogResto dialog1 = new DialogResto(pnPanel, false, 0, 80);
		dialog1.setName("dlXoa");
		dialog1.setTitle("Xóa ");
		dialog1.setLocationRelativeTo(null);
		dialog1.setModal(true);
		dialog1.setVisible(true);

		if (pnPanel.isDelete()) {
			if (option.getCode().equals(AccountingModelManager.typeBanHang)
			    || option.getCode().equals(AccountingModelManager.typeDatHang)
			    || option.getCode().equals(AccountingModelManager.typeThuChi)
			    || option.getCode().equals(AccountingModelManager.typeTraHang)
			    || option.getCode().equals(AccountingModelManager.typeTraTruoc)) {
				JOptionPane.showMessageDialog(null, "Không thể xóa các loại hóa đơn này!");
				return;
			}
			GenericOptionModelManager.getInstance().deleteOption(optionGroup, index);
			lbMessenger.setText("");
			optionGroup = GenericOptionModelManager.getInstance().getOptionGroup(module, service, name);
			reset();
		} else if (pnPanel.isDelete() == false) {
			reset();
		}
		// else {
		// lbMessenger
		// .setText("Hãy chắc chắn là bạn đã chọn 1 ngôn ngữ để xóa");
		// lbMessenger.setVisible(true);
		// return;
		// }

	}

	// Hàm reset lại giao diện
	@Override
	public void reset() {
		setEnableCompoment(true);
		txtLabel.setText("");
		txtCode.setText(DateUtil.asCompactDateTimeId(new Date()));
		txtDescription.setText("");

		lbLabel.setForeground(Color.black);
		lbCode.setForeground(Color.black);
		lbDescription.setForeground(Color.black);
		lbMessenger.setText(" ");

		option = null;
		index = -1;
		loadTable();

	}

	@Override
	public void refresh() throws Exception {
		setData();
		setEnableCompoment(false);
		lbLabel.setForeground(Color.black);
		lbCode.setForeground(Color.black);
		lbMessenger.setText(" ");
	}

	private void loadTable() {
		tableOptions.setOptions(getObjects());
	}

	public OptionGroup getObjects() {
		try {
			optionGroup = GenericOptionModelManager.getInstance().getOptionGroup(module, service, name);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, optionGroup);
		}

		return optionGroup;
	}


	private Option getData() {
		try {
			if (option == null) {
				option = new Option();
				option.setPriority(tableOptions.getBeans().size() + 1);
			}
			option.setCode(txtCode.getText());
			option.setLabel(txtLabel.getText());
			option.setDescription(txtDescription.getText());
			lbMessenger.setText(" ");
			return option;
		} catch (Exception e) {
			lbMessenger.setVisible(true);
			lbMessenger.setText("Lỗi định dạng dữ liệu");
			e.printStackTrace();
			return new Option();
		}
	}

	private void setData() {
		try {
			txtCode.setText(option.getCode());
			txtLabel.setText(option.getLabel());
			txtDescription.setText(option.getDescription());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setEnableCompoment(boolean value) {
		txtLabel.setEnabled(value);
		txtCode.setEnabled(value);
		txtDescription.setEnabled(value);
	}

//	@Override
//	public void createBeginTest() {
//		if (isTest) {
//		}
//		super.createBeginTest();
//	}

	@Override
	public void createDataTest() {
		for(int i = 2; i<10; i++)
		{
			if (option == null) {
				option = new Option();
				option.setPriority(tableOptions.getBeans().size() + 1);
			}
			option.setCode("Mã HktTest" + i);
			option.setLabel("Tên HktTest" + (i+ 1));
			option.setDescription("Miêu tả HktTest" + i);
			if (option != null) {
				if (optionGroup.getOption(option.getCode()) == null) {
					optionGroup.getOptions().add(option);
				} else {
					optionGroup.getOption(option.getCode()).setLabel(option.getLabel());
					optionGroup.getOption(option.getCode()).setPriority(option.getPriority());
					optionGroup.getOption(option.getCode()).setDescription(option.getDescription());
				}
				try {
					optionGroup = GenericOptionModelManager.getInstance().saveOptionGroup(optionGroup);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}
	}

	@Override
	public void deleteDataTest() {
		if (!PanelTestAll.runAll) {
			DialogTest.getInstance().show("Xóa toàn bộ dữ liệu test ?");
			if (DialogTest.getInstance().isTest()) {
				for( int i = 1; i < 10; i++)
				{
				Option option = optionGroup.getOption("Mã HktTest" + i);
				try {
					GenericOptionModelManager.getInstance().deleteOption(optionGroup, optionGroup.getOptions().indexOf(option));
				} catch (Exception e) {
				}
				}
			}
		}
	}

}