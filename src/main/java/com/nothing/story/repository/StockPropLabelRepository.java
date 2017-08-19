package com.nothing.story.repository;

import com.nothing.story.model.StockPropBalance;
import com.nothing.story.model.StockPropLabel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockPropLabelRepository extends JpaRepository<StockPropLabel, Integer>{

    List<StockPropLabel> findByParentId(String parentId);




}