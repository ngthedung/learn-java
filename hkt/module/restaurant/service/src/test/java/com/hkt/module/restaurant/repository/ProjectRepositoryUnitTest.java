package com.hkt.module.restaurant.repository;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.swing.JOptionPane;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.hkt.module.account.entity.Account;
import com.hkt.module.account.entity.Account.Type;
import com.hkt.module.account.repository.AccountRepository;
import com.hkt.module.restaurant.AbstractUnitTest;
import com.hkt.module.restaurant.entity.Project;
import com.hkt.module.restaurant.entity.ProjectMember;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Transactional
public class ProjectRepositoryUnitTest extends AbstractUnitTest{

	static {
		System.setProperty("hibernate.show_sql", "true");
		System.setProperty("hibernate.format_sql", "true");
	}
	
	@PersistenceContext
	private EntityManager em;
	
	Project instance;
	@Autowired
	private ProjectRepository repo;
	
	@Autowired
	private AccountRepository accountRepository;
	
	
	@Before
	public void creatContext(){
		Account account = new Account();
		account.setLoginId("hkt");
		account.setPassword("abc");
		account.setType(Type.ORGANIZATION);
		account.setEmail("hkt@gmail.com");
		accountRepository.save(account);
//		List<ProjectMember> projectMembers = new ArrayList<ProjectMember>();
//		projectMembers.add(creatProjectMember(1234));
//		instance.setProjectMembers(projectMembers);
//		instance = repo.save(instance);
//		assertEquals(1, instance.getProjectMembers().size());
//		
	}	
	
	@Before
	public void setup(){
		instance = createProject("hkt");
		List<ProjectMember> projectMembers = new ArrayList<ProjectMember>();
		projectMembers.add(creatProjectMember(1234));
		instance.setProjectMembers(projectMembers);
		instance = repo.save(instance);
		assertEquals(1, instance.getProjectMembers().size());
	}
	@Test
	public void testCRUD(){
		Project project =  createProject("test");
		repo.save(project);
		System.out.println(repo.findProjectByCode("es"));
		project = repo.getProjectByCode("test");
		assertNotNull(project);
		assertEquals(2, repo.findProjectByParentCode("hkt").size());
	}
	
	@Test
	public void testCRUD1(){
		Project project =  createProject("test", true);
		repo.save(project);
		
		assertNotNull(project);
		assertEquals(0, repo.findByValueRecycleBin(true).size());
	}
	
	@Test
	public void testProjectMember(){
		Project project = repo.findOne(instance.getId());
		project.add(creatProjectMember(33333));
		repo.save(project);
		for(ProjectMember p:repo.findInvoiceDetailByEmployeeCode(33333).get(0).getProjectMembers()){
			JOptionPane.showConfirmDialog(null, p);
		}
	}
	
	public ProjectMember creatProjectMember(double employeecode){
		ProjectMember projectMember = new ProjectMember();
		projectMember.setEmployeeCode(String.valueOf(employeecode));
		projectMember.setRole("abc");
		projectMember.setPercent(80);
		projectMember.setDirectAward(30);
		projectMember.setPriority(40);
		projectMember.setStatus("HKT-Test");
		return projectMember;
	}
	
	public Project createProject(String name){
		Project project = new Project();
		project.setCode(name);
		project.setStatus(name);
		project.setName(name);
		project.setParentCode("hkt");
		project.setCustomerCode(name);
		return project;
	}
	public Project createProject(String name, boolean k){
		Project project = new Project();
		project.setStatus(name);
		project.setName(name);
		project.setCustomerCode(name);
		project.setCode(name);
		project.setParentCode("hkt");
		return project;
	}
	
}
