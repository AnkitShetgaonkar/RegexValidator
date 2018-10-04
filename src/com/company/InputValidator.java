package com.company;

import com.company.db.DbHelper;

import java.util.*;

/**
 * @author Ankit
 */
public class InputValidator {

    private Scanner scanner = new Scanner(System.in);
    private DbHelper mDbHelper;

    private void showUserMenu() {
        System.out.println("\n\nMake your choice");
        System.out.println("Add a new contact: '1'");
        System.out.println("Delete an existing contact by Name: '2'");
        System.out.println("Delete an existing contact by Number: '3'");
        System.out.println("List all the contacts: '4'");
        System.out.println("Enter '-1' anywhere to return to main menu");
        System.out.println("Exit the program: 'exit'");
        String input = scanner.nextLine();
        switch (input) {
            case "1":
                addNewContact();
                break;
            case "2":
                deleteContactByName();
                break;
            case "3":
                deleteContactByNumber();
                break;
            case "4":
                listAllContacts();
                break;
            case "exit":
                System.exit(0);
                break;
            default:
                System.out.println("Invalid option selected");
                showUserMenu();
                break;
        }
    }

    private void listAllContacts() {
        System.out.println("All the contacts in the phonebook: ");
        List<Contact> contactList = mDbHelper.getAllContacts();
        for (Contact contact :
                contactList) {
            System.out.println(contact.getName() + " _______ " + contact.getPhoneNumber());
        }
        //once done show the main menu
        showUserMenu();
    }

    public void deleteContactByName(){
        System.out.println("Enter the name of user to delete from phonebook");
        String name = scanner.nextLine();
        if(mDbHelper.deleteContactByName(name)) {
            System.out.println("Deleted the user " + name + " from phonebook");
        }else{
            System.out.println("No such user by name " + name + " in phonebook");
        }
        showUserMenu();
    }

    public void deleteContactByNumber(){
        System.out.println("Enter the number of user to delete from phonebook");
        String number = scanner.nextLine();
        if(mDbHelper.deleteContactByNumber(number)) {
            System.out.println("Deleted the user " + number + " from phonebook");
        }else{
            System.out.println("No such user by number " + number + " from phonebook");
        }
        showUserMenu();
    }

    /**
     * adds new contact to the phonebook
     */
    private void addNewContact() {
        String name = getName();
        if (name == null) {
            return;
        }
        String phoneNumber = getPhoneNumber();
        if (phoneNumber == null) {
            return;
        }
        if(mDbHelper.createContact(new Contact(name, phoneNumber))){
            System.out.println("Added contact to phonebook");
        }else{
            System.out.println("failed to add contact");
        }
        showUserMenu();
    }


    /**
     * get the phone number from the user
     *
     * @return
     */
    private String getPhoneNumber() {
        System.out.println("Enter your valid phone number");
        String phoneNumber = scanner.nextLine();
        if (("-1").equals(phoneNumber)) {
            showUserMenu();
            return null;
        }
        if (!isPhoneNumberValid(phoneNumber)) {
            System.out.println("Invalid phoneNumber");
            phoneNumber = getPhoneNumber();
        }
        return phoneNumber;
    }

    /**
     * get the name from the user
     *
     * @return
     */
    private String getName() {
        System.out.println("Enter the name in a valid format");
        String name = scanner.nextLine();
        //back to main menu
        if (("-1").equals(name)) {
            showUserMenu();
            return null;
        }
        if (!isNameValid(name)) {
            System.out.println("Invalid name");
            name = getName();
        }
        return name;
    }


    private boolean isPhoneNumberValid(String phoneNumber) {
        String numberPattern = "(^|,)((\\d{5}\\.\\d{5}$)|" +
                "(?=.*[\\s-])" +
                "((\\d{3}\\s\\d\\s)?(\\d{3}\\s)?(\\+?\\d{1,2})?(\\d{1,2}\\s\\d{1,2})?\\s?" +
                "(\\(\\d{2}\\)\\s)?(\\(\\d{3}\\))?(\\d{1,3})?[\\s-]?(\\d{4})))+$";
        return phoneNumber.matches(numberPattern);
    }

    private boolean isNameValid(String name) {
        String namePattern = "([^\\W\\d]+(['-]?[^\\W\\d]+)?(['-]?[^\\W\\d]+)?)(,)?" +
                "(\\s[^\\W\\d]+(['-][^\\W\\d]+)?(['-]?[^\\W\\d]+)?)?" +
                "(\\s[^\\W\\d]+(['-]?[^\\W\\d]+)?(['-]?[^\\W\\d]+)?)?\\.?";
        return name.matches(namePattern);
    }

    private void initializeDb() {
        mDbHelper = DbHelper.getInstance();
    }

    public static void main(String[] args) {
        InputValidator inputValidator = new InputValidator();
        inputValidator.initializeDb();
        inputValidator.showUserMenu();

    }

}
