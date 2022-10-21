package com.moneytransfer.backendserver.repositories;

import com.moneytransfer.backendserver.objects.Friend;
import com.moneytransfer.backendserver.objects.Transaction;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TransactionRepo {

    Transaction save(Transaction transaction);

    List<Transaction> getTransactionList(String userEmail);

    Optional<Transaction> getTransaction(String transactionId);

    List<Transaction> getPendingList(String userEmail);

    void updatePayerAccepted(String transactionId, boolean confirmed);

    void updatePayeeAccepted(String transactionId, boolean confirmed);

    void updateTimestamp(String transactionId, long timestamp);

    List<Transaction> findAll();

}