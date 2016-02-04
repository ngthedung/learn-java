package com.hkt.client.swingexp.app.khuvucbanhang;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import com.hkt.client.rest.ClientContext;
import com.hkt.client.rest.service.GenericOptionServiceClient;
import com.hkt.client.swingexp.app.component.ExtendJLabel;
import com.hkt.client.swingexp.app.component.ExtendJTextField;
import com.hkt.client.swingexp.app.component.MyPanel;
import com.hkt.client.swingexp.app.component.TextPopup;
import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.IDialogMain;
import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.client.swingexp.app.core.MyGroupLayout;
import com.hkt.client.swingexp.app.hethong.PanelOption;
import com.hkt.client.swingexp.app.hethong.PanelChoise;
import com.hkt.client.swingexp.model.GenericOptionModelManager;
import com.hkt.client.swingexp.model.HRModelManager;
import com.hkt.module.config.generic.Option;
import com.hkt.module.config.generic.OptionGroup;
import com.hkt.module.hr.entity.Employee;
import com.hkt.module.restaurant.entity.ProjectMember;

@SuppressWarnings("serial")
public class PanelProjectMember extends MyPanel implements IDialogMain {

  private ExtendJLabel lbName, lbRole, lbPercent, lbDirectAward, lbDescription, lbMessenger;
  private ExtendJTextField txtPercent, txtDirectAward, txtDescription;
  private TextPopup<Employee> txtEmployee;
  private JComboBox<Option> cbOption;
  private List<Option> options;
  private JScrollPane scrollPaneTable, scrollIndex;
  private ProjectMember projectMember = new ProjectMember();
  private List<ProjectMember> projectMembers = new ArrayList<ProjectMember>();
  private TableListEmployee listEmployee;
  private boolean restore = true;
  private int j = 0;
  public PanelProject panelProject;
  private boolean flag = true;

  public PanelProjectMember(List<ProjectMember> projectMembers) {
    this.projectMembers = projectMembers;
    init();
    reset();
  }

