package com.xwolf.os.db;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author ming
 * @Description:
 * @create 2019-09-14 12:39 PM
 **/
@Data
@Entity
@Table(name = "trade_tbl")
public class TradeEntity {

    @Id
    @GeneratedValue
    private Integer id;

    private String uuid;

    private String sourceSystem;

    private String tradeType;

    private String tradeJsonStr;

    private String matchIndex;

}
