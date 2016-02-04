//package com.hkt.module.accounting.repository;
//
//import static org.junit.Assert.assertEquals;
//
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.hkt.module.accounting.entity.BankTransaction;
//import com.hkt.module.accounting.repository.BankTransactionRepository;
//import com.hkt.module.warehouse.AbstractUnitTest;
//
//@Transactional
//public class BankTransactionRepositoryUnitTest extends AbstractUnitTest {
//  @Autowired
//  private BankTransactionRepository repo;
//  
//  @Test
//  public void testCRUD() {
//	  BankTransaction bankTransaction, bankTransaction1;
//	  bankTransaction = createBankTransaction("Bank","BankCode","TracsactionCode",20000,true);
//	  bankTransaction1 = createBankTransaction("Bank1","BankCode","TracsactionCode",40000,false);
//	  bankTransaction = repo.save(bankTransaction);
//	  bankTransaction1 = repo.save(bankTransaction1);
//	  assertEquals(2, repo.count());
//    
//	  assertEquals(2, repo.findByName("b").size());
//	  assertEquals(1, repo.findByRangeTotal(20000).size());
//	  assertEquals(2, repo.findByBankCode("c").size());
//	  assertEquals(2, repo.findByTracsactionCode("s").size());
//	  assertEquals(1, repo.findByType(false).size());
//    
//	  assertEquals(bankTransaction, repo.findByBankTracsactionRecycleBin(false).get(0));
//	  repo.delete(bankTransaction);
//	  assertEquals(1, repo.count());
//  }
//  
//  public BankTransaction createBankTransaction(String name
//		  , String bankCode
//		  ,	String transactionCode
//		  , int total
//		  , boolean type){
//	  BankTransaction bankTransaction = new BankTransaction();
//	  bankTransaction.setName(name);
//	  bankTransaction.setBankCode(bankCode);
//	  bankTransaction.setTransactionCode(transactionCode);
//	  bankTransaction.setTotal(total);
//	  bankTransaction.setType(type);
//	  bankTransaction.setRecycleBin(false);
//	  return bankTransaction;
//  }
//}
