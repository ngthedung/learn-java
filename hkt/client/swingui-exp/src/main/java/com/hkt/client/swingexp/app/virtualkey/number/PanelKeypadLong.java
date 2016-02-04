/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PanelKeypadA.java
 *
 * Created on Sep 20, 2013, 11:51:00 AM
 */
package com.hkt.client.swingexp.app.virtualkey.number;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.Border;

import com.hkt.client.swingexp.app.component.PanelBackground;
import com.hkt.client.swingexp.app.virtualkey.text.MouseEventKeypad;
import com.hkt.client.swingexp.homescreen.HomeScreen;
import com.hkt.module.promotion.HKTFile;

/**
 *
 * @author BinhMinh
 */
public class PanelKeypadLong extends JPanel  {

    private JTextField textField;
    /**Index là chỉ số của Cursor(dấu nháy chuột)*/
    private int index;
    /**Eo thấy dùng j cả*/
    private String toolTipText;
    /**
     * - void setShowAll(boolean flag)
     * - boolean isShowAll()
     * éo hiểu nhỉ
     */
    private boolean flagAll = false;
    /**
     * void setShowSale(boolean flag)
     * boolean isShowSale()
     * éo hiểu nhi
     */
    private boolean flagSale = false;
    private String url;
    private String data = "DataKeyboard";
    private List<String> listKeyboard;
    //--
    private Timer timerCancel;
    private Timer timerRight;
    private Timer timerLeft;

    public PanelKeypadLong() {
        initComponents();
        buttonEvent();
        btn1.addMouseListener(new MouseEventKeypad("/com/vn/hkt/virtualkey/keypad/icon/", "1.png", "11.png"));
        btn2.addMouseListener(new MouseEventKeypad("/com/vn/hkt/virtualkey/keypad/icon/", "2.png", "22.png"));
        btn3.addMouseListener(new MouseEventKeypad("/com/vn/hkt/virtualkey/keypad/icon/", "3.png", "33.png"));
        btn4.addMouseListener(new MouseEventKeypad("/com/vn/hkt/virtualkey/keypad/icon/", "4.png", "44.png"));
        btn5.addMouseListener(new MouseEventKeypad("/com/vn/hkt/virtualkey/keypad/icon/", "5.png", "55.png"));
        btn6.addMouseListener(new MouseEventKeypad("/com/vn/hkt/virtualkey/keypad/icon/", "6.png", "66.png"));
        btn7.addMouseListener(new MouseEventKeypad("/com/vn/hkt/virtualkey/keypad/icon/", "7.png", "77.png"));
        btn8.addMouseListener(new MouseEventKeypad("/com/vn/hkt/virtualkey/keypad/icon/", "8.png", "88.png"));
        btn9.addMouseListener(new MouseEventKeypad("/com/vn/hkt/virtualkey/keypad/icon/", "9.png", "99.png"));
        btnDot.addMouseListener(new MouseEventKeypad("/com/vn/hkt/virtualkey/keypad/icon/", "dot.png", "dot.png"));
        btn0.addMouseListener(new MouseEventKeypad("/com/vn/hkt/virtualkey/keypad/icon/", "0.png", "00.png"));
        btnC.addMouseListener(new MouseEventKeypad("/com/vn/hkt/virtualkey/keypad/icon/", "Cancel.png", "CAL.png"));
        btnok.addMouseListener(new MouseEventKeypad("/com/vn/hkt/virtualkey/keypad/icon/", "okok.png", "okok.png"));
        btnPrevious.addMouseListener(new MouseEventKeypad("/com/vn/hkt/virtualkey/keypad/icon/", "leftRed.png", "leftRed.png"));
        btnNext.addMouseListener(new MouseEventKeypad("/com/vn/hkt/virtualkey/keypad/icon/", "rightRed.png", "rightRed.png"));
        //--
        /**Lấy ra đường dẫn module DataBase, và file truyền vào, đang là rỗng, nên chẳng có éo j*/
        File file = HKTFile.getFile("DataBase", "");
        url = file.getAbsolutePath();
        loadKeyboard();
//        textView.setVisible(false);
        timerCancel = new Timer(150, new ActionListener() {

            
            public void actionPerformed(ActionEvent e) {
                actionCanel();
            }
        });

        timerRight = new Timer(150, new ActionListener() {

            
            public void actionPerformed(ActionEvent e) {
                actionRight();
            }
        });

        timerLeft = new Timer(150, new ActionListener() {

            
            public void actionPerformed(ActionEvent e) {
                actionLeft();
            }
        });
    }

