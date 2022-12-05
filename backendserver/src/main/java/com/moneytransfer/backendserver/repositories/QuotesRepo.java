package com.moneytransfer.backendserver.repositories;

import com.moneytransfer.backendserver.objects.Quotes;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface QuotesRepo {

    Quotes save(Quotes quotes);

    Optional<Quotes> findByQuotesId(UUID id);

    void deleteByQuotesId(UUID id);

    List<Quotes> findAll();
    List<Quotes> findQuotesByPayerName(String name);
    List<Quotes> findQuotesByPayeeName(String name);
    void updateQuotes(UUID id);
}