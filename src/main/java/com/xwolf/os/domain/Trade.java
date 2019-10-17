package com.xwolf.os.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ming
 * @Description:
 * @create 2019-09-13 11:22 PM
 **/
@Data
public class Trade implements Cloneable{
    private String uuid;
    private String tradeType;
    private Map<String,String> fields = new HashMap();

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

    @Override
    public Object clone() throws CloneNotSupportedException {
        Object obj = super.clone();
        Map<String,String> f = new HashMap<>();
        f.putAll(((Trade)obj).fields);
        ((Trade)obj).setFields(f);
        return obj;
    }
}
