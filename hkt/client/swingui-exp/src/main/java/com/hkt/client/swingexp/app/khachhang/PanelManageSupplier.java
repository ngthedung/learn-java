package com.hkt.client.swingexp.app.khachhang;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import org.jdesktop.swingx.decorator.ResetDTCRColorHighlighter;

import com.hkt.client.swingexp.app.component.ExtendJButton;
import com.hkt.client.swingexp.app.component.ExtendJComboBox;
import com.hkt.client.swingexp.app.component.ExtendJLabel;
import com.hkt.client.swingexp.app.component.ExtendJTextField;
import com.hkt.client.swingexp.app.component.MyPanel;
import com.hkt.client.swingexp.app.component.TableFillterSorter;
import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.DialogNotice;
import com.hkt.client.swingexp.app.core.DialogTest;
import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.client.swingexp.app.core.ManagerAuthenticate;
import com.hkt.client.swingexp.app.core.MouseEventClickButtonDialogPlus;
import com.hkt.client.swingexp.app.hethong.PanelCurrency;
import com.hkt.client.swingexp.app.hethong.PanelTestAll;
import com.hkt.client.swingexp.app.khachhang.list.TableListModelSupplier;
import com.hkt.client.swingexp.app.khachhang.list.TableManageSupplier;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.LocaleModelManager;
import com.hkt.client.swingexp.model.SupplierModelManager;
import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.module.account.entity.Account;
import com.hkt.module.account.entity.AccountGroup;
import com.hkt.module.account.entity.AccountMembership;
import com.hkt.module.account.entity.AccountMembership.Capability;
import com.hkt.module.partner.supplier.entity.Supplier;

public class PanelManageSupplier extends MyPanel implements IDialogResto {
	private ExtendJLabel lblOwner, lblTypeSupplierCurrent, lblTypeSupplierNew;
	private ExtendJComboBox cbTypeSupplierCurrent, cbTypeSupplierNew;
	private TableListModelSupplier tableSupplierLeft, tableSupplierRight;
	private JScrollPane scrollPane;
	private AccountGroup rootGroupSupplier;
	private List<Supplier> listTableLeft, listTableRight;
	private List<RelativeShip> saveMemberShip;
	private int fillterByComboBoxLeft = 0;
	private int fillterByComboBoxRight = 0;
	private ExtendJButton btnCancel;

	/**
	 * Create the PanelManagePartner
	 */

	public PanelManageSupplier() {
		createBeginTest();
		try {
			rootGroupSupplier = SupplierModelManager.getInstance().getRootGroupSupplier();
		} catch (Exception e) {
			e.printStackTrace();
		}
		saveMemberShip = new ArrayList<PanelManageSupplier.RelativeShip>();

		this.setLayout(new BorderLayout(10, 10));
		this.setOpaque(false);

//		this.add(new Panel_PAGESTART(), BorderLayout.PAGE_START);
		this.add(new Panel_LEFT(), BorderLayout.LINE_START);
		this.add(new Panel_CENTER(), BorderLayout.CENTER);
		this.add(new Panel_RIGHT(), BorderLayout.LINE_END);

	}

//	protected class Panel_PAGESTART extends JPanel {
//
//		public Panel_PAGESTART() {
//			init();
//			txtOwner.setText(AccountModelManager.getInstance().getNameByLoginId(
//			    ManagerAuthenticate.getInstance().getOrganizationLoginId()));
//		}
//
//		public void init() {
//			this.setLayout(new BorderLayout(20, 0));
//			this.setOpaque(false);
//
//			lblOwner = new ExtendJLabel("Doanh nghiệp");
//			lblOwner.setPreferredSize(new Dimension(100, 22));
//			txtOwner = new ExtendJTextField();
//			txtOwner.setEditable(false);
//
//			this.add(lblOwner, BorderLayout.LINE_START);
//			this.add(txtOwner, BorderLayout.CENTER);
//		}
//	}

