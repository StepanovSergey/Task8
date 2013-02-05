/**
 * 
 */
package com.epam.task8.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.epam.task8.model.Address;
import com.epam.task8.model.City;
import com.epam.task8.model.Company;
import com.epam.task8.model.Country;
import com.epam.task8.model.Employee;
import com.epam.task8.model.Office;
import com.epam.task8.model.Position;
import com.epam.task8.model.Work;

/**
 * @author Siarhei_Stsiapanau
 * 
 */
public final class EmployeeJDBCDao implements IEmployeeDao {
    private static final String EMPLOYEE_COUNT = "EMPLOYEE_COUNT";
    private static final String OFFICE_APARTMENT = "OFFICE_APARTMENT";
    private static final String OFFICE_BUILDING = "OFFICE_BUILDING";
    private static final String OFFICE_STREET = "OFFICE_STREET";
    private static final String OFFICE_CITY_NAME = "OFFICE_CITY_NAME";
    private static final String OFFICE_COUNTRY_NAME = "OFFICE_COUNTRY_NAME";
    private static final String COMPANY_NAME = "COMPANY_NAME";
    private static final String LASTNAME = "LASTNAME";
    private static final String FIRSTNAME = "FIRSTNAME";
    private static final String EMPLOYEE_APARTMENT = "EMPLOYEE_APARTMENT";
    private static final String EMPLOYEE_BUILDING = "EMPLOYEE_BUILDING";
    private static final String EMPLOYEE_STREET = "EMPLOYEE_STREET";
    private static final String EMPLOYEE_CITY_NAME = "EMPLOYEE_CITY_NAME";
    private static final String EMPLOYEE_COUNTRY_NAME = "EMPLOYEE_COUNTRY_NAME";
    private static final String POSITION_NAME = "POSITION_NAME";
    private static final String ID_EMPLOYEE = "ID_EMPLOYEE";
    private static final Logger logger = Logger
	    .getLogger(EmployeeJDBCDao.class);

    private static final String GET_ALL_EMPLOYEES_QUERY = " select * from ( select a.*, ROWNUM rnum from ( "
	    + "SELECT employee_country.COUNTRY_NAME AS EMPLOYEE_COUNTRY_NAME, "
	    + "employee_city.CITY_NAME AS EMPLOYEE_CITY_NAME, employee_address.STREET AS EMPLOYEE_STREET, "
	    + "employee_address.BUILDING AS EMPLOYEE_BUILDING, employee_address.APARTMENT AS EMPLOYEE_APARTMENT, "
	    + "employee.ID_EMPLOYEE AS ID_EMPLOYEE, employee.FIRSTNAME AS FIRSTNAME, employee.LASTNAME AS LASTNAME "
	    + "FROM EMPLOYEE "
	    + "INNER JOIN address employee_address ON employee_address.ID_ADDRESS = employee.ID_ADDRESS "
	    + "INNER JOIN city employee_city ON employee_city.ID_CITY = employee_address.ID_CITY "
	    + "INNER JOIN country employee_country ON employee_country.ID_COUNTRY = employee_city.ID_COUNTRY order by ID_EMPLOYEE ) a "
	    + "where ROWNUM <= ? ) where rnum > ?";
    private static final String GET_EMPLOYEE_COUNT_QUERY = "select count(*) from employee";
    private static final String GET_WORKS_BY_EMPLOYEE_ID_QUERY = "select position.POSITION_NAME AS POSITION_NAME, "
	    + "company.COMPANY_NAME AS COMPANY_NAME,  office_country.COUNTRY_NAME AS OFFICE_COUNTRY_NAME,  "
	    + "office_city.CITY_NAME AS OFFICE_CITY_NAME,  office_address.STREET AS OFFICE_STREET,  "
	    + "office_address.BUILDING AS OFFICE_BUILDING,  office_address.APARTMENT AS OFFICE_APARTMENT,  "
	    + "(select count(*) from work w where office.id_office=w.ID_OFFICE ) AS EMPLOYEE_COUNT from work w "
	    + "INNER JOIN office ON office.ID_OFFICE = w.ID_OFFICE "
	    + "INNER JOIN address office_address ON office.ID_ADDRESS = office_address.ID_ADDRESS "
	    + "INNER JOIN city office_city ON office_city.ID_CITY = office_address.ID_CITY "
	    + "INNER JOIN country office_country ON office_country.ID_COUNTRY = office_city.ID_COUNTRY "
	    + "INNER JOIN company ON company.ID_COMPANY = office.ID_COMPANY "
	    + "INNER JOIN position ON position.ID_POSITION = w.ID_POSITION where w.id_employee=?";

