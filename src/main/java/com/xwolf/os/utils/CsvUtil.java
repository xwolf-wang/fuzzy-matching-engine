package com.xwolf.os.utils;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author ming
 * @Description:
 * @create 2019-10-01 7:58 PM
 **/
public class CsvUtil {

    public static List<Map<String, String>> readObjectsFromCsv(String filePath) throws IOException {
        File file = new File(filePath);

        CsvSchema bootstrap = CsvSchema.emptySchema().withHeader();
        CsvMapper csvMapper = new CsvMapper();
        MappingIterator<Map<String, String>> mappingIterator = csvMapper.readerFor(Map.class).with(bootstrap).readValues(file);

        return mappingIterator.readAll();
    }

}
