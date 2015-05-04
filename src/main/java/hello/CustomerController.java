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

/**
 *
 * @author Stephen R. Williams Spring Controller for Customer class.
 * CustomerForm class is used by Model to map the given form (webform.html)
 *
 *
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import javax.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

    //hardcoded database connection parameters
    // JDBC driver name and database URL
    private final static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private final static String DB_URL = "jdbc:mysql://localhost:3306/mysql";
    //  Database credentials
    private final static String USER = "root";
    private final static String PASS = "password";

    private final AtomicLong counter = new AtomicLong();

    //test method to for method constructor testing
    /*
     @RequestMapping("/customer/test")
     public Customer customerTest(
     @RequestParam(value = "username", defaultValue = "defaultUsername") String username,
     @RequestParam(value = "password", defaultValue = "defaultPassword") String password,
     @RequestParam(value = "emailAddr", defaultValue = "defaultEmail") String emailAddress
     ) {
     return new Customer(-1, username, password, emailAddress);
     }*/
    /*
     //CustomerForm is a model entity class with variable names that match the form's input tag attributes
     //this method stores two fields. the username is not supported by the form, but was indicated by assignment text.
     //see webform.html
     //https://stackoverflow.com/questions/15497738/handle-form-post-with-a-array-of-items-in-spring-mvc
     @RequestMapping(value = "/helloworld-webapp/customer/customers", method = RequestMethod.POST)
     public Customer customerStore(@ModelAttribute CustomerForm customerForm) {
     //populate fields
     int id = (int) counter.incrementAndGet();//long cast to int
     String username = "USERNAME NOT SUPPORTED BY FORM";
     String password = customerForm.getInputPassword();
     String emailAddress = customerForm.getInputEmail();
     Customer customer = new Customer(id, username, password, emailAddress);

     //check if email field is blank, or if there was a problem reading request
     if (emailAddress == null) {
     emailAddress = "REQUEST NOT READ";
     return customer;
     }

     MyDatabaseConn mdb = new MyDatabaseConn(JDBC_DRIVER, DB_URL, USER, PASS);
     String statement = customer.getInsertStatement();
     //System.out.println(statement);
     mdb.insert(statement);

     //System.out.println("customerStore returning: "+customer.toString());
     return customer;
     }*/
    //JSON attempt, will require a new webform design for input, or use a POST request tester
    //NOTE: had problems constructing a proper POST in POSTMAN extension for Chrome. try Advanced REST tester
    @RequestMapping(value = "/helloworld-webapp/customer/customers", method = RequestMethod.POST)
    public @ResponseBody Customer jsonStore(@RequestBody Customer customer) {
        if (customer.getId() < 0) {
            int id = (int) counter.incrementAndGet();//long cast to int
            customer.setId(id);
        }

        MyDatabaseConn mdb = new MyDatabaseConn(JDBC_DRIVER, DB_URL, USER, PASS);
        String statement = customer.getInsertStatement();
        //System.out.println(statement);
        mdb.insert(statement);

        System.out.println("Returning: " + customer.toString());
        return customer;
    }

    @RequestMapping(value = "/helloworld-webapp/customer/customers", method = RequestMethod.PUT)
    public Customer jsonUpdate(@RequestBody Customer customer) {
        MyDatabaseConn mdb = new MyDatabaseConn(JDBC_DRIVER, DB_URL, USER, PASS);
        String statement = customer.getUpdateStatement();
        //System.out.println(statement);
        mdb.update(statement);

        //System.out.println("Returning: " + customer.toString());
        return customer;
    }

    @RequestMapping(value = "/helloworld-webapp/customer/customers", method = RequestMethod.DELETE)
    public Customer jsonRemove(@RequestBody Customer customer) {
        MyDatabaseConn mdb = new MyDatabaseConn(JDBC_DRIVER, DB_URL, USER, PASS);
        String statement = customer.getDeleteStatement();
        //System.out.println(statement);
        mdb.delete(statement);
        
        //System.out.println("Returning: " + customer.toString());
        return customer;
    }

    //This method returns a report showing all the records in the database.
    //TODO:Chrome does not show linebreaks properly with this, format for prettyer display
    //http://localhost:8080/helloworld-webapp/customer/report
    @RequestMapping("/helloworld-webapp/customer/report")
    public String customerReport() {
        MyDatabaseConn mdb = new MyDatabaseConn(JDBC_DRIVER, DB_URL, USER, PASS);
        List<Map<String, Object>> table = mdb.runQuery("SELECT * FROM customer");
        List<Customer> list = CustomerController.getCustomerList(table);
        String output = "";
        if (list.isEmpty()) {
            return "DATABASE EMPTY =(";
        }

        for (Customer c : list) {
            output += "\n" + c.toString();
        }
        return output;
    }

    //utility class that transforms the arraylist of hashmaps into a list of customers.
    //list of hashmaps is returned by the MyDatabaseConn class, which I designed to be portable for other entities
    public static List<Customer> getCustomerList(List<Map<String, Object>> table) {
        List<Customer> customers = new ArrayList();
        Customer customer = null;
        for (Map m : table) {
            int id = (int) m.get("customerID");
            String username = (String) m.get("username");
            String password = (String) m.get("password");
            String emailAddress = (String) m.get("emailAddress");
            customer = new Customer(id, username, password, emailAddress);
            //System.out.println("Adding "+customer);
            customers.add(customer);
        }
        return customers;
    }

}
