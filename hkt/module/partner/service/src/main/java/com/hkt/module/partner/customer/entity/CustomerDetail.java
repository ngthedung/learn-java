package com.hkt.module.partner.customer.entity;

import java.io.Serializable;

import com.hkt.module.account.entity.Account;

public class CustomerDetail implements Serializable {
  private Customer customer;
  private Account  account;
  private Point    point;
  private Credit   credit;

  public CustomerDetail() { }

  public CustomerDetail(Customer customer, Account account, Point point, Credit credit) {
    this.customer = customer;
    this.account = account;
    this.point = point;
    this.credit = credit;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public Account getAccount() {
    return account;
  }

  public void setAccount(Account account) {
    this.account = account;
  }

  public Point getPoint() {
    return point;
  }

  public void setPoint(Point point) {
    this.point = point;
  }

  public Credit getCredit() {
    return credit;
  }

  public void setCredit(Credit credit) {
    this.credit = credit;
  }
}
