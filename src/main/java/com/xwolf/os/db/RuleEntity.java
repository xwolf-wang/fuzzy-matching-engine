package com.xwolf.os.db;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author ming
 * @Description:
 * @create 2019-09-14 12:40 PM
 **/

@Entity
@Data
@Table(name = "rule_tbl")
public class RuleEntity {
    @Id
    @GeneratedValue
    private Integer id;

    private String sourceSystem;

    private String leftTradeType;

    private String leftField;

    private String rightTradeType;

    private String rightField;

}
