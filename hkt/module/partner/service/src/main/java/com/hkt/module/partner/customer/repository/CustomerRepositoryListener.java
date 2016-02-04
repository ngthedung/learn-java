package com.hkt.module.partner.customer.repository;

import javax.persistence.EntityNotFoundException;
import javax.persistence.PrePersist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hkt.module.account.entity.Account;
import com.hkt.module.account.entity.Account.Type;
import com.hkt.module.account.repository.AccountRepository;
import com.hkt.module.partner.customer.entity.Customer;

@Component
public class CustomerRepositoryListener {

static private AccountRepository accountRepo ;
  
  @Autowired(required = true)
  public void setAccountRepository(AccountRepository instance) {
    accountRepo = instance ;
  }

  
  @PrePersist
  public void prePersist(Customer customer) {
    if(accountRepo.getByLoginId(customer.getLoginId()) == null) {
      throw new EntityNotFoundException("cannot find Account " + customer.getLoginId()) ;
    }
   
    if(accountRepo.getByLoginId(customer.getOrganizationLoginId()) == null) {
      throw new EntityNotFoundException("cannot find Account " + customer.getOrganizationLoginId()) ;
    }else{
      Account account = accountRepo.getByLoginId(customer.getOrganizationLoginId());
      if(account.getType() != Type.ORGANIZATION){
        throw new EntityNotFoundException("Account " + customer.getOrganizationLoginId() +" isn't ORGANIZATION") ;
      }
    }
  }
}
