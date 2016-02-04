package com.hkt.client.swingexp.app;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import org.apache.commons.codec.binary.Base64;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.junit.Test;

import com.hkt.client.swingexp.app.banhang.PanelSearchInvoice;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.HKTFile;
import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.client.swingexp.app.hethong.PaneSetDate;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.client.swingexp.model.RestaurantModelManager;
import com.hkt.client.swingexp.model.UIConfigModelManager;
import com.hkt.util.FileUtil;

public class TestAuthorization {
	private HSSFWorkbook workbook;
	private HSSFSheet sheet;
	private FileInputStream fileInputStream;
	private POIFSFileSystem fileSystem;
	private FileOutputStream fileOutputStream;
	private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

	@Test
	public void test() throws Exception {
		ProcessBuilder pb = new ProcessBuilder("java", "-jar", "absolute path upto jar");
		Process p = pb.start();
	}
	
	public void listFilesForFolder(final File folder) {
    for (final File fileEntry : folder.listFiles()) {
        try {
	        FileUtil.removeIfExist(fileEntry.getAbsolutePath());
        } catch (Exception e) {
	        // TODO: handle exception
        }
    }
}



	public class AA {
		private String name;
		private List<AA> list;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public List<AA> getList() {
			return list;
		}

		public void setList(List<AA> list) {
			this.list = list;
		}

		@Override
		public String toString() {
			return "AA [name=" + name + "]";
		}

	}

	public class FileFilterExtension extends FileFilter {

		private String extansion;

		public FileFilterExtension(String extansion) {
			this.extansion = extansion;
		}

		@Override
		public boolean accept(File f) {
			return f.isDirectory() || f.getName().toLowerCase().endsWith(extansion);
		}

		// Set description for the type of file that should be display
		@Override
		public String getDescription() {
			return extansion;
		}
	}

	private MyDouble getMoney(double moneyMissing1, double rounding) {
		MyDouble a = new MyDouble(moneyMissing1);
		if (rounding != 0) {
			if (rounding > 0) {
				moneyMissing1 = moneyMissing1 / rounding;
				MyDouble b = new MyDouble(moneyMissing1);
				b.setNumDot(0);
				if (moneyMissing1 > new MyDouble(b.toString()).doubleValue()) {
					moneyMissing1 = moneyMissing1 + 1;
				}
				MyDouble d = new MyDouble(moneyMissing1);
				d.setNumDot(0);
				MyDouble c = new MyDouble(d.toString());
				moneyMissing1 = c.doubleValue() * rounding;
				a = new MyDouble(moneyMissing1);
				a.setNumDot(0);
				moneyMissing1 = a.doubleValue();
			} else {
				try {
					int k = MyDouble.valueOf(rounding).intValue() * (-1);
					a = new MyDouble(moneyMissing1);
					a.setNumDot(k);
				} catch (Exception e) {
				}
			}
		}
		return a;
	}

	public void runImport(String fileName) throws Exception {
		try {
			fileInputStream = new FileInputStream(fileName);
			fileSystem = new POIFSFileSystem(fileInputStream);
			workbook = new HSSFWorkbook(fileSystem);
			int sheetCount = workbook.getNumberOfSheets();
			int i;
			for (i = 0; i < sheetCount; i++) {
				sheet = workbook.getSheetAt(i);
				String sheetName = sheet.getSheetName();

				// ///////Xu ly du lieu tren tung Row ///////////
				int rowCurrsor = 0; // con tro danh dau header
				List<String> headers = getListHeader(sheet);

				for (Row row : sheet) {
					try {
						if (rowCurrsor != 0) { // Neu khong phai la header

							for (Cell cell : row) {
								String s = String.valueOf(cell);
								System.out.println(s);
								// int column = cell.getColumnIndex(); // Lay vi tri cot cua
								// cell
								// String heahName = headers.get(column);
								// if (heahName.equals("id")) {
								// float f = Float.parseFloat(String.valueOf(cell));
								// long l = (long) f;
								// }
								// int index = checkIn(heahName, fs); // Lay vi tri cua Field
								// if (index > -1) {
								// Field field = fs[index];
								// field.setAccessible(true);
								// commitField(field, cell);
								// }

							}
						}
						rowCurrsor++;
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, sheetName + " row: " + rowCurrsor);
						ex.printStackTrace();
					}
				}

			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Đã xảy ra lỗi trong quá trình xử lý import!" + e.toString());
		}
	}