	protected class Panel_LEFT extends JPanel {
		 private TableFillterSorter tableFillterSorter;
		 private ExtendJTextField txtSearch;
		public Panel_LEFT() {
			listTableLeft = new ArrayList<Supplier>();
			init();
			try {
				cbTypeSupplierCurrent.setSelectedIndex(0);
			} catch (Exception e) {
			}

			updateViewTableLeft();

			cbTypeSupplierCurrent.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (cbTypeSupplierCurrent.getSelectedIndex() >= 1) {
						fillterByComboBoxLeft = 1;
					} else {
						fillterByComboBoxLeft = 0;
					}
					updateViewTableLeft();
				}
			});
			cbTypeSupplierCurrent.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if (e.getButton() == MouseEvent.BUTTON3) {
						PanelGroupSupplier panelGroupSupplier;
						try {
							panelGroupSupplier = new PanelGroupSupplier();
							panelGroupSupplier.setName("QLNhomNhaPhanPhoi");
							DialogMain dialog = new DialogMain(panelGroupSupplier);
							dialog.setIcon("canhan1.png");
							dialog.setName("dialogGroupSupplier");
							dialog.setTitle("Quản lý nhóm nhà cung cấp");
							dialog.setModal(true);
							dialog.setVisible(true);
							cbTypeSupplierCurrent.setModel(new DefaultComboBoxModel(SupplierModelManager.getInstance()
							    .getAllSuppliers().toArray()));
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
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
			cbTypeSupplierCurrent = new ExtendJComboBox();
			cbTypeSupplierCurrent.setName("cbNhaPhanPhoiHienTai");
			lblTypeSupplierCurrent = new ExtendJLabel("Nhà phân phối hiện tại");
			PanelComboBox<AccountGroup> panelComboBox = new PanelComboBox<AccountGroup>(cbTypeSupplierCurrent,
			    lblTypeSupplierCurrent);
			panelComboBox.setModelComboBox(getListAccountGroup());

			tableSupplierLeft = new TableListModelSupplier(listTableLeft);
			tableSupplierLeft.setName("tbNhaPhanPhoiHienTai");
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(tableSupplierLeft);
		      tableFillterSorter = new TableFillterSorter(tableSupplierLeft);
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
					    && cbTypeSupplierCurrent.getSelectedIndex() != cbTypeSupplierNew.getSelectedIndex()
					    && cbTypeSupplierNew.getSelectedIndex() != 0) {
						// Kiểm tra đối tượng bên nhóm phải đã có chưa?
						boolean duplicate = false;
						Supplier sup = tableSupplierLeft.getSelectedBean();
						for (int i = 0; i < listTableRight.size(); i++) {
							if (listTableRight.get(i).getLoginId().equals(sup.getLoginId())) {
								duplicate = true;
								break;
							}
						}
						// Nếu chưa có thì thêm vào danh sách nhóm phải
						if (!duplicate) {
							if (cbTypeSupplierCurrent.getSelectedIndex() == 0) {
								try {
									List<AccountMembership> ams = AccountModelManager.getInstance().findMembershipByAccountLoginId(
									    sup.getLoginId());
									for (AccountMembership a : ams) {
										if (a.getGroupPath().indexOf(AccountModelManager.Supplier) >= 0) {
											((AccountGroup) cbTypeSupplierCurrent.getSelectedItem()).setPath(a.getGroupPath());
											break;
										}
									}
								} catch (Exception ex) {
									ex.printStackTrace();
								}
							}
							// Danh sách lưu tạm để cuối cùng SAVE vào CSDL
							saveAddOrRemove(sup, ((AccountGroup) cbTypeSupplierCurrent.getSelectedItem()),
							    ((AccountGroup) cbTypeSupplierNew.getSelectedItem()));
							// Set thuộc tính disabled cho 2 comboBox
							setEnabledSelectGroup(false);

							listTableRight.add(sup);
							tableSupplierRight.setSuppliers(listTableRight);

							listTableLeft.remove(sup);
							tableSupplierLeft.setSuppliers(listTableLeft);
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
					    && cbTypeSupplierCurrent.getSelectedIndex() != cbTypeSupplierNew.getSelectedIndex()
					    && cbTypeSupplierNew.getSelectedIndex() != 0) {
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
								if (cbTypeSupplierCurrent.getSelectedIndex() == 0) {
									try {
										List<AccountMembership> ams = AccountModelManager.getInstance().findMembershipByAccountLoginId(
										    listTableLeft.get(j).getLoginId());
										for (AccountMembership a : ams) {
											if (a.getGroupPath().indexOf(AccountModelManager.Customer) >= 0) {
												((AccountGroup) cbTypeSupplierCurrent.getSelectedItem()).setPath(a.getGroupPath());
												break;
											}
										}
									} catch (Exception ex) {
										ex.printStackTrace();
									}
								}
								saveAddOrRemove(listTableLeft.get(j), ((AccountGroup) cbTypeSupplierCurrent.getSelectedItem()),
								    ((AccountGroup) cbTypeSupplierNew.getSelectedItem()));
								listTableRight.add(listTableLeft.get(j));
								listTableLeft.remove(listTableLeft.get(j));
								updateData = true;
							} else
								j++;
						}
						// Nếu có thay đổi list data thì cập nhật lên bảng
						if (updateData) {
							tableSupplierRight.setSuppliers(listTableRight);
							tableSupplierLeft.setSuppliers(listTableLeft);
							setEnabledSelectGroup(false);
							btnCancel.setEnabled(true);
						}
					}
				}
			});

			btnRemoveOne.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// Kiểm tra điều kiện list > 0 && các nhóm chọn # nhau && # nhóm 'Tất
					// cả'
					if (listTableRight.size() > 0
					    && cbTypeSupplierCurrent.getSelectedIndex() != cbTypeSupplierNew.getSelectedIndex()
					    && cbTypeSupplierCurrent.getSelectedIndex() != 0) {
						// Kiểm tra đối tượng bên nhóm phải đã có chưa?
						boolean duplicate = false;
						Supplier sup = tableSupplierRight.getSelectedBean();
						for (int i = 0; i < listTableLeft.size(); i++) {
							if (listTableLeft.get(i).getLoginId().equals(sup.getLoginId())) {
								duplicate = true;
								break;
							}
						}
						// Nếu chưa có thì thêm vào danh sách nhóm phải
						if (!duplicate) {
							if (cbTypeSupplierNew.getSelectedIndex() == 0) {
								try {
									List<AccountMembership> ams = AccountModelManager.getInstance().findMembershipByAccountLoginId(
									    sup.getLoginId());
									for (AccountMembership a : ams) {
										if (a.getGroupPath().indexOf(AccountModelManager.Customer) >= 0) {
											((AccountGroup) cbTypeSupplierNew.getSelectedItem()).setPath(a.getGroupPath());
											break;
										}
									}
								} catch (Exception ex) {
									ex.printStackTrace();
								}
							}
							saveAddOrRemove(sup, ((AccountGroup) cbTypeSupplierNew.getSelectedItem()),
							    ((AccountGroup) cbTypeSupplierCurrent.getSelectedItem()));
							setEnabledSelectGroup(false);

							listTableLeft.add(sup);
							tableSupplierLeft.setSuppliers(listTableLeft);

							listTableRight.remove(sup);
							tableSupplierRight.setSuppliers(listTableRight);
							btnCancel.setEnabled(true);
						}
					}
				}
			});

			btnRemoveAll.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (listTableRight.size() > 0
					    && cbTypeSupplierCurrent.getSelectedIndex() != cbTypeSupplierNew.getSelectedIndex()
					    && cbTypeSupplierCurrent.getSelectedIndex() != 0) {
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
								if (cbTypeSupplierNew.getSelectedIndex() == 0) {
									try {
										List<AccountMembership> ams = AccountModelManager.getInstance().findMembershipByAccountLoginId(
										    listTableRight.get(j).getLoginId());
										for (AccountMembership a : ams) {
											if (a.getGroupPath().indexOf(AccountModelManager.Customer) >= 0) {
												((AccountGroup) cbTypeSupplierNew.getSelectedItem()).setPath(a.getGroupPath());
												break;
											}
										}
									} catch (Exception ex) {
										ex.printStackTrace();
									}
								}
								saveAddOrRemove(listTableRight.get(j), ((AccountGroup) cbTypeSupplierNew.getSelectedItem()),
								    ((AccountGroup) cbTypeSupplierCurrent.getSelectedItem()));
								listTableLeft.add(listTableRight.get(j));
								listTableRight.remove(listTableRight.get(j));
								updateData = true;
							} else
								j++;
						}
						// Nếu có thay đổi list data thì cập nhật lên bảng
						if (updateData) {
							tableSupplierRight.setSuppliers(listTableRight);
							tableSupplierLeft.setSuppliers(listTableLeft);
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

			btnAddOne.addMouseListener(new MouseEventClickButtonDialogPlus("", "", ""));
			btnAddAll.addMouseListener(new MouseEventClickButtonDialogPlus("", "", ""));
			btnRemoveOne.addMouseListener(new MouseEventClickButtonDialogPlus("", "", ""));
			btnRemoveAll.addMouseListener(new MouseEventClickButtonDialogPlus("", "", ""));
			btnCancel.addMouseListener(new MouseEventClickButtonDialogPlus("", "", ""));

			JLabel label1 = new JLabel("");
			label1.setPreferredSize(new Dimension(50, 50));
			JLabel label2 = new JLabel("");
			label2.setPreferredSize(new Dimension(50, 50));

			btnAddOne.setName(">");
			btnAddAll.setName(">>");
			btnRemoveOne.setName("<");
			btnRemoveAll.setName("<<");
			btnCancel.setName("btnCancel");
			btnCancel.setEnabled(false);

			this.add(label1);
			this.add(label2);
			this.add(btnAddAll);
			this.add(btnAddOne);
			this.add(btnRemoveOne);
			this.add(btnRemoveAll);
			this.add(btnCancel);
		}
	}

	protected class Panel_RIGHT extends JPanel {
		 private TableFillterSorter tableFillterSorter;
		  private ExtendJTextField txtSearch;
		public Panel_RIGHT() {
			listTableRight = new ArrayList<Supplier>();
			init();
			try {
				cbTypeSupplierNew.setSelectedIndex(0);
			} catch (Exception e) {
			}

			updateViewTableRight();

			cbTypeSupplierNew.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (cbTypeSupplierNew.getSelectedIndex() >= 1) {
						fillterByComboBoxRight = 1;
					} else {
						fillterByComboBoxRight = 0;
					}
					updateViewTableRight();
				}
			});
			cbTypeSupplierNew.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if (e.getButton() == MouseEvent.BUTTON3) {
						PanelGroupSupplier panelGroupSupplier;
						try {
							panelGroupSupplier = new PanelGroupSupplier();
							panelGroupSupplier.setName("QLNhomNhaPhanPhoi");
							DialogMain dialog = new DialogMain(panelGroupSupplier);
							dialog.setIcon("canhan1.png");
							dialog.setName("dialogGroupSupplier");
							dialog.setTitle("Quản lý nhóm nhà cung cấp");
							dialog.setModal(true);
							dialog.setVisible(true);

						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
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
			lblTypeSupplierNew = new ExtendJLabel("Nhà phân phối mới");
			cbTypeSupplierNew = new ExtendJComboBox();
			cbTypeSupplierNew.setName("cbNhaPhanPhoiMoi");
			PanelComboBox<AccountGroup> panelComboBox = new PanelComboBox<AccountGroup>(cbTypeSupplierNew, lblTypeSupplierNew);
			panelComboBox.setModelComboBox(getListAccountGroup());

			tableSupplierRight = new TableListModelSupplier(listTableRight);
			tableSupplierRight.setName("tbNhaPhanPhoiMoi");
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(tableSupplierRight);
		      tableFillterSorter = new TableFillterSorter(tableSupplierRight);
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

	// Class tạm lưu memberShip
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

	private List<Supplier> getListDataTable(AccountGroup accGroup) {
		List<Supplier> list = new ArrayList<Supplier>();
		try {
			List<AccountMembership> listMembership = AccountModelManager.getInstance().findMembershipByGroupPath(
			    accGroup.getPath());
			if (listMembership.size() > 0)
				for (AccountMembership accMem : listMembership) {
					Supplier s = SupplierModelManager.getInstance().getBydLoginId(accMem.getLoginId());
					if (s != null) {
						list.add(s);
					}
				}
			return list;
		} catch (Exception e1) {
			return null;
		}
	}

	private List<AccountGroup> getListAccountGroup() {
		try {
			List<AccountGroup> list = AccountModelManager.getInstance().getGroupDetailByPath(rootGroupSupplier.getPath())
			    .getChildren();
			AccountGroup ac = new AccountGroup();
			ac.setName("All");
			ac.setLabel("Tất cả");
			ac.setParentPath(null);
			ac.setPath(null);
			list.add(0, ac);
			return list;
		} catch (Exception e) {
			return new ArrayList<AccountGroup>();
		}
	}

	private void saveAddOrRemove(Supplier sup, AccountGroup accOld, AccountGroup accNew) {
		RelativeShip relativeShip = new RelativeShip(sup.getLoginId(), accOld.getPath(), accNew.getName());
		boolean push = true;
		for (int i = 0; i < saveMemberShip.size();) {
			if (saveMemberShip.get(i).getLoginId().equals(sup.getLoginId())) {
				saveMemberShip.remove(i);
				push = false;
				break;
			} else
				i++;
		}
		if (push)
			saveMemberShip.add(relativeShip);
	}

	private void updateViewTableLeft() {
		try {
			AccountGroup accGroup = (AccountGroup) cbTypeSupplierCurrent.getSelectedItem();
			if (accGroup.getName().equals("All"))
				listTableLeft = SupplierModelManager.getInstance().getAllSuppliers();
			else
				listTableLeft = getListDataTable(accGroup);
			tableSupplierLeft.setSuppliers(listTableLeft);
		} catch (Exception ex) {
			new ArrayList<AccountGroup>();
		}
	}

	private void updateViewTableRight() {
		try {
			AccountGroup accGroup = (AccountGroup) cbTypeSupplierNew.getSelectedItem();
			if (accGroup.getName().equals("All"))
				listTableRight = SupplierModelManager.getInstance().getAllSuppliers();
			else
				listTableRight = getListDataTable(accGroup);
			tableSupplierRight.setSuppliers(listTableRight);
		} catch (Exception ex) {
			new ArrayList<AccountGroup>();
		}
	}

	private void setEnabledSelectGroup(boolean value) {
		cbTypeSupplierCurrent.setEnabled(value);
		cbTypeSupplierNew.setEnabled(value);
	}

	@Override
	public void Save() throws Exception {
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
							accMembership.setGroupPath(rootGroupSupplier.getPath() + "/" + rs.getPathNew());
							AccountModelManager.getInstance().saveAccountMembership(accMembership);
						} else {
							accMembership.setGroupPath(rootGroupSupplier.getPath() + "/" + rs.getPathNew());
							AccountModelManager.getInstance().saveAccountMembership(accMembership);
						}
					} else {
						AccountMembership accMembership = new AccountMembership();
						accMembership.setLoginId(rs.getLoginId());
						accMembership.setGroupPath(rootGroupSupplier.getPath() + "/" + rs.getPathNew());
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

	// Thêm dữ liệu liên quan để Test auto
	@Override
	public void createBeginTest() {
		if (MyPanel.isTest) {
			try {
				// Tạo nhóm nhà phân phối
				AccountGroup root = AccountModelManager.getInstance().getRootGroupDetail().getChildren().get(0);
				AccountGroup parent = new AccountGroup(root, AccountModelManager.Supplier);
				parent.setLabel(AccountModelManager.Supplier);
				if (AccountModelManager.getInstance().getGroupByPath(root.getPath() + "/" + AccountModelManager.Supplier) == null) {
					parent = AccountModelManager.getInstance().saveGroup(parent);
				} else {
					parent = AccountModelManager.getInstance().getGroupByPath(parent.getPath());
				}
				// Tạo ra 10 nhà phân phối
				for (int i = 1; i <= 9; i++) {
					AccountGroup accountGroup = new AccountGroup();
					accountGroup.setName("Mã Nhóm NPP HktTest" + i);
					accountGroup.setLabel("Nhóm NPP HktTest" + i);
					accountGroup.setParent(parent);
					AccountModelManager.getInstance().saveGroup(accountGroup);

					// Tạo Account (thông tin cá nhân) nhà phân phối
					Account account = new Account();
					account.setLoginId("HktTest" + i);
					account.setPassword("HktTest" + i);
					account.setEmail("HktTest" + i + "@gmail.com");
					AccountModelManager.getInstance().saveAccount(account);

					// Tạo entity nhà phân phối
					Supplier supplier = new Supplier();
					supplier.setLoginId("HktTest" + i);
					supplier.setOrganizationLoginId(ManagerAuthenticate.getInstance().getOrganizationLoginId());
					supplier.setName("Tên NPP HktTest" + i);
					supplier.setCode("Mã NPP HktTest" + i);
					SupplierModelManager.getInstance().saveSupplier(supplier);

					// Tạo mối liên hệ -> nhà phân phối thuộc nhóm nhà phân phối nào?
					AccountMembership accountMembership = new AccountMembership(account, accountGroup);
					AccountModelManager.getInstance().saveAccountMembership(accountMembership);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		super.createBeginTest();
	}

	// Xóa tất cả dữ liệu test tự sinh
	@Override
	public void deleteDataTest() {
		if (!PanelTestAll.runAll) {
			DialogTest.getInstance().show("Xóa toàn bộ dữ liệu test ?");
			if (DialogTest.getInstance().isTest()) {
				try {
					AccountModelManager.getInstance().deleteTest("HktTest");
					SupplierModelManager.getInstance().deleteTest("HktTest");
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}

}
