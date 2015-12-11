/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ireport;

import java.util.HashMap;
import java.util.Scanner;
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
    private int sum = 0;
    
    public Report(){
        String[] str = new String[20];
        DefaultTableModel tab = new DefaultTableModel(str, 1);
        jTable = new JTable(tab);
        
        String[] key = new String[]{"hoten","quequan","tuoi","lop","dtoan","dvan","danh","xeploai"};
        String[] valueS;
        valueS = new String[20];
        Scanner sc = new Scanner(System.in);
        hMap = new HashMap();
        
        System.out.println("Nhap ho va ten:");
        valueS[0] = sc.nextLine();
        hMap.put(key[0], valueS[0]);
        System.out.println("Nhap que quan:");
        valueS[1] = sc.nextLine();
        hMap.put(key[1], valueS[1]);
        System.out.println("Nhap tuoi:");
        valueS[2] = sc.nextLine();
        hMap.put(key[2], valueS[2]);
        System.out.println("Lop:");
        valueS[3] = sc.nextLine();
        hMap.put(key[3], valueS[3]);
        
        int[] valueN;
        valueN = new int[20];
        
        System.out.println("Nhap diem toan:");
        valueN[0] = sc.nextInt();
        valueS[4] = "" + valueN[0];
        hMap.put(key[4], valueS[4]);
        System.out.println("Nhap diem van:");
        valueN[1] = sc.nextInt();
        valueS[5] = "" + valueN[1];
        hMap.put(key[5], valueS[5]);
        System.out.println("Nhap diem anh:");
        valueN[2] = sc.nextInt();
        valueS[6] = "" + valueN[2];
        hMap.put(key[6], valueS[6]);
        
        sum = valueN[0] + valueN[1] + valueN[2];
        if ((sum/3)<=5) {
            valueS[7] = "Yeu";
            hMap.put(key[7], valueS[7]);
        }else if ((sum/3)>=8) {
            valueS[7] = "Gioi";
            hMap.put(key[7], valueS[7]);
        }else{
            valueS[7] = "Kha";
            hMap.put(key[7], valueS[7]);
        }
        System.out.println("Dang xu ly du lieu...");
    }
    
    public void export(){
        reportExport("Report.jasper", hMap, jTable.getModel(),false);
    }
    
    private void reportExport(String filePathResource, HashMap hashMap, TableModel model, boolean flag) {
        ReportManager.getInstance().viewReport(filePathResource, hashMap, model, flag);
    }
}
