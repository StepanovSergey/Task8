package com.epam.task8.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author Siarhei_Stsiapanau
 * 
 */
@Entity
@Table(name="CITY")
public class City {
    @Id
    @Column(name="ID_CITY")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "citySequence")
    @SequenceGenerator(name = "citySequence", sequenceName = "CITY_SEQ")
    private int id;
    @Column(name="CITY_NAME")
    private String name;
    @ManyToOne
    @JoinColumn(name="ID_COUNTRY")
    private Country country;

    public City() {
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

    /**
     * @return the country
     */
    public Country getCountry() {
	return country;
    }

    /**
     * @param country
     *            the country to set
     */
    public void setCountry(Country country) {
	this.country = country;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("City [id=");
	builder.append(id);
	builder.append(", name=");
	builder.append(name);
	builder.append(", country=");
	builder.append(country);
	builder.append("]");
	return builder.toString();
    }


}
