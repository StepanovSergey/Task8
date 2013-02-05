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
@Table(name = "POSITION")
public class Position {
    @Id
    @Column(name = "ID_POSITION")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "positionSequence")
    @SequenceGenerator(name = "positionSequence", sequenceName = "POSITION_SEQ")
    private int id;
    @Column(name = "POSITION_NAME")
    private String name;

    public Position() {
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
	builder.append("Position ");
	builder.append(name);
	return builder.toString();
    }

}
