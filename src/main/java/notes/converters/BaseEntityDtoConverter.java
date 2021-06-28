package notes.converters;

import lombok.SneakyThrows;
import notes.dto.Dto;
import notes.models.BaseEntity;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseEntityDtoConverter<EntityT extends BaseEntity, SaveDtoT extends Dto, GetDtoT extends Dto> {

    private final Class<EntityT> entityTClass;

    public BaseEntityDtoConverter(Class<EntityT> entityTClass) {
        this.entityTClass = entityTClass;
    }

    @SneakyThrows //TODO Add custom exception to wrap the below ones
    public EntityT newEntityFromDto(SaveDtoT saveDto, @NotNull String userId) {
        EntityT entityT = entityTClass.getDeclaredConstructor().newInstance();
        enrichEntityWithDto(entityT, saveDto);
        entityT.setCreatedBy(userId);
        return entityT;
    }

    abstract void enrichEntityWithDto(EntityT entity, SaveDtoT saveDto);

    abstract GetDtoT convertEntityToDto(EntityT entity);

    public List<GetDtoT> convertEntityToDto(List<EntityT> entityList) {
        return entityList.stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }

}
