package com.epam.task8.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * This class provides jpa entity manager factory
 * 
 * @author Siarhei_Stsiapanau
 * 
 */
public final class EntityManagerFactoryWrapper {
    private static final String UNIT_NAME = "employeesList";
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory(UNIT_NAME);
    
    private EntityManagerFactoryWrapper(){
    }
    
    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