    private void buttonEvent() {
        /**Return the system default cursor*/
        Cursor c = Cursor.getDefaultCursor();
        /**The hand cursor type.*/
        c = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
        /**set cursor hình bàn tay*/
        btn0.setCursor(c);
        /**set tập trung miêu ta false*/
        btn0.setFocusPainted(false);
        btn1.setCursor(c);
        btn1.setFocusPainted(false);
        btn2.setCursor(c);
        btn2.setFocusPainted(false);
        btn3.setCursor(c);
        btn3.setFocusPainted(false);
        btn4.setCursor(c);
        btn4.setFocusPainted(false);
        btn5.setCursor(c);
        btn5.setFocusPainted(false);
        btn6.setCursor(c);
        btn6.setFocusPainted(false);
        btn7.setCursor(c);
        btn7.setFocusPainted(false);
        btn8.setCursor(c);
        btn8.setFocusPainted(false);
        btn9.setCursor(c);
        btn9.setFocusPainted(false);
        btnDot.setCursor(c);
        btnDot.setFocusPainted(false);
        btnC.setCursor(c);
        btnC.setFocusPainted(false);
        btnok.setCursor(c);
        btnok.setFocusPainted(false);
        btnPrevious.setCursor(c);
        btnPrevious.setFocusPainted(false);
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
        btn0 = new javax.swing.JButton();
        btn1 = new javax.swing.JButton();
        btn2 = new javax.swing.JButton();
        btn3 = new javax.swing.JButton();
        btn4 = new javax.swing.JButton();
        btn5 = new javax.swing.JButton();
        btn6 = new javax.swing.JButton();
        btn7 = new javax.swing.JButton();
        btn8 = new javax.swing.JButton();
        btn9 = new javax.swing.JButton();
        btnDot = new javax.swing.JButton();
        btnPrevious = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnC = new javax.swing.JButton();
        btnok = new javax.swing.JButton();
        textView = new javax.swing.JTextField();

        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(207, 270));

        jPanel2.setMinimumSize(new java.awt.Dimension(207, 56));
        jPanel2.setOpaque(false);

