package com.moneytransfer.backendserver.exceptions;

public class LoginException extends Exception{

    private final int ERROR_CODE;
    private String errorDescription;

    public LoginException(int error_code) {
        ERROR_CODE = error_code;
        binding(error_code);
    }

    public LoginException(int error_code, String errorDescription) {
        ERROR_CODE = error_code;
        this.errorDescription = errorDescription;
    }

    private void binding(int errorCode) {
        if (errorCode == 0) {
            this.errorDescription = "User Email Already Exists";
        } else if (errorCode == 1) {
            this.errorDescription = "User password is wrong";
        }
    }

    public int getERROR_CODE() {
        return ERROR_CODE;
    }

    public String getErrorDescription() {
        return errorDescription;
    }
}
