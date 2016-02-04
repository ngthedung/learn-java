package com.hkt.client.swingexp.app.khachhang;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import com.hkt.client.swingexp.app.component.ExtendJButton;
import com.hkt.client.swingexp.app.component.ExtendJComboBox;
import com.hkt.client.swingexp.app.component.ExtendJLabel;
import com.hkt.client.swingexp.app.component.ExtendJTextField;
import com.hkt.client.swingexp.app.component.MyPanel;
import com.hkt.client.swingexp.app.component.TableFillterSorter;
import com.hkt.client.swingexp.app.core.DialogNotice;
import com.hkt.client.swingexp.app.core.DialogTest;
import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.client.swingexp.app.core.ManagerAuthenticate;
import com.hkt.client.swingexp.app.core.MouseEventClickButtonDialog;
import com.hkt.client.swingexp.app.hethong.PanelTestAll;
import com.hkt.client.swingexp.app.khachhang.list.TableListModelCustomer;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.client.swingexp.model.CustomerModelManager;
import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.module.account.entity.Account;
import com.hkt.module.account.entity.AccountGroup;
import com.hkt.module.account.entity.AccountMembership;
import com.hkt.module.account.entity.AccountMembership.Capability;
import com.hkt.module.accounting.entity.InvoiceDetail;
import com.hkt.module.partner.customer.entity.Customer;

public class PanelManageCustomer extends MyPanel implements IDialogResto {

  private ExtendJLabel lblOwner, lblTypePartnerCurrent, lblTypePartnerNew, lblSearch;
  private ExtendJComboBox cbTypePartnerCurrent, cbTypePartnerNew;
  private TableListModelCustomer tableCustomersLeft, tableCustomersRight;
  private JScrollPane scrollPane;
  private AccountGroup rootGroupCustomer;
  private List<Customer> listTableLeft, listTableRight;
  private List<RelativeShip> saveMemberShip;
  private int fillterLeft = 0;
  private int fillterRight = 0;
  private ExtendJButton btnCancel;


  /** Hàm khởi tạo class */
  public PanelManageCustomer() {
    createBeginTest();
    try {
      rootGroupCustomer = CustomerModelManager.getInstance().getRootGroupCustomer();
    } catch (Exception e) {
      e.printStackTrace();
    }
    saveMemberShip = new ArrayList<PanelManageCustomer.RelativeShip>();

    this.setLayout(new BorderLayout(10, 10));
    this.setOpaque(false);

//    this.add(new Panel_PAGESTART(), BorderLayout.PAGE_START);
    this.add(new Panel_LEFT(), BorderLayout.LINE_START);
    this.add(new Panel_CENTER(), BorderLayout.CENTER);
    this.add(new Panel_RIGHT(), BorderLayout.LINE_END);

  }

//  // Định nghĩa mô tả cho phần trên cùng giao diện
//  protected class Panel_PAGESTART extends JPanel {
//
//    public Panel_PAGESTART() {
//      init();
//      txtOwner.setText(AccountModelManager.getInstance().getNameByLoginId(
//          ManagerAuthenticate.getInstance().getOrganizationLoginId()));
//    }
//
//    public void init() {
//      this.setLayout(new BorderLayout(20, 0));
//      this.setOpaque(false);
//
//      lblOwner = new ExtendJLabel("Doanh nghiệp");
//      lblOwner.setPreferredSize(new Dimension(100, 22));
//      txtOwner = new ExtendJTextField();
//      txtOwner.setEditable(false);
//
//      this.add(lblOwner, BorderLayout.LINE_START);
//      this.add(txtOwner, BorderLayout.CENTER);
//    }
//  }
  

