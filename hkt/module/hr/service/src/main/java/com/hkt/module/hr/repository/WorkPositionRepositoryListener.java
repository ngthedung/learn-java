package com.hkt.module.hr.repository;

import javax.persistence.EntityNotFoundException;
import javax.persistence.PrePersist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hkt.module.account.repository.AccountGroupRepository;
import com.hkt.module.hr.entity.WorkPosition;

@Component
public class WorkPositionRepositoryListener {
  
  static private AccountGroupRepository groupRepo ;
  
  @Autowired(required = true)
  public void setAccountGroupRepository(AccountGroupRepository instance) {
    groupRepo = instance ;
  }
  
  @PrePersist
  public void prePersist(WorkPosition workPosition){
    if(groupRepo.getByPath(workPosition.getGroup()) == null) {
      throw new EntityNotFoundException("cannot find AccountGroup " + workPosition.getGroup()) ;
    }
  }
}