    /*
     * (non-Javadoc)
     * 
     * @see com.epam.task8.dao.IEmployeeDao#getEmployees(int, int)
     */
    @Override
    public List<Employee> getEmployees(int page, int itemsPerPage) {
	List<Employee> list = new ArrayList<>();
	Connection connection = ConnectionPool.getConnection();
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	try {
	    preparedStatement = connection
		    .prepareStatement(GET_ALL_EMPLOYEES_QUERY);
	    int startFrom = (page - 1) * itemsPerPage;
	    int endTo = startFrom + itemsPerPage;
	    preparedStatement.setInt(1, endTo);
	    preparedStatement.setInt(2, startFrom);
	    resultSet = preparedStatement.executeQuery();
	    while (resultSet.next()) {
		Employee employee = new Employee();
		employee = setEmployeeParameters(resultSet);
		list.add(employee);
	    }
	} catch (SQLException e) {
	    if (logger.isEnabledFor(Level.ERROR)) {
		logger.error(e.getMessage(), e);
	    }
	} finally {
	    releaseResources(null, preparedStatement, resultSet);
	    ConnectionPool.releaseConnection(connection);
	}
	return list;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.epam.task8.dao.IEmployeeDao#getEmployeesCount()
     */
    @Override
    public long getEmployeesCount() {
	Long employeeCount = 0L;

	Connection connection = ConnectionPool.getConnection();
	Statement statement = null;
	ResultSet resultSet = null;
	try {
	    statement = connection.createStatement();
	    resultSet = statement.executeQuery(GET_EMPLOYEE_COUNT_QUERY);
	    if (resultSet.next()) {
		employeeCount = resultSet.getLong(1);
	    } else {
		throw new SQLException("Count employees return 0 rows");
	    }
	} catch (SQLException e) {
	    if (logger.isEnabledFor(Level.ERROR)) {
		logger.error(e.getMessage(), e);
	    }
	} finally {
	    releaseResources(statement, null, resultSet);
	    ConnectionPool.releaseConnection(connection);
	}

	return employeeCount;
    }

    private Employee setEmployeeParameters(ResultSet resultSet) {
	Employee employee = new Employee();
	Country employeeCountry = new Country();
	City employeeCity = new City();
	Address employeeAddress = new Address();

	try {
	    employeeCountry.setName(resultSet.getString(EMPLOYEE_COUNTRY_NAME));

	    employeeCity.setName(resultSet.getString(EMPLOYEE_CITY_NAME));
	    employeeCity.setCountry(employeeCountry);

	    employeeAddress.setStreet(resultSet.getString(EMPLOYEE_STREET));
	    employeeAddress.setBuilding(resultSet.getInt(EMPLOYEE_BUILDING));
	    employeeAddress.setApartment(resultSet.getInt(EMPLOYEE_APARTMENT));
	    employeeAddress.setCity(employeeCity);

	    employee.setFirstName(resultSet.getString(FIRSTNAME));
	    employee.setLastName(resultSet.getString(LASTNAME));
	    employee.setAddress(employeeAddress);

	    int idEmployee = resultSet.getInt(ID_EMPLOYEE);
	    employee.setWorks(getWorksByEmployee(idEmployee));

	} catch (SQLException e) {
	    if (logger.isEnabledFor(Level.ERROR)) {
		logger.error(e.getMessage(), e);
	    }
	}
	return employee;
    }

    private Set<Work> getWorksByEmployee(int idEmployee) {
	Set<Work> works = new HashSet<>();
	Connection connection = ConnectionPool.getConnection();
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	try {
	    preparedStatement = connection
		    .prepareStatement(GET_WORKS_BY_EMPLOYEE_ID_QUERY);
	    preparedStatement.setInt(1, idEmployee);
	    resultSet = preparedStatement.executeQuery();
	    while (resultSet.next()) {
		Work work = setWorkParameters(resultSet);
		works.add(work);
	    }
	} catch (SQLException e) {
	    if (logger.isEnabledFor(Level.ERROR)) {
		logger.error(e.getMessage(), e);
	    }
	} finally {
	    releaseResources(null, preparedStatement, resultSet);
	    ConnectionPool.releaseConnection(connection);
	}

	return works;
    }

    private Work setWorkParameters(ResultSet resultSet) {
	Position position = new Position();
	Office office = new Office();
	Country officeCountry = new Country();
	City officeCity = new City();
	Address officeAddress = new Address();
	Company company = new Company();
	Work work = new Work();

	try {
	    company.setName(resultSet.getString(COMPANY_NAME));
	    position.setName(resultSet.getString(POSITION_NAME));

	    officeCountry.setName(resultSet.getString(OFFICE_COUNTRY_NAME));

	    officeCity.setName(resultSet.getString(OFFICE_CITY_NAME));
	    officeCity.setCountry(officeCountry);

	    officeAddress.setStreet(resultSet.getString(OFFICE_STREET));
	    officeAddress.setBuilding(resultSet.getInt(OFFICE_BUILDING));
	    officeAddress.setApartment(resultSet.getInt(OFFICE_APARTMENT));
	    officeAddress.setCity(officeCity);

	    office.setAddress(officeAddress);
	    office.setCountOfEmployees(resultSet.getInt(EMPLOYEE_COUNT));
	    office.setCompany(company);

	    work.setOffice(office);
	    work.setPosition(position);
	} catch (SQLException e) {
	    if (logger.isEnabledFor(Level.ERROR)) {
		logger.error(e.getMessage(), e);
	    }
	}

	return work;
    }

    /**
     * Close result set, statement and prepared statement
     * 
     * @param statement
     *            statement to close
     * @param preparedStatement
     *            prepared statement to close
     * @param resultSet
     *            result set to close
     */
    private void releaseResources(Statement statement,
	    PreparedStatement preparedStatement, ResultSet resultSet) {
	if (statement != null) {
	    try {
		statement.close();
	    } catch (SQLException e) {
		if (logger.isEnabledFor(Level.ERROR)) {
		    logger.error(e.getMessage(), e);
		}
	    }
	}
	if (resultSet != null) {
	    try {
		resultSet.close();
	    } catch (SQLException e) {
		if (logger.isEnabledFor(Level.ERROR)) {
		    logger.error(e.getMessage(), e);
		}
	    }
	}
	if (preparedStatement != null) {
	    try {
		preparedStatement.close();
	    } catch (SQLException e) {
		if (logger.isEnabledFor(Level.ERROR)) {
		    logger.error(e.getMessage(), e);
		}
	    }
	}
    }
}
