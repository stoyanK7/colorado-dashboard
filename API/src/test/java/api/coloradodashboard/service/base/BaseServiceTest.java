package api.coloradodashboard.service.base;

import api.coloradodashboard.repository.base.BaseRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BaseServiceTest {
    @InjectMocks
    private BaseService componentUnderTest;
    @Mock
    private BaseRepository repository;
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
    @DisplayName("INTEGRATION: getAll(true,...) invokes repository getAllAggregated().")
    void getAllAggregated() {
        componentUnderTest
                .getAll(true, "day");

        verify(repository)
                .getAllAggregated(any());
    }

    @Test
    @DisplayName("INTEGRATION: getAll(false,...) invokes repository getAllNonAggregated().")
    void getAllNonAggregated() {
        componentUnderTest
                .getAll(false, "day");

        verify(repository)
                .getAllNonAggregated(any());
    }

    @Test
    @DisplayName("INTEGRATION: getAllForPeriod(true,...) invokes repository getAllForPeriodAggregated().")
    void getAllForPeriodAggregated() {
        componentUnderTest
                .getAllForPeriod(true, "day", from, to);

        verify(repository)
                .getAllForPeriodAggregated(any(),
                        fromArgumentCaptor.capture(),
                        toArgumentCaptor.capture());
        assertThat(fromArgumentCaptor.getValue())
                .isEqualTo(from);
        assertThat(toArgumentCaptor.getValue())
                .isEqualTo(to);
    }

    @Test
    @DisplayName("INTEGRATION: getAllForPeriod(false,...) invokes repository getAllForPeriodNonAggregated().")
    void getAllForPeriodNonAggregated() {
        componentUnderTest
                .getAllForPeriod(false, "day", from, to);

        verify(repository)
                .getAllForPeriodNonAggregated(any(),
                        fromArgumentCaptor.capture(),
                        toArgumentCaptor.capture());
        assertThat(fromArgumentCaptor.getValue())
                .isEqualTo(from);
        assertThat(toArgumentCaptor.getValue())
                .isEqualTo(to);
    }

    @Test
    @DisplayName("INTEGRATION: getAllForPrinters(true,...) invokes repository getAllForPrintersAggregated().")
    void getAllForPrintersAggregated() {
        componentUnderTest
                .getAllForPrinters(true, "day", printerIds);

        verify(repository)
                .getAllForPrintersAggregated(any(),
                        printerIdsArgumentCaptor.capture());
        assertThat(printerIdsArgumentCaptor.getValue())
                .isEqualTo(printerIds);
    }

    @Test
    @DisplayName("INTEGRATION: getAllForPrinters(false,...) invokes repository getAllForPrintersNonAggregated().")
    void getAllForPrintersNonAggregated() {
        componentUnderTest
                .getAllForPrinters(false, "day", printerIds);

        verify(repository)
                .getAllForPrintersNonAggregated(any(),
                        printerIdsArgumentCaptor.capture());
        assertThat(printerIdsArgumentCaptor.getValue())
                .isEqualTo(printerIds);
    }

    @Test
    @DisplayName("INTEGRATION: getAllForPeriodAndPrinters(true,...) invokes repository getAllForPeriodAndPrintersAggregated().")
    void getAllForPeriodAndPrintersAggregated() {
        componentUnderTest
                .getAllForPeriodAndPrinters(true,
                        "day",
                        from,
                        to,
                        printerIds);

        verify(repository)
                .getAllForPeriodAndPrintersAggregated(any(),
                        fromArgumentCaptor.capture(),
                        toArgumentCaptor.capture(),
                        printerIdsArgumentCaptor.capture());
        assertThat(fromArgumentCaptor.getValue())
                .isEqualTo(from);
        assertThat(toArgumentCaptor.getValue())
                .isEqualTo(to);
        assertThat(printerIdsArgumentCaptor.getValue())
                .isEqualTo(printerIds);
    }

    @Test
    @DisplayName("INTEGRATION: getAllForPeriodAndPrinters(false,...) invokes repository getAllForPeriodAndPrintersNonAggregated().")
    void getAllForPeriodAndPrintersNonAggregated() {
        componentUnderTest
                .getAllForPeriodAndPrinters(false,
                        "day",
                        from,
                        to,
                        printerIds);

        verify(repository)
                .getAllForPeriodAndPrintersNonAggregated(any(),
                        fromArgumentCaptor.capture(),
                        toArgumentCaptor.capture(),
                        printerIdsArgumentCaptor.capture());
        assertThat(fromArgumentCaptor.getValue())
                .isEqualTo(from);
        assertThat(toArgumentCaptor.getValue())
                .isEqualTo(to);
        assertThat(printerIdsArgumentCaptor.getValue())
                .isEqualTo(printerIds);
    }

    @Test
    @DisplayName("INTEGRATION: getAvailableTimePeriod() invokes repository method.")
    void getAvailableTimePeriod() {
        componentUnderTest
                .getAvailableTimePeriod();

        verify(repository)
                .getAvailableTimePeriod();
    }

    @Test
    @DisplayName("INTEGRATION: getAvailablePrinters() invokes repository method.")
    void getAvailablePrinters() {
        componentUnderTest
                .getAvailablePrinters();

        verify(repository)
                .getAvailablePrinters();
    }

    @Test
    @DisplayName("INTEGRATION: getChartDataKeys() invokes repository method.")
    void getChartDataKeys() {
        componentUnderTest
                .getChartDataKeys();

        verify(repository)
                .getChartDataKeys();
    }
}