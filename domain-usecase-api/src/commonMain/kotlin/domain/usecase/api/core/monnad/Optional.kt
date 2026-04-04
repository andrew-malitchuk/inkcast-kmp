package domain.usecase.api.core.monnad

/**
 * Represents the result of an operation that returns no specific value (Unit), but can fail with a [domain.core.core.monad.Failure].
 * This is a typealias for [Result<Unit>].
 */
public typealias Optional = Result<Unit>
