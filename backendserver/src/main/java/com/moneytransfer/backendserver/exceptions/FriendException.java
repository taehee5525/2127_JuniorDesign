package com.moneytransfer.backendserver.exceptions;

public class FriendException extends Exception {

    private final int ERROR_CODE;
    private String errorDescription;

    public FriendException(int error_code) {
        ERROR_CODE = error_code;
        binding(error_code);
    }

    public FriendException(int error_code, String errorDescription) {
        ERROR_CODE = error_code;
        this.errorDescription = errorDescription;
    }

    private void binding(int errorCode) {
        if (errorCode == 0) {
            this.errorDescription = "Relationship already exists";
        } else if (errorCode == 1) {
            this.errorDescription = "Friend account does not exists";
        }  else if (errorCode == 2) {
            this.errorDescription = "Relationship does not exists";
        }
    }

    public int getERROR_CODE() {
        return ERROR_CODE;
    }

    public String getErrorDescription() {
        return errorDescription;
    }
}