        btn0.setBackground(new java.awt.Color(255, 255, 255));
        btn0.setFont(new java.awt.Font("Tahoma", 0, 36));
        btn0.setIcon(new javax.swing.ImageIcon(HomeScreen.class.getResource("icon/0.png"))); // NOI18N
        btn0.setText("0"); // NOI18N
        btn0.setContentAreaFilled(false);
        btn0.setMargin(new java.awt.Insets(0, 0, 0, 0));
        btn0.setMaximumSize(new java.awt.Dimension(56, 56));
        btn0.setMinimumSize(new java.awt.Dimension(56, 56));
        btn0.setPreferredSize(new java.awt.Dimension(56, 56));
        btn0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn0ActionPerformed(evt);
            }
        });

        btn1.setBackground(new java.awt.Color(255, 255, 255));
        btn1.setFont(new java.awt.Font("Tahoma", 0, 36));
        btn1.setIcon(new javax.swing.ImageIcon(HomeScreen.class.getResource("icon/1.png"))); // NOI18N
        btn1.setText("1"); // NOI18N
        btn1.setAlignmentY(0.0F);
        btn1.setContentAreaFilled(false);
        btn1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btn1.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btn1.setMargin(new java.awt.Insets(0, 0, 0, 0));
        btn1.setPreferredSize(new java.awt.Dimension(56, 56));
        btn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn1ActionPerformed(evt);
            }
        });

        btn2.setBackground(new java.awt.Color(255, 255, 255));
        btn2.setFont(new java.awt.Font("Tahoma", 0, 36));
        btn2.setIcon(new javax.swing.ImageIcon(HomeScreen.class.getResource("icon/2.png"))); // NOI18N
        btn2.setText("2"); // NOI18N
        btn2.setContentAreaFilled(false);
        btn2.setMargin(new java.awt.Insets(0, 0, 0, 0));
        btn2.setPreferredSize(new java.awt.Dimension(56, 56));
        btn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn2ActionPerformed(evt);
            }
        });

        btn3.setBackground(new java.awt.Color(255, 255, 255));
        btn3.setFont(new java.awt.Font("Tahoma", 0, 36));
        btn3.setIcon(new javax.swing.ImageIcon(HomeScreen.class.getResource("icon/3.png"))); // NOI18N
        btn3.setText("3"); // NOI18N
        btn3.setContentAreaFilled(false);
        btn3.setMargin(new java.awt.Insets(0, 0, 0, 0));
        btn3.setPreferredSize(new java.awt.Dimension(56, 56));
        btn3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn3ActionPerformed(evt);
            }
        });

        btn4.setBackground(new java.awt.Color(255, 255, 255));
        btn4.setFont(new java.awt.Font("Tahoma", 0, 36));
        btn4.setIcon(new javax.swing.ImageIcon(HomeScreen.class.getResource("icon/4.png"))); // NOI18N
        btn4.setText("4"); // NOI18N
        btn4.setContentAreaFilled(false);
        btn4.setMargin(new java.awt.Insets(0, 0, 0, 0));
        btn4.setMaximumSize(new java.awt.Dimension(56, 56));
        btn4.setMinimumSize(new java.awt.Dimension(56, 56));
        btn4.setPreferredSize(new java.awt.Dimension(56, 56));
        btn4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn4ActionPerformed(evt);
            }
        });

        btn5.setBackground(new java.awt.Color(255, 255, 255));
        btn5.setFont(new java.awt.Font("Tahoma", 0, 36));
        btn5.setIcon(new javax.swing.ImageIcon(HomeScreen.class.getResource("icon/5.png"))); // NOI18N
        btn5.setText("5"); // NOI18N
        btn5.setContentAreaFilled(false);
        btn5.setMargin(new java.awt.Insets(0, 0, 0, 0));
        btn5.setMaximumSize(new java.awt.Dimension(56, 56));
        btn5.setMinimumSize(new java.awt.Dimension(56, 56));
        btn5.setPreferredSize(new java.awt.Dimension(56, 56));
        btn5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn5ActionPerformed(evt);
            }
        });

        btn6.setBackground(new java.awt.Color(255, 255, 255));
        btn6.setFont(new java.awt.Font("Tahoma", 0, 36));
        btn6.setIcon(new javax.swing.ImageIcon(HomeScreen.class.getResource("icon/6.png"))); // NOI18N
        btn6.setText("6"); // NOI18N
        btn6.setContentAreaFilled(false);
        btn6.setMargin(new java.awt.Insets(0, 0, 0, 0));
        btn6.setMaximumSize(new java.awt.Dimension(56, 56));
        btn6.setMinimumSize(new java.awt.Dimension(56, 56));
        btn6.setPreferredSize(new java.awt.Dimension(56, 56));
        btn6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn6ActionPerformed(evt);
            }
        });

        btn7.setBackground(new java.awt.Color(255, 255, 255));
        btn7.setFont(new java.awt.Font("Tahoma", 0, 36));
        btn7.setIcon(new javax.swing.ImageIcon(HomeScreen.class.getResource("icon/7.png"))); // NOI18N
        btn7.setText("7"); // NOI18N
        btn7.setContentAreaFilled(false);
        btn7.setMargin(new java.awt.Insets(0, 0, 0, 0));
        btn7.setMaximumSize(new java.awt.Dimension(56, 56));
        btn7.setMinimumSize(new java.awt.Dimension(56, 56));
        btn7.setPreferredSize(new java.awt.Dimension(56, 56));
        btn7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn7ActionPerformed(evt);
            }
        });

        btn8.setBackground(new java.awt.Color(255, 255, 255));
        btn8.setFont(new java.awt.Font("Tahoma", 0, 36));
        btn8.setIcon(new javax.swing.ImageIcon(HomeScreen.class.getResource("icon/8.png"))); // NOI18N
        btn8.setText("8"); // NOI18N
        btn8.setContentAreaFilled(false);
        btn8.setMargin(new java.awt.Insets(0, 0, 0, 0));
        btn8.setMaximumSize(new java.awt.Dimension(56, 56));
        btn8.setMinimumSize(new java.awt.Dimension(56, 56));
        btn8.setPreferredSize(new java.awt.Dimension(56, 56));
        btn8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn8ActionPerformed(evt);
            }
        });

        btn9.setBackground(new java.awt.Color(255, 255, 255));
        btn9.setFont(new java.awt.Font("Tahoma", 0, 36));
        btn9.setIcon(new javax.swing.ImageIcon(HomeScreen.class.getResource("icon/9.png"))); // NOI18N
        btn9.setText("9"); // NOI18N
        btn9.setContentAreaFilled(false);
        btn9.setMargin(new java.awt.Insets(0, 0, 0, 0));
        btn9.setMaximumSize(new java.awt.Dimension(56, 56));
        btn9.setMinimumSize(new java.awt.Dimension(56, 56));
        btn9.setPreferredSize(new java.awt.Dimension(56, 56));
        btn9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn9ActionPerformed(evt);
            }
        });

        btnDot.setBackground(new java.awt.Color(255, 255, 255));
        btnDot.setFont(new java.awt.Font("Tahoma", 0, 36));
        btnDot.setIcon(new javax.swing.ImageIcon(HomeScreen.class.getResource("icon/dot.png"))); // NOI18N
        btnDot.setText(""); // NOI18N
        btnDot.setContentAreaFilled(false);
        btnDot.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDot.setMargin(new java.awt.Insets(0, 0, 0, 0));
        btnDot.setMaximumSize(new java.awt.Dimension(56, 56));
        btnDot.setMinimumSize(new java.awt.Dimension(56, 56));
        btnDot.setPreferredSize(new java.awt.Dimension(56, 56));
        btnDot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDotActionPerformed(evt);
            }
        });

        btnPrevious.setBackground(new java.awt.Color(255, 255, 255));
        btnPrevious.setFont(new java.awt.Font("Tahoma", 0, 36));
        btnPrevious.setIcon(new javax.swing.ImageIcon(HomeScreen.class.getResource("icon/leftRed.png"))); // NOI18N
        btnPrevious.setText(""); // NOI18N
        btnPrevious.setContentAreaFilled(false);
        btnPrevious.setMargin(new java.awt.Insets(0, 0, 0, 0));
        btnPrevious.setMinimumSize(new java.awt.Dimension(56, 56));
        btnPrevious.setPreferredSize(new java.awt.Dimension(56, 56));
        btnPrevious.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnPreviousMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btnPreviousMouseReleased(evt);
            }
        });
        btnPrevious.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreviousActionPerformed(evt);
            }
        });

        btnNext.setBackground(new java.awt.Color(255, 255, 255));
        btnNext.setFont(new java.awt.Font("Tahoma", 0, 36));
        btnNext.setIcon(new javax.swing.ImageIcon(HomeScreen.class.getResource("icon/rightRed.png"))); // NOI18N
        btnNext.setText(""); // NOI18N
        btnNext.setContentAreaFilled(false);
        btnNext.setMargin(new java.awt.Insets(0, 0, 0, 0));
        btnNext.setMinimumSize(new java.awt.Dimension(56, 56));
        btnNext.setPreferredSize(new java.awt.Dimension(56, 56));
        btnNext.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnNextMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btnNextMouseReleased(evt);
            }
        });
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnC.setBackground(new java.awt.Color(255, 255, 255));
        btnC.setFont(new java.awt.Font("Tahoma", 0, 36));
        btnC.setIcon(new javax.swing.ImageIcon(HomeScreen.class.getResource("icon/Cancel.png"))); // NOI18N
        btnC.setText("C"); // NOI18N
        btnC.setContentAreaFilled(false);
        btnC.setMargin(new java.awt.Insets(0, 0, 0, 0));
        btnC.setMaximumSize(new java.awt.Dimension(56, 56));
        btnC.setMinimumSize(new java.awt.Dimension(56, 56));
        btnC.setPreferredSize(new java.awt.Dimension(56, 56));
        btnC.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnCMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btnCMouseReleased(evt);
            }
        });
        btnC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCActionPerformed(evt);
            }
        });

        btnok.setBackground(new java.awt.Color(255, 255, 255));
        btnok.setFont(new java.awt.Font("Tahoma", 0, 36));
        btnok.setIcon(new javax.swing.ImageIcon(HomeScreen.class.getResource("icon/okok.png"))); // NOI18N
        btnok.setText(""); // NOI18N
        btnok.setContentAreaFilled(false);
        btnok.setMargin(new java.awt.Insets(0, 0, 0, 0));
        btnok.setMinimumSize(new java.awt.Dimension(56, 56));
        btnok.setPreferredSize(new java.awt.Dimension(56, 56));
        btnok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnokActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(btn0, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btn1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btn2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btn3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btn4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btn5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btn6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btn7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btn8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btn9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnDot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnPrevious, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btn0, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(btn1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(btn2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(btn3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(btn4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(btn5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(btn6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(btn7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(btn8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(btn9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(btnDot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(btnPrevious, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(btnC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(btnok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 840, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        textView.setFont(new java.awt.Font("Tahoma", 0, 20));
        textView.setText(""); // NOI18N

        javax.swing.GroupLayout panelBackground1Layout = new javax.swing.GroupLayout(panelBackground1);
        panelBackground1.setLayout(panelBackground1Layout);
        panelBackground1Layout.setHorizontalGroup(
            panelBackground1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBackground1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelBackground1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 840, Short.MAX_VALUE)
                    .addComponent(textView, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 837, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelBackground1Layout.setVerticalGroup(
            panelBackground1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBackground1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(textView, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelBackground1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelBackground1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

private void btn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn1ActionPerformed
    if (textField != null) {
        /**Nếu trước đó đã ấn một nút nào đó rồi ấn nút 1*/
        if (textField.getText().length() < textView.getText().length()) {
            textField.setText(textView.getText());
            index = textView.getCaretPosition();
        }
        /**Nếu chỉ số index hiện tại lớn hơn giá trị được truyền vào,
        trường hợp khi nhập 123456789 rồi, ok có index = 9
        vào một textfield khác chỉ có 123, để nhập tiếp, thì lại set index = texview
        vì textView.setText(jtextfield.getText() ở trên)*/
        if (index > textField.getText().length()) {
            index = textView.getText().length();
        }
        index = textView.getCaretPosition();
        StringBuilder sb = new StringBuilder();
        sb.append(textView.getText());
        sb.insert(index, "1");
        textView.setText(sb.toString());
        textField.setText(sb.toString());
        index = index + 1;
        textView.requestFocus();
        textView.setCaretPosition(index);
    }
}//GEN-LAST:event_btn1ActionPerformed

private void btn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn2ActionPerformed
    if (textField != null) {
        /**Nếu trước đó đã ấn một nút nào đó rồi ấn nút 1*/
        if (textField.getText().length() < textView.getText().length()) {
            textField.setText(textView.getText());
            index = textView.getCaretPosition();
        }
        /**Nếu chỉ số index hiện tại lớn hơn giá trị được truyền vào,
        trường hợp khi nhập 123456789 rồi, ok có index = 9
        vào một textfield khác chỉ có 123, để nhập tiếp, thì lại set index = texview
        vì textView.setText(jtextfield.getText() ở trên)*/
        if (index > textField.getText().length()) {
            index = textView.getText().length();
        }
        index = textView.getCaretPosition();
        StringBuilder sb = new StringBuilder();
        sb.append(textView.getText());
        sb.insert(index, "2");
        textView.setText(sb.toString());
        textField.setText(sb.toString());
        index = index + 1;
        textView.requestFocus();
        textView.setCaretPosition(index);
    }
}//GEN-LAST:event_btn2ActionPerformed

private void btn3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn3ActionPerformed
    if (textField != null) {
        /**Nếu trước đó đã ấn một nút nào đó rồi ấn nút 1*/
        if (textField.getText().length() < textView.getText().length()) {
            textField.setText(textView.getText());
            index = textView.getCaretPosition();
        }
        /**Nếu chỉ số index hiện tại lớn hơn giá trị được truyền vào,
        trường hợp khi nhập 123456789 rồi, ok có index = 9
        vào một textfield khác chỉ có 123, để nhập tiếp, thì lại set index = texview
        vì textView.setText(jtextfield.getText() ở trên)*/
        if (index > textField.getText().length()) {
            index = textView.getText().length();
        }
        index = textView.getCaretPosition();
        StringBuilder sb = new StringBuilder();
        sb.append(textView.getText());
        sb.insert(index, "3");
        textView.setText(sb.toString());
        textField.setText(sb.toString());
        index = index + 1;
        textView.requestFocus();
        textView.setCaretPosition(index);
    }
}//GEN-LAST:event_btn3ActionPerformed

private void btn4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn4ActionPerformed
    if (textField != null) {
        /**Nếu trước đó đã ấn một nút nào đó rồi ấn nút 1*/
        if (textField.getText().length() < textView.getText().length()) {
            textField.setText(textView.getText());
            index = textView.getCaretPosition();
        }
        /**Nếu chỉ số index hiện tại lớn hơn giá trị được truyền vào,
        trường hợp khi nhập 123456789 rồi, ok có index = 9
        vào một textfield khác chỉ có 123, để nhập tiếp, thì lại set index = texview
        vì textView.setText(jtextfield.getText() ở trên)*/
        if (index > textField.getText().length()) {
            index = textView.getText().length();
        }
        index = textView.getCaretPosition();
        StringBuilder sb = new StringBuilder();
        sb.append(textView.getText());
        sb.insert(index, "4");
        textView.setText(sb.toString());
        textField.setText(sb.toString());
        index = index + 1;
        textView.requestFocus();
        textView.setCaretPosition(index);
    }
}//GEN-LAST:event_btn4ActionPerformed

private void btn5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn5ActionPerformed
    if (textField != null) {
        /**Nếu trước đó đã ấn một nút nào đó rồi ấn nút 1*/
        if (textField.getText().length() < textView.getText().length()) {
            textField.setText(textView.getText());
            index = textView.getCaretPosition();
        }
        /**Nếu chỉ số index hiện tại lớn hơn giá trị được truyền vào,
        trường hợp khi nhập 123456789 rồi, ok có index = 9
        vào một textfield khác chỉ có 123, để nhập tiếp, thì lại set index = texview
        vì textView.setText(jtextfield.getText() ở trên)*/
        if (index > textField.getText().length()) {
            index = textView.getText().length();
        }
        index = textView.getCaretPosition();
        StringBuilder sb = new StringBuilder();
        sb.append(textView.getText());
        sb.insert(index, "5");
        textView.setText(sb.toString());
        textField.setText(sb.toString());
        index = index + 1;
        textView.requestFocus();
        textView.setCaretPosition(index);
    }
}//GEN-LAST:event_btn5ActionPerformed

private void btn6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn6ActionPerformed
    if (textField != null) {
        /**Nếu trước đó đã ấn một nút nào đó rồi ấn nút 1*/
        if (textField.getText().length() < textView.getText().length()) {
            textField.setText(textView.getText());
            index = textView.getCaretPosition();
        }
        /**Nếu chỉ số index hiện tại lớn hơn giá trị được truyền vào,
        trường hợp khi nhập 123456789 rồi, ok có index = 9
        vào một textfield khác chỉ có 123, để nhập tiếp, thì lại set index = texview
        vì textView.setText(jtextfield.getText() ở trên)*/
        if (index > textField.getText().length()) {
            index = textView.getText().length();
        }
        index = textView.getCaretPosition();
        StringBuilder sb = new StringBuilder();
        sb.append(textView.getText());
        sb.insert(index, "6");
        textView.setText(sb.toString());
        textField.setText(sb.toString());
        index = index + 1;
        textView.requestFocus();
        textView.setCaretPosition(index);
    }
}//GEN-LAST:event_btn6ActionPerformed

private void btn7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn7ActionPerformed
    if (textField != null) {
        /**Nếu trước đó đã ấn một nút nào đó rồi ấn nút 1*/
        if (textField.getText().length() < textView.getText().length()) {
            textField.setText(textView.getText());
            index = textView.getCaretPosition();
        }
        /**Nếu chỉ số index hiện tại lớn hơn giá trị được truyền vào,
        trường hợp khi nhập 123456789 rồi, ok có index = 9
        vào một textfield khác chỉ có 123, để nhập tiếp, thì lại set index = texview
        vì textView.setText(jtextfield.getText() ở trên)*/
        if (index > textField.getText().length()) {
            index = textView.getText().length();
        }
        index = textView.getCaretPosition();
        StringBuilder sb = new StringBuilder();
        sb.append(textView.getText());
        sb.insert(index, "7");
        textView.setText(sb.toString());
        textField.setText(sb.toString());
        index = index + 1;
        textView.requestFocus();
        textView.setCaretPosition(index);
    }
}//GEN-LAST:event_btn7ActionPerformed

private void btn8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn8ActionPerformed
    if (textField != null) {
        /**Nếu trước đó đã ấn một nút nào đó rồi ấn nút 1*/
        if (textField.getText().length() < textView.getText().length()) {
            textField.setText(textView.getText());
            index = textView.getCaretPosition();
        }
        /**Nếu chỉ số index hiện tại lớn hơn giá trị được truyền vào,
        trường hợp khi nhập 123456789 rồi, ok có index = 9
        vào một textfield khác chỉ có 123, để nhập tiếp, thì lại set index = texview
        vì textView.setText(jtextfield.getText() ở trên)*/
        if (index > textField.getText().length()) {
            index = textView.getText().length();
        }
        index = textView.getCaretPosition();
        StringBuilder sb = new StringBuilder();
        sb.append(textView.getText());
        sb.insert(index, "8");
        textView.setText(sb.toString());
        textField.setText(sb.toString());
        index = index + 1;
        textView.requestFocus();
        textView.setCaretPosition(index);
    }
}//GEN-LAST:event_btn8ActionPerformed

private void btn9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn9ActionPerformed
    if (textField != null) {
        /**Nếu trước đó đã ấn một nút nào đó rồi ấn nút 1*/
        if (textField.getText().length() < textView.getText().length()) {
            textField.setText(textView.getText());
            index = textView.getCaretPosition();
        }
        /**Nếu chỉ số index hiện tại lớn hơn giá trị được truyền vào,
        trường hợp khi nhập 123456789 rồi, ok có index = 9
        vào một textfield khác chỉ có 123, để nhập tiếp, thì lại set index = texview
        vì textView.setText(jtextfield.getText() ở trên)*/
        if (index > textField.getText().length()) {
            index = textView.getText().length();
        }
        index = textView.getCaretPosition();
        StringBuilder sb = new StringBuilder();
        sb.append(textView.getText());
        sb.insert(index, "9");
        textView.setText(sb.toString());
        textField.setText(sb.toString());
        index = index + 1;
        textView.requestFocus();
        textView.setCaretPosition(index);
    }
}//GEN-LAST:event_btn9ActionPerformed

private void btnDotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDotActionPerformed
    if (textField != null) {
        /**Nếu trước đó đã ấn một nút nào đó rồi ấn nút 1*/
        if (textField.getText().length() < textView.getText().length()) {
            textField.setText(textView.getText());
            index = textView.getCaretPosition();
        }
        /**Nếu chỉ số index hiện tại lớn hơn giá trị được truyền vào,
        trường hợp khi nhập 123456789 rồi, ok có index = 9
        vào một textfield khác chỉ có 123, để nhập tiếp, thì lại set index = texview
        vì textView.setText(jtextfield.getText() ở trên)*/
        if (index > textField.getText().length()) {
            index = textView.getText().length();
        }
        index = textView.getCaretPosition();
        StringBuilder sb = new StringBuilder();
        sb.append(textView.getText());
        sb.insert(index, ".");
        textView.setText(sb.toString());
        textField.setText(sb.toString());
        index = index + 1;
        textView.requestFocus();
        textView.setCaretPosition(index);
    }
}//GEN-LAST:event_btnDotActionPerformed

private void btn0ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn0ActionPerformed
    if (textField != null) {
        /**Nếu trước đó đã ấn một nút nào đó rồi ấn nút 1, nghĩ lại thì cái này cũng ko xẩy ra
        vì cuối cùng cũng thực hiện
        textView.setText(sb.toString());
        textField.setText(sb.toString());*/
        if (textField.getText().length() < textView.getText().length()) {
            textField.setText(textView.getText());
            index = textView.getCaretPosition();
        }
        /**Nếu chỉ số index hiện tại lớn hơn giá trị được truyền vào,
        trường hợp khi nhập 123456789 rồi, ok có index = 9
        vào một textfield khác chỉ có 123, để nhập tiếp, thì lại set index = texview
        vì textView.setText(jtextfield.getText() ở trên)*/
        if (index > textField.getText().length()) {
            index = textView.getText().length();
        }
        index = textView.getCaretPosition();
        StringBuilder sb = new StringBuilder();
        sb.append(textView.getText());
        sb.insert(index, "0");
        textView.setText(sb.toString());
        textField.setText(sb.toString());
        index = index + 1;
        textView.requestFocus();
        textView.setCaretPosition(index);
    }
}//GEN-LAST:event_btn0ActionPerformed

private void btnCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCActionPerformed
    actionCanel();
}//GEN-LAST:event_btnCActionPerformed

private void btnokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnokActionPerformed
    javax.swing.SwingUtilities.getWindowAncestor(PanelKeypadLong.this).dispose();
}//GEN-LAST:event_btnokActionPerformed

private void btnPreviousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreviousActionPerformed
    actionLeft();

}//GEN-LAST:event_btnPreviousActionPerformed

private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
    actionRight();

}//GEN-LAST:event_btnNextActionPerformed

private void btnPreviousMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPreviousMousePressed
    timerLeft.start();
}//GEN-LAST:event_btnPreviousMousePressed

private void btnPreviousMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPreviousMouseReleased
    timerLeft.stop();
}//GEN-LAST:event_btnPreviousMouseReleased

