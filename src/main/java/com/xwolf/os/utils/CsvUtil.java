package com.xwolf.os.utils;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author ming
 * @Description:
 * @create 2019-10-01 7:58 PM
 **/
public class CsvUtil {

    private final static String UPLOAD_PATH = "/uploadFiles/";
    private static String fomartDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

    public static List<Map<String, String>> readObjectsFromCsv(String filePath) throws IOException {
        File file = new File(filePath);

        return readObjectsFromCsv(file);
    }

    public static List<Map<String, String>> readObjectsFromCsv(File file) throws IOException {

        CsvSchema bootstrap = CsvSchema.emptySchema().withHeader();
        CsvMapper csvMapper = new CsvMapper();
        MappingIterator<Map<String, String>> mappingIterator = csvMapper.readerFor(Map.class).with(bootstrap).readValues(file);

        return mappingIterator.readAll();
    }

    public static List<Map<String, String>> readObjectsFromMultiFile(MultipartFile file) throws IOException {

        File folder = getRootPath();
        String originalFilename = file.getOriginalFilename();
        String newName = UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf("."), originalFilename.length());
        File newFile = new File(folder, newName);
        file.transferTo(newFile);

        return readObjectsFromCsv(newFile);
    }

    public static File getRootPath() {
        File file = new File(System.getProperty("user.dir") + UPLOAD_PATH);
        if (!file.exists()) {
            file.mkdirs();
        }
        String rootPath = file.getAbsolutePath();
        File folder = new File(rootPath + '/' + fomartDate);
        if (!folder.isDirectory()) {
            folder.mkdirs();
        }
        return folder;
    }
}
