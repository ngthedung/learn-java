package com.hkt.client.swingexp.app.banhang.screen.pos;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
import javax.swing.JPanel;

import com.hkt.client.swingexp.app.banhang.screen.often.PanelPayment;
import com.hkt.client.swingexp.app.component.ExtendJComboBox;
import com.hkt.client.swingexp.app.component.ExtendJLabel;
import com.hkt.client.swingexp.app.component.ExtendJTextField;
import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.client.swingexp.app.core.MyGroupLayout;
import com.hkt.client.swingexp.model.ProductModelManager;
import com.hkt.module.product.entity.Tax;

public class PanelChooseTaxPos extends JPanel implements IDialogResto {
	private ExtendJComboBox		cboTax;
	private ExtendJTextField	txtValueTax;
	private boolean						isSave	= false;
	private PanelPayment			panelPayment;
	
	public PanelChooseTaxPos(PanelPayment _panelPayment) {
		this.panelPayment = _panelPayment;
		init();
		loadCboTax();
		
		cboTax.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				panelPayment.setTax(cboTax.getSelectedIndex());
				txtValueTax.setText(panelPayment.getTotalMoneyTax());
			}
		});
	}
	
	private void init() {
		ExtendJLabel lblThue = new ExtendJLabel("Thuế");
		ExtendJLabel lblTienThue = new ExtendJLabel("Tiền thuế");
		cboTax = new ExtendJComboBox();
		txtValueTax = new ExtendJTextField();
		txtValueTax.setEditable(false);
		
		MyGroupLayout groupLayout = new MyGroupLayout(this);
		groupLayout.add(0, 0, lblThue);
		groupLayout.add(0, 1, cboTax);
		groupLayout.add(1, 0, lblTienThue);
		groupLayout.add(1, 1, txtValueTax);
		
		groupLayout.updateGui();
	}
	
	protected boolean isClickSave() {
		return isSave;
	}
	
	public int getTaxSelectedIndex() {
		return cboTax.getSelectedIndex();
	}
	
	public void setTaxSelectedByIndex(int index){
		cboTax.setSelectedIndex(index);
	}
	
	private void loadCboTax() {
		try {
			List<Tax> list = ProductModelManager.getInstance().getTaxs();
			List<Double> list2 = new ArrayList<Double>();
			list2.add(0, null);
			for (Tax tax : list) {
				list2.add(tax.getPercent());
			}
			cboTax.setModel(new DefaultComboBoxModel(list2.toArray()));
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
	
	@Override
	public void Save() throws Exception {
		isSave = true;
		((JDialog) (this.getRootPane().getParent())).dispose();
	}
	
}
