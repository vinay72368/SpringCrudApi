package com.asint.spring.crud.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.asint.spring.crud.example.entity.EmployeeModel;
import com.asint.spring.crud.example.repository.EmployeeRepository;
import com.asint.spring.crud.example.service.EmployeeService;

@SpringBootTest
class SpringCrudoperationApplicationTests {

	@Autowired
	private EmployeeService service;
	@MockBean
	private EmployeeRepository repository;
	
	
	
	@Test
	public void saveEmployeeTest() {
		EmployeeModel employee = new EmployeeModel(1,"abc","verma","abc@gmail.com");
		when(repository.save(employee)).thenReturn(employee);
	}
	
	@Test
	public void saveEmployeesTest() {
		List<EmployeeModel> employees = new ArrayList<>();
		employees.add(new EmployeeModel(1,"abc","xyz","abc@gmail.com"));
		employees.add(new EmployeeModel(2,"xyz","abc","xyz@gmail.com"));
		when(repository.saveAll(employees)).thenReturn(employees);
		List<EmployeeModel> savedEmployees = service.saveEmployees(employees);
		verify(repository).saveAll(employees);
		assertEquals(employees, savedEmployees);
		
	}
	
	@Test
	public void getEmployeesTest() {
		when(repository.findAll()).thenReturn(Stream.
				of(new EmployeeModel(1,"usa","jujaray","vinay@gmail.com"),
						new EmployeeModel(2,"abc","xyz","hitech@gmail.com")).collect(Collectors.toList()));
		assertEquals(2, service.getEmployees().size());
	}
	
	@Test
	public void getEmployeeByIdTest() {
		EmployeeModel employee = new EmployeeModel(1,"abc","xyz","abc@gmail.com");
		when(repository.findById(1)).thenReturn(Optional.of(employee));
		EmployeeModel fetchedEmployee = service.getEmployeeById(1);
		verify(repository).findById(1);
		assertEquals(employee,fetchedEmployee);
		
	}
	
	@Test
	public void getEmployeeByFirstNameTest() {
		EmployeeModel employee1 = new EmployeeModel(1,"abc","xyz","abc@gmail.com");
	
		
		when(repository.findByFirstName("abc")).thenReturn((employee1));
		EmployeeModel foundEmployees = service.getEmployeeByFirstName("abc");
		assertNotNull(foundEmployees);
		assertEquals(employee1 , foundEmployees);	
		
		
		
		
		
	}
	
	@Test
	public void deleteEmployeeByIdTest() {
	
		EmployeeModel employee = new EmployeeModel(1,"abc","xyz","abc@gmail.com");
		service.deleteEmployee(employee.getId());
		verify(repository,times(1)).deleteById(1);
		
		
	}
	
	
}
