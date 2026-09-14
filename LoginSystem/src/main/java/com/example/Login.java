package com.example;

public class Login {

    // User attributes
    private String username;
    private String password;
    private String cellPhoneNumber;
    private String firstName;
    private String lastName;

    // Constructor with 5 parameters 
    public Login(String username, String password, String cellPhoneNumber,
                 String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.cellPhoneNumber = cellPhoneNumber;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // Checks that username contains "_" and is max 5 characters long
    public boolean checkUserName(String username) {
        return username.contains("_") && username.length() <= 5;
    }

    // Checks password complexity
    public boolean checkPasswordComplexity(String password) {
        return password.length() >= 8
                && password.matches(".*[A-Z].*")
                && password.matches(".*[0-9].*")
                && password.matches(".*[^a-zA-Z0-9].*");
    }

    // Checks cell phone number (international code +27, 9 digits)
    public boolean checkCellPhoneNumber(String cellPhoneNumber) {
        return cellPhoneNumber.matches("^\\+27[0-9]{9}$");
    }

    // Registers the user and returns the appropriate message
    public String registerUser() {
        if (!checkUserName(this.username)) {
            return "Username is not correctly formatted; please ensure that your "
                 + "username contains an underscore and is no more than five "
                 + "characters in length.";
        }
        if (!checkPasswordComplexity(this.password)) {
            return "Password is not correctly formatted; please ensure that the "
                 + "password contains at least eight characters, a capital letter, "
                 + "a number, and a special character.";
        }
        if (!checkCellPhoneNumber(this.cellPhoneNumber)) {
            return "Cell number is incorrectly formatted or does not contain "
                 + "international code; please correct the number and try again.";
        }
        return "Username successfully captured.\n"
             + "Password successfully captured.\n"
             + "Cell number successfully added.";
    }

    // Verifies login credentials
    public boolean loginUser(String enteredUsername, String enteredPassword) {
        return this.username.equals(enteredUsername)
                && this.password.equals(enteredPassword);
    }

    // Returns the login status message
    public String returnLoginStatus(boolean isLoggedIn) {
        if (isLoggedIn) {
            return "Welcome " + this.firstName + ", " + this.lastName
                 + " it is great to see you again.";
        }
        return "Username or password incorrect, please try again.";
    }
}