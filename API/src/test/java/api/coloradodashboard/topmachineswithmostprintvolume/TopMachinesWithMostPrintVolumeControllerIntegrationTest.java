package api.coloradodashboard.topmachineswithmostprintvolume;

import api.coloradodashboard.PeriodAndPrinterRequest;
import api.coloradodashboard.PeriodRequest;
import api.coloradodashboard.PrinterRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TopMachinesWithMostPrintVolumeControllerTest {
    @InjectMocks
    private TopMachinesWithMostPrintVolumeController componentUnderTest;
    @Mock
    private TopMachinesWithMostPrintVolumeService service;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("INTEGRATION: getAll() invokes service method.")
    void integrationTestGetAll() {
        componentUnderTest.getAll();
        verify(service).getAll();
    }

    @Test
    @DisplayName("INTEGRATION: getAllForPeriod() invokes service method.")
    void integrationTestGetAllForPeriod() {
        Date from = mock(Date.class);
        Date to = mock(Date.class);
        PeriodRequest request = new PeriodRequest(from, to);

        componentUnderTest.getAllForPeriod(request);

        ArgumentCaptor<Date> fromArgumentCaptor = ArgumentCaptor.forClass(Date.class);
        ArgumentCaptor<Date> toArgumentCaptor = ArgumentCaptor.forClass(Date.class);

        verify(service)
                .getAllForPeriod(fromArgumentCaptor.capture(), toArgumentCaptor.capture());


        assertThat(fromArgumentCaptor.getValue()).isEqualTo(PeriodRequest.removeTime(from));
        assertThat(toArgumentCaptor.getValue()).isEqualTo(PeriodRequest.removeTime(to));
    }

    @Test
    @DisplayName("INTEGRATION: getPrinters() invokes service method.")
    void integrationTestGetPrinters() {
        List printerIds = mock(List.class, RETURNS_DEEP_STUBS);
        PrinterRequest request = new PrinterRequest(printerIds);

        componentUnderTest.getPrinters(request);

        ArgumentCaptor<List> printerIdsArgumentCaptor = ArgumentCaptor.forClass(List.class);

        verify(service)
                .getPrinters(printerIdsArgumentCaptor.capture());

        assertThat(printerIdsArgumentCaptor.getValue()).isEqualTo(printerIds);
    }

    @Test
    @DisplayName("INTEGRATION: getPrintersForPeriod() invokes service method.")
    void integrationTestGetPrintersForPeriod() {
        Date from = mock(Date.class, RETURNS_DEEP_STUBS);
        Date to = mock(Date.class, RETURNS_DEEP_STUBS);
        List printerIds = mock(List.class, RETURNS_DEEP_STUBS);
        PeriodAndPrinterRequest request = new PeriodAndPrinterRequest(from, to, printerIds);

        componentUnderTest.getPrintersForPeriod(request);

        ArgumentCaptor<Date> fromArgumentCaptor = ArgumentCaptor.forClass(Date.class);
        ArgumentCaptor<Date> toArgumentCaptor = ArgumentCaptor.forClass(Date.class);
        ArgumentCaptor<List> printerIdsArgumentCaptor = ArgumentCaptor.forClass(List.class);

        verify(service)
                .getPrintersForPeriod(
                        fromArgumentCaptor.capture(),
                        toArgumentCaptor.capture(),
                        printerIdsArgumentCaptor.capture());

        assertThat(fromArgumentCaptor.getValue()).isEqualTo(from);
        assertThat(toArgumentCaptor.getValue()).isEqualTo(to);
        assertThat(printerIdsArgumentCaptor.getValue()).isEqualTo(printerIds);
    }

    @Test
    @DisplayName("UNIT: getAll() returns (404)NOT FOUND when no data present.")
    void unitTestGetAllNotFound() {
        componentUnderTest.getAll();
        verify(service).getAll();
    }
}