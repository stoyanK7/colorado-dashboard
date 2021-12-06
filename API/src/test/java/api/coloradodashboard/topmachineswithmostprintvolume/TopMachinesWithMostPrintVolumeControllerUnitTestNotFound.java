package api.coloradodashboard.topmachineswithmostprintvolume;

import api.coloradodashboard.PeriodAndPrinterRequest;
import api.coloradodashboard.PeriodRequest;
import api.coloradodashboard.PrinterRequest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Date;

import static api.coloradodashboard.TestUtil.asJsonString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TopMachinesWithMostPrintVolumeControllerUnitTestNotFound {
    @InjectMocks
    private TopMachinesWithMostPrintVolumeController componentUnderTest;
    @Mock
    private TopMachinesWithMostPrintVolumeService service;
    private MockMvc mockMvc;

    @BeforeAll
    public void setUp(@Autowired DataSource dataSource) {
        mockMvc = MockMvcBuilders.standaloneSetup(componentUnderTest).build();
    }

    @Test
    @DisplayName("UNIT: GET /TopMachinesWithMostPrintVolume returns (404) NOT FOUND when no data is present.")
    void unitTestGetAllNotFound() throws Exception {
        given(service.getAll()).willReturn(new ArrayList<>());

        mockMvc.perform(get("/TopMachinesWithMostPrintVolume"))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    @DisplayName("UNIT: POST /TopMachinesWithMostPrintVolume/Period returns (404) NOT FOUND when no data is present.")
    void unitTestGetAllForPeriodNotFound() throws Exception {
        PeriodRequest request = new PeriodRequest(new Date(), new Date());

        given(service.getAllForPeriod(any(), any())).willReturn(new ArrayList<>());

        mockMvc
                .perform(post("/TopMachinesWithMostPrintVolume/Period")
                        .content(asJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    @DisplayName("UNIT: POST /TopMachinesWithMostPrintVolume/Printer returns (404) NOT FOUND when no data is present.")
    void unitTestGetPrintersNotFound() throws Exception {
        PrinterRequest request = new PrinterRequest();

        given(service.getPrinters(any())).willReturn(new ArrayList<>());

        mockMvc.perform(post("/TopMachinesWithMostPrintVolume/Printer")
                        .content(asJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    @DisplayName("UNIT: POST /TopMachinesWithMostPrintVolume/PeriodAndPrinter returns (404) NOT FOUND when no data is present.")
    void unitTestGetPrintersForPeriodNotFound() throws Exception {
        PeriodAndPrinterRequest request = new PeriodAndPrinterRequest(new Date(), new Date(), new ArrayList<>());

        given(service.getPrintersForPeriod(any(), any(), any())).willReturn(new ArrayList<>());

        mockMvc.perform(post("/TopMachinesWithMostPrintVolume/PeriodAndPrinter")
                        .content(asJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }
}
