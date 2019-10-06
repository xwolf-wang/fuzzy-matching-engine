package com.xwolf.os;

import com.xwolf.os.utils.CsvUtil;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

/**
 * @author ming
 * @Description:
 * @create 2019-10-01 8:05 PM
 **/
public class CsvUtilTest {

    @Test
    public void test() throws IOException {
        String filepath = "./src/test/resources/1-N/channel1.csv";

        List list = CsvUtil.readObjectsFromCsv(filepath);
        System.out.println(list);
    }

    @Test
    public void testUpload() {
        System.out.println(System.getProperty("user.dir"));
        System.out.println(CsvUtil.getRootPath());
    }
}
