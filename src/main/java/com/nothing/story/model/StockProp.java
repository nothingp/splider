package com.nothing.story.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class StockProp {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    private String code;

    private String type;

    private String name;

    private String year;

    private String month;

    private String prop;

    private Double value;
}
