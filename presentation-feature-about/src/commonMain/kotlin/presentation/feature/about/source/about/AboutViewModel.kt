package presentation.feature.about.source.about

import androidx.lifecycle.ViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.viewmodel.container

/**
 * Orbit MVI ViewModel for the About feature screen.
 *
 * Manages [AboutState] and emits [AboutSideEffect] in response to [AboutIntent] actions.
 *
 * @see AboutScreen
 * @see AboutContract
 */
@OrbitExperimental
public class AboutViewModel(
    // inject use cases here
) : ContainerHost<AboutState, AboutSideEffect>, ViewModel() {

    override val container: Container<AboutState, AboutSideEffect> =
        container<AboutState, AboutSideEffect>(AboutState())

    public fun handleIntent(intent: AboutIntent) {
        when (intent) {
            AboutIntent.OnBackClick -> onBackClick()
        }
    }

    private fun onBackClick() = intent {
        postSideEffect(AboutSideEffect.GoBackEffect)
    }
}
