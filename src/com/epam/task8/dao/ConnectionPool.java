package com.epam.task8.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * This class provides connection pool
 * 
 * @author Siarhei_Stsiapanau
 * 
 */
public final class ConnectionPool {
    private static final Logger logger = Logger.getLogger(ConnectionPool.class);
    private static final int POOL_SIZE = 5;
    private static final String DRIVER_CLASS = "oracle.jdbc.OracleDriver";
    private static final String URI = "jdbc:oracle:thin:@localhost:1521:XE";
    private static final String USER = "SYSTEM";
    private static final String PASSWORD = "root";
    private static Queue<Connection> occupiedConnections = new ConcurrentLinkedQueue<Connection>();
    private static Queue<Connection> freeConnections = new ConcurrentLinkedQueue<Connection>();
    private static Semaphore semaphore;
    private static ConnectionPool pool = new ConnectionPool();

    /**
     * Initialize connection pool
     */
    private ConnectionPool() {
	semaphore = new Semaphore(POOL_SIZE, true);
	try {
	    Class.forName(DRIVER_CLASS);
	    for (int i = 0; i < POOL_SIZE; i++) {
		Connection connection = openConnection();
		freeConnections.add(connection);
	    }
	} catch (ClassNotFoundException e) {
	    if (logger.isEnabledFor(Level.ERROR)) {
		logger.error(e.getMessage(), e);
	    }
	}
    }

    /**
     * Get connection from pool. If there is no connections available, thread
     * will wait for free connection
     * 
     * @return connection
     */
    public static Connection getConnection() {
	Connection connection = null;
	try {
	    semaphore.acquire();
	    connection = freeConnections.poll();
	    occupiedConnections.add(connection);
	} catch (InterruptedException e) {
	    if (logger.isEnabledFor(Level.ERROR)) {
		logger.error(e.getMessage(), e);
	    }
	}
	return connection;
    }

    private static Connection openConnection() {
	Connection connection = null;
	try {
	    connection = DriverManager.getConnection(URI, USER, PASSWORD);
	} catch (SQLException e) {
	    if (logger.isEnabledFor(Level.ERROR)) {
		logger.error(e.getMessage(), e);
	    }
	}
	return connection;
    }

    /**
     * Releases used connection to pool. If released connection is closed, pool
     * create new connection.
     * 
     * @param connection
     *            connection to release
     */
    public static void releaseConnection(Connection connection) {
	occupiedConnections.remove(connection);
	try {
	    if (connection.isClosed()) {
		connection = openConnection();
	    }
	} catch (SQLException e) {
	    if (logger.isEnabledFor(Level.ERROR)) {
		logger.error(e.getMessage(), e);
	    }
	} finally {
	    freeConnections.add(connection);
	    semaphore.release();
	}
    }

    /**
     * Close all connections of application
     */
    public static void closeAllConnections() {
	closeConnectionsOfQueue(freeConnections);
	closeConnectionsOfQueue(occupiedConnections);
    }

    private static void closeConnectionsOfQueue(Queue<Connection> queue) {
	Iterator<Connection> iterator = queue.iterator();
	while (iterator.hasNext()) {
	    Connection connection = iterator.next();
	    try {
		connection.close();
	    } catch (SQLException e) {
		if (logger.isEnabledFor(Level.ERROR)) {
		    logger.error(e.getMessage(), e);
		}
	    }
	}
    }

    /**
     * @return the pool
     */
    public static ConnectionPool getPool() {
	return pool;
    }

    /**
     * @param pool
     *            the pool to set
     */
    public static void setPool(ConnectionPool pool) {
	ConnectionPool.pool = pool;
    }
}
