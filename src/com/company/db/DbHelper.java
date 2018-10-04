package com.company.db;

import com.company.Contact;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ankit
 */
public class DbHelper {

    private static DbHelper mInstance;
    private Connection mConnection;

    private static final String PHONE_BOOK = "PHONE_BOOK";


    /**
     * singleton helper
     *
     * @return
     */
    public static synchronized DbHelper getInstance() {
        if (mInstance == null) {
            mInstance = new DbHelper();
        }

        return mInstance;
    }

    private DbHelper() {
        init();
        createContactTable();
    }

    private void init() {
        try {
            mConnection = DriverManager.
                    getConnection("jdbc:h2:" + System.getProperty("user.dir") + "\\db\\phonebook", "sqdb", "");
        } catch (SQLException e) {
            System.out.println("Could not create db");
            e.printStackTrace();
        }
    }

    private void createContactTable() {
        if (mConnection == null) {
            System.out.println("Error: Database not yet initialized");
            return;
        }
        try {
            mConnection.createStatement().execute("CREATE TABLE IF NOT EXISTS " + PHONE_BOOK +
                    "(user_name varchar(255),"
                    + "phone_number varchar(255));");
            mConnection.commit();
        } catch (SQLException e) {
            System.out.println("Could not create student table");
            e.printStackTrace();
        }
    }

    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<>();
        if (mConnection == null) {
            System.out.println("Error: Database not yet initialized");
            return contactList;
        }
        try {
            ResultSet resultSet = mConnection.createStatement().executeQuery("SELECT * FROM " + PHONE_BOOK + " ;");
            if (resultSet == null)
                return contactList;

            while (resultSet.next()) {
                contactList.add(new Contact(resultSet.getString("user_name"),
                        resultSet.getString("phone_number")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Could not create student table");
            e.printStackTrace();
        }
        return contactList;
    }

    /**
     * adds a new contact to sql db
     *
     * @param contact
     */
    public boolean createContact(Contact contact) {
        String query = "INSERT INTO " + PHONE_BOOK + "(user_name,phone_number) values(?,?)";
        return runQuery(query,contact.getName(),contact.getPhoneNumber());
    }

    public boolean deleteContactByNumber(String number) {
        if (mConnection == null) {
            System.out.println("Error: Database not yet initialized");
            return false;
        }
        String query = "Delete From PHONE_BOOK WHERE phone_number =?";
        return runQuery(query,number,null);
    }

    public boolean deleteContactByName(String name) {
        if (mConnection == null) {
            System.out.println("Error: Database not yet initialized");
            return false;
        }
        String query = "Delete From PHONE_BOOK WHERE user_name =?";
        return runQuery(query,name,null);
    }

    /**
     * use the prepare statement for security
     * @param query
     * @param param1
     * @param param2
     * @return
     */
    private boolean runQuery(String query, String param1, String param2) {
        try {
            PreparedStatement sqlState = mConnection.prepareStatement(query);
            sqlState.setString(1, param1);
            if(param2 !=null)
                sqlState.setString(2,param2);
            if (sqlState.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
