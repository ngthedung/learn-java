package com.hkt.module.partner.supplier.repository;

import javax.persistence.EntityNotFoundException;
import javax.persistence.PrePersist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hkt.module.account.entity.Account;
import com.hkt.module.account.entity.Account.Type;
import com.hkt.module.account.repository.AccountRepository;
import com.hkt.module.partner.supplier.entity.Supplier;

@Component
public class SupplierRepositotyListener {

  static private AccountRepository accountRepo ;
  
  @Autowired(required = true)
  public void setAccountRepository(AccountRepository instance) {
    accountRepo = instance ;
  }

  
  @PrePersist
  public void prePersist(Supplier supplier) {
    if(accountRepo.getByLoginId(supplier.getLoginId()) == null) {
      throw new EntityNotFoundException("cannot find Account " + supplier.getLoginId()) ;
    }
   
    if(accountRepo.getByLoginId(supplier.getOrganizationLoginId()) == null) {
      throw new EntityNotFoundException("cannot find Account " + supplier.getOrganizationLoginId()) ;
    }else{
      Account account = accountRepo.getByLoginId(supplier.getOrganizationLoginId());
      if(account.getType() != Type.ORGANIZATION){
        throw new EntityNotFoundException("Account " + supplier.getOrganizationLoginId() +" isn't ORGANIZATION") ;
      }
    }
  }
}
