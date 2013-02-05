package com.epam.task8.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;

/**
 * @author Siarhei_Stsiapanau
 * 
 */
@Entity
@Table(name = "ADDRESS")
@BatchSize(size = 100)
public class Address implements Serializable {
    private static final long serialVersionUID = -566629084710847588L;
    @Id
    @Column(name = "ID_ADDRESS")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "addressSequence")
    @SequenceGenerator(name = "addressSequence", sequenceName = "ADDRESS_SEQ")
    private int id;
    @Column(name = "STREET")
    private String street;
    @Column(name = "BUILDING")
    private int building;
    @Column(name = "APARTMENT")
    private int apartment;
    @ManyToOne
    @JoinColumn(name = "ID_CITY")
    private City city;

    public Address() {
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
     * @return the street
     */
    public String getStreet() {
	return street;
    }

    /**
     * @param street
     *            the street to set
     */
    public void setStreet(String street) {
	this.street = street;
    }

    /**
     * @return the building
     */
    public int getBuilding() {
	return building;
    }

    /**
     * @param building
     *            the building to set
     */
    public void setBuilding(int building) {
	this.building = building;
    }

    /**
     * @return the apartment
     */
    public int getApartment() {
	return apartment;
    }

    /**
     * @param apartment
     *            the apartment to set
     */
    public void setApartment(int apartment) {
	this.apartment = apartment;
    }

    /**
     * @return the city
     */
    public City getCity() {
	return city;
    }

    /**
     * @param city
     *            the city to set
     */
    public void setCity(City city) {
	this.city = city;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append(city.getCountry().getName());
	builder.append(", ");
	builder.append(city.getName());
	builder.append(", ");
	builder.append(street);
	builder.append(" street, ");
	builder.append(building);
	builder.append(" building, ");
	builder.append(apartment);
	builder.append(" ap. ");
	return builder.toString();
    }

}
