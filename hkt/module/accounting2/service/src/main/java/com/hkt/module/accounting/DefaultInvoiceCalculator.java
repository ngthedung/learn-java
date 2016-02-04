package com.hkt.module.accounting;

import java.util.List;

import com.hkt.module.accounting.entity.Invoice;
import com.hkt.module.accounting.entity.InvoiceDetail;
import com.hkt.module.accounting.entity.InvoiceItem;
import com.hkt.module.accounting.entity.InvoiceTransaction;

public class DefaultInvoiceCalculator implements InvoiceCalculator {

  public InvoiceDetail calculate(InvoiceDetail invoiceDetail) {
    try {
      List<InvoiceItem> items = invoiceDetail.getItems();
      double invoiceTotal = 0, discountByItem = 0, discountByInvoice = 0, invoiceTax = invoiceDetail.getTotalTax();
      double discountRateByInvoice = invoiceDetail.getDiscountRate();
      double rateTax = 0;
      discountByInvoice = discountRateByInvoice * invoiceDetail.getTotal() / 100d;
      invoiceDetail.setDiscount(discountByInvoice);
      
      double serviceByInvoice =  invoiceDetail.getServiceRate() * invoiceDetail.getTotal() / 100d;
      invoiceDetail.setService(serviceByInvoice);
      if (invoiceTax != 0) {
        rateTax = invoiceTax / invoiceDetail.getTotal() * 100;
      }
      double pointRate = 0;
      if (invoiceDetail.getPoint() > 0 || invoiceDetail.getCredit() > 0) {
        double finalCharge = invoiceDetail.getTotal() - discountByInvoice - invoiceDetail.getDiscountByItem()
            + invoiceDetail.getTotalTax();
        pointRate = invoiceDetail.getPoint() / finalCharge * 100d;
      }

      if (items != null) {
        for (int i = 0; i < items.size(); i++) {
          InvoiceItem item = items.get(i);
          if (!item.getStatus().equals("Hủy") &&
          		!item.getStatus().equals("Cho thuê")) {
            item.setTotal(item.getQuantity() * item.getUnitPrice());
            if (item.getDiscountRate() == 0 && item.getTotal() != 0) {
              item.setDiscountRate(item.getDiscount() / item.getTotal() * 100d);
            } else {
              item.setDiscount(item.getDiscountRate() * item.getTotal() / 100d);
            }
            item.setDiscountRateByInvoice(discountRateByInvoice);
            item.setDiscountByInvoice((item.getTotal() * discountRateByInvoice) / 100d);
            item.setPoint(pointRate * item.getFinalCharge() / 100d);
            item.setCredit(0);
            double totalAffterDiscount = item.getTotal() - item.getDiscount() - item.getDiscountByInvoice();

            // tÃ¡ÂºÂ¡m thÃ¡Â»ï¿½i bÃ¡Â»ï¿½ tÃƒÂ­nh thuÃ¡ÂºÂ¿ cho tÃ¡Â»Â«ng sÃ¡ÂºÂ£n phÃ¡ÂºÂ©m, khi nÃƒÂ o cÃ¡ÂºÂ§n thiÃ¡ÂºÂ¿t cho
            // vÃƒÂ o sau

            // if (flagTax) {
            // item.setTax((totalAffterDiscount * item.getTaxRate()) / 100d);
            // invoiceTax += item.getTax();
            // } else {
            item.setTaxRate(rateTax);
            item.setTax((totalAffterDiscount * rateTax) / 100d);
            // }
            double finalCharge = totalAffterDiscount + item.getTax() - item.getPoint() - item.getCredit();
            item.setFinalCharge(finalCharge);
            invoiceTotal += item.getTotal();
            discountByItem += item.getDiscount();
          }
        }
      }
      invoiceDetail.setDiscountByItem(discountByItem);
      double totalAffterDiscount = invoiceDetail.getTotal() - invoiceDetail.getDiscount()
          - invoiceDetail.getDiscountByItem();
      invoiceTax = rateTax * totalAffterDiscount / 100;
      invoiceDetail.setTotalTax(invoiceTax);
      invoiceDetail.setTotal(invoiceTotal);

      double finalCharge = invoiceDetail.getTotal() - discountByInvoice - invoiceDetail.getDiscountByItem()
          + invoiceDetail.getTotalTax() - invoiceDetail.getPoint() + serviceByInvoice;
      invoiceDetail.setFinalCharge(finalCharge);
      
      if(invoiceDetail.getTransactions()!=null){
        double totalPaid = 0;
        for(InvoiceTransaction transaction: invoiceDetail.getTransactions()){
          totalPaid = totalPaid+transaction.getTotal();
        }
        invoiceDetail.setTotalPaid(totalPaid);
      }
      return invoiceDetail;
    } catch (Exception e) {
      return invoiceDetail;
    }

  }

}
