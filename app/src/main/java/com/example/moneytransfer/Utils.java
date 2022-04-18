package com.example.moneytransfer;

import java.util.*;
import java.util.regex.*;

public class Utils {
    /**
     * check validation of emaill address.
     * see more details on : https://en.wikipedia.org/wiki/Email_address#Validation_and_verification
     * @param emailAddress
     * @return
     */
    public static boolean checkEamailAddressValidation(String emailAddress) {

        // "@" cannot be occur more than 1 time.
        String[] splittedAddr = emailAddress.split("@");
        if (splittedAddr.length != 2) {
            return false;
        }
        //localPart@Domain
        String localPart = splittedAddr[0];
        String domain = splittedAddr[1];
        //local part check
        if (!checkEmailLocalPartValidation(emailAddress) || !localcheckDominPartValidation(domain)) {
            return false;
        }
        return true;
    }

    private static boolean checkEmailLocalPartValidation(String localPart) {
        List<StringBuilder> sbList = new ArrayList<>();
        boolean doubleQuoteStarted = false;

        char prev = '.';
        for (int i = 0; i < localPart.length(); i++) {
            char ch = localPart.charAt(i);

            //check dot is provided consecutively or provided at the start of the local part.
            if (ch == '.' && prev != '.') {
                return false;
            }

            if (('a' <= ch && ch <= 'z') || ('A' <= ch && ch <= 'Z') || ('0' <= ch && ch <= '9')
                    || (ch == '!') || (ch == '#') || (ch == '$') || (ch == '%') || (ch == '&') || (ch == '\'') || (ch == '*')
                    || (ch == '+') || (ch == '-') || (ch == '/') || (ch == '=') || (ch == '?') || (ch == '^') || (ch == '_')
                    || (ch == '`') || (ch == '{') || (ch == '|') || (ch == '}') || (ch == '~') || (ch == '~') || (ch == '~')
                    || (ch == '`') || (ch == '`') || (ch == '`') || (ch == '`') || (ch == '`') || (ch == '`')) {

            } else {
                return false;
            }

            if (ch == '"' && prev == '\\') {
                return false;
            }
            prev = ch;
        }

        // check dot is provided at the end of the local part.
        if (localPart.charAt(localPart.length() - 1) == '.') {
            return false;
        }
        return true;
    }

    /**
     * Checking if the domain is valid
     * @param domainPart the string contains the domain information
     * @return boolean value whether the domain is valid or not.
     */
    private static boolean localcheckDominPartValidation(String domainPart) {
        // This domain checker is only used for format validation
        // Does not check whether the domain is actually on-line
        // The function to check whether the valid domain
        // is a working domain
        // will be implemented next semester

        // Regex to check valid domain name
        String regex = "^((?!-)[A-Za-z0-9-]"
                + "{1,63}(?<!-)\\.)"
                + "+[A-Za-z]{2,6}";

        // Compile the regex
        Pattern p = Pattern.compile(regex);

        // Checking whether the domainPart is null is not necessary
        // Because it is coming from the checkEamailAddressValidation method

        // If the domain has at least two separate part
        // Use the regex to check
        // It checks whether the domain part is valid or not
        // by comparing the given string to the regex
        Matcher m = p.matcher(domainPart);

        return m.matches();
    }

    /**
     * This method checks if the user-entered password is valid during sign-up phase.
     *
     * @param password user-entered password
     * @return true if the password has: 1. at least 8 characters
     *                                   2. at least 1 uppercase letter
     *                                   3. at least 1 number
     *                                   4. at least 1 special character
     */
    private static boolean checkPasswordValidity(String password) {
        String uppercaseRegex = "(.*[A-Z].*)";
        String numberRegex = "(.*[0-9].*)";
        String specialCharRegex = "^(?=.*[_.()$&@]).*$";
        
        return (password.length() >= 8
                    && password.matches(uppercaseRegex)
                    && password.matches(numberRegex)
                    && password.matches(specialCharRegex));
    }
}
