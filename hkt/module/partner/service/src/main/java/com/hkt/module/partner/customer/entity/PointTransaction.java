package com.hkt.module.partner.customer.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.hkt.module.core.entity.AbstractPersistable;

@Table
@Entity
public class PointTransaction extends AbstractPersistable<Long> {
	private static final long serialVersionUID = 1L;

	private String loginId;
	private long pointId;
	private long invoiceId;
	private long pointConversionRuleId;
	private double point; // diem su dung
	private Date dateExecute;
	private String description;

	@Override
	public String toString() {
		return Double.toString(point);
	}

	public PointTransaction() {
	}

	public long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(long invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public long getPointId() {
		return pointId;
	}

	public void setPointId(long pointId) {
		this.pointId = pointId;
	}

	public double getPoint() {
		return point;
	}

	public long getPointConversionRuleId() {
		return pointConversionRuleId;
	}

	public void setPointConversionRuleId(long pointConversionRuleId) {
		this.pointConversionRuleId = pointConversionRuleId;
	}

	public void setPoint(double point) {
		this.point = point;
	}

	public Date getDateExecute() {
		return dateExecute;
	}

	public void setDateExecute(Date dateExecute) {
		this.dateExecute = dateExecute;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
