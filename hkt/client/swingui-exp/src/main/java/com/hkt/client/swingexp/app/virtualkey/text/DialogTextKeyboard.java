/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DialogTextKeyboard.java
 *
 * Created on Jul 31, 2013, 4:38:06 PM
 */
package com.hkt.client.swingexp.app.virtualkey.text;

import java.awt.GridLayout;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.hkt.client.swingexp.app.component.PanelBackground;

/**
 *
 * @author longnt
 */
public class DialogTextKeyboard extends javax.swing.JDialog {

    private PanelKeyboardForDialog panelKeyboard;

    /** Creates new form DialogTextKeyboard */
    public DialogTextKeyboard() {
        initComponents();
        dispose();
        setUndecorated(true);
        this.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
        this.setAlwaysOnTop(true);
        this.setLayout(new GridLayout());
        panelKeyboard = new PanelKeyboardForDialog();
        jScrollPane1.setViewportView(panelKeyboard);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelBackground1 = new PanelBackground();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
                formWindowLostFocus(evt);
            }
        });

        jPanel1.setOpaque(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 61, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 302, Short.MAX_VALUE)
        );

        jPanel2.setOpaque(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 52, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 302, Short.MAX_VALUE)
        );

        jScrollPane1.setOpaque(false);

        javax.swing.GroupLayout panelBackground1Layout = new javax.swing.GroupLayout(panelBackground1);
        panelBackground1.setLayout(panelBackground1Layout);
        panelBackground1Layout.setHorizontalGroup(
            panelBackground1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBackground1Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 755, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelBackground1Layout.setVerticalGroup(
            panelBackground1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 868, Short.MAX_VALUE)
            .addComponent(panelBackground1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 302, Short.MAX_VALUE)
            .addComponent(panelBackground1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void formWindowLostFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowLostFocus
    this.dispose();
}//GEN-LAST:event_formWindowLostFocus
    /**
     * @param args the command line arguments
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private PanelBackground panelBackground1;
    // End of variables declaration//GEN-END:variables

   
    public void setTypeKeyboard(int typeKeyboard) {
        panelKeyboard.setTypeKeyboard(typeKeyboard);
    }

   
    public void setTextField(JTextField textField) {
        panelKeyboard.setTextField(textField);
    }

   
    public void setTextAria(JTextArea jTextArea) {
        panelKeyboard.setTextAria(jTextArea);
    }

   
    public void setIndex(int index) {
        panelKeyboard.setIndex(index);
    }

   
    public void setToolTip(String toolTipText) {
        panelKeyboard.setToolTip(toolTipText);
    }

   
    public void setShowAll(boolean flag) {
        panelKeyboard.setShowAll(flag);
    }

   
    public void setShowSale(boolean flag) {
        panelKeyboard.setShowSale(flag);
    }

   
    public boolean isShowAll() {
        return panelKeyboard.isShowAll();
    }

   
    public boolean isShowSale() {
        return panelKeyboard.isShowSale();
    }

   
    public void loadKeyboard() {
        panelKeyboard.loadKeyboard();
    }

   
    public void setTable(JTable jTable, int rowSelected) {
        panelKeyboard.setTable(jTable, rowSelected);
    }
}
