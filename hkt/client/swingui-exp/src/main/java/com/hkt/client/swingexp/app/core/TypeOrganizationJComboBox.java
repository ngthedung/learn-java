package com.hkt.client.swingexp.app.core;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import com.hkt.client.swingexp.model.GenericOptionModelManager;
import com.hkt.client.swingexp.model.GuiModelManager;
import com.hkt.module.config.generic.Option;
import com.hkt.module.config.generic.OptionGroup;

public class TypeOrganizationJComboBox extends JComboBox<Option>{
	private List<Option> options;
	
	public TypeOrganizationJComboBox() {
		super();
		setFont(new Font("Tahoma", 0, 14));
		setOpaque(false);
		setPreferredSize(new Dimension(200, 23));
		options = new ArrayList<Option>();
		resetData();
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == 3) {
					new GuiModelManager<Option>().viewDialog(TypeOrganizationJComboBox.this, Option.class);
					resetData();
				}
			}
		});
	}
	
	public void resetData(){
		try {
			OptionGroup optionGroup = GenericOptionModelManager.getInstance().getOptionGroup(
					"config", "LocaleService", "type_restaurant");
			options.clear();
			if (optionGroup.getOptions() != null && optionGroup.getOptions().size() > 0) {
				options.addAll(optionGroup.getOptions());
			}
			options.add(0, null);
			setModel(new DefaultComboBoxModel(options.toArray()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setSelectStoreByCode(String code){
		for(int i = 0; i < this.getItemCount(); i++){
			Option p = this.getItemAt(i);
			if(p != null && p.getCode().equals(code)){
				this.setSelectedIndex(i);
				break;
			}
		}
	}
	
	public Option getSelectedStore(){
		try {
			return (Option) getSelectedItem();
		} catch(Exception ex){
			return null;
		}
	}

}
