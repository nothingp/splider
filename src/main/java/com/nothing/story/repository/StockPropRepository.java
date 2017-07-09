package com.nothing.story.repository;

import com.nothing.story.model.StockProp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface StockPropRepository extends JpaRepository<StockProp, String>{

}