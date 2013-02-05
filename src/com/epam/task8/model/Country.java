package com.epam.task8.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author Siarhei_Stsiapanau
 * 
 */
@Entity
@Table(name = "COUNTRY")
public class Country {
    @Id
    @Column(name = "ID_COUNTRY")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "countrySequence")
    @SequenceGenerator(name = "countrySequence", sequenceName = "COUNTRY_SEQ")
    private int id;
    @Column(name = "COUNTRY_NAME")
    private String name;

    public Country() {
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
     * @return the name
     */
    public String getName() {
	return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
	this.name = name;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("Country [id=");
	builder.append(id);
	builder.append(", name=");
	builder.append(name);
	builder.append("]");
	return builder.toString();
    }

}
