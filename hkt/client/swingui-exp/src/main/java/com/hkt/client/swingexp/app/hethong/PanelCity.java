package com.hkt.client.swingexp.app.hethong;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import com.hkt.client.swingexp.app.component.ImageTool;
import com.hkt.client.swingexp.app.component.MyPanel;
import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.DialogTest;
import com.hkt.client.swingexp.app.core.IDialogMain;
import com.hkt.client.swingexp.app.core.MyGroupLayout;
import com.hkt.client.swingexp.model.LocaleModelManager;
import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.module.account.entity.AccountMembership.Capability;
import com.hkt.module.config.locale.City;
import com.hkt.module.config.locale.CityImage;
import com.hkt.module.config.locale.Country;
import com.hkt.module.config.locale.CountryImage;
import com.hkt.util.text.DateUtil;

@SuppressWarnings("serial")
public class PanelCity extends MyPanel implements IDialogMain {

  private JLabel lbCode, lbName, lbCountry, lbDescription, lbMessenger, lbImage;
  private JTextField txtName, txtCode, txtDescription;
  @SuppressWarnings("rawtypes")
  private JComboBox cbCountry;
  private JScrollPane scrollPaneTable, scrollIndex;
  private CityImage cityimage;
  private CityTable cityTable;
  private City city;
  @SuppressWarnings("unused")
  private int index = 0;
  private Country country;
  private List<Country> listCountry = new ArrayList<Country>();
  private boolean flags = true;
  public static String permission;

  public PanelCity() throws Exception {
    init();
    setOpaque(false);
    reset();
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  private void init() throws Exception {
    city = new City();
    lbMessenger = new JLabel("");
    scrollPaneTable = new JScrollPane();
    scrollPaneTable.setOpaque(false);
    scrollPaneTable.getViewport().setOpaque(false);
    scrollPaneTable.setBorder(null);

    cityTable = new CityTable();
    cityTable.setName("tbcityTable");

    cityTable.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent event) {

        try {
          tableOptionsMouseClicked(event);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }

    });

    cityTable.setPreferredScrollableViewportSize(new Dimension(200, 290));
    scrollPaneTable.setViewportView(cityTable);

    lbCode = new JLabel("Mã");
    lbName = new JLabel("Thành Phố");
    lbCountry = new JLabel("Quốc gia");
    lbDescription = new JLabel("Miêu tả");

    txtCode = new JTextField();
    txtCode.setName("txtCode");
    txtCode.setDisabledTextColor(Color.black);

    txtName = new JTextField();
    txtName.setName("txtName");
    txtName.setDisabledTextColor(Color.black);

    cbCountry = new JComboBox();
    cbCountry.setName("cbCountry");
    cbCountry.setBackground(Color.white);
    //
    cbCountry.addMouseListener(new MouseAdapter() {
    	@Override
    	public void mouseClicked(MouseEvent e) {
    		if(e.getButton() == MouseEvent.BUTTON3)
    		{
				PanelCountry pnlCountry;
				
				try {
					pnlCountry = new PanelCountry();
					pnlCountry.setName("QuocGia");
					DialogMain dialog = new DialogMain(pnlCountry);
					dialog.setIcon("country1.png");
					dialog.setTitle("Quốc gia");
					dialog.setName("dlQuocGia");
					dialog.setModal(true);
					dialog.setVisible(true);
					try {
						cbCountry.setModel(new DefaultComboBoxModel(LocaleModelManager.getInstance().getCountries().toArray()));
					} catch (Exception e1) {
						cbCountry.setModel(new DefaultComboBoxModel());
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
//				cbCountry.setModel(new DefaultComboBoxModel(listCountry.toArray()));
    		}
    		
    	}
	});

    listCountry = LocaleModelManager.getInstance().getCountries();
    cbCountry.setModel(new DefaultComboBoxModel(listCountry.toArray()));

    // Set màu khi disable comboBox
    UIManager.put("ComboBox.disabledBackground", Color.WHITE);
    UIManager.put("ComboBox.disabledForeground", new Color(51, 51, 51));

    if (getCountries().size() > 0) {
      cbCountry.setSelectedIndex(0);
    } else
      cbCountry.setSelectedItem("");
    
    // Event Choose ComboBox ( Chon quốc gia nào thì Load nên Các thành phố
    // của
    // quốc gia ấy )
    cbCountry.addItemListener(new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent e) {
        try {

          country = ((Country) cbCountry.getSelectedItem());
          if (country.getCities() == null) {
            country.setCities(new ArrayList<City>());
          }
          try {
            String image = country.getCountryImage().get("image").toString();
            if (image != null) {
              ImageIcon icon = ImageTool.getInstance().decodeToImage(image);
              lbImage.setText("");
              lbImage.setIcon(icon);
            } else {
              lbImage.setIcon(null);
              lbImage.setText("Quốc kỳ");
            }
          } catch (Exception e1) {
            lbImage.setIcon(null);
            lbImage.setText("Quốc kỳ");
          }
          loadTable();
        } catch (Exception e1) {
          e1.printStackTrace();
        }
      }
    });