	private Field[] getActualFiels(Field[] fields) {
		List<Field> list = new ArrayList<Field>();
		Field[] fs = {};
		for (Field f : fields) {
			String fieldDeclare = ReflectionManager.getDeclaredField(f);
			if (fieldDeclare.equals("private") || fieldDeclare.equals("public")) {
				list.add(f);
			}
		}
		if (list != null && !list.isEmpty()) {
			Field[] fie = new Field[list.size()];
			for (int i = 0; i < list.size(); i++) {
				fie[i] = list.get(i);
			}
			fs = fie;
		}
		return fs;
	}

	private int checkIn(String heahName, Field[] fields) {
		if (fields.length != 0) {
			int n = fields.length, i;
			for (i = 0; i < n; i++) {
				Field field = fields[i];
				String name = ReflectionManager.getFieldName(field);
				if (heahName.toLowerCase().equals(name.toLowerCase())) {
					return i;
				}
			}
		}
		return -1;
	}

	private List<String> getListHeader(HSSFSheet sheet) {
		List<String> list = new ArrayList<String>();
		int row = 0;
		for (Row r : sheet) {
			if (row == 0) {
				for (Cell c : r) {
					list.add(String.valueOf(c));
				}
				break;
			}
			row++;
		}
		return list;
	}

	private void commitField(Field field, Cell cell) {
		String s = String.valueOf(cell);
		Object object = null;
		String returnType = ReflectionManager.getReturnTypeOfField(field);
		if (!s.toLowerCase().equals("null") && s.trim().length() != 0) {
			try {
				if (returnType.equals("String")) {
					try {
						object = s;
					} catch (IllegalArgumentException ex) {

					}
				}
				if (returnType.equals("int")) {
					try {
						float f = Float.parseFloat(s);
						int i = (int) f;
						object = i;
					} catch (IllegalArgumentException ex) {

					}
				}

				if (returnType.equals("float")) {
					try {
						object = Float.parseFloat(s);
					} catch (IllegalArgumentException ex) {

					}
				}

				if (returnType.equals("byte[]")) {
					try {
						object = ByteConvertor.decodeString(s);
					} catch (Exception e) {
					}
				}

				if (returnType.equals("List")) {
					try {
						/**
						 * Chuyen tu string --> byte[] --> List<Integer>
						 */
						byte[] bs = ByteConvertor.decodeString(s);
						List<Integer> integers = ByteConvertor.byteToListInteger(bs);
						object = integers;
					} catch (Exception e) {
					}
				}

				if (returnType.equals("BigDecimal")) {
					try {
						object = new BigDecimal(s);
					} catch (Exception e) {
						object = new BigDecimal(0);
					}
				}

				if (returnType.equals("double")) {
					try {
						object = Double.parseDouble(s);
					} catch (IllegalArgumentException ex) {

					}
				}

				if (returnType.equals("long")) {
					try {
						float f = Float.parseFloat(s);
						long l = (long) f;
						object = l;
					} catch (IllegalArgumentException ex) {
						JOptionPane.showMessageDialog(null, "Lỗi " + field.getName() + " --> " + s);

					}
				}

				if (returnType.equals("Date")) {
					try {
						object = (Date) dateFormat.parse(s);
					} catch (Exception ex) {
						object = new Date();
					}
				}

				if (returnType.equals("Calendar")) {
					try {
						Date d = (Date) dateFormat.parse(s);
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(d);
						object = calendar;
					} catch (Exception ex) {
					}
				}

				if (returnType.equals("boolean")) {
					try {
						object = (boolean) s.equals("true");
					} catch (IllegalArgumentException ex) {
					}
				}

				field.set(null, object);
				s = "";
			} catch (IllegalArgumentException ex) {
				s = "";
			} catch (IllegalAccessException ex) {
				s = "";

			}
		}
	}

