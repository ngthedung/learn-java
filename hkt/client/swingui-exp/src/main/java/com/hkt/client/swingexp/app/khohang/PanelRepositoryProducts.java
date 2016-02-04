package com.hkt.client.swingexp.app.khohang;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.hkt.client.swingexp.app.component.ExtendJComboBox;
import com.hkt.client.swingexp.app.component.ExtendJLabel;
import com.hkt.client.swingexp.app.component.ExtendJTextField;
import com.hkt.client.swingexp.app.component.GridLabelLayoutPabel;
import com.hkt.client.swingexp.app.component.MyPanel;
import com.hkt.client.swingexp.app.core.IDialogMain;
import com.hkt.client.swingexp.app.core.MyGroupLayout;
import com.hkt.client.swingexp.model.LocaleModelManager;
import com.hkt.client.swingexp.model.ProductModelManager;
import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.module.account.entity.AccountMembership.Capability;
import com.hkt.module.config.locale.ProductUnit;
import com.hkt.module.product.entity.Product;
import com.hkt.module.product.entity.ProductAttribute;
import com.hkt.module.product.entity.ProductTag;

@SuppressWarnings("serial")
public class PanelRepositoryProducts extends MyPanel implements IDialogMain{
	
	public static String nameClass;
	private ExtendJLabel lblName, lblCode, lblBarCode, lblPicture, lblNameOther, lblUnit, lblMessager;
	private ExtendJTextField txtName, txtCode, txtBarCode, txtNameOther;
	private ExtendJComboBox cbUnit;
	private JScrollPane scrollPanel;
	private JPanel container1;
	
	private SimpleDateFormat fomatDate = new SimpleDateFormat("yyyyMMddHHmmssSS");
	private List<CheckBoxProductTag> listCheckBoxProductTag = new ArrayList<CheckBoxProductTag>();
	private List<ProductTag> listProductTagSelected = new ArrayList<ProductTag>();
	private Product product;
	private List<Component> components;
	
	/**
	 * Create the PanelRepositoryProducts
	 */
	
	//Hàm tạo 
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public PanelRepositoryProducts() {
		product = new Product();
		init();
		this.setPreferredSize(new Dimension(300, 250));
		
		txtCode.setText(fomatDate.format(new Date()));
		txtBarCode.setText(fomatDate.format(new Date()));
		
		DefaultComboBoxModel<ProductUnit> modelProductUnits = new DefaultComboBoxModel(getProductUnits().toArray());
		cbUnit.setModel(modelProductUnits);	
	}
	
	//Hàm tạo và truyền dữ liệu sang
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public PanelRepositoryProducts(Product product) {
		this.product = product;
		this.listProductTagSelected = new ArrayList<ProductTag>();
		this.listProductTagSelected.addAll(product.getProductTags());		
		init();
		
		DefaultComboBoxModel<ProductUnit> modelProductUnits = new DefaultComboBoxModel(getProductUnits().toArray());
		cbUnit.setModel(modelProductUnits);
		
		setData();
	}
	
	/*
	 * Giao diện chia làm 2 phần chính : 
	 * - Phần đầu chứa nội dung nhập textField [PAGESTART]
	 * - Phần thân chứa checkBox và thông báo [PAGECENTER]
	 */
	public void init(){
		this.setLayout(new BorderLayout(0, 0));
		this.setPreferredSize(new Dimension(300, 250));
		setOpaque(false);
			
		Panel_PAGESTART panelPAGESTART = new Panel_PAGESTART();
		Panel_PAGECENTER panel_PAGECENTER = new Panel_PAGECENTER();
		scrollPanel = new JScrollPane();
		scrollPanel.setViewportView(panel_PAGECENTER);
		scrollPanel.getViewport().setOpaque(false);
		scrollPanel.setOpaque(false);
		scrollPanel.setBorder(null);
		
		this.add(panelPAGESTART, BorderLayout.PAGE_START);
		this.add(scrollPanel, BorderLayout.CENTER);
	}
	
