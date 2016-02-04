package com.hkt.client.swingexp.app.khohang;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.transaction.Transactional.TxType;

import com.hkt.client.swingexp.app.component.ExtendJTextField;
import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.client.swingexp.app.core.UnitMoneyJComboBox;
import com.hkt.module.product.entity.ProductPriceType;

@SuppressWarnings("serial")
public class CheckBoxProductPriceType extends JCheckBox{
	
	private ProductPriceType productPriceType;
	private ExtendJTextField textField;
	private UnitMoneyJComboBox comboBox;

	public CheckBoxProductPriceType(ProductPriceType _productPriceType) {
		this.productPriceType = _productPriceType;
	    setOpaque(false);
	    setText(_productPriceType.getName());
	    setFont(new Font("Tahoma", 1, 14));
	    
	    this.addItemListener(new ItemListener() {	
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(!isSelected()){
					textField.setText("");
				}
			}
		});
	}
	
	
	
	public ProductPriceType getProductPriceType() {
		return productPriceType;
	}
	
	public void setProductPriceType(ProductPriceType _productPriceType) {
		this.productPriceType = _productPriceType;
	}
	
	public ExtendJTextField getTextField() {
		return textField;
	}

	public void setTextField(ExtendJTextField textField1) {
		this.textField = textField1;
		this.textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try{
					MyDouble.parseDouble(textField.getText());
					textField.setForeground(Color.BLACK);
					setForeground(new Color(54, 54, 54));
					if(textField.getText().isEmpty()){
						setSelected(false);
					} else {
						setSelected(true);
					}
				}catch(Exception ex){
					textField.setForeground(Color.RED);
				}
			}
		});
	}

	public UnitMoneyJComboBox getComboBox() {
		return comboBox;
	}

	public void setComboBox(UnitMoneyJComboBox comboBox) {
		this.comboBox = comboBox;
	}
}
