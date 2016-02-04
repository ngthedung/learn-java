package com.hkt.client.swingexp.app;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.junit.Test;

import com.hkt.client.swingexp.app.banhang.screen.often.PanelTimeKara;
import com.hkt.client.swingexp.app.banhang.screen.often.PanelTimed;
import com.hkt.client.swingexp.app.component.TextPopup;
import com.hkt.client.swingexp.app.core.DialogMain;
import com.hkt.client.swingexp.app.core.DialogResto;
import com.hkt.client.swingexp.app.core.GoogleMail;
import com.hkt.client.swingexp.app.core.IDialogResto;
import com.hkt.client.swingexp.app.core.MyDouble;
import com.hkt.client.swingexp.app.khohang.PanelInventory;
import com.hkt.client.swingexp.app.khuvucbanhang.PanelManageCodes;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.client.swingexp.model.AccountingModelManager;
import com.hkt.client.swingexp.model.CustomerModelManager;
import com.hkt.client.swingexp.model.HRModelManager;
import com.hkt.client.swingexp.model.ProductModelManager;
import com.hkt.client.swingexp.model.RestaurantModelManager;
import com.hkt.client.swingexp.model.WarehouseModelManager;
import com.hkt.module.account.entity.AccountGroup;
import com.hkt.module.account.entity.AccountMembership;
import com.hkt.module.accounting.DefaultInvoiceCalculator;
import com.hkt.module.accounting.entity.Contributor;
import com.hkt.module.accounting.entity.Invoice;
import com.hkt.module.accounting.entity.InvoiceDetail;
import com.hkt.module.accounting.entity.InvoiceItem;
import com.hkt.module.accounting.entity.InvoiceTransaction;
import com.hkt.module.accounting.entity.Invoice.ActivityType;
import com.hkt.module.accounting.entity.Invoice.Status;
import com.hkt.module.accounting.entity.InvoiceTransaction.TransactionType;
import com.hkt.module.config.locale.Currency;
import com.hkt.module.core.entity.ReportTable;
import com.hkt.module.core.entity.SQLSelectQuery;
import com.hkt.module.hr.entity.Employee;
import com.hkt.module.partner.customer.entity.CreditTransaction;
import com.hkt.module.partner.supplier.entity.Supplier;
import com.hkt.module.product.entity.Product;
import com.hkt.module.restaurant.entity.Recipe;
import com.hkt.module.restaurant.entity.RecipeIngredient;
import com.hkt.module.warehouse.entity.IdentityProduct;
import com.hkt.module.warehouse.entity.IdentityProduct.ExportType;
import com.hkt.module.warehouse.entity.IdentityProduct.ImportType;
import com.hkt.swingexp.app.report.entity.ReportSalesEntity;
import com.hkt.util.text.DateUtil;

public class MainTest {
	@Test
	public void test() throws Exception {
	
		
	}

	@Test
	public void testInvoiceItem() throws Exception {
		PanelInventory t = new PanelInventory();
		DialogResto dialog = new DialogResto(t, true, 700, 500);
		dialog.setTitle("Máy in khu vực");
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
	}

