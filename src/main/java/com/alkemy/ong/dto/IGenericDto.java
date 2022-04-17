package com.alkemy.ong.dto;

public interface IGenericDto<T extends Enum> {

    public Object generateDto(T type);

}
