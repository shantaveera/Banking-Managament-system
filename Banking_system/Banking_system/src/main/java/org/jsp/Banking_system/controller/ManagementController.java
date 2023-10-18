package org.jsp.Banking_system.controller;

import java.util.List;

import org.jsp.Banking_system.Exception.MyException;
import org.jsp.Banking_system.Repository.ManagementRepository;
import org.jsp.Banking_system.dto.BankAccount;
import org.jsp.Banking_system.dto.Management;
import org.jsp.Banking_system.helper.ResponseStructure;
import org.jsp.Banking_system.service.ManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("management")
public class ManagementController {
	@Autowired
	ManagementService service;
	
	@PostMapping("add")
	public ResponseStructure<Management> saveManagement(@RequestBody Management management){
		return service.saveManagement(management);
		
	}
	@PostMapping("login")
	public ResponseStructure<Management> login(@RequestBody Management management) throws MyException
	{
		return service.login(management);
	}
	@GetMapping("accounts")
	public ResponseStructure<List<BankAccount>> fetchAllAccounts() throws MyException
	{
		return service.fetchAllAccounts();
		
	}
	@PutMapping("accountchange/{acno}")
	public ResponseStructure<BankAccount> changeStatus(@PathVariable long acno)
	{
		return service.changeStatus(acno);
	}
}
	

