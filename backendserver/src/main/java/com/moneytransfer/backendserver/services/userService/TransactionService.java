package com.moneytransfer.backendserver.services.userService;

import com.moneytransfer.backendserver.Util;
import com.moneytransfer.backendserver.exceptions.FriendException;
import com.moneytransfer.backendserver.exceptions.TransactionException;
import com.moneytransfer.backendserver.objects.Transaction;
import com.moneytransfer.backendserver.repositories.FriendRepo;
import com.moneytransfer.backendserver.repositories.TransactionRepo;
import com.moneytransfer.backendserver.repositories.UserRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class TransactionService {

    private TransactionRepo transactionRepo;
    private FriendRepo friendRepo;
    private UserRepo userRepo;

    public TransactionService(FriendRepo friendRepo, TransactionRepo transactionRepo, UserRepo userRepo) {
        this.transactionRepo = transactionRepo;
        this.friendRepo = friendRepo;
        this.userRepo = userRepo;
    }

    public List<Transaction> getTransactionList(String email) throws TransactionException {
        if (userRepo.findByUserEmail(email).isEmpty()) {
            throw new TransactionException(0); //user not exists
        }
        return transactionRepo.getTransactionList(email);
    }

    public Transaction getTransaction(String transactionId) throws TransactionException {
        Optional<Transaction> transactionOpt = transactionRepo.getTransaction(transactionId);
        if (transactionOpt.isEmpty()) {
            throw new TransactionException(4); //Transaction does not exist
        }
        return transactionOpt.get();
    }

    public List<Transaction> getPendingTransactionList(String email) throws TransactionException {
        if (userRepo.findByUserEmail(email).isEmpty()) {
            throw new TransactionException(0); //user not exists
        }
        return transactionRepo.getPendingList(email);
    }

    public double getUserBalance(String email) throws TransactionException {
        if (userRepo.findByUserEmail(email).isEmpty()) {
            throw new TransactionException(0); //user not exists
        }
        List<Transaction> transactionList = transactionRepo.getTransactionList(email);

        double balance = 0;

        for (Transaction transaction : transactionList) {
            if (transaction.getPayeeEmail().equalsIgnoreCase(email)) {
                balance += transaction.getAmount();
            } else if (transaction.getPayerEmail().equalsIgnoreCase(email)) {
                balance -= transaction.getAmount();
            }
        }
        return balance;
    }

    public String makeInternalTransaction(String payerEmail, String payeeEmail, double amount) throws TransactionException {
        if (userRepo.findByUserEmail(payerEmail).isEmpty()) {
            throw new TransactionException(1); //payer not exists
        }
        if (userRepo.findByUserEmail(payeeEmail).isEmpty()) {
            throw new TransactionException(2); //payee not exists
        }
        if (friendRepo.areTheyFriend(payerEmail, payeeEmail).isEmpty()) {
            throw new TransactionException(3); //they are not friend.
        }

        Transaction transaction = new Transaction();
        String transactionId = Util.makeRandomString(32);
        while(transactionRepo.getTransaction(transactionId).isPresent()) {
            transactionId = Util.makeRandomString(32);
        }
        transaction.setTransactionId(transactionId);
        transaction.setPayerFSP("MoneyTransfer");
        transaction.setPayeeFSP("MoneyTransfer");
        transaction.setPayerEmail(payerEmail);
        transaction.setPayeeEmail(payeeEmail);
        transaction.setAmount(amount);

        transactionRepo.save(transaction);

        return transactionId;
    }

    public void userAccept(String transactionId, String userEmail, boolean confirm) throws TransactionException {
        Optional<Transaction> transactionOptional = transactionRepo.getTransaction(transactionId);
        if (transactionOptional.isEmpty()) {
            throw new TransactionException(4);
        }

        Transaction transaction = transactionOptional.get();
        String tId = transaction.getTransactionId();

        if (transaction.getPayerEmail().equalsIgnoreCase(userEmail)) {
            if (confirm) {
                transactionRepo.updatePayerAccepted(tId, true);
            }
            if (transaction.isPayeeConfirmed()) {
                transactionRepo.updateTimestamp(tId, System.currentTimeMillis());
            }
        } else if (transaction.getPayeeEmail().equalsIgnoreCase(userEmail)) {
            if (confirm) {
                transactionRepo.updatePayeeAccepted(tId, true);
            }
            if (transaction.isPayerConfirmed()) {
                transactionRepo.updateTimestamp(tId, System.currentTimeMillis());
            }
        } else {
            throw new TransactionException(5);
        }
    }
}
