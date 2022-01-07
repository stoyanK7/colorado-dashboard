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
class TopMachinesWithMostPrintVolumeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    @BeforeAll
    public void setUp(@Autowired DataSource dataSource) throws Exception {
        Connection conn = dataSource.getConnection();
        ScriptUtils.executeSqlScript(conn, new ClassPathResource("sql/TopMachinesWithMostPrintVolumeTestData.sql"));
    }

    @Test
    @DisplayName("UNIT: POST /TopMachinesWithMostPrintVolume?aggregated=true returns correct data.")
    void getAllAggregated() throws Exception {
        mockMvc.perform(post("/TopMachinesWithMostPrintVolume?aggregated=true"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'Printer id':'701','Printed square meters':10.5},{'Printer id':'700','Printed square meters':6.5},{'Printer id':'702','Printed square meters':6.0}]"))
                .andReturn();
    }

    @Test
    @DisplayName("UNIT: POST /TopMachinesWithMostPrintVolume?aggregated=false&bin=week returns correct data.")
    void getAllNonAggregatedPerWeek() throws Exception {
        mockMvc.perform(post("/TopMachinesWithMostPrintVolume?aggregated=false&bin=week"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'Date':'2021/48','Printer id':'701','Printed square meters':10.5},{'Date':'2021/48','Printer id':'700','Printed square meters':6.5},{'Date':'2021/48','Printer id':'702','Printed square meters':6.0}]\n"))
                .andReturn();
    }

    @Test
    @DisplayName("UNIT: POST /'TopMachinesWithMostPrintVolume?aggregated=false returns correct data.")
    void getAllNonAggregated() throws Exception {
        mockMvc.perform(post("/TopMachinesWithMostPrintVolume?aggregated=false"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'Date':'2021/Dec/02','Printer id':'701','Printed square meters':5.0},{'Date':'2021/Dec/02','Printer id':'700','Printed square meters':3.0},{'Date':'2021/Dec/03','Printer id':'701','Printed square meters':3.0},{'Date':'2021/Dec/01','Printer id':'701','Printed square meters':2.5},{'Date':'2021/Dec/01','Printer id':'702','Printed square meters':2.0},{'Date':'2021/Dec/02','Printer id':'702','Printed square meters':2.0},{'Date':'2021/Dec/03','Printer id':'700','Printed square meters':2.0},{'Date':'2021/Dec/03','Printer id':'702','Printed square meters':2.0},{'Date':'2021/Dec/01','Printer id':'700','Printed square meters':1.5}]"))
                .andReturn();
    }

    @Test
    @DisplayName("UNIT: POST /TopMachinesWithMostPrintVolume/Period?aggregated=true returns correct data.")
    void getAllForPeriodAggregated() throws Exception {
        PeriodDto request
                = new PeriodDto(
                formatter.parse("2021-12-02"),
                formatter.parse("2021-12-03"));

        mockMvc.perform(post("/TopMachinesWithMostPrintVolume/Period?aggregated=true")
                        .content(asJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'Printer id':'701','Printed square meters':8.0},{'Printer id':'700','Printed square meters':5.0},{'Printer id':'702','Printed square meters':4.0}]"))
                .andReturn();
    }

    @Test
    @DisplayName("UNIT: POST /TopMachinesWithMostPrintVolume/Period?aggregated=false returns correct data.")
    void getAllForPeriodNonAggregated() throws Exception {
        PeriodDto request
                = new PeriodDto(
                formatter.parse("2021-12-02"),
                formatter.parse("2021-12-03"));

        mockMvc.perform(post("/TopMachinesWithMostPrintVolume/Period?aggregated=false")
                        .content(asJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'Date':'2021/Dec/02','Printer id':'701','Printed square meters':5.0},{'Date':'2021/Dec/02','Printer id':'700','Printed square meters':3.0},{'Date':'2021/Dec/02','Printer id':'702','Printed square meters':2.0},{'Date':'2021/Dec/03','Printer id':'701','Printed square meters':3.0},{'Date':'2021/Dec/03','Printer id':'700','Printed square meters':2.0},{'Date':'2021/Dec/03','Printer id':'702','Printed square meters':2.0}]"))
                .andReturn();
    }

    @Test
    @DisplayName("UNIT: POST /TopMachinesWithMostPrintVolume/PeriodAndPrinters?aggregated=true returns correct data.")
    void getAllForPeriodAndPrintersAggregated() throws Exception {
        PeriodAndPrinterIdsDto request
                = new PeriodAndPrinterIdsDto(
                formatter.parse("2021-12-02"),
                formatter.parse("2021-12-03"),
                Arrays.asList("700", "702"));

        mockMvc.perform(post("/TopMachinesWithMostPrintVolume/PeriodAndPrinters?aggregated=true")
                        .content(asJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'Printer id':'700','Printed square meters':5.0},{'Printer id':'702','Printed square meters':4.0}]"))
                .andReturn();
    }

    @Test
    @DisplayName("UNIT: POST /TopMachinesWithMostPrintVolume/PeriodAndPrinters?aggregated=false returns correct data.")
    void getAllForPeriodAndPrintersNonAggregated() throws Exception {
        PeriodAndPrinterIdsDto request
                = new PeriodAndPrinterIdsDto(
                formatter.parse("2021-12-02"),
                formatter.parse("2021-12-03"),
                Arrays.asList("700", "702"));

        mockMvc.perform(post("/TopMachinesWithMostPrintVolume/PeriodAndPrinters?aggregated=false")
                        .content(asJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'Date':'2021/Dec/02','Printer id':'700','Printed square meters':3.0},{'Date':'2021/Dec/02','Printer id':'702','Printed square meters':2.0},{'Date':'2021/Dec/03','Printer id':'700','Printed square meters':2.0},{'Date':'2021/Dec/03','Printer id':'702','Printed square meters':2.0}]"))
                .andReturn();
    }

    @Test
    @DisplayName("UNIT: GET /TopMachinesWithMostPrintVolume/AvailableTimePeriod returns correct data.")
    void getAvailableTimePeriod() throws Exception {
        mockMvc.perform(get("/TopMachinesWithMostPrintVolume/AvailableTimePeriod"))
                .andExpect(status().isOk())
                .andExpect(content().json("{'from':'2021-12-01','to':'2021-12-03'}"))
                .andReturn();
    }

    @Test
    @DisplayName("UNIT: GET /TopMachinesWithMostPrintVolume/AvailablePrinters returns correct data.")
    void getAvailablePrinters() throws Exception {
        mockMvc.perform(get("/TopMachinesWithMostPrintVolume/AvailablePrinters"))
                .andExpect(status().isOk())
                .andExpect(content().json("{'printerIds':['700','701','702']}"))
                .andReturn();
    }

    @Test
    @DisplayName("UNIT: GET /TopMachinesWithMostPrintVolume/ChartDataKeys returns correct data.")
    void getChartDataKeys() throws Exception {
        mockMvc.perform(get("/TopMachinesWithMostPrintVolume/ChartDataKeys"))
                .andExpect(status().isOk())
                .andExpect(content().json("{'dataKeys':['700','701','702']}"))
                .andReturn();
    }
}