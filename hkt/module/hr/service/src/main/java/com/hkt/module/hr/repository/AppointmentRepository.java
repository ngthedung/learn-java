package com.hkt.module.hr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.hkt.module.hr.entity.Appointment;

public interface AppointmentRepository extends CrudRepository<Appointment, Long>, AppointmentRepositoryCustom {

  @Modifying
  @Query(value = "DELETE FROM Appointment WHERE employeeLoginId = :employeeLoginId ")
  public void deleteByLoginId(@Param("employeeLoginId") String employeeLoginId);
  
  @Query(value = "SELECT a FROM Appointment a WHERE a.employeeLoginId = :employeeLoginId and a.recycleBin = 0 ORDER BY a.id DESC")
  List<Appointment> findByEmployeeLoginId(@Param("employeeLoginId") String employeeLoginId);
  
  //khanhdd
  @Query("SELECT a FROM Appointment a WHERE  a.recycleBin = :value")
  List<Appointment> findByValueRecycleBin(@Param("value") boolean value); 
  
  @Modifying
  @Query("DELETE FROM Appointment WHERE name like %:test%")
  public void deleteTest(@Param("test") String test);
}
