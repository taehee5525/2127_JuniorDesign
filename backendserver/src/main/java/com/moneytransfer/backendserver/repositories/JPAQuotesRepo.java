package com.moneytransfer.backendserver.repositories;

import com.moneytransfer.backendserver.objects.Quotes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JPAQuotesRepo extends JpaRepository<Quotes, Long>, QuotesRepo{

    @Override
    Quotes save(Quotes quotes);
    @Override
    Optional<Quotes> findByQuotesId(UUID id);

    @Override
    @Modifying
    @Query("delete from Quotes q where q.quotesId = :id")
    void deleteByQuotesId(UUID id);

    @Override
    @Query(value = "SELECT * FROM QUOTES q" +
            "WHERE q.payerName = :payerName", nativeQuery = true)
    List<Quotes> findQuotesByPayerName(String payerName);

    @Override
    @Query(value = "SELECT * FROM QUOTES q" +
            "WHERE q.payeeName = :payeeName", nativeQuery = true)
    List<Quotes> findQuotesByPayeeName(String payeeName);

    @Override
    @Modifying
    @Query("UPDATE Quotes set completed = true WHERE quotesId = :id")
    void updateQuotes(@Param("quotesId") UUID id);
    @Override
    List<Quotes> findAll();
}