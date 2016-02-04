package com.hkt.module.school.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.hkt.module.account.entity.Account;
import com.hkt.module.account.entity.Account.Type;
import com.hkt.module.account.repository.AccountRepository;
import com.hkt.module.core.entity.FilterQuery;
import com.hkt.module.core.entity.FilterResult;
import com.hkt.module.school.AbstractUnitTest;
import com.hkt.module.school.entity.Student;
import com.hkt.util.json.JSONSerializer;

@Transactional
public class StudentRepositoryUnitTest extends AbstractUnitTest {
  
  @Autowired
  StudentRepository repository;
  
  @Autowired
  AccountRepository accountRepository;
  
  @Before
  public void createAccount(){
    Account account = new Account();
    account.setLoginId("hoang.student");
    account.setPassword("hoang.student");
    account.setEmail("hoang.student@school.com");
    account.setType(Type.USER);
    accountRepository.save(account);
    for (int i = 0; i < 5; i++) {
      Account acc = new Account();
      acc.setLoginId("test" + i + ".student");
      acc.setPassword("test" + i + ".student");
      acc.setEmail("test" + i + ".student@school.com");
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
    Student student = new Student("hoang.student", "Hoang", "Nguyen Vu");
    student = repository.save(student);
    assertEquals(student, repository.getByLoginId("hoang.student"));
    
    student.setLastName("update");
    assertEquals("update", repository.findOne(student.getId()).getLastName());
    assertEquals(1, repository.count());
    
    repository.delete(student);
    assertNull(repository.findOne(student.getId()));
    assertEquals(0, repository.count());
  }
  
  @Test
  public void testSearch() throws IOException {
    for (int i = 0; i < 5; i++) {
      repository.save(createStudent("test" + i + ".student", "first", "last"));
    }
    assertEquals(5, repository.findByFirstName("first").size());
    
    FilterQuery query = new FilterQuery();
    query.add("firstName", FilterQuery.Operator.LIKE, "first*");
    FilterResult<Student> result = repository.search(query);
    assertEquals(5, result.getData().size());
    
    String json = JSONSerializer.INSTANCE.toString(query);
    query = JSONSerializer.INSTANCE.fromString(json, FilterQuery.class);
    System.out.println(query);
    
  }
  
}
