package com.hkt.client.swingexp.app.banhang.screen.often;

import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.client.swingexp.app.core.ManagerAuthenticate;
import com.hkt.client.swingexp.app.core.MyGroupLayout;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.module.account.entity.Profile;

public class PanelDiscount extends JPanel implements IDialogResto {
	
	private JCheckBox chbPT, chbCK;
	private JTextField namePT;
	private JTextField nameCK;
	private Profile profile;
	
	public PanelDiscount() {
		chbCK = new JCheckBox("Dùng chiết khấu tiền");
		chbCK.setSelected(true);
		chbCK.setOpaque(false);
		chbPT = new JCheckBox("Dùng chiết khấu %");
		chbPT.setSelected(true);
		chbPT.setOpaque(false);
		nameCK = new JTextField("Tiền CK");
		namePT = new JTextField("% CK");
    MyGroupLayout layout = new MyGroupLayout(this);
    layout.add(0, 0, new JLabel("CK Tiền"));
    layout.add(0, 1, chbCK);
    layout.add(1, 0, new JLabel("CK %"));
    layout.add(1, 1, chbPT);
    layout.add(2, 0, new JLabel("Sửa tên"));
    layout.add(2, 1, nameCK);
    layout.add(3, 0, new JLabel("Sửa tên"));
    layout.add(3, 1, namePT);
    layout.updateGui();
    profile = AccountModelManager.getInstance().getProfileConfigAdmin();
    if(profile.get("CKTIEN")!=null && profile.get("CKTIEN").toString().equals("true")){
    	chbCK.setSelected(true);
    }else{
    	if(profile.get("CKTIEN")!=null && profile.get("CKTIEN").toString().equals("false")){
    		chbCK.setSelected(false);
    	}
    }
    if(profile.get("CKPHANTRAM")!=null && profile.get("CKPHANTRAM").toString().equals("true")){
    	chbPT.setSelected(true);
    }else{
    	if(profile.get("CKPHANTRAM")!=null && profile.get("CKPHANTRAM").toString().equals("false")){
    		chbPT.setSelected(false);
    	}
    }
    if(profile.get("NAMECKTIEN")!=null){
    	nameCK.setText(profile.get("NAMECKTIEN").toString());
    }
    if(profile.get("NAMECKPHANTRAM")!=null){
    	namePT.setText(profile.get("NAMECKPHANTRAM").toString());
    }
  }

	@Override
  public void Save() throws Exception {
		if(chbCK.isSelected()){
			profile.put("CKTIEN", "true");
		}else{
			profile.put("CKTIEN", "false");
		}
		if(chbPT.isSelected()){
			profile.put("CKPHANTRAM", "true");
		}else{
			profile.put("CKPHANTRAM", "false");
		}
		profile.put("NAMECKTIEN", nameCK.getText());
		profile.put("NAMECKPHANTRAM", namePT.getText());
		AccountModelManager.getInstance().saveProfileConfig(ManagerAuthenticate.getInstance().getOrganizationLoginId(), profile);
		((JDialog) getRootPane().getParent()).dispose();
	  
  }

}
