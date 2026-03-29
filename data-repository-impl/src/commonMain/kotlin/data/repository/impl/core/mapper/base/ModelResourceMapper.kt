package data.repository.impl.core.mapper.base

import common.core.core.mapper.Mapper
import data.core.source.resource.Resource
import domain.core.source.model.base.Model

/**
 * Bidirectional mapper between domain [Model] and data [Resource] types.
 *
 * Usage example:
 * ```kotlin
 * object UserMapper : ModelResourceMapper<UserModel, UserDto> {
 *     override val toModel = Mapper<UserDto, UserModel> { dto ->
 *         UserModel(id = dto.id, email = dto.email)
 *     }
 *     override val toResource = Mapper<UserModel, UserDto> { model ->
 *         UserDto(id = model.id, email = model.email)
 *     }
 * }
 * ```
 *
 * @param MODEL The domain model type.
 * @param RESOURCE The data resource type.
 */
public interface ModelResourceMapper<MODEL : Model, RESOURCE : Resource> {
    /**
     * Mapper to convert a [RESOURCE] to a [MODEL].
     */
    public val toModel: Mapper<RESOURCE, MODEL>

    /**
     * Mapper to convert a [MODEL] to a [RESOURCE].
     */
    public val toResource: Mapper<MODEL, RESOURCE>
}
