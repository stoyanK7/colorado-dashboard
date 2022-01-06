package api.coloradodashboard.controller;

import api.coloradodashboard.dto.PeriodAndPrinterIdsDto;
import api.coloradodashboard.dto.PeriodDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.web.servlet.MockMvc;

import javax.sql.DataSource;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Locale;

import static api.coloradodashboard.TestUtil.asJsonString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class InkUsageControllerTest {
    @Autowired
    private MockMvc mockMvc;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    @BeforeAll
    public void setUp(@Autowired DataSource dataSource) throws Exception {
        Connection conn = dataSource.getConnection();
        ScriptUtils.executeSqlScript(conn, new ClassPathResource("sql/InkUsageTestData.sql"));
    }

    @Test
    @DisplayName("UNIT: POST /InkUsage?aggregated=true returns correct data.")
    void getAllAggregated() throws Exception {
        mockMvc.perform(post("/InkUsage?aggregated=true"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'Date':'2021/Dec/01','Cyan':5.5,'Magenta':9.5,'Yellow':9.0,'Black':12.0},{'Date':'2021/Dec/02','Cyan':11.5,'Magenta':4.0,'Yellow':14.5,'Black':7.0},{'Date':'2021/Dec/03','Cyan':11.0,'Magenta':8.0,'Yellow':2.0,'Black':2.5}]"))
                .andReturn();
    }

    @Test
    @DisplayName("UNIT: POST /InkUsage?aggregated=false returns correct data.")
    void getAllNonAggregated() throws Exception {
        mockMvc.perform(post("/InkUsage?aggregated=false"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'Date':'2021/Dec/01','Printer id':'700','Cyan':1.5,'Magenta':2.5,'Yellow':3.5,'Black':4.5},{'Date':'2021/Dec/01','Printer id':'701','Cyan':1.0,'Magenta':2.5,'Yellow':1.5,'Black':5.5},{'Date':'2021/Dec/01','Printer id':'702','Cyan':3.0,'Magenta':4.5,'Yellow':4.0,'Black':2.0},{'Date':'2021/Dec/02','Printer id':'700','Cyan':2.5,'Magenta':0.5,'Yellow':5.5,'Black':3.0},{'Date':'2021/Dec/02','Printer id':'701','Cyan':5.5,'Magenta':2.5,'Yellow':3.5,'Black':1.0},{'Date':'2021/Dec/02','Printer id':'702','Cyan':3.5,'Magenta':1.0,'Yellow':5.5,'Black':3.0},{'Date':'2021/Dec/03','Printer id':'700','Cyan':4.5,'Magenta':5.5,'Yellow':0.5,'Black':1.5},{'Date':'2021/Dec/03','Printer id':'701','Cyan':4.0,'Magenta':1.0,'Yellow':0.5,'Black':0.5},{'Date':'2021/Dec/03','Printer id':'702','Cyan':2.5,'Magenta':1.5,'Yellow':1.0,'Black':0.5}]"))
                .andReturn();
    }

    @Test
    @DisplayName("UNIT: POST /InkUsage/Period?aggregated=true returns correct data.")
    void getAllForPeriodAggregated() throws Exception {
        PeriodDto request
                = new PeriodDto(
                formatter.parse("2021-12-02"),
                formatter.parse("2021-12-03"));

        mockMvc.perform(post("/InkUsage/Period?aggregated=true")
                        .content(asJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'Date':'2021/Dec/02','Cyan':11.5,'Magenta':4.0,'Yellow':14.5,'Black':7.0},{'Date':'2021/Dec/03','Cyan':11.0,'Magenta':8.0,'Yellow':2.0,'Black':2.5}]"))
                .andReturn();
    }

    @Test
    @DisplayName("UNIT: POST /InkUsage/Period?aggregated=false returns correct data.")
    void getAllForPeriodNonAggregated() throws Exception {
        PeriodDto request
                = new PeriodDto(
                formatter.parse("2021-12-02"),
                formatter.parse("2021-12-03"));

        mockMvc.perform(post("/InkUsage/Period?aggregated=false")
                        .content(asJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'Date':'2021/Dec/02','Printer id':'700','Cyan':2.5,'Magenta':0.5,'Yellow':5.5,'Black':3.0},{'Date':'2021/Dec/02','Printer id':'701','Cyan':5.5,'Magenta':2.5,'Yellow':3.5,'Black':1.0},{'Date':'2021/Dec/02','Printer id':'702','Cyan':3.5,'Magenta':1.0,'Yellow':5.5,'Black':3.0},{'Date':'2021/Dec/03','Printer id':'700','Cyan':4.5,'Magenta':5.5,'Yellow':0.5,'Black':1.5},{'Date':'2021/Dec/03','Printer id':'701','Cyan':4.0,'Magenta':1.0,'Yellow':0.5,'Black':0.5},{'Date':'2021/Dec/03','Printer id':'702','Cyan':2.5,'Magenta':1.5,'Yellow':1.0,'Black':0.5}]"))
                .andReturn();
    }

    @Test
    @DisplayName("UNIT: POST /InkUsage/PeriodAndPrinters?aggregated=true returns correct data.")
    void getAllForPeriodAndPrintersAggregated() throws Exception {
        PeriodAndPrinterIdsDto request
                = new PeriodAndPrinterIdsDto(
                formatter.parse("2021-12-02"),
                formatter.parse("2021-12-03"),
                Arrays.asList("700", "702"));

        mockMvc.perform(post("/InkUsage/PeriodAndPrinters?aggregated=true")
                        .content(asJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'Date':'2021/Dec/02','Cyan':6.0,'Magenta':1.5,'Yellow':11.0,'Black':6.0},{'Date':'2021/Dec/03','Cyan':7.0,'Magenta':7.0,'Yellow':1.5,'Black':2.0}]"))
                .andReturn();
    }

    @Test
    @DisplayName("UNIT: POST /InkUsage/PeriodAndPrinters?aggregated=false returns correct data.")
    void getAllForPeriodAndPrintersNonAggregated() throws Exception {
        PeriodAndPrinterIdsDto request
                = new PeriodAndPrinterIdsDto(
                formatter.parse("2021-12-02"),
                formatter.parse("2021-12-03"),
                Arrays.asList("700", "702"));

        mockMvc.perform(post("/InkUsage/PeriodAndPrinters?aggregated=false")
                        .content(asJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'Date':'2021/Dec/02','Printer id':'700','Cyan':2.5,'Magenta':0.5,'Yellow':5.5,'Black':3.0},{'Date':'2021/Dec/02','Printer id':'702','Cyan':3.5,'Magenta':1.0,'Yellow':5.5,'Black':3.0},{'Date':'2021/Dec/03','Printer id':'700','Cyan':4.5,'Magenta':5.5,'Yellow':0.5,'Black':1.5},{'Date':'2021/Dec/03','Printer id':'702','Cyan':2.5,'Magenta':1.5,'Yellow':1.0,'Black':0.5}]"))
                .andReturn();
    }

    @Test
    @DisplayName("UNIT: GET /InkUsage/AvailableTimePeriod returns correct data.")
    void getAvailableTimePeriod() throws Exception {
        mockMvc.perform(get("/InkUsage/AvailableTimePeriod"))
                .andExpect(status().isOk())
                .andExpect(content().json("{'from':'2021-12-01','to':'2021-12-03'}"))
                .andReturn();
    }

    @Test
    @DisplayName("UNIT: GET /InkUsage/AvailablePrinters returns correct data.")
    void getAvailablePrinters() throws Exception {
        mockMvc.perform(get("/InkUsage/AvailablePrinters"))
                .andExpect(status().isOk())
                .andExpect(content().json("{'printerIds':['700','701','702']}"))
                .andReturn();
    }
}