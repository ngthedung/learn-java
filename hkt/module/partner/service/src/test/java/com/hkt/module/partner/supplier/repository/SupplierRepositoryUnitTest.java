package com.hkt.module.partner.supplier.repository;

import static org.junit.Assert.assertEquals;

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
import com.hkt.module.partner.AbstractUnitTest;
import com.hkt.module.partner.supplier.entity.Supplier;
import com.hkt.module.partner.supplier.repository.SupplierRepository;
import com.hkt.util.json.JSONSerializer;

@Transactional
public class SupplierRepositoryUnitTest extends AbstractUnitTest{
  @Autowired
  SupplierRepository repository;
  
  @Autowired
  AccountRepository accountRepository;
    
  @Before
  public void createAccount(){
    Account account = new Account();
    account.setLoginId("hktSupplier");
    account.setPassword("abc");
    account.setEmail("long@email.com");
    account.setType(Type.USER);
    accountRepository.save(account);
    
    Account account2 = new Account();
    account2.setLoginId("orgSupplier");
    account2.setPassword("abc");
    account2.setEmail("hkt@email.com");
    account2.setType(Type.ORGANIZATION);
    accountRepository.save(account2);
  }
  
  @After
  public void endTest(){
    accountRepository.deleteAll();
    repository.deleteAll();
  }

  @Test
  public void testCrud() {
    Supplier supplier = repository.save(create("hktSupplier", "orgSupplier"));
    assertEquals(supplier, repository.findOne(supplier.getId()));
    repository.delete(supplier);
    assertEquals(0, repository.count());
  }

//  @Test
//  public void testSearch() throws IOException {
//    for (int i = 0; i < 5; i++) {
//      repository.save(create("hktSupplier", "orgSupplier"));
//    }
//    assertEquals(5, repository.findByOrganizationLoginId("orgSupplier").size());
//   
//    FilterQuery query = new FilterQuery() ;
//    query.add("loginId", FilterQuery.Operator.LIKE, "hktSuppli*") ;
//    FilterResult<Supplier> result = repository.search(query) ;
//    assertEquals(5, result.getData().size()) ;
//    
//    query.add("organizationLoginId", FilterQuery.Operator.LIKE, "org*") ;
//    result = repository.search(query) ;
//    assertEquals(5, result.getData().size()) ;
//    
//    String json = JSONSerializer.INSTANCE.toString(query);
//    query = JSONSerializer.INSTANCE.fromString(json, FilterQuery.class) ;
//    System.out.println(json);
//  }

  public Supplier create(String loginId, String orgLoginId) {
    Supplier sup = new Supplier();
    sup.setLoginId(loginId);
    sup.setOrganizationLoginId(orgLoginId);
    return sup;
  }
}
