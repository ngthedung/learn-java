package com.hkt.module.school.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.hkt.module.account.entity.Account;
import com.hkt.module.account.entity.Account.Type;
import com.hkt.module.account.repository.AccountRepository;
import com.hkt.module.school.AbstractUnitTest;
import com.hkt.module.school.entity.Teacher;

@Transactional
public class TeacherRepositoryUnitTest extends AbstractUnitTest {
  @Autowired
  TeacherRepository repository;
  
  @Autowired
  AccountRepository accountRepository;
  
  @Before
  public void createAccount(){
    Account account = new Account();
    account.setLoginId("cuong.teacher");
    account.setPassword("123456");
    account.setEmail("cuong.teacher@school.com");
    account.setType(Type.USER);
    accountRepository.save(account);
    for (int i = 0; i < 5; i++) {
      Account acc = new Account();
      acc.setLoginId("test" + i + ".teacher");
      acc.setPassword("123456");
      acc.setEmail("test" + i + ".teacher"+"@school.com");
      acc.setType(Type.USER);
      accountRepository.save(acc);
    }
  }
  
  @After
  public void deleteAccount(){
    accountRepository.deleteAll();
  }
  
  @Test
  public void testCrud() {
    Teacher teacher = new Teacher("cuong.teacher", "Cuong", "Pham Manh");
    teacher = repository.save(teacher);
    assertEquals(teacher, repository.getByLoginId("cuong.teacher"));
    
    teacher.setLastName("update");
    assertEquals("update", repository.findOne(teacher.getId()).getLastName());
    assertEquals(1, repository.count());
    
    repository.delete(teacher);
    assertNull(repository.findOne(teacher.getId()));
    assertEquals(0, repository.count());
  }
  
  @Test
  public void testFind() {
    for (int i = 0; i < 5; i++) {
      repository.save(createTeacher("test" + i + ".teacher", "first", "last"));
    }
    List<Teacher> list = (List<Teacher>)repository.findAll();
    assertEquals(5, list.size());
  }
  
}
