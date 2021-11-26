package api.coloradodashboard;

import api.coloradodashboard.inkusage.InkUsage;
import api.coloradodashboard.inkusage.InkUsageService;
import api.coloradodashboard.interfaces.GenericRepository;
import api.coloradodashboard.interfaces.GenericService;
import api.coloradodashboard.mediacategoryusage.MediaCategoryUsageService;
import api.coloradodashboard.squaremeterperprintmode.SquareMeterPerPrintModeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;

@SpringBootTest
class CanonDashboardApiApplicationTests {
    @Autowired
    InkUsageService inkService;
    @Autowired
    MediaCategoryUsageService mediaCategoryUsageService;
    @Autowired
    SquareMeterPerPrintModeService squareMeterPerPrintModeServiceService;

    @Test
    void getInkUsageWhenThereIsNoData() {
        List<InkUsage> fullData = inkService.getAll();
        Assertions.assertEquals(0,fullData.size());
    }

    @Test
    void getInkUsageWithPresentData() {

        List<InkUsage> fullData = inkService.getAll();
        Assertions.assertEquals(0,fullData.size());
    }

    @Test
    void getMediaCategoryUsageWhenThereIsNoData() {
        List<InkUsage> fullData = inkService.getAll();
        Assertions.assertEquals(0,fullData.size());
    }

    @Test
    void getMediaCategoryUsageWithPresentData() {

        List<InkUsage> fullData = inkService.getAll();
        Assertions.assertEquals(0,fullData.size());
    }

}
