package com.moneytransfer.backendserver.repositories;

import com.moneytransfer.backendserver.objects.Friend;
import com.moneytransfer.backendserver.objects.Token;
import com.moneytransfer.backendserver.objects.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface JPATransactionRepo extends JpaRepository<Transaction, Long>, TransactionRepo{
    @Override
    Transaction save(Transaction transaction);

    @Override
    @Query(value = "SELECT * FROM Transaction t " +
            "WHERE (t.payer_Confirmed = true and t.payee_Confirmed = true) " +
            "and (t.payer_email = :userEmail or t.payee_email = :userEmail)", nativeQuery = true)
    List<Transaction> getTransactionList(String userEmail);

    @Override
    @Query(value = "SELECT * FROM Transaction t " +
            "WHERE t.transaction_id = :transactionId", nativeQuery = true)
    Optional<Transaction> getTransaction(String transactionId);

    @Override
    @Query(value = "SELECT * FROM Transaction t " +
            "WHERE ((t.payer_Confirmed = false and t.payee_Confirmed = true) " +
            "or (t.payer_Confirmed = true and t.payee_Confirmed = false)) " +
            "and (t.payer_email = :userEmail or t.payee_email = :userEmail)" +
            "and (t.timestamp is null or t.timestamp = 0)", nativeQuery = true)
    List<Transaction> getPendingList(String userEmail);

    @Override
    @Modifying
    @Query("update Transaction set payerConfirmed = :confirmed where transactionId = :transactionId")
    void updatePayerAccepted(String transactionId, boolean confirmed);

    @Override
    @Modifying
    @Query("update Transaction set payeeConfirmed = :confirmed where transactionId = :transactionId")
    void updatePayeeAccepted(String transactionId, boolean confirmed);

    @Override
    @Modifying
    @Query("update Transaction set timestamp = :timestamp where transactionId = :transactionId")
    void updateTimestamp(String transactionId, long timestamp);

    @Override
    List<Transaction> findAll();
}
