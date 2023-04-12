package com.asint.spring.crud.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.asint.spring.crud.example.entity.EmployeeModel;
import com.asint.spring.crud.example.service.EmployeeService;

@RestController
public class EmployeeController {
	
	@Autowired
	private EmployeeService service;
	
	@PostMapping("/addEmployee")
	public EmployeeModel addEmployee(@RequestBody EmployeeModel employee) {
		return service.saveEmployee(employee);
	}
	
	@PostMapping("/addEmployees")
	public List<EmployeeModel> addEmployees(@RequestBody List<EmployeeModel> employees){
		return service.saveEmployees(employees);
	}
	
	@GetMapping("/employees")
	public List<EmployeeModel> findAllEmployees(){
		return service.getEmployees();
	}
	
	@GetMapping("/employee/{id}")
	public EmployeeModel findEmployeeById(@PathVariable int id) {
		return service.getEmployeeById(id);
	}
	
	@GetMapping("/employeeByFirstName/{firstName}")
	public EmployeeModel findEmployeeByFirstName(@PathVariable String firstName) {
		return service.getEmployeeByFirstName(firstName);
	}
	
	@PutMapping("/update")
	public EmployeeModel updateEmployee(@RequestBody EmployeeModel employee) {
		return service.updateEmployee(employee);
	}
	
	@PutMapping("/updateEmployees")
	public List<EmployeeModel> updateEmployees(@RequestBody List<EmployeeModel> employee) {
		return service.updateEmployees(employee);
	}
	
	@DeleteMapping("/delete/{id}")
	public String deleteEmployee(@PathVariable int id) {
		return service.deleteEmployee(id);
		
	}
	
	
	
	
	
	
}
