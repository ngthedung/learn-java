package com.hkt.module.accounting.repository;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hkt.module.accounting.AbstractUnitTest;
import com.hkt.module.accounting.entity.BankAccount;

@Transactional
public class BankAccountRepositoryUnitTest extends AbstractUnitTest {
  @Autowired
  BankAccountRepository repository;

  @Test
  public void testCrud() {
    BankAccount bankAccount = repository.save(create("Bank-NgoQuyen"));
    assertEquals(bankAccount, repository.findOne(bankAccount.getId()));
    assertEquals(bankAccount, repository.findBankAccountByRecycleBin(false).get(0));
    repository.delete(bankAccount);
    assertEquals(0, repository.count());
  }

  @Test
  public void testFind() {
    for (int i = 0; i < 5; i++) {
      String code = "Bank-NgoQuyen" + i;
      repository.save(create(code));
    }
    BankAccount bankAccount = repository.getByBankAccountId("Bank-NgoQuyen2");
    assertEquals("VND", repository.findOne(bankAccount.getId()).getCurrency());
    assertEquals(5, repository.findByType((BankAccount.Type.Money)).size());
    repository.delete(bankAccount);
    assertEquals(4, repository.findByType((BankAccount.Type.Money)).size());
  }

  public BankAccount create(String bankAccountId) {
    BankAccount bankAccount = new BankAccount();
    bankAccount.setBankAccountId(bankAccountId);
    bankAccount.setType(BankAccount.Type.Money);
    bankAccount.setCurrency("VND");
    bankAccount.setAddress("33 Ngo Quyen, Hai Ba Trung, Ha Noi");
    return bankAccount;
  }
}
