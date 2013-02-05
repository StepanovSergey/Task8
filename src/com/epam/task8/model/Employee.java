package com.epam.task8.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;

/**
 * @author Siarhei_Stsiapanau
 * 
 */
@NamedQueries({
	@NamedQuery(name = "getEmployeeCount", query = "select count(e) from Employee e"),
	@NamedQuery(name = "getAllEmployees", query = "from Employee e") })
@Entity
@Table(name = "EMPLOYEE")
public class Employee implements Serializable {
    private static final long serialVersionUID = 4462945517583558083L;
    @Id
    @Column(name = "ID_EMPLOYEE")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "employeeSequence")
    @SequenceGenerator(name = "employeeSequence", sequenceName = "EMPLOYEE_SEQ")
    private int id;
    @Column(name = "FIRSTNAME")
    private String firstName;
    @Column(name = "LASTNAME")
    private String lastName;
    @OneToOne
    @JoinColumn(name = "ID_ADDRESS")
    private Address address;
    @OneToMany(mappedBy = "employee", fetch = FetchType.EAGER)
    @BatchSize(size = 100)
    private Set<Work> works;

    public Employee() {
    }

    /**
     * @return the works
     */
    public Set<Work> getWorks() {
	return works;
    }

    /**
     * @param works
     *            the works to set
     */
    public void setWorks(Set<Work> works) {
	this.works = works;
    }

    /**
     * @return the id
     */
    public int getId() {
	return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(int id) {
	this.id = id;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
	return firstName;
    }

    /**
     * @param firstName
     *            the firstName to set
     */
    public void setFirstName(String firstName) {
	this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
	return lastName;
    }

    /**
     * @param lastName
     *            the lastName to set
     */
    public void setLastName(String lastName) {
	this.lastName = lastName;
    }

    /**
     * @return the address
     */
    public Address getAddress() {
	return address;
    }

    /**
     * @param address
     *            the address to set
     */
    public void setAddress(Address address) {
	this.address = address;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("Employee [id=");
	builder.append(id);
	builder.append(", firstName=");
	builder.append(firstName);
	builder.append(", lastName=");
	builder.append(lastName);
	builder.append(", address=");
	builder.append(address);
	builder.append(", works=");
	builder.append(works);
	builder.append("]");
	return builder.toString();
    }
}
