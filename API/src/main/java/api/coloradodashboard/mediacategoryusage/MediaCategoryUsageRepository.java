package api.coloradodashboard.mediacategoryusage;

import api.coloradodashboard.PeriodDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Repository providing access to the table with all data for <b>Media categories
 * usage</b>.
 */
public interface MediaCategoryUsageRepository extends JpaRepository<MediaCategoryUsageEntity, Long> {
    /**
     * Retrieve aggregated printed square meters for all media categories for all printers for all time
     * grouping by printer, ordered by printed square meters descending.
     *
     * @return A <b>list of MediaCategoryUsageDto objects</b>, each
     * one representing a different printer. An <b>empty list</b> if no data
     * is present in the database.
     */
    @Query("SELECT new api.coloradodashboard.mediacategoryusage.MediaCategoryUsageDto(m.date, sum(m.filmUsed), sum(m.lightPaperUsed), sum(m.heavyPaperUsed), sum(m.lightBannerUsed), sum(m.textileUsed), sum(m.monomericVinylUsed), sum(m.canvasUsed), sum(m.polymericAndCastVinylUsed), sum(m.heavyBannerUsed), sum(m.paperUsed), sum(m.thickFilmUsed)) " +
            "FROM MediaCategoryUsageEntity m " +
            "GROUP BY m.date " +
            "ORDER BY m.date ASC")
    List<MediaCategoryUsageDto> getAll();

    /**
     * Retrieve aggregated printed square meters for all media categories for all printers for provided
     * period of interest, ordered by printed square meters descending.
     *
     * @param from Period of interest starting date inclusive.
     * @param to   Period of interest ending date inclusive.
     * @return A <b>list of MediaCategoryUsageDto objects</b>, each
     * one representing a different printer. An <b>empty list</b> if no data
     * is present in the database.
     */
    @Query("SELECT new api.coloradodashboard.mediacategoryusage.MediaCategoryUsageDto(m.date, sum(m.filmUsed), sum(m.lightPaperUsed), sum(m.heavyPaperUsed), sum(m.lightBannerUsed), sum(m.textileUsed), sum(m.monomericVinylUsed), sum(m.canvasUsed), sum(m.polymericAndCastVinylUsed), sum(m.heavyBannerUsed), sum(m.paperUsed), sum(m.thickFilmUsed)) " +
            "FROM MediaCategoryUsageEntity m " +
            "WHERE m.date BETWEEN :from AND :to " +
            "GROUP BY m.date " +
            "ORDER BY m.date ASC")
    List<MediaCategoryUsageDto> getAllForPeriod(@Param("from") Date from, @Param("to") Date to);

    /**
     * Retrieve aggregated printed square meters for all media categories for all time for provided
     * list of printers, ordered by printed square meters descending.
     *
     * @param printerIds List of printer id's.
     * @return A <b>list of MediaCategoryUsageDto objects</b>, each
     * one representing a different printer. An <b>empty list</b> if no data
     * is present in the database.
     */
    @Query("SELECT new api.coloradodashboard.mediacategoryusage.MediaCategoryUsageDto(m.date, sum(m.filmUsed), sum(m.lightPaperUsed), sum(m.heavyPaperUsed), sum(m.lightBannerUsed), sum(m.textileUsed), sum(m.monomericVinylUsed), sum(m.canvasUsed), sum(m.polymericAndCastVinylUsed), sum(m.heavyBannerUsed), sum(m.paperUsed), sum(m.thickFilmUsed)) " +
            "FROM MediaCategoryUsageEntity m " +
            "WHERE m.printerId IN :printerIds " +
            "GROUP BY m.date " +
            "ORDER BY m.date ASC")
    List<MediaCategoryUsageDto> getPrinters(@Param("printerIds") List<String> printerIds);

    /**
     * Retrieve aggregated printed square meters for all media categories for provided period of interest
     * and list of printers, ordered by printed square meters descending.
     *
     * @param from       Period of interest starting date inclusive.
     * @param to         Period of interest ending date inclusive.
     * @param printerIds List of printer id's.
     * @return A <b>list of MediaCategoryUsageDto objects</b>, each
     * one representing a different printer. An <b>empty list</b> if no data
     * is present in the database.
     */
    @Query("SELECT new api.coloradodashboard.mediacategoryusage.MediaCategoryUsageDto(m.date, sum(m.filmUsed), sum(m.lightPaperUsed), sum(m.heavyPaperUsed), sum(m.lightBannerUsed), sum(m.textileUsed), sum(m.monomericVinylUsed), sum(m.canvasUsed), sum(m.polymericAndCastVinylUsed), sum(m.heavyBannerUsed), sum(m.paperUsed), sum(m.thickFilmUsed)) " +
            "FROM MediaCategoryUsageEntity m " +
            "WHERE (m.date BETWEEN :from AND :to) " +
            "AND (m.printerId IN :printerIds) " +
            "GROUP BY m.date " +
            "ORDER BY m.date ASC")
    List<MediaCategoryUsageDto> getPrintersForPeriod(@Param("from") Date from, @Param("to") Date to, @Param("printerIds") List<String> printerIds);

    /**
     * Retrieve min and max date from the table.
     *
     * @return A <b>PeriodDto object</b> containing the min and max date. <b>From</b>
     * contains the <b>min</b> and <b>to</b> contains the <b>max</b> date.
     */
    @Query("SELECT new api.coloradodashboard.PeriodDto(min(m.date), max(m.date)) " +
            "FROM MediaCategoryUsageEntity m")
    PeriodDto getAvailableTimePeriod();

    /**
     * Retrieve all available printers from the table.
     *
     * @return A <b>list of Strings</b>, each one representing a <b>printer id</b>.
     */
    @Query("SELECT m.printerId " +
            "FROM MediaCategoryUsageEntity m " +
            "GROUP BY m.printerId " +
            "ORDER BY m.printerId ASC")
    List<String> getAvailablePrinters();
}
