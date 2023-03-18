package com.hackerthon.service;

import org.xml.sax.SAXException;
import java.sql.Connection;
import java.util.logging.Logger;
import java.sql.DriverManager;
import javax.xml.parsers.ParserConfigurationException;
import java.sql.PreparedStatement;
import javax.xml.xpath.XPathExpressionException;
import com.hackerthon.common.UtilTransform;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.sql.Statement;
import com.hackerthon.common.UtilQ;
import java.io.IOException;
import com.hackerthon.model.Employee;
import java.util.ArrayList;
import java.util.Map;

import com.hackerthon.common.CommonConstant;
import com.hackerthon.common.UtilC;

public class EmployeeService extends UtilC {

	private final ArrayList<Employee> employees = new ArrayList<>();

	private static Connection connection;

	private static Statement statement;

	private PreparedStatement preparedStatement;
	
	private static final Logger Log =Logger.getLogger(Employee.class.getName());
	
	
	//Create the database connection
	public EmployeeService() {
		try {
			Class.forName(CommonConstant.MY_SQL_JDBC_DRIVER);
			connection = DriverManager.getConnection(properties.getProperty(CommonConstant.URL), properties.getProperty(CommonConstant.DATABASE_USERNAME),
					properties.getProperty(CommonConstant.DATABASE_PASSWORD));
		} catch (Exception exception) {
			 exception.printStackTrace();
		} 
	}
	
	//getting employees data from XML File and set

	public void EmployeeXml() {

		try {
			int s = UtilTransform.XMLXPATHS().size();
			
			for (int i = 0; i < s; i++) {
				Map<String, String> l = UtilTransform.XMLXPATHS().get(i);
				Employee EMPLOYEE = new Employee();
                EMPLOYEE.setEmployeeId(l.get(CommonConstant.EMPLOYEE_ID));
                EMPLOYEE.setFullName(l.get(CommonConstant.EMPLOYEE_NAME));
                EMPLOYEE.setAddress(l.get(CommonConstant.EMPLOYEE_ADDRESS));
                EMPLOYEE.setFacultyName(l.get( CommonConstant.EMPLOYEE_FACULTY_NAME));
                EMPLOYEE.setDepartment(l.get(CommonConstant.DEPARTEMENT));
				EMPLOYEE.setDesignation(l.get(CommonConstant.DESIGNATION));
				employees.add(EMPLOYEE);
				System.out.println(EMPLOYEE.toString() + "\n");
			}
		} catch (Exception exception) {
			throw new RuntimeException("Error getting with read xml".concat(exception.getMessage()));
		}
	}
	
	//Creating employees table
	public void EmployeeCreate() {
		try {
			statement = connection.createStatement();
			statement.executeUpdate(UtilQ.Q("q2"));
			statement.executeUpdate(UtilQ.Q("q1"));
		} catch (Exception exception) {
		}
	}
	
	
	
	//Adding an employee details to the database
	public void EmployeeAdd() {
		
			
			
			employees.forEach(employee->{
				
				try {
				preparedStatement.setString(1, employee.getEmployeeId());
				preparedStatement.setString(2, employee.getFullName());
				preparedStatement.setString(3, employee.getAddress());
				preparedStatement.setString(4, employee.getFacultyName());
				preparedStatement.setString(5, employee.getDepartment());
				preparedStatement.setString(6, employee.getDesignation());
				
				preparedStatement.addBatch();
				
		       } catch (SQLException exception) {
		    	   exception.printStackTrace();
		       }
	
	});
	}
	
	//Get the employee details by ID from the database
	public void EmployeeGetById(String EmployeeID) throws Exception{

		Employee e = new Employee();
		
			preparedStatement = connection.prepareStatement(UtilQ.Q("q4"));
			preparedStatement.setString(1, EmployeeID);
			ResultSet Resultset = preparedStatement.executeQuery();
                       while (Resultset.next()) {
				e.setEmployeeId(Resultset.getString(1));
				e.setFullName(Resultset.getString(2));
				e.setAddress(Resultset.getString(3));
				e.setFacultyName(Resultset.getString(4));
				e.setDepartment(Resultset.getString(5));
				e.setDesignation(Resultset.getString(6));
			}
			ArrayList<Employee> l = new ArrayList<Employee>();
			l.add(e);
			EmployeeOutput(l);
		
		}
	
	
	//Delete the employee by ID

	public void EmployeeDelete (String eid) throws Exception  {

		
			preparedStatement = connection.prepareStatement(UtilQ.Q("q6"));
			preparedStatement.setString(1, eid);
			preparedStatement.executeUpdate();
		
	}
	
	//Display the employee details

	public void EmployeeDisplay() throws Exception{

		ArrayList<Employee> l = new ArrayList<Employee>();
		
			preparedStatement = connection.prepareStatement(UtilQ.Q("q5"));
			ResultSet result = preparedStatement.executeQuery();
			    while (result.next()) {
				Employee employee = new Employee();
				employee.setEmployeeId(result.getString(1));
                employee.setFullName(result.getString(2));
				employee.setAddress(result.getString(3));
				employee.setFacultyName(result.getString(4));
				employee.setDepartment(result.getString(5));
				employee.setDesignation(result.getString(6));
				l.add(employee);
			}
		
		EmployeeOutput(l);
	}
	
	//Display the output from an array list
	public void EmployeeOutput(ArrayList<Employee> l){
		
		System.out.println();
		
		Log.info("Employee ID" + "\t\t" + "Full Name" + "\t\t" + "Address" + "\t\t" + "Faculty Name" + "\t\t"
				  + "Department" + "\t\t" + "Designation" + "\n");
		System.out.println("================================================================================================================");
		
		
		for(int i = 0; i < l.size(); i++){
			
			Employee employee = l.get(i);
			System.out.println(employee.getEmployeeId() + "\t" + employee.getFullName() + "\t\t"
            + employee.getAddress() + "\t" + employee.getFacultyName() + "\t" + employee.getDepartment() + "\t"
		    + employee.getDesignation() + "\n");
			System.out.println("----------------------------------------------------------------------------------------------------------------");
		}
		
	}
}
