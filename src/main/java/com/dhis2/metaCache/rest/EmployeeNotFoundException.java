package com.dhis2.metaCache.rest;

public class EmployeeNotFoundException extends RuntimeException {

	public EmployeeNotFoundException(Long id) {
		super("Could not find employee " + id);
	}
}