	//Thiết kế nội dung phần đầu
	protected class Panel_PAGESTART extends JPanel {		
		
		public Panel_PAGESTART(){
			init();			
		}
		
		public void init(){
			setOpaque(false);
			this.setLayout(new GridLayout(4, 2, 10, 5));
			lblName = new ExtendJLabel("Tên SP");
			lblCode = new ExtendJLabel("Mã SP");
			lblBarCode = new ExtendJLabel("Mã Barcode");
			lblPicture = new ExtendJLabel("Chọn ảnh");			
			lblNameOther = new ExtendJLabel("Ngoại ngữ");
			lblUnit = new ExtendJLabel("Đơn vị");
			lblMessager = new ExtendJLabel("");
			lblMessager.setForeground(Color.RED);
			
			lblName.setPreferredSize(new Dimension(95, 22));
			lblCode.setPreferredSize(new Dimension(95, 22));
			lblBarCode.setPreferredSize(new Dimension(95, 22));
			lblPicture.setPreferredSize(new Dimension(220, 22));
			lblNameOther.setPreferredSize(new Dimension(97, 22));
			lblUnit.setPreferredSize(new Dimension(97, 22));
			
			lblPicture.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			lblPicture.setHorizontalAlignment(JLabel.CENTER);
			
			txtName = new ExtendJTextField();
			txtCode = new ExtendJTextField();
			txtBarCode = new ExtendJTextField();
			txtNameOther = new ExtendJTextField();
			
			txtCode.setHorizontalAlignment(JTextField.RIGHT);
			txtBarCode.setHorizontalAlignment(JTextField.RIGHT);
			
			cbUnit = new ExtendJComboBox();
			
			MyGroupLayout layout = new MyGroupLayout(this);
			layout.add(0, 0, lblName);
			layout.add(0, 1, txtName);
			layout.add(1, 0, lblCode);
			layout.add(1, 1, txtCode);
			layout.add(2, 0, lblBarCode);
			layout.add(2, 1, txtBarCode);
			layout.add(3, 0, lblNameOther);
			layout.add(3, 1, txtNameOther);
			layout.add(3, 2, lblUnit);
			layout.add(3, 3, cbUnit);
			layout.addImage(lblPicture);
			
			layout.updateGui();	
		}
	}
	
	//Thiết kế nội dung phần thân
	protected class Panel_PAGECENTER extends JPanel{
		private ExtendJLabel label;
		private CheckBoxProductTag checkBox;
		public Panel_PAGECENTER(){			
			init();
		}
		
		public void init(){
			setOpaque(false);
			setLayout(new BorderLayout(0, 0));
			container1 = new JPanel();
			
			//Vẽ các nhóm sản phẩm lên giao diện
			MyGroupLayout layout1 = new MyGroupLayout(container1);
			for(int i = 1; i <= getProductTags().size(); i++){
				label = new ExtendJLabel("Nhóm " + i);
				checkBox = new CheckBoxProductTag(getProductTags().get(i-1));
				listCheckBoxProductTag.add(checkBox);
				checkBox.setOpaque(false);				
				
				if (i % 2 == 0) {
					layout1.add(i - 2, 2, label);
					layout1.add(i - 2, 3, checkBox);
				} else {
					layout1.add(i - 1, 0, label);
					layout1.add(i - 1, 1, checkBox);
				}
				
				if(listProductTagSelected.size() > 0){
					for(ProductTag p : listProductTagSelected){
						if(p.getId().equals(getProductTags().get(i-1).getId())){
							checkBox.setSelected(true);
						}
					}
				}
				
				checkBox.addItemListener(new ItemListener() {					
					@Override
					public void itemStateChanged(ItemEvent e) {
						CheckBoxProductTag chb = (CheckBoxProductTag)e.getSource();
						if(chb.isSelected() == true){
							listProductTagSelected.add(chb.getProductTag());
						} else {
							listProductTagSelected.remove(chb.getProductTag());
						}
					}
				});
			}
			layout1.updateGui();
			
			this.add(container1, BorderLayout.CENTER);
			this.add(lblMessager, BorderLayout.PAGE_END);
		}
	}

