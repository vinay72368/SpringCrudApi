package com.asint.spring.crud.example.service;

import com.asint.spring.crud.example.entity.EmployeeModel;
import com.asint.spring.crud.example.repository.EmployeeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.NoSuchElementException;

@Service
public class EmployeeService {
	@Autowired
	private EmployeeRepository repository;

	public EmployeeModel saveEmployee(EmployeeModel employee) {
		return repository.save(employee);
	}

	public List<EmployeeModel> saveEmployees(List<EmployeeModel> employees) {
		List<EmployeeModel> savedEmployees = new ArrayList<>();
		int batchSize = 1000;
		int i =0;
		while(i<employees.size()) {
			List<EmployeeModel> batch = employees.subList(i, Math.min(i+batchSize, employees.size()));
			savedEmployees.addAll(repository.saveAll(batch));
			i+= batchSize;
		}
		return savedEmployees;
	}

	public List<EmployeeModel> getEmployees() {
		Pageable pageable = PageRequest.of(0,1000);
		List<EmployeeModel> employees = repository.findAll(pageable).getContent();
		if (employees.isEmpty()) {
			throw new NoSuchElementException("No Employees record found");
		}
		return employees;
	}

	public EmployeeModel getEmployeeById(int id) {
		Optional<EmployeeModel> optionalEmployee = repository.findById(id);
		return optionalEmployee.orElseThrow(()->new NoSuchElementException("No Employee Found with this ID"));
	}

	public EmployeeModel getEmployeeByFirstName(String firstName) {

		EmployeeModel employee = repository.findByFirstName(firstName);
		if (employee == null) {
			throw new NoSuchElementException("No Employee found with first Name: " + firstName);
		}
		return employee;

	}

	public String deleteEmployee(int id) {
		boolean result = !repository.existsById(id);
		if (result) {
			
			repository.deleteById(id);
			return "Employee removed !! " + id;

		}
		else {
			
			throw new NoSuchElementException("No Employee found with id: "+id);
		}

	}

	public EmployeeModel updateEmployee(EmployeeModel employee) {
		EmployeeModel existingProduct = repository.findById(employee.getId()).orElse(null);
		existingProduct.setFirstName(employee.getFirstName());
		existingProduct.setLastName(employee.getLastName());
		existingProduct.setEmail(employee.getEmail());
		return repository.save(existingProduct);
	}
	
	public List<EmployeeModel> updateEmployees(List<EmployeeModel> employee){
		List<EmployeeModel> updatedEmployees = new ArrayList<>();
		for(EmployeeModel employees : employee) {
			EmployeeModel existingEmployee = repository.findById(employees.getId()).orElse(null);
			if(existingEmployee !=null) {
				existingEmployee.setFirstName(employees.getFirstName());
				existingEmployee.setLastName(employees.getLastName());
				existingEmployee.setEmail(employees.getEmail());
				updatedEmployees.add(repository.save(existingEmployee));
				
				
			}
			
			
		}
		return updatedEmployees;
		
	}

}