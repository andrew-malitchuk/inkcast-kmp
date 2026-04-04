package presentation.core.platform.source.date

/**
 * Snapshot of the current calendar date.
 *
 * @property year Full year (e.g. 2026).
 * @property month Month of year (1–12).
 * @property day Day of month (1–31).
 */
public data class CurrentDate(val year: Int, val month: Int, val day: Int)

/**
 * Returns the current calendar date from the platform clock.
 */
public expect fun getCurrentDate(): CurrentDate

/**
 * Returns the current system time in milliseconds since Unix epoch.
 */
public expect fun currentTimeMillis(): Long