  // Định nghĩa mô tả cho phần bảng trái
  protected class Panel_LEFT extends JPanel {
	 private TableFillterSorter tableFillterSorter;
	  private ExtendJTextField txtSearch;
    public Panel_LEFT() {
      listTableLeft = new ArrayList<Customer>();
      init();
      
      cbTypePartnerCurrent.setSelectedIndex(0);
      updateViewTableLeft();

      cbTypePartnerCurrent.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          if (cbTypePartnerCurrent.getSelectedIndex() >= 1) {
            fillterLeft = 1;
          } else {
            fillterLeft = 0;
          }
          updateViewTableLeft();
        }
      });
    }

    public void init() {
      this.setLayout(new BorderLayout(0, 10));
      this.setPreferredSize(new Dimension(400, 100));
      this.setOpaque(false);
      txtSearch = new ExtendJTextField();
      txtSearch.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent evt) {
				txtSearchCaretUpdate(evt);
			}
		});
      cbTypePartnerCurrent = new ExtendJComboBox();
      cbTypePartnerCurrent.setName("cbDoiTacHientai");
      lblTypePartnerCurrent = new ExtendJLabel("Loại đối tác hiện tại");
      PanelComboBox<AccountGroup> panelComboBox = new PanelComboBox<AccountGroup>(cbTypePartnerCurrent,
          lblTypePartnerCurrent);
      panelComboBox.setModelComboBox(getListAccountGroup());

      tableCustomersLeft = new TableListModelCustomer(listTableLeft);
      tableCustomersLeft.setName("tbDoiTacHienTai");
      scrollPane = new JScrollPane();
      scrollPane.setViewportView(tableCustomersLeft);
      tableFillterSorter = new TableFillterSorter(tableCustomersLeft);
  	  tableFillterSorter.createTableSorter();
  	  tableFillterSorter.createTableFillter();
      this.add(panelComboBox, BorderLayout.PAGE_START);
      this.add(txtSearch, BorderLayout.CENTER);
      this.add(scrollPane, BorderLayout.SOUTH);
    }
	private void txtSearchCaretUpdate(CaretEvent evt) {
		searchData();
		
	}
	  private void searchData() {
		  tableFillterSorter.fillter(txtSearch.getText(), "Tên đối tác");
		
	}
  }

  // Định nghĩa mô tả cho phần nút trung tâm
  protected class Panel_CENTER extends JPanel {
    private ExtendJButton btnAddOne, btnAddAll, btnRemoveOne, btnRemoveAll;

    public Panel_CENTER() {
      init();

      btnAddOne.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          // Kiểm tra danh sách hiện tại > 0 và nhóm đẩy sang # nhóm hiện tại và
          // khác lựa chọn 'Tất cả'
          if (listTableLeft.size() > 0
              && cbTypePartnerCurrent.getSelectedIndex() != cbTypePartnerNew.getSelectedIndex()
              && cbTypePartnerNew.getSelectedIndex() != 0) {
            // Kiểm tra đối tượng bên nhóm phải đã có chưa?
            boolean duplicate = false;
            Customer cus = tableCustomersLeft.getSelectBean();
            for (int i = 0; i < listTableRight.size(); i++) {
              if (listTableRight.get(i).getLoginId().equals(cus.getLoginId())) {
                duplicate = true;
                break;
              }
            }
            // Nếu chưa có thì thêm vào danh sách nhóm phải
            if (!duplicate) {
              if (cbTypePartnerCurrent.getSelectedIndex() == 0) {
                try {
                  List<AccountMembership> ams = AccountModelManager.getInstance().findMembershipByAccountLoginId(
                      cus.getLoginId());
                  for (AccountMembership a : ams) {
                    if (a.getGroupPath().indexOf(AccountModelManager.Customer) >= 0) {
                      ((AccountGroup) cbTypePartnerCurrent.getSelectedItem()).setPath(a.getGroupPath());
                      break;
                    }
                  }
                } catch (Exception ex) {
                  ex.printStackTrace();
                }
              }
              // Danh sách lưu tạm để cuối cùng SAVE vào CSDL
              saveAddOrRemove(cus, ((AccountGroup) cbTypePartnerCurrent.getSelectedItem()),
                  ((AccountGroup) cbTypePartnerNew.getSelectedItem()));
              // Set thuộc tính disabled cho 2 comboBox
              setEnabledSelectGroup(false);
              listTableRight.add(cus);
//              tableCustomersRight.setModel(dataModel);

              listTableLeft.remove(cus);
              tableCustomersLeft.setCustomers(listTableLeft);
              tableCustomersRight.setCustomers(listTableRight);
              btnCancel.setEnabled(true);
            }
          }
        }
      });

      btnAddAll.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          // Kiểm tra danh sách hiện tại > 0 và nhóm đẩy sang # nhóm hiện tại và
          // khác lựa chọn 'Tất cả'
          if (listTableLeft.size() > 0
              && cbTypePartnerCurrent.getSelectedIndex() != cbTypePartnerNew.getSelectedIndex()
              && cbTypePartnerNew.getSelectedIndex() != 0) {
            // Biến kiểm tra có thay đổi dữ liệu ở bảng ko?
            boolean updateData = false;
            for (int j = 0; j < listTableLeft.size();) {
              // Kiểm tra đối tượng bên nhóm phải đã có chưa?
              boolean duplicate = false;
              for (int i = 0; i < listTableRight.size(); i++) {
                if (listTableRight.get(i).getLoginId().equals(listTableLeft.get(j).getLoginId())) {
                  duplicate = true;
                  break;
                }
              }
              // Nếu chưa có thì thêm vào danh sách nhóm phải và xóa khỏi danh
              // sách nhóm trái
              if (!duplicate) {
                if (cbTypePartnerCurrent.getSelectedIndex() == 0) {
                  try {
                    List<AccountMembership> ams = AccountModelManager.getInstance().findMembershipByAccountLoginId(
                        listTableLeft.get(j).getLoginId());
                    for (AccountMembership a : ams) {
                      if (a.getGroupPath().indexOf(AccountModelManager.Customer) >= 0) {
                        ((AccountGroup) cbTypePartnerCurrent.getSelectedItem()).setPath(a.getGroupPath());
                        break;
                      }
                    }
                  } catch (Exception ex) {
                    ex.printStackTrace();
                  }
                }
                saveAddOrRemove(listTableLeft.get(j), ((AccountGroup) cbTypePartnerCurrent.getSelectedItem()),
                    ((AccountGroup) cbTypePartnerNew.getSelectedItem()));
                listTableRight.add(listTableLeft.get(j));
                listTableLeft.remove(listTableLeft.get(j));
                updateData = true;
              } else
                j++;
            }
            // Nếu có thay đổi list data thì cập nhật lên bảng
            if (updateData) {
              tableCustomersRight.setCustomers(listTableRight);
              tableCustomersLeft.setCustomers(listTableLeft);
              setEnabledSelectGroup(false);
              btnCancel.setEnabled(true);
            }
          }
        }
      });

      btnRemoveOne.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          if (listTableRight.size() > 0
              && cbTypePartnerCurrent.getSelectedIndex() != cbTypePartnerNew.getSelectedIndex()
              && cbTypePartnerCurrent.getSelectedIndex() != 0) {
            // Kiểm tra đối tượng bên nhóm phải đã có chưa?
            boolean duplicate = false;
            Customer cus = tableCustomersRight.getSelectBean();
            for (int i = 0; i < listTableLeft.size(); i++) {
              if (listTableLeft.get(i).getLoginId().equals(cus.getLoginId())) {
                duplicate = true;
                break;
              }
            }
            // Nếu chưa có thì thêm vào danh sách nhóm phải
            if (!duplicate) {
              if (cbTypePartnerNew.getSelectedIndex() == 0) {
                try {
                  List<AccountMembership> ams = AccountModelManager.getInstance().findMembershipByAccountLoginId(
                      cus.getLoginId());
                  for (AccountMembership a : ams) {
                    if (a.getGroupPath().indexOf(AccountModelManager.Customer) >= 0) {
                      ((AccountGroup) cbTypePartnerNew.getSelectedItem()).setPath(a.getGroupPath());
                      break;
                    }
                  }
                } catch (Exception ex) {
                  ex.printStackTrace();
                }
              }

              saveAddOrRemove(cus, ((AccountGroup) cbTypePartnerNew.getSelectedItem()),
                  ((AccountGroup) cbTypePartnerCurrent.getSelectedItem()));
              setEnabledSelectGroup(false);

              listTableLeft.add(cus);
              tableCustomersLeft.setCustomers(listTableLeft);

              listTableRight.remove(cus);
              tableCustomersRight.setCustomers(listTableRight);
              btnCancel.setEnabled(true);
            }
          }
        }
      });

      btnRemoveAll.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          if (listTableRight.size() > 0
              && cbTypePartnerCurrent.getSelectedIndex() != cbTypePartnerNew.getSelectedIndex()
              && cbTypePartnerCurrent.getSelectedIndex() != 0) {
            // Biến kiểm tra có thay đổi dữ liệu ở bảng ko?
            boolean updateData = false;
            for (int j = 0; j < listTableRight.size();) {
              // Kiểm tra đối tượng bên nhóm trái đã có chưa?
              boolean duplicate = false;
              for (int i = 0; i < listTableLeft.size(); i++) {
                if (listTableLeft.get(i).getLoginId().equals(listTableRight.get(j).getLoginId())) {
                  duplicate = true;
                  break;
                }
              }
              // Nếu chưa có thì thêm vào danh sách nhóm phải và xóa khỏi danh
              // sách nhóm trái
              if (!duplicate) {
                if (cbTypePartnerNew.getSelectedIndex() == 0) {
                  try {
                    List<AccountMembership> ams = AccountModelManager.getInstance().findMembershipByAccountLoginId(
                        listTableRight.get(j).getLoginId());
                    for (AccountMembership a : ams) {
                      if (a.getGroupPath().indexOf(AccountModelManager.Customer) >= 0) {
                        ((AccountGroup) cbTypePartnerNew.getSelectedItem()).setPath(a.getGroupPath());
                        break;
                      }
                    }
                  } catch (Exception ex) {
                    ex.printStackTrace();
                  }
                }
                saveAddOrRemove(listTableRight.get(j), ((AccountGroup) cbTypePartnerNew.getSelectedItem()),
                    ((AccountGroup) cbTypePartnerCurrent.getSelectedItem()));
                listTableLeft.add(listTableRight.get(j));
                listTableRight.remove(listTableRight.get(j));
                updateData = true;
              } else
                j++;
            }
            // Nếu có thay đổi list data thì cập nhật lên bảng
            if (updateData) {
              tableCustomersRight.setCustomers(listTableRight);
              tableCustomersLeft.setCustomers(listTableLeft);
              setEnabledSelectGroup(false);
              btnCancel.setEnabled(true);
            }
          }
        }
      });

      btnCancel.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          saveMemberShip.clear();
          updateViewTableLeft();
          updateViewTableRight();
          setEnabledSelectGroup(true);
          btnCancel.setEnabled(false);
        }
      });
    }

    public void init() {
      this.setLayout(new GridLayout(10, 1, 10, 14));
      this.setPreferredSize(new Dimension(50, 150));
      this.setOpaque(false);

      btnAddOne = new ExtendJButton(">");
      btnAddAll = new ExtendJButton(">>");
      btnRemoveOne = new ExtendJButton("<");
      btnRemoveAll = new ExtendJButton("<<");
      btnCancel = new ExtendJButton("Hủy");

      btnAddOne.setName(">");
      btnAddAll.setName(">>");
      btnRemoveOne.setName("<");
      btnRemoveAll.setName("<<");
      btnCancel.setName("btnCancel");
      btnCancel.setEnabled(false);

      btnAddAll.addMouseListener(new MouseEventClickButtonDialog("", "", ""));
      btnAddOne.addMouseListener(new MouseEventClickButtonDialog("", "", ""));
      btnRemoveOne.addMouseListener(new MouseEventClickButtonDialog("", "", ""));
      btnRemoveAll.addMouseListener(new MouseEventClickButtonDialog("", "", ""));
      btnCancel.addMouseListener(new MouseEventClickButtonDialog("", "", ""));
      
      JLabel label1 = new JLabel("");
      label1.setPreferredSize(new Dimension(50, 50));
      JLabel label2 = new JLabel("");
      label2.setPreferredSize(new Dimension(50, 50));

      this.add(label1);
      this.add(label2);
      this.add(btnAddAll);
      this.add(btnAddOne);
      this.add(btnRemoveOne);
      this.add(btnRemoveAll);
      this.add(btnCancel);
    }
  }

  // Định nghĩa mô tả cho phần bảng phải
  protected class Panel_RIGHT extends JPanel {
	  private TableFillterSorter tableFillterSorter;
	  private ExtendJTextField txtSearch;
    public Panel_RIGHT() {
      listTableRight = new ArrayList<Customer>();
      init();

      cbTypePartnerNew.setSelectedIndex(0);
      updateViewTableRight();

      cbTypePartnerNew.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          if (cbTypePartnerNew.getSelectedIndex() >= 1) {
            fillterRight = 1;
          } else {
            fillterRight = 0;
          }
          updateViewTableRight();
        }
      });
    }

    public void init() {
    	try {
    		 this.setLayout(new BorderLayout(0, 10));
    	      this.setPreferredSize(new Dimension(400, 100));
    	      this.setOpaque(false);
    	      txtSearch = new ExtendJTextField();
    	      txtSearch.addCaretListener(new CaretListener() {
    				public void caretUpdate(CaretEvent evt) {
    					txtSearchCaretUpdate(evt);
    				}
    			});
    	      
    	      lblTypePartnerNew = new ExtendJLabel("Loại đối tác mới");
    	      cbTypePartnerNew = new ExtendJComboBox();
    	      cbTypePartnerNew.setName("cbDoiTacMoi");
    	      PanelComboBox<AccountGroup> panelComboBox = new PanelComboBox<AccountGroup>(cbTypePartnerNew, lblTypePartnerNew);
    	      panelComboBox.setModelComboBox(getListAccountGroup());

    	      tableCustomersRight = new TableListModelCustomer(listTableRight);
    	      tableCustomersRight.setName("tbDoiTacMoi");
    	      tableFillterSorter = new TableFillterSorter(tableCustomersRight);
    	  	  tableFillterSorter.createTableSorter();
    	  	  tableFillterSorter.createTableFillter();
    	      scrollPane = new JScrollPane();
    	      scrollPane.setViewportView(tableCustomersRight);
    	      this.add(panelComboBox, BorderLayout.PAGE_START);
    	      this.add(txtSearch, BorderLayout.CENTER);
    	      this.add(scrollPane, BorderLayout.SOUTH);
		} catch (Exception e) {
			
		}
     
    }

	private void txtSearchCaretUpdate(CaretEvent evt) {
		searchData();
		
	}
	  private void searchData() {
		  tableFillterSorter.fillter(txtSearch.getText(), "Tên đối tác");
		
	}
  }

  protected class RelativeShip {
    private String loginId;
    private String pathOld;
    private String pathNew;

    public RelativeShip() {
    }

    public RelativeShip(String loginId, String pathOld, String pathNew) {
      this.loginId = loginId;
      this.pathOld = pathOld;
      this.pathNew = pathNew;
    }

    public String getLoginId() {
      return loginId;
    }

    public void setLoginId(String loginId) {
      this.loginId = loginId;
    }

    public String getPathOld() {
      return pathOld;
    }

    public void setPathOld(String pathOld) {
      this.pathOld = pathOld;
    }

    public String getPathNew() {
      return pathNew;
    }

    public void setPathNew(String pathNew) {
      this.pathNew = pathNew;
    }
  }

  // Tạo Panel chứa label và comboBox
  protected class PanelComboBox<E> extends JPanel {
    private ExtendJComboBox cbBox;
    private ExtendJLabel lblName;

    public PanelComboBox(ExtendJComboBox comboBox, ExtendJLabel label) {
      this.cbBox = comboBox;
      this.lblName = label;

      this.setLayout(new GridLayout(2, 1, 0, 0));
      this.setOpaque(false);

      this.add(lblName);
      this.add(cbBox);
    }

    public void setModelComboBox(List<E> list) {
      DefaultComboBoxModel<E> modelCb = new DefaultComboBoxModel<E>();
      for (E e : list) {
        modelCb.addElement(e);
      }
      cbBox.setModel(modelCb);
    }
  }

  private void updateViewTableLeft() {
    try {
      AccountGroup accGroup = (AccountGroup) cbTypePartnerCurrent.getSelectedItem();
      if (accGroup.getName().equals("All"))
        listTableLeft = CustomerModelManager.getInstance().getCustomers(false);
      else
        listTableLeft = getListDataTable(accGroup);
      tableCustomersLeft.setCustomers(listTableLeft);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }


private void updateViewTableRight() {
    try {
      AccountGroup accGroup = (AccountGroup) cbTypePartnerNew.getSelectedItem();
      if (accGroup.getName().equals("All"))
        listTableRight = CustomerModelManager.getInstance().getCustomers(false);
      else
        listTableRight = getListDataTable(accGroup);
      tableCustomersRight.setCustomers(listTableRight);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private void setEnabledSelectGroup(boolean value) {
    cbTypePartnerCurrent.setEnabled(value);
    cbTypePartnerNew.setEnabled(value);
  }

  // Lưu tạm danh sách các khách hàng được chuyển từ nhóm cũ sang nhóm mới
  private void saveAddOrRemove(Customer cus, AccountGroup accOld, AccountGroup accNew) {
    RelativeShip relativeShip = new RelativeShip(cus.getLoginId(), accOld.getPath(), accNew.getPath());
    boolean push = true;
    for (int i = 0; i < saveMemberShip.size();) {
      if (saveMemberShip.get(i).getLoginId().equals(cus.getLoginId())) {
        saveMemberShip.remove(i);
        push = false;
        break;
      } else
        i++;
    }
    if (push)
      saveMemberShip.add(relativeShip);
  }

  // Truyền vào nhóm lấy ra toàn bộ khách hàng trong nhóm đó
  private List<Customer> getListDataTable(AccountGroup accGroup) {
    List<Customer> list = new ArrayList<Customer>();
    try {
      List<AccountMembership> listMembership = AccountModelManager.getInstance().findMembershipByGroupPath(
          accGroup.getPath());
      if (listMembership.size() > 0)
        for (AccountMembership accMem : listMembership) {
        	Customer c = CustomerModelManager.getInstance().getBydLoginId(accMem.getLoginId());
					if (c != null) {
						list.add(c);
					}
        }
      return list;
    } catch (Exception e1) {
      e1.printStackTrace();
      return null;
    }
  }

  // Lấy toàn bộ danh sách nhóm con của "path = ([TenDoanhNghiep]/Customer)"
  private List<AccountGroup> getListAccountGroup() {
    try {
      List<AccountGroup> list = AccountModelManager.getInstance().getGroupDetailByPath(rootGroupCustomer.getPath())
          .getChildren();
      AccountGroup ac = new AccountGroup();
      ac.setName("All");
      ac.setLabel("Tất cả");
      ac.setParentPath(null);
      ac.setPath(null);
      list.add(0, ac);
      return list;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  // Phương thức cho nút [Đồng ý]
  @Override
  public void Save() {
    if (UIConfigModelManager.getInstance().getPermission(getToolTipText()) == Capability.READ) {
      JOptionPane.showMessageDialog(null, "Bạn chưa được cấp quyền này !");
    } else {
      try {
        for (RelativeShip rs : saveMemberShip) {
          if (rs.getPathOld() != null) {
            AccountMembership accMembership = AccountModelManager.getInstance().getMembershipByAccountAndGroup(
                rs.getLoginId(), rs.getPathOld());
            if (accMembership == null) {
              accMembership = new AccountMembership();
              accMembership.setLoginId(rs.getLoginId());
              accMembership.setGroupPath(rs.getPathNew());
              AccountModelManager.getInstance().saveAccountMembership(accMembership);
            } else {
              List<InvoiceDetail> invoiceDetails = AccountingModelManager.getInstance()
                  .findInvoiceDetailByIdentifierId(rs.getPathOld());
              for (InvoiceDetail invoiceDetail : invoiceDetails) {
                invoiceDetail = AccountingModelManager.getInstance().getInvoiceDetail(invoiceDetail.getId());

                System.out.println(rs.getPathOld() + "   " + rs.getPathNew());
                String idDentit = invoiceDetail.getGroupCustomerCode().replaceAll(rs.getPathOld(), rs.getPathNew());
                invoiceDetail.setGroupCustomerCode(idDentit);

                AccountingModelManager.getInstance().saveInvoiceDetail(invoiceDetail);
              }
              accMembership.setGroupPath(rs.getPathNew());
              AccountModelManager.getInstance().saveAccountMembership(accMembership);
            }
          } else {
            AccountMembership accMembership = new AccountMembership();
            accMembership.setLoginId(rs.getLoginId());
            accMembership.setGroupPath(rs.getPathNew());
            AccountModelManager.getInstance().saveAccountMembership(accMembership);
          }
        }
        DialogNotice.getInstace().setVisible(true);
        saveMemberShip.clear();
        updateViewTableLeft();
        updateViewTableRight();
        setEnabledSelectGroup(true);
        btnCancel.setEnabled(false);
      } catch (Exception ex) {
        ex.printStackTrace();
      } finally {
        saveMemberShip.clear();
      }
    }
  }

  @Override
  public void createBeginTest() {
    if (MyPanel.isTest) {
      // Tạo nhóm đối tác
      try {
        AccountGroup root = AccountModelManager.getInstance().getRootGroupDetail().getChildren().get(0);
        AccountGroup parent = new AccountGroup(root, AccountModelManager.Customer);
        parent.setLabel(AccountModelManager.Customer);
        if (AccountModelManager.getInstance().getGroupByPath(root.getPath() + "/" + AccountModelManager.Customer) == null) {
          parent = AccountModelManager.getInstance().saveGroup(parent);
        } else {
          parent = AccountModelManager.getInstance().getGroupByPath(parent.getPath());
        }

        for (int i = 1; i <= 9; i++) {
          AccountGroup accountGroup = new AccountGroup();
          accountGroup.setName("Mã NĐT HktTest" + i);
          accountGroup.setLabel("Nhóm ĐT HktTest" + i);
          accountGroup.setParent(parent);
          AccountModelManager.getInstance().saveGroup(accountGroup);

          // Tạo đối tác
          Account account = new Account();
          account.setLoginId("HktTest" + i);
          account.setPassword("HktTest" + i);
          account.setEmail("HktTest" + i + "@gmail.com");
          try {
            AccountModelManager.getInstance().saveAccount(account);
          } catch (Exception e1) {
            e1.printStackTrace();
          }
          Customer customer = new Customer();
          customer.setLoginId("HktTest" + i);
          customer.setOrganizationLoginId(ManagerAuthenticate.getInstance().getOrganizationLoginId());
          customer.setName("Tên ĐT HktTest" + i);
          customer.setCode("Mã ĐT HktTest" + i);
          CustomerModelManager.getInstance().saveCustomer(customer);

          // Tạo mối liên hệ -> đối tác thuộc nhóm đối tác
          AccountMembership accountMembership = new AccountMembership(account, accountGroup);
          AccountModelManager.getInstance().saveAccountMembership(accountMembership);
        }
      } catch (Exception e2) {
        e2.printStackTrace();
      }
    }
    super.createBeginTest();
  }

  @Override
  public void deleteDataTest() {
    if (!PanelTestAll.runAll) {
      DialogTest.getInstance().show("Xóa toàn bộ dữ liệu test ?");
      if (DialogTest.getInstance().isTest()) {
        try {
          AccountModelManager.getInstance().deleteTest("HktTest");
          CustomerModelManager.getInstance().deleteTest("HktTest");
        } catch (Exception ex) {
          ex.printStackTrace();
        }
      }
    }
  }
}
