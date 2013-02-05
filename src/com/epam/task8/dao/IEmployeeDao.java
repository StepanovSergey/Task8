package com.epam.task8.dao;

import java.util.List;

import com.epam.task8.model.Employee;

/**
 * @author Siarhei_Stsiapanau
 * 
 */
public interface IEmployeeDao {
    /**
     * Get employees
     * 
     * @param page
     *            current page. Offset of employees to fetch
     * @param itemsPerPage
     *            number of employees to fetch
     * @return list of employees
     */
    public List<Employee> getEmployees(int page, int itemsPerPage);

    /**
     * Get number of all employees
     * 
     * @return number of employees
     */
    public long getEmployeesCount();

}
