package com.hkt.client.swingexp.app.hethong.list;

import java.util.List;

import com.hkt.module.account.entity.AccountMembership;

public interface IComboBoxDelete<E> {
  public boolean delete(List<E> list);

  public void changeGroup(List<E> list);
}
