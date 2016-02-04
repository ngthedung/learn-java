package com.hkt.module.promotion.repository;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.hkt.module.promotion.entity.Weekly;
import com.hkt.module.warehouse.AbstractUnitTest;


@Transactional
public class TimeOptionRepositoryUnitTest extends AbstractUnitTest {
  
 
  @Autowired
  WeeklyRepository menuRepository;
  
    
  @Test
  public void testCrud() {
    Weekly app = menuRepository.save(createMenu());
    assertEquals(app, menuRepository.findOne(app.getId()));
    assertEquals(1, menuRepository.count());
    assertEquals(6, menuRepository.findOne(app.getId()).dayOfWeeks().get(0).intValue());
    System.out.println(menuRepository.getByTimeOptionId(1l).get(0).dayOfWeeks().get(0));
    menuRepository.delete(app);
    assertEquals(0, menuRepository.count());
  }
  
  public Weekly createMenu(){
    Weekly  menu = new Weekly();
    menu.setCode("a");
    menu.setStep(1);
    menu.setIdTimeOption(1);
    menu.add(6);
    return menu;
  }
  
  @After
  public void deleteAll(){
    menuRepository.deleteAll();
  }

}
