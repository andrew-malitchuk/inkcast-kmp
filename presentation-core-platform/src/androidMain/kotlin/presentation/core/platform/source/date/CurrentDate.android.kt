package presentation.core.platform.source.date

import java.util.Calendar

/**
 * Android implementation using `java.util.Calendar`.
 *
 * @return Current date with year, month (1-based), and day fields.
 */
public actual fun getCurrentDate(): CurrentDate {
    val cal = Calendar.getInstance()
    return CurrentDate(
        year = cal.get(Calendar.YEAR),
        month = cal.get(Calendar.MONTH) + 1,
        day = cal.get(Calendar.DAY_OF_MONTH),
    )
}

/** Android implementation delegating to [System.currentTimeMillis]. */
public actual fun currentTimeMillis(): Long = System.currentTimeMillis()
