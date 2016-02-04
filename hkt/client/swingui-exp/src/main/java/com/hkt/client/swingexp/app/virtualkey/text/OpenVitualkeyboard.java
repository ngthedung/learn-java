/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkt.client.swingexp.app.virtualkey.text;

import java.awt.Toolkit;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.hkt.client.swingexp.app.virtualkey.number.Keypad;

/**
 *
 * @author Zero
 */
public class OpenVitualkeyboard {

    private static OpenVitualkeyboard numberVitualkeyboard;
    private Keypad iNumberKeyboard;
    private TextKeyboard iTextKeyboard;
    private DialogTextKeyboard iDialogTextKeyboard;
    private int index = 0;

    public OpenVitualkeyboard() {
        iNumberKeyboard = new Keypad();
        iTextKeyboard = new TextKeyboard();
        iDialogTextKeyboard = new DialogTextKeyboard();
    }

    public static OpenVitualkeyboard getInstancce() {
        if (numberVitualkeyboard == null) {
            numberVitualkeyboard = new OpenVitualkeyboard();
        }
        return numberVitualkeyboard;
    }

    public void vitualNumberKeyboard(JTextField textField) {
        if (iNumberKeyboard.isShowAll()) {
            String toolTip = textField.getToolTipText();
            iNumberKeyboard.setToolTip(toolTip);
            int h = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
            int k = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
            int x, y;
            x = (int) textField.getLocationOnScreen().getX();
            y = (int) textField.getLocationOnScreen().getY();
            if (h < y + 400 + textField.getHeight()) {
                y = (int) textField.getLocationOnScreen().getY() - 400;
            } else {
                y = y + textField.getHeight();
            }
            if (k < x + 245) {

                x = x + textField.getWidth() - 245;
            }
            ((JDialog) iNumberKeyboard).setLocation(x, y);
            ((JDialog) iNumberKeyboard).setTitle(toolTip);
            iNumberKeyboard.setTextField(textField);
            iNumberKeyboard.setIndex(index);
            ((JDialog) iNumberKeyboard).setVisible(true);
        } else {
           // if (iNumberKeyboard.isShowSale()) {
                String toolTip = textField.getToolTipText();
                iNumberKeyboard.setToolTip(toolTip);
                int h = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
                int k = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
                int x, y;
                x = (int) textField.getLocationOnScreen().getX();
                y = (int) textField.getLocationOnScreen().getY();
                if (h < y + 400 + textField.getHeight()) {
                    y = (int) textField.getLocationOnScreen().getY() - 400;
                } else {
                    y = y + textField.getHeight();
                }
                if (k < x + 245) {

                    x = x + textField.getWidth() - 245;
                }
                ((JDialog) iNumberKeyboard).setLocation(x, y);
                ((JDialog) iNumberKeyboard).setTitle(toolTip);
                index = textField.getCaretPosition();
                if (index > textField.getText().length()) {
                    index = textField.getCaretPosition();
                }
                iNumberKeyboard.setIndex(index);
                iNumberKeyboard.setTextField(textField);
                ((JDialog) iNumberKeyboard).setVisible(true);
            //}
        }
    }

    public void vitualTextKeyboard(JTextField textField) {
          //  if (iTextKeyboard.isShowSale()) {
                String toolTip = textField.getToolTipText();
                iTextKeyboard.setToolTip(toolTip);
                int h = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
                int k = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
                int x, y;
                x = (int) textField.getLocationOnScreen().getX();
                y = (int) textField.getLocationOnScreen().getY();
                if (h < y + 330 + textField.getHeight()) {
                    y = (int) textField.getLocationOnScreen().getY() - 330;
                } else {
                    y = y + textField.getHeight();
                }
                if (k < x + 655) {

                    x = x + textField.getWidth() - 655;
                }
                ((JDialog) iTextKeyboard).setLocation(x, y);
                ((JDialog) iTextKeyboard).setTitle(toolTip);
                index = textField.getCaretPosition();
                if (index > textField.getText().length()) {
                    index = textField.getCaretPosition();
                }
                iTextKeyboard.setTypeKeyboard(0);
                iTextKeyboard.setIndex(index);
                iTextKeyboard.setTextField(textField);
                ((JDialog) iTextKeyboard).setSize(760, 270);
                ((JDialog) iTextKeyboard).setVisible(true);
           // }
    }

