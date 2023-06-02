package gov.doc.isu.dwarf.core;

import gov.doc.isu.dwarf.exception.DAOException;
import gov.doc.isu.dwarf.resources.Constants;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

/**
 * BaseDAO class contains methods for managing database connections.
 * 
 * @author Steven L. Skinner
 */
public abstract class BaseDAO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private static Logger log = Logger.getLogger("gov.doc.isu.dwarf.core.BaseDAO");
    private static final String LINESEPERATOR = System.getProperty("line.separator");

    /**
     * Used to obtain connection to database.
     * 
     * @return Connection Database connection
     * @throws DAOException
     *         DAOException
     */
    public synchronized Connection getConnection() throws DAOException {
        log.debug("Entering getConnection. This method is used to obtain connection to database.");
        // Use this connection for deployed project.
        Connection conn = getConnection(Constants.DATASOURCE);
        log.debug("Exiting getConnection. Return value is: " + String.valueOf(conn));
        return conn;
    }// end getConnection

    /**
     * Gets database connection
     * 
     * @param datasource
     *        datasource
     * @return Connection con
     * @throws DAOException
     *         DAOException
     */
    public static Connection getConnection(final DataSource datasource) throws DAOException {
        log.debug("Entering getConnection. This method is used to obtain connection to database. Incoming parameter is: " + String.valueOf(datasource));
        Connection con = null;
        try{
            con = datasource.getConnection();
        }catch(final SQLException se){
            throw new DAOException("BaseDAO.getConnection: SQL Exception while getting DB connection : " + LINESEPERATOR + se);
        }// end try/catch
        log.debug("Exiting getConnection. Return value is: " + String.valueOf(con));
        return con;
    }// end getConnection

    /**
     * This method is used to retrieve a database connection from connnetionPool, we allow it to re-try at most three times. If failed, a exception is thrown.
     * 
     * @param datasourceNameUrlString
     *        String
     * @return java.sql.Connection Database connection
     * @throws DAOException
     *         An exception if the method fails.
     */
    public synchronized Connection getConnection(String datasourceNameUrlString) throws DAOException {
        log.debug("Entering getConnection. This method is used to retrieve a database connection from connnetionPool, we allow it to re-try at most three times. If failed, a exception is thrown. Incoming parameter is: " + String.valueOf(datasourceNameUrlString));
        DataSource ds = null;
        Connection conn = null;
        try{
            InitialContext ic = new InitialContext();
            ds = (DataSource) ic.lookup(datasourceNameUrlString);
        }catch(NamingException ne){
            log.error("NamingException while looking up DB context  : " + ne.getMessage());
            throw new DAOException("NamingException while looking up DB context  : " + ne.getMessage());
        }// end try/catch
        int count = 0;
        try{
            while(null == conn){
                // now get the database connection
                conn = ds.getConnection();
                if(conn == null){
                    count++;
                    if(count >= 4){
                        log.error("failed to get connection in: " + count + " trials." + LINESEPERATOR);
                        throw new Exception("failed to get connection in: " + count + " trials." + LINESEPERATOR);
                    }// end if
                    try{
                        log.error("couldn't get connection and waiting....");
                        Thread.sleep(3000); // wait 3000 millis
                    }catch(InterruptedException ie){
                        log.error("Interrupted while waiting to get connection: " + ie.getMessage());
                        throw new Exception("Interrupted while waiting to get connection: " + ie.getMessage());
                    } // end try/catch
                } // end if
            } // end while
        }catch(Exception e){
            log.error("Error accessing jdbc connection through datasource:" + ds);
            throw new DAOException("Error accessing jdbc connection through datasource:" + ds + " " + e.getMessage());
        } // end catch
        log.debug("Exiting getConnection. Returning connection: " + String.valueOf(conn));
        return conn;
    } // end getConnection

    /**
     * This method is used to destroy or close the database connection, prepared statement, and result set objects in one call.
     * 
     * @param conn
     *        Connection
     * @param pstmt
     *        PreparedStatement
     * @param rs
     *        ResultSet
     */
    public void destroyObjects(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        log.debug("Entering destroyObjects. This method is used to destroy or close the database connection, prepared statement, and result set objects in one call. Incoming parameters are: conn=" + String.valueOf(conn) + ", pstmt=" + String.valueOf(pstmt) + ", rs=" + String.valueOf(rs));
        try{
            if(rs != null){
                if(rs.getWarnings() != null){
                    log.warn(logWarningsFromResultSet(rs));
                } // end if
                rs.close();
            } // end if
        }catch(SQLException e){
            log.error("Exception - Destroy ResultSet - " + e.getMessage() + logSQLException(e));
        } // end try/catch
        try{
            if(pstmt != null){
                if(pstmt.getWarnings() != null){
                    log.warn(logWarningsFromStatement(pstmt));
                } // end if
                pstmt.close();
            } // end if
        }catch(SQLException e){
            log.error("Exception - Destroy Statement - " + e.getMessage() + logSQLException(e));
        } // end try/catch
        try{
            if(conn != null){
                if(!conn.getAutoCommit()){
                    conn.setAutoCommit(true);
                }// end if
                conn.close();
            } // end if
        }catch(SQLException e){
            log.error("Exception - Destroy Connection - " + e.getMessage() + logSQLException(e));
        } // end try/catch
        log.debug("Exiting destroyObjects.");
    } // end destroyObjects

    /**
     * This method is used to print warnings from the result set.
     * 
     * @param rs
     *        ResultSet
     * @throws SQLException
     *         An exception if the method fails.
     */
    public void printWarningsFromResultSet(ResultSet rs) throws SQLException {
        log.debug("Entering printWarningsFromResultSet. This method is used to print warnings from the result set. Incoming parameter is: " + String.valueOf(rs));
        printWarnings(rs.getWarnings());
        log.debug("Exiting printWarningsFromResultSet.");
    }// end printWarningsFromResultSet

    /**
     * This method is used to print an SQL warning from a Statement.
     * 
     * @param stmt
     *        Statement
     * @throws SQLException
     *         An exception if the method fails.
     */
    public void printWarningsFromStatement(final Statement stmt) throws SQLException {
        log.debug("Entering printWarningsFromStatement. This method is used to print an SQL warning. Incoming parameter is: " + String.valueOf(stmt));
        printWarnings(stmt.getWarnings());
        log.debug("Exiting printWarningsFromStatement.");
    }// end printWarningsFromStatement

    /**
     * This method is used to print an SQL warning.
     * 
     * @param warning
     *        SQLWarning
     * @throws SQLException
     *         An exception if the method fails.
     */
    public void printWarnings(SQLWarning warning) throws SQLException {
        log.debug("Entering printWarnings. This method is used to print an SQL warning. Incoming parameter is: " + String.valueOf(warning));
        if(warning != null){
            log.debug(LINESEPERATOR + "---Warning---" + LINESEPERATOR);
            while(warning != null){
                log.debug("Message: " + warning.getMessage());
                log.debug("SQLState: " + warning.getSQLState());
                log.debug("Vendor error code: ");
                log.debug(warning.getErrorCode());
                log.debug("");
                warning = warning.getNextWarning();
            } // end while
        } // end if
        log.debug("Exiting printWarnings.");
    } // end printWarnings

    /**
     * This method is used to log the warnings from a result set.
     * 
     * @param rs
     *        ResultSet
     * @return String String
     * @throws SQLException
     *         An exception if the method fails.
     */
    public String logWarningsFromResultSet(ResultSet rs) throws SQLException {
        log.debug("Entering logWarningsFromResultSet. This method is used to log the warnings from a result set. Incoming parameter is: " + String.valueOf(rs));
        String s = logableWarnings(rs.getWarnings());
        log.debug("Exiting logWarningsFromResultSet.");
        return s;
    }// end logWarningsFromResultSet

    /**
     * This method is used to log the warnings from a statement.
     * 
     * @param stmt
     *        Statement
     * @return String String
     * @throws SQLException
     *         An exception if the method fails.
     */
    public String logWarningsFromStatement(Statement stmt) throws SQLException {
        log.debug("Entering logWarningsFromStatement. This method is used to log the warnings from a statement. Incoming parameter is: " + String.valueOf(stmt));
        String s = logableWarnings(stmt.getWarnings());
        log.debug("Exiting logWarningsFromStatement.");
        return s;
    }// end logWarningsFromStatement

    /**
     * This method is used to construct an SQL warning in a loggable format.
     * 
     * @param warning
     *        SQLWarning
     * @return String String
     * @throws SQLException
     *         An exception if the method fails.
     */
    public String logableWarnings(SQLWarning warning) throws SQLException {
        log.debug("Entering logableWarnings. This method is used to construct an SQL warning in a loggable format. Incoming parameter is: " + String.valueOf(warning));
        StringBuffer sb = new StringBuffer();
        if(warning != null){
            sb.append(LINESEPERATOR + "---Warning---" + LINESEPERATOR);
            while(warning != null){
                sb.append("Message: " + warning.getMessage() + LINESEPERATOR);
                sb.append("SQLState: " + warning.getSQLState() + LINESEPERATOR);
                sb.append("Vendor error code: " + LINESEPERATOR + warning.getErrorCode() + LINESEPERATOR);
                warning = warning.getNextWarning();
            } // end while
        } // end if
        log.debug("Exiting logableWarnings.");
        return sb.toString();
    } // end logableWarnings

    /**
     * This method is used to log an SQL exception.
     * 
     * @param ex
     *        SQLException
     * @return String
     */
    public String logSQLException(SQLException ex) {
        log.debug("Entering logSQLException. This method is used to log an SQL exception. Incoming parameter is: " + String.valueOf(ex));
        SQLException nextException = ex;
        StringBuffer sb = new StringBuffer();
        sb.append(LINESEPERATOR + "---SQLExceptions---" + LINESEPERATOR);
        while(nextException != null){
            if(nextException instanceof SQLException){
                sb.append(nextException.toString() + LINESEPERATOR);
                sb.append("SQLState: " + ((SQLException) nextException).getSQLState() + LINESEPERATOR);
                sb.append("Error Code: " + ((SQLException) nextException).getErrorCode() + LINESEPERATOR);
                sb.append("Message: " + nextException.getMessage() + LINESEPERATOR);
                Throwable t = ex.getCause();
                while(t != null){
                    sb.append("Cause: " + t + LINESEPERATOR);
                    t = t.getCause();
                } // end while
                nextException = nextException.getNextException();
            } // end if
        } // end while
        log.debug("Exiting logSQLException.");
        return sb.toString();
    } // end logSQLException

    /**
     * This method is used to print an SQL exception.
     * 
     * @param ex
     *        SQLException
     */
    public void printSQLException(SQLException ex) {
        log.debug("Entering printSQLException. This method is used to print an SQL exception. Incoming parameter is: " + String.valueOf(ex));
        SQLException nextException = ex;
        while(nextException != null){
            if(nextException instanceof SQLException){
                nextException.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) nextException).getSQLState());
                System.err.println("Error Code: " + ((SQLException) nextException).getErrorCode());
                System.err.println("Message: " + nextException.getMessage());
                Throwable t = ex.getCause();
                while(t != null){
                    log.debug("Cause: " + t);
                    t = t.getCause();
                } // end while
                nextException = nextException.getNextException();
            } // end if
        } // end while
        log.debug("Exiting printSQLException.");
    } // end printSQLException
} // end class
