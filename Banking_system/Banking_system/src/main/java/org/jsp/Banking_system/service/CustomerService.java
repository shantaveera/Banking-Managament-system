package org.jsp.Banking_system.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.jsp.Banking_system.Exception.MyException;
import org.jsp.Banking_system.Repository.BankRepository;
import org.jsp.Banking_system.Repository.CustomerRepository;
import org.jsp.Banking_system.dto.BankAccount;
import org.jsp.Banking_system.dto.BankTransaction;
import org.jsp.Banking_system.dto.Customer;
import org.jsp.Banking_system.dto.Login;
import org.jsp.Banking_system.helper.MailVerification;
import org.jsp.Banking_system.helper.ResponseStructure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
@Service
public class CustomerService {
	@Autowired
	  CustomerRepository repository;
	  
	  @Autowired
	  MailVerification verification;
	  
	  @Autowired
	  BankRepository repository2;
	  
	  @Autowired
	  BankAccount bankAccount;
	  
	  @Autowired
	  BankTransaction bankTransaction;
	  
	  public ResponseStructure<Customer> saveCustomer(Customer customer) throws MyException{
	   ResponseStructure<Customer> structure=new ResponseStructure<>();
	   int age= Period.between(customer.getDob().toLocalDate(), LocalDate.now()).getYears();
	    customer.setAge(age);
	   if(age<18)
	   {
	    throw new MyException ("You should be above 18 to create Account");
	   }
	   else {
	     Random random=new Random();
	     int otp=random.nextInt(100000,999999);
	     customer.setOtp(otp);
	     verification.sendMail(customer);
	     
	    structure.setMsg("verification Mail Sent");
	    structure.setCode(HttpStatus.PROCESSING.value());
	    structure.setData(repository.save(customer));
	   }
	   return structure;
	}

	  public ResponseStructure<Customer> otpverify(int cust_id,int otp) throws MyException {
	    ResponseStructure<Customer> structure=new ResponseStructure<>();
	    Optional<Customer> optional =repository.findById(cust_id);
	    if(optional.isEmpty()) 
	    {
	      throw new MyException("Check Id and Try Again");
	    }else {
	      Customer customer=optional.get();
	      if(customer.getOtp()==otp)
	      {
	        structure.setCode(HttpStatus.CREATED.value());
	        structure.setMsg("Account Created Successfully");
	        customer.setStatus(true);
	        structure.setData(repository.save(customer));
	    }else {
	      throw new MyException  ("OTP MISSMATCH");
	    }
	  }
	    return structure;
	  }

	public ResponseStructure<Customer> login(Login login) throws Exception {
		 ResponseStructure<Customer> structure=new ResponseStructure<>();
		 
		 Optional<Customer> optional =repository.findById(login.getId());
		    if(optional.isEmpty()) 
		    {
		      throw new MyException("Invalid customer Id");
		    }else {
		      Customer customer=optional.get();
		      if(customer.getPassword().equals(login.getPassword()))
		      {
		    	 if(customer.isStatus()) 
		    	 {
		    		 structure.setCode(HttpStatus.ACCEPTED.value());
		    		 structure.setMsg("Login success");
		    		 structure.setData(customer);
		    	 }
		    	 else {
		    		 throw new MyException("Verify your email first");
		    	 }
		      }
		      else {
		    	  throw new MyException("Invalid password");
		      }
		
		return structure;
		    }
	}
		

	public ResponseStructure<Customer> createAccount(int cust_id, String type) throws MyException {
		 ResponseStructure<Customer> structure=new ResponseStructure<>();
		 Optional<Customer> optional =repository.findById(cust_id);
		    if(optional.isEmpty()) 
		    {
		      throw new MyException("Invalid customer Id");
		    }else {
		      Customer customer=optional.get();
		      List<BankAccount> list=customer.getBankAccounts();
		      
		      boolean flag=true;
		      for(BankAccount bankAccount:list )
		      {
		    	  if(bankAccount.getType().equals(type))
		    	  {
		    		  flag=false;
		    		  break;
		    	  }
		      }
		      
		      if(!flag)
		      {
		    	  throw new MyException(type+"Account Already Exists");
		      }else {
		    	  bankAccount.setType(type);
		    	  if(type.equals("savings"))
		    	  {
		    		  bankAccount.setBanklimit(5000);
		    	  }else {
		    		  bankAccount.setBanklimit(10000);
		    	  }
		    	  list.add(bankAccount);
		    	  customer.setBankAccounts(list);
		      }
		    	  structure.setCode(HttpStatus.ACCEPTED.value());
		    	  structure.setData(repository.save(customer));
		    	  structure.setMsg("account created succesfully");
		      }
		    return structure;
	}

