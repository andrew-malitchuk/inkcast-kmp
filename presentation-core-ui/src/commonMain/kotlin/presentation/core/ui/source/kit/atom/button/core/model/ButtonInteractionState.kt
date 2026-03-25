package presentation.core.ui.source.kit.atom.button.core.model

/**
 * Bitmask flags representing the current interaction state of a button.
 *
 * Multiple flags can be combined with bitwise OR and tested with bitwise AND.
 */
internal object ButtonInteractionState {
    /** Pointer is hovering over the button. */
    val HOVER: Int = 1.shl(0)

    /** Button is currently being pressed. */
    val PRESSED: Int = 1.shl(1)

    /** Button has keyboard focus. */
    val FOCUSED: Int = 1.shl(2)

    /** Button is in its selected / toggled-on state. */
    val SELECTED: Int = 1.shl(3)
}
