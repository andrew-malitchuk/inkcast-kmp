package presentation.core.ui.source.kit.atom.button.core.model

internal object ButtonInteractionState {
    val HOVER: Int = 1.shl(0)

    val PRESSED: Int = 1.shl(1)

    val FOCUSED: Int = 1.shl(2)

    val SELECTED: Int = 1.shl(3)
}
