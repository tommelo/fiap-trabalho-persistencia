package br.com.fiap.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.rowset.CachedRowSet;
import com.sun.rowset.CachedRowSetImpl;

/**
 * Hello world!
 *
 */
public class App {
	
	private final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private final String DB_URL = "jdbc:mysql://localhost/jdbc";
	private final String USER = "root";
	private final String PASSWORD = "root";
	
    public static void main( String[] args ) {
        App app = new App();
        app.execute();
    }
    
    private void execute() {
    	try {
			
    		executeQuery();
			executeTransaction();
			cacheResultSet();
			
		} catch (ClassNotFoundException e) {			
			e.printStackTrace();
		} catch (SQLException e) { 
			e.printStackTrace();
		}
    }
    
    private void executeQuery() throws ClassNotFoundException, SQLException {
    	String query = "SELECT * FROM users";
    	Connection conn = getConnection();
    	CallableStatement stmt = conn.prepareCall(query);
    	ResultSet rs = stmt.executeQuery();
    	    	
    	while(rs.next()) {
    		System.out.println("Usuário encontrado:");
    		System.out.println(rs.getLong("id"));
    		System.out.println(rs.getString("name"));
    	}
    	
    	conn.close();
    }
    
    private void executeTransaction() throws ClassNotFoundException, SQLException {
    	String queryOne = "UPDATE products SET products_left = ?";
    	String queryTwo = "UPDATE sold_products SET sold_count = ?";
    	
    	System.out.println("Efetuando dois updates em uma transação...");
    	
    	Connection conn = getConnection();
    	conn.setAutoCommit(false);
    	
    	CallableStatement stmtOne = conn.prepareCall(queryOne);
    	stmtOne.setInt(1, 50);
    	stmtOne.executeUpdate();
    	
    	CallableStatement stmtTwo = conn.prepareCall(queryTwo);
    	stmtTwo.setInt(1, 50);
    	stmtTwo.executeUpdate();
    	
    	conn.commit();
    	conn.close();
    }
    
    @SuppressWarnings({ "restriction", "resource" })
	private void cacheResultSet() throws ClassNotFoundException, SQLException {
    	String query = "SELECT * FROM users";
    	Connection conn = getConnection();
    	CallableStatement stmt = conn.prepareCall(query);
    	ResultSet rs = stmt.executeQuery();
    	
    	CachedRowSet crs = new CachedRowSetImpl();
    	crs.populate(rs);
    	
    	conn.close();
    	    
    	System.out.println("ResultSet em cache após fechar a conexão!");
    	
    	while(crs.next()) {
    		System.out.println("Usuário encontrado:");
    		System.out.println(crs.getLong("id"));
    		System.out.println(crs.getString("name"));
    	}
    }
    
    private Connection getConnection() throws ClassNotFoundException, SQLException {
    	Class.forName(JDBC_DRIVER);
    	return DriverManager
    			.getConnection(DB_URL, 
    					USER, 
    					PASSWORD);
    }
}
