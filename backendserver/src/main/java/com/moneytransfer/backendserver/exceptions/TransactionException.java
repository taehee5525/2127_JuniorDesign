package com.moneytransfer.backendserver.exceptions;

public class TransactionException extends Exception {

    private final int ERROR_CODE;
    private String errorDescription;

    public TransactionException(int error_code) {
        ERROR_CODE = error_code;
        binding(error_code);
    }

    public TransactionException(int error_code, String errorDescription) {
        ERROR_CODE = error_code;
        this.errorDescription = errorDescription;
    }

    private void binding(int errorCode) {
        if (errorCode == 0) {
            this.errorDescription = "User Email Does not Exists";
        } else if (errorCode == 1) {
            this.errorDescription = "Payer not exists";
        } else if (errorCode == 2) {
            this.errorDescription = "Payee not exists";
        } else if (errorCode == 3) {
            this.errorDescription = "They are not friend";
        } else if (errorCode == 4) {
            this.errorDescription = "Transaction does not exist";
        } else if (errorCode == 5) {
            this.errorDescription = "User is not Payer or Payee";
        } else if (errorCode == 6) {
            this.errorDescription = "Transaction denied.";
        }
    }

    public int getERROR_CODE() {
        return ERROR_CODE;
    }

    public String getErrorDescription() {
        return errorDescription;
    }
}