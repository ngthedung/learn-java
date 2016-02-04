package com.hkt.module.hr.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.hkt.module.hr.AbstractUnitTest;
import com.hkt.module.hr.entity.DailyWork;

@Transactional
public class DailyWorkRepositoryUnitTest extends AbstractUnitTest {
  
  @Autowired
  DailyWorkRepository repository;

  @Test
  public void testCrud() {
    DailyWork dailywork = create(1l, "hkt");
    dailywork = repository.save(dailywork);
    assertEquals("hkt", dailywork.getLoginId());

    assertEquals(1, repository.findByPositionId(1l).size());
    dailywork.setNote("update");
    dailywork = repository.save(dailywork);
    assertEquals("update", repository.findOne(dailywork.getId()).getNote());
    repository.deleteByPositionId(1l);
    assertEquals(0, repository.findByPositionId(1l).size());
  }
  
  //khanhdd
  @Test
  public void testCrud1() {
    DailyWork dailywork = create(1l, "hkt", true);
    dailywork = repository.save(dailywork);
    
    assertNotNull(dailywork);
    assertEquals(1, repository.findByValueRecycleBin(true).size());
  }

  public DailyWork create(Long positionId, String loginId) {
    DailyWork dailywork = new DailyWork();
    dailywork.setPositionId(positionId);
    dailywork.setLoginId(loginId);
    dailywork.setStartTime(new Date());
    dailywork.setEndTime(null);
    dailywork.setLocation("Cong ty hkt - 335CauGiay - Ha Noi");
    dailywork.setNote("Cong viec hang ngay");
    return dailywork;
  }
  
  //khanhdd
  public DailyWork create(Long positionId, String loginId, boolean k) {
	    DailyWork dailywork = new DailyWork();
	    dailywork.setPositionId(positionId);
	    dailywork.setLoginId(loginId);
	    dailywork.setStartTime(new Date());
	    dailywork.setEndTime(null);
	    dailywork.setLocation("Cong ty hkt - 335CauGiay - Ha Noi");
	    dailywork.setNote("Cong viec hang ngay");
	    dailywork.setRecycleBin(k);
	    return dailywork;
	  }
}
