/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hkt.com.javaswing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author MRG
 */
public class Table extends JFrame{
    static JTable table;
    
    public Object[] columnNames(){
        return new Object[]{"Không Sửa", "Không Sửa", "Được Sửa", "Được Sửa", "Thêm dòng mới"};
    }
    
    public Object[][] data(){
        return new Object[][]{{"", "", "", "", ""}};
    }
    
    public Table(){
        setLayout(new BorderLayout());
        table = new JTable();
        TableModel dataModel = new DefaultTableModel(data(), columnNames());
        table.setModel(dataModel);

        JScrollPane scPanel = new JScrollPane(table);
        JButton btnOk = new JButton("Ok");
        JLabel lbTitle = new JLabel("Bang JTable");
        add(scPanel, BorderLayout.CENTER);
        add(btnOk, BorderLayout.PAGE_END);
        add(lbTitle, BorderLayout.PAGE_START);
    }
    
//    private void tableMouseClicked(java.awt.event.MouseEvent evt) { 
//        if (table.getSelectedColumn() == 1 || table.getSelectedColumn() == 0){
//            table.setEnabled(false);
//            ;
//        }
//    }  
        
    
    //Add row khi chon column cuoi
    public static void addRow(){
        table.addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent event){
                if (table.columnAtPoint(event.getPoint())==4){
                    DefaultTableModel modelAdd = (DefaultTableModel) table.getModel();
                    modelAdd.addRow(new Object[]{"", "", "", "", ""});
                }
            }
        });
    }
    
    //Disable edit 2 column dau tien
    public static void disableEdit(){
        table.addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent event){
                if (table.columnAtPoint(event.getPoint())==0 || table.columnAtPoint(event.getPoint())==1){
                table.setEnabled(false);
                }else{
                table.setEnabled(true);
                }
            }
        });
    }
    
    public static void main(String[] args) {
        Table demo = new Table();
        demo.setVisible(true);
        demo.setSize(500, 500);
        demo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
        disableEdit();
        addRow();
    }
}
