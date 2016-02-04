package com.hkt.module.school.repository;

import javax.persistence.EntityNotFoundException;
import javax.persistence.PrePersist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hkt.module.account.repository.AccountRepository;
import com.hkt.module.school.entity.CourseSession;

@Component
public class CourseSessionRepositoryListener {

  static private AccountRepository accountRepo;

  @Autowired(required = true)
  public void setAccountRepository(AccountRepository instance) {
    accountRepo = instance;
  }

  @PrePersist
  public void prePersist(CourseSession courseSession) {
    if (courseSession.getTeacherLoginId() != null) {
      if (accountRepo.getByLoginId(courseSession.getTeacherLoginId()) == null)
        throw new EntityNotFoundException("cannot find Account " + courseSession.getTeacherLoginId());
    }
  }
}
