package com.hkt.client.swingexp.app.component;

import java.awt.Component;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import com.hkt.client.swingexp.app.banhang.screen.often.DialogConfig;
import com.hkt.client.swingexp.app.core.TextFieldObservable;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.GuiModelManager;
import com.hkt.module.account.entity.Profile;
import com.hkt.module.core.entity.AbstractPersistable;

public class TextPopup<E> extends TextFieldObservable {
	private Component								component, component2;
	public static int								INDEX		= 0;
	private int											value		= 0;
	private int											widthD	= 0;
	private DefaultTableModelCommon	model;
	private Object									object;
	private E												item;
	private boolean									clearText1;
	private boolean									clearText;
	private boolean									viewCode;
	protected List<E>								data		= new ArrayList<E>();
	private List<String>						filters	= new ArrayList<String>();
	private Class<E>								class1;
	public final static String			LOAD		= "load";
	private Profile									profile;
	private PopupFilter<E>					popupFilter;

	public TextPopup(Class<E> class2) {
		this.class1 = class2;
		filters.add("toString");
		filters.add("code");
		filters.add("description");

		filters.add("mobile");
		this.setFont(new Font("Tahoma", Font.PLAIN, 14));
		this.addCaretListener(new CaretListener() {
			@Override
			public void caretUpdate(CaretEvent e) {
				if (object != null) {
					visiblePop();
					item = (E) object;
					// object = null;
					if (component == null || !(component instanceof JTextField) || clearText1) {
						changeFilter(item);
						return;
					}
				} else {
					if (getText().trim().length() > 0) {
						loadModelTxtSearch();
					} else {
						item = null;
						changeFilter(null);

					}
				}
				//
		    // if (item != null) {
		    // if (getText().trim().isEmpty()) {
		    // changeFilter(null);
		    // item = null;
		    // }
		    // }
			}
		});
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == 3) {
					try {
						new GuiModelManager<E>().getDialog(TextPopup.this, class1);
						changeFilter(LOAD);
					} catch (Exception e2) {
						e2.printStackTrace();
					}

				}
			}
		});
	}

	public void showView() {
		if (object == null) {
			popupFilter = new PopupFilter<E>(this, model);
			if (getText().trim().length() > 0) {
				if (model.getRowCount() == 0) {
					popupFilter.showPopup();
					this.requestFocus();
					popupFilter.setVisible(false);
				} else {
					popupFilter.showPopup();
					this.requestFocus();
				}
			} else {
				popupFilter.showPopup();
				this.requestFocus();
				popupFilter.setVisible(false);
			}
		} else {
			popupFilter.setVisible(false);
		}
	}

	private void visiblePop() {
		if (popupFilter != null) {
			popupFilter.setVisible(false);
		}
	}

	public void setData(List<E> list) {
		this.data = list;
	}

	public List<E> getData() {
		return data;
	}

	public void setFilter(List<String> list) {
		this.filters = list;
	}

	public void setViewCode(boolean viewCode) {
		this.viewCode = viewCode;
	}

	public Object getObject() {
		return object;
	}

	public E getItem() {
		return item;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public void setClearText(boolean clearText) {
		this.clearText = clearText;
	}

	public void setModel(DefaultTableModelCommon model) {
		this.model = model;
	}

	public void setWidthDialog(int width) {
		this.widthD = width;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public void setItem(E object) {
		if (object != null) {
			this.object = object;
			this.item = object;
			this.setText(item.toString());
			this.object = null;
		} else {
			this.item = object;
			this.setText("");
		}
	}

	public void setComponentFocus2(Component component) {
		this.component2 = component;
	}

	public void setComponentFocus(Component component) {
		this.component = component;
		if (component != null && component instanceof JTextField) {
			((JTextField) this.component).addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					JTextField txt = (JTextField) e.getSource();
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						if (item != null) {
							changeFilter(txt.getText());
							if (component2 == null) {
								setText("");
								txt.setText("");
								requestFocus();
							}
						}
					}
				}
			});
		}
	}

	public void showDialog() {
		if (model != null) {
			PopupFilter<E> popupFilter = new PopupFilter<E>(this, model);
			popupFilter.showPopup();
		}
	}

	private void loadModelTxtSearch() {
		List<AbstractPersistable> filter = CollectionUtil.getInstancce().filter(data, filters, getText());
		if (filter.size() == 2 && filter.get(0).getCode().equals(this.getText())) {
			List<AbstractPersistable> filter1 = new ArrayList<AbstractPersistable>();
			filter1.add(filter.get(1));
			filter1.add(filter.get(0));
			DefaultTableModelCommon modelProductFilter = new DefaultTableModelCommon(filter1);
			setModel(modelProductFilter);
			showView();
		} else {
			DefaultTableModelCommon modelProductFilter = new DefaultTableModelCommon(filter);
			setModel(modelProductFilter);
			showView();
		}

	}

	public class PopupFilter<E> extends JPopupMenu {
		private TextPopup<E>	textField;
		private JTable				table;

		public PopupFilter(TextPopup txtSearch, DefaultTableModelCommon defaultTableModelCommon) {
			this.textField = txtSearch;
			table = new JTable();
			table.setModel(defaultTableModelCommon);
			add(table);
			table.setRowHeight(23);
			table.getColumnModel().getColumn(1).setMaxWidth(100);
			table.setFont(new Font("Tahoma", Font.PLAIN, 14));

			if (table.getRowCount() >= 0) {
				table.changeSelection(0, 0, true, false);
			}

			table.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					JTable table = (JTable) e.getSource();
					switch (e.getKeyCode()) {
						case KeyEvent.VK_ESCAPE:
							setVisible(false);
							return;
						case KeyEvent.VK_ENTER:
							// textField.setText(" ");
							try {
								object = table.getValueAt(table.getSelectedRow(), value);
							} catch (Exception e1) {
								textField.setText("");
								setVisible(false);
								return;
							}
							if (object != null) {
								if (clearText) {
									textField.setText("");
									object = null;
								} else {
									if (viewCode) {
										textField.setText(table.getValueAt(table.getSelectedRow(), 0).toString());
										object = null;
									} else {
										textField.setText(object.toString());
										object = null;
									}
								}
								setVisible(false);
								if (component instanceof JTextField) {
									if (((JTextField) component).getText().trim().isEmpty() && !(component instanceof TextPopup)) {
										((JTextField) component).setText("1");
									}
									((JTextField) component).setSelectionStart(0);
									component.requestFocus();
								}
								table.clearSelection();
								return;
							}
					}
				}
			});

			table.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					JTable table = (JTable) e.getSource();
					if (e.getClickCount() >= 2) {
						try {
							object = table.getValueAt(table.getSelectedRow(), value);
							try {
								profile = AccountModelManager.getInstance().getProfileConfigAdmin();
								if (profile.get(DialogConfig.Keyboard) != null
			              && profile.get(DialogConfig.Keyboard).toString().equals("true")) {
									clearText1 = true;
								} else {
									clearText1 = false;
								}
							} catch (Exception e1) {
							}
						} catch (Exception e1) {
							setVisible(false);
							return;
						}
						// textField.setText(" ");
						if (object != null) {
							if (clearText) {
								textField.setText("");
								object = null;
							} else {
								if (viewCode) {
									textField.setText(table.getValueAt(table.getSelectedRow(), 0).toString());
									object = null;
								} else {
									textField.setText(object.toString());
									object = null;
								}
							}
							setVisible(false);
							if (profile.get(DialogConfig.Keyboard) != null
			            && profile.get(DialogConfig.Keyboard).toString().equals("true")) {
								table.clearSelection();
								return;
							}
							if (component instanceof JTextField) {
								if (((JTextField) component).getText().trim().isEmpty() && !(component instanceof TextPopup)) {
									((JTextField) component).setText("1");
									((JTextField) component).setSelectionStart(0);
								}
								component.requestFocus();
							}
							table.clearSelection();
							return;
						}
					}
				}
			});
			if (textField.getKeyListeners().length > 0) {
				textField.removeKeyListener(textField.getKeyListeners()[0]);
			}

			textField.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					switch (e.getKeyCode()) {
						case KeyEvent.VK_DOWN:
							table.clearSelection();
							table.changeSelection(1, 1, true, false);
							table.requestFocus();
							return;
						case KeyEvent.VK_ENTER:
							if (table.getSelectedRow() >= 0) {
								try {
									object = table.getValueAt(table.getSelectedRow(), value);
								} catch (Exception e1) {
									setVisible(false);
									return;
								}
								// textField.setText(" ");
								if (object != null) {
									if (clearText) {
										textField.setText("");
										object = null;
										setVisible(false);
									} else {
										if (viewCode) {
											textField.setText(table.getValueAt(table.getSelectedRow(), 0).toString());
											object = null;
											setVisible(false);
										} else {
											textField.setText(object.toString());
											object = null;
											setVisible(false);
										}
									}

									if (component instanceof JTextField) {
										if (((JTextField) component).getText().trim().isEmpty() && !(component instanceof TextPopup)) {
											((JTextField) component).setText("1");
											((JTextField) component).setSelectionStart(0);
										}
										component.requestFocus();
									}
									table.clearSelection();
									return;
								}
							}
					}
				}
			});
		}

		public JTextField getTxtSearch() {
			return textField;
		}

		public void setTxtSearch(TextPopup txtSearch) {
			this.textField = txtSearch;
		}

		public void showPopup() {

			if (getTxtSearch().isShowing()) {
				int wd = getTxtSearch().getWidth();
				if (widthD != 0) {
					wd = widthD;
				}

				if (table.getRowCount() > 10) {
					int h = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
					int y = (int) textField.getLocationOnScreen().getY();
					if ((h - y - 25) < 250) {
						setPopupSize(wd, 5 * 23);
					} else {
						setPopupSize(wd, table.getRowHeight() * 10);
					}

				} else {
					int h = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
					int y = (int) textField.getLocationOnScreen().getY();
					if ((h - y - 25) < 250) {
						int a = table.getRowHeight() * table.getRowCount();
						int b = 5 * 23;
						if (a > b) {
							setPopupSize(wd, b);
						} else {
							setPopupSize(wd, a);
						}
					} else {
						setPopupSize(wd, table.getRowHeight() * table.getRowCount());
					}

				}

				show(getTxtSearch(), 0, getTxtSearch().getHeight());
			}
		}

	}

	private void changeFilter(Object object) {
		setChanged();
		if (object == null || !object.equals(LOAD)) {
			notifyObservers(item, object);
		} else {
			notifyObservers(class1, object);
		}

	}

}
