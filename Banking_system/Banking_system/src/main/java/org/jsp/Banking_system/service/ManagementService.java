package org.jsp.Banking_system.service;

import java.util.List;
import java.util.Optional;

import org.jsp.Banking_system.Exception.MyException;
import org.jsp.Banking_system.Repository.BankRepository;
import org.jsp.Banking_system.Repository.ManagementRepository;
import org.jsp.Banking_system.dto.BankAccount;
import org.jsp.Banking_system.dto.Customer;
import org.jsp.Banking_system.dto.Management;
import org.jsp.Banking_system.helper.ResponseStructure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ManagementService {
	@Autowired
	ManagementRepository repository;
	
	@Autowired
	BankRepository repository2;

	public ResponseStructure<Management> saveManagement(Management management) {
		ResponseStructure structure=new ResponseStructure();
		 structure.setCode(HttpStatus.CREATED.value());
		 structure.setData(repository.save(management));
		 structure.setMsg("Account created succesfully");
		return structure;
	}

	public ResponseStructure<Management> login(Management management) throws MyException {
 ResponseStructure<Management> structure=new ResponseStructure<>();
		 
		 Management management1 =repository.findByEmail(management.getEmail());
		    if( management1==null) 
		    {
		      throw new MyException("Invalid management email");
		    }else {

		      if( management1.getPassword().equals( management.getPassword()))
		      {
		    	 
		    		 structure.setCode(HttpStatus.ACCEPTED.value());
		    		 structure.setMsg("Login success");
		    		 structure.setData( management);
		    	 
		   
		      }
		      else {
		    	  throw new MyException("Invalid password");
		      }
		    }
		return structure;
		    }
		    public ResponseStructure<List<BankAccount>> fetchAllAccounts() throws MyException
		    {
		    	ResponseStructure<List<BankAccount>> structure=new ResponseStructure<List<BankAccount>>();
		    	
		    	List<BankAccount> list=repository2.findAll();
		    	if( list.isEmpty()) 
			    {
			      throw new MyException("no Accounts present");
			    }else {
			    	 structure.setCode(HttpStatus.FOUND.value());
		    		 structure.setMsg("Data Found");
		    		 structure.setData(list); 	 
			   
			      }
		    	return structure;
		    	
		    }
		    public ResponseStructure<BankAccount> changeStatus(long acno)
			{
				ResponseStructure<BankAccount> structure=new ResponseStructure<>();
				
				Optional<BankAccount> optional=repository2.findById(acno);
				BankAccount account=optional.get();
				if(account.isStatus())
				{
					account.setStatus(false);
				}
				else {
					account.setStatus(true);
				}
				structure.setCode(HttpStatus.OK.value());
	    		 structure.setMsg("changed status success"); 
				structure.setData(repository2.save(account)); 	 
		   
				return structure;
			}
	

	}

