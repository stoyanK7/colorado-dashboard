package api.coloradodashboard.topmachineswithmostprintvolume;

import api.coloradodashboard.PeriodAndPrinterIdsDto;
import api.coloradodashboard.PeriodDto;
import api.coloradodashboard.PrinterIdsDto;
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
class TopMachinesWithMostPrintVolumeControllerIntegrationTest {
    @InjectMocks
    private TopMachinesWithMostPrintVolumeController componentUnderTest;
    @Mock
    private TopMachinesWithMostPrintVolumeService service;

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
        PeriodDto request = new PeriodDto(from, to);

        componentUnderTest.getAllForPeriod(request);

        ArgumentCaptor<Date> fromArgumentCaptor = ArgumentCaptor.forClass(Date.class);
        ArgumentCaptor<Date> toArgumentCaptor = ArgumentCaptor.forClass(Date.class);

        verify(service)
                .getAllForPeriod(fromArgumentCaptor.capture(), toArgumentCaptor.capture());


        assertThat(fromArgumentCaptor.getValue()).isEqualTo(PeriodDto.removeTime(from));
        assertThat(toArgumentCaptor.getValue()).isEqualTo(PeriodDto.removeTime(to));
    }

    @Test
    @DisplayName("INTEGRATION: getPrinters() invokes service method.")
    void integrationTestGetPrinters() {
        List printerIds = mock(List.class, RETURNS_DEEP_STUBS);
        PrinterIdsDto request = new PrinterIdsDto(printerIds);

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
        PeriodAndPrinterIdsDto request = new PeriodAndPrinterIdsDto(from, to, printerIds);

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
}