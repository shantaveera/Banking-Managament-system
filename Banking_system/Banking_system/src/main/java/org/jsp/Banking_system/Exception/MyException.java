package org.jsp.Banking_system.Exception;

public class MyException extends Exception {
	String msg="Id not Found";
	  public MyException(String msg) {
	    this.msg=msg;
	  }
	  public MyException() {
	    
	  }
	  @Override
	  public String toString() {
	    return  msg;
	  }
	}


