package api.coloradodashboard;

import api.coloradodashboard.inkusage.*;
import api.coloradodashboard.mediacategoryusage.MediaCategoryUsageConverter;
import api.coloradodashboard.mediacategoryusage.MediaCategoryUsageRepository;
import api.coloradodashboard.mediacategoryusage.MediaCategoryUsageService;
import api.coloradodashboard.squaremeterperprintmode.SquareMeterPerPrintModeConverter;
import api.coloradodashboard.squaremeterperprintmode.SquareMeterPerPrintModeRepository;
import api.coloradodashboard.squaremeterperprintmode.SquareMeterPerPrintModeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
public class CanonDashboardApiMockingTests {
    @Mock private InkUsageRepository inkUsageRepo;
    @Mock private MediaCategoryUsageRepository mediaCategoryUsageRepo;
    @Mock private SquareMeterPerPrintModeRepository squareMeterPerPrintModeRepo;
//    @Mock private InkUsageConverter inkUsageConverter ;
//    @Mock private MediaCategoryUsageConverter mediaCategoryUsageConverter ;
//    @Mock private SquareMeterPerPrintModeConverter  squareMeterPerPrintModeConverter ;

    private InkUsageService inkUsageServiceTest;
    private MediaCategoryUsageService mediaCategoryServiceTest;
    private SquareMeterPerPrintModeService squareMeterServiceTest;
    Date testDate;

    @BeforeEach
    void setUp() throws ParseException {
        inkUsageServiceTest = new InkUsageService(inkUsageRepo);
        mediaCategoryServiceTest = new MediaCategoryUsageService(mediaCategoryUsageRepo);
        squareMeterServiceTest = new SquareMeterPerPrintModeService(squareMeterPerPrintModeRepo);
        testDate=new SimpleDateFormat("dd/MM/yyyy").parse("12/12/2021");
    }

    @Test
    void canGetInkUsagesWhenThereIsData(){
        //when
        inkUsageServiceTest.getAll();
        //then
        verify(inkUsageRepo).getAll();
    }

    @Test
    void CanGetMediaCategoryWhenThereIsData(){
        //when
        mediaCategoryServiceTest.getAll();
        //then
        verify(mediaCategoryUsageRepo).getAll();
    }
    @Test
    void canGetInkUsageWhenThereIsNoData(){
        //when
        squareMeterServiceTest.getAll();
        //then
        verify(squareMeterPerPrintModeRepo).getAll();
    }
}
