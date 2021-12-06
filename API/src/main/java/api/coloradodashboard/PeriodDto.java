package api.coloradodashboard;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Calendar;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
public class PeriodDto {
    @Temporal(TemporalType.DATE)
    private Date from;
    @Temporal(TemporalType.DATE)
    private Date to;

    public Date getFrom() {
        return removeTime(from);
    }

    public Date getTo() {
        return removeTime(to);
    }

    /**
     * @param date Any Date object.
     * @return Same Date object with time set to 00:00:00.
     */
    public static Date removeTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
}
