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
 * @author Stephen R. Williams Customer entity class
 */
public class Customer {

    private int id;
    private String username;
    private String password;
    private String emailAddress;

    public Customer() {
    }

    public Customer(int id, String username, String password, String emailAddress) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.emailAddress = emailAddress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getInsertStatement() {
        return "INSERT INTO Customer VALUES " + "(" + id + ",'" + username + "','" + password + "','" + emailAddress + "');";
    }

    //update a record with the same id as the instance
    public String getUpdateStatement() {
        return "UPDATE Customer SET " + "customerid=" + id + ",username='" + username + "',password='" + password + "',emailaddress='" + emailAddress
                + "' WHERE customerid=" + id + ";";
    }

    //update a different record with this instance, changing the id of the record to match the instance
    public String getUpdateStatement(int idRecordToChange) {
        return "UPDATE Customer SET " + "customerid=" + this.id + ",username='" + this.username + "',password='" + this.password + "',emailaddress='" + this.emailAddress
                + "' WHERE customerid=" + idRecordToChange + ";";
    }

    //delete this instance from the DB
    public String getDeleteStatement() {
        return "DELETE FROM Customer WHERE customerid=" + this.id;
    }

    //delete any record from the DB (static to avoid mistake)
    public static String getDeleteStatement(int id) {
        return "DELETE FROM Customer WHERE customerid=" + id;
    }

    @Override
    public String toString() {
        return "{" + "id:" + id + ", username:" + username + ", password:" + password + ", emailAddress:" + emailAddress + '}';
    }

}
