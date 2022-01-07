package api.coloradodashboard.repository.base;

import api.coloradodashboard.dto.PeriodDto;

import java.util.Date;
import java.util.List;

/**
 * Base repository interface that defines the methods/queries each repository should
 * implement.
 *
 * @param <T> DTO that the repository returns.
 */
public interface BaseRepository<T> {
    /**
     * Retrieves all data for all time summarized for all printers.
     *
     * @param dateFormat The format of the date. Decides whether the data will be
     *                   summarized by day, week, etc.
     * @return List of DTOs
     */
    List<T> getAllAggregated(String dateFormat);

    /**
     * Retrieves all data for all time separate for each printer.
     *
     * @param dateFormat The format of the date. Decides whether the data will be
     *                   summarized by day, week, etc.
     * @return List of DTOs
     */
    List<T> getAllNonAggregated(String dateFormat);

    /**
     * Retrieves all data for provided time period summarized by date.
     *
     * @param dateFormat The format of the date. Decides whether the data will be
     *                   summarized by day, week, etc.
     * @param from       Starting date
     * @param to         Ending date
     * @return List of DTOs
     */
    List<T> getAllForPeriodAggregated(String dateFormat,
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
    List<T> getAllForPeriodNonAggregated(String dateFormat,
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
    List<T> getAllForPrintersAggregated(String dateFormat,
                                        List<String> printerIds);

    /**
     * Retrieves data for all time and printers of interest separate for each printer.
     *
     * @param dateFormat The format of the date. Decides whether the data will be
     *                   summarized by day, week, etc.
     * @param printerIds Printers of interest
     * @return List of DTOs
     */
    List<T> getAllForPrintersNonAggregated(String dateFormat,
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
    List<T> getAllForPeriodAndPrintersAggregated(String dateFormat,
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
    List<T> getAllForPeriodAndPrintersNonAggregated(String dateFormat,
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
