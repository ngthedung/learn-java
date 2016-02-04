package com.hkt.module.account.repository;

import javax.persistence.EntityNotFoundException;
import javax.persistence.PrePersist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hkt.module.account.entity.AccountMembership;

@Component
public class AccountMembershipRepositoryListener {
  static private AccountRepository accountRepo ;
  static private AccountGroupRepository groupRepo ;
  
  @Autowired(required = true)
  public void setAccountRepository(AccountRepository instance) {
    accountRepo = instance ;
  }
  
  @Autowired(required = true)
  public void setAccountGroupRepository(AccountGroupRepository instance) {
    groupRepo = instance ;
  }
  
  @PrePersist
  public void prePersist(AccountMembership m) {
    if(accountRepo.getByLoginId(m.getLoginId()) == null) {
      throw new EntityNotFoundException("cannot find Account " + m.getLoginId()) ;
    }
    
    if(groupRepo.getByPath(m.getGroupPath()) == null) {
      throw new EntityNotFoundException("cannot find AccountGroup " + m.getGroupPath()) ;
    }
  }
}
