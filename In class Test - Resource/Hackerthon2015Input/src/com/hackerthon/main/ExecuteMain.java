package com.hackerthon.main;

import java.sql.SQLException;

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import com.hackerthon.common.UtilTransform;
import com.hackerthon.service.EmployeeService;

public class ExecuteMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		EmployeeService employeeService = new EmployeeService();
		try {
			UtilTransform.rEQUESTtRANSFORM();
			employeeService.EmployeeXml();
			employeeService.EmployeeCreate();
			employeeService.EmployeeAdd();
			employeeService.EmployeeGetById("EMP10004");
 		    employeeService.EmployeeDelete("EMP10001");
			employeeService.EmployeeDisplay();
		} catch (Exception exception) {
			exception.printStackTrace();		       
		}

	}

}