	//Lấy toàn bộ danh sách ProductUnit
	private List<ProductUnit> getProductUnits() {
		try {
			return LocaleModelManager.getInstance().getProductUnits();
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<ProductUnit>();
		}
	}
	
	//Lấy toàn bộ danh sách ProductTag
	private List<ProductTag> getProductTags(){
		try {
			return ProductModelManager.getInstance().getProductTags();
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<ProductTag>();
		}
	}
	
	//Lấy dữ liệu lên giao diện
	public void setData(){
		txtName.setText(product.getName());
		txtCode.setText(product.getCode());
		txtNameOther.setText(product.getNameOther());
		txtBarCode.setText(product.getCode());
		for(int i = 0; i < getProductUnits().size(); i++){
			if(getProductUnits().get(i).getCode().equals(product.getUnit())){
				cbUnit.setSelectedIndex(i);
			}
		}
		setEnabledCompoment(false);
	}
	
	//Lưu dữ liệu từ giao diện xuống đối tượng
	private void getData(){
		product.setName(txtName.getText());
		product.setCode(txtCode.getText());
		product.setCreatedTime(new Date());
		product.setNameOther(txtNameOther.getText());
		product.setProductTags(listProductTagSelected);
		product.setUnit(((ProductUnit)cbUnit.getSelectedItem()).getCode());
		product.setMaker("HKT");
		product.setProductTags(listProductTagSelected);

		boolean checkBarCode = false;
		if (product.getProductAttributes() != null) {
			for (ProductAttribute pa : product.getProductAttributes()) {
				if (pa.getName().equals("BarCode")) {
					pa.setValue(txtBarCode.getText());
					checkBarCode = true;
				}
			}
			if (!checkBarCode) {
				ProductAttribute pa = new ProductAttribute();
				pa.setName("BarCode");
				pa.setValue(txtBarCode.getText());
				product.add(pa);
			}
		}
		txtCode.setEditable(false);
		txtBarCode.setEditable(false);
	}
	
