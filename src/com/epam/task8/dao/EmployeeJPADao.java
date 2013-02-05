package com.epam.task8.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.epam.task8.model.Employee;
import com.epam.task8.utils.EntityManagerFactoryWrapper;

/**
 * @author Siarhei_Stsiapanau
 * 
 */
public final class EmployeeJPADao implements IEmployeeDao {
    private static final String GET_EMPLOYEE_COUNT = "getEmployeeCount";
    private static final String GET_ALL_EMPLOYEES = "getAllEmployees";

    /*
     * (non-Javadoc)
     * 
     * @see com.epam.task8.dao.IEmployeeDao#getEmployees(int, int)
     */
    @Override
    public List<Employee> getEmployees(int page, int itemsPerPage) {
	List<Employee> list = new ArrayList<>();
	EntityManager em = EntityManagerFactoryWrapper.getEntityManager();
	Query query = em.createNamedQuery(GET_ALL_EMPLOYEES);
	int startFrom = (page - 1) * itemsPerPage;
	query.setFirstResult(startFrom);
	query.setMaxResults(itemsPerPage);
	list = (List<Employee>) query.getResultList();
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
	EntityManager em = EntityManagerFactoryWrapper.getEntityManager();
	employeeCount = (Long) em.createNamedQuery(GET_EMPLOYEE_COUNT)
		.getSingleResult();
	return employeeCount;
    }

}
