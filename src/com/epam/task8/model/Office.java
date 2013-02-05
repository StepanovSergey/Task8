package com.epam.task8.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

/**
 * @author Siarhei_Stsiapanau
 * 
 */

@Entity
@Table(name = "OFFICE")
public class Office {
    @Id
    @Column(name = "ID_OFFICE")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "officeSequence")
    @SequenceGenerator(name = "officeSequence", sequenceName = "OFFICE_SEQ")
    private int id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_COMPANY")
    private Company company;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_ADDRESS")
    private Address address;
    @Formula("(select count(*) from Employee)")
    private int countOfEmployees;

    public Office() {
    }

    /**
     * @return the countOfEmployees
     */
    public int getCountOfEmployees() {
	return countOfEmployees;
    }

    /**
     * @param countOfEmployees
     *            the countOfEmployees to set
     */
    public void setCountOfEmployees(int countOfEmployees) {
	this.countOfEmployees = countOfEmployees;
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
     * @return the company
     */
    public Company getCompany() {
	return company;
    }

    /**
     * @param company
     *            the company to set
     */
    public void setCompany(Company company) {
	this.company = company;
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
	builder.append("Office [id=");
	builder.append(id);
	builder.append(", company=");
	builder.append(company);
	builder.append(", address=");
	builder.append(address);
	builder.append(", countOfEmployees=");
	builder.append(countOfEmployees);
	builder.append("]");
	return builder.toString();
    }

}
