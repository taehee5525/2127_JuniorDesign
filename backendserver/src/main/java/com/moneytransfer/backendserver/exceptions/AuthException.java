package com.moneytransfer.backendserver.exceptions;

public class AuthException extends Exception {

    private final int ERROR_CODE;
    private String errorDescription;

    public AuthException(int error_code) {
        ERROR_CODE = error_code;
        binding(error_code);
    }

    public AuthException(int error_code, String errorDescription) {
        ERROR_CODE = error_code;
        this.errorDescription = errorDescription;
    }

    private void binding(int errorCode) {
        if (errorCode == 0) {
            this.errorDescription = "Token does not exist.";
        } else if (errorCode == 1) {
            this.errorDescription = "Expired Token was used.";
        }
    }

    public int getERROR_CODE() {
        return ERROR_CODE;
    }

    public String getErrorDescription() {
        return errorDescription;
    }
}
