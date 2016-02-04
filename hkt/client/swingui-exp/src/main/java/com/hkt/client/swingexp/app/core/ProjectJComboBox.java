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

import com.hkt.client.swingexp.model.GuiModelManager;
import com.hkt.client.swingexp.model.RestaurantModelManager;
import com.hkt.module.restaurant.entity.Project;

public class ProjectJComboBox extends JComboBox<Project> {
	private List<Project> projects;
	
	public ProjectJComboBox() {
		super();
		setFont(new Font("Tahoma", 0, 14));
		setOpaque(false);
		setPreferredSize(new Dimension(200, 23));
		resetData();
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == 3) {
					new GuiModelManager<Project>().viewDialog(ProjectJComboBox.this, Project.class);
					resetData();
				}
			}
		});
	}
	
	public void resetData() {
		try {
			projects = RestaurantModelManager.getInstance().getAllProject();
		} catch (Exception e1) {
			projects = new ArrayList<Project>();
		}
		projects.add(0, null);
		setModel(new DefaultComboBoxModel(projects.toArray()));
	}
	
	public Project getSelectedProject(){
		try {
			return (Project) getSelectedItem();
		} catch(Exception ex) {
			return null;
		}
	}
	
	public void setSelectProjectByCode(String code) {
		for(Project p : projects){
			if(p != null && p.getCode().equals(code)){
				setSelectedItem(p);
				break;
			}
		}
	}
	
	public void setConfig(){
		String projectCode = RestaurantModelManager.getInstance().getProjectOther().getCode();
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
			projectCode = (String) obj.get("projectCode");
		} catch(Exception ex){
//			ex.printStackTrace();
		}
		
		if(!projectCode.equals("") && !projectCode.equals(RestaurantModelManager.getInstance().getProjectOther().getCode())){
			setSelectProjectByCode(projectCode);
		} else {
			setSelectedIndex(0);
		}
	}
}
