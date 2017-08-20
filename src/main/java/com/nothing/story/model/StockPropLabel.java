package com.nothing.story.model;

import lombok.Data;
import org.springframework.data.annotation.*;

import javax.persistence.*;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Entity
public class StockPropLabel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected String id;

    protected String key;

    protected String label;

    protected String value;

    protected String parentId;

//    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy="parentId")
    @Transient
    protected List<StockPropLabel> children = new ArrayList<>();
}
