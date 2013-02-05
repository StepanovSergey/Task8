package com.epam.task8.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Siarhei_Stsiapanau
 * 
 */
@Entity
@Table(name = "WORK")
public class Work implements Serializable {
    private static final long serialVersionUID = 5255241233583304724L;
    @EmbeddedId
    private WorkPK workPK;
    @ManyToOne
    @JoinColumn(name = "id_office", insertable = false, updatable = false, nullable = false)
    private Office office;
    @ManyToOne
    @JoinColumn(name = "id_employee", insertable = false, updatable = false, nullable = false)
    private Employee employee;
    @ManyToOne
    @JoinColumn(name = "ID_POSITION")
    private Position position;

    public Work() {
    }

    /**
     * @return the office
     */
    public Office getOffice() {
	return office;
    }

    /**
     * @param office
     *            the office to set
     */
    public void setOffice(Office office) {
	this.office = office;
    }

    /**
     * @return the employee
     */
    public Employee getEmployee() {
	return employee;
    }

    /**
     * @param employee
     *            the employee to set
     */
    public void setEmployee(Employee employee) {
	this.employee = employee;
    }

    /**
     * @return the workPK
     */
    public WorkPK getWorkPK() {
	return workPK;
    }

    /**
     * @param workPK
     *            the workPK to set
     */
    public void setWorkPK(WorkPK workPK) {
	this.workPK = workPK;
    }

    /**
     * @return the position
     */
    public Position getPosition() {
	return position;
    }

    /**
     * @param position
     *            the position to set
     */
    public void setPosition(Position position) {
	this.position = position;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("Work [workPK=");
	builder.append(workPK);
	builder.append(", office=");
	builder.append(office);
	builder.append(", employee=");
	builder.append(employee);
	builder.append(", position=");
	builder.append(position);
	builder.append("]");
	return builder.toString();
    }
}