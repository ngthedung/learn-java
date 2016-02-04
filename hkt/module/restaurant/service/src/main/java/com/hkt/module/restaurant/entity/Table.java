package com.hkt.module.restaurant.entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.type.TypeReference;

import com.hkt.module.core.entity.AbstractPersistable;
import com.hkt.util.json.JSONSerializer;

@Entity
@javax.persistence.Table(name = "restaurant_table")
public class Table extends AbstractPersistable<Long> {
  private static final long serialVersionUID = 1L;

  static public enum Status {
    TableFree,//Bàn trống 
    TableGross, //Bàn gộp
    TableSet, //Bàn đặt trước
    TableBusy, //Bàn bận
    TableServe, //Bàn đã phục vụ
    TableKitchen, //Bàn đã in chế biến
    InActivate //Bàn chưa mở
  };

  private String organizationLoginId;
  private String responsibleGroup;
  private String locationCode;
  private String invoiceCode;

  private String name;
  private String description;
  private Status status = Status.TableFree;

  @Transient
  private List<Reservation> reservations;
  
  @Transient
  private List<NoteTable> noteTables;

  public String getOrganizationLoginId() {
    return organizationLoginId;
  }

  public String getInvoiceCode() {
    return invoiceCode;
  }
  
  

  public List<NoteTable> getNoteTables() {
		return noteTables;
	}

	public void setNoteTables(List<NoteTable> noteTables) {
		this.noteTables = noteTables;
	}

	public long invoiceId() {
    try {
      return Long.parseLong(invoiceCode);
    } catch (Exception e) {
      return 0;
    }
  }

  public void setInvoiceCode(String invoiceCode) {
    this.invoiceCode = invoiceCode;
  }

  public void setOrganizationLoginId(String organizationLoginId) {
    this.organizationLoginId = organizationLoginId;
  }

  public String getResponsibleGroup() {
    return responsibleGroup;
  }

  public void setResponsibleGroup(String responsibleGroup) {
    this.responsibleGroup = responsibleGroup;
  }

  public String getLocationCode() {
    return locationCode;
  }

  public void setLocationCode(String locationCode) {
    this.locationCode = locationCode;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  @JsonIgnore
  @Column(length = 64000)
  @Lob
  @Access(AccessType.PROPERTY)
  public String getNoteTableData() throws IOException {
    if (noteTables == null)
      return null;
    return JSONSerializer.INSTANCE.toString(noteTables);
  }

  public void setNoteTableData(String data) throws IOException {
    if (data == null || data.length() == 0)
      return;
    noteTables = JSONSerializer.INSTANCE.fromString(data, new TypeReference<List<NoteTable>>() {
    });
  }
  
  @JsonIgnore
  @Column(length = 64000)
  @Lob
  @Access(AccessType.PROPERTY)
  public String getReservationData() throws IOException {
    if (reservations == null)
      return null;
    return JSONSerializer.INSTANCE.toString(reservations);
  }

  public void setReservationData(String data) throws IOException {
    if (data == null || data.length() == 0)
      return;
    reservations = JSONSerializer.INSTANCE.fromString(data, new TypeReference<List<Reservation>>() {
    });
  }

  @Transient
  public List<Reservation> getReservations() {
    return reservations;
  }

  public void setReservations(List<Reservation> reservations) {
    this.reservations = reservations;
  }

  public void addReservation(Reservation reservation) {
    if (reservations == null)
      reservations = new ArrayList<Reservation>();
    reservations.add(reservation);
  }

  @Override
  public String toString() {
    return name;
  }

}
