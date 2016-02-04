package com.hkt.client.swingexp.app.core;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.hkt.client.swingexp.model.GenericOptionModelManager;
import com.hkt.client.swingexp.model.GuiModelManager;
import com.hkt.module.config.generic.Option;
import com.hkt.module.config.generic.OptionGroup;

public class StoreJComboBox extends JComboBox<Option>{
	private List<Option> options;
	
	public StoreJComboBox() {
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
					new GuiModelManager<Option>().viewDialog(StoreJComboBox.this, Option.class);
					resetData();
				}
			}
		});
	}
	
	public void resetData(){
		try {
			OptionGroup optionGroup = GenericOptionModelManager.getInstance().getOptionGroup("restaurant", "RestaurantService", "store");
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
	
	public void setConfig(){
		String storeCode = "";
		try {
			FileReader fr = new FileReader(HKTFile.getFile("Database", "configPayment.json"));
			BufferedReader br = new BufferedReader(fr);
			String s;
			String jsonString = "";
			while((s = br.readLine()) != null){
				jsonString = jsonString + s;
			}
			br.close();
			fr.close();
			
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(jsonString);
			storeCode = (String) obj.get("storeCode");
		} catch(Exception ex){
//			ex.printStackTrace();
		}
		
		if(!storeCode.equals("")){
			setSelectStoreByCode(storeCode);
		} else {
			setSelectedIndex(0);
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
	
//	public Option getItemAt(int index) {
//		try {
//			return options.get(index);
//		} catch (Exception e) {
//			return null;
//		}
//	}
}