    country = ((Country) cbCountry.getSelectedItem());
    if (country == null) {
      country = new Country();
    } else {
      if (country.getCities() == null) {
        country.setCities(new ArrayList<City>());
      }
    }

    txtDescription = new JTextField();
    txtDescription.setName("txtDescription");
    txtDescription.setDisabledTextColor(Color.black);

    MyGroupLayout layout = new MyGroupLayout(this);

    JPanel panelQuocKy = new JPanel();
    panelQuocKy.setOpaque(false);
    panelQuocKy.setLayout(new GridLayout());
    lbImage = new JLabel("Quốc kỳ", SwingConstants.CENTER);
    lbImage.setBorder(BorderFactory.createEtchedBorder());
    panelQuocKy.add(lbImage);
    layout.add(0, 0, lbCode);
    layout.add(0, 1, txtCode);
    layout.add(1, 0, lbName);
    layout.add(1, 1, txtName);
    layout.add(2, 0, lbCountry);
    layout.add(2, 1, cbCountry);
    layout.addImage(lbImage);
    layout.add(3, 0, lbDescription);
    layout.add(3, 1, txtDescription);
    layout.addMessenger(lbMessenger);

    // Thêm button cập nhật index
    scrollIndex = new JScrollPane();
    scrollIndex.setOpaque(false);
    scrollIndex.getViewport().setOpaque(false);
    scrollIndex.setBorder(null);
    scrollIndex.setViewportView(cityTable.getPanleButton());

