package api.coloradodashboard.repository.base;

import api.coloradodashboard.dto.PeriodDto;
import api.coloradodashboard.entity.base.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Date;
import java.util.List;

/**
 * Base repository interface that defines the methods/queries each repository should
 * implement.
 *
 * @param <E> Entity
 * @param <D> Dto
 */
@NoRepositoryBean
public interface BaseRepository<E extends BaseEntity, D>
        extends JpaRepository<E, Long> {
    /**
     * Retrieves all data for all time summarized for all printers.
     *
     * @param dateFormat The format of the date. Decides whether the data will be
     *                   summarized by day, week, etc.
     * @return List of DTOs
     */
    List<D> getAllAggregated(String dateFormat);

    /**
     * Retrieves all data for all time separate for each printer.
     *
     * @param dateFormat The format of the date. Decides whether the data will be
     *                   summarized by day, week, etc.
     * @return List of DTOs
     */
    List<D> getAllNonAggregated(String dateFormat);

    /**
     * Retrieves all data for provided time period summarized by date.
     *
     * @param dateFormat The format of the date. Decides whether the data will be
     *                   summarized by day, week, etc.
     * @param from       Starting date
     * @param to         Ending date
     * @return List of DTOs
     */
    List<D> getAllForPeriodAggregated(String dateFormat,
                                      Date from,
                                      Date to);

    /**
     * Retrieves all data for provided time period separate for each printer.
     *
     * @param dateFormat The format of the date. Decides whether the data will be
     *                   summarized by day, week, etc.
     * @param from       Starting date
     * @param to         Ending date
     * @return List of DTOs
     */
    List<D> getAllForPeriodNonAggregated(String dateFormat,
                                         Date from,
                                         Date to);

    /**
     * Retrieves data for all time and printers of interest summarized by date.
     *
     * @param dateFormat The format of the date. Decides whether the data will be
     *                   summarized by day, week, etc.
     * @param printerIds Printers of interest
     * @return List of DTOs
     */
    List<D> getAllForPrintersAggregated(String dateFormat,
                                        List<String> printerIds);

    /**
     * Retrieves data for all time and printers of interest separate for each printer.
     *
     * @param dateFormat The format of the date. Decides whether the data will be
     *                   summarized by day, week, etc.
     * @param printerIds Printers of interest
     * @return List of DTOs
     */
    List<D> getAllForPrintersNonAggregated(String dateFormat,
                                           List<String> printerIds);

    /**
     * Retrieves data for provided time period and printers of interest summarized
     * by date.
     *
     * @param dateFormat The format of the date. Decides whether the data will be
     *                   summarized by day, week, etc.
     * @param from       Starting date
     * @param to         Ending date
     * @param printerIds Printers of interest
     * @return List of DTOs
     */
    List<D> getAllForPeriodAndPrintersAggregated(String dateFormat,
                                                 Date from, Date to,
                                                 List<String> printerIds);

    /**
     * Retrieves data for provided time period and printers of interest separate
     * for each printer
     *
     * @param dateFormat The format of the date. Decides whether the data will be
     *                   summarized by day, week, etc.
     * @param from       Starting date
     * @param to         Ending date
     * @param printerIds Printers of interest
     * @return List of DTOs
     */
    List<D> getAllForPeriodAndPrintersNonAggregated(String dateFormat,
                                                    Date from,
                                                    Date to,
                                                    List<String> printerIds);

    /**
     * Retrieves the minimum and maximum date in the database table.
     *
     * @return PeriodDto object containing 'from' and 'to' fields, representing
     * the earliest and latest date.
     */
    PeriodDto getAvailableTimePeriod();

    /**
     * Retrieves a list of all printers in the database table.
     *
     * @return PrinterIdsDto containing an array of the printers.
     */
    List<String> getAvailablePrinters();

    /**
     * Retrieves a list of the chart data keys that Recharts requires in order
     * to visualize the chart. Not all charts might need this method, but most do.
     *
     * @return ChartDataKeysDto containing an array of the keys.
     */
    List<String> getChartDataKeys();
}
