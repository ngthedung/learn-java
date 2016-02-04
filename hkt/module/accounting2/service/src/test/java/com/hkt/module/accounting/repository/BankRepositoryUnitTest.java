//package com.hkt.module.accounting.repository;
//
//import static org.junit.Assert.assertEquals;
//
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.hkt.module.accounting.entity.Bank;
//import com.hkt.module.accounting.repository.BankRepository;
//import com.hkt.module.warehouse.AbstractUnitTest;
//
//@Transactional
//public class BankRepositoryUnitTest extends AbstractUnitTest {
//  @Autowired
//  private BankRepository repo;
//  
//  @Test
//  public void testCRUD() {
//    Bank bank = createBank("Bank");
//    Bank bank1 = createBank("Bank1");
//    bank = repo.save(bank);
//    bank1 = repo.save(bank1);
//    assertEquals(2, repo.count());
//    
//    assertEquals(2, repo.findByName("b").size());
//    
//    assertEquals(bank, repo.findByBankRecycleBin(false).get(0));
//    repo.delete(bank);
//    assertEquals(1, repo.count());
//  }
//  
//  public Bank createBank(String name) {
//	Bank bank = new Bank();
//	bank.setName(name);
//	bank.setRecycleBin(false);
//    return bank;
//  }
//}