	//Kiểm tra dữ liệu trước khi lưu
	private boolean checkData(){
		//Biến đếm số lỗi phát sinh -> tăng lên khi gặp lỗi
		int countError = 0;
		boolean codeError = false;
		//Kiểm tra ô tên hàng hóa khác trống
		if (!txtName.getText().equals("") && txtName.getText() != null) {			
			lblName.setForeground(new Color(51, 51, 51));			
		} else {
			lblName.setForeground(Color.RED);
			countError++;
		}
		//Kiểm tra ô mã hàng hóa khác trống và trùng mã
		if(!txtCode.getText().equals("") && txtCode.getText() != null){
			lblCode.setForeground(new Color(51, 51, 51));	
			try {
				Product pr = ProductModelManager.getInstance().getProductByCode(txtCode.getText());
				if(pr != null){
					lblCode.setForeground(Color.RED);
					codeError = true;
					countError++;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			lblCode.setForeground(Color.RED);
			countError++;
		}
		//Kiểm tra ô mã barCode khác trống
		if(!txtBarCode.getText().equals("") && txtBarCode.getText() != null){
			lblBarCode.setForeground(new Color(51, 51, 51));
		} else {
			lblBarCode.setForeground(Color.RED);
			countError++;
		}
		//Kết thúc kiểm tra hiện thông báo nếu có lỗi
		if(countError > 0){
			if(codeError)
				lblMessager.setText("Kiểm tra mã bị trùng - lỗi báo đỏ");
			else 
				lblMessager.setText("Vui lòng kiểm tra lỗi báo đỏ");
			return false;
		} else {
			lblMessager.setText("");
			return true;
		}
	}
	
	//Cho chỉnh sửa ô nhập hay không?
	private void setEnabledCompoment(boolean value){
		txtName.setEditable(value);
		txtNameOther.setEditable(value);
		cbUnit.setEnabled(value);
	}
	
	// Phương thức lấy ra toàn bộ component có trong Container
	private List<Component> getAllComponents(final Container c) {
		Component[] comps = c.getComponents();
		List<Component> compList = new ArrayList<Component>();
		for (Component comp : comps) {
			compList.add(comp);
			if (comp instanceof Container) {
				compList.addAll(getAllComponents((Container) comp));
			}
		}
		return compList;
	}

	//Phương thức lưu cho nút [Lưu]
	@Override
	public void save() throws Exception {
		if(UIConfigModelManager.getInstance().getPermission(nameClass)==Capability.READ){
			JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này!");
		}
		if(checkData()){
			getData();
			ProductModelManager.getInstance().saveProduct(product);
			reset();
		} 
	}

	//Phương thức chỉnh sửa cho nút [Sửa]
	@Override
	public void edit() throws Exception {
		if(UIConfigModelManager.getInstance().getPermission(PanelRepositoryProducts.nameClass)==Capability.READ){
		      JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này!");
		      return;
		    }
		setEnabledCompoment(true);
	}

	//Phương thức xóa cho nút [Xóa]
	@Override
	public void delete() throws Exception {
		if(UIConfigModelManager.getInstance().getPermission(PanelRepositoryProducts.nameClass)!=Capability.ADMIN){
		      JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này!");
		      return;
		    }
		if(ProductModelManager.getInstance().deleteProductByCode(product.getCode())){
			reset();	
		} else lblMessager.setText("Chưa xóa được");
	}

	//Phương thức Viết lại cho nút [Viết lại]
	@Override
	public void reset() throws Exception {
		txtName.setText("");
		txtNameOther.setText("");
		txtCode.setText(fomatDate.format(new Date()));
		txtBarCode.setText(txtCode.getText());
		lblMessager.setText("");
		product = new Product();
		try{
			cbUnit.setSelectedIndex(0);
		} catch(Exception ex){
			cbUnit.setModel(new DefaultComboBoxModel<ProductUnit>());
		}
		
		components = getAllComponents(container1);
		for (Component c : components) {
			if (c instanceof CheckBoxProductTag) {
				((JCheckBox) c).setSelected(false);
			}
		}
		setEnabledCompoment(true);
		listProductTagSelected.clear();
		txtCode.setEditable(true);
		txtBarCode.setEditable(true);
	}

	//Phương thức xem lại cho nút [Xem lại]
	@Override
	public void refresh() throws Exception {
		setData();
		int i = 0;
		listProductTagSelected.clear();
		for (CheckBoxProductTag chb : listCheckBoxProductTag) {
			boolean check = false;
			if (product != null && product.getProductTags() != null) {
				for (ProductTag p : product.getProductTags()) {
					if (p.getId().equals(getProductTags().get(i).getId())) {
						chb.setSelected(true);
						listProductTagSelected.add(p);
						check = true;
					}
				}
			}

			if (!check) {
				chb.setSelected(false);
			}
			i++;
		}
	}
	
	//Tự động thêm 35 bản ghi lưu vào danh sách
	@Override
	public void createDataTest() {
		for(int i = 0; i < 35; i++){
			try{
				product = new Product();
				product.setName("Hàng hóa HKTTest_" + i);
				product.setCode("Mã HKTTest_" + i);
				product.setCreatedTime(new Date());
				product.setNameOther("Ngôn ngữ HKTTest_" + i);
				product.setProductTags(new ArrayList<ProductTag>());
				product.setUnit("Chiếc");
				product.setMaker("HKT");
				
				ProductAttribute pa = new ProductAttribute();
				pa.setName("BarCode");
				pa.setValue(Integer.toString(1234 + i));
				product.add(pa);
				
				Product pAuto = ProductModelManager.getInstance().saveProduct(product);
			} catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}
}
