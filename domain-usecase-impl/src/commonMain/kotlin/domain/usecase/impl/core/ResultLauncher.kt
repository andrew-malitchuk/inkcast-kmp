package domain.usecase.impl.core

import domain.core.monad.Failure

/**
 * Executes [block] and wraps its result in [Result.success].
 *
 * If [block] throws a [Failure] (a domain-level error), it is propagated as-is
 * inside [Result.failure]. Any other [Throwable] is first mapped through
 * [errorMapper] before being wrapped, ensuring that infrastructure exceptions
 * are translated into the appropriate [Failure] subtype.
 *
 * @param T The success type.
 * @param errorMapper Converts unexpected [Throwable]s into a [Failure].
 * @param block The suspending computation to execute.
 * @return [Result.success] with the computed value, or [Result.failure] with a [Failure].
 */
internal suspend inline fun <T> resultLauncher(
    errorMapper: (Throwable) -> Failure,
    crossinline block: suspend () -> T,
): Result<T> = try {
    Result.success(block())
} catch (failure: Failure) {
    Result.failure(failure)
} catch (throwable: Throwable) {
    Result.failure(errorMapper(throwable))
}
