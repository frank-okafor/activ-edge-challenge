package com.activedge.challenge.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.activedge.challenge.model.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    Optional<Stock> findByName(String name);

    @Query(value = "select count(*) from stock s where s.name = :name and s.id != :id", nativeQuery = true)
    int checkIfNameExists(@Param("name") String name, @Param("id") Long id);

}
