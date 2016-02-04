
package com.hkt.client.swingexp.app.khohang.list;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import com.hkt.client.swingexp.app.component.ExtendJComboBox;
import com.hkt.client.swingexp.app.component.ExtendJTextField;
import com.hkt.client.swingexp.app.component.LabelIconSearch;
import com.hkt.client.swingexp.app.component.LabelTextSearch;
import com.hkt.client.swingexp.app.component.TableFillterSorter;
import com.hkt.client.swingexp.app.core.IPanelShowList;
import com.hkt.client.swingexp.app.khohang.RecipeTable;
import com.hkt.client.swingexp.app.print.ReportGeneral;
import com.hkt.module.restaurant.entity.Recipe;

public class PanelRecipeList extends JPanel implements IPanelShowList {
  
  private JScrollPane scrollPaneTable;
  private RecipeTable recipeTable;
  
  private Recipe recipe;
  private List<Recipe> recipes;
  
  public PanelRecipeList() {
    setLayout(new BorderLayout());
    addKeyBindings(PanelRecipeList.this);
    
    setSize(Toolkit.getDefaultToolkit().getScreenSize());
    setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, null, new Color(204,
        204, 204), null, null), "Danh sách khai báo định lương", TitledBorder.DEFAULT_JUSTIFICATION,
        TitledBorder.DEFAULT_POSITION, new Font("Tahoma", 1, 14), new Color(126, 0, 0)));
    this.setBackground(new Color(255, 255, 255));
    
    scrollPaneTable = new JScrollPane();
    scrollPaneTable.getViewport().setBackground(Color.white);
    
    recipe = new Recipe();
    recipes = new ArrayList<Recipe>();
    recipes.add(recipe);
    
    recipeTable = new RecipeTable();
    recipeTable.setRecipes(recipes);
    recipeTable.addMouseListener(new MouseAdapter() {
      
      public void mouseClicked(MouseEvent event) {
        try {
          recipeTableMouseClicked(event);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
    scrollPaneTable.setViewportView(recipeTable);
    
    add(new HeaderDanhSach(recipeTable), BorderLayout.PAGE_START);
    add(scrollPaneTable, BorderLayout.CENTER);
  }
  
  protected void recipeTableMouseClicked(MouseEvent event) {
    if (recipeTable.getSelectedRow() < 0) { return; }
    if (event.getClickCount() >= 2) {
      recipe = recipeTable.getSelectedBean();
    }
  }
  
  @Override
  public void print() {
    printData();
  }
  
  @Override
  public void loadPanel() throws Exception {
    
  }
  
  public class HeaderDanhSach extends JPanel {
    
    private JTextField txtSearch;
    private JComboBox cbSearch;
    private String strSearch;
    private TableFillterSorter tableFillterSorter;
    private boolean firstClick = false;
    private RecipeTable recipeTable;
    
    public HeaderDanhSach(RecipeTable recipeTable) {
      
      this.recipeTable = recipeTable;
      setOpaque(false);
      setLayout(new BorderLayout());
      setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
      
      txtSearch = new ExtendJTextField(20);
      txtSearch.setPreferredSize(new Dimension(100, 25));
      txtSearch.addCaretListener(new CaretListener() {
        
        public void caretUpdate(CaretEvent evt) {
          txtSearchCaretUpdate(evt);
        }
      });
      
      tableFillterSorter = new TableFillterSorter(recipeTable);
      tableFillterSorter.createTableSorter();
      tableFillterSorter.createTableFillter();
      
      cbSearch = new ExtendJComboBox();
      cbSearch.setPreferredSize(new Dimension(200, 25));
      cbSearch.setModel(new DefaultComboBoxModel(tableFillterSorter.getHashtable().keySet().toArray()));
      cbSearch.addItemListener(new ItemListener() {
        
        public void itemStateChanged(ItemEvent evt) {
          cbSearchItemStateChanged(evt);
        }
      });
      
      add(new LabelIconSearch());
      add(txtSearch);
      add(new LabelTextSearch("Theo"));
      add(cbSearch);
      
    }
    
    protected void cbSearchItemStateChanged(ItemEvent evt) {
      strSearch = cbSearch.getSelectedItem().toString();
      searchData();
    }
    
    protected void txtSearchCaretUpdate(CaretEvent evt) {
      if (!firstClick) {
        firstClick = true;
        txtSearch.setText("");
      }
      searchData();
    }
    
    private void searchData() {
      tableFillterSorter.fillter(txtSearch.getText(), cbSearch.getSelectedItem().toString());
    }
    
  }
  
  private void addKeyBindings(JComponent jc) {
    jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0, false), "F12");
    jc.getActionMap().put("F12", new AbstractAction() {
      
      @Override
      public void actionPerformed(ActionEvent ae) {
        printData();
      }
    });
  }
  
  public void printData() {
    try {
      ReportGeneral report = new ReportGeneral();
      report.setTable(recipeTable);
      report.setEnterprise("HKT");
      report.setAddress("D1T9, Tầng 9, Tòa nhà TopCare, 335 Cầu Giấy, Hà Nội");
      report.setLogoEnterprise(new ImageIcon());
      report.setReportName("Danh sách phòng ban");
      report.setVisible(true);
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Chức năng in lỗi:");
      e.printStackTrace();
    }
  }
  
}
