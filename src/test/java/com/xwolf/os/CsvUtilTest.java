package com.xwolf.os;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.xwolf.os.utils.CsvUtil;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author ming
 * @Description:
 * @create 2019-10-01 8:05 PM
 **/
public class CsvUtilTest {

    @Test
    public void test() throws IOException {
        String filepath = "./target/test-classes/fusion_fill.csv";

        List list = CsvUtil.readObjectsFromCsv(filepath);
        System.out.println(list);
    }
}
