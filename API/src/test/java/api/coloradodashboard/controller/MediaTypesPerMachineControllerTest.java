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
class MediaTypesPerMachineControllerTest {
    @Autowired
    private MockMvc mockMvc;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    @BeforeAll
    public void setUp(@Autowired DataSource dataSource) throws Exception {
        Connection conn = dataSource.getConnection();
        ScriptUtils.executeSqlScript(conn, new ClassPathResource("sql/MediaTypesPerMachineTestData.sql"));
    }

    @Test
    @DisplayName("UNIT: POST /MediaTypesPerMachine?aggregated=true returns correct data.")
    void getAllAggregated() throws Exception {
        mockMvc.perform(post("/MediaTypesPerMachine?aggregated=true"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'Media type':'Oneway','Printed square meters':8.0},{'Media type':'Trasparente','Printed square meters':7.5},{'Media type':'EPS Canvas','Printed square meters':5.0},{'Media type':'Monomerico Retro Grigio','Printed square meters':4.0},{'Media type':'Creative smooth 320','Printed square meters':3.0},{'Media type':'Backfilm SP Midia','Printed square meters':2.0}]"))
                .andReturn();
    }

    @Test
    @DisplayName("UNIT: POST /'MediaTypesPerMachine?aggregated=false returns correct data.")
    void getAllNonAggregated() throws Exception {
        mockMvc.perform(post("/MediaTypesPerMachine?aggregated=false"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'Printer id':'701','Media type':'Backfilm SP Midia','Printed square meters':2.0},{'Printer id':'702','Media type':'Creative smooth 320','Printed square meters':3.0},{'Printer id':'700','Media type':'EPS Canvas','Printed square meters':1.5},{'Printer id':'701','Media type':'EPS Canvas','Printed square meters':2.0},{'Printer id':'702','Media type':'EPS Canvas','Printed square meters':1.5},{'Printer id':'700','Media type':'Monomerico Retro Grigio','Printed square meters':2.0},{'Printer id':'702','Media type':'Monomerico Retro Grigio','Printed square meters':2.0},{'Printer id':'700','Media type':'Oneway','Printed square meters':3.0},{'Printer id':'701','Media type':'Oneway','Printed square meters':3.0},{'Printer id':'702','Media type':'Oneway','Printed square meters':2.0},{'Printer id':'700','Media type':'Trasparente','Printed square meters':2.5},{'Printer id':'701','Media type':'Trasparente','Printed square meters':5.0}]"))
                .andReturn();
    }

    @Test
    @DisplayName("UNIT: POST /MediaTypesPerMachine/Period?aggregated=true returns correct data.")
    void getAllForPeriodAggregated() throws Exception {
        PeriodDto request
                = new PeriodDto(
                formatter.parse("2021-12-01"),
                formatter.parse("2021-12-01"));

        mockMvc.perform(post("/MediaTypesPerMachine/Period?aggregated=true")
                        .content(asJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'Media type':'Trasparente','Printed square meters':7.5},{'Media type':'Creative smooth 320','Printed square meters':3.0},{'Media type':'Backfilm SP Midia','Printed square meters':2.0},{'Media type':'Monomerico Retro Grigio','Printed square meters':2.0},{'Media type':'EPS Canvas','Printed square meters':1.5}]"))
                .andReturn();
    }

    @Test
    @DisplayName("UNIT: POST /MediaTypesPerMachine/Period?aggregated=false returns correct data.")
    void getAllForPeriodNonAggregated() throws Exception {
        PeriodDto request
                = new PeriodDto(
                formatter.parse("2021-12-01"),
                formatter.parse("2021-12-01"));

        mockMvc.perform(post("/MediaTypesPerMachine/Period?aggregated=false")
                        .content(asJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'Printer id':'701','Media type':'Backfilm SP Midia','Printed square meters':2.0},{'Printer id':'702','Media type':'Creative smooth 320','Printed square meters':3.0},{'Printer id':'700','Media type':'EPS Canvas','Printed square meters':1.5},{'Printer id':'702','Media type':'Monomerico Retro Grigio','Printed square meters':2.0},{'Printer id':'700','Media type':'Trasparente','Printed square meters':2.5},{'Printer id':'701','Media type':'Trasparente','Printed square meters':5.0}]"))
                .andReturn();
    }

    @Test
    @DisplayName("UNIT: POST /MediaTypesPerMachine/PeriodAndPrinters?aggregated=true returns correct data.")
    void getAllForPeriodAndPrintersAggregated() throws Exception {
        PeriodAndPrinterIdsDto request
                = new PeriodAndPrinterIdsDto(
                formatter.parse("2021-12-01"),
                formatter.parse("2021-12-01"),
                Arrays.asList("700", "702"));

        mockMvc.perform(post("/MediaTypesPerMachine/PeriodAndPrinters?aggregated=true")
                        .content(asJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'Media type':'Creative smooth 320','Printed square meters':3.0},{'Media type':'Trasparente','Printed square meters':2.5},{'Media type':'Monomerico Retro Grigio','Printed square meters':2.0},{'Media type':'EPS Canvas','Printed square meters':1.5}]"))
                .andReturn();
    }

    @Test
    @DisplayName("UNIT: POST /MediaTypesPerMachine/PeriodAndPrinters?aggregated=false returns correct data.")
    void getAllForPeriodAndPrintersNonAggregated() throws Exception {
        PeriodAndPrinterIdsDto request
                = new PeriodAndPrinterIdsDto(
                formatter.parse("2021-12-01"),
                formatter.parse("2021-12-01"),
                Arrays.asList("700", "702"));

        mockMvc.perform(post("/MediaTypesPerMachine/PeriodAndPrinters?aggregated=false")
                        .content(asJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'Printer id':'702','Media type':'Creative smooth 320','Printed square meters':3.0},{'Printer id':'700','Media type':'EPS Canvas','Printed square meters':1.5},{'Printer id':'702','Media type':'Monomerico Retro Grigio','Printed square meters':2.0},{'Printer id':'700','Media type':'Trasparente','Printed square meters':2.5}]"))
                .andReturn();
    }

    @Test
    @DisplayName("UNIT: GET /MediaTypesPerMachine/AvailableTimePeriod returns correct data.")
    void getAvailableTimePeriod() throws Exception {
        mockMvc.perform(get("/MediaTypesPerMachine/AvailableTimePeriod"))
                .andExpect(status().isOk())
                .andExpect(content().json("{'from':'2021-12-01','to':'2021-12-02'}"))
                .andReturn();
    }

    @Test
    @DisplayName("UNIT: GET /MediaTypesPerMachine/AvailablePrinters returns correct data.")
    void getAvailablePrinters() throws Exception {
        mockMvc.perform(get("/MediaTypesPerMachine/AvailablePrinters"))
                .andExpect(status().isOk())
                .andExpect(content().json("{'printerIds':['700','701','702']}"))
                .andReturn();
    }

    @Test
    @DisplayName("UNIT: GET /MediaTypesPerMachine/ChartDataKeys returns correct data.")
    void getChartDataKeys() throws Exception {
        mockMvc.perform(get("/MediaTypesPerMachine/ChartDataKeys"))
                .andExpect(status().isOk())
                .andExpect(content().json("{'dataKeys':['EPS Canvas','Trasparente','Monomerico Retro Grigio','Oneway','Backfilm SP Midia','Creative smooth 320']}"))
                .andReturn();
    }
}