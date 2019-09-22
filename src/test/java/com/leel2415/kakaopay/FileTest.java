package com.leel2415.kakaopay;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.*;

@Slf4j
public class FileTest {

    final static String DATA_FILE_NAME = "data.csv";

    CSVParser parser;

    @Before
    public void init() throws IOException {
        ClassPathResource cpr = new ClassPathResource((DATA_FILE_NAME));
        BufferedReader in = new BufferedReader(new InputStreamReader(cpr.getInputStream()));
        parser = CSVFormat.EXCEL.parse(in);
    }

    @Test
    public void readTest() throws IOException{
        assertThat(parser.getRecords().size(), not(0));
    }





}
