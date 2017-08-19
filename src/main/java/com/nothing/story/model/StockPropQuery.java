package com.nothing.story.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class StockPropQuery implements Serializable {

    protected String code;

    protected String name;

    protected String year;

    protected String month;

    protected Map<String,Double> items = new HashMap<>();
}
