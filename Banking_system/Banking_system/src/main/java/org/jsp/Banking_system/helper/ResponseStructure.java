package org.jsp.Banking_system.helper;

import org.jsp.Banking_system.dto.BankAccount;

import lombok.Data;

@Data
public class ResponseStructure<T> {
		int code;
		String msg;
		T data;	
		
		

	}


