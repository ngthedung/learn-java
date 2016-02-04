package com.hkt.client.swingexp.app.nhansu;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import com.hkt.client.rest.ClientContext;
import com.hkt.client.rest.service.AccountServiceClient;
import com.hkt.client.rest.service.UIConfigServiceClient;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.client.swingexp.app.core.ManagerAuthenticate;
import com.hkt.client.swingexp.model.HRModelManager;
import com.hkt.module.account.entity.AccountMembership.Capability;
import com.hkt.module.hr.entity.Employee;

public class PanelPermission extends JPanel implements IDialogResto{
	
	private static PanelPermission panelPermission;
	private ClientContext clientContext = ClientContext.getInstance();
	  private AccountServiceClient accountServiceClient = clientContext.getBean(AccountServiceClient.class);
	  private UIConfigServiceClient clientCore = clientContext.getBean(UIConfigServiceClient.class); 
	  private Capability c;
	  private boolean online = true;
	  
	public static PanelPermission getInstance(){
		if(panelPermission==null){
			panelPermission =new PanelPermission();
		}
		return panelPermission;
	}
	
	private String screen;
	
	
	
	public Capability getC() {
		return c;
	}
	
	public boolean onlineSetup(){
		txtTaiKhoan.setText("");
		txtMK.setText("");
		jCheckBox1.setSelected(true);
		online = false;
		DialogResto dialog = new DialogResto(this, false, 170, 200);
		dialog.setName("dlInfo");
		dialog.setIcon("lienhe1.png");
		dialog.setLocationRelativeTo(null);
		dialog.setTitle("Truy cập online");
		dialog.setVisible(true);
		return online;
	}

	public Capability isPermisson(String screen){
		online = true;
		this.screen=screen;
		txtTaiKhoan.setText("");
		txtMK.setText("");
		jCheckBox1.setSelected(false);
		DialogResto dialog = new DialogResto(this, false, 170, 200);
		dialog.setName("dlInfo");
		dialog.setIcon("lienhe1.png");
		dialog.setLocationRelativeTo(null);
		dialog.setTitle("Truy cập nhanh");
		dialog.setVisible(true);
		return c;
	}

	 public PanelPermission() {
	        initComponents();
	    }

	    /** This method is called from within the constructor to
	     * initialize the form.
	     * WARNING: Do NOT modify this code. The content of this method is
	     * always regenerated by the Form Editor.
	     */
	 private void initComponents() {

	        jLabel1 = new javax.swing.JLabel();
	        jLabel2 = new javax.swing.JLabel();
	        jCheckBox1 = new javax.swing.JCheckBox();
	        jLabel3 = new javax.swing.JLabel();
	        jLabel4 = new javax.swing.JLabel();
	        txtTaiKhoan = new javax.swing.JTextField();
	        txtMK = new JPasswordField();
	        lbStt = new javax.swing.JLabel();

	        jLabel1.setText("jLabel1");

	        setOpaque(false);

	        jLabel2.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
	        jLabel2.setText("Bạn chưa được phân quyền chức năng này, cần liên hệ với quản trị viên!");

	        jCheckBox1.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
	        jCheckBox1.setText("Truy cập nhanh");
	        jCheckBox1.setOpaque(false);
	        jCheckBox1.addItemListener(new java.awt.event.ItemListener() {
	            public void itemStateChanged(java.awt.event.ItemEvent evt) {
	                jCheckBox1ItemStateChanged(evt);
	            }
	        });

	        jLabel3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
	        jLabel3.setText("Tài khoản");

	        jLabel4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
	        jLabel4.setText("Mật khẩu");

	        txtTaiKhoan.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
	        txtTaiKhoan.setEnabled(false);
	        txtTaiKhoan.setOpaque(false);

	        txtMK.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
	        txtMK.setEnabled(false);
	        txtMK.setOpaque(false);

	        lbStt.setForeground(new java.awt.Color(-8650752,true));
	        lbStt.setText(" ");

	        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
	        this.setLayout(layout);
	        layout.setHorizontalGroup(
	            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(layout.createSequentialGroup()
	                .addContainerGap()
	                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                    .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
	                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                    .addGroup(layout.createSequentialGroup()
	                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                            .addComponent(jLabel4)
	                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
	                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
	                            .addComponent(txtMK)
	                            .addComponent(txtTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)))
	                    .addComponent(lbStt, javax.swing.GroupLayout.DEFAULT_SIZE, 462, Short.MAX_VALUE))
	                .addContainerGap())
	        );
	        layout.setVerticalGroup(
	            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(layout.createSequentialGroup()
	                .addContainerGap()
	                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                .addComponent(jCheckBox1)
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
	                    .addComponent(txtTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
	                    .addComponent(txtMK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                .addComponent(lbStt)
	                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
	        );
	    }// </editor-fold>

	private void jCheckBox1ItemStateChanged(java.awt.event.ItemEvent evt) {
	    if(jCheckBox1.isSelected()){
	        txtMK.setEnabled(true);
	        txtTaiKhoan.setEnabled(true);
	    }else{
	        txtMK.setEnabled(false);
	        txtTaiKhoan.setEnabled(false);
	    }
	}

	    // Variables declaration - do not modify
	    private javax.swing.JCheckBox jCheckBox1;
	    private javax.swing.JLabel jLabel1;
	    private javax.swing.JLabel jLabel2;
	    private javax.swing.JLabel jLabel3;
	    private javax.swing.JLabel jLabel4;
	    private javax.swing.JLabel lbStt;
	    private javax.swing.JPasswordField txtMK;
	    private javax.swing.JTextField txtTaiKhoan;
	    // End of variab
	    // End of variables declaration
		@Override
		public void Save() throws Exception {
			if(!online){
				String txt = txtMK.getText();
				if(txtTaiKhoan.getText().equals("hktc") && txt.equals("hktc688")){
					online = true;
				}else {
					online = false;
				}
				((JDialog)getRootPane().getParent()).dispose();
			}else {
				if(accountServiceClient.login(txtTaiKhoan.getText(),txtMK.getText())!=null){
					Employee employee = HRModelManager.getInstance().getBydLoginId(txtTaiKhoan.getText());
					String permission="";
					if (employee.getPermissionGroup() != null) {
						permission = employee.getPermissionGroup();
					} else {
						permission = employee.getLoginId();
					}
					 c = clientCore.getPermission(ManagerAuthenticate.getInstance().getOrganizationLoginId(), permission,
						    screen);
				
					 ((JDialog)getRootPane().getParent()).dispose();
					 
				}else {
					lbStt.setText("Sai tên đăng nhập hoặc mật khẩu");
				}
			}
			
			
			
		}
	}

