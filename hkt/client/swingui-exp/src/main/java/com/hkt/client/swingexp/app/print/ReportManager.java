package com.hkt.client.swingexp.app.print;

import java.awt.Dialog.ModalExclusionType;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.print.PrintService;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.MediaSize;
import javax.print.attribute.standard.MediaSizeName;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.engine.export.FontKey;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.PdfFont;
import net.sf.jasperreports.view.JasperViewer;

import com.hkt.client.swingexp.app.core.HKTFile;
import com.hkt.client.swingexp.model.AccountModelManager;
import com.hkt.module.account.entity.Profile;

public class ReportManager {

	private static ReportManager manager;

	public static ReportManager getInstance() {
		if (manager == null) {
			manager = new ReportManager();
		}
		return manager;
	}

	private ReportManager() {
	}

	public void viewReport(String filePathResoutce, HashMap hashMap, TableModel model, boolean flagExtension) {
		try {
			JasperPrint print;
			if (flagExtension) {
				print = JasperFillManager.fillReport(filePathResoutce, hashMap, new JRTableModelDataSource(model));
			} else {
				URL url = getClass().getResource(filePathResoutce);
				print = JasperFillManager.fillReport(url.openConnection().getInputStream(), hashMap,
				    new JRTableModelDataSource(model));
			}
			JasperViewer jv = new JasperViewer(print, false);
			jv.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
			jv.setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	public void exelReport(String filePathResoutce, HashMap hashMap, TableModel model, boolean flagExtension, String name) {
		try {
			JasperPrint print;
			if (flagExtension) {
				print = JasperFillManager.fillReport(filePathResoutce, hashMap, new JRTableModelDataSource(model));
			} else {
				URL url = getClass().getResource(filePathResoutce);
				print = JasperFillManager.fillReport(url.openConnection().getInputStream(), hashMap,
				    new JRTableModelDataSource(model));
			}

			Profile profile = AccountModelManager.getInstance().getProfileConfigAdmin();
			if (profile.get("Exel") != null) {
				//
				// Code xuất ra exel
				ByteArrayOutputStream output = new ByteArrayOutputStream();
				name = HKTFile.getDirectory("Hoa Don") + "/" + name.replace(":", "_") + ".xls";
				System.out.println(name);
				OutputStream outputfile = new FileOutputStream(new File(name));
				//
				// coding For Excel:
				JRXlsExporter exporterXLS = new JRXlsExporter();
				exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, print);
				exporterXLS.setParameter(JRXlsExporterParameter.CHARACTER_ENCODING, "UTF-8");
				exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, output);
				exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
				exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
				exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
				exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
				exporterXLS.exportReport();
				outputfile.write(output.toByteArray());
			}

			if (profile.get("Pdf") != null) {
				ByteArrayOutputStream output = new ByteArrayOutputStream();
				if (name.endsWith("xls")) {
					name = name.replace("xls", "pdf");
				} else {
					name = HKTFile.getDirectory("Hoa Don") + "\\" + name.replace(":", "_") + ".pdf";
				}

				// System.out.println(name);
				OutputStream outputfile = new FileOutputStream(new File(name));

				// coding For Excel:
				JRPdfExporter exporterXLS = new JRPdfExporter();
				exporterXLS.setParameter(JRPdfExporterParameter.JASPER_PRINT, print);
				exporterXLS.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, output);
				exporterXLS.setParameter(JRPdfExporterParameter.CHARACTER_ENCODING, "UTF-8");
				Map fontMap = new HashMap();
				fontMap.put(new FontKey("Tahoma", true, false), new PdfFont("C:/windows/Fonts/Tahoma.TTF",
				    com.lowagie.text.pdf.BaseFont.IDENTITY_H, true));
				exporterXLS.setParameter(JRPdfExporterParameter.FONT_MAP, fontMap);
				exporterXLS.exportReport();
				outputfile.write(output.toByteArray());
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	@SuppressWarnings("deprecation")
	public void exelReportMail(String filePathResoutce, HashMap hashMap, TableModel model, boolean flagExtension, String name) {
		try {
			JasperPrint print;
			if (flagExtension) {
				print = JasperFillManager.fillReport(filePathResoutce, hashMap, new JRTableModelDataSource(model));
			} else {
				URL url = getClass().getResource(filePathResoutce);
				print = JasperFillManager.fillReport(url.openConnection().getInputStream(), hashMap,
				    new JRTableModelDataSource(model));
			}
				//
				// Code xuất ra exel
				ByteArrayOutputStream output = new ByteArrayOutputStream();
				name = HKTFile.getDirectory("Hoa Don") + "/" + name.replace(":", "_") + ".xls";
				System.out.println(name);
				OutputStream outputfile = new FileOutputStream(new File(name));
				//
				// coding For Excel:
				JRXlsExporter exporterXLS = new JRXlsExporter();
				exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, print);
				exporterXLS.setParameter(JRXlsExporterParameter.CHARACTER_ENCODING, "UTF-8");
				exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, output);
				exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
				exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
				exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
				exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
				exporterXLS.exportReport();
				outputfile.write(output.toByteArray());
			

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void exelReportMailPayment(String filePathResoutce, HashMap hashMap, TableModel model, boolean flagExtension, String name) {
		try {
			JasperPrint print;
			if (flagExtension) {
				print = JasperFillManager.fillReport(filePathResoutce, hashMap, new JRTableModelDataSource(model));
			} else {
				URL url = getClass().getResource(filePathResoutce);
				print = JasperFillManager.fillReport(url.openConnection().getInputStream(), hashMap,
				    new JRTableModelDataSource(model));
			}
				//
				// Code xuất ra exel
				ByteArrayOutputStream output = new ByteArrayOutputStream();
				HKTFile.deleteFilesForFolder(HKTFile.getFile("SendMail", ""));
				name = HKTFile.getDirectory("SendMail") + "/" + name.replace(":", "_") + ".xls";
				System.out.println(name);
				OutputStream outputfile = new FileOutputStream(new File(name));
				//
				// coding For Excel:
				JRXlsExporter exporterXLS = new JRXlsExporter();
				exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, print);
				exporterXLS.setParameter(JRXlsExporterParameter.CHARACTER_ENCODING, "UTF-8");
				exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, output);
				exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
				exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
				exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
				exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
				exporterXLS.exportReport();
				outputfile.write(output.toByteArray());
			

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void printReport(String filePathResoutce, HashMap hashMap, TableModel model, PrintService printService,
	    boolean flagExtension) {
		int k80 = 1000;
		if (filePathResoutce.endsWith("K57.jasper")) {
			k80 = 63;
		}
		if (filePathResoutce.endsWith("K80.jasper")) {
			k80 = 97;
		}
		if (model.getRowCount() > k80) {
			viewReport(filePathResoutce, hashMap, model, flagExtension);
		} else {
			try {
				JasperPrint print;
				if (flagExtension) {
					print = JasperFillManager.fillReport(filePathResoutce, hashMap, new JRTableModelDataSource(model));
				} else {
					URL url = getClass().getResource(filePathResoutce);
					print = JasperFillManager.fillReport(url.openConnection().getInputStream(), hashMap,
					    new JRTableModelDataSource(model));
				}
				PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
				if (filePathResoutce.endsWith("K57.jasper") || filePathResoutce.endsWith("K80.jasper")) {
					printRequestAttributeSet.add(MediaSize.ISO.A2.getMediaSizeName());
				} else {
					MediaSizeName mediaSizeName;
					if (filePathResoutce.endsWith("A6.jasper")) {
						mediaSizeName = MediaSize.ISO.A6.getMediaSizeName();
					} else {
						if (filePathResoutce.endsWith("A5.jasper")) {
							mediaSizeName = MediaSize.ISO.A5.getMediaSizeName();
						} else {
							mediaSizeName = MediaSize.ISO.A4.getMediaSizeName();
						}
					}
					printRequestAttributeSet.add(mediaSizeName);
				}
				printRequestAttributeSet.add(new Copies(1));
				JRPrintServiceExporter exporter;
				exporter = new JRPrintServiceExporter();
				exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
				exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE, printService);
				exporter
				    .setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, printService.getAttributes());
				exporter.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, printRequestAttributeSet);
				exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
				exporter.exportReport();
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Không có máy in");
			}
		}

	}

	public void printReportSize(String filePathResoutce, HashMap hashMap, TableModel model, PrintService printService,
	    boolean flagExtension, String size) {
			int k80 = 1000;
			if (size.equals("K80")) {
				k80 = 97;
			}
			if (model.getRowCount() > k80) {
				viewReport(filePathResoutce, hashMap, model, flagExtension);
			}
		try {
			JasperPrint print;
			if (flagExtension) {
				print = JasperFillManager.fillReport(filePathResoutce, hashMap, new JRTableModelDataSource(model));
			} else {
				URL url = getClass().getResource(filePathResoutce);
				print = JasperFillManager.fillReport(url.openConnection().getInputStream(), hashMap,
				    new JRTableModelDataSource(model));
			}
			PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
			if (size.equals("K80")) {
				printRequestAttributeSet.add(MediaSize.ISO.A2.getMediaSizeName());
			}else {
				MediaSizeName mediaSizeName;
				if (size.endsWith("A5")) {
					mediaSizeName = MediaSize.ISO.A5.getMediaSizeName();
				} else {
					if (size.endsWith("A6")) {
						mediaSizeName = MediaSize.ISO.A6.getMediaSizeName();
					} else {
						mediaSizeName = MediaSize.ISO.A4.getMediaSizeName();
					}
				}
				printRequestAttributeSet.add(mediaSizeName);
			}
			
			printRequestAttributeSet.add(new Copies(1));
			JRPrintServiceExporter exporter;
			exporter = new JRPrintServiceExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
			exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE, printService);
			exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, printService.getAttributes());
			exporter.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, printRequestAttributeSet);
			exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
			exporter.exportReport();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Không có máy in");
		}

	}

	// Chạy phiếu in khi khởi động
	public void printReport1(String filePathResoutce, HashMap hashMap, TableModel model, PrintService printService,
	    boolean flagExtension) {
		try {
			JasperPrint print;
			if (flagExtension) {
				print = JasperFillManager.fillReport(filePathResoutce, hashMap, new JRTableModelDataSource(model));
			} else {
				URL url = getClass().getResource(filePathResoutce);
				print = JasperFillManager.fillReport(url.openConnection().getInputStream(), hashMap,
				    new JRTableModelDataSource(model));
			}
			PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
			// if (filePathResoutce.endsWith("K57.jasper")
			// ||filePathResoutce.endsWith("K80.jasper")) {
			// MediaPrintableArea media = new MediaPrintableArea(2, 2,71 ,2000,
			// MediaPrintableArea.MM);
			// printRequestAttributeSet.add(media);
			// } else {
			MediaSizeName mediaSizeName;
			if (filePathResoutce.endsWith("A6.jasper")) {
				mediaSizeName = MediaSize.ISO.A6.getMediaSizeName();
			} else {
				if (filePathResoutce.endsWith("A5.jasper")) {
					mediaSizeName = MediaSize.ISO.A5.getMediaSizeName();
				} else {
					mediaSizeName = MediaSize.ISO.A4.getMediaSizeName();
				}
			}
			printRequestAttributeSet.add(mediaSizeName);
			// }

			printRequestAttributeSet.add(new Copies(1));
			JRPrintServiceExporter exporter;
			exporter = new JRPrintServiceExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
			exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE, printService);
			exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, printService.getAttributes());
			exporter.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, printRequestAttributeSet);
			exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
			exporter.exportReport();
		} catch (Exception ex) {
		}
	}

	public void printReportBarcode(String filePathResoutce, HashMap hashMap, TableModel model, PrintService printService,
	    boolean flagExtension, int size) {
		try {
			JasperPrint print;
			if (flagExtension) {
				print = JasperFillManager.fillReport(filePathResoutce, hashMap, new JRTableModelDataSource(model));
			} else {
				URL url = getClass().getResource(filePathResoutce);
				print = JasperFillManager.fillReport(url.openConnection().getInputStream(), hashMap,
				    new JRTableModelDataSource(model));
			}
			PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
			MediaPrintableArea media = null;
			if (size < 0) {
				if (filePathResoutce.endsWith("10422.jasper")) {
				} else {
					if (filePathResoutce.endsWith("10432.jasper")) {
						media = new MediaPrintableArea(0, 0, 104, 32, MediaPrintableArea.MM);
					} else {
						if (filePathResoutce.endsWith("7022.jasper")) {
							media = new MediaPrintableArea(0, 0, 70, 22, MediaPrintableArea.MM);
						} else {
							media = new MediaPrintableArea(0, 0, 106, 22, MediaPrintableArea.MM);
						}
					}
				}
			} else {
				media = new MediaPrintableArea(0, 0, 70, size * 23, MediaPrintableArea.MM);
			}
			printRequestAttributeSet.add(media);
			printRequestAttributeSet.add(new Copies(1));
			JRPrintServiceExporter exporter;
			exporter = new JRPrintServiceExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
			exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE, printService);
			exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, printService.getAttributes());
			exporter.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, printRequestAttributeSet);
			exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
			exporter.exportReport();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Không có máy in");
		}
	}
}
