package com.nothing.story.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Stock {

    @Id
    private String code;

    private String name;
}
