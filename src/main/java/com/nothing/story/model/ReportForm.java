package com.nothing.story.model;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by dick on 2017/8/19.
 */
@Data
public class ReportForm implements Serializable {

    private String code;

    private String[] props;
}
