/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hkt.hello;

import java.util.HashMap;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Nguyen The Dung
 */
public class Report {
    private HashMap hMap;
    private JTable jTable;
    
    public Report(){
        String[] str = new String[15];
        DefaultTableModel tab = new DefaultTableModel(str, 1);
        jTable = new JTable(tab);
        
        String[] key = new String[]{"java","world","maven","github",};
        hMap = new HashMap();
        String[] value = new String[15];
        value[0] = "java";
        value[1] = "world";
        value[2] = "maven";
        value[3] = "github";
        Object obj1 = hMap.put(key[0], value[0]);
        Object obj2 = hMap.put(key[1], value[1]);
        Object obj3 = hMap.put(key[2], value[2]);
        Object obj4 = hMap.put(key[3], value[3]);
    }
    
        
    public void export(){
        reportExport("report.jasper", hMap, jTable.getModel(), false);
    }
    
    private void reportExport(String filePathResource, HashMap hashMap, TableModel model, boolean flag){
        ReportManager.getInstance().viewReport(filePathResource, hashMap, model, flag);
    }
}