package com.nothing.story.repository;

import com.nothing.story.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface StockRepository extends JpaRepository<Stock, String>{

}