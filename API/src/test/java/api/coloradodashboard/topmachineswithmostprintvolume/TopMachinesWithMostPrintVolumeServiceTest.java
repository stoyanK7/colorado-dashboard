package api.coloradodashboard.topmachineswithmostprintvolume;

import api.coloradodashboard.repository.TopMachinesWithMostPrintVolumeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
@ExtendWith(MockitoExtension.class)
class TopMachinesWithMostPrintVolumeServiceTest {
    @InjectMocks
    private TopMachinesWithMostPrintVolumeService componentUnderTest;
    @Mock
    private TopMachinesWithMostPrintVolumeRepository repository;

    @Test
    @DisplayName("INTEGRATION: getAll() invokes repository method.")
    void integrationTestGetAll() {
        componentUnderTest.getAll();
        verify(repository).getAll();
    }

    @Test
    @DisplayName("INTEGRATION: getAllForPeriod() invokes repository method.")
    void integrationTestGetAllForPeriod() {
        Date from = mock(Date.class, RETURNS_DEEP_STUBS);
        Date to = mock(Date.class, RETURNS_DEEP_STUBS);

        componentUnderTest.getAllForPeriod(from, to);

        ArgumentCaptor<Date> fromArgumentCaptor = ArgumentCaptor.forClass(Date.class);
        ArgumentCaptor<Date> toArgumentCaptor = ArgumentCaptor.forClass(Date.class);

        verify(repository)
                .getAllForPeriod(fromArgumentCaptor.capture(), toArgumentCaptor.capture());

        assertThat(fromArgumentCaptor.getValue()).isEqualTo(from);
        assertThat(toArgumentCaptor.getValue()).isEqualTo(to);
    }

    @Test
    @DisplayName("INTEGRATION: getPrinters() invokes repository method.")
    void integrationTestGetPrinters() {
        List printerIds = mock(List.class, RETURNS_DEEP_STUBS);

        componentUnderTest.getPrinters(printerIds);

        ArgumentCaptor<List> printerIdsArgumentCaptor = ArgumentCaptor.forClass(List.class);

        verify(repository)
                .getPrinters(printerIdsArgumentCaptor.capture());

        assertThat(printerIdsArgumentCaptor.getValue()).isEqualTo(printerIds);
    }

    @Test
    @DisplayName("INTEGRATION: getPrintersForPeriod() invokes repository method.")
    void integrationTestGetPrintersForPeriod() {
        Date from = mock(Date.class, RETURNS_DEEP_STUBS);
        Date to = mock(Date.class, RETURNS_DEEP_STUBS);
        List printerIds = mock(List.class, RETURNS_DEEP_STUBS);

        componentUnderTest.getPrintersForPeriod(from, to, printerIds);

        ArgumentCaptor<Date> fromArgumentCaptor = ArgumentCaptor.forClass(Date.class);
        ArgumentCaptor<Date> toArgumentCaptor = ArgumentCaptor.forClass(Date.class);
        ArgumentCaptor<List> printerIdsArgumentCaptor = ArgumentCaptor.forClass(List.class);

        verify(repository)
                .getPrintersForPeriod(
                        fromArgumentCaptor.capture(),
                        toArgumentCaptor.capture(),
                        printerIdsArgumentCaptor.capture());

        assertThat(fromArgumentCaptor.getValue()).isEqualTo(from);
        assertThat(toArgumentCaptor.getValue()).isEqualTo(to);
        assertThat(printerIdsArgumentCaptor.getValue()).isEqualTo(printerIds);
    }
}