    public void vitualTextAreaKeyboard(JTextArea textField) {
      //  if (iTextKeyboard.isShowSale()) {
      String toolTip = textField.getToolTipText();
      iTextKeyboard.setToolTip(toolTip);
      int h = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
      int k = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
      int x, y;
      x = (int) textField.getLocationOnScreen().getX();
      y = (int) textField.getLocationOnScreen().getY();
      if (h < y + 330 + textField.getHeight()) {
          y = (int) textField.getLocationOnScreen().getY() - 330;
      } else {
          y = y + textField.getHeight();
      }
      if (k < x + 655) {

          x = x + textField.getWidth() - 655;
      }
      ((JDialog) iTextKeyboard).setLocation(x, y);
      ((JDialog) iTextKeyboard).setTitle(toolTip);
      index = textField.getCaretPosition();
      if (index > textField.getText().length()) {
          index = textField.getCaretPosition();
      }
      iTextKeyboard.setTypeKeyboard(0);
      iTextKeyboard.setIndex(index);
      iTextKeyboard.setTextAria(textField);
      ((JDialog) iTextKeyboard).setSize(760, 270);
      ((JDialog) iTextKeyboard).setVisible(true);
 // }
    }

    public void vitualTextTableKeyboard(JTable jTable, int rowSelected) {
        if (iTextKeyboard.isShowAll()) {
            int h = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
            int k = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
            int x, y;
            x = (int) jTable.getLocationOnScreen().getX();
            y = (int) jTable.getLocationOnScreen().getY();
            if (h < y + 330 + jTable.getHeight()) {
                y = (int) jTable.getLocationOnScreen().getY() - 330;
            } else {
                y = y + jTable.getHeight();
            }
            if (k < x + 655) {

                x = x + jTable.getWidth() - 655;
            }
            ((JDialog) iTextKeyboard).setLocation(x, y);

            String toolTip = jTable.getToolTipText();
            rowSelected = jTable.getSelectedRow();
            if (rowSelected >= 0) {
                iTextKeyboard.setTable(jTable, rowSelected);
                iTextKeyboard.setToolTip(toolTip);
                iTextKeyboard.setTypeKeyboard(0);
                ((JDialog) iTextKeyboard).setTitle(toolTip);
                ((JDialog) iTextKeyboard).setSize(655, 330);
                ((JDialog) iTextKeyboard).setVisible(true);
            }
        } else {
            if (iTextKeyboard.isShowSale()) {
                String toolTip = jTable.getToolTipText();
                iTextKeyboard.setToolTip(toolTip);
                int h = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
                int k = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
                int x, y;
                x = (int) jTable.getLocationOnScreen().getX();
                y = (int) jTable.getLocationOnScreen().getY();
                if (h < y + 330 + jTable.getHeight()) {
                    y = (int) jTable.getLocationOnScreen().getY() - 330;
                } else {
                    y = y + jTable.getHeight();
                }
                if (k < x + 655) {

                    x = x + jTable.getWidth() - 655;
                }
                ((JDialog) iTextKeyboard).setLocation(x, y);
                ((JDialog) iTextKeyboard).setTitle(toolTip);
                rowSelected = jTable.getSelectedRow();
                if (rowSelected >= 0) {
                    iTextKeyboard.setTable(jTable, rowSelected);
                    iTextKeyboard.setToolTip(toolTip);
                    iTextKeyboard.setTypeKeyboard(0);
                    ((JDialog) iTextKeyboard).setTitle(toolTip);
                    ((JDialog) iTextKeyboard).setSize(655, 330);
                    ((JDialog) iTextKeyboard).setVisible(true);
                }
            }
        }

    }

