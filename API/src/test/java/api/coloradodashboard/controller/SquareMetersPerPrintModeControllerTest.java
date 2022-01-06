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
class SquareMetersPerPrintModeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    @BeforeAll
    public void setUp(@Autowired DataSource dataSource) throws Exception {
        Connection conn = dataSource.getConnection();
        ScriptUtils.executeSqlScript(conn, new ClassPathResource("sql/SquareMetersPerPrintModeTestData.sql"));
    }

    @Test
    @DisplayName("UNIT: POST /SquareMetersPerPrintMode?aggregated=true returns correct data.")
    void getAllAggregated() throws Exception {
        mockMvc.perform(post("/SquareMetersPerPrintMode?aggregated=true"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'Date':'2021/Dec/01','Print mode':'Backlit','Printed square meters':1.5},{'Date':'2021/Dec/01','Print mode':'High speed','Printed square meters':5.0},{'Date':'2021/Dec/01','Print mode':'Production','Printed square meters':7.5},{'Date':'2021/Dec/01','Print mode':'Specialty','Printed square meters':2.0},{'Date':'2021/Dec/02','Print mode':'Backlit','Printed square meters':3.5},{'Date':'2021/Dec/02','Print mode':'Reliance','Printed square meters':8.0},{'Date':'2021/Dec/02','Print mode':'Specialty','Printed square meters':2.0}]"))
                .andReturn();
    }

    @Test
    @DisplayName("UNIT: POST /SquareMetersPerPrintMode?aggregated=true&bin=week returns correct data.")
    void getAllAggregatedPerWeek() throws Exception {
        mockMvc.perform(post("/SquareMetersPerPrintMode?aggregated=true&bin=week"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'Date':'2021/48','Print mode':'Backlit','Printed square meters':5.0},{'Date':'2021/48','Print mode':'High speed','Printed square meters':5.0},{'Date':'2021/48','Print mode':'Production','Printed square meters':7.5},{'Date':'2021/48','Print mode':'Reliance','Printed square meters':8.0},{'Date':'2021/48','Print mode':'Specialty','Printed square meters':4.0}]"))
                .andReturn();
    }

    @Test
    @DisplayName("UNIT: POST /'SquareMetersPerPrintMode?aggregated=false returns correct data.")
    void getAllNonAggregated() throws Exception {
        mockMvc.perform(post("/SquareMetersPerPrintMode?aggregated=false"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'Date':'2021/Dec/01','Printer id':'700','Print mode':'Backlit','Printed square meters':1.5},{'Date':'2021/Dec/01','Printer id':'700','Print mode':'Production','Printed square meters':2.5},{'Date':'2021/Dec/01','Printer id':'701','Print mode':'High speed','Printed square meters':2.0},{'Date':'2021/Dec/01','Printer id':'701','Print mode':'Production','Printed square meters':5.0},{'Date':'2021/Dec/01','Printer id':'702','Print mode':'High speed','Printed square meters':3.0},{'Date':'2021/Dec/01','Printer id':'702','Print mode':'Specialty','Printed square meters':2.0},{'Date':'2021/Dec/02','Printer id':'700','Print mode':'Reliance','Printed square meters':3.0},{'Date':'2021/Dec/02','Printer id':'700','Print mode':'Specialty','Printed square meters':2.0},{'Date':'2021/Dec/02','Printer id':'701','Print mode':'Backlit','Printed square meters':2.0},{'Date':'2021/Dec/02','Printer id':'701','Print mode':'Reliance','Printed square meters':3.0},{'Date':'2021/Dec/02','Printer id':'702','Print mode':'Backlit','Printed square meters':1.5},{'Date':'2021/Dec/02','Printer id':'702','Print mode':'Reliance','Printed square meters':2.0}]"))
                .andReturn();
    }

    @Test
    @DisplayName("UNIT: POST /SquareMetersPerPrintMode/Period?aggregated=true returns correct data.")
    void getAllForPeriodAggregated() throws Exception {
        PeriodDto request
                = new PeriodDto(
                formatter.parse("2021-12-01"),
                formatter.parse("2021-12-01"));

        mockMvc.perform(post("/SquareMetersPerPrintMode/Period?aggregated=true")
                        .content(asJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'Date':'2021/Dec/01','Print mode':'Backlit','Printed square meters':1.5},{'Date':'2021/Dec/01','Print mode':'High speed','Printed square meters':5.0},{'Date':'2021/Dec/01','Print mode':'Production','Printed square meters':7.5},{'Date':'2021/Dec/01','Print mode':'Specialty','Printed square meters':2.0}]"))
                .andReturn();
    }

    @Test
    @DisplayName("UNIT: POST /SquareMetersPerPrintMode/Period?aggregated=false returns correct data.")
    void getAllForPeriodNonAggregated() throws Exception {
        PeriodDto request
                = new PeriodDto(
                formatter.parse("2021-12-01"),
                formatter.parse("2021-12-01"));

        mockMvc.perform(post("/SquareMetersPerPrintMode/Period?aggregated=false")
                        .content(asJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'Date':'2021/Dec/01','Printer id':'700','Print mode':'Backlit','Printed square meters':1.5},{'Date':'2021/Dec/01','Printer id':'700','Print mode':'Production','Printed square meters':2.5},{'Date':'2021/Dec/01','Printer id':'701','Print mode':'High speed','Printed square meters':2.0},{'Date':'2021/Dec/01','Printer id':'701','Print mode':'Production','Printed square meters':5.0},{'Date':'2021/Dec/01','Printer id':'702','Print mode':'High speed','Printed square meters':3.0},{'Date':'2021/Dec/01','Printer id':'702','Print mode':'Specialty','Printed square meters':2.0}]"))
                .andReturn();
    }

    @Test
    @DisplayName("UNIT: POST /SquareMetersPerPrintMode/PeriodAndPrinters?aggregated=true returns correct data.")
    void getAllForPeriodAndPrintersAggregated() throws Exception {
        PeriodAndPrinterIdsDto request
                = new PeriodAndPrinterIdsDto(
                formatter.parse("2021-12-01"),
                formatter.parse("2021-12-01"),
                Arrays.asList("700", "702"));

        mockMvc.perform(post("/SquareMetersPerPrintMode/PeriodAndPrinters?aggregated=true")
                        .content(asJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'Date':'2021/Dec/01','Print mode':'Backlit','Printed square meters':1.5},{'Date':'2021/Dec/01','Print mode':'High speed','Printed square meters':3.0},{'Date':'2021/Dec/01','Print mode':'Production','Printed square meters':2.5},{'Date':'2021/Dec/01','Print mode':'Specialty','Printed square meters':2.0}]"))
                .andReturn();
    }

    @Test
    @DisplayName("UNIT: POST /SquareMetersPerPrintMode/PeriodAndPrinters?aggregated=false returns correct data.")
    void getAllForPeriodAndPrintersNonAggregated() throws Exception {
        PeriodAndPrinterIdsDto request
                = new PeriodAndPrinterIdsDto(
                formatter.parse("2021-12-01"),
                formatter.parse("2021-12-01"),
                Arrays.asList("700", "702"));

        mockMvc.perform(post("/SquareMetersPerPrintMode/PeriodAndPrinters?aggregated=false")
                        .content(asJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'Date':'2021/Dec/01','Printer id':'700','Print mode':'Backlit','Printed square meters':1.5},{'Date':'2021/Dec/01','Printer id':'700','Print mode':'Production','Printed square meters':2.5},{'Date':'2021/Dec/01','Printer id':'702','Print mode':'High speed','Printed square meters':3.0},{'Date':'2021/Dec/01','Printer id':'702','Print mode':'Specialty','Printed square meters':2.0}]"))
                .andReturn();
    }

    @Test
    @DisplayName("UNIT: GET /SquareMetersPerPrintMode/AvailableTimePeriod returns correct data.")
    void getAvailableTimePeriod() throws Exception {
        mockMvc.perform(get("/SquareMetersPerPrintMode/AvailableTimePeriod"))
                .andExpect(status().isOk())
                .andExpect(content().json("{'from':'2021-12-01','to':'2021-12-02'}"))
                .andReturn();
    }

    @Test
    @DisplayName("UNIT: GET /SquareMetersPerPrintMode/AvailablePrinters returns correct data.")
    void getAvailablePrinters() throws Exception {
        mockMvc.perform(get("/SquareMetersPerPrintMode/AvailablePrinters"))
                .andExpect(status().isOk())
                .andExpect(content().json("{'printerIds':['700','701','702']}"))
                .andReturn();
    }

    @Test
    @DisplayName("UNIT: GET /SquareMetersPerPrintMode/ChartDataKeys returns correct data.")
    void getChartDataKeys() throws Exception {
        mockMvc.perform(get("/SquareMetersPerPrintMode/ChartDataKeys"))
                .andExpect(status().isOk())
                .andExpect(content().json("{'dataKeys':['Backlit','Production','Specialty','Reliance','High speed']}"))
                .andReturn();
    }
}