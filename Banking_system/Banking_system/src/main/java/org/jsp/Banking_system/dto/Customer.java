package org.jsp.Banking_system.dto;


import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;

@Entity
@Data
@Component
public class Customer {
	@Id
	@SequenceGenerator(initialValue = 123456701,allocationSize = 1,name = "cust_id",sequenceName = "cust_id")
	@GeneratedValue(generator = "cust_id")
	int  cust_id;
	String name;
	String email;
	String password;
	long mobile;
	boolean status;
	int otp;
	int age;
	Date dob;
	
	@OneToMany(cascade = CascadeType.ALL)
	List<BankAccount> bankAccounts;
		

}
