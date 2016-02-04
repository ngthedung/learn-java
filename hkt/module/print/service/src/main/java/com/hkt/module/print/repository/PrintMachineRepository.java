package com.hkt.module.print.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.hkt.module.print.entity.PrintMachine;

public interface PrintMachineRepository extends JpaRepository<PrintMachine, Long>{

  @Query("SELECT p FROM PrintMachine p JOIN p.productTags t WHERE t.tag IN :tags "
      + "GROUP BY p.id")
  List<PrintMachine> findPrintMachineByTags(@Param("tags") List<String> tags);
  
  @Query("SELECT p FROM PrintMachine p JOIN p.productTags t WHERE t.tag = :tag")
  List<PrintMachine> findPrintMachineByTag(@Param("tag") String tag);
  
  @Query("SELECT p FROM PrintMachine p JOIN p.tables t WHERE t.code = :code")
  List<PrintMachine> findPrintMachineByTable(@Param("code") String code);
  
  @Query("SELECT p FROM PrintMachine p JOIN p.locations t WHERE t.code = :code")
  List<PrintMachine> findPrintMachineByLocation(@Param("code") String code);
  
  @Query("SELECT p FROM PrintMachine p JOIN p.accountGroups t WHERE t.path = :value")
  List<PrintMachine> findPrintMachineByDepartment(@Param("value") String value);
  
  PrintMachine getPrintMachineByPrintMachineName(String printMachineName);
}
