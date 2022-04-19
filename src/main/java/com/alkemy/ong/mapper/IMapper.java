package com.alkemy.ong.mapper;

import java.util.List;

/**
 * Interface used for mapping entities and dtos
 * @param <E> Entity
 * @param <T> Dto
 */
public interface IMapper<E, T> {

    /**
     * Method used for mapping from dto to entity (create endpoint)
     * @param dto Dto
     * @return Entity
     */
    public E toEntity(T dto);

    /**
     * Method used for mapping from dto to entity (update endpoint)
     * @param id Id of the entity in the database
     * @param dto Dto
     * @return Entity
     */
    public E toEntity(Long id, T dto);

    /**
     * Method used for mapping from entity to dto to retrieve data (get endpoints and represent persisted objects)
     * @param entity Entity
     * @return Dto
     */
    public T toDto(E entity);

    /**
     * Method used for mapping a collection of entities into a collection of dtos. Same as above
     * @param list Entity collection
     * @return Dto collection
     */
    public List<T> toDtoList(List<E> list);

}