  private void init() {
    lbName = new ExtendJLabel("Nhân viên");
    lbRole = new ExtendJLabel("Quyền hạn");
    lbPercent = new ExtendJLabel("Tỉ lệ(%)");
    lbDirectAward = new ExtendJLabel("Thưởng (%)");
    lbDescription = new ExtendJLabel("Miêu tả");
    lbMessenger = new ExtendJLabel("");
    lbMessenger.setForeground(Color.RED);

    txtEmployee = new TextPopup<Employee>(Employee.class);
    txtEmployee.setName("txtEmployee");
    try {
      List<Employee> employees = HRModelManager.getInstance().getEmployees();
      txtEmployee.setData(employees);
    } catch (Exception e) {
    }
    try {
      GenericOptionServiceClient clientCore = ClientContext.getInstance().getBean(GenericOptionServiceClient.class);
      OptionGroup group = clientCore.getOptionGroup("accounting", "AccountingService", "contributor_role");
      options = new ArrayList<Option>();
      if (group != null) {
        if (group.getOptions() != null) {
          options = group.getOptions();
        }
      }
      options.add(0, null);
      cbOption = new JComboBox(options.toArray());
      cbOption.setName("cbOption");
      cbOption.setSelectedItem(null);
    } catch (Exception e) {
    }
    cbOption.addMouseListener(new MouseAdapter() {
		public void mouseClicked(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON3) {
				PanelOption pnOption = new PanelOption("accounting", "AccountingService", "contributor_role");
				pnOption.setName("ViTriPhongBan");
				DialogMain dialog;
				try {
					dialog = new DialogMain(pnOption);
					dialog.setName("dlViTriPhongBan");
					dialog.setTitle("Vị trí phòng ban");
					dialog.setModal(true);
					dialog.setLocationRelativeTo(null);
					dialog.setVisible(true);
					try {
						options = GenericOptionModelManager.getInstance().getOptionGroup("accounting", "AccountingService", "contributor_role").getOptions();
						options.add(0, null);
						cbOption.setModel(new DefaultComboBoxModel(options.toArray()));
					} catch (Exception e2) {
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
	});
    txtPercent = new ExtendJTextField("100");
    txtPercent.setName("txtPercent");
    txtPercent.setHorizontalAlignment(SwingConstants.RIGHT);
    txtDirectAward = new ExtendJTextField("0");
    txtDirectAward.setName("txtDirectAward");
    txtDirectAward.setHorizontalAlignment(SwingConstants.RIGHT);
    txtDescription = new ExtendJTextField();
    txtDescription.setName("txtDescription");

    listEmployee = new TableListEmployee();
    listEmployee.setName("listEmployee");
    listEmployee.setEmployee(projectMembers);
    listEmployee.addMouseListener(new MouseAdapter() {

      @Override
      public void mouseClicked(MouseEvent event) {
        try {
          tableOPtionsMouseClick(event);
        } catch (Exception e) {

        }

      }

    });
    listEmployee.setPreferredScrollableViewportSize(new Dimension(200, 290));
    scrollPaneTable = new JScrollPane();
    scrollPaneTable.setOpaque(false);
    scrollPaneTable.getViewport().setOpaque(false);
    scrollPaneTable.setBorder(null);
    scrollPaneTable.setViewportView(listEmployee);

    scrollIndex = new JScrollPane();
    scrollIndex.setOpaque(false);
    scrollIndex.getViewport().setOpaque(false);
    scrollIndex.setBorder(null);
    scrollIndex.setViewportView(listEmployee.getPanleButton());

    MyGroupLayout layout = new MyGroupLayout(this);
    layout.add(0, 0, lbName);
    layout.add(0, 1, txtEmployee);
    layout.add(0, 2, lbRole);
    layout.add(0, 3, cbOption);

    layout.add(1, 0, lbPercent);
    layout.add(1, 1, txtPercent);
    layout.add(1, 2, lbDirectAward);
    layout.add(1, 3, txtDirectAward);

    layout.add(2, 0, lbDescription);
    layout.add(2, 1, txtDescription);

    layout.add(3, 0, scrollIndex);
    layout.add(3, 1, scrollPaneTable);

    layout.addMessenger(lbMessenger);
    layout.updateGui();

  }

  public void setPanelProject(PanelProject panelProject) {
    this.panelProject = panelProject;
  }

  public void setListEmployee(TableListEmployee listEmployee) {
    this.listEmployee = listEmployee;
  }

  protected void tableOPtionsMouseClick(MouseEvent e) throws Exception {
    int click = 2;
    if (e.getClickCount() >= click) {
      for (int i = 0; i < projectMembers.size(); i++) {
        if (projectMembers.get(i).equals(listEmployee.getSelectedBean())) {
          j = i;
          projectMember = listEmployee.getSelectedBean();

          setData();
          setEnableCompoment(false);
          ((DialogMain) getRootPane().getParent()).showButton(false);
          refresh();
          return;
        }

      }

    }
  }

  // Đẩy dữ liệu vào đối tượng hiển thị
  private void setData() {
    try {
      Employee emp = HRModelManager.getInstance().getEmployeeByCode(projectMember.getEmployeeCode());
      txtEmployee.setObject(emp);
      txtEmployee.setText(emp.getName());
      txtEmployee.setObject(null);
    } catch (Exception e) {
      e.printStackTrace();
    }
    for (int i = 0; i < projectMembers.size(); i++) {
      for (int j = 1; j < options.size(); j++) {
        if (options.get(j).getLabel().equals(projectMembers.get(i).getRole())) {
          cbOption.setSelectedItem(options.get(j));
        }
      }
    }
    txtPercent.setText(MyDouble.valueOf(projectMember.getPercent()).toString());
    txtDirectAward.setText(MyDouble.valueOf(projectMember.getDirectAward()).toString());
    txtDescription.setText(projectMember.getStatus());

  }

  @Override
  public void save() throws Exception {
    // Kiểm tra các trường dữ liệu sau khi đổ dữ liệu vào
    if (!checkData()) {
      return;
    }
    getData();
    if (restore) {
      if (projectMembers.size() > 0) {
        loadTable();
        reset();
        panelProject.setProjectMembers(projectMembers);
      }
    }
  }

  private boolean checkData() {
    boolean check = true;
    if (txtEmployee.getText() == null || txtEmployee.getText().isEmpty() || txtEmployee.getItem() == null) {
      lbName.setForeground(Color.RED);
      check = false;
    } else
      lbName.setForeground(Color.BLACK);

    if (cbOption.getSelectedItem() == null) {
      lbRole.setForeground(Color.RED);
      check = false;
    } else
      lbRole.setForeground(Color.BLACK);

    // Kiểm tra trường Percent
    if (txtPercent.getText() == null || txtPercent.getText().isEmpty()) {
      lbPercent.setForeground(Color.RED);
      check = false;
    } else {
      // Kiểm tra định dạng chữ
      try {
        MyDouble.parseDouble(txtPercent.getText());
        lbPercent.setForeground(Color.BLACK);
      } catch (Exception e) {
        lbPercent.setForeground(Color.RED);
        check = false;
      }

    }

    // Kiểm tra trường PercentDirectAward
    try {
      // Kiểm tra định dạng chữ
      MyDouble.parseDouble(txtDirectAward.getText());
      lbDirectAward.setForeground(Color.BLACK);
    } catch (Exception e) {
      lbDirectAward.setForeground(Color.RED);
      check = false;
    }

    // Không cho nhập số âm
    if (MyDouble.parseDouble(txtPercent.getText()) < 0) {
      lbDirectAward.setForeground(Color.RED);
      check = false;
    } else
      lbDirectAward.setForeground(Color.BLACK);

    if (MyDouble.parseDouble(txtDirectAward.getText()) < 0) {
      lbDirectAward.setForeground(Color.RED);
      check = false;
    } else
      lbDirectAward.setForeground(Color.BLACK);
    // Thông báo kết quả kiểm tra ra màn hình nếu có lỗi
    if (!check) {
      restore = false;
      lbMessenger.setText("Dữ liệu vùng đỏ bị lỗi");
      lbMessenger.setForeground(Color.RED);
      lbMessenger.setVisible(true);
      return false;
    } else {
      lbMessenger.setText("");
      restore = true;
      return true;
    }
  }

  private List<ProjectMember> getData() {
    restore = true;
    try {
      if (projectMember == null) {
        projectMember = new ProjectMember();
        flag = true;
      }
      Employee employee = (Employee) txtEmployee.getItem();
      if (employee != null)
        projectMember.setEmployeeCode(employee.getCode());
      else
        projectMember.setEmployeeCode(null);

      if (cbOption.getSelectedItem() != null)
        projectMember.setRole(((Option) cbOption.getSelectedItem()).getLabel());
      else
        projectMember.setRole(null);

      if (txtPercent.getText().length() > 0)
        projectMember.setPercent(Double.parseDouble(txtPercent.getText()));
      else
        lbPercent.setForeground(Color.RED);
      if (txtDirectAward.getText().length() > 0)
        projectMember.setDirectAward(Double.parseDouble(txtDirectAward.getText()));
      else
        projectMember.setDirectAward(0);
      projectMember.setStatus(txtDescription.getText());
      lbMessenger.setText("");
      if (projectMembers.size() > 0 && flag == false) {
        projectMembers.get(j).set(projectMember);
        flag = true;
      } else {
        projectMembers.add(projectMember);
      }
      return projectMembers;
    } catch (Exception e) {
      if (projectMember.getEmployeeCode() == null | projectMember.getPercent() == 0d | projectMember.getRole() == null) {
        if (projectMember.getEmployeeCode().isEmpty())
          lbName.setForeground(Color.RED);
        else
          lbName.setForeground(Color.BLACK);

        if (projectMember.getPercent() == 0d)
          lbPercent.setForeground(Color.RED);
        else
          lbPercent.setForeground(Color.BLACK);

        if (projectMember.getRole() == null)
          lbRole.setForeground(Color.RED);
        else
          lbRole.setForeground(Color.BLACK);

        lbMessenger.setText("Dữ liệu vùng đỏ bị lỗi");
        lbMessenger.setForeground(Color.RED);
        lbMessenger.setVisible(true);
        return new ArrayList<ProjectMember>();
      }
      lbMessenger.setText("Lỗi định dạng dữ liệu");
      lbMessenger.setForeground(Color.RED);
      lbMessenger.setVisible(true);
      if (lbMessenger.getText().equals("Lỗi định dạng dữ liệu")) {
        lbPercent.setForeground(Color.RED);
        lbDirectAward.setForeground(Color.RED);
      }
      return new ArrayList<ProjectMember>();
    }

  }

  @Override
  public void edit() throws Exception {
    setEnableCompoment(true);
    flag = false;
    restore = true;

  }

  @Override
  public void delete() throws Exception {
    String str = "Xóa nhân sự ";
    Employee employee = HRModelManager.getInstance().getEmployeeByCode(projectMembers.get(j).getEmployeeCode());
    PanelChoise pnPanel = new PanelChoise(str + employee.getName() + "?");
    pnPanel.setName("Xoa");
    DialogResto dialog1 = new DialogResto(pnPanel, false, 0, 80);
    dialog1.setName("dlXoa");
    dialog1.setTitle("Xóa nhân sự");
    dialog1.setLocationRelativeTo(null);
    dialog1.setModal(true);
    dialog1.setVisible(true);

    if (pnPanel.isDelete()) {
      projectMembers.remove(j);
      panelProject.setProjectMembers(projectMembers);
      lbMessenger.setText("");
    }
    reset();
  }

  @Override
  public void reset() {
    txtEmployee.setItem(null);
    cbOption.setSelectedIndex(0);
    txtPercent.setText("100");
    txtDirectAward.setText("0");
    txtDescription.setText(null);

    lbName.setForeground(Color.BLACK);
    lbRole.setForeground(Color.BLACK);
    lbPercent.setForeground(Color.BLACK);
    lbDirectAward.setForeground(Color.BLACK);
    lbDescription.setForeground(Color.BLACK);
    lbMessenger.setText("");
    projectMember = new ProjectMember();
    setEnableCompoment(true);
    loadTable();
  }

  private void loadTable() {
    listEmployee.setEmployee(projectMembers);
  }

  private void setEnableCompoment(boolean b) {

    txtEmployee.setEnabled(b);
    cbOption.setEnabled(b);
    txtPercent.setEnabled(b);
    txtDirectAward.setEnabled(b);
    txtDescription.setEnabled(b);
  }

  @Override
  public void refresh() throws Exception {
    setData();
    setEnableCompoment(false);
    lbName.setForeground(Color.BLACK);
    lbDescription.setForeground(Color.BLACK);
    lbDirectAward.setForeground(Color.BLACK);
    lbMessenger.setText("");
    lbPercent.setForeground(Color.BLACK);
    lbRole.setForeground(Color.BLACK);

  }

}