private void btnNextMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNextMousePressed
    timerRight.start();
}//GEN-LAST:event_btnNextMousePressed

private void btnNextMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNextMouseReleased
    timerRight.stop();
}//GEN-LAST:event_btnNextMouseReleased

private void btnCMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCMousePressed
    timerCancel.start();
}//GEN-LAST:event_btnCMousePressed

private void btnCMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCMouseReleased
    timerCancel.stop();
}//GEN-LAST:event_btnCMouseReleased
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn0;
    private javax.swing.JButton btn1;
    private javax.swing.JButton btn2;
    private javax.swing.JButton btn3;
    private javax.swing.JButton btn4;
    private javax.swing.JButton btn5;
    private javax.swing.JButton btn6;
    private javax.swing.JButton btn7;
    private javax.swing.JButton btn8;
    private javax.swing.JButton btn9;
    private javax.swing.JButton btnC;
    private javax.swing.JButton btnDot;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrevious;
    private javax.swing.JButton btnok;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private PanelBackground panelBackground1;
    private javax.swing.JTextField textView;
    // End of variables declaration//GEN-END:variables

    /**Truyền index của phần tử cần xét vào*/
    
    public void setIndex(int index) {
        this.index = index;
    }

    /**Tooltip*/
    
    public void setToolTip(String toolTipText) {
        this.toolTipText = toolTipText;
    }

    /**Show all là sao*/
    
    public void setShowAll(boolean flag) {
        this.flagAll = flag;
    }

    /**show sale là sao*/
    
    public void setShowSale(boolean flag) {
        this.flagSale = flag;
    }

    /**Kiểm tra show all là sao*/
    
    public boolean isShowAll() {
        return flagAll;
    }

    /**kiểm tra show sale*/
    
    public boolean isShowSale() {
        return flagSale;
    }

    /**Hàm này làm cái j*/
    
    public void loadKeyboard() {
        listKeyboard = new ArrayList<String>();
        try {
            for (String string : readKeyboard()) {
                listKeyboard.add(string);
            }
        } catch (Exception e) {
        }
        if (listKeyboard.isEmpty()) {
            listKeyboard.add("0");
            listKeyboard.add("All");
        }
        if (listKeyboard.get(0).equals("1")) {
            if (listKeyboard.get(1).equals("All")) {
                flagAll = true;
            } else {
                flagAll = false;
                flagSale = true;
            }
        } else {
            flagAll = false;
            flagSale = false;
        }
    }

    /**ghi đối tượng String vào đường dẫn file url/data*/
    public void writeKeyboard(List<String> listAccount) throws FileNotFoundException, IOException {
        FileOutputStream os = new FileOutputStream(url + "/" + data);
        ObjectOutputStream output = new ObjectOutputStream(os);
        for (String string : listAccount) {
            output.writeObject(string);
            output.reset();
        }
        output.close();
        os.close();;
    }

    /**Đọc đói tượng String từ đường dẫn file url/data*/
    public List<String> readKeyboard() throws FileNotFoundException, IOException {
        List<String> listAccount = new ArrayList<String>();
        FileInputStream istream = new FileInputStream(url + "/" + data);
        ObjectInputStream input = new ObjectInputStream(istream);
        try {
            while (istream.available() != -1) {
                String acc = input.readObject().toString();
                listAccount.add(acc);
            }
        } catch (Exception e) {
        }
        input.close();
        istream.close();
        return listAccount;
    }

    
    public void setTextField(JTextField textField) {
        this.textField = textField;
        textView.setText(textField.getText());
        textView.setCaretPosition(index);
    }

    private static class RoundedBorder implements Border {

        private int radius;

        RoundedBorder(int radius) {
            this.radius = radius;
        }

        
        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius + 1, this.radius + 1, this.radius + 2, this.radius);
        }

        
        public boolean isBorderOpaque() {
            return true;
        }

        
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }
    }

    private void actionCanel() {
        if (textField != null) {
            /**Trường hợp clear này không xẩy ra
            vì ban đầu đã set this.textField = textfield && textView.setText(textfield.getText())*/
            if (textField.getText().length() < textView.getText().length()) {
                textField.setText(textView.getText());
                index = textView.getCaretPosition();
            }
            if (index > textView.getText().length()) {
                index = textView.getText().length();
            }
            if (index == 0) {
                return;
            }
            index = textView.getCaretPosition();
            StringBuilder builder = new StringBuilder();
            builder.append(textView.getText());
            builder.deleteCharAt(index - 1);
            textView.setText(builder.toString());
            textField.setText(builder.toString());
            builder = null;
            textView.requestFocus();
            textView.setCaretPosition(index - 1);
            index = index - 1;
        }
    }

    private void actionRight() {
        if (textField != null) {
            if (index == textView.getText().length()) {
                return;
            } else {
                index = textView.getCaretPosition();
                index = index + 1;
                textField.setCaretPosition(index);
                textView.requestFocus();
                textView.setCaretPosition(index);
            }
        }
    }

    private void actionLeft() {
        if (textField != null) {
            if (index == 0) {
                return;
            } else {
                index = textView.getCaretPosition();
                index = index - 1;
                textField.setCaretPosition(index);
                textView.requestFocus();
                textView.setCaretPosition(index);
            }
        }
    }
}
