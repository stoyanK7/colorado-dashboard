package api.coloradodashboard.controller.base;

import api.coloradodashboard.dto.PeriodAndPrinterIdsDto;
import api.coloradodashboard.dto.PeriodDto;
import api.coloradodashboard.dto.PrinterIdsDto;
import api.coloradodashboard.service.base.BaseService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BaseChartControllerIntegrationTest {
    @InjectMocks
    private BaseChartController componentUnderTest;
    @Mock
    private BaseService service;
    @Captor
    private ArgumentCaptor<Boolean> aggregatedArgumentCaptor;
    private boolean aggregated = true;
    @Captor
    private ArgumentCaptor<String> binArgumentCaptor;
    private String bin = "day";
    @Captor
    private ArgumentCaptor<Date> fromArgumentCaptor;
    private Date from = mock(Date.class);
    @Captor
    private ArgumentCaptor<Date> toArgumentCaptor;
    private Date to = mock(Date.class);
    @Captor
    private ArgumentCaptor<List<String>> printerIdsArgumentCaptor;
    private List<String> printerIds = mock(List.class);

    @Test
    @DisplayName("INTEGRATION: getAll() invokes service method.")
    void getAll() {
        componentUnderTest
                .getAll(aggregated, bin);

        verify(service)
                .getAll(aggregatedArgumentCaptor.capture(),
                        binArgumentCaptor.capture());
        assertThat(aggregatedArgumentCaptor.getValue())
                .isEqualTo(aggregated);
        assertThat(binArgumentCaptor.getValue())
                .isEqualTo(bin);
    }

    @Test
    @DisplayName("INTEGRATION: getAllForPeriod() invokes service method.")
    void getAllForPeriod() {
        componentUnderTest
                .getAllForPeriod(aggregated,
                        bin,
                        new PeriodDto(from, to));

        verify(service)
                .getAllForPeriod(aggregatedArgumentCaptor.capture(),
                        binArgumentCaptor.capture(),
                        fromArgumentCaptor.capture(),
                        toArgumentCaptor.capture());
        assertThat(aggregatedArgumentCaptor.getValue())
                .isEqualTo(aggregated);
        assertThat(binArgumentCaptor.getValue())
                .isEqualTo(bin);
        assertThat(fromArgumentCaptor.getValue())
                .isEqualTo(from);
        assertThat(toArgumentCaptor.getValue())
                .isEqualTo(to);
    }

    @Test
    @DisplayName("INTEGRATION: getAllForPrinters() invokes service method.")
    void getAllForPrinters() {
        componentUnderTest
                .getAllForPrinters(aggregated,
                        bin,
                        new PrinterIdsDto(printerIds));

        verify(service)
                .getAllForPrinters(aggregatedArgumentCaptor.capture(),
                        binArgumentCaptor.capture(),
                        printerIdsArgumentCaptor.capture());
        assertThat(aggregatedArgumentCaptor.getValue())
                .isEqualTo(aggregated);
        assertThat(binArgumentCaptor.getValue())
                .isEqualTo(bin);
        assertThat(printerIdsArgumentCaptor.getValue())
                .isEqualTo(printerIds);
    }

    @Test
    @DisplayName("INTEGRATION: getAllForPeriodAndPrinters() invokes service method.")
    void getAllForPeriodAndPrinters() {
        componentUnderTest
                .getAllForPeriodAndPrinters(aggregated,
                        bin,
                        new PeriodAndPrinterIdsDto(from, to, printerIds));

        verify(service)
                .getAllForPeriodAndPrinters(aggregatedArgumentCaptor.capture(),
                        binArgumentCaptor.capture(),
                        fromArgumentCaptor.capture(),
                        toArgumentCaptor.capture(),
                        printerIdsArgumentCaptor.capture());
        assertThat(aggregatedArgumentCaptor.getValue())
                .isEqualTo(aggregated);
        assertThat(binArgumentCaptor.getValue())
                .isEqualTo(bin);
        assertThat(fromArgumentCaptor.getValue())
                .isEqualTo(from);
        assertThat(toArgumentCaptor.getValue())
                .isEqualTo(to);
        assertThat(printerIdsArgumentCaptor.getValue())
                .isEqualTo(printerIds);
    }

    @Test
    @DisplayName("INTEGRATION: getAvailableTimePeriod() invokes service method.")
    void getAvailableTimePeriod() {
        componentUnderTest.getAvailableTimePeriod();

        verify(service).getAvailableTimePeriod();
    }

    @Test
    @DisplayName("INTEGRATION: getAvailablePrinters() invokes service method.")
    void getAvailablePrinters() {
        componentUnderTest.getAvailablePrinters();

        verify(service).getAvailablePrinters();
    }

    @Test
    @DisplayName("INTEGRATION: getChartDataKeys() invokes service method.")
    void getChartDataKeys() {
        componentUnderTest.getChartDataKeys();

        verify(service).getChartDataKeys();
    }
}