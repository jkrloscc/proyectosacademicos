package controller;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;


/**
 * Application Lifecycle Listener implementation class ServletContextListener
 *
 */
public class ServletContextListener implements javax.servlet.ServletContextListener {

	private static final Logger logger = Logger.getLogger(ServletContextListener.class.getName());

	private Connection conn;
	/**
	 * Default constructor. 
	 */
	public ServletContextListener() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent event) {


		logger.info("Creating DB");
		Connection conn = null;

		try {
		Class.forName("org.hsqldb.jdbcDriver");
		} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}

		try {
		conn = DriverManager.getConnection("jdbc:hsqldb:file:C:/Users/Krlos/workspace/PI/TrelloRES/WebContent/WEB-INF/lib/db",
		"sa", // username
		""); //password

		ServletContext sc = event.getServletContext();
		sc.setAttribute("dbConn", conn);

		} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		} 

		logger.info("DB created");
	}

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent event) {
		// TODO Auto-generated method stub
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("SHUTDOWN");
			conn.close();

			Enumeration<Driver> drivers = DriverManager.getDrivers();
			while (drivers.hasMoreElements()) {
				Driver driver = drivers.nextElement();
				try {
					DriverManager.deregisterDriver(driver);
					//	                LOG.log(Level.INFO, String.format("deregistering jdbc driver: %s", driver));
				} catch (SQLException e) {
					//	                LOG.log(Level.SEVERE, String.format("Error deregistering driver %s", driver), e);
				}

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
