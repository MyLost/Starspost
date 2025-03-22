/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.npd.starspost.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.Future;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.XAConnection;

import org.apache.derby.jdbc.ClientXADataSource;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

/**
 *
 * @author User
 */
public class ConnectionDatabase {

    //JDBC Constance
    private static final String DB_PROVIDER_NAME = "_POSTGRE";
    private static final String DB_URL_DERBY = "jdbc:derby://localhost:1527/starsPostDB;create=true";
    private static final String DB_URL_POSTGRE = "jdbc:derby://localhost:1527/starsPostDB;create=true";
    private static final String DRIVER_NAME = "org.apache.derby.jdbc.ClientDriver";
    private static final String DB_USERNAME= "stars";
    private static final String DB_PASSWORD = "stars12";
    private static final String DB_NAME = "starsPostDB";
    private static final String DB_HOST = "localhost";
    private static final int DB_PORT = 1527;
    private static String dbUrl = null;
    private static String driverName = null;
    private static String dbUsername = null;
    private static String dbPassword = null;
    private static String dbName = null;
    private static String dbHost = null;
    private static String dbPort = null;
    private static String dataSource = null;
    private static String fullConnectionUrl = null;
    //DataSource Constance
    private static final String DATA_SOURCE = "java:comp/env/jdbc/starsPostDataSource";
    // Connection
    private static ConnectionDatabase instance;
    private Connection conn = null;
    //DB url Constant
    private static final String FULL_CONNECTION_URL = "jdbc:derby://localhost:1527/starsPostDB;user=stars;password=stars12;create=false";
    private ConnectionDatabase() {
    }
    public static ConnectionDatabase getInstance() {
        initializeDBProperties();
        if (instance == null) {
            instance = new ConnectionDatabase();
        }
        return instance;
    }
    private static void initializeDBProperties() {
        try(InputStream input =  ConnectionDatabase.class.getClassLoader().getResourceAsStream("META-INF/config/database.properties")){
            if (input == null) {
// logger.error("Sorry, unable to find database.properties");
                return;
            }
            Properties dbProperties = new Properties();
            dbProperties.load(input);
            //logDBProperties(dbProperties);
            setProperties(dbProperties);
        }catch(IOException ex) {
// logger.error(ex.getMessage());
        }
    }
    private static void setProperties(Properties dbProperties) {
        dbUrl = dbProperties.getProperty("DB_URL" + DB_PROVIDER_NAME);
        driverName = dbProperties.getProperty("DRIVER_NAME" + DB_PROVIDER_NAME);
        dbUsername = dbProperties.getProperty("DB_USERNAME" + DB_PROVIDER_NAME);
        dbPassword = dbProperties.getProperty("DB_PASSWORD" + DB_PROVIDER_NAME);
        dbName = dbProperties.getProperty("DB_NAME" + DB_PROVIDER_NAME);
        dbHost = dbProperties.getProperty("DB_HOST"+ DB_PROVIDER_NAME);
        dbPort = dbProperties.getProperty("DB_PORT" + DB_PROVIDER_NAME);
        dataSource = dbProperties.getProperty("DATA_SOURCE" + DB_PROVIDER_NAME);
        fullConnectionUrl =  dbProperties.getProperty("FULL_CONNECTION_URL" + DB_PROVIDER_NAME);
    }
    private static void logDBProperties(Properties dbProperties) {
// logger.info("Database url: " + dbProperties.getProperty("DB_URL" +
// DB_PROVIDER_NAME));
// logger.info("Driver name: " + dbProperties.getProperty("DRIVER_NAME" +
// DB_PROVIDER_NAME));
// logger.info("Database username: " + dbProperties.getProperty("DB_USERNAME" +
// DB_PROVIDER_NAME));
// logger.info("Database password: " + dbProperties.getProperty("DB_PASSWORD" +
// DB_PROVIDER_NAME));
// logger.info("Database name: " + dbProperties.getProperty("DB_NAME" +
// DB_PROVIDER_NAME));
// logger.info("Database host: " + dbProperties.getProperty("DB_HOST" +
// DB_PROVIDER_NAME));
// logger.info("Database port: " + dbProperties.getProperty("DB_PORT" +
// DB_PROVIDER_NAME));
// logger.info("Database source: " + dbProperties.getProperty("DATA_SOURCE" +
// DB_PROVIDER_NAME));
// logger.info("Database full connection url: " +
// dbProperties.getProperty("FULL_CONNECTION_URL" + DB_PROVIDER_NAME));
    }
    @SuppressWarnings("deprecation")
    public Connection getConnectionJDBC() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

        Class.forName(driverName).newInstance();
        conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        return conn;
    }
    public Connection getConnectionDS(String option) throws ClassNotFoundException, SQLException, Exception {
        if(option.equals("tomee_ds_props")) {
            //Get data source with Props
            PoolProperties properties = new PoolProperties();
            properties.setUrl(dbUrl);
            properties.setDriverClassName(driverName);
            properties.setUsername(dbUsername);
            properties.setPassword(dbPassword);
            org.apache.tomcat.jdbc.pool.DataSource datasource = new DataSource();
            datasource.setPoolProperties(properties);
            conn = datasource.getConnection();
            return conn;
        }
        if(option.equals("tomeeAsync_ds_props")) {
            PoolProperties properties = new PoolProperties();
            properties.setUrl(dbUrl);
            properties.setDriverClassName(driverName);
            properties.setUsername(dbUsername);
            properties.setPassword(dbPassword);
            org.apache.tomcat.jdbc.pool.DataSource datasource = new DataSource();
            datasource.setPoolProperties(properties);
            Future<Connection> future = datasource.getConnectionAsync();
            conn = future.get();
            return conn;
        }
        if(option.equals("tomme_ds")) {
            Context context = new InitialContext();
            Context envContext = (Context) context.lookup("java:comp/env");
// logger.info("Environment context: " + envContext);
            javax.sql.DataSource dataSource = (javax.sql.DataSource)context.lookup("java:comp/env/jdbc/starsPostDataSource");
// logger.info("Datasource: " + dataSource);
            conn = dataSource.getConnection();
            return conn;
        }
        return null;
    }
    public XAConnection getConnectionXDS(String managed) throws SQLException, NamingException, ClassNotFoundException {
        if(managed.equals("local")) {
            ClientXADataSource xaDataSource = new ClientXADataSource();
            xaDataSource.setDatabaseName (dbName);
            xaDataSource.setServerName(dbHost);
            xaDataSource.setPortNumber(Integer.parseInt(dbPort));
// logger.info("XADatasource " + xaDataSource);
// logger.info("XAconnection " + xaDataSource.getXAConnection());
            return xaDataSource.getXAConnection(dbUsername, dbPassword);
        }
        //This code don't wotk. Why - I don't know!!!
        //Context ctx = new InitialContext();
        //javax.sql.XADataSource sqlXADS= (javax.sql.XADataSource) ctx.lookup("java:comp/env/jdbc/starsPostDataSourceJTA");
        //logger.info("XADatasource " + sqlXADS);
        //logger.info("XAconnection " + sqlXADS.getXAConnection().getConnection());
        //return sqlXADS.getXAConnection();
        return null;
    }
}