	public ResponseStructure<List<BankAccount>> featchAccountTrue(int cust_id) throws MyException {
		ResponseStructure<List<BankAccount>> structure=new ResponseStructure<>();
		Optional<Customer> optional=repository.findById(cust_id);
		Customer customer=optional.get();
		List<BankAccount> list=customer.getBankAccounts();
		List<BankAccount> res=new ArrayList<BankAccount>();
		for(BankAccount account:list)
		{
			if(account.isStatus())
			{
				res.add(account);
			}
		}
		if(res.isEmpty())
		{
			throw new MyException("no Active Accounts found");
		}else {
			 structure.setCode(HttpStatus.FOUND.value());
			 structure.setData(res);
	    	  structure.setMsg("accouts found");
		}
		return structure;
	}

	public ResponseStructure<Double> checkBalance(long acno) {
		ResponseStructure<Double> structure=new ResponseStructure<Double>();
		Optional<BankAccount> optional=repository2.findById(acno);
		BankAccount bankAccount=optional.get();

		 structure.setCode(HttpStatus.OK.value());
		 structure.setData(bankAccount.getAmount());
    	  structure.setMsg("accouts found");
		
		return structure;
	}

	public ResponseStructure<BankAccount> deposit(long acno, double amount) {
		ResponseStructure<BankAccount> structure=new ResponseStructure<BankAccount>();
		BankAccount bankAccount=repository2.findById(acno).get();
		bankAccount.setAmount(bankAccount.getAmount()+amount);

		 bankTransaction.setDateTime(LocalDateTime.now());
		 bankTransaction.setDeposit(amount);
    	  bankTransaction.setBalance(bankAccount.getAmount());
    	  
    	  List<BankTransaction> bankTransactions=bankAccount.getBankTransactions();
    	  bankTransactions.add(bankTransaction);
    	  
    	   bankAccount.setBankTransactions(bankTransactions);
    	   
    	   structure.setCode(HttpStatus.ACCEPTED.value());
  		 structure.setData(repository2.save(bankAccount));
      	  structure.setMsg("amount added succesfully");
    	  
		
		return structure;
	}

	public ResponseStructure<BankAccount> withdraw(long acno, double amount) throws MyException {
		ResponseStructure<BankAccount> structure=new ResponseStructure<BankAccount>();
		BankAccount bankAccount=repository2.findById(acno).get();
		
		if(amount>bankAccount.getBanklimit())
		{
			throw new MyException("out of limit");
		}
		else {
			if(amount>bankAccount.getAmount())
			{
				throw new MyException("insufficient funds");
			}
			else {
				bankAccount.setAmount((bankAccount.getAmount()));
			}
		}
		bankAccount.setAmount(bankAccount.getAmount()-amount);

		 bankTransaction.setDateTime(LocalDateTime.now());
		 bankTransaction.setDeposit(amount);
    	  bankTransaction.setBalance(bankAccount.getAmount());
    	  
    	  List<BankTransaction> bankTransactions=bankAccount.getBankTransactions();
    	  bankTransactions.add(bankTransaction);
    	  
    	   bankAccount.setBankTransactions(bankTransactions);
    	   
    	   structure.setCode(HttpStatus.ACCEPTED.value());
  		 structure.setData(repository2.save(bankAccount));
      	  structure.setMsg("amount added succesfully");
    	  
	
		return structure;

		
	}

	public ResponseStructure<List<BankTransaction>> viewTransaction(long acno)throws MyException{
		ResponseStructure<List<BankTransaction>> structure=new ResponseStructure<>();
		
		BankAccount bankAccount=repository2.findById(acno).get();
		List<BankTransaction>list= bankAccount.getBankTransactions();
		
		if(list.isEmpty())
				{
				throw new MyException("NO Transcation");
			}
			else {
				structure.setCode(HttpStatus.FOUND.value());
	     		 structure.setData(list);
	         	  structure.setMsg("Data Found");
			}
			return structure;	
	}
}

	
