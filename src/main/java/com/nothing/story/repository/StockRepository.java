package com.nothing.story.repository;

import com.nothing.story.model.Stock;
import com.nothing.story.model.StockPropLabel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface StockRepository extends JpaRepository<Stock, String>{

    List<Stock> findByCodeLike(String code);

}