    public void OpenDialogVitualKeyboard(JComponent component) {
        if (iDialogTextKeyboard.isShowAll()) {
            int h = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
            int k = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
            int x, y;
            x = (int) component.getLocationOnScreen().getX();
            y = (int) component.getLocationOnScreen().getY();
            y = h - 275;
            x = 0;
            ((JDialog) iDialogTextKeyboard).setLocation(x, y);

            String toolTip = component.getToolTipText();
            if (component instanceof JTextField) {
                index = ((JTextField) component).getCaretPosition();
                if (index > ((JTextField) component).getText().length()) {
                    index = ((JTextField) component).getCaretPosition();
                }
                iDialogTextKeyboard.setTextField((JTextField) component);
                iDialogTextKeyboard.setIndex(index);
                iDialogTextKeyboard.setToolTip(toolTip);
                iDialogTextKeyboard.setTypeKeyboard(0);
                ((JDialog) iDialogTextKeyboard).setTitle(toolTip);
                ((JDialog) iDialogTextKeyboard).setSize(k, 300);
                ((JDialog) iDialogTextKeyboard).setVisible(true);
            } else if (component instanceof JTextArea) {
                index = ((JTextArea) component).getCaretPosition();
                if (index > ((JTextArea) component).getText().length()) {
                    index = ((JTextArea) component).getCaretPosition();
                }
                iDialogTextKeyboard.setTextAria((JTextArea) component);
                iDialogTextKeyboard.setIndex(index);
                iDialogTextKeyboard.setToolTip(toolTip);
                iDialogTextKeyboard.setTypeKeyboard(0);
                ((JDialog) iDialogTextKeyboard).setTitle(toolTip);
                ((JDialog) iDialogTextKeyboard).setVisible(true);
            } else if (component instanceof JTable) {
                int rowSelected = ((JTable) component).getSelectedRow();
                if (rowSelected > 0) {
                    iDialogTextKeyboard.setTable((JTable) component, rowSelected);
                    iDialogTextKeyboard.setToolTip(toolTip);
                    iDialogTextKeyboard.setTypeKeyboard(0);
                    ((JDialog) iDialogTextKeyboard).setTitle(toolTip);
                    ((JDialog) iDialogTextKeyboard).setVisible(true);
                }
            }
        } else if (iDialogTextKeyboard.isShowSale()) {
            int h = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
            int k = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
            int x, y;
            x = (int) component.getLocationOnScreen().getX();
            y = (int) component.getLocationOnScreen().getY();
            y = h - 330;
            x = (k - 866) / 2;
            ((JDialog) iDialogTextKeyboard).setLocation(x, y);

            String toolTip = component.getToolTipText();
            if (component instanceof JTextField) {
                index = ((JTextField) component).getCaretPosition();
                if (index > ((JTextField) component).getText().length()) {
                    index = ((JTextField) component).getCaretPosition();
                }
                iDialogTextKeyboard.setTextField((JTextField) component);
                iDialogTextKeyboard.setIndex(index);
                iDialogTextKeyboard.setToolTip(toolTip);
                iDialogTextKeyboard.setTypeKeyboard(0);
                ((JDialog) iDialogTextKeyboard).setTitle(toolTip);
                ((JDialog) iDialogTextKeyboard).setVisible(true);
            } else if (component instanceof JTextArea) {
                index = ((JTextArea) component).getCaretPosition();
                if (index > ((JTextArea) component).getText().length()) {
                    index = ((JTextArea) component).getCaretPosition();
                }
                iDialogTextKeyboard.setTextAria((JTextArea) component);
                iDialogTextKeyboard.setIndex(index);
                iDialogTextKeyboard.setToolTip(toolTip);
                iDialogTextKeyboard.setTypeKeyboard(0);
                ((JDialog) iDialogTextKeyboard).setTitle(toolTip);
                ((JDialog) iDialogTextKeyboard).setVisible(true);
            } else if (component instanceof JTable) {
                int rowSelected = ((JTable) component).getSelectedRow();
                if (rowSelected > 0) {
                    iDialogTextKeyboard.setTable((JTable) component, rowSelected);
                    iDialogTextKeyboard.setToolTip(toolTip);
                    iDialogTextKeyboard.setTypeKeyboard(0);
                    ((JDialog) iDialogTextKeyboard).setTitle(toolTip);
                    ((JDialog) iDialogTextKeyboard).setVisible(true);
                }
            }
        }
    }
}
