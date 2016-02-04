package com.hkt.module.hr.repository;

import javax.persistence.EntityNotFoundException;
import javax.persistence.PrePersist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hkt.module.account.entity.Account;
import com.hkt.module.account.entity.Account.Type;
import com.hkt.module.account.repository.AccountRepository;
import com.hkt.module.hr.entity.Employee;

@Component
public class EmployeeRepositoryListener {

  static private AccountRepository accountRepo;

  @Autowired(required = true)
  public void setAccountRepository(AccountRepository instance) {
    accountRepo = instance;
  }

  @PrePersist
  public void prePersist(Employee employee) {
    if (employee.getLoginId() != null) {
      if (accountRepo.getByLoginId(employee.getLoginId()) == null)
        throw new EntityNotFoundException("cannot find Account " + employee.getLoginId());
    }

    if (employee.getOrganizationLoginId() != null) {
      if (accountRepo.getByLoginId(employee.getOrganizationLoginId()) == null) {
        throw new EntityNotFoundException("cannot find Account " + employee.getOrganizationLoginId());
      } else {
        Account acc = accountRepo.getByLoginId(employee.getOrganizationLoginId());
        if (acc.getType() != Type.ORGANIZATION)
          throw new EntityNotFoundException("Account " + employee.getOrganizationLoginId() + " isn't ORGANIZATION ");
      }
    }
  }
}
