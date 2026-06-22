package com.InsuranceSystem.repository;

import com.InsuranceSystem.model.Quote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuoteRepository extends JpaRepository<Quote, Integer> {
}
