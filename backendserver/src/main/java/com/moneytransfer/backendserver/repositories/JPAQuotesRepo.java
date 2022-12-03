package com.moneytransfer.backendserver.repositories;

import com.moneytransfer.backendserver.objects.Quotes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JPAQuotesRepo extends JpaRepository<Quotes, Long>, QuotesRepo{

    @Override
    Optional<Quotes> findByQuotesId(UUID id);

    @Override
    @Modifying
    @Query("delete from Quotes q where q.quotesId = :id")
    void deleteByQuotesId(UUID id);
    @Override
    List<Quotes> findAll();
}