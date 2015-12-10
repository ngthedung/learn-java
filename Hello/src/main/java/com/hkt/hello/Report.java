/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hkt.hello;

import java.io.File;
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
        String[] str = new String[5];
        DefaultTableModel tab = new DefaultTableModel(str, 1);
        jTable = new JTable(tab);
        
        String[] key;
        key = new String[]{"World","Maven","GitHub","Java"};
        
        hMap = new HashMap();
        String[] value;
        value = new String[]{"World","Maven","GitHub","Java"};
        
        hMap.put(key[0], value[0]);
        hMap.put(key[1], value[1]);
        hMap.put(key[2], value[2]);
        hMap.put(key[3], value[3]);
    }
    
        
    public void export(){
        reportExport(File.separator + "report.jasper", hMap, jTable.getModel(), false);
    }
    
    private void reportExport(String filePathResource, HashMap hashMap, TableModel model, boolean flag){
        ReportManager.getInstance().viewReport(filePathResource, hashMap, model, flag);
    }
}