    // Giao diện Button cập nhật index đi cùng với scrollPaneTable
    layout.add(4, 0, scrollIndex);
    layout.add(4, 1, scrollPaneTable);
    layout.updateGui();

  }

  protected void tableOptionsMouseClicked(MouseEvent event) throws Exception {
    city = cityTable.getSelectedBean();
    int click = 2;
    if (city.getName().indexOf("HktTest") > 0 && event.getButton() == 3) {
      click = 1;
    }
    if (event.getClickCount() >= click) {
      setData();
      index = cityTable.getSelectedRow();
      setEnableCompoment(false);
      ((DialogMain) getRootPane().getParent()).showButton(false);
    }
    refresh();
  }

  public void setObject(City object) throws Exception {
    this.city = object;
    if (object == null) {
      city = new City();
    }
    setData();
  }

  private boolean getData() {
    try {
      if (city == null) {
        city = new City();
        city.getIndex();
      } else {
        if (!cityTable.isEdit()) {
          city.setIndex(cityTable.getBeans().indexOf(city));
        }
      }

      @SuppressWarnings("unused")
      CountryImage image = country.getCountryImage();
      if (cityimage == null) {
        cityimage = new CityImage();
      } else {
        cityimage.put("image", null);
        city.setCityImage(cityimage);
      }
      city.setName(txtName.getText());
      city.setCode(txtCode.getText());
      city.setDescription(txtDescription.getText());
      city.setIndex(country.getCities().size() + 1);
      lbMessenger.setText("");
      return true;
    } catch (Exception e) {
      lbMessenger.setText("Lỗi định dạng dữ liệu");
      return false;
    }
  }

  private void setData() {
    try {
      txtCode.setText(city.getCode());
      txtName.setText(city.getName());
      txtDescription.setText(city.getDescription());
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  private boolean checkData() {

    getData();
    country = (Country) cbCountry.getSelectedItem();
    if (country == null) {
      lbCountry.setForeground(Color.red);
      lbMessenger.setText("Dữ liệu vùng đỏ bị lỗi");
      return false;
    } else {
      lbCountry.setForeground(Color.black);
    }
    boolean check = true;

    if (city.getCode().trim().equals("")) {
      lbCode.setForeground(Color.red);
      check = false;
    } else {
      lbCode.setForeground(Color.black);
    }
    if (city.getName().trim().equals("")) {
      lbName.setForeground(Color.red);
      check = false;
    } else {
      lbName.setForeground(Color.black);
    }

    // Kiểm tra xem có bị trùng code hay không
    if (txtCode.isEnabled()) {
      try {
        List<City> cities = new ArrayList<City>();
        cities = country.getCities();
        for (int i = 0; i < cities.size(); i++) {
          if (cities.get(i).getCode().toLowerCase().equals(txtCode.getText().toLowerCase())) {
            check = false;
            lbCode.setForeground(Color.red);
          }
        }
      } catch (Exception e) {
      }
    }

    if (!check) {
      lbMessenger.setText("Dữ liệu vùng đỏ bị lỗi");
      lbMessenger.setForeground(Color.red);
      return false;
    } else {
      lbMessenger.setText("");
      return true;
    }
  }

  @Override
  public void save() throws Exception {
    if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.READ) {
      JOptionPane.showMessageDialog(null, "Bạn chưa được phân quyền này");
    } else {
      if (!checkData()) {
        return;
      }

      if (flags == true) {
        country.getCities().add(city);

      }
      if (country != null) {
        LocaleModelManager.getInstance().saveCountry(country);
        reset();
      }
    }

  }

  @Override
  public void edit() throws Exception {
    if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.READ) {
      JOptionPane.showMessageDialog(null, "Bạn chưa được phân quyền này !");
    } else {

      setEnableCompoment(true);
      txtCode.setEnabled(false);
      cbCountry.setEnabled(false);
      flags = false;
    }

  }

  @Override
  public void delete() throws Exception {
    if (UIConfigModelManager.getInstance().getPermission(permission) == Capability.ADMIN) {

      @SuppressWarnings("unused")
      boolean flag = false;
      for (int i = 0; i < country.getCities().size(); i++) {
        if (city.getName().equals(city.getName())) {
          flag = true;
        }
      }
      if (flag = true) {

        String str = "Xóa thành phố ";
        PanelChoise pnPanel = new PanelChoise(str + city + "?");
        pnPanel.setName("Xoa");
        DialogResto dialog1 = new DialogResto(pnPanel, false, 0, 80);
        dialog1.setName("dlXoa");
        dialog1.setTitle("Xóa thành phố");
        dialog1.setLocationRelativeTo(null);
        dialog1.setModal(true);
        dialog1.setVisible(true);

        if (pnPanel.isDelete()) {
          country.getCities().remove(city);
          LocaleModelManager.getInstance().saveCountry(country);
          lbMessenger.setText("");
          reset();
          flags = true;
        } else if (pnPanel.isDelete() == false) {
          reset();
        } else {
          lbMessenger.setText("Hãy chắc chắn là bạn đã chọn 1 thành phố để xóa");
          return;
        }
      }
    } else {
        JOptionPane.showMessageDialog(null, "Bạn chưa được phân quyền này !");
        return;
      }
  }

  @Override
  public void reset() throws Exception {
    try {
      String image = country.getCountryImage().get("image").toString();
      if (image != null) {
        ImageIcon icon = ImageTool.getInstance().decodeToImage(image);
        lbImage.setText("");
        lbImage.setIcon(icon);
      } else {
        lbImage.setIcon(null);
        lbImage.setText("Quốc kỳ");
      }
    } catch (Exception e1) {
      lbImage.setIcon(null);
      lbImage.setText("Quốc kỳ");
    }
    setEnableCompoment(true);
    txtCode.setText(DateUtil.asCompactDateTimeId(new Date()));
    txtName.setText("");
    txtDescription.setText("");
    lbCode.setForeground(Color.black);
    lbName.setForeground(Color.black);
    lbCountry.setForeground(Color.black);
    lbDescription.setForeground(Color.black);

    lbMessenger.setText(" ");
    city = new City();
    flags = true;
    loadTable();

  }

  @Override
  public void refresh() throws Exception {
    setData();
    setEnableCompoment(true);
    lbCode.setForeground(Color.black);
    lbName.setForeground(Color.black);
    lbCountry.setForeground(Color.black);
    lbDescription.setForeground(Color.black);

    lbMessenger.setText(" ");
  }

  public List<Country> getCountries() throws Exception {
    return LocaleModelManager.getInstance().getCountries();
  }

  private void setEnableCompoment(boolean value) {

    txtCode.setEnabled(value);
    txtName.setEnabled(value);
    txtDescription.setEnabled(value);
    cbCountry.setEnabled(value);
  }

  private void loadTable() throws Exception {

    if (country.getCities() != null && country.getCities().size() > 0) {
      cityTable.setCitiess(country);
    } else {
      country.setCities(new ArrayList<City>());
      cityTable.setCitiess(country);
    }
  }

  @Override
  public void deleteDataTest() {
    if (!PanelTestAll.runAll) {
      DialogTest.getInstance().show("Xóa toàn bộ dữ liệu test ?");
      if (DialogTest.getInstance().isTest()) {
        try {
          LocaleModelManager.getInstance().deleteTest("HktTest");
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
  }

}
