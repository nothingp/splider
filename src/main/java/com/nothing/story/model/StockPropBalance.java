package com.nothing.story.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Data
public class StockPropBalance implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected String id;

    protected String code;

    protected String type;

    protected String name;

    protected String year;

    protected String month;

    protected String day;

    protected String prop;

    protected Double value;

    protected Integer time;
}
