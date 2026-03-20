package presentation.core.platform.source.date

import java.util.Calendar

public actual fun getCurrentDate(): CurrentDate {
    val cal = Calendar.getInstance()
    return CurrentDate(
        year = cal.get(Calendar.YEAR),
        month = cal.get(Calendar.MONTH) + 1,
        day = cal.get(Calendar.DAY_OF_MONTH),
    )
}

public actual fun currentTimeMillis(): Long = System.currentTimeMillis()
