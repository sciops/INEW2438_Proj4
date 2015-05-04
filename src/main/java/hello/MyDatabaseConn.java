/*
 * The MIT License
 *
 * Copyright 2015 Pivotal Software, Inc..
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package hello;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/*
 Maven dependency, add to pom.xml

 <dependency>
 <groupId>mysql</groupId>
 <artifactId>mysql-connector-java</artifactId>
 <version>5.1.6</version>
 </dependency>
 */

/**
 *
 * @author Stephen R. Williams Attempt at a SQL query runner that is agnostic to
 * entity structure query returns a list of hashmaps which require parsing into
 * an entity class
 */
public class MyDatabaseConn {

    // JDBC driver name and database URL
    private String JDBC_DRIVER;
    private String DB_URL;
    //  Database credentials
    private String USER;
    private String PASS;

    //default constructor with my hardcoded mysql settings
    public MyDatabaseConn() {
        this.JDBC_DRIVER = "com.mysql.jdbc.Driver";
        this.DB_URL = "jdbc:mysql://localhost:3306/mysql";
        this.USER = "root";
        this.PASS = "password";
    }

    //constructor for class re-use
    public MyDatabaseConn(String jdbcDriver, String url, String user, String pass) {
        this.JDBC_DRIVER = jdbcDriver;
        this.DB_URL = url;
        this.USER = user;
        this.PASS = pass;
    }

    public void update(String sql) {
        Connection conn = null;
        Statement stmt = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            //System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            //System.out.println("Connected database successfully...");

            //STEP 4: Execute a query
            //System.out.println("Creating statement...");
            stmt = conn.createStatement();
            //String sql = "UPDATE Registration " +"SET age = 30 WHERE id in (100, 101)";
            //System.out.println("\nString sql = "+sql);
            stmt.executeUpdate(sql);

        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    conn.close();
                }
            } catch (SQLException se) {
            }// do nothing
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
    }

    //http://www.tutorialspoint.com/jdbc/jdbc-delete-records.htm
    public void delete(String sql) {
        Connection conn = null;
        Statement stmt = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            //System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            //System.out.println("Connected database successfully...");

            //STEP 4: Execute a query
            //System.out.println("Creating statement...");
            stmt = conn.createStatement();
            //String sql = "DELETE FROM Registration " + "WHERE id = 101";
            stmt.executeUpdate(sql);

        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    conn.close();
                }
            } catch (SQLException se) {
            }// do nothing
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
    }

    public void insert(String sql) {
        Connection conn = null;
        Statement stmt = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            //System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            //System.out.println("Connected database successfully...");

            //STEP 4: Execute a query
            //System.out.println("Inserting records into the table...");
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            //System.out.println("Inserted records into the table...");

        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    conn.close();
                }
            } catch (SQLException se) {
            }// do nothing
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
    }

    public List<Map<String, Object>> runQuery(String statement) {
        Connection conn = null;
        Statement stmt = null;
        List<Map<String, Object>> tableReturned = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            //System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //STEP 4: Execute a query
            //System.out.println("Creating statement...");
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(statement);//SQL statement is passed in method argument
            //https://stackoverflow.com/questions/2614416/how-to-get-the-number-of-columns-from-a-jdbc-resultset
            //get the number of columns
            ResultSetMetaData rsmd = rs.getMetaData();
            int cols = rsmd.getColumnCount();
            //generate a list of column names for Map
            List<String> columnNames = new ArrayList();
            for (int h = 1; h <= cols; h++) {//columns are NOT zero-based
                //System.out.println("\nColumn #:"+h);
                //System.out.println(" | Name: "+rsmd.getColumnName(h));
                columnNames.add(rsmd.getColumnName(h));
            }

            //list as a table that holds all the maps (records)
            List<Map<String, Object>> table = new ArrayList();

            //STEP 5: Extract data from result set
            while (rs.next()) {//for every row 
                //Map with column name as key, this map acts as a row in the table
                Map<String, Object> record = new HashMap();
                for (int i = 0; i < cols; i++) {//for every column
                    String s = columnNames.get(i);//ZERO BASED
                    Object o = rs.getObject(i + 1);//NOT ZERO BASED
                    //System.out.println("\n"+s+":"+o.toString());
                    //put the cell value into the map                    
                    record.put(s, o);
                }
                //put the record into the table
                //System.out.println("mdb adding " + record.toString());
                table.add(record);
            }

            //assign to return variable to bypass a scoping problem with try/catch
            tableReturned = table;

            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        //System.out.println("Goodbye!");
        return tableReturned;
    }//end main
}