	private void replaceMain(Hashtable<String, Hashtable> hashMain) {
		Set set = hashMain.keySet();
		Iterator it = set.iterator();
		while (it.hasNext()) {
			String entityName = String.valueOf(it.next());
			Hashtable<Long, Long> h = hashMain.get(entityName);
			// refactorId.replaceId(h, entityName);
		}
	}

	public static class ByteConvertor {

		public static List<Integer> byteToListInteger(byte[] bs) {
			List<Integer> strings = new ArrayList<Integer>();
			ByteArrayInputStream bais = new ByteArrayInputStream(bs);
			DataInputStream in = new DataInputStream(bais);
			try {
				while (in.available() > 0) {
					String element = in.readUTF();
					strings.add(Integer.parseInt(element));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return strings;
		}

		public static byte[] listIntegerToByte(List<Integer> list) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			DataOutputStream out = new DataOutputStream(baos);
			for (Integer element : list) {
				try {
					out.writeUTF(String.valueOf(element));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			byte[] bytes = baos.toByteArray();
			return bytes;
		}

		public static String encodeByte(byte[] imageByteArray) {
			return Base64.encodeBase64URLSafeString(imageByteArray);
		}

		/**
		 * Decodes the base64 string into byte array
		 * 
		 * @param imageDataString
		 *          - a {@link java.lang.String}
		 * @return byte array
		 */
		public static byte[] decodeString(String imageDataString) {
			return Base64.decodeBase64(imageDataString);
		}
	}

	public static class ReflectionManager {

		private static Class clsInput;

		public ReflectionManager() {
		}

		public ReflectionManager(Class clsInput) {
			this.clsInput = clsInput;
		}

		public Class getClsInput() {
			return clsInput;
		}

		public void setClsInput(Class clsInput) {
			this.clsInput = clsInput;
		}

		public Field[] getListField() {
			return clsInput.getDeclaredFields();
		}

		public Method[] getListMethod() {
			return clsInput.getDeclaredMethods();
		}

		public static String getFieldName(Field f) {
			return f.getName();
		}

		public static String getDeclaredField(Field f) {
			int mod = f.getModifiers();
			String declared = Modifier.toString(mod);
			return declared;
		}

		public static String getReturnTypeOfField(Field f) {
			Class c = f.getType();
			String type = c.getSimpleName();
			return type;
		}

		public static String getMethodName(Method m) {
			return m.getName();
		}

		public static String getDeclaredMethod(Method m) {
			int mod = m.getModifiers();
			String declared = Modifier.toString(mod);
			return declared;
		}

		public static String getReturnTypeOfMethod(Method m) {
			Class c = m.getReturnType();
			String type = c.getSimpleName();
			return type;
		}

		/**
		 * Lay thong tin Constructor
		 */
		public static Constructor getConstructor() {
			Constructor[] cons = clsInput.getDeclaredConstructors();
			for (Constructor c : cons) {
				Class cl[] = c.getParameterTypes();
				if (cl.length == 0) {
					return c;
				}
			}
			return null;
		}

		/**
		 * 
		 * @param methodName
		 *          : ten method
		 * @param inputMethod
		 *          : Danh sach type dau vao cua method
		 * @param methobj
		 *          : Doi tuong chua method
		 * @param arglist
		 *          : Danh sach value truyen cho type dau vao
		 * @return
		 */
		public static Object runMethod(String methodName, Class[] inputMethod, Object methobj, Object arglist[]) {
			try {
				Method meth = clsInput.getMethod(methodName, inputMethod);
				meth.setAccessible(true);
				Object retobj = meth.invoke(methobj, arglist);
				return retobj;
			} catch (Throwable e) {
				System.err.println(e);
				return "";
			}
		}

		public static Object runMethod(Method method, Object methobj) {
			try {
				Object[] arglist = {};
				method.setAccessible(true);
				Object retobj = method.invoke(methobj, arglist);
				return retobj;
			} catch (Throwable e) {
				System.err.println(e);
				return "";
			}
		}
	}

}
