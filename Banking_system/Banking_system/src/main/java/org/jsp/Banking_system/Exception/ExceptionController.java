package org.jsp.Banking_system.Exception;

import org.jsp.Banking_system.helper.ResponseStructure;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
@ControllerAdvice
public class ExceptionController extends Exception{
	 @ExceptionHandler(value = MyException.class)
	  public ResponseEntity<ResponseStructure<String>> IdNotFoundException(MyException ie) {
	    ResponseStructure<String> responseStructure = new ResponseStructure<String>();
	    responseStructure.setCode(HttpStatus.NOT_FOUND.value());
	    responseStructure.setMsg("Request failed");
	    responseStructure.setData(ie.toString());
	    return new ResponseEntity<ResponseStructure<String>>(responseStructure, HttpStatus.NOT_ACCEPTABLE);
	}
	}
