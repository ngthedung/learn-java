package com.hkt.module.school.repository;

import javax.persistence.EntityNotFoundException;
import javax.persistence.PrePersist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hkt.module.account.repository.AccountRepository;
import com.hkt.module.school.entity.Student;

@Component
public class StudentRepositoryListener {

  static private AccountRepository accountRepo;

  @Autowired(required = true)
  public void setAccountRepository(AccountRepository instance) {
    accountRepo = instance;
  }
  
  @PrePersist
  public void prePersist(Student student){
    if (student.getLoginId() != null) {
      if (accountRepo.getByLoginId(student.getLoginId()) == null)
        throw new EntityNotFoundException("cannot find Account " + student.getLoginId());
    }
  }
}
