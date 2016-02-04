package com.hkt.module.hr.repository;

import static org.junit.Assert.assertEquals;



import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.hkt.module.hr.AbstractUnitTest;
import com.hkt.module.hr.entity.Appointment;

@Transactional
public class AppointmentRepositoryUnitTest extends AbstractUnitTest {
  
  @Autowired
  AppointmentRepository appointmentRepository;
  
  @Test
  public void testCrud() {
    Appointment app = appointmentRepository.save(createAppointment());
    assertEquals(app, appointmentRepository.findOne(app.getId()));
    assertEquals(1, appointmentRepository.count());
    appointmentRepository.deleteTest("HktTest");
    assertEquals(0, appointmentRepository.count());
  }
  
  //khanhdd
  @Test
  public void testCrud1() {	 
    Appointment app = appointmentRepository.save(createAppointment(true));
    
    assertNotNull(app);
    assertEquals(1, appointmentRepository.findByValueRecycleBin(true).size());
  }
  
  public Appointment createAppointment(){
    Appointment  appointment = new Appointment();
    appointment.setName("Cong viec moi HktTest1");
    appointment.setContent("Noi dung cong viec");
    return appointment;
  }

  public Appointment createAppointment(boolean k){
	    Appointment  appointment = new Appointment();
	    appointment.setName("Cong viec moi HktTest1");
	    appointment.setContent("Noi dung cong viec");
	    appointment.setRecycleBin(k);
	    return appointment;
  }
  
}
