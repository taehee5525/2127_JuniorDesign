package com.example.moneytransfer;

import java.util.*;

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
        if (!checkEmailLocalPartValidation(emailAddress) || !checkDominPartValidation(domain)) {
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
     * To-Do
     * @param domainPart
     * @return
     */
    private static boolean checkDominPartValidation(String domainPart) {
        return true;
    }


}
