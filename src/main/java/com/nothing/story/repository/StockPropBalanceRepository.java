package com.nothing.story.repository;

import com.nothing.story.model.StockProp;
import com.nothing.story.model.StockPropBalance;
import com.nothing.story.model.StockPropLabel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockPropBalanceRepository extends JpaRepository<StockPropBalance, String>{

    List<StockPropBalance> findByCodeAndPropInAndTimeBetweenOrderByMonth(String code, List props,Integer start,Integer end);


    List<StockPropBalance> findByCodeAndMonthAndProp(String code, String month, String prop);

}