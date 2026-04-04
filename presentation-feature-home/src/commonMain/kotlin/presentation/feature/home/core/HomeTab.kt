package presentation.feature.home.core

/**
 * Enumeration of the top-level tabs available on the Home screen.
 *
 * Each entry corresponds to a distinct sub-feature displayed inside the
 * [presentation.feature.home.source.home.HomeSuccessContent] tab container.
 * The ordinal value is used as the index for the bottom [TabBar].
 *
 * @see presentation.feature.home.source.home.HomeSuccessContent
 */
public enum class HomeTab {
    /** Saved files / library listing. */
    FILES,

    /** EPUB creation from URL or plain text. */
    CREATE,

    /** Sleep / lock screen for the e-reader device. */
    SLEEP,

    /** Device info, orientation, and theme settings. */
    DEVICE,
}
