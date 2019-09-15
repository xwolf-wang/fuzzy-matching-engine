package com.xwolf.os.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ming
 * @Description:
 * @create 2019-09-13 11:22 PM
 **/
@Data
public class Trade {
    private String uuid;
    private String sourceSystem;
    private String tradeType;
    private List<Field> fields = new ArrayList<Field>();

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }
}
