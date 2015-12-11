/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ireport;

/**
 *
 * @author longnt
 */
import java.awt.Dialog.ModalExclusionType;
import java.net.URL;
import java.util.HashMap;

import javax.print.PrintService;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaSize;
import javax.print.attribute.standard.MediaSizeName;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.view.JasperViewer;

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
                print = JasperFillManager.fillReport(url.openConnection().getInputStream(), hashMap, new JRTableModelDataSource(model));
            }
            JasperViewer jv = new JasperViewer(print, false);
            jv.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
            jv.setVisible(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void printReport(String filePathResoutce, HashMap hashMap, TableModel model, PrintService printService, boolean flagExtension) {
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
                    print = JasperFillManager.fillReport(url.openConnection().getInputStream(), hashMap, new JRTableModelDataSource(model));
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
                exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, printService.getAttributes());
                exporter.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, printRequestAttributeSet);
                exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
                exporter.exportReport();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Không có máy in");
            }
        }

    }

}
