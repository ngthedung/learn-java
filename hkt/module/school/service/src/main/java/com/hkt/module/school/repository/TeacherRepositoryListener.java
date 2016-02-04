package com.hkt.module.school.repository;

import javax.persistence.EntityNotFoundException;
import javax.persistence.PrePersist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hkt.module.account.repository.AccountRepository;
import com.hkt.module.school.entity.Teacher;
@Component
public class TeacherRepositoryListener {
  
  static private AccountRepository accountRepo;

  @Autowired(required = true)
  public void setAccountRepository(AccountRepository instance) {
    accountRepo = instance;
  }

  @PrePersist
  public void prePersist(Teacher teacher) {
    if (teacher.getLoginId() != null) {
      if (accountRepo.getByLoginId(teacher.getLoginId()) == null)
        throw new EntityNotFoundException("cannot find Account " + teacher.getLoginId());
    }
  }

}
