package presentation.core.platform.source.date

import platform.Foundation.NSCalendar
import platform.Foundation.NSCalendarUnitDay
import platform.Foundation.NSCalendarUnitMonth
import platform.Foundation.NSCalendarUnitYear
import platform.Foundation.NSDate
import platform.Foundation.timeIntervalSince1970

/**
 * iOS implementation using `NSCalendar` and `NSDate`.
 *
 * @return Current date with year, month (1-based), and day fields.
 */
public actual fun getCurrentDate(): CurrentDate {
    val cal = NSCalendar.currentCalendar
    val components = cal.components(
        NSCalendarUnitYear or NSCalendarUnitMonth or NSCalendarUnitDay,
        NSDate(),
    )
    return CurrentDate(
        year = components.year.toInt(),
        month = components.month.toInt(),
        day = components.day.toInt(),
    )
}

/** iOS implementation using `NSDate.timeIntervalSince1970` converted to milliseconds. */
public actual fun currentTimeMillis(): Long =
    (NSDate().timeIntervalSince1970 * 1000).toLong()