	@Test
	public void test1() throws Exception {

		try {
			PanelTimed panel = new PanelTimed();
			panel.initEvent(new InvoiceDetail());
			DialogResto dialog = new DialogResto(panel, false, 450, 220);
			dialog.setTitle("Thêm ngày giờ kara");
			dialog.setBtnExt3(panel.getBtnPrint());
			dialog.setLocationRelativeTo(null);
			dialog.setModal(true);
			dialog.setVisible(true);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	@Test
	public void test2() throws Exception {
		String sql = "update `invoiceDetail` set `projectCode` = \"/DN20110200\" where"
		    + " projectCode = \"project-other\" or projectCode is null";
		System.out.println(sql.toString());
		AccountingModelManager.getInstance().executeSQL(sql.toString());

		String sql2 = "update `invoiceTransaction` set `projectCode` = \"/DN20110200\" where"
		    + " projectCode = \"project-other\" or projectCode is null";
		System.out.println(sql.toString());
		AccountingModelManager.getInstance().executeSQL(sql2.toString());

	}

	@Test
	public void nen() {
		String str1 = "E:\\hkt\\server\\target\\server-release\\server";
		String str2 = "E:\\hkt\\server\\target\\server-release\\server.zip";
		zipDirectory(new File(str1), new File(str2));

	}

	@Test
	public void nenUbuntu() {
		String str1 = "/home/longnt/Desktop/hkt/server/target/server-release/server";
		String str2 = "/home/longnt/Desktop/hkt/server/target/server-release/server.zip";
		zipDirectory(new File(str1), new File(str2));

	}

	@Test
	public void nen1() {
		String str1 = "C:\\HKTSoft4.0\\DataBase\\mysql";
		String str2 = "C:\\HKTSoft4.0\\DataBase\\mysql.zip";
		zipDirectory(new File(str1), new File(str2));
	}

	@Test
	public void nenMysqlUbuntu() {
		String str1 = "/home/longnt/Share\\mysql";
		String str2 = "/home/longnt/Share\\mysql.zip";
		zipDirectory(new File(str1), new File(str2));
	}

	@Test
	public void giaiNen() {
		String str2 = "E:\\hkt\\server\\target\\server-release\\server.zip";
		zipFile(getFile(str2), "E:\\hkt\\server\\target\\server-release\\server1");
	}

	private InputStream getFile(String str) {
		try {
			InputStream fileStream = new FileInputStream(str);
			return fileStream;
		} catch (Exception e) {
			return null;
		}
	}

	private void zipFile(InputStream zipFile, String outputFolder) {
		try {
			ZipInputStream zis = new ZipInputStream(zipFile);
			ZipEntry ze = zis.getNextEntry();
			while (ze != null) {
				String entryName = ze.getName();
				System.out.print("Extracting " + entryName + " -> " + outputFolder + File.separator + entryName + "...");
				File f = new File(outputFolder + File.separator + entryName);
				// create all folder needed to store in correct relative path.
				f.getParentFile().mkdirs();
				FileOutputStream fos = new FileOutputStream(f);
				int len;
				byte buffer[] = new byte[1024];
				try {
					while ((len = zis.read(buffer)) > 0) {
						fos.write(buffer, 0, len);
					}
				} catch (Exception e) {
				}

				fos.close();
				System.out.println("OK!");
				ze = zis.getNextEntry();
			}
			zis.closeEntry();
			zis.close();
		} catch (Exception e) {
		}

		System.out.println(zipFile + " unzipped successfully");
	}

	public void zipDirectory(File inputDir, File outputZipFile) {
		// Tạo thư mục cha cho file output.
		// Create parent directory for output file
		outputZipFile.getParentFile().mkdirs();

		String inputDirPath = inputDir.getAbsolutePath();
		byte[] buffer = new byte[1024];

		FileOutputStream fileOs = null;
		ZipOutputStream zipOs = null;
		try {
			// Tất cả các file con cháu,.. trong inputDir.
			List<File> allFiles = this.listChildFiles(inputDir);
			// Bây giờ thì zip lần lượt từng file.
			// Tạo ZipOutputStream để ghi file zip.
			fileOs = new FileOutputStream(outputZipFile);
			//
			zipOs = new ZipOutputStream(fileOs);
			for (File file : allFiles) {
				String filePath = file.getAbsolutePath();

				System.out.println("Zipping " + filePath);
				// entryName chính là đường dẫn tương đối.
				String entryName = filePath.substring(inputDirPath.length() + 1);

				ZipEntry ze = new ZipEntry(entryName);
				// Thêm entry vào file zip.
				zipOs.putNextEntry(ze);
				// Đọc dữ liệu file cần zip và ghi vào luồng zip
				FileInputStream fileIs = new FileInputStream(filePath);

				int len;
				while ((len = fileIs.read(buffer)) > 0) {
					zipOs.write(buffer, 0, len);
				}
				fileIs.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeQuite(zipOs);
			closeQuite(fileOs);
		}

	}

	private void closeQuite(OutputStream out) {
		try {
			out.close();
		} catch (Exception e) {
		}
	}

	private List<File> listChildFiles(File dir) throws IOException {
		List<File> allFiles = new ArrayList<File>();

		// Danh sách các file con trực tiếp của thư mục.
		System.out.println(dir);
		File[] childFiles = dir.listFiles();
		System.out.println(childFiles);
		for (File file : childFiles) {
			if (file.isFile()) {
				allFiles.add(file);
			} else {
				// Gọi đệ quy.
				List<File> files = this.listChildFiles(file);
				allFiles.addAll(files);
			}
		}
		return allFiles;
	}

}
