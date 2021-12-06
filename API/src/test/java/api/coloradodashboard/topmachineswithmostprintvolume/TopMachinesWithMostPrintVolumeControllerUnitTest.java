package api.coloradodashboard.topmachineswithmostprintvolume;

import api.coloradodashboard.request.PeriodAndPrinterRequest;
import api.coloradodashboard.request.PeriodRequest;
import api.coloradodashboard.request.PrinterRequest;
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
public class TopMachinesWithMostPrintVolumeControllerUnitTest {
    @Autowired
    private MockMvc mockMvc;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    @BeforeAll
    public void setUp(@Autowired DataSource dataSource) throws Exception {
        Connection conn = dataSource.getConnection();
        ScriptUtils.executeSqlScript(conn, new ClassPathResource("sql/TopMachinesWithMostPrintVolumeTestData.sql"));
    }

    @Test
    @DisplayName("UNIT: GET /TopMachinesWithMostPrintVolume returns (200) OK when data is present.")
    void unitTestGetAll() throws Exception {
        mockMvc.perform(get("/TopMachinesWithMostPrintVolume"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'Printer id':'701','Printed square meters':243.0},{'Printer id':'702','Printed square meters':238.0},{'Printer id':'700','Printed square meters':196.0}]"))
                .andReturn();
    }

    @Test
    @DisplayName("UNIT: POST /TopMachinesWithMostPrintVolume/Period returns (200) OK when data is present.")
    void unitTestGetAllForPeriod() throws Exception {
        PeriodRequest request
                = new PeriodRequest(
                formatter.parse("2021-12-11"),
                formatter.parse("2021-12-12"));

        mockMvc.perform(post("/TopMachinesWithMostPrintVolume/Period")
                        .content(asJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'Printer id':'701','Printed square meters':138.0},{'Printer id':'702','Printed square meters':136.0},{'Printer id':'700','Printed square meters':96.0}]"))
                .andReturn();
    }

    @Test
    @DisplayName("UNIT: POST /TopMachinesWithMostPrintVolume/Printer returns (200) OK when data is present.")
    void unitTestGetPrinters() throws Exception {
        PrinterRequest request =
                new PrinterRequest(Arrays.asList("700", "702"));

        mockMvc.perform(post("/TopMachinesWithMostPrintVolume/Printer")
                        .content(asJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'Printer id':'702','Printed square meters':238.0},{'Printer id':'700','Printed square meters':196.0}]"))
                .andReturn();
    }

    @Test
    @DisplayName("UNIT: POST /TopMachinesWithMostPrintVolume/PeriodAndPrinter returns (200) OK when data is present.")
    void unitTestGetPrintersForPeriod() throws Exception {
        PeriodAndPrinterRequest request
                = new PeriodAndPrinterRequest(
                formatter.parse("2021-12-11"),
                formatter.parse("2021-12-12"),
                Arrays.asList("700", "702"));

        mockMvc.perform(post("/TopMachinesWithMostPrintVolume/PeriodAndPrinter")
                        .content(asJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'Printer id':'702','Printed square meters':136.0},{'Printer id':'700','Printed square meters':96.0}]"))
                .andReturn();
    }
}
