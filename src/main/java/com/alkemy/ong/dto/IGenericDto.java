package com.alkemy.ong.dto;

import org.springframework.context.MessageSource;

public interface IGenericDto<T extends Enum> {

    public Object generateDto(T type, MessageSource messageSource);

}
