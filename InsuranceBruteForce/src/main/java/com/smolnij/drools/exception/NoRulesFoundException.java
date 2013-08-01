package com.smolnij.drools.exception;

public class NoRulesFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public NoRulesFoundException(){
		super();
	};
	public NoRulesFoundException(String message){
		super(message);
	};
}