package org.jsp.Banking_system.controller;

import java.util.List;

import org.jsp.Banking_system.Exception.MyException;
import org.jsp.Banking_system.dto.BankAccount;
import org.jsp.Banking_system.dto.BankTransaction;
import org.jsp.Banking_system.dto.Customer;
import org.jsp.Banking_system.dto.Login;

import org.jsp.Banking_system.helper.ResponseStructure;
import org.jsp.Banking_system.service.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("customer")
public class customercontroller {
	
	@Autowired
	CustomerService service;
	
	@PostMapping("add")
	  public ResponseStructure<Customer> saveCustomer(@RequestBody Customer customer) throws MyException{
	    return service.saveCustomer(customer);
	    
	  }
	  @PutMapping("otp/{cust_id}/{otp}")
	  public ResponseStructure<Customer> otpVerify(@PathVariable int cust_id, @PathVariable int otp) throws MyException{
	    return service.otpverify(cust_id,otp);
	  }
	  
	  @PostMapping("login")
	  public ResponseStructure<Customer> login(@RequestBody Login login) throws Exception
	  {
		  return service.login(login);
	  }
	 @PostMapping("accounts/{cust_id}/{type}")
	 public ResponseStructure<Customer> createAccount(@PathVariable int cust_id,@PathVariable String type) throws MyException
	 {
		return service.createAccount(cust_id, type);
		 
	  }
	  @GetMapping("accounts/{cust_id}")
	  public ResponseStructure<List<BankAccount>> featchAllTrue(@PathVariable int cust_id) throws MyException
	  {
		  return service.featchAccountTrue(cust_id);
	  }
	  @GetMapping("accounts/checkBalance/{acno}")
	  public ResponseStructure<Double> checkBalance(@PathVariable long acno)
	  {
		  return service.checkBalance(acno);
	  }
	  @PutMapping("accounts/deposit/{acno}/{amount}")
	  public ResponseStructure<BankAccount> deposit(@PathVariable long acno,@PathVariable double amount)
	  {
		  return service.deposit(acno,amount);
	  }
	  @PutMapping("accounts/withdraw/{acno}/{amount}")
	  public ResponseStructure<BankAccount> withdraw(@PathVariable long acno,@PathVariable double amount) throws MyException
	  {
		  return service.withdraw(acno,amount);
	  }
	  @GetMapping("accounts/viewtransaction/{acno}")
	  public ResponseStructure<List<BankTransaction>> viewTransaction(@PathVariable long acno) throws MyException
	  {
		return service.viewTransaction(acno);
		  
	  }
